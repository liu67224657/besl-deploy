/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.*;
import com.fivewh.deploy.command.TpDeployCommand;
import com.fivewh.deploy.command.TpServerCommand;
import com.fivewh.deploy.util.DeployDirectoryUtil;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class TpServerAction extends OperationAction {
    private String version;
    private String app;  //wikipage ROOT
    private DeployItem filterItem = new DeployItem("toolsapps", "");


    public String execute() {
        if (!isLogin()) {
            return LOGIN_REDIRECT;
        }

        if (!hasAccess(getOperationEnv(), getExecType())) {
            return NO_ACCESS_REDIRECT;
        }

        operKey = new OperationKey(getOperationEnv(), getOper(), getArgs());
        operation = OperationManager.get().getOperation(operKey);
        formSelectHosts.addAll(this.getToolsPlatformHosts());

        filterWebServers(operKey.getArgs());

        if (operation == null) {
            if (isPreview()) {
                TpServerCommand cmd = new TpServerCommand(operKey.getArgs(),operKey.getOperName());
                operation = new Operation(operKey);
                OperationManager.get().putOperation(operation);

                operation.setCommand(cmd);
                operation.setOperType(OperationType.PARALLEL);

                Map<String, OperationHost> destHosts = new HashMap<String, OperationHost>();
                Map<String, OperationHost> allReleaseHosts = OperationManager.get().getToolsplatformHostsByEnv(getOperationEnv());
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
                    TpServerCommand cmd = new TpServerCommand(app,operKey.getArgs());
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

    private void filterWebServers(String args) {
        boolean hasWebItem = false;
        DeployItem filterItem = new DeployItem("toolsapps", args);
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

    public String getVersion() {
        return version == null ? "0.0.0.0" : version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

}
