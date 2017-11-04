/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import java.util.HashMap;
import java.util.Map;

import com.fivewh.deploy.Operation;
import com.fivewh.deploy.OperationHost;
import com.fivewh.deploy.OperationKey;
import com.fivewh.deploy.OperationManager;
import com.fivewh.deploy.OperationType;
import com.fivewh.deploy.command.NewsdeployCommand;
import com.fivewh.deploy.command.OpsCommand;
import com.fivewh.deploy.command.PatchCommand;

/**
 * @Auther: <a mailto:lim@gmail.com>Li Ming</a>
 */
public class NewsDeployAction extends OperationAction {
    private static final long serialVersionUID = 1508609815586631397L;
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
        if (NewsdeployCommand.SYNC.equalsIgnoreCase(operKey.getArgs())) {
            formSelectHosts.add(OperationManager.get().getBuildHostByEnv(getOperationEnv()));
        } else {
            formSelectHosts.addAll(OperationManager.get().getHostsByEnv(getOperationEnv()).values());
        }

        if (operation == null) {
            if (isPreview()) {
                NewsdeployCommand cmd = new NewsdeployCommand(operKey.getArgs());
                operation = new Operation(operKey);
                OperationManager.get().putOperation(operation);

                operation.setCommand(cmd);
                if (PatchCommand.CHECK.equalsIgnoreCase(getArgs())) {
                    operation.setOperType(OperationType.PARALLEL);
                } else {
                    operation.setOperType(OperationType.ONEBYONE);
                }

                Map<String, OperationHost> destHosts = new HashMap<String, OperationHost>();
                OperationHost buildHost = OperationManager.get().getBuildHostByEnv(getOperationEnv());
                if (OpsCommand.SYNC.equalsIgnoreCase(operKey.getArgs())) {
                    for (String host : this.getHosts()) {
                        if (buildHost.getHostname().equalsIgnoreCase(host)) {
                            destHosts.put(buildHost.getHostname(), buildHost);
                        }
                    }
                } else {
                    Map<String, OperationHost> allReleaseHosts = OperationManager.get().getHostsByEnv(getOperationEnv());

                    for (String host : this.getHosts()) {
                        if (allReleaseHosts.containsKey(host)) {
                            destHosts.put(host, allReleaseHosts.get(host));
                        }
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
                    OperationHost host = OperationManager.get().getBuildWebserverHostByEnv(getOperationEnv());
                    destHosts.put(host.getHostname(), host);
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
