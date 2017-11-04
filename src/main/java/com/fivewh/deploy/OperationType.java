/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class OperationType {
    private static Map<String, OperationType> operationTypes = new HashMap<String, OperationType>();

    public static final OperationType PARALLEL = new OperationType("parallel", "Parallel operation");
    public static final OperationType ONEBYONE = new OperationType("onebyone", "One by one operation");

    private String code;
    private String name;

    private OperationType(String c, String n) {
        code = c.toLowerCase();
        name = n;

        operationTypes.put(code, this);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String c) {
        code = c;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public String toString() {
        return "OperationType: code=" + code + ", name=" + name;
    }

    public int hashCode() {
        return code.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OperationType)) {
            return false;
        }

        OperationType type = (OperationType) obj;
        return code.equalsIgnoreCase(type.getCode());
    }

}
