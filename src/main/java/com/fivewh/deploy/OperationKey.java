/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class OperationKey {
    private OperationEnv env;
    private String operName;
    private String args;

    public static final String SPLIT_KEY = ".";

    //the env and oper can't been null;
    public OperationKey(OperationEnv env, String oper, String args) {
        this.env = env;
        this.operName = oper;

        this.args = args;
    }

    public OperationEnv getEnv() {
        return env;
    }

    public void setEnv(OperationEnv env) {
        this.env = env;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getOperationKeyValue() {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(env.getCode()).append(SPLIT_KEY).append(operName);

        if (args != null) {
            strBuf.append(SPLIT_KEY).append(args);
        }

        return strBuf.toString();
    }

    public String toString() {
        return "env.operName.args=" + getOperationKeyValue();
    }

    public int hashCode() {
        return (env.getCode() + operName + args).hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OperationKey)) {
            return false;
        }

        OperationKey key = (OperationKey) obj;
        return this.getOperationKeyValue().equals(key.getOperationKeyValue());
    }
}
