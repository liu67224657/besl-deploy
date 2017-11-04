/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.ExecType;
import com.fivewh.deploy.OperationEnv;
import com.fivewh.deploy.Operator;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import java.util.Date;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class AccessCheckAction extends ActionSupport {
    private Operator operator;

    private static final String SESSION_KEY = "com.fivewh.deploy.html.action.operator";
    protected static final String LOGIN_REDIRECT = "deploy-login";
    protected static final String NO_ACCESS_REDIRECT = "deploy-no-access";

    protected boolean isLogin() {
        getOperatorFromSession();

        return operator != null;
    }

    protected boolean hasAccess(OperationEnv env, ExecType exec) {
        if (!isLogin()) {
            return false;
        }

        if (!operator.getEnvs().containsKey(env.getCode())) {
            return false;
        }

        return operator.getType().hasAccess(exec);
    }

    protected boolean isOwner(OperationEnv env) {
        if (!isLogin()) {
            return false;
        }

        if (!operator.getEnvs().containsKey(env.getCode())) {
            return false;
        }

        return true;
    }

    public Operator getOperator() {
        if (operator == null) {
            getOperatorFromSession();
        }

        return operator;
    }


    protected String getOperatorIP() {
        return ServletActionContext.getRequest().getRemoteAddr();
    }

    protected void getOperatorFromSession() {
        Object sessionObj = ServletActionContext.getContext().getSession().get(SESSION_KEY);

        if (sessionObj != null) {
            operator = (Operator) sessionObj;
        } else {
            operator = null;
        }
    }

    protected void setOperatorToSession(Operator operator) {
        ServletActionContext.getContext().getSession().put(SESSION_KEY, operator);
    }

    protected void removeOperatorFromSession() {
        ServletActionContext.getContext().getSession().remove(SESSION_KEY);
    }

    protected void writeLog(String msg, Exception e) {
        System.out.println("[" + new Date() + "] " + msg);
        e.printStackTrace();
    }

    protected void writeLog(String msg) {
        System.out.println("[" + new Date() + "] " + msg);
    }
}
