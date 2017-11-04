/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.DeployItem;
import com.fivewh.deploy.Operation;
import com.fivewh.deploy.OperationHost;
import com.fivewh.deploy.OperationKey;
import com.fivewh.deploy.OperationManager;
import com.fivewh.deploy.OperationType;
import com.fivewh.deploy.command.WsDeployCommand;
import com.fivewh.deploy.util.DeployDirectoryUtil;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class WsDeployAction extends OperationAction {
    private String version;
    private DeployItem filterItem = new DeployItem("webapps", "");

    private List<String> versions;
    private static long lastLoadTime = 0;
    private static long reloadInterval = 5 * 60 * 1000;

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

        initVersions();

        if (operation == null) {
            if (isPreview()) {
                WsDeployCommand cmd = new WsDeployCommand(operKey.getArgs(), getVersion());
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
                    WsDeployCommand cmd = new WsDeployCommand(operKey.getArgs(), getVersion());
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

    private void initVersions() {
        if ((System.currentTimeMillis() - lastLoadTime) > reloadInterval) {
            try {
                versions = DeployDirectoryUtil.getAllFileNamesInDir(OperationManager.get().getWebServerRoot());
            } catch (FileNotFoundException e) {
                versions = Collections.EMPTY_LIST;
            }
        }
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

    public String getVersion() {
        return version == null ? "0.0.0.0" : version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getVersions() {
        return versions;
    }
}
