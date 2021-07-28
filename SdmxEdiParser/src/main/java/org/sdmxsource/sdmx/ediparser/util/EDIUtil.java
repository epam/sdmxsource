/*******************************************************************************
 * Copyright (c) 2013 Metadata Technology Ltd.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License v 3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This file is part of the SDMX Component Library.
 *
 * The SDMX Component Library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with The SDMX Component Library If not, see
 * http://www.gnu.org/licenses/lgpl.
 *
 * Contributors:
 * Metadata Technology - initial API and implementation
 ******************************************************************************/
package org.sdmxsource.sdmx.ediparser.util;

import org.sdmxsource.sdmx.ediparser.constants.EDI_CONSTANTS;
import org.sdmxsource.sdmx.ediparser.constants.EDI_PREFIX;
import org.sdmxsource.sdmx.ediparser.model.reader.EDIReader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Edi util.
 */
public class EDIUtil {
    private static final DecimalFormat EIGHT_HASH_FORMATTER = new DecimalFormat("0.########E0");
    private static final DecimalFormat NINE_HASH_FORMATTER = new DecimalFormat("0.#########E0");
    private static final DecimalFormat TEN_HASH_FORMATTER = new DecimalFormat("0.##########E0");
    private static String siblingGroupId = "Sibling";
    private static boolean truncateLongValues = false;

    /**
     * Parse string as int int.
     *
     * @param str the str
     * @return the int
     */
    public static int parseStringAsInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Expecting integer value, but was '" + str + "'");
        }
    }

    //*********************************************************************************************//
    //***********************          COMMON                             *************************//
    //*********************************************************************************************//

    /**
     * Takes a string literal and converts it to an EDI valid string with escape characters
     *
     * @param inputStr the input str
     * @return string string
     */
    public static String stringToEdi(String inputStr) {
        inputStr = inputStr.replaceAll("\\?", "??");
        inputStr = inputStr.replaceAll(":", "?:");
        inputStr = inputStr.replaceAll("\\+", "?+");
        inputStr = inputStr.replaceAll("'", "?'");
        return inputStr;
    }

    /**
     * Takes a string literal and converts it to an EDI valid string with escape characters but it may truncate the string if it goes beyond
     * the specified maximum length. Note that the produced String may have a specified length greater than that specified if the inputString
     * has EDI escape characters (Release Characters).
     * e.g. Converting the 16 character string: 012+'?6789012345 to 10 characters would result in a 13 character string
     * being produced (012?+?'??6789).
     *
     * @param inputStr         the input str
     * @param maxAllowedLength The maximum string length, but based on the original string. Only specifying a positive integers will have any effect.
     * @return string string
     */
    public static String stringToEDIWithMaxLength(String inputStr, int maxAllowedLength) {
        if (inputStr == null) {
            return null;
        }

        if (maxAllowedLength < 1 || inputStr.length() <= maxAllowedLength) {
            return EDIUtil.stringToEdi(inputStr);
        }

        String ediForm = EDIUtil.stringToEdi(inputStr.substring(0, maxAllowedLength));
        return ediForm;
    }

    /**
     * Takes an EDI String, removes all EDI escape characters
     *
     * @param inputStr the input str
     * @return string string
     */
    public static String ediToString(String inputStr) {
        inputStr = inputStr.replaceAll("(?<!\\?):", ""); //Remove separator character : not escaped by escape character ?
        inputStr = inputStr.replaceAll("\\?:", ":");
        inputStr = inputStr.replaceAll("\\?'", "'");
        inputStr = inputStr.replaceAll("\\?\\+", "+");
        inputStr = inputStr.replaceAll("\\?\\?", "?");
        return inputStr;
    }

    /**
     * Takes a string literal and converts it to an EDI valid string. This involves:
     * <ul>
     * <li>Adding the EDI Free Text prefix.
     * <li>Inserting escape characters.
     * <li>Inserting colons every maxLength number of characters.
     * <li>Adding the EDI end of line character to the end of the input string.
     * </ul>
     *
     * @param str           The string to convert
     * @param segmentLength the segment length
     * @param maxLength     The maximum permissible length of the String
     * @return The String in EDI format
     */
    public static String stringToEDIFreeText(String str, int segmentLength, int maxLength) {
        if (str.length() > maxLength) {
            throw new IllegalArgumentException("Error in segment " + EDI_PREFIX.STRING + " - text string exceeds max length of " + maxLength);
        }
        return stringToEDI(str, segmentLength);
    }

    /**
     * Takes a string literal and converts it to an EDI valid list of Strings. This involves:
     * <ul>
     * <li>Adding the EDI Free Text prefix to each returned String element.
     * <li>Inserting escape characters.
     * <li>Inserting colons at the end of each line
     * <li>Adding the EDI end of line character to the end of each returned String element.
     * </ul>
     *
     * @param str           The string to convert
     * @param segmentLength The maximum length before a segment is inserted into the String
     * @param lineLength    The maximum permissible length of a single line
     * @param maxRows       The maximum number of rows allowed
     * @return The String in EDI format
     */
    public static List<String> stringToEDIFreeText(String str, int segmentLength, int lineLength, int maxRows) {
        if (str.length() > lineLength * maxRows) {
            throw new IllegalArgumentException("Error in segment " + EDI_PREFIX.STRING + " - text string exceeds max length of " + lineLength);
        }

        List<String> returnedStrings = new ArrayList<String>();

        int strLen = str.length();
        for (int rows = 0; rows < strLen; rows += lineLength) {
            // Each row stars with the PREFIX
            StringBuilder sb = new StringBuilder();
            sb.append(EDI_PREFIX.STRING);

            String stringPart = (str.substring(rows, Math.min(strLen, rows + lineLength)));
            returnedStrings.add(stringToEDI(stringPart, segmentLength));
        }

        if (returnedStrings.size() > maxRows) {
            throw new IllegalArgumentException("Error in segment " + EDI_PREFIX.STRING + " - text string exceeds the maximum permitted rows: " + maxRows + " with " + returnedStrings.size());
        }

        return returnedStrings;
    }

    /**
     * Converts the supplied String into EDI format with colons at the partition size.
     *
     * @param str           The string to convert
     * @param partitionSize The number of characters after which a colon will be inserted into the string.
     * @return The string in EDI format.
     */
    private static String stringToEDI(String str, int partitionSize) {
        StringBuilder sb = new StringBuilder();
        sb.append(EDI_PREFIX.STRING);

        int strLen = str.length();
        for (int i = 0; i < strLen; i += partitionSize) {
            String stringPart = (str.substring(i, Math.min(strLen, i + partitionSize)));

            // If this is NOT the first time round the loop and there is content in the string part, then
            // add a colon separator
            if (i != 0 && !stringPart.trim().equals("")) {
                sb.append(":");
            }
            sb.append(stringToEdi(stringPart));
        }

        sb.append(EDI_CONSTANTS.END_TAG);
        return sb.toString();
    }

    /**
     * Ensures that the supplied id does not contain invalid characters.
     * Allowed characters are A-Z 0-9 and _ and the length of the id must not be beyond 18 characters.
     *
     * @param id The id to check
     * @throws IllegalArgumentException if the specified id is deemed to be invalid
     */
    public static void parseId(String id) {
        if (id.length() > 18) {
            throw new IllegalArgumentException("Illegal id, structure identifiers can not be more then 18 characters in EDI");
        }
    }

    /**
     * Split on plus string [ ].
     *
     * @param str the str
     * @return the string [ ]
     */
    public static String[] splitOnPlus(String str) {
        return splitOnChar(str, "\\+", -1);
    }

    /**
     * Split on plus string [ ].
     *
     * @param str                  the str
     * @param expectedNumbersplits the expected numbersplits
     * @return the string [ ]
     */
    public static String[] splitOnPlus(String str, int expectedNumbersplits) {
        return splitOnChar(str, "\\+", expectedNumbersplits);
    }

    /**
     * Split on colon string [ ].
     *
     * @param str the str
     * @return the string [ ]
     */
    public static String[] splitOnColon(String str) {
        return splitOnChar(str, ":", -1);
    }

    /**
     * Split on colon string [ ].
     *
     * @param str                  the str
     * @param expectedNumbersplits the expected numbersplits
     * @return the string [ ]
     */
    public static String[] splitOnColon(String str, int expectedNumbersplits) {
        return splitOnChar(str, ":", expectedNumbersplits);
    }

    private static String[] splitOnChar(String str, String character, int expectedNumbersplits) {
        String[] splitOnPlus = str.split("(?<!\\?)" + character);
        if (expectedNumbersplits > 0) {
            if (splitOnPlus.length != expectedNumbersplits) {
                throw new IllegalArgumentException("Unexpected number of '" + character + "' characters, expecting " + expectedNumbersplits + " actual '" + splitOnPlus.length + "'");
            }
        }
        return splitOnPlus;
    }

    /**
     * Checks the prefix is as expected
     * <p>
     * If the prefix is not as expected an exception will be thrown
     *
     * @param dataReader  the data reader
     * @param prefix      the prefix
     * @param errorOnFail the error on fail
     * @return the boolean
     */
    public static boolean assertPrefix(EDIReader dataReader, EDI_PREFIX prefix, boolean errorOnFail) {
        if (dataReader.getLineType() != prefix) {
            if (errorOnFail) {
                throw new IllegalArgumentException("Expecting prefix : '" + prefix.getPrefix() + "' but got '" + dataReader.getLineType() + "'");
            }
            return false;
        }
        return true;
    }

    /**
     * Given a string, truncates it by rounding the value.
     *
     * @param aStr the a str
     * @return The rounded value in String form.
     */
    public static String truncate(String aStr) {
        // If the input is less than 15 characters we can almost early out, BUT check for the exponent plus issue!!!
        if (aStr.length() < 15) {
            if (aStr.contains("E+")) {
                aStr = aStr.replace("E+", "E");
            }
            return aStr;
        }

        String retStr = convertStringToExponentForm(aStr);
        // EDI exponent values must not be represented by E+ but just E.
        // For example 1.2E5 rather than 1.2E+5
        if (retStr.contains("E+")) {
            retStr = retStr.replace("E+", "E");
        }

        // Cater for the case of .0000 (or indeed any number of zeroes after a decimal place).
        // NOTE cannot just look for trailing zeroes as a number such as:
        //   7.6E-20 is legit and the trailing zero is VERY important
        // So hunt for .0000
        retStr = retStr.replaceAll("\\.0*$", "");
        if (retStr.endsWith(".")) {
            retStr = retStr.substring(0, retStr.length() - 1);
        }
        // Beware the case of the string having been totally obliterated by the removal of zeroes
        if (retStr.equals("")) {
            return "0";
        }
        return retStr;
    }

    private static String convertStringToExponentForm(String aStr) {
        double aDouble = Double.valueOf(aStr);

        // Early out in the corner-case of lots of zeroes
        if (aDouble == 0) {
            return "0";
        }

        double intPart = (long) aDouble;

        // If we start with a zero, format it like so
        if (intPart == 0 || aDouble < 0.00001) {
            NumberFormat formatter;
            if (aDouble > 0) {
                if (!aStr.contains("E")) {
                    // Original string does NOT contain an exponent, but need to check the double of the string since this may have one.
                    aStr = Double.toString(aDouble);
                    // Does this new string contain an exponent?
                    if (!aStr.contains("E")) {
                        formatter = TEN_HASH_FORMATTER;
                    } else {
                        int ePos = aStr.indexOf("E");
                        if (aStr.length() - ePos == 4) {
                            formatter = NINE_HASH_FORMATTER;
                        } else {
                            formatter = TEN_HASH_FORMATTER;
                        }
                    }
                } else {
                    // There is an Exponent
                    // Convert the original String to the Double form. This is because the double form may have changed
                    // the exponent length. e.g. from E9 to E10
                    aStr = Double.toString(aDouble);
                    int ePos = aStr.indexOf("E");
                    if (aStr.length() - ePos == 4) {
                        formatter = NINE_HASH_FORMATTER;
                    } else {
                        formatter = TEN_HASH_FORMATTER;
                    }
                }
            } else {
                // We lose one digit for the leading negative symbol
                // But if the E is a negative number we need to drop another digit
                aStr = Double.toString(aDouble);
                int ePos = aStr.indexOf("E");
                if (aStr.contains("E-")) {
                    // It's a positive E
                    if (aStr.length() - ePos == 3) {
                        formatter = NINE_HASH_FORMATTER;
                    } else {
                        formatter = EIGHT_HASH_FORMATTER;
                    }
                } else {
                    // It's a negative E so does it have 1 or 2 values after the E
                    if (aStr.length() - ePos == 4) {
                        formatter = EIGHT_HASH_FORMATTER;
                    } else {
                        formatter = NINE_HASH_FORMATTER;
                    }
                }
            }
            String convertedValue = formatter.format(aDouble);
            return convertedValue;
        }

        int intPartLength;
        if (intPart > 0) {
            String numberAsString = aStr;
            int dotIndex = numberAsString.indexOf(".");
            if (dotIndex != -1) {
                intPartLength = dotIndex;
            } else {
                intPartLength = numberAsString.length();
            }
            // If the part before the dot is longer than 15 characters, we just round to the exponent
            if (intPartLength > 15) {
                NumberFormat formatter = new DecimalFormat("#.##########E00");  // 10 hashes, 2 char exponent
                return formatter.format(aDouble);
            }
        } else {
            intPartLength = 0;
        }

        // Either round to 15 characters or 14 characters (if negative)
        BigDecimal bd = new BigDecimal(aDouble);
        if (aDouble > 0) {
            bd = bd.setScale(14 - intPartLength, RoundingMode.HALF_UP);
        } else {
            // It's a negative
            bd = bd.setScale(13 - intPartLength, RoundingMode.HALF_UP);
        }

        // If the number is still bigger than 15 characters
        if (bd.toString().length() > 15) {
            NumberFormat formatter;
            if (aDouble > 0) {
                formatter = new DecimalFormat("#.##########E00");  // 10 Hashes, 2 char exponent
            } else {
                formatter = new DecimalFormat("#.#########E00");   // 9 Hashes, 2 char exponent
            }
            return formatter.format(aDouble);
        }

        return bd.toString();
    }


    /**
     * Gets sibling group id.
     *
     * @return the sibling group id
     */
    public static String getSiblingGroupId() {
        return siblingGroupId;
    }

    /**
     * Sets sibling group id.
     *
     * @param siblingGroupId the sibling group id
     */
    public static void setSiblingGroupId(String siblingGroupId) {
        EDIUtil.siblingGroupId = siblingGroupId;
    }

    /**
     * Is truncate long values boolean.
     *
     * @return the boolean
     */
    public static boolean isTruncateLongValues() {
        return truncateLongValues;
    }

    /**
     * Setting this to true, requests that the EDI Data Writer truncate and round any observation value
     * longer than 15 characters.
     *
     * @param newValue the new value
     */
    public static void setTruncateLongValues(boolean newValue) {
        EDIUtil.truncateLongValues = newValue;
    }
}