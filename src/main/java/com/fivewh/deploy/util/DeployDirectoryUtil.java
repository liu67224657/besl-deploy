/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class DeployDirectoryUtil {
    private static VersionComparator<String> versionComparator = new VersionComparator<String>();

    public static List<String> getAllSubDirNames(String dir) throws FileNotFoundException {
        if (dir == null || "".equals(dir)) {
            throw new InvalidParameterException("The directory parameter can't be empty.");
        }

        File file = new File(dir);

        if (file != null && file.exists() && file.isDirectory()) {
            List<String> subDirectories = new ArrayList<String>();

            File[] subFiles = file.listFiles();
            File f = null;
            for (int i = 0; i < subFiles.length; i++) {
                f = subFiles[i];

                if (f.isDirectory()) {
                    subDirectories.add(f.getName());
                }
            }

            Collections.sort(subDirectories, versionComparator);

            return subDirectories;
        } else {
            throw new FileNotFoundException("The directory is not exist:" + dir);
        }
    }

    public static List<String> getAllFileNamesInDir(String dir) throws FileNotFoundException {
        if (dir == null || "".equals(dir)) {
            throw new InvalidParameterException("The directory parameter can't be empty.");
        }

        File file = new File(dir);

        if (file != null && file.exists() && file.isDirectory()) {
            List<String> fileNames = new ArrayList<String>();

            File[] subFiles = file.listFiles();
            File f = null;
            String fileName = null;
            int lastDotPos = -1;
            for (int i = 0; i < subFiles.length; i++) {
                f = subFiles[i];

                if (f.isFile()) {
                    fileName = f.getName();
                    lastDotPos = fileName.lastIndexOf(".");

                    if (lastDotPos < 0) {
                        fileNames.add(f.getName());
                    } else {
                        fileNames.add(fileName.substring(0, lastDotPos));
                    }
                }
            }

            Collections.sort(fileNames, versionComparator);

            return fileNames;
        } else {
            throw new FileNotFoundException("The directory is not exist:" + dir);
        }
    }

    static class VersionComparator<String> implements Comparator<String> {
        private Pattern regexPattern;

        public VersionComparator() {
            regexPattern = Pattern.compile("([\\d]+)");
        }

        public int compare(String o, String o1) {
            return getVersionValue(o1) - getVersionValue(o);
        }

        private int getVersionValue(String v) {
            int value = 0;

            //
            Matcher matcher = regexPattern.matcher((CharSequence) v);
            while (matcher.find()) {
                value += Integer.valueOf(matcher.group());

                value *= 100;
            }

            //
            return value;
        }
    }
}
