/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.html.action;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class LogoutAction extends AccessCheckAction {
    public String execute() {
        removeOperatorFromSession();

        return SUCCESS;
    }
}
