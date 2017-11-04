/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

import com.fivewh.deploy.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public abstract class OperationAction extends AccessCheckAction {
    private String env;
    private String oper;
    private String args;
    private String exec;
    private String[] hosts = new String[]{};

    protected Operation operation;
    protected OperationKey operKey;

    private OperationEnv operationEnv;
    private ExecType execType = ExecType.REFRESH;
    private Collection<OperationHost> envAllHosts;
    protected Collection<OperationHost> formSelectHosts = new ArrayList<OperationHost>();

    private Collection<OperationHost> toolsPlatformHosts;

    //////////////////////////////////////////////////////////
    public void setEnv(String env) {
        this.env = env;

        operationEnv = OperationManager.get().getOperationEnv(this.env);
        envAllHosts = OperationManager.get().getHostsByEnv(operationEnv).values();

        toolsPlatformHosts = OperationManager.get().getToolsplatformHostsByEnv(operationEnv).values();
    }

    public String getEnv() {
        return env;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getExec() {
        return exec;
    }

    public ExecType getExecType() {
        return execType;
    }

    public void setExec(String exec) {
        this.exec = exec;

        execType = ExecType.getTypeByCode(exec);
    }

    public String[] getHosts() {
        return hosts;
    }

    public void setHosts(String[] hosts) {
        this.hosts = hosts;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public boolean isPreview() {
        return ExecType.PREVIEW.equals(execType);
    }

    public boolean isRefresh() {
        return ExecType.REFRESH.equals(execType);
    }

    public boolean isExecute() {
        return ExecType.EXECUTE.equals(execType);
    }

    public boolean isRemove() {
        return ExecType.REMOVE.equals(execType);
    }

    public OperationEnv getOperationEnv() {
        return operationEnv;
    }

    public void setOperationEnv(OperationEnv operationEnv) {
        this.operationEnv = operationEnv;
    }


    public Collection<OperationHost> getEnvAllHosts() {
        return envAllHosts;
    }

    public void setEnvAllHosts(Collection<OperationHost> envAllHosts) {
        this.envAllHosts = envAllHosts;
    }

    public Collection<OperationHost> getToolsPlatformHosts() {
        return toolsPlatformHosts;
    }

    public void setToolsPlatformHosts(Collection<OperationHost> toolsPlatformHosts) {
        this.toolsPlatformHosts = toolsPlatformHosts;
    }

    public Collection<OperationHost> getFormSelectHosts() {
        return formSelectHosts;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public OperationKey getOperKey() {
        return operKey;
    }

    public void setOperKey(OperationKey operKey) {
        this.operKey = operKey;
    }
}
