/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.command;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class BuildCommand extends SshCommand {
    private static final String CMD_BUILD = "/home/ops/bin/build";

    public BuildCommand(String buildDir, String buildVersion) {
        super(CMD_BUILD + CMD_SPLIT_SYMBOL + buildDir + CMD_SPLIT_SYMBOL + buildVersion);
    }
}
