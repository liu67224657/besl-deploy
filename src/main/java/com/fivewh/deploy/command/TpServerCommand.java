/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.command;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class TpServerCommand extends SshCommand {
    private static final String CMD_DEPLOY = "/home/ops/bin/tp-tomcatctl";


    public TpServerCommand(String args, String deployOper) {
        super(CMD_DEPLOY + CMD_SPLIT_SYMBOL + args + CMD_SPLIT_SYMBOL + deployOper);
    }
}
