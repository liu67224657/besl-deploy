/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class ExecType {
    private static Map<String, ExecType> execTypeMap = new HashMap<String, ExecType>();

    //exec types
    public static final ExecType PREVIEW = new ExecType("preview");
    public static final ExecType EXECUTE = new ExecType("execute");
    public static final ExecType REFRESH = new ExecType("refresh");
    public static final ExecType REMOVE = new ExecType("remove");

    private String code;

    private ExecType(String c) {
        code = c.toLowerCase();

        execTypeMap.put(code, this);
    }

    public String getCode() {
        return code;
    }

    public static ExecType getTypeByCode(final String c) {
        if (c == null) {
            return REFRESH;
        }

        ExecType type = execTypeMap.get(c.toLowerCase());

        if (type == null) {
            type = REFRESH;
        }

        return type;
    }

    public static Map<String, ExecType> getExecutiveAccesses() {
        Map<String, ExecType> returnValue = new HashMap<String, ExecType>();

        returnValue.putAll(execTypeMap);

        return returnValue;
    }

    public static Map<String, ExecType> getReviewerAccesses() {
        Map<String, ExecType> returnValue = new HashMap<String, ExecType>();

        returnValue.put(REFRESH.getCode(), REFRESH);

        return returnValue;
    }

    public String toString() {
        return "ExecType: code=" + code;
    }

    public int hashCode() {
        return code.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ExecType)) {
            return false;
        }

        ExecType type = (ExecType) obj;
        return code.equalsIgnoreCase(type.getCode());
    }
}
