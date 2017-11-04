/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy;

import com.fivewh.deploy.command.LinuxCommand;
import com.fivewh.deploy.command.RunStatus;
import com.fivewh.deploy.command.SshCommand;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class Operation {
    private OperationKey key;
    private Map<String, OperationHost> operationHosts = new TreeMap<String, OperationHost>();
    private LinuxCommand command;
    private OperationType operType = OperationType.PARALLEL;
    private boolean previewed = false;

    private Thread execThread = new OperationThread();
    private Date initDate;
    private Date startDate;
    private Date exitDate;
    private int runResult = -1;
    private RunStatus runStatus = RunStatus.PRERUN;

    private Map<String, LinuxCommand> commandMap = new TreeMap<String, LinuxCommand>();

    public Operation(OperationKey k) {
        key = k;

        initDate = new Date();
    }

    public synchronized void preview() {
        for (OperationHost host : operationHosts.values()) {
            if (commandMap.containsKey(host.getHostname())) {
                continue;
            }

            LinuxCommand cmd = new SshCommand(command.getOperCmd());
            cmd.setOperHost(host);

            commandMap.put(host.getHostname(), cmd);
        }

        previewed = true;
    }

    public synchronized void execute() {
        if (!previewed) {
            preview();
        }

        if (!commandMap.isEmpty()) {
            execThread.start();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Date getInitDate() {
        return initDate;
    }

    public Date getExitDate() {
        if (exitDate == null && isExit()) {
            for (LinuxCommand cmd : commandMap.values()) {
                exitDate = cmd.getExitDate();
            }
        }

        return exitDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public OperationKey getKey() {
        return key;
    }

    public Map<String, OperationHost> getOperationHosts() {
        return operationHosts;
    }

    public void setOperationHosts(Map<String, OperationHost> hosts) {
        this.operationHosts = hosts;
    }

    public void addHostName(OperationHost host) {
        operationHosts.put(host.getHostname(), host);
    }

    public LinuxCommand getCommand() {
        return command;
    }

    public void setCommand(LinuxCommand command) {
        this.command = command;
    }

    public OperationType getOperType() {
        return operType;
    }

    public void setOperType(OperationType operType) {
        this.operType = operType;
    }

    public Collection<LinuxCommand> getCommands() {
        return commandMap.values();
    }

    public int getRunResult() {
        if (runResult == -1 && isExit()) {
            runResult = 0;
            for (LinuxCommand cmd : commandMap.values()) {
                runResult += cmd.getExecResult();
            }
        }

        return runResult;
    }

    public String toString() {
        StringBuffer strBuffer = new StringBuffer();

        strBuffer.append("Operation: key=[").append(this.key).append("],");
        strBuffer.append("hostname=[").append(operationHosts).append("]");
        strBuffer.append("operCmd=").append(command);
        strBuffer.append("operType=[").append(operType).append("]");

        return strBuffer.toString();
    }

    public int hashCode() {
        return getKey().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Operation)) {
            return false;
        }

        Operation oper = (Operation) obj;
        return getKey().equals(oper.getKey());
    }

    public RunStatus getStatus() {
        if (isPreRun()) {
            runStatus = RunStatus.PRERUN;
        } else if (isExit()) {
            runStatus = RunStatus.EXIT;
        } else if (isRunning()) {
            runStatus = RunStatus.RUNNING;
        }

        return runStatus;
    }

    private boolean isPreRun() {
        boolean returnValue = true;

        for (LinuxCommand cmd : commandMap.values()) {
            returnValue = returnValue && cmd.getStatus().isPreRun();
        }

        return returnValue;
    }

    private boolean isRunning() {
        boolean returnValue = false;
        boolean hasInPreRun = false;
        boolean hasExited = false;

        for (LinuxCommand cmd : commandMap.values()) {
            hasInPreRun = hasInPreRun || cmd.getStatus().isPreRun();
            hasExited = hasExited || cmd.getStatus().isExit();

            returnValue = returnValue || cmd.getStatus().isRunning();
        }

        return returnValue || (hasInPreRun && hasExited);
    }

    private boolean isExit() {
        boolean returnValue = true;

        for (LinuxCommand cmd : commandMap.values()) {
            returnValue = returnValue && cmd.getStatus().isExit();
        }

        return returnValue;
    }

    // the OperationThread run the operCmd;
    private class OperationThread extends Thread {
        public void run() {
            synchronized (commandMap) {
                startDate = new Date();
                for (LinuxCommand command : commandMap.values()) {
                    if (operType.equals(OperationType.PARALLEL)) {
                        System.out.println("run operation " + command);
                        command.execute();
                    } else if (operType.equals(OperationType.ONEBYONE)) {
                        command.execute(false);
                    }
                }
            }
        }
    }
}
