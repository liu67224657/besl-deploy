<s:if test="%{operation != null}">
    <table width="100%" border="0" cellspacing="0" cellpadding="1">
        <tr>
            <td>
       <table width="100%" border="1" cellspacing="0" cellpadding="1">
        <tr class="toolbar">
		  <td width="20%" align="right"><s:text name="def.oper.name.title"/></td>
          <td width="30%"><b><s:text name="def.%{oper}.%{args}.name"/></b></td> 
          <td width="20%" align="right"><s:text name="def.oper.status.title"/></td>
		  <td width="30%"> <b><i><s:text name="def.%{operation.status.code}.name"/></i></b></td>
        </tr>
        <tr class="toolbar">
          <td width="20%" align="right"><s:text name="def.oper.runtype.title"/></td>
          <td width="30%"> <b><i><s:text name="def.%{operation.operType.code}.name"/></i></b></td>
          <td width="20%" align="right"><s:text name="def.oper.result.title"/></td>
          <td width="30%"> <s:property value="operation.runResult"/> </td>
        </tr>
        <tr class="toolbar"> 
          <td width="20%" align="right"><s:text name="def.oper.command.title"/></td>
          <td width="30%"> <s:property value="operation.command.operCmd"/> </td>
          <td width="20%" align="right"><s:text name="def.oper.initdate.title"/></td>
          <td width="30%"> <s:text name="global.format.date"> <s:param value="operation.initDate"></s:param> 
            </s:text> </td>
        </tr>
        <tr class="toolbar"> 
          <td align="right"><s:text name="def.oper.startdate.title"/></td>
          <td> <s:text name="global.format.date"> <s:param value="operation.startDate"></s:param> 
            </s:text> </td>
          <td align="right"><s:text name="def.oper.exitdate.title"/></td>
          <td> <s:text name="global.format.date"> <s:param value="operation.exitDate"></s:param> 
            </s:text> </td>
        </tr>
      </table>
            </td>
        </tr>
    </table> 
	<table width="100%" border="0" cellspacing="0" cellpadding="1">
	  <tr class="toolbar">      
		<td height="24" align="right">
			<a href="?env=${env}&oper=${oper}&args=${args}&exec=refresh"><s:text name="def.exec.refresh.name"/></a> 
	    </td>
        <td height="24" width="240" align="center">
      		<s:if test="%{operation.status.preRun}"><a href="?env=${env}&oper=${oper}&args=${args}&exec=execute"><s:text name="def.exec.execute.name"/></a></s:if>
	    </td>
        <td height="24">
			<s:if test="operation.status.exit || operation.status.preRun"><a href="javascript:if(confirm('Do you want to remove the operation log?')){this.location='?env=${env}&oper=${oper}&args=${args}&exec=remove'}"><s:text name="def.exec.remove.name"/></a></s:if>
	    </td>
      </tr>
    </table>
    <table width="100%" border="1" cellspacing="0" cellpadding="1">
        <tr>
            <td align="center">
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
				  <tr class="runtstatus-title">				    
          			<td height="24" align="center" nowrap><s:text name="def.exe.hostname.title"/></td>
                    <td height="24" align="center" nowrap><s:text name="def.exe.exestatus.title"/></td>
                    <td height="24" align="center" nowrap><s:text name="def.exe.exeresult.title"/></td>
                    <td height="24"><s:text name="def.exe.output.title"/></td>
                    <td height="24"><s:text name="def.exe.error.title"/></td>
                   </tr>
                    <s:iterator value="operation.commands" id="sshcmd">
						<s:set name="classType" value="%{#sshcmd.getStatus().getCode()}"/>
						<s:if test="%{#sshcmd.getStatus().isExit() && #sshcmd.getExecResult() != 0}">
							<s:set name="classType" value="%{#classType + '-error'}"/>
						</s:if>
						<s:elseif test="%{#sshcmd.getStatus().isExit() && #sshcmd.getExecResult() == 0}">
							<s:set name="classType" value="%{#classType + '-succ'}"/>
						</s:elseif>
                        <tr class='runstatus-<s:property value="%{classType}"/>'>
                            <td align="center" nowrap>
                                <s:property value="%{#sshcmd.getOperHost().getHostname()}"/>
                            </td>
                            <td align="center" nowrap>
								<b><i><s:text name="def.%{#sshcmd.getStatus().getCode()}.name"/></i></b>
                            </td>
                            <td align="center" nowrap>
                                <s:property value="%{#sshcmd.getExecResult()}"/>
                                <br>
                                <s:property value="%{#sshcmd.getExecuteTime()}"/>ms
                            </td>
                            <td><textarea name='<s:property value="%{#sshcmd.getOperHost().getHostname()}"/>_C'
                                          cols="40" rows="3" readonly style="color:green"><s:property value="%{#sshcmd.getExecResultMsg()}"/></textarea></td>
                            <td><textarea name='<s:property value="%{#sshcmd.getOperHost().getHostname()}"/>_E'
                                          cols="40" rows="3" readonly style="color:red"><s:property value="%{#sshcmd.getErrorResultMsg()}"/></textarea></td>
                        </tr>
                    </s:iterator>
                </table>
            </td>
        </tr>
    </table>
</s:if>