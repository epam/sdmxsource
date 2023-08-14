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
package org.sdmxsource.util.thread;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmxsource.util.log.LoggingUtil;


/**
 * The type Lockable.
 */
public class Lockable {
    /**
     * The Log.
     */
    Logger log = LoggerFactory.getLogger(Lockable.class);
    private boolean isLocked = false;
    private String lockOwner;
    private int lockCount = 0;

    /**
     * Lock.
     */
    public synchronized void lock() {
        if (isLocked && lockOwner != null && lockOwner.equals(Thread.currentThread().getName())) {
            lockCount++;
            if (log.isDebugEnabled()) {
                log.debug("Lockable Class " + this.getClass().getName() + " - Lock requested from thread that already owns lock (" + Thread.currentThread().getName() + "), lock count = " + lockCount);
            }
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("Lockable Class " + this.getClass().getName() + " - Aquire new lock : " + Thread.currentThread().getName());
        }

        // If some other thread locked this object then we need to wait
        // until they release the lock
        if (isLocked) {
            do {
                try {
                    // this releases the synchronized that we are in
                    // then waits for a notify to be called in this object
                    // then does a synchronized again before continuing
                    wait();
                } catch (Exception e) {
                    throw new RuntimeException("Error while trying to lock synchronized object");
                }
            } while (isLocked);     // we can't leave until we got the lock, which
            // we may not have got if an exception occurred
        }
        isLocked = true;
        lockCount = 0;
        lockOwner = Thread.currentThread().getName();
    }

    /**
     * Releases the lock owned by the current thread
     *
     * @param fullRelease - if true, this will release all locks the thread has called on this instance, if false, it will unlock the last call to this                    instance, if the last call is the last lock to release then this instance is unlocked.
     */
    public synchronized void releaseLock(boolean fullRelease) {
        if (!isLocked) {
            return;
        }
        if (!Thread.currentThread().getName().equals(lockOwner)) {
            throw new RuntimeException("Lockable Class " + this.getClass().getName() + " releaseLock call from Thread " + Thread.currentThread().getName() + " is attempting to release a lock owned by " + lockOwner);
        }

        LoggingUtil.debug(log, "Release Lock Request:" + Thread.currentThread().getName());
        if (isLocked) {
            if (fullRelease) {
                LoggingUtil.debug(log, "Full Release");
                lockCount = 0;
                isLocked = false;
                notify();
            } else {
                if (lockCount <= 0) {
                    LoggingUtil.debug(log, "Lock Count " + lockCount + " Release Lock");
                    isLocked = false;
                    lockOwner = null;
                    notify();
                }
                LoggingUtil.debug(log, "Lock Count " + lockCount + " Decrease Count Value by 1");
                lockCount--;
            }
        }
    }

    /**
     * Is locked boolean.
     *
     * @return the boolean
     */
    public synchronized boolean isLocked() {
        return isLocked;
    }
}
