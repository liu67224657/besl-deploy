/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.*;
import com.fivewh.deploy.command.DeployCommand;
import com.fivewh.deploy.util.DeployDirectoryUtil;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class DeployAction extends OperationAction {
    private String version;

    private List<String> versions;
    private static long lastLoadTime = 0;
    private static long reloadInterval = 2 * 60 * 1000;

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

        initVersions();

        if (operation == null) {
            if (isPreview()) {
                DeployCommand cmd = new DeployCommand(operKey.getArgs(), getVersion());
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
                    DeployCommand cmd = new DeployCommand(operKey.getArgs(), getVersion());
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
                versions = DeployDirectoryUtil.getAllSubDirNames(
                        OperationManager.get().getPackageDirByEnv(this.getOperationEnv()));
            } catch (FileNotFoundException e) {
                versions = Collections.EMPTY_LIST;
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
