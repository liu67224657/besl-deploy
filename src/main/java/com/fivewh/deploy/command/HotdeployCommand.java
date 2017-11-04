/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.command;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class HotdeployCommand extends SshCommand {
    private static final String CMD_HOTDEPLOY = "/home/ops/bin/hotdeploy";

    public static final String SYNC = "sync";
    public static final String DEPLOY = "deploy";

    public HotdeployCommand(String hotdeployOper) {
        super(CMD_HOTDEPLOY + CMD_SPLIT_SYMBOL + hotdeployOper);
    }
}
