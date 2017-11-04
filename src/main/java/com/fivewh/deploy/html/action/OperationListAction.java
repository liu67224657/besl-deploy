/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.Operation;
import com.fivewh.deploy.OperationManager;

import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class OperationListAction extends OperationAction {
    private Map<String, Operation> operations;

    public String execute() {
        if (!isLogin()) {
            return LOGIN_REDIRECT;
        }

        if (!isOwner(getOperationEnv())) {
            return NO_ACCESS_REDIRECT;
        }

        generateMap();

        return SUCCESS;
    }

    private void generateMap() {
        operations = OperationManager.get().getOperationsByEnv(super.getOperationEnv());
    }

    public Map<String, Operation> getOperations() {
        return operations;
    }

    public OperationManager getOperationMgr() {
        return OperationManager.get();
    }
}
