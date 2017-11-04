<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>
        <s:text name="def.webapp.title"/>
    </title>
    <link href="/include/theme/default.css" rel="stylesheet" type="text/css">
    <script language="javascript" type="text/javascript" src="/include/javascript/common.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@include file="tile-refresh.jsp"%>
</head>
<body class="body-default">
<table width="100%" height="100%" border="0" cellpadding="2" cellspacing="0">
  <%@include file="tile-nav-operation.jsp"%>
  <tr valign="top"> 
    <td colspan="2" align="center"><br>
        <s:if test="%{operation == null}">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <form name="buildForm" method="post" action="?env=${env}&oper=${oper}&args=${args}">
                    <tr>
                        <td>
                            <table width="100%" border="1" cellspacing="0" cellpadding="1">
                                <tr class="toolbar">
                                    <td width="25%" align="right">请选择编译视图的根目录：</td>
                                    <td><select name="buildDir">
                                        <s:iterator value="buildDirs" id="dir">
                                            <option id="${dir}">${dir}</option>
                                        </s:iterator>
                                    </select> &nbsp;&nbsp;&nbsp; <input name="version" type="checkbox" id="version"
                                                                        value="yes" checked>进行版本编译。
                                    </td>
                                    <td width="25%" align="center">
                                        <input name="exec" type="hidden" value="refresh">
                                        <input name="refresh" type="button" id="refresh"
                                               value='<s:text name="def.exec.refresh.name"/>'
                                               onClick="submitOperation(document.buildForm, 'refresh')">
                                        <input name="preview" type="button" id="preview"
                                               value='<s:text name="def.exec.preview.name"/>'
                                               onClick="submitOperation(document.buildForm, 'preview')">
                                    </td>
                                </tr>
                            </table>
                            <%@ include file="tile-hostselect.jsp" %>
                        </td>
                    </tr>
                </form>
            </table>
            <br>
        </s:if> 
      <%@include file="tile-hostlist.jsp"%>
	</td>
  </tr>
  <tr class="footer"> 
    <td height="24" colspan="2" align="center"><b><i><s:text name="def.webapp.footer"/></i></b></td>
  </tr>
</table>
</body>
</html>