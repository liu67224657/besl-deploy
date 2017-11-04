/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.command;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class RunStatus {
    private static Map<String, RunStatus> statusMap = new HashMap<String, RunStatus>();

    public static final RunStatus PRERUN = new RunStatus("prerun");
    public static final RunStatus RUNNING = new RunStatus("running");
    public static final RunStatus EXIT = new RunStatus("exit");

    private String code;

    private RunStatus(String c) {
        code = c.toLowerCase();

        statusMap.put(code, this);
    }

    public String getCode() {
        return code;
    }

    public boolean isPreRun() {
        return equals(PRERUN);
    }

    public boolean isRunning() {
        return equals(RUNNING);
    }

    public boolean isExit() {
        return equals(EXIT);
    }

    public String toString() {
        return "RunStatus: code=" + code;
    }

    public int hashCode() {
        return code.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RunStatus)) {
            return false;
        }

        RunStatus status = (RunStatus) obj;
        return code.equalsIgnoreCase(status.getCode());
    }
}
