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
package org.sdmxsource.sdmx.api.exception;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_ERROR_CODE;
import org.sdmxsource.sdmx.api.util.MessageResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Abstract class to be sub-classed by all custom Exceptions in the system.
 * <p>
 * If the exceptions are multi-lingual, this class provides the means
 * for retrieving exception messages in different languages.
 * <p>
 * The Exceptions that sub-class this are all Checked Exceptions
 */
public class SdmxException extends RuntimeException {
    private static final long serialVersionUID = 7230038232116051945L;
    private static Logger LOG = LoggerFactory.getLogger(SdmxException.class);
    private static MessageResolver messageResolver;
    private static LocalObjectStore localObjectStore = new LocalObjectStore();
    private ExceptionCode code;
    private String codeStr;
    private Object[] args;
    private Throwable th;
    private SDMX_ERROR_CODE errorCode = SDMX_ERROR_CODE.INTERNAL_SERVER_ERROR;

    /**
     * Creates Exception from an error String and an Error code
     *
     * @param errorMessage the error message
     */
    public SdmxException(String errorMessage) {
        this(null, null, errorMessage);
    }

    /**
     * Creates Exception from an error String and an Error code
     *
     * @param code the code
     */
    public SdmxException(ExceptionCode code) {
        this(SDMX_ERROR_CODE.INTERNAL_SERVER_ERROR, code);
    }

    /**
     * Creates Exception from an error String and an Error code
     *
     * @param errorMessage the error message
     * @param errorCode    required
     */
    public SdmxException(String errorMessage, SDMX_ERROR_CODE errorCode) {
        this(null, errorCode, errorMessage);
    }

    /**
     * Creates an exception from a Throwable, if the Throwable is a SdmxException - then the
     * error code wil be used, if it is not, then InternalServerError will be used
     *
     * @param th  the th
     * @param str the str
     */
    public SdmxException(Throwable th, String str) {
        this(th, null, str);
    }

    /**
     * Creates Exception from an error String and an Error code
     *
     * @param th        the th
     * @param errorCode required
     * @param message   the message
     */
    public SdmxException(Throwable th, SDMX_ERROR_CODE errorCode, String message) {
        super(message, th);
        this.th = th;
        if (errorCode != null) {
            this.errorCode = errorCode;
        }
        if (th != null) {
            //LOG.error(this.getMessage(), th);
            if (errorCode == null) {
                if (th instanceof SdmxException) {
                    this.errorCode = ((SdmxException) th).getSdmxErrorCode();
                }
            }
        } else {
            //LOG.error(this);
        }
        displayExceptionTrace();
//		LOG.error(this.getMessage(), this);
    }

    /**
     * Instantiates a new Sdmx exception.
     *
     * @param th   the th
     * @param code the code
     * @param args the args
     */
    public SdmxException(Throwable th, ExceptionCode code, Object... args) {
        this(th, null, code, args);
    }

    /**
     * Creates Exception from an error String and an Error code
     *
     * @param errorCode required
     * @param code      the code
     * @param args      the args
     */
    public SdmxException(SDMX_ERROR_CODE errorCode, ExceptionCode code, Object... args) {
        this(null, errorCode, code, args);
    }

    /**
     * Creates Exception from an error String and an Error code
     *
     * @param th        the th
     * @param errorCode required
     * @param code      the code
     * @param args      the args
     */
    public SdmxException(Throwable th, SDMX_ERROR_CODE errorCode, ExceptionCode code, Object... args) {
        super(th);
        this.th = th;
        this.errorCode = errorCode;
        this.args = args;
        if (th != null) {
            //th.printStackTrace();
            if (errorCode == null) {
                if (th instanceof SdmxException) {
                    this.errorCode = ((SdmxException) th).getSdmxErrorCode();
                }
            }
        } else {
            //printStackTrace();
        }
        if (code != null) {
            this.code = code;
            this.codeStr = code.getCode();
        }
        displayExceptionTrace();
//		LOG.error(this.getMessage(), this);
    }

    /**
     * Disable exception trace.
     *
     * @param bool the bool
     */
    public static void disableExceptionTrace(boolean bool) {
        //Disable on local
        if (bool) {
            localObjectStore.get().put("KEY", true);
        } else {
            localObjectStore.get().remove("KEY");
        }
    }

    /**
     * Sets message resolver.
     *
     * @param messageResolver the message resolver
     */
    public static void setMessageResolver(MessageResolver messageResolver) {
        SdmxException.messageResolver = messageResolver;
    }

    private void displayExceptionTrace() {
        if (!localObjectStore.get().containsKey("KEY")) {
            LOG.error(this.getMessage(), this);
        }
    }

    /**
     * Gets sdmx error code.
     *
     * @return the sdmx error code
     */
    public SDMX_ERROR_CODE getSdmxErrorCode() {
        return errorCode;
    }

    /**
     * Gets http rest error code.
     *
     * @return the http rest error code
     */
    public Integer getHttpRestErrorCode() {
        return errorCode.getHttpRestErrorCode();
    }

    @Override
    public String getMessage() {
        if (codeStr == null) {
            return super.getMessage();
        }
        return getMessage(th, codeStr, args);
    }

    /**
     * Gets full message.
     *
     * @return the full message
     */
    public String getFullMessage() {
        if (codeStr == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(super.getMessage());
            if (this.getCause() != null) {
                sb.append("  - caused by " + System.getProperty("line.separator"));
                if (this.getCause() instanceof SdmxException) {
                    sb.append(((SdmxException) this.getCause()).getFullMessage());
                } else {
                    sb.append(this.getCause().getMessage());
                }
            }
            return sb.toString();
        }
        return getFullMessage(th, codeStr, args);
    }

    /**
     * Gets message.
     *
     * @param loc the loc
     * @return the message
     */
    public String getMessage(Locale loc) {
        return getMessage(th, codeStr, args, loc);
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public ExceptionCode getCode() {
        if (code == null && th != null && th instanceof SdmxException) {
            return ((SdmxException) th).getCode();
        }
        return code;
    }


    /**
     * Gets message.
     *
     * @param th   the th
     * @param code the code
     * @param args the args
     * @return the message
     */
    public String getMessage(Throwable th, String code, Object[] args) {
        String nestedMessage = "";
//		if(th != null) {
//			nestedMessage = "Nested Message : " + th.getMessage();
//		}
        if (code == null) {
            return nestedMessage;
        }
//		if(ObjectUtil.validString(nestedMessage)) {
//			return resolveMessage(code, args) + "\n\n" + nestedMessage;
//		}
        return resolveMessage(code, args);
    }

    /**
     * Gets full message.
     *
     * @param th   the th
     * @param code the code
     * @param args the args
     * @return the full message
     */
    public String getFullMessage(Throwable th, String code, Object[] args) {
        String nestedMessage = "";
        if (th != null) {
            if (th instanceof SdmxException) {
                SdmxException ex = (SdmxException) th;
                nestedMessage = "Nested Message : " + ex.getFullMessage();
            } else {
                nestedMessage = "Nested Message : " + th.getMessage();
            }

        }
        if (code == null) {
            return nestedMessage;
        }
        if (nestedMessage != null && nestedMessage.length() > 0) {
            return resolveMessage(code, args) + "\n\n" + nestedMessage;
        }
        return resolveMessage(code, args);
    }

    /**
     * Gets message.
     *
     * @param th   the th
     * @param code the code
     * @param args the args
     * @param loc  the loc
     * @return the message
     */
    public String getMessage(Throwable th, String code, Object[] args, Locale loc) {
        String nestedMessage = "";
        if (th != null) {
            if (th instanceof SdmxException) {
                nestedMessage = ((SdmxException) th).getMessage(loc);
            }
            nestedMessage = th.getMessage();
        }
        if (code == null) {
            return nestedMessage;
        }
        if (nestedMessage != null && nestedMessage.length() > 0) {
            return resolveMessage(code, args, loc) + "\n\n" + nestedMessage;
        }
        return resolveMessage(code, args, loc);
    }

    /**
     * Resolve message string.
     *
     * @param code the code
     * @param args the args
     * @return the string
     */
    public String resolveMessage(String code, Object[] args) {
        if (messageResolver == null) {
            return code;
        }
        StringBuilder sb = new StringBuilder();
        // Try preferred locale first
        Locale myLoc = null;
        String message;

        myLoc = Locale.ENGLISH;

        sb.append(myLoc);
        sb.append("\n\n");
        message = messageResolver.resolveMessage(code, myLoc, args);
        if (message != null && message.length() > 0) {
            return message;
        }

        return "Exception message could not be resolved for code : " + code + " the following locales were checked: " + sb.toString();
    }

    /**
     * Resolve message string.
     *
     * @param code the code
     * @param args the args
     * @param loc  the loc
     * @return the string
     */
    public String resolveMessage(String code, Object[] args, Locale loc) {
        if (messageResolver == null) {
            return code;
        }
        String message = messageResolver.resolveMessage(code, loc, args);
        if (message != null && message.length() > 0) {
            return message;
        }
        return "Exception message could not be resolved for code : " + code + " for the following locale " + loc;
    }

    /**
     * Gets error type.
     *
     * @return the error type
     */
    public String getErrorType() {
        return "Sdmx Exception";
    }

    private static class LocalObjectStore extends ThreadLocal<Map<Object, Object>> {
        @Override
        public Map<Object, Object> initialValue() {
            return new HashMap<Object, Object>();
        }
    }
}
