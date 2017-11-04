package com.fivewh.deploy.html.action;

import com.fivewh.deploy.Operation;
import com.fivewh.deploy.OperationHost;
import com.fivewh.deploy.OperationKey;
import com.fivewh.deploy.OperationManager;
import com.fivewh.deploy.command.TpBuildCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-6-30
 * Time: 下午1:29
 * To change this template use File | Settings | File Templates.
 */
public class TpBuildAction extends OperationAction {
    private OperationHost buildHost;

    private List<String> buildDirs;
    private String buildDir;

    public String execute() {
        if (!isLogin()) {
            return LOGIN_REDIRECT;
        }

        if (!hasAccess(getOperationEnv(), getExecType())) {
            return NO_ACCESS_REDIRECT;
        }

        operKey = new OperationKey(getOperationEnv(), getOper(), getArgs());
        operation = OperationManager.get().getOperation(operKey);
        buildHost = OperationManager.get().getBuildToolsPlatformHostByEnv(getOperationEnv());
        formSelectHosts.add(buildHost);
        buildDirs = OperationManager.get().getBuildToolsPlatformDirsByEnv(getOperationEnv());

        if (operation == null) {
            if (isPreview()) {
                TpBuildCommand cmd = new TpBuildCommand(buildDir);

                Map<String, OperationHost> buildHosts = new HashMap<String, OperationHost>();
                for (String host : this.getHosts()) {
                    if (buildHost.getHostname().equalsIgnoreCase(host)) {
                        buildHosts.put(buildHost.getHostname(), buildHost);
                    }
                }

                operation = new Operation(operKey);
                OperationManager.get().putOperation(operation);
                operation.setCommand(cmd);
                operation.setOperationHosts(buildHosts);


                operation.preview();

                this.writeLog("The user " + getOperator().getLoginName() + " previews " + operation);
            }

            if (isExecute()) {
                if (operation == null) {
                    TpBuildCommand cmd = new TpBuildCommand(buildDir);

                    Map<String, OperationHost> buildHosts = new HashMap<String, OperationHost>();
                    for (String host : this.getHosts()) {
                        if (buildHost.getHostname().equalsIgnoreCase(host)) {
                            buildHosts.put(buildHost.getHostname(), buildHost);
                        }
                    }

                    operation = new Operation(operKey);
                    OperationManager.get().putOperation(operation);
                    operation.setCommand(cmd);
                    operation.setOperationHosts(buildHosts);
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


    public List<String> getBuildDirs() {
        return buildDirs;
    }


    public String getBuildDir() {
        return buildDir;
    }

    public void setBuildDir(String buildDir) {
        this.buildDir = buildDir;
    }
}
