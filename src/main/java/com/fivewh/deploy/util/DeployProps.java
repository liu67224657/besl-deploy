/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class DeployProps {
    private Properties props = new Properties();

    public DeployProps(String fname) {
        this(fname, false);
    }

    public DeployProps(String fname, boolean loadSystemProps) {
        this(DeployProps.class.getResourceAsStream(fname), loadSystemProps);
    }

    public DeployProps(InputStream inputStream, boolean loadSystemProps) {
        try {
            loadTrimmed(props, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (loadSystemProps) {
            Properties systemProperties = System.getProperties();

            for (Enumeration e = systemProperties.propertyNames(); e.hasMoreElements(); ) {
                String propertyName = (String) e.nextElement();
                String propertyValue = systemProperties.getProperty(propertyName);
                props.setProperty(propertyName, propertyValue);
            }
        }
    }

    private void loadTrimmed(Properties props, InputStream input) {
        Properties temp = new Properties();

        try {
            input = new PropertiesEncodingInputStream(input, "UTF8");
            temp.load(input);

            for (Iterator i = temp.entrySet().iterator(); i.hasNext(); ) {
                Map.Entry entry = (Map.Entry) i.next();
                String name = (String) entry.getKey();
                String value = (String) entry.getValue();

                if (value != null) {
                    value = value.trim();
                    props.setProperty(name, value);
                }
            }
        } catch (IOException e) {
            //do nothing
            e.printStackTrace();
        }
    }

    /**
     * Return the property value.
     *
     * @param propname The name of the property whose value we want.
     */
    public String get(String propname) {
        return props.getProperty(propname);
    }

    /**
     * Retrieve a property value. Note that if the entry is
     * not found, or the property value is null, the default
     * value is returned.
     *
     * @param propname The name of the property whose value we want.
     * @param defValue A default value to use if the property is not
     *                 found.
     */
    public String get(String propname, String defValue) {
        String sval = props.getProperty(propname);
        if (sval == null) {
            return defValue;
        }

        return sval;
    }

    /**
     * A utility method to retrieve a boolean property.
     */
    public boolean getBoolean(String propname, boolean defaultVal) {
        String sval = props.getProperty(propname);
        if (sval == null) {
            return defaultVal;
        }

        return Boolean.valueOf(sval).booleanValue();
    }

    /**
     * Retrieve an integer property value. Note that if the entry is
     * not found, or the property value is not numeric, a 0 is returned.
     */
    public int getInt(String propname) {
        return getInt(propname, 0);
    }

    /**
     * Retrieve an integer property value. Note that if the entry is
     * not found, or the property value is not numeric, the default
     * value is returned.
     *
     * @param propname The name of the property whose value we want.
     * @param defValue A default value to use if the property is not
     *                 found.
     */
    public int getInt(String propname, int defValue) {
        String sval = props.getProperty(propname);
        if (sval == null) {
            return defValue;
        }

        int val = defValue;
        try {
            val = Integer.parseInt(sval);
        } catch (NumberFormatException e) {
        }
        return val;
    }

    /**
     * Retrieve a double property value. Note that if the entry is
     * not found, or the property value does not parse as a double,
     * the default value is returned.
     *
     * @param propname The name of the property whose value we want.
     * @param defValue A default value to use if the property is not
     *                 found.
     */
    public double getDouble(String propname, double defValue) {
        String sval = props.getProperty(propname);
        if (sval == null) {
            return defValue;
        }

        double val = defValue;
        try {
            val = Double.parseDouble(sval);
        } catch (NumberFormatException e) {
        }
        return val;
    }

    /**
     * Retrieve a long property value.If a value is not found the default
     * value is returned.
     *
     * @param propname name of the property whose value we want.
     * @param defValue A default value to use if the property is not found.
     */
    public long getLong(String propname, long defValue) {
        String sval = props.getProperty(propname);
        if (sval == null) {
            return defValue;
        }

        long val = defValue;
        try {
            val = Long.parseLong(sval);
        } catch (NumberFormatException e) {
        }
        return val;
    }

    /**
     * Returns a list of strings. If a property is missing or the value of a property
     * is an empty string, return an empty Collection. If you really want to know
     * whether a property by a given name exists, use FiveProps.get(propertyName)
     */
    public List<String> getList(String propname, String delim) {
        List result = new LinkedList();
        String sval = props.getProperty(propname);
        if (sval != null) {
            StringTokenizer st;
            if (delim == null) {
                st = new StringTokenizer(sval);
            } else {
                st = new StringTokenizer(sval, delim);
            }
            while (st.hasMoreTokens()) {
                result.add(st.nextToken());
            }
        }
        return result;
    }

    /**
     * <p>Get props list delimited by common delims: the space character,
     * the tab character, the newline character, the carriage-return character,
     * and the form-feed character.</p>
     * <p>If a property is missing or the value of a property
     * is an empty string, return an empty Collection. If you really want to know
     * whether a property by a given name exists, use FiveProps.get(propertyName)</p>
     *
     * @param propName
     * @return List
     */
    public List<String> getList(String propName) {
        return getList(propName, null);
    }
}
