<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title><s:text name="def.webapp.title"/></title>
    <link href="/include/theme/default.css" rel="stylesheet"
          type="text/css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Refresh"
          content='300;url=?env=<s:property value="env"/>'>
</head>
<body class="body-default">
<table width="100%" height="100%" border="0" cellpadding="2"
       cellspacing="0">
<tr class="header">
    <td width="100%" height="24" colspan="2" align="right">
        <s:text name="def.login.operator.prompt">
            <s:param>
                <s:text name="def.operator.%{operator.type.code}.name"/>
            </s:param>
        </s:text>
        |
        <a href="/logout.cgi"><s:text name="def.logout.label"/>
        </a> &nbsp;
    </td>
</tr>
<tr class="navigation">
    <td height="24" colspan="2">
        <s:text name="def.nav.welcome"/>
        <a href="/envlist.cgi"><s:text name="def.nav.home"/>
        </a>
        <s:text name="def.nav.split"/>
        <s:text name="def.%{operationEnv.getCode()}.name"/>
    </td>
</tr>
<tr>
<td align="center">
<br>
<table width="98%" border="1" cellspacing="0" cellpadding="1">
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.ops.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('ops.sync') != null}">
                        <s:text
                                name="def.%{operations.get('ops.sync').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href="/ops.cgi?env=${operationEnv.code}&oper=ops&args=sync">
                        <s:text name="def.ops.sync.name"/>
                    </a> -
                    <s:text name="def.ops.sync.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('ops.deploy') != null}">
                        <s:text
                                name="def.%{operations.get('ops.deploy').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/ops.cgi?env=${operationEnv.code}&oper=ops&args=deploy'>
                        <s:text name="def.ops.deploy.name"/>
                    </a> -
                    <s:text name="def.ops.deploy.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.build.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('build.version') != null}">
                        <s:text
                                name="def.%{operations.get('build.version').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/build.cgi?env=${operationEnv.code}&oper=build&args=version'>
                        <s:text name="def.build.version.name"/>
                    </a> -
                    <s:text name="def.build.version.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.wsbuild.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('wsbuild.version') != null}">
                        <s:text
                                name="def.%{operations.get('wsbuild.version').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/wsbuild.cgi?env=${operationEnv.code}&oper=wsbuild&args=version'>
                        <s:text name="def.wsbuild.version.name"/>
                    </a> -
                    <s:text name="def.wsbuild.version.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
<td width="25%" align="right" class="toolbar">
    <s:text name="def.release.prompt"/>
</td>
<td>
<table width="100%" cellspacing="1" cellpadding="1">
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if test="%{operations.get('release.stoprelease') != null}">
            <s:text
                    name="def.%{operations.get('release.stoprelease').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=stoprelease'>
            <s:text name="def.release.stoprelease.name"/>
        </a> -
        <s:text name="def.release.stoprelease.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if
                test="%{operations.get('release.startrelease') != null}">
            <s:text
                    name="def.%{operations.get('release.startrelease').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=startrelease'>
            <s:text name="def.release.startrelease.name"/>
        </a> -
        <s:text name="def.release.startrelease.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if
                test="%{operations.get('release.switchstoppage') != null}">
            <s:text
                    name="def.%{operations.get('release.switchstoppage').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=switchstoppage'>
            <s:text name="def.release.switchstoppage.name"/>
        </a> -
        <s:text name="def.release.switchstoppage.descript"/>
    </td>
</tr>

<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if
                test="%{operations.get('release.switchrelease') != null}">
            <s:text
                    name="def.%{operations.get('release.switchrelease').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=switchrelease'>
            <s:text name="def.release.switchrelease.name"/>
        </a> -
        <s:text name="def.release.switchrelease.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if
                test="%{operations.get('release.switchlive') != null}">
            <s:text
                    name="def.%{operations.get('release.switchlive').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=switchlive'>
            <s:text name="def.release.switchlive.name"/>
        </a> -
        <s:text name="def.release.switchlive.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        &nbsp;
    </td>
    <td>
        &nbsp;
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if test="%{operations.get('release.stopall') != null}">
            <s:text
                    name="def.%{operations.get('release.stopall').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=stopall'>
            <s:text name="def.release.stopall.name"/>
        </a> -
        <s:text name="def.release.stopall.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if test="%{operations.get('release.startall') != null}">
            <s:text
                    name="def.%{operations.get('release.startall').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=startall'>
            <s:text name="def.release.startall.name"/>
        </a> -
        <s:text name="def.release.startall.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if test="%{operations.get('release.restartall') != null}">
            <s:text
                    name="def.%{operations.get('release.restartall').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=restartall'>
            <s:text name="def.release.restartall.name"/>
        </a> -
        <s:text name="def.release.restartall.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        &nbsp;
    </td>
    <td>
        &nbsp;
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if
                test="%{operations.get('release.stopstaticweb') != null}">
            <s:text
                    name="def.%{operations.get('release.stopstaticweb').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=stopstaticweb'>
            <s:text name="def.release.stopstaticweb.name"/>
        </a> -
        <s:text name="def.release.stopstaticweb.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if
                test="%{operations.get('release.startstaticweb') != null}">
            <s:text
                    name="def.%{operations.get('release.startstaticweb').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=startstaticweb'>
            <s:text name="def.release.startstaticweb.name"/>
        </a> -
        <s:text name="def.release.startstaticweb.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if
                test="%{operations.get('release.restartstaticweb') != null}">
            <s:text
                    name="def.%{operations.get('release.restartstaticweb').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=restartstaticweb'>
            <s:text name="def.release.restartstaticweb.name"/>
        </a> -
        <s:text name="def.release.restartstaticweb.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        &nbsp;
    </td>
    <td>
        &nbsp;
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if
                test="%{operations.get('release.stopdynamicweb') != null}">
            <s:text
                    name="def.%{operations.get('release.stopdynamicweb').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=stopdynamicweb'>
            <s:text name="def.release.stopdynamicweb.name"/>
        </a> -
        <s:text name="def.release.stopdynamicweb.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if
                test="%{operations.get('release.startdynamicweb') != null}">
            <s:text
                    name="def.%{operations.get('release.startdynamicweb').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=startdynamicweb'>
            <s:text name="def.release.startdynamicweb.name"/>
        </a> -
        <s:text name="def.release.startdynamicweb.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if
                test="%{operations.get('release.restartdynamicweb') != null}">
            <s:text
                    name="def.%{operations.get('release.restartdynamicweb').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=restartdynamicweb'>
            <s:text name="def.release.restartdynamicweb.name"/>
        </a> -
        <s:text name="def.release.restartdynamicweb.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        &nbsp;
    </td>
    <td>
        &nbsp;
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if test="%{operations.get('release.stopweb') != null}">
            <s:text
                    name="def.%{operations.get('release.stopweb').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=stopweb'>
            <s:text name="def.release.stopweb.name"/>
        </a> -
        <s:text name="def.release.stopweb.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if test="%{operations.get('release.startweb') != null}">
            <s:text
                    name="def.%{operations.get('release.startweb').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=startweb'>
            <s:text name="def.release.startweb.name"/>
        </a> -
        <s:text name="def.release.startweb.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if test="%{operations.get('release.restartweb') != null}">
            <s:text
                    name="def.%{operations.get('release.restartweb').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=restartweb'>
            <s:text name="def.release.restartweb.name"/>
        </a> -
        <s:text name="def.release.restartweb.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        &nbsp;
    </td>
    <td>
        &nbsp;
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if test="%{operations.get('release.stopbesl') != null}">
            <s:text
                    name="def.%{operations.get('release.stopbesl').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=stopbesl'>
            <s:text name="def.release.stopbesl.name"/>
        </a> -
        <s:text name="def.release.stopbesl.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if test="%{operations.get('release.startbesl') != null}">
            <s:text
                    name="def.%{operations.get('release.startbesl').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=startbesl'>
            <s:text name="def.release.startbesl.name"/>
        </a> -
        <s:text name="def.release.startbesl.descript"/>
    </td>
</tr>
<tr>
    <td width="100" align="center" class="operation-runstatus">
        <s:if test="%{operations.get('release.restartbesl') != null}">
            <s:text
                    name="def.%{operations.get('release.restartbesl').getStatus().getCode()}.name"/>
        </s:if>
    </td>
    <td>
        <a
                href='/release.cgi?env=${operationEnv.code}&oper=release&args=restartbesl'>
            <s:text name="def.release.restartbesl.name"/>
        </a> -
        <s:text name="def.release.restartbesl.descript"/>
    </td>
</tr>
</table>
</td>
</tr>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.deploy.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('deploy.backup') != null}">
                        <s:text
                                name="def.%{operations.get('deploy.backup').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/deploy.cgi?env=${operationEnv.code}&oper=deploy&args=backup'>
                        <s:text name="def.deploy.backup.name"/>
                    </a> -
                    <s:text name="def.deploy.backup.descript"/>
                </td>
            </tr>
            <!--
                                    <tr>
										<td width="100" align="center" class="operation-runstatus">
											<s:if
												test="%{operations.get('deploy.backuptodeploy') != null}">
												<s:text
													name="def.%{operations.get('deploy.backuptodeploy').getStatus().getCode()}.name" />
											</s:if>
										</td>
										<td>
											<a
												href='/deploy.cgi?env=${operationEnv.code}&oper=deploy&args=backuptodeploy'>
												<s:text name="def.deploy.backuptodeploy.name" />
											</a> -
											<s:text name="def.deploy.backuptodeploy.descript" />
										</td>
									</tr>
									-->
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('deploy.deploy') != null}">
                        <s:text
                                name="def.%{operations.get('deploy.deploy').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/deploy.cgi?env=${operationEnv.code}&oper=deploy&args=deploy'>
                        <s:text name="def.deploy.deploy.name"/>
                    </a> -
                    <s:text name="def.deploy.deploy.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('deploy.rollback') != null}">
                        <s:text
                                name="def.%{operations.get('deploy.rollback').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/deploy.cgi?env=${operationEnv.code}&oper=deploy&args=rollback'>
                        <s:text name="def.deploy.rollback.name"/>
                    </a> -
                    <s:text name="def.deploy.rollback.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('deploy.commit') != null}">
                        <s:text
                                name="def.%{operations.get('deploy.commit').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/deploy.cgi?env=${operationEnv.code}&oper=deploy&args=commit'>
                        <s:text name="def.deploy.commit.name"/>
                    </a> -
                    <s:text name="def.deploy.commit.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('deploy.restore') != null}">
                        <s:text
                                name="def.%{operations.get('deploy.restore').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/deploy.cgi?env=${operationEnv.code}&oper=deploy&args=restore'>
                        <s:text name="def.deploy.restore.name"/>
                    </a> -
                    <s:text name="def.deploy.restore.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.wsdeploy.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('wsdeploy.backup') != null}">
                        <s:text
                                name="def.%{operations.get('wsdeploy.backup').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/wsdeploy.cgi?env=${operationEnv.code}&oper=wsdeploy&args=backup'>
                        <s:text name="def.wsdeploy.backup.name"/>
                    </a> -
                    <s:text name="def.wsdeploy.backup.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('wsdeploy.deploy') != null}">
                        <s:text
                                name="def.%{operations.get('wsdeploy.deploy').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/wsdeploy.cgi?env=${operationEnv.code}&oper=wsdeploy&args=deploy'>
                        <s:text name="def.wsdeploy.deploy.name"/>
                    </a> -
                    <s:text name="def.wsdeploy.deploy.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('wsdeploy.rollback') != null}">
                        <s:text
                                name="def.%{operations.get('wsdeploy.rollback').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/wsdeploy.cgi?env=${operationEnv.code}&oper=wsdeploy&args=rollback'>
                        <s:text name="def.wsdeploy.rollback.name"/>
                    </a> -
                    <s:text name="def.wsdeploy.rollback.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('wsdeploy.commit') != null}">
                        <s:text
                                name="def.%{operations.get('wsdeploy.commit').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/wsdeploy.cgi?env=${operationEnv.code}&oper=wsdeploy&args=commit'>
                        <s:text name="def.wsdeploy.commit.name"/>
                    </a> -
                    <s:text name="def.wsdeploy.commit.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('wsdeploy.restore') != null}">
                        <s:text
                                name="def.%{operations.get('wsdeploy.restore').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/wsdeploy.cgi?env=${operationEnv.code}&oper=wsdeploy&args=restore'>
                        <s:text name="def.wsdeploy.restore.name"/>
                    </a> -
                    <s:text name="def.wsdeploy.restore.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.hotdeploy.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('hotdeploy.sync') != null}">
                        <s:text
                                name="def.%{operations.get('hotdeploy.sync').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/hotdeploy.cgi?env=${operationEnv.code}&oper=hotdeploy&args=sync'>
                        <s:text name="def.hotdeploy.sync.name"/>
                    </a> -
                    <s:text name="def.hotdeploy.sync.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('hotdeploy.deploy') != null}">
                        <s:text
                                name="def.%{operations.get('hotdeploy.deploy').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/hotdeploy.cgi?env=${operationEnv.code}&oper=hotdeploy&args=deploy'>
                        <s:text name="def.hotdeploy.deploy.name"/>
                    </a> -
                    <s:text name="def.hotdeploy.deploy.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.patch.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('patch.check') != null}">
                        <s:text
                                name="def.%{operations.get('patch.check').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/patch.cgi?env=${operationEnv.code}&oper=patch&args=check'>
                        <s:text name="def.patch.check.name"/>
                    </a> -
                    <s:text name="def.patch.check.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('patch.deploy') != null}">
                        <s:text
                                name="def.%{operations.get('patch.deploy').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/patch.cgi?env=${operationEnv.code}&oper=patch&args=deploy'>
                        <s:text name="def.patch.deploy.name"/>
                    </a> -
                    <s:text name="def.patch.deploy.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.wshotdeploy.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('wshotdeploy.sync') != null}">
                        <s:text
                                name="def.%{operations.get('wshotdeploy.sync').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/wshotdeploy.cgi?env=${operationEnv.code}&oper=wshotdeploy&args=sync'>
                        <s:text name="def.wshotdeploy.sync.name"/>
                    </a> -
                    <s:text name="def.wshotdeploy.sync.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('wshotdeploy.deploy') != null}">
                        <s:text
                                name="def.%{operations.get('wshotdeploy.deploy').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/wshotdeploy.cgi?env=${operationEnv.code}&oper=wshotdeploy&args=deploy'>
                        <s:text name="def.wshotdeploy.deploy.name"/>
                    </a> -
                    <s:text name="def.wshotdeploy.deploy.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('wshotdeploy.applya') != null}">
                        <s:text
                                name="def.%{operations.get('wshotdeploy.applya').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/wshotdeploy.cgi?env=${operationEnv.code}&oper=wshotdeploy&args=applya'>
                        <s:text name="def.wshotdeploy.applya.name"/>
                    </a> -
                    <s:text name="def.wshotdeploy.applya.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('wshotdeploy.applyt') != null}">
                        <s:text
                                name="def.%{operations.get('wshotdeploy.applyt').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/wshotdeploy.cgi?env=${operationEnv.code}&oper=wshotdeploy&args=applyt'>
                        <s:text name="def.wshotdeploy.applyt.name"/>
                    </a> -
                    <s:text name="def.wshotdeploy.applyt.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.newsdeploy.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('newsdeploy.sync') != null}">
                        <s:text
                                name="def.%{operations.get('newsdeploy.sync').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/newsdeploy.cgi?env=${operationEnv.code}&oper=newsdeploy&args=sync'>
                        <s:text name="def.newsdeploy.sync.name"/>
                    </a> -
                    <s:text name="def.newsdeploy.sync.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('newsdeploy.deploy') != null}">
                        <s:text
                                name="def.%{operations.get('newsdeploy.deploy').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/newsdeploy.cgi?env=${operationEnv.code}&oper=newsdeploy&args=deploy'>
                        <s:text name="def.newsdeploy.deploy.name"/>
                    </a> -
                    <s:text name="def.newsdeploy.deploy.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>

<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.tpbuild.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('tpbuild.version') != null}">
                        <s:text
                                name="def.%{operations.get('tpbuild.version').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpbuild.cgi?env=${operationEnv.code}&oper=tpbuild&args=version'>
                        <s:text name="def.tpbuild.version.name"/>
                    </a> -
                    <s:text name="def.tpbuild.version.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.tpdeploy.wikipage.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('stop.wikipage') != null}">
                        <s:text
                                name="def.%{operations.get('stop.wikipage').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpserver.cgi?env=${operationEnv.code}&oper=stop&args=wikipage'>
                        <s:text name="def.stop.wikipage.name"/>
                    </a> -
                    <s:text name="def.stop.wikipage.descript"/>
                </td>
            </tr>

            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('backup.wikipage') != null}">
                        <s:text
                                name="def.%{operations.get('backup.wikipage').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpdeploy.cgi?env=${operationEnv.code}&oper=backup&args=wikipage'>
                        <s:text name="def.backup.wikipage.name"/>
                    </a> -
                    <s:text name="def.backup.wikipage.descript"/>
                </td>
            </tr>

            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('deploy.wikipage') != null}">
                        <s:text
                                name="def.%{operations.get('deploy.wikipage').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpdeploy.cgi?env=${operationEnv.code}&oper=deploy&args=wikipage'>
                        <s:text name="def.deploy.wikipage.name"/>
                    </a> -
                    <s:text name="def.deploy.wikipage.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('start.wikipage') != null}">
                        <s:text
                                name="def.%{operations.get('start.wikipage').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpserver.cgi?env=${operationEnv.code}&oper=start&args=wikipage'>
                        <s:text name="def.start.wikipage.name"/>
                    </a> -
                    <s:text name="def.start.wikipage.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>

<%--marticle--%>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.tpdeploy.marticle.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('stop.marticle') != null}">
                        <s:text
                                name="def.%{operations.get('stop.marticle').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpserver.cgi?env=${operationEnv.code}&oper=stop&args=marticle'>
                        <s:text name="def.stop.marticle.name"/>
                    </a> -
                    <s:text name="def.stop.marticle.descript"/>
                </td>
            </tr>

            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('backup.marticle') != null}">
                        <s:text
                                name="def.%{operations.get('backup.marticle').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpdeploy.cgi?env=${operationEnv.code}&oper=backup&args=marticle'>
                        <s:text name="def.backup.marticle.name"/>
                    </a> -
                    <s:text name="def.backup.marticle.descript"/>
                </td>
            </tr>

            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('deploy.marticle') != null}">
                        <s:text
                                name="def.%{operations.get('deploy.marticle').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpdeploy.cgi?env=${operationEnv.code}&oper=deploy&args=marticle'>
                        <s:text name="def.deploy.marticle.name"/>
                    </a> -
                    <s:text name="def.deploy.marticle.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('start.marticle') != null}">
                        <s:text
                                name="def.%{operations.get('start.marticle').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpserver.cgi?env=${operationEnv.code}&oper=start&args=marticle'>
                        <s:text name="def.start.marticle.name"/>
                    </a> -
                    <s:text name="def.start.marticle.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>

<%--cmsimage--%>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.tpdeploy.cmsimage.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('stop.cmsimage') != null}">
                        <s:text
                                name="def.%{operations.get('stop.cmsimage').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpserver.cgi?env=${operationEnv.code}&oper=stop&args=cmsimage'>
                        <s:text name="def.stop.cmsimage.name"/>
                    </a> -
                    <s:text name="def.stop.cmsimage.descript"/>
                </td>
            </tr>

            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('backup.cmsimage') != null}">
                        <s:text
                                name="def.%{operations.get('backup.cmsimage').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpdeploy.cgi?env=${operationEnv.code}&oper=backup&args=cmsimage'>
                        <s:text name="def.backup.cmsimage.name"/>
                    </a> -
                    <s:text name="def.backup.cmsimage.descript"/>
                </td>
            </tr>

            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('deploy.cmsimage') != null}">
                        <s:text
                                name="def.%{operations.get('deploy.cmsimage').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpdeploy.cgi?env=${operationEnv.code}&oper=deploy&args=cmsimage'>
                        <s:text name="def.deploy.cmsimage.name"/>
                    </a> -
                    <s:text name="def.deploy.cmsimage.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('start.cmsimage') != null}">
                        <s:text
                                name="def.%{operations.get('start.cmsimage').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpserver.cgi?env=${operationEnv.code}&oper=start&args=cmsimage'>
                        <s:text name="def.start.cmsimage.name"/>
                    </a> -
                    <s:text name="def.start.cmsimage.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>


<%--search--%>
<tr>
    <td width="25%" align="right" class="toolbar">
        <s:text name="def.tpdeploy.cmsimage.prompt"/>
    </td>
    <td>
        <table width="100%" cellspacing="1" cellpadding="1">
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('stop.search') != null}">
                        <s:text
                                name="def.%{operations.get('stop.search').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpserver.cgi?env=${operationEnv.code}&oper=stop&args=search'>
                        <s:text name="def.stop.search.name"/>
                    </a> -
                    <s:text name="def.stop.search.descript"/>
                </td>
            </tr>

            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('backup.search') != null}">
                        <s:text
                                name="def.%{operations.get('backup.search').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpdeploy.cgi?env=${operationEnv.code}&oper=backup&args=search'>
                        <s:text name="def.backup.search.name"/>
                    </a> -
                    <s:text name="def.backup.search.descript"/>
                </td>
            </tr>

            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('deploy.search') != null}">
                        <s:text
                                name="def.%{operations.get('deploy.search').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpdeploy.cgi?env=${operationEnv.code}&oper=deploy&args=search'>
                        <s:text name="def.deploy.search.name"/>
                    </a> -
                    <s:text name="def.deploy.search.descript"/>
                </td>
            </tr>
            <tr>
                <td width="100" align="center" class="operation-runstatus">
                    <s:if test="%{operations.get('start.search') != null}">
                        <s:text
                                name="def.%{operations.get('start.search').getStatus().getCode()}.name"/>
                    </s:if>
                </td>
                <td>
                    <a
                            href='/tpserver.cgi?env=${operationEnv.code}&oper=start&args=search'>
                        <s:text name="def.start.search.name"/>
                    </a> -
                    <s:text name="def.start.search.descript"/>
                </td>
            </tr>
        </table>
    </td>
</tr>

<%--webcache--%>
<tr>
        <td width="25%" align="right" class="toolbar">
            <s:text name="def.tpdeploy.webcache.prompt"/>
        </td>
        <td>
            <table width="100%" cellspacing="1" cellpadding="1">
                <tr>
                    <td width="100" align="center" class="operation-runstatus">
                        <s:if test="%{operations.get('stop.webcache') != null}">
                            <s:text
                                    name="def.%{operations.get('stop.webcache').getStatus().getCode()}.name"/>
                        </s:if>
                    </td>
                    <td>
                        <a
                                href='/tpserver.cgi?env=${operationEnv.code}&oper=stop&args=webcache'>
                            <s:text name="def.stop.webcache.name"/>
                        </a> -
                        <s:text name="def.stop.webcache.descript"/>
                    </td>
                </tr>

                <tr>
                    <td width="100" align="center" class="operation-runstatus">
                        <s:if test="%{operations.get('backup.webcache') != null}">
                            <s:text
                                    name="def.%{operations.get('backup.webcache').getStatus().getCode()}.name"/>
                        </s:if>
                    </td>
                    <td>
                        <a
                                href='/tpdeploy.cgi?env=${operationEnv.code}&oper=backup&args=webcache'>
                            <s:text name="def.backup.webcache.name"/>
                        </a> -
                        <s:text name="def.backup.webcache.descript"/>
                    </td>
                </tr>

                <tr>
                    <td width="100" align="center" class="operation-runstatus">
                        <s:if test="%{operations.get('deploy.webcache') != null}">
                            <s:text
                                    name="def.%{operations.get('deploy.webcache').getStatus().getCode()}.name"/>
                        </s:if>
                    </td>
                    <td>
                        <a
                                href='/tpdeploy.cgi?env=${operationEnv.code}&oper=deploy&args=webcache'>
                            <s:text name="def.deploy.webcache.name"/>
                        </a> -
                        <s:text name="def.deploy.webcache.descript"/>
                    </td>
                </tr>
                <tr>
                    <td width="100" align="center" class="operation-runstatus">
                        <s:if test="%{operations.get('start.webcache') != null}">
                            <s:text
                                    name="def.%{operations.get('start.webcache').getStatus().getCode()}.name"/>
                        </s:if>
                    </td>
                    <td>
                        <a
                                href='/tpserver.cgi?env=${operationEnv.code}&oper=start&args=webcache'>
                            <s:text name="def.start.webcache.name"/>
                        </a> -
                        <s:text name="def.start.webcache.descript"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

<%--activity--%>
<tr>
        <td width="25%" align="right" class="toolbar">
            <s:text name="def.tpdeploy.activity.prompt"/>
        </td>
        <td>
            <table width="100%" cellspacing="1" cellpadding="1">
                <tr>
                    <td width="100" align="center" class="operation-runstatus">
                        <s:if test="%{operations.get('stop.activity') != null}">
                            <s:text
                                    name="def.%{operations.get('stop.activity').getStatus().getCode()}.name"/>
                        </s:if>
                    </td>
                    <td>
                        <a
                                href='/tpserver.cgi?env=${operationEnv.code}&oper=stop&args=activity'>
                            <s:text name="def.stop.activity.name"/>
                        </a> -
                        <s:text name="def.stop.activity.descript"/>
                    </td>
                </tr>

                <tr>
                    <td width="100" align="center" class="operation-runstatus">
                        <s:if test="%{operations.get('backup.activity') != null}">
                            <s:text
                                    name="def.%{operations.get('backup.activity').getStatus().getCode()}.name"/>
                        </s:if>
                    </td>
                    <td>
                        <a
                                href='/tpdeploy.cgi?env=${operationEnv.code}&oper=backup&args=activity'>
                            <s:text name="def.backup.activity.name"/>
                        </a> -
                        <s:text name="def.backup.activity.descript"/>
                    </td>
                </tr>

                <tr>
                    <td width="100" align="center" class="operation-runstatus">
                        <s:if test="%{operations.get('deploy.activity') != null}">
                            <s:text
                                    name="def.%{operations.get('deploy.activity').getStatus().getCode()}.name"/>
                        </s:if>
                    </td>
                    <td>
                        <a
                                href='/tpdeploy.cgi?env=${operationEnv.code}&oper=deploy&args=activity'>
                            <s:text name="def.deploy.activity.name"/>
                        </a> -
                        <s:text name="def.deploy.activity.descript"/>
                    </td>
                </tr>
                <tr>
                    <td width="100" align="center" class="operation-runstatus">
                        <s:if test="%{operations.get('start.activity') != null}">
                            <s:text
                                    name="def.%{operations.get('start.activity').getStatus().getCode()}.name"/>
                        </s:if>
                    </td>
                    <td>
                        <a
                                href='/tpserver.cgi?env=${operationEnv.code}&oper=start&args=activity'>
                            <s:text name="def.start.activity.name"/>
                        </a> -
                        <s:text name="def.start.activity.descript"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

</td>
</tr>
<tr class="footer">
    <td height="24" colspan="2" align="center">
        <b><i><s:text name="def.webapp.footer"/>
        </i>
        </b>
    </td>
</tr>
</table>
</body>
</html>