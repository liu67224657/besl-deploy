/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.command;

import com.fivewh.deploy.OperationManager;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class SshCommand extends LinuxCommand {
    public SshCommand(String operCmd) {
        super(operCmd);

        prefixCmd = OperationManager.get().getCmdPrefix();
    }
}
