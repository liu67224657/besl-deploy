/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.command;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class TpDeployCommand extends SshCommand {
    private static final String CMD_DEPLOY = "/home/ops/bin/tpdeploy";

    public static final String BACKUP = "backup";
    public static final String BACKUPTODEPLOY = "backuptodeploy";
    public static final String DEPLOY = "deploy";
    public static final String ROLLBACK = "rollback";
    public static final String COMMIT = "commit";
    public static final String RESTORE = "restore";


    public TpDeployCommand(String args, String deployOper, String deployVersion) {
        super(CMD_DEPLOY + CMD_SPLIT_SYMBOL + args + CMD_SPLIT_SYMBOL + "ROOT" + CMD_SPLIT_SYMBOL + deployVersion + CMD_SPLIT_SYMBOL + deployOper);
    }
}
