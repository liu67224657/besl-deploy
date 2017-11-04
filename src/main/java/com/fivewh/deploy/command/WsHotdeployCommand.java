/**
 * (C) 2008 Operation platform ccnec.com
 */
package com.fivewh.deploy.command;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class WsHotdeployCommand extends SshCommand {
    private static final String CMD_HOTDEPLOY = "/home/ops/bin/wshotdeploy";

    public static final String SYNC = "sync";
    public static final String DEPLOY = "deploy";
    public static final String APPLY_A = "applya";
    public static final String APPLY_T = "applyt";

    public WsHotdeployCommand(String hotdeployOper) {
        super(CMD_HOTDEPLOY + CMD_SPLIT_SYMBOL + hotdeployOper);
    }
}
