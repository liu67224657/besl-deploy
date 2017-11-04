/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class DeployItem {
    private static final String KEY_SPLIT = ".";

    private String applicationCode;
    private String moduleCode;

    public DeployItem(String mc, String ac) {
        moduleCode = mc.toLowerCase();
        applicationCode = (ac == null ? "" : ac.toLowerCase());
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public boolean accept(DeployItem item) {
        boolean accept = false;

        if (moduleCode.equals(item.getModuleCode())) {
            accept = applicationCode.startsWith(item.getApplicationCode());
        }

        return accept;
    }

    public static DeployItem parseDeployItem(String code) {
        if (code == null || "".equals(code)) {
            return null;
        }

        String appCode = null;
        String modCode = null;
        int splitIndex = code.indexOf(KEY_SPLIT);

        if (splitIndex < 1) {
            if (splitIndex > 0) {
                modCode = code.substring(0, splitIndex);
                appCode = code.substring(splitIndex + 1, code.length());
            } else {
                modCode = code;
                appCode = "";
            }
        } else {
            modCode = code.substring(0, splitIndex);
            appCode = code.substring(splitIndex + 1, code.length());
        }

        return new DeployItem(modCode, appCode);
    }

    public String getItemKey() {
        return moduleCode + ("".equals(applicationCode) ? "" : (KEY_SPLIT + applicationCode));
    }

    public String toString() {
        return "DeployItem: key=" + getItemKey();
    }

    public int hashCode() {
        return getItemKey().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DeployItem)) {
            return false;
        }

        DeployItem item = (DeployItem) obj;
        return getItemKey().equalsIgnoreCase(item.getItemKey());
    }
}
