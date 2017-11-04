<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>
        <s:text name="def.webapp.title"/>
    </title>
    <link href="/include/theme/default.css" rel="stylesheet" type="text/css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="body-default">
<table width="100%" height="100%" border="0" cellpadding="2" cellspacing="0">
  <tr>
    <td height="40%" align="center" valign="bottom"> <s:if test="loginStatus != 0 && loginStatus != null"><font color="#FF0000"><b><i><s:text name="def.login.error.%{loginStatus}"/></i></b></font></s:if> 
      <s:else><b><i><s:text name="def.login.welcome"/></i></b></s:else> <br>
      <br>
    </td>
  </tr>
  <tr> 
    <td align="center" valign="top">
	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
	    <form action="/login.cgi" method="post" name="loginForm" id="loginForm">
        <tr class="toolbar" align="center"> 
          <td>
			<s:text name="def.login.loginname.prompt"/> 
			<input name="loginName" type="text" id="loginName" size="16" maxlength="16">
			<s:text name="def.login.password.prompt"/> 
			<input name="password" type="password" id="password" size="16" maxlength="16">
			<input type="submit" name="lk" value="Login">
		  </td>
        </tr>
		</form>
      </table> 
	</td>
  </tr>
  <tr class="footer"> 
    <td height="24" colspan="2" align="center"><b><i><s:text name="def.webapp.footer"/></i></b></td>
  </tr>
</table>
</body>
</html>