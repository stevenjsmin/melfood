<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="/resources/js/melfood/admin/contractmgt.js?ver=<%=Ctx.releaseVersion%>"></script>
<script type="text/javascript">
$(document).ready(function() {
     
}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>
</head>

<body>
     <table>
          <tr>
               <td valign="top">
                    <table class="detail_table">
                         <colgroup>
                              <col width="250px" />
                              <col width="250px" />
                              <col width="250px" />
                              <col width="250px" />
                         </colgroup>       
                         <tr>
                              <td class="label"><span class="required">* </span>Seller :</td>
                              <td class="value"><c:out value="${cbxSeller}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr>                         
                         <tr>
                              <td class="label"><span class="required">* </span>Status</td>
                              <td class="value"><c:out value="${cbxContractStatus}" escapeXml="false"/></td>
                              <td></td>
                              <td></td>
                         </tr>                         
                         <tr>
                              <td class="label"><span class="required">* </span>Period :</td>
                              <td class="value" colspan="3" style="padding-left: 3px;">
                                   <table>
                                        <tr>
                                             <td><input type="text" id="contractStartDate" name="contractStartDate" value=''/></td>
                                             <td>~</td>
                                             <td><input type="text" id="contractEndDate" name="contractEndDate" value=''/></td>
                                        </tr>
                                   </table> 
                              </td>
                         </tr>                         
                         <tr>
                              <td class="label">Description</td>
                              <td class="value" colspan="2"><textarea class="form-control" rows="3" id="contractDescription" name="contractDescription"></textarea></td>
                              <td></td>
                         </tr>
                         <tr><td colspan="4">&nbsp;</td></tr>
                    </table>
               </td>
          </tr>
          <tr>
               <td>
                    <span class="subtitle">Attachment Files</span>
                    <hr class="subtitle"/>
                            첨부해야할 파일이 있는 경우 정보 등록을 완료후 등록해 주세요
               </td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr>
               <td>
                    <table class="action_button_table" width="100%">
                         <tr>
                              <td>
                                   <a href="javascript:goList();" class="btn btn-info">&nbsp;&nbsp; List &nbsp;&nbsp;</a>
                                   <a href="javascript:save();" class="btn btn-primary">Save</a>
                              </td>
                         </tr>
                    </table>
               </td>
          </tr>
          
     </table>
     
     <script type="text/javascript">
          var ACTION_MODE = "ADD";
     </script>     
</body>
</html>