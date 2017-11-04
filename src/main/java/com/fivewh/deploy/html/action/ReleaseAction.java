/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.*;
import com.fivewh.deploy.command.ReleaseCommand;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class ReleaseAction extends OperationAction {

    private DeployItem webappsFilterItem = new DeployItem("webapps", "");
    private DeployItem beslfilterItem = new DeployItem("besl", "");

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

        filterWebServers();

        if (operation == null) {
            if (isPreview()) {
                ReleaseCommand cmd = new ReleaseCommand(operKey.getArgs());
                operation = new Operation(operKey);
                OperationManager.get().putOperation(operation);

                operation.setCommand(cmd);
                operation.setOperType(OperationType.PARALLEL);

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
                    ReleaseCommand cmd = new ReleaseCommand(operKey.getArgs());
                    operation = new Operation(operKey);
                    OperationManager.get().putOperation(operation);

                    operation.setCommand(cmd);
                    operation.setOperType(OperationType.PARALLEL);
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

    private void filterWebServers() {
        boolean hasWebItem = false;
        for (Iterator<OperationHost> itr = formSelectHosts.iterator(); itr.hasNext();) {
            OperationHost host = itr.next();

            hasWebItem = false;
            for (DeployItem item : host.getDeployItems().values()) {
                hasWebItem = hasWebItem || item.accept(webappsFilterItem) || item.accept(beslfilterItem);
            }

            if (!hasWebItem) {
                itr.remove();
            }
        }
    }
}
