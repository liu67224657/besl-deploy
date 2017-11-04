/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.command;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class ReleaseCommand extends SshCommand {
    private static final String CMD_RELEASE = "/home/ops/bin/release";

    // stop->down start->[Deploy operations]->release or just restart
    public static final String STOPALL = "stopall";
    public static final String STARTRELEASE = "startrelease";
    public static final String STOPRELEASE = "stoprelease";
    public static final String RESTARTRELEASE = "restartrelease";
    public static final String STARTALL = "startall";
    public static final String RESTARTALL = "restartall";

    public static final String STOPALLTODOWN = "stopalltodown";
    public static final String STOPRELEASETOLIVE = "stopreleasetolive";

    public ReleaseCommand(String releaseOper) {
        super(CMD_RELEASE + CMD_SPLIT_SYMBOL + releaseOper);
    }
}
