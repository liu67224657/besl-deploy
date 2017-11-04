/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.Operation;
import com.fivewh.deploy.OperationHost;
import com.fivewh.deploy.OperationKey;
import com.fivewh.deploy.OperationManager;
import com.fivewh.deploy.command.BuildCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class BuildAction extends OperationAction {
    private String version;

    private String buildDir;
    private OperationHost buildHost;
    private List<String> buildDirs;

    private static final String NIGHTLY = "nightly";
    private static final String VERSION = "version";

    public String execute() {
        if (!isLogin()) {
            return LOGIN_REDIRECT;
        }

        if (!hasAccess(getOperationEnv(), getExecType())) {
            return NO_ACCESS_REDIRECT;
        }

        operKey = new OperationKey(getOperationEnv(), getOper(), getArgs());
        operation = OperationManager.get().getOperation(operKey);
        buildHost = OperationManager.get().getBuildHostByEnv(getOperationEnv());
        buildDirs = OperationManager.get().getBuildDirsByEnv(getOperationEnv());
        formSelectHosts.add(buildHost);

        if (operation == null) {
            if (isPreview()) {
                BuildCommand cmd = new BuildCommand(getBuildDir(), getVersion().equals("yes") ? VERSION : NIGHTLY);

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
                    BuildCommand cmd = new BuildCommand(getBuildDir(), getVersion().equals("yes") ? VERSION : NIGHTLY);

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

                this.writeLog("The user " + getOperator().getLoginName() + " priviews " + operation);
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


    public String getBuildDir() {
        return buildDir;
    }

    public void setBuildDir(String buildDir) {
        this.buildDir = buildDir;
    }

    public List<String> getBuildDirs() {
        return buildDirs;
    }

    public OperationHost getBuildHost() {
        return buildHost;
    }

    public String getVersion() {
        return version == null ? "" : version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
