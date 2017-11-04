              <br>
              <table width="100%" border="1" cellspacing="0" cellpadding="1">
                        <tr class="toolbar">
                          <td width="25%" align="right"><s:text name="def.operation.hosts.prompt"/></td>
                  <td>
                    <s:iterator value="formSelectHosts" id="host">
                        <input type="checkbox" name="hosts" value="${host.hostname}" checked>${host.hostname}
                        <s:if test="%{#host.deployItems != null}">-<s:text name="def.host.item.prompt"/>
                            <s:iterator value="#host.getDeployItems().values()" id="item">
                                <s:text name="def.item.%{#item.itemKey}.name"/>
                            </s:iterator>
                        </s:if>
                        <br>
                    </s:iterator>
                  </td>
                </tr>
              </table>