/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class Operator implements Serializable {
    private String loginName;
    private String password;
    private Map<String, OperationEnv> envs = new HashMap<String, OperationEnv>();
    private OperatorType type;

    public Operator(String l, String p, OperatorType t) {
        loginName = l;
        password = p;
        type = t;
    }

    public Operator(String l, String p, OperatorType t, Map<String, OperationEnv> e) {
        loginName = l;
        password = p;
        type = t;
        envs = e;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OperatorType getType() {
        return type;
    }

    public void setType(OperatorType type) {
        this.type = type;
    }


    public Map<String, OperationEnv> getEnvs() {
        return envs;
    }

    public void setEnvs(Map<String, OperationEnv> envs) {
        this.envs = envs;
    }

    public void addEnv(String envCode, OperationEnv env) {
        this.envs.put(envCode, env);
    }

    public String toString() {
        return "Operator: loginName=" + loginName + ", password=" + password + ", type=[" + type + "]" + ", envs=[" + envs + "]";
    }

    public int hashCode() {
        return loginName.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Operator)) {
            return false;
        }

        Operator operator = (Operator) obj;
        return loginName.equalsIgnoreCase(operator.getLoginName());
    }
}
