/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.*;
import com.fivewh.deploy.command.PatchCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class PatchAction extends OperationAction {
    private String patchnum;


    public String execute() {
        if (!isLogin()) {
            return LOGIN_REDIRECT;
        }

        if (!hasAccess(getOperationEnv(), getExecType())) {
            return NO_ACCESS_REDIRECT;
        }

        operKey = new OperationKey(getOperationEnv(), getOper(), getArgs());
        operation = OperationManager.get().getOperation(operKey);
        formSelectHosts.addAll(this.getEnvAllHosts());

        if (operation == null) {
            if (isPreview()) {
                PatchCommand cmd = new PatchCommand(operKey.getArgs(), getPatchnum());
                operation = new Operation(operKey);
                OperationManager.get().putOperation(operation);

                operation.setCommand(cmd);
                if (PatchCommand.CHECK.equalsIgnoreCase(getArgs())) {
                    operation.setOperType(OperationType.PARALLEL);
                } else {
                    operation.setOperType(OperationType.ONEBYONE);
                }

                Map<String, OperationHost> destHosts = new HashMap<String, OperationHost>();
                Map<String, OperationHost> allReleaseHosts = OperationManager.get().getHostsByEnv(getOperationEnv());
                for (String host : this.getHosts()) {
                    if (allReleaseHosts.containsKey(host)) {
                        destHosts.put(host, allReleaseHosts.get(host));
                    }
                }
                operation.setOperationHosts(destHosts);

                operation.preview();

                this.writeLog("The user " + getOperator().getLoginName() + " previews " + operation);
            }

            if (isExecute()) {
                if (operation == null) {
                    PatchCommand cmd = new PatchCommand(operKey.getArgs(), getPatchnum());
                    operation = new Operation(operKey);
                    OperationManager.get().putOperation(operation);

                    operation.setCommand(cmd);
                    if (PatchCommand.CHECK.equalsIgnoreCase(getArgs())) {
                        operation.setOperType(OperationType.PARALLEL);
                    } else {
                        operation.setOperType(OperationType.ONEBYONE);
                    }

                    Map<String, OperationHost> destHosts = new HashMap<String, OperationHost>();
                    Map<String, OperationHost> allReleaseHosts = OperationManager.get().getHostsByEnv(getOperationEnv());
                    for (String host : this.getHosts()) {
                        if (allReleaseHosts.containsKey(host)) {
                            destHosts.put(host, allReleaseHosts.get(host));
                        }
                    }
                    operation.setOperationHosts(destHosts);
                }

                if (operation.getStatus().isPreRun()) {
                    operation.execute();

                    this.writeLog("The user " + getOperator().getLoginName() + " executes " + operation);
                }
            }
        } else {
            if (isPreview()) {
                operation.preview();

                this.writeLog("The user " + getOperator().getLoginName() + " previews " + operation);
            }

            if (isRemove() && (operation.getStatus().isExit() || operation.getStatus().isPreRun())) {
                OperationManager.get().removeOperation(operation);
                operation = null;
            }

            if (isExecute() && operation.getStatus().isPreRun()) {
                operation.execute();

                this.writeLog("The user " + getOperator().getLoginName() + " executes " + operation);
            }
        }

        return SUCCESS;
    }

    public String getPatchnum() {
        return patchnum == null ? "" : patchnum;
    }

    public void setPatchnum(String patchnum) {
        this.patchnum = patchnum;
    }
}
