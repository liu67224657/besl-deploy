/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.command;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class TpBuildCommand extends SshCommand {
    private static final String CMD_BUILD = "/home/ops/bin/tpbuild";

    public TpBuildCommand(String buildDir) {
        super(CMD_BUILD + CMD_SPLIT_SYMBOL + buildDir + CMD_SPLIT_SYMBOL + "version");
    }
}
