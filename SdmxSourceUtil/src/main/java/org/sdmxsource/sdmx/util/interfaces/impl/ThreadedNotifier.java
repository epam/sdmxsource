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
package org.sdmxsource.sdmx.util.interfaces.impl;

import org.sdmxsource.sdmx.api.listener.Listener;

import java.util.List;


/**
 * The type Threaded notifier.
 */
public class ThreadedNotifier implements Runnable {
    private Thread aThread;
    private Listener listener;
    private Object notification;


    /**
     * Instantiates a new Threaded notifier.
     *
     * @param listener     the listener
     * @param notification the notification
     */
    public ThreadedNotifier(Listener listener, Object notification) {
        this.listener = listener;
        this.notification = notification;
        aThread = new Thread(this);
        aThread.start();
    }

    /**
     * Perform notifications.
     *
     * @param listeners    the listeners
     * @param notification the notification
     */
    public static void performNotifications(List<Listener<? extends Object>> listeners, Object notification) {
        for (Listener currentListener : listeners) {
            performNotification(currentListener, notification);
        }
    }

    /**
     * Perform notification.
     *
     * @param listener     the listener
     * @param notification the notification
     */
    public static void performNotification(Listener listener, Object notification) {
        ThreadedNotifier notifier = new ThreadedNotifier(listener, notification);
    }

    @Override
    public void run() {
        if (notification != null && notification.getClass().equals(Integer.class)) {
            //HACK - WHy?
            notification = new Float((Integer) notification);
        }
        listener.invoke(notification);
    }
}
