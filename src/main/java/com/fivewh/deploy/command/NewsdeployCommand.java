/**
 * (C) 2008 Operation platform ccnec.com
 */
package com.fivewh.deploy.command;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class NewsdeployCommand extends SshCommand {
    private static final String CMD_HOTDEPLOY = "/home/ops/bin/newsdeploy";

    public static final String SYNC = "sync";
    public static final String DEPLOY = "deploy";
    public NewsdeployCommand(String hotdeployOper) {
        super(CMD_HOTDEPLOY + CMD_SPLIT_SYMBOL + hotdeployOper);
    }
}
