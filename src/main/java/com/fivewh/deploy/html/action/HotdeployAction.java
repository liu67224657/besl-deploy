/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.*;
import com.fivewh.deploy.command.HotdeployCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class HotdeployAction extends OperationAction {

    public String execute() {
        if (!isLogin()) {
            return LOGIN_REDIRECT;
        }

        if (!hasAccess(getOperationEnv(), getExecType())) {
            return NO_ACCESS_REDIRECT;
        }

        operKey = new OperationKey(getOperationEnv(), getOper(), getArgs());
        operation = OperationManager.get().getOperation(operKey);

        if (HotdeployCommand.SYNC.equalsIgnoreCase(operKey.getArgs())) {
            formSelectHosts.add(OperationManager.get().getBuildHostByEnv(getOperationEnv()));
        } else {
            formSelectHosts.addAll(OperationManager.get().getHostsByEnv(getOperationEnv()).values());
        }


        if (operation == null) {
            if (isPreview()) {
                HotdeployCommand cmd = new HotdeployCommand(operKey.getArgs());
                operation = new Operation(operKey);
                OperationManager.get().putOperation(operation);

                Map<String, OperationHost> destHosts = new HashMap<String, OperationHost>();
                OperationHost buildHost = OperationManager.get().getBuildHostByEnv(getOperationEnv());
                if (HotdeployCommand.SYNC.equalsIgnoreCase(operKey.getArgs())) {
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
                operation.setOperType(OperationType.PARALLEL);
                operation.setOperationHosts(destHosts);

                operation.preview();
            }

            if (isExecute()) {
                if (operation == null) {
                    HotdeployCommand cmd = new HotdeployCommand(operKey.getArgs());
                    operation = new Operation(operKey);
                    OperationManager.get().putOperation(operation);

                    Map<String, OperationHost> destHosts = new HashMap<String, OperationHost>();
                    OperationHost buildHost = OperationManager.get().getBuildHostByEnv(getOperationEnv());
                    if (HotdeployCommand.SYNC.equalsIgnoreCase(operKey.getArgs())) {
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
                    operation.setOperType(OperationType.PARALLEL);
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
}
