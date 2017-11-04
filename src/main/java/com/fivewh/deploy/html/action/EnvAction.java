/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.OperationEnv;
import com.fivewh.deploy.OperationManager;

import java.util.List;

import org.apache.struts2.ServletActionContext;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class EnvAction extends AccessCheckAction {
    private List<OperationEnv> envs;

    public String execute() {
        if (!isLogin()) {
            return LOGIN_REDIRECT;
        }

        envs = OperationManager.get().getOperationEnvs();

        return SUCCESS;
    }

    public List<OperationEnv> getEnvs() {
        return envs;
    }


}
