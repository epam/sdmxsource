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
package org.sdmxsource.util.reflect;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Reflect util.
 *
 * @param <T> the type parameter
 */
public class ReflectUtil<T> {

    /**
     * Is getter boolean.
     *
     * @param method the method
     * @return the boolean
     */
    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) return false;
        if (method.getParameterTypes().length != 0) return false;
        if (void.class.equals(method.getReturnType())) return false;
        return true;
    }

    /**
     * Gets composite objects.
     *
     * @param referencedClass the referenced class
     * @param o               the o
     * @param ignoreMethods   the ignore methods
     * @return the composite objects
     */
    public Set<T> getCompositeObjects(Class referencedClass, Object o, Method... ignoreMethods) {
        Set<T> returnSet = new HashSet<T>();
        try {
            for (Method currentMethod : o.getClass().getMethods()) {
                Class returnClass = currentMethod.getReturnType();
                if (!contains(currentMethod, ignoreMethods) && isGetter(currentMethod)) {
                    if (Collection.class.isAssignableFrom(returnClass)) {
                        Type returnType = currentMethod.getGenericReturnType();
                        if (returnType instanceof ParameterizedType) {
                            ParameterizedType type = (ParameterizedType) returnType;
                            Type[] typeArguments = type.getActualTypeArguments();
                            for (Type typeArgument : typeArguments) {
                                if (typeArgument instanceof Class) {
                                    Class typeArgClass = (Class) typeArgument;
                                    if (referencedClass.isAssignableFrom(typeArgClass)) {
                                        Collection<T> colection = (Collection<T>) currentMethod.invoke(o);
                                        if (colection != null) {
                                            returnSet.addAll(colection);
                                        }
                                        break;
                                    }
                                } else if (typeArgument instanceof TypeVariable) {
                                    TypeVariable typeVariable = (TypeVariable) typeArgument;
                                    if (typeVariable.getGenericDeclaration() instanceof Class) {
                                        Class typeArgClass = (Class) typeVariable.getGenericDeclaration();
                                        if (referencedClass.isAssignableFrom(typeArgClass)) {
                                            Collection<T> colection = (Collection<T>) currentMethod.invoke(o);
                                            if (colection != null) {
                                                returnSet.addAll(colection);
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (referencedClass.isAssignableFrom(returnClass)) {
                            T claz = (T) currentMethod.invoke(o);
                            if (claz != null) {
                                returnSet.add(claz);
                            }
                        }
                    }
                }
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return returnSet;
    }

    private boolean contains(Method mthd, Method[] mds) {
        if (mds == null) {
            return false;
        }
        for (Method currentMethod : mds) {
            if (currentMethod.getName().equals(mthd.getName())) {
                return true;
            }
        }
        return false;
    }
}
