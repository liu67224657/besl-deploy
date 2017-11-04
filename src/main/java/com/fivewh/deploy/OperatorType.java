/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class OperatorType {
    private static Map<String, OperatorType> operatorTypeMap = new HashMap<String, OperatorType>();

    //Operator types
    public static final OperatorType REVIEWER = new OperatorType("reviewer");
    public static final OperatorType EXECUTIVE = new OperatorType("executive");

    //
    private static Map<OperatorType, Map<String, ExecType>> operatorAccessesMap
            = new HashMap<OperatorType, Map<String, ExecType>>();

    static {
        operatorAccessesMap.put(REVIEWER, ExecType.getReviewerAccesses());
        operatorAccessesMap.put(EXECUTIVE, ExecType.getExecutiveAccesses());
    }

    private String code;

    private OperatorType(String c) {
        code = c.toLowerCase();

        operatorTypeMap.put(code, this);
    }

    public String getCode() {
        return code;
    }

    public static OperatorType getTypeByCode(final String c) {
        if (c == null) {
            return REVIEWER;
        }

        OperatorType type = operatorTypeMap.get(c.toLowerCase());

        if (type == null) {
            type = REVIEWER;
        }

        return type;
    }

    public boolean hasAccess(ExecType execType) {
        return operatorAccessesMap.get(this).containsKey(execType.getCode());
    }

    public String toString() {
        return "OperatorType: code=" + code;
    }

    public int hashCode() {
        return code.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OperatorType)) {
            return false;
        }

        OperatorType type = (OperatorType) obj;
        return code.equalsIgnoreCase(type.getCode());
    }
}
