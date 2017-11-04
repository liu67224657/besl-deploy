  <tr class="header"> 
    <td width="100%" height="24" colspan="2" align="right">
	<s:text name="def.login.operator.prompt"><s:param><s:text name="def.operator.%{operator.type.code}.name"/></s:param></s:text> | 
	<a href="/logout.cgi"><s:text name="def.logout.label"/></a> &nbsp;
	</td>
  </tr>
  <tr class="navigation"> 
    <td height="24" colspan="2" nowrap>
	  <s:text name="def.nav.welcome"/> <a href="/envlist.cgi"><s:text name="def.nav.home"/></a>
	  <s:text name="def.nav.split"/> <a href="/operationlist.cgi?env=${operationEnv.code}"><s:text name="def.%{operationEnv.getCode()}.name"/></a> 
      <s:text name="def.nav.split"/> <s:text name="def.%{oper}.%{args}.descript"/> 
	</td>
  </tr>