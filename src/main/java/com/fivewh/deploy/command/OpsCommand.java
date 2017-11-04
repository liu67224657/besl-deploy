/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.command;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class OpsCommand extends SshCommand {
    private static final String CMD_OPS = "/home/ops/bin/ops";

    public static final String SYNC = "sync";
    public static final String INIT = "init";

    public OpsCommand(String opsOper) {
        super(CMD_OPS + CMD_SPLIT_SYMBOL + opsOper);
    }
}
