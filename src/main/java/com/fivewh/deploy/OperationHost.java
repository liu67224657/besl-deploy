/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class OperationHost {
    private String hostname;
    private Map<String, DeployItem> deployItems = new LinkedHashMap<String, DeployItem>();

    public static final OperationHost UNKNOWN = new OperationHost("unknown");

    public OperationHost(String name) {
        hostname = name;
    }

    public OperationHost(String name, Map<String, DeployItem> items) {
        hostname = name;
        deployItems = items;
    }

    public String getHostname() {
        return hostname;
    }

    public Map<String, DeployItem> getDeployItems() {
        return deployItems;
    }

    public String toString() {
        return "OperationHost: hostname=" + hostname + ", deployItems=[" + deployItems + "]";
    }

    public int hashCode() {
        return hostname.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OperationHost)) {
            return false;
        }

        OperationHost host = (OperationHost) obj;
        return hostname.equalsIgnoreCase(host.getHostname());
    }
}
