/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy;

import com.fivewh.deploy.util.DeployProps;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class OperationManager {
    private static DeployProps deployProps;
    private static DeployProps operatorProps;
    private static DeployProps cmdProps;

    //////////////////////////////////////////////////////////////////
    private static List<OperationEnv> deployEnvList = new ArrayList<OperationEnv>();
    private static Map<String, OperationEnv> deployEnvMap = new HashMap<String, OperationEnv>();
    private static Map<OperationEnv, Map<String, OperationHost>> envToolsplatformHostMap =
            new HashMap<OperationEnv, Map<String, OperationHost>>();

    private static Map<OperationEnv, Map<String, OperationHost>> envHostMap =
            new HashMap<OperationEnv, Map<String, OperationHost>>();

    private static Map<OperationEnv, OperationHost> buildHostMap = new HashMap<OperationEnv, OperationHost>();
    private static Map<OperationEnv, List<String>> buildHostDirsMap = new HashMap<OperationEnv, List<String>>();

    private static Map<OperationEnv, OperationHost> buildWebserverHostMap = new HashMap<OperationEnv, OperationHost>();
    private static Map<OperationEnv, List<String>> buildWebserverHostDirsMap = new HashMap<OperationEnv, List<String>>();


    private static Map<OperationEnv, OperationHost> buildToolsPlatformHostMap = new HashMap<OperationEnv, OperationHost>();
    private static Map<OperationEnv, List<String>> buildToolsPlatformDirsMap = new HashMap<OperationEnv, List<String>>();

    private static Map<OperationEnv, String> packageDirsMap = new HashMap<OperationEnv, String>();
    private static Map<OperationEnv, String> toolsPlatformPackageDirsMap = new HashMap<OperationEnv, String>();

    private static Map<OperationKey, Operation> operationMap = new HashMap<OperationKey, Operation>();

    private static Map<String, Operator> operatorMap = new HashMap<String, Operator>();
    private static Map<String, Pattern> openIPPatternMap = new HashMap<String, Pattern>();

    private static String patchRoot = "/opt/package/patch";
    private static String webserverRoot = "/opt/package/webserver";


    //
    private static String cmdPrefix = "ssh ";

    //////////////////////////////////////////////////////////////////

    //test if test local modify CONFIG_FILE_NAME dir 
    private static final String CONFIG_FILE_NAME = "/opt/ops/bin/hostname.properties";
    private static final String OPERATOR_FILE_NAME = "/deployoperators.properties";
    private static final String CMD_FILE_NAME = "/cmd.properties";

    //////////////////////////////////////////////////////////////////
    private static final String KEY_ENV_LIST = "env.list";
    private static final String KEY_OPERATOR_LIST = "deploy.operators";
    private static final String KEY_OPEN_IP_LIST = "ip.open.list";

    private static final String KEY_HOSTS_SUFFIX = "hosts";
    private static final String KEY_PACKAGE_ROOT_SUFFIX = "package.root";
    private static final String KEY_PATCH_ROOT = "deploy.patch.root";
    private static final String KEY_WEBSERVER_ROOT = "deploy.webserver.root";

    private static final String KEY_ITEMS_SUFFIX = "items";
    private static final String KEY_DOT = ".";

    private static final String KEY_OPERATOR_PWD = "operator.pwd";
    private static final String KEY_OPERATOR_TYPE = "operator.type";
    private static final String KEY_OPERATOR_ENVS = "operator.envs";

    private static final String KEY_BUILD_HOST_SUFFIX = "build.host";
    private static final String KEY_BUILD_DIRS_SUFFIX = "build.dirs";

    private static final String KEY_BUILD_WEBSERVER_DIRS_SUFFIX = "webserver.build.dirs";
    private static final String KEY_BUILD_WEBSERVER_HOST_SUFFIX = "webserver.build.host";

    private static final String KEY_BUILD_TOOLPLATFORM_DIRS_SUFFIX = "toolsplatform.build.dirs";
    private static final String KEY_BUILD_TOOLPLATFORM_HOSTS_SUFFIX = "toolsplatform.build.host";
    private static final String KEY_TOOLSPLATFORM_PACKAGE_ROOT = "toolsplatform.package.root";

    private static final String KEY_CMD_SUFFIX = "cmd.prefix";


    private static final String KEY_TOOLSPLATFORM_HOSTS_SUFFIX = "toolsplatform.hosts";
    private static final String KEY_TOOLSPLATFORM_ITEMS_SUFFIX = "toolsplatform.items";

    //////////////////////////////////////////////////////////////////

    private static OperationManager instance;

    private OperationManager() {
        init();
    }

    private void init() {
        //read the properties files
        try {
            deployProps = new DeployProps(new FileInputStream(CONFIG_FILE_NAME), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        operatorProps = new DeployProps(OPERATOR_FILE_NAME, true);
        cmdProps = new DeployProps(CMD_FILE_NAME, true);

        initEnv();
        initHost();
        initOperators();
        initCmds();
    }

    private void initEnv() {
        List<String> envs = deployProps.getList(KEY_ENV_LIST);

        OperationEnv oEnv = null;
        for (String env : envs) {
            oEnv = new OperationEnv(env);

            deployEnvList.add(oEnv);
            deployEnvMap.put(oEnv.getCode(), oEnv);
        }

        patchRoot = deployProps.get(KEY_PATCH_ROOT, patchRoot);
        webserverRoot = deployProps.get(KEY_WEBSERVER_ROOT, webserverRoot);

    }

    private void initCmds() {
        cmdPrefix = cmdProps.get(KEY_CMD_SUFFIX, cmdPrefix);
    }

    private void initOperators() {
        List<String> operators = operatorProps.getList(KEY_OPERATOR_LIST);

        String password = null;
        String type = null;
        List<String> envs = null;
        Operator oper = null;
        for (String operator : operators) {
            password = operatorProps.get(operator + KEY_DOT + KEY_OPERATOR_PWD);
            type = operatorProps.get(operator + KEY_DOT + KEY_OPERATOR_TYPE);
            envs = operatorProps.getList(operator + KEY_DOT + KEY_OPERATOR_ENVS);

            oper = new Operator(operator, password, OperatorType.getTypeByCode(type));
            for (String env : envs) {
                if (deployEnvMap.containsKey(env)) {
                    oper.addEnv(env, deployEnvMap.get(env));
                }
            }

            operatorMap.put(operator, oper);
        }

        List<String> ipExps = operatorProps.getList(KEY_OPEN_IP_LIST);

        Pattern pattern = null;
        for (String ip : ipExps) {
            pattern = Pattern.compile(ip);

            openIPPatternMap.put(ip, pattern);
        }
    }

    private void initHost() {
        List<String> envHosts;
        OperationHost buildHost;
        OperationHost buildWebserverHost;
        List<String> buildDirs;
        List<String> buildWebserverDirs;
        String packageDir;

        OperationHost buildToolsplatformHost;
        List<String> buildToolsplatformDirs;
        String toolsplatformRoot;


        List<String> toolsPlatformEnvHosts;
        for (OperationEnv oEnv : deployEnvList) {
            envHosts = deployProps.getList(oEnv.getCode() + KEY_DOT + KEY_HOSTS_SUFFIX);

            OperationHost oHost = null;
            for (String host : envHosts) {
                oHost = new OperationHost(host, getDeployItems(host + KEY_DOT + KEY_ITEMS_SUFFIX));

                putInEnvHostMap(oEnv, oHost);
            }


            toolsPlatformEnvHosts = deployProps.getList(oEnv.getCode() + KEY_DOT + KEY_TOOLSPLATFORM_HOSTS_SUFFIX);
            for (String host : toolsPlatformEnvHosts) {
                oHost = new OperationHost(host, getDeployItems(host + KEY_DOT + KEY_TOOLSPLATFORM_ITEMS_SUFFIX));

                putInToolsEnvHostMap(oEnv, oHost);
            }


            buildHost = new OperationHost(deployProps.get(oEnv.getCode() + KEY_DOT + KEY_BUILD_HOST_SUFFIX));
            buildWebserverHost = new OperationHost(deployProps.get(oEnv.getCode() + KEY_DOT + KEY_BUILD_WEBSERVER_HOST_SUFFIX));
            buildDirs = deployProps.getList(oEnv.getCode() + KEY_DOT + KEY_BUILD_DIRS_SUFFIX);
            buildWebserverDirs = deployProps.getList(oEnv.getCode() + KEY_DOT + KEY_BUILD_WEBSERVER_DIRS_SUFFIX);
            packageDir = deployProps.get(oEnv.getCode() + KEY_DOT + KEY_PACKAGE_ROOT_SUFFIX);

            buildToolsplatformHost = new OperationHost(deployProps.get(oEnv.getCode() + KEY_DOT + KEY_BUILD_TOOLPLATFORM_HOSTS_SUFFIX));
            buildToolsplatformDirs = deployProps.getList(oEnv.getCode() + KEY_DOT + KEY_BUILD_TOOLPLATFORM_DIRS_SUFFIX);
            toolsplatformRoot = deployProps.get(oEnv.getCode() + KEY_DOT +KEY_TOOLSPLATFORM_PACKAGE_ROOT);

            buildHostMap.put(oEnv, buildHost);
            buildHostDirsMap.put(oEnv, buildDirs);
            packageDirsMap.put(oEnv, packageDir);
            buildWebserverHostMap.put(oEnv, buildWebserverHost);
            buildWebserverHostDirsMap.put(oEnv, buildWebserverDirs);

            buildToolsPlatformHostMap.put(oEnv, buildToolsplatformHost);
            buildToolsPlatformDirsMap.put(oEnv, buildToolsplatformDirs);
            toolsPlatformPackageDirsMap.put(oEnv, toolsplatformRoot);
        }
    }

    private void putInEnvHostMap(OperationEnv env, OperationHost host) {
        Map<String, OperationHost> hostMap = envHostMap.get(env);

        if (hostMap == null) {
            hostMap = new LinkedHashMap<String, OperationHost>();
            envHostMap.put(env, hostMap);
        }

        hostMap.put(host.getHostname(), host);
    }


    private void putInToolsEnvHostMap(OperationEnv env, OperationHost host) {
        Map<String, OperationHost> hostMap = envToolsplatformHostMap.get(env);

        if (hostMap == null) {
            hostMap = new LinkedHashMap<String, OperationHost>();
            envToolsplatformHostMap.put(env, hostMap);
        }

        hostMap.put(host.getHostname(), host);
    }

    private Map<String, DeployItem> getDeployItems(String key) {
        Map<String, DeployItem> returnValue = new LinkedHashMap<String, DeployItem>();

        List<String> items = deployProps.getList(key);
        DeployItem item = null;
        for (String itemKey : items) {
            item = DeployItem.parseDeployItem(itemKey);

            if (item != null) {
                returnValue.put(item.getItemKey(), item);
            }
        }

        return returnValue;
    }

    public static OperationManager get() {
        if (instance == null) {
            instance = new OperationManager();
        }

        return instance;
    }

    public String getPatchRoot() {
        return patchRoot;
    }

    public String getWebServerRoot() {
        return webserverRoot;
    }


    public boolean hasOperator(String operator) {
        return operatorMap.containsKey(operator);
    }

    public Operator getOperator(String operator) {
        return operatorMap.get(operator);
    }

    public String getCmdPrefix() {
        return cmdPrefix;
    }

    public boolean hasAccess(String ip) {
        boolean returnValue = false;

        for (Pattern p : openIPPatternMap.values()) {
            returnValue = returnValue || p.matcher(ip).find();

            if (returnValue) {
                break;
            }
        }

        return returnValue;
    }

    public Operation getOperation(OperationKey key) {
        return operationMap.get(key);
    }

    public OperationEnv getOperationEnv(String env) {
        return deployEnvMap.get(env);
    }

    public List<OperationEnv> getOperationEnvs() {
        return deployEnvList;
    }

    public Map<String, OperationHost> getHostsByEnv(OperationEnv env) {
        return envHostMap.get(env);
    }

    public Map<String, OperationHost> getToolsplatformHostsByEnv(OperationEnv env) {
        return envToolsplatformHostMap.get(env);
    }

    public OperationHost getBuildHostByEnv(OperationEnv env) {
        return buildHostMap.get(env);
    }

    public List<String> getBuildDirsByEnv(OperationEnv env) {
        return buildHostDirsMap.get(env);
    }

    public OperationHost getBuildWebserverHostByEnv(OperationEnv env) {
        return buildWebserverHostMap.get(env);
    }

    public List<String> getBuildWebserverDirsByEnv(OperationEnv env) {
        return buildWebserverHostDirsMap.get(env);
    }


    public OperationHost getBuildToolsPlatformHostByEnv(OperationEnv env) {
        return buildToolsPlatformHostMap.get(env);
    }

    public List<String> getBuildToolsPlatformDirsByEnv(OperationEnv env) {
        return buildToolsPlatformDirsMap.get(env);
    }


    public String getPackageDirByEnv(OperationEnv env) {
        return packageDirsMap.get(env);
    }

    public void putOperation(Operation oper) {
        operationMap.put(oper.getKey(), oper);
    }

    public void removeOperation(Operation oper) {
        operationMap.remove(oper.getKey());
    }

    public Map<String, Operation> getOperationsByEnv(OperationEnv env) {
        Map<String, Operation> opers = new HashMap<String, Operation>();

        for (OperationKey key : operationMap.keySet()) {
            if (key.getEnv().equals(env)) {
                opers.put(key.getOperName() + "." + key.getArgs(), operationMap.get(key));
            }
        }

        return opers;
    }

    public Map<OperationEnv, OperationHost> getBuildToolsPlatformHostMap() {
        return buildToolsPlatformHostMap;
    }

    public Map<OperationEnv, List<String>> getBuildToolsPlatformDirsMap() {
        return buildToolsPlatformDirsMap;
    }

    public Map<OperationEnv, String> getToolsPlatformPackageDirsMap() {
        return toolsPlatformPackageDirsMap;
    }

    public String toString() {
        StringBuffer strBuffer = new StringBuffer();

        strBuffer.append("deployProps=[").append(deployProps).append("]\n");
        strBuffer.append("deployEnvList=[").append(deployEnvList).append("]\n");
        strBuffer.append("envHostMap=[").append(envHostMap).append("]\n");
        strBuffer.append("operationMap=[").append(operationMap).append("]\n");
        strBuffer.append("buildHostMap=[").append(buildHostMap).append("]\n");
        strBuffer.append("buildHostDirsMap=[").append(buildHostDirsMap).append("]\n");
        strBuffer.append("buildToolsPlatformHostMap=[").append(buildToolsPlatformHostMap).append("]\n");
        strBuffer.append("buildToolsPlatformDirsMap=[").append(buildToolsPlatformDirsMap).append("]\n");
        strBuffer.append("toolsPlatformPackageDirsMap=[").append(toolsPlatformPackageDirsMap).append("]\n");
        return strBuffer.toString();
    }

    public String getToolsPlatformPackageDirByEnv(OperationEnv operationEnv,String app) {
        return toolsPlatformPackageDirsMap.get(operationEnv)+"/"+app;
    }
}
