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

import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.util.MessageResolver;

import java.util.Locale;


/**
 * Abstract class to be sub-classed by all custom Exceptions in the system.
 * <p>
 * If the exceptions are multi-lingual, this class provides the means
 * for retrieving exception messages in different languages.
 * <p>
 * The Exceptions that sub-class this are all Checked Exceptions
 */
public abstract class BaseCheckedException extends Exception {
    private static final long serialVersionUID = 1368799655413086605L;
    private static MessageResolver messageResolver;
    private ExceptionCode code;
    private String codeStr;
    private Object[] args;
    private Throwable th;

    public BaseCheckedException(String str) {
        super(str);
        this.printStackTrace();
        System.err.println(getMessage());
    }

    public BaseCheckedException(Throwable th, String str) {
        super(str, th);
        if (th != null) {
            th.printStackTrace();
        }
        super.printStackTrace();
        System.out.println(getMessage());
    }


    public BaseCheckedException(Throwable th, ExceptionCode code, Object[] args) {
        super(th);
        this.th = th;
        if (th != null) {
            th.printStackTrace();
        }
        super.printStackTrace();
        if (code != null) {
            this.code = code;
            this.codeStr = code.getCode();
        }
        this.args = args;
        System.out.println(getMessage());
    }


    @Override
    public String getMessage() {
        if (codeStr == null) {
            return super.getMessage();
        }
        return getMessage(th, codeStr, args);
    }

    public String getFullMessage() {
        if (codeStr == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(super.getMessage());
            if (this.getCause() != null) {
                sb.append("  - caused by " + System.getProperty("line.separator"));
                if (this.getCause() instanceof BaseCheckedException) {
                    sb.append(((BaseCheckedException) this.getCause()).getFullMessage());
                } else {
                    sb.append(this.getCause().getMessage());
                }
            }
            return sb.toString();
        }
        return getFullMessage(th, codeStr, args);
    }

    public String getMessage(Locale loc) {
        return getMessage(th, codeStr, args, loc);
    }

    public ExceptionCode getCode() {
        if (code == null && th != null && th instanceof BaseCheckedException) {
            return ((BaseCheckedException) th).getCode();
        }
        return code;
    }


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

    public String getFullMessage(Throwable th, String code, Object[] args) {
        String nestedMessage = "";
        if (th != null) {
            if (th instanceof BaseCheckedException) {
                BaseCheckedException ex = (BaseCheckedException) th;
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

    public String getMessage(Throwable th, String code, Object[] args, Locale loc) {
        String nestedMessage = "";
        if (th != null) {
            if (th instanceof BaseCheckedException) {
                nestedMessage = ((BaseCheckedException) th).getMessage(loc);
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


    public abstract String getErrorType();

    public void setMessageResolver(MessageResolver messageResolver) {
        BaseCheckedException.messageResolver = messageResolver;
    }
}
