/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.MD5Encoder;
import com.fivewh.deploy.OperationManager;
import com.fivewh.deploy.Operator;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class LoginAction extends AccessCheckAction {
    private String loginName;
    private String password;
    private int loginStatus = 0;
    private String lk;

    private static final String FAILURE = "failure";

    private static MD5Encoder md5Encoder = new MD5Encoder();

    public String execute() {
        if (isLogin()) {
            return SUCCESS;
        }

        if (getLk() == null || "".equalsIgnoreCase(getLk())) {
            return FAILURE;
        }

        login();

        if (loginStatus != 0) {
            return FAILURE;
        } else {
            setOperatorToSession(OperationManager.get().getOperator(getLoginName()));

            this.writeLog("The user " + getLoginName() + " login in.");

//            this.writeLog("======"+OperationManager.get());
        }

        return SUCCESS;
    }

    public void login() {
        if (OperationManager.get().hasAccess(getOperatorIP())) {
            if (OperationManager.get().hasOperator(getLoginName())) {
                Operator operator = OperationManager.get().getOperator(getLoginName());

                if (operator.getPassword() != null && operator.getPassword().equals(md5Encoder.encode(password))) {
                    loginStatus = 0;
                } else {
                    loginStatus = 3;
                }
            } else {
                loginStatus = 2;
            }
        } else {
            loginStatus = 1;
        }
    }

    public String getLk() {
        return lk;
    }

    public void setLk(String lk) {
        this.lk = lk;
    }

    public int getLoginStatus() {
        return loginStatus;
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
}
