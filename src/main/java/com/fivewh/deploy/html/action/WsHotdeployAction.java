/**
 * (C) 2008 Operation platform ccnec.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.DeployItem;
import com.fivewh.deploy.Operation;
import com.fivewh.deploy.OperationHost;
import com.fivewh.deploy.OperationKey;
import com.fivewh.deploy.OperationManager;
import com.fivewh.deploy.OperationType;
import com.fivewh.deploy.command.WsHotdeployCommand;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class WsHotdeployAction extends OperationAction {
    private DeployItem filterItem = new DeployItem("webapps", "");

    public String execute() {
        if (!isLogin()) {
            return LOGIN_REDIRECT;
        }

        if (!hasAccess(getOperationEnv(), getExecType())) {
            return NO_ACCESS_REDIRECT;
        }

        operKey = new OperationKey(getOperationEnv(), getOper(), getArgs());
        operation = OperationManager.get().getOperation(operKey);

        if (WsHotdeployCommand.SYNC.equalsIgnoreCase(operKey.getArgs())) {
            formSelectHosts.add(OperationManager.get().getBuildHostByEnv(getOperationEnv()));
        } else {
            formSelectHosts.addAll(OperationManager.get().getHostsByEnv(getOperationEnv()).values());

            filterWebServers();
        }

        if (operation == null) {
            if (isPreview()) {
                WsHotdeployCommand cmd = new WsHotdeployCommand(operKey.getArgs());
                operation = new Operation(operKey);
                OperationManager.get().putOperation(operation);

                Map<String, OperationHost> destHosts = new HashMap<String, OperationHost>();
                OperationHost buildHost = OperationManager.get().getBuildHostByEnv(getOperationEnv());
                if (WsHotdeployCommand.SYNC.equalsIgnoreCase(operKey.getArgs())) {
                    for (String host : this.getHosts()) {
                        if (buildHost.getHostname().equalsIgnoreCase(host)) {
                            destHosts.put(buildHost.getHostname(), buildHost);
                        }
                    }
                } else {
                    Map<String, OperationHost> allReleaseHost = OperationManager.get().getHostsByEnv(getOperationEnv());

                    for (String host : this.getHosts()) {
                        if (allReleaseHost.containsKey(host)) {
                            destHosts.put(host, allReleaseHost.get(host));
                        }
                    }
                }

                operation.setCommand(cmd);

                if (WsHotdeployCommand.APPLY_A.equalsIgnoreCase(operKey.getArgs())
                        || WsHotdeployCommand.APPLY_T.equalsIgnoreCase(operKey.getArgs())) {
                    operation.setOperType(OperationType.ONEBYONE);
                } else {
                    operation.setOperType(OperationType.PARALLEL);
                }

                operation.setOperationHosts(destHosts);

                operation.preview();
            }

            if (isExecute()) {
                if (operation == null) {
                    WsHotdeployCommand cmd = new WsHotdeployCommand(operKey.getArgs());
                    operation = new Operation(operKey);
                    OperationManager.get().putOperation(operation);

                    Map<String, OperationHost> destHosts = new HashMap<String, OperationHost>();
                    OperationHost buildHost = OperationManager.get().getBuildHostByEnv(getOperationEnv());
                    if (WsHotdeployCommand.SYNC.equalsIgnoreCase(operKey.getArgs())) {
                        for (String host : this.getHosts()) {
                            if (buildHost.getHostname().equalsIgnoreCase(host)) {
                                destHosts.put(buildHost.getHostname(), buildHost);
                            }
                        }
                    } else {
                        Map<String, OperationHost> allReleaseHost = OperationManager.get().getHostsByEnv(getOperationEnv());

                        for (String host : this.getHosts()) {
                            if (allReleaseHost.containsKey(host)) {
                                destHosts.put(host, allReleaseHost.get(host));
                            }
                        }
                    }

                    operation.setCommand(cmd);

                    if (WsHotdeployCommand.APPLY_A.equalsIgnoreCase(operKey.getArgs())
                            || WsHotdeployCommand.APPLY_T.equalsIgnoreCase(operKey.getArgs())) {
                        operation.setOperType(OperationType.ONEBYONE);
                    } else {
                        operation.setOperType(OperationType.PARALLEL);
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
                hasWebItem = hasWebItem || item.accept(filterItem);
            }

            if (!hasWebItem) {
                itr.remove();
            }
        }
    }
}
