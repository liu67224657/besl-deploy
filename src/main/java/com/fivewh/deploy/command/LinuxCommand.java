/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.command;

import com.fivewh.deploy.OperationHost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public abstract class LinuxCommand {
    protected final static String CMD_SPLIT_SYMBOL = " ";

    //the operCmd execute result
    private int execResult = -1;
    private long executeTime = 0;
    private Date exitDate;
    private StringBuffer execResultMsg = new StringBuffer();
    private StringBuffer errorResultMsg = new StringBuffer();
    private RunStatus status = RunStatus.PRERUN;
    private Object lockObject = new Object();

    //operCmd parameters
    protected OperationHost operHost = OperationHost.UNKNOWN;
    protected String prefixCmd;
    protected String operCmd;

    //operCmd run thread
    private Thread execThread = new ExecThread();

    public LinuxCommand(String oc) {
        operCmd = oc;
    }

    public LinuxCommand(OperationHost oh, String oc) {
        operHost = oh;
        operCmd = oc;
    }

    public void setOperHost(OperationHost oh) {
        operHost = oh;
    }

    public OperationHost getOperHost() {
        return operHost;
    }

    public String getOperCmd() {
        return operCmd;
    }

    public void setOperCmd(String oc) {
        operCmd = oc;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public int hashCode() {
        return (prefixCmd + operHost.getHostname() + operCmd).hashCode();
    }

    public String toString() {
        return "LinuxCommand=[" + generateCommand() + "]";
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof LinuxCommand)) {
            return false;
        }

        return generateCommand().equalsIgnoreCase(((LinuxCommand) obj).generateCommand());
    }

    public String generateCommand() {
        return prefixCmd + operHost.getHostname() + CMD_SPLIT_SYMBOL + operCmd;
    }

    public void execute() {
        execute(true);
    }

    public void execute(final boolean daemon) {
        execThread.start();

        //wait the execThread is executed.
        if (!daemon) {
            while (true) {
                if (status.isExit()) {
                    break;
                }

                //
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // do nothing.
                }
            }
        }
    }

    public int getExecResult() {
        return execResult;
    }

    public String getExecResultMsg() {
        return execResultMsg.toString();
    }

    public String getErrorResultMsg() {
        return errorResultMsg.toString();
    }

    public RunStatus getStatus() {
        return status;
    }

    public Date getExitDate() {
        return exitDate;
    }

    // the execThread run the operCmd;
    private class ExecThread extends Thread {
        public void run() {
            long startTime = System.currentTimeMillis();

            synchronized (lockObject) {
                status = RunStatus.RUNNING;

                try {
                    Process proc = Runtime.getRuntime().exec(generateCommand());

                    StreamPumper inputPumper = new StreamPumper(proc.getInputStream(), execResultMsg);
                    StreamPumper errorPumper = new StreamPumper(proc.getErrorStream(), errorResultMsg);

                    inputPumper.start();
                    errorPumper.start();

                    proc.waitFor();
                    inputPumper.join();
                    errorPumper.join();
                    proc.destroy();

                    execResult = proc.exitValue();
                } catch (Exception e) {
                    execResult = -1;
                    errorResultMsg.append(e.getMessage());
                } finally {
                    status = RunStatus.EXIT;
                    exitDate = new Date();
                    executeTime = exitDate.getTime() - startTime;
                }
            }
        }
    }

    private class StreamPumper extends Thread {
        private BufferedReader bufferReader;
        private StringBuffer outputBuffer;

        public StreamPumper(InputStream is, StringBuffer outBuffer) {
            bufferReader = new BufferedReader(new InputStreamReader(is));
            outputBuffer = outBuffer;
        }

        public void run() {
            try {
                String resultSegment = null;
                while ((resultSegment = bufferReader.readLine()) != null) {
                    outputBuffer.append(resultSegment).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
