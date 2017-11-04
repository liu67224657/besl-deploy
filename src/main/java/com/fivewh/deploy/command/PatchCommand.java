/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.command;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class PatchCommand extends SshCommand {
    private static final String CMD_PATCH = "/home/ops/bin/platformpatch";

    public static final String CHECK = "check";
    public static final String DEPLOY = "deploy";

    public PatchCommand(String patchOper, String patchNum) {
        super(CMD_PATCH + CMD_SPLIT_SYMBOL + patchNum + CMD_SPLIT_SYMBOL + patchOper);
    }
}
