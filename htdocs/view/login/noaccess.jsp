<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title><s:text name="def.webapp.title"/></title>
<link href="/include/theme/default.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
<body class="body-default">
<table width="100%" height="100%" border="0" cellpadding="2" cellspacing="0">
  <tr class="header"> 
    <td width="100%" height="24" colspan="2" align="right">
	<s:text name="def.login.operator.prompt"><s:param><s:text name="def.operator.%{operator.type.code}.name"/></s:param></s:text> | 
	<a href="/logout.cgi"><s:text name="def.logout.label"/></a> &nbsp;
	</td>
  </tr>
  <tr class="navigation"> 
    <td height="24" colspan="2">
	  <s:text name="def.nav.welcome"/> <s:text name="def.nav.home"/>
	</td>
  </tr>
  <tr> 
    <td align="center"><font color="#FF0000"><b>
	  <s:text name="def.login.noaccess">
          <s:param ><s:text name="def.%{env}.name" /> </s:param>
          <s:param ><s:text name="def.exec.%{execType.code}.name" /> </s:param>
      </s:text></b></font>
	  <br>
	  <br>
      <br>
      <a href="javascript:history.back()"><s:text name="def.login.relogin.back"/></a> | <a href="/logout.cgi"><s:text name="def.login.relogin.welcome"/></a>
	</td>
  </tr>
  <tr class="footer"> 
    <td height="24" colspan="2" align="center"><b><i><s:text name="def.webapp.footer"/></i></b></td>
  </tr>
</table>
</body>
</html>