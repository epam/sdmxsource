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
package org.sdmxsource.util.io;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * The type File util.
 */
public class FileUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);
    private static final String TMP_FILE_DIR = System.getProperty("java.io.tmpdir");

    /**
     * Create temporary file file.
     *
     * @param prefix the prefix
     * @param suffix the suffix
     * @return the file
     */
    public static File createTemporaryFile(String prefix, String suffix) {
        File tmpDir = new File(TMP_FILE_DIR);
        if (!tmpDir.exists()) {
            if (!tmpDir.mkdirs()) {
                throw new RuntimeException("Unable to create directory to store temporary files : " + tmpDir.getAbsolutePath());
            }
        }
        try {
            return File.createTempFile(prefix, suffix);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create teporary file in directory : " + tmpDir.getAbsolutePath(), e);
        }
    }

    /**
     * Read file as stream input stream.
     *
     * @param filePath the file path
     * @return the input stream
     */
    public static InputStream readFileAsStream(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                throw new IllegalArgumentException("File not found : " + filePath);
            }
            FileInputStream stream = new FileInputStream(f);

            return new BufferedInputStream(stream);
        } catch (IOException e) {
            //TODO Exception - wrap
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Gets output stream.
     *
     * @param filePath the file path
     * @return the output stream
     */
    public static OutputStream getOutputStream(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                createFile(f);
            }
            FileOutputStream stream = new FileOutputStream(f);
            return new BufferedOutputStream(stream);
        } catch (IOException e) {
            //TODO Exception - wrap
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Read file as string string.
     *
     * @param filePath the file path
     * @return the string
     */
    public static String readFileAsString(String filePath) {
        File f = new File(filePath);
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(f);

            br = new BufferedReader(fr);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintWriter pw = new PrintWriter(out);
            String line;
            while ((line = br.readLine()) != null) {
                pw.println(line);
            }
            pw.close();
            return new String(out.toByteArray());
        } catch (IOException e) {
            //TODO Exception - wrap
            throw new IllegalArgumentException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // Unable to close the BufferedReader
                }
            }
        }
    }

    /**
     * Exists boolean.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public static boolean exists(String filePath) {
        File f = new File(filePath);
        return f.exists();
    }

    /**
     * Delete file boolean.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public static boolean deleteFile(String filePath) {
        File f = new File(filePath);
        return f.delete();
    }

    /**
     * Creates the directory (or group of directories) if it does not already exist, if the supplied path is a path to a known file location, then an exception wil be thrown
     *
     * @param dir the dir
     */
    public static void createDirectory(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        } else if (!f.isDirectory()) {
            throw new IllegalArgumentException("Directory '" + dir + "' can not be created, it already exists as a file");
        }
    }

    /**
     * Creates a file at location given by the string, will create any directories required, and will create the file, but only if
     * it does not already exist
     *
     * @param fileStr the file str
     * @return file file
     */
    public static File createFile(String fileStr) {
        File f = new File(fileStr);
        createFile(f);
        return f;
    }

    /**
     * Creates the specified file if it doesn't exists and creates all of the required sub-directories if they don't exist.
     *
     * @param aFile The file to create.
     * @throws IllegalStateException if the file or parent directories could not be created.
     */
    public static void createFile(File aFile) {
        if (aFile.exists()) {
            // Since the file already exists, there is nothing further to do.
            return;
        }

        File parentDir = aFile.getParentFile();
        if (!parentDir.exists()) {
            boolean createdParentDirectories = parentDir.mkdirs();
            if (!createdParentDirectories) {
                throw new IllegalStateException("Unable to create directory structure: " + parentDir.getAbsolutePath());
            }
        }
        try {
            aFile.createNewFile();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create file: " + aFile.getAbsolutePath());
        }
    }

    /**
     * Delete directory boolean.
     *
     * @param path the path
     * @return the boolean
     */
    static public boolean deleteDirectory(String path) {
        return deleteDirectory(new File(path));
    }

    /**
     * Delete directory boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    /**
     * Count files int.
     *
     * @param directory the directory
     * @return the int
     */
    public static int countFiles(String directory) {
        File f = new File(directory);
        if (f.isDirectory()) {
            String[] list = f.list();
            if (list == null) {
                return 0;
            }
            return list.length;
        }
        throw new IllegalArgumentException(directory + " is not a directory");
    }

    /**
     * Returns a list of strings - representing all the file names in the given directory
     *
     * @param directory the directory
     * @return string [ ]
     */
    public static String[] getFileNames(String directory) {
        File f = new File(directory);
        if (f.isDirectory()) {
            return f.list();
        }
        throw new IllegalArgumentException(directory + " is not a directory");
    }

    /**
     * Returns a list of files for the specified directory
     *
     * @param directory A directory
     * @return An array of File objects.
     */
    public static File[] getFiles(String directory) {
        File f = new File(directory);
        if (f.isDirectory()) {
            return f.listFiles();
        }
        throw new IllegalArgumentException(directory + " is not a directory");
    }

    /**
     * Deletes all the files in a directory older then a given date
     *
     * @param directory            the directory
     * @param deleteFilesOlderThen the delete files older then
     */
    public static void deleteFilesOlderThen(String directory, Date deleteFilesOlderThen) {
        File f = new File(directory);
        if (f.isDirectory()) {
            File files[] = f.listFiles();
            for (File currentFile : files) {
                long lastModified = currentFile.lastModified();
                if (lastModified < deleteFilesOlderThen.getTime()) {
                    currentFile.delete();
                }
            }
            return;
        }
        //TODO Exception
        throw new IllegalArgumentException(directory + " is not a directory");
    }

    /**
     * Delete oldest file.
     *
     * @param directory the directory
     */
    public static void deleteOldestFile(String directory) {
        long oldestFileTime = Long.MAX_VALUE;
        File oldestFile = null;
        File f = new File(directory);
        if (f.isDirectory()) {
            File files[] = f.listFiles();
            for (File currentFile : files) {
                long lastModified = currentFile.lastModified();
                if (lastModified < oldestFileTime) {
                    oldestFileTime = lastModified;
                    oldestFile = currentFile;
                }
            }
            if (oldestFile != null) {
                oldestFile.delete();
            }
            return;
        }
        //TODO Exception
        throw new IllegalArgumentException(directory + " is not a directory");
    }

    /**
     * Returns the most recent file from a directory
     *
     * @param directory the directory
     * @return the newest file
     */
    public static File getNewestFile(String directory) {
        long newestFileTime = Long.MIN_VALUE;
        File newestFile = null;
        File f = new File(directory);
        if (f.isDirectory()) {
            File files[] = f.listFiles();
            for (File currentFile : files) {
                long lastModified = currentFile.lastModified();
                if (lastModified > newestFileTime) {
                    newestFileTime = lastModified;
                    newestFile = currentFile;
                }
            }
            return newestFile;
        }
        //TODO Exception
        throw new IllegalArgumentException(directory + " is not a directory");
    }

    private static InputStream obtainInputStream(File aFile) {
        // To prevent this method from waiting for ever (if the file is locked by another process)
        // create a time-out value. This is currently set to 3 minutes.
        long proposedAbortTime = System.currentTimeMillis() + (180 * 1000);

        while (true) {
            try {
                return new FileInputStream(aFile);
            } catch (FileNotFoundException e) {
                // On Windows OS attempting to access a file being written to throws this exception.
                // Pause and retry.
                // On Linux, the OS allows access to a file being written to.

                // Note, it is possible that the file has been removed from the file system, so
                // perform a check.
                if (!aFile.exists()) {
                    return null;
                }

                LOG.warn("Unable to access the file - it appears to be locked!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    // Do nothing
                }

                if (System.currentTimeMillis() > proposedAbortTime) {
                    System.out.println("Due to timeout, aborting attempting to get a lock on file: " + aFile);
                    return null;
                }
            }
        }
    }

    private static long readFile(InputStream is) {
        int size = 0;
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] ba = new byte[1024];

        try {
            while (bis.read(ba) != -1) {
                size += ba.length;
            }
        } catch (IOException e) {
            // TODO - Need to understand what should be done here.
            System.err.println("IO exception when trying to read input Stream: " + e);
            e.printStackTrace();
        }
        try {
            bis.close();
        } catch (IOException e) {
            // TODO - Need to understand what should be done here.
            System.err.println("IO exception when trying to close BufferedInputStream: " + e);
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            // TODO - Need to understand what should be done here.
            System.err.println("IO exception when trying to close InputStream: " + e);
            e.printStackTrace();
        }

        return size;
    }

    /**
     * Pause the current thread until the specified file is available for writing to.
     *
     * @param aFile The file
     */
    public static void waitTillAvailableForWriting(File aFile) {
        if (!aFile.exists()) {
            // Since the file doesn't exist there is nothing to do
            return;
        }

        InputStream is = obtainInputStream(aFile);
        if (is == null) {
            return;
        }
        long priorSize = readFile(is);

        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
                // Do nothing
            }

            is = obtainInputStream(aFile);
            if (is == null) {
                return;
            }
            long currentSize = readFile(is);

            if (currentSize == priorSize) {
                // The file size hasn't changed. Assume no-one else is writing to it.
                break;
            }

            priorSize = currentSize;
        }
    }

    /**
     * Given a specified directory name, returns a File object representing that directory if the
     * specified name is valid. Validity is determined by if the directory already exists, it being a directory
     * and it being readable. If the specified directory does not exist, then it is created.
     *
     * @param directoryName The fully-qualified name of the directory
     * @return A file object representing the directory
     */
    public static File ensureDirectoryExists(String directoryName) {
        File f = new File(directoryName);

        // If the directory exists, not need to create it, but need to check validity and permissions.
        if (f.exists()) {
            if (!f.isDirectory()) {
                // The directory exists but the specified location is a file
                throw new RuntimeException(
                        "The specified directory is a file not a directory! Specified value: '" + directoryName + "'");
            }

            if (!f.canWrite()) {
                // The directory exists but cannot be written to
                throw new RuntimeException(
                        "The specified directory does not have read permission! Specified value: '" + directoryName + "'");
            }
        } else {
            // Create the directory.
            boolean successfulCreate = f.mkdirs();
            if (!successfulCreate) {
                throw new RuntimeException("Unable to create directory: " + directoryName);
            }
        }
        return f;
    }

    /**
     * Returns the size in bytes of the specified file.
     *
     * @param aFile The file to evaluate.
     * @return The size in bytes.
     */
    public static long getFileSize(File aFile) {
        return aFile.length();
    }

    /**
     * Returns the size in bytes of the specified URI.
     *
     * @param aURI The URI to evaluate.
     * @return The size in bytes.
     */
    public static long getFileSize(URI aURI) {
        return getFileSize(URIUtil.getFile(aURI));
    }

    /**
     * Copies all of the files in the specified source directory into the specified target directory.
     *
     * @param srcDir    The source directory
     * @param targetDir The target directory
     * @throws IOException If anything goes wrong
     */
    public static void copyAllFilesInDir(File srcDir, File targetDir) throws IOException {
        if (!srcDir.exists()) {
            // Since the source directory doesn't exist there is nothing to do
            return;
        }

        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File aFile = files[i];
            File target = new File(targetDir, aFile.getName());
            if (aFile.isDirectory()) {
                target.mkdirs();
                copyAllFilesInDir(aFile, target);
            } else {
                copyFile(aFile, target);
            }
        }
    }

    /**
     * Copy file.
     *
     * @param sourceFile the source file
     * @param destFile   the dest file
     * @throws IOException the io exception
     */
    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            // Since the source file doesn't exist there is nothing to do
            return;
        }

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    /**
     * Gets all lines.
     *
     * @param f       the f
     * @param charset the charset
     * @return the all lines
     * @throws FileNotFoundException the file not found exception
     */
    public static List<String> getAllLines(File f, Charset charset) throws FileNotFoundException {
        List<String> lines = new ArrayList<String>();
        Scanner sc = null;
        try {
            sc = new Scanner(f, charset.name());
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return lines;
    }
}
