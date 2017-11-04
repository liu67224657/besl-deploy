/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy;

import java.io.Serializable;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class OperationEnv implements Serializable{
    private String code;

    public OperationEnv(String c) {
        code = c.toLowerCase();
    }

    public String getCode() {
        return code;
    }


    public String toString() {
        return "OperationEnv: code=" + code;
    }

    public int hashCode() {
        return code.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OperationEnv)) {
            return false;
        }

        OperationEnv env = (OperationEnv) obj;
        return code.equalsIgnoreCase(env.getCode());
    }
}
