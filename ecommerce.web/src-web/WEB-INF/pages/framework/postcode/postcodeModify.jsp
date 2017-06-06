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
<script src="/resources/js/melfood/framework/postcodemanager.js?ver=<%=Ctx.releaseVersion%>"></script>
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
                         </colgroup>  
                         <tr>
                              <td class="label">State</td>
                              <td class="value"><span style="color: #dc0a5e;font-weight: bold;">${postcode.stateLabel}</span></td>
                         </tr>                         
                         <tr>
                              <td class="label">Suburb</td>
                              <td class="value"><span style="color: #d5d500;font-weight: bold;">${postcode.suburb}</span></td>
                         </tr>                         
                         <tr>
                              <td class="label">Postcode</td>
                              <td class="value"><span style="color: #f89e1f;font-weight: bold;">${postcode.postcode}</span></td>
                         </tr>                         
                         <tr>
                              <td class="label">Longitude / Latitude</td>
                              <td class="value"><input class="form-control" type="text" id="longitude" name="longitude" value='${postcode.longitude}' placeholder="Longitude"/></td>
                              <td class="value"><input class="form-control" type="text" id="latitude" name="latitude" value='${postcode.latitude}' placeholder="Latitude"/></td>
                         </tr>
                         <tr><td class="metavalue" colspan="3">Creator : ${postcode.creator}, Create Time : ${postcode.createDatetime}, Modify Time : ${postcode.modifyDatetime}</td></tr>                          
                    </table>
                    <input type="hidden" name="postcodeId" id="postcodeId" value="${postcode.postcodeId}"/>                    
                    <input type="hidden" name="state" id="state" value="${postcode.state}"/>                    
                    <input type="hidden" name="suburb" id="suburb" value="${postcode.suburb}"/>
                    <input type="hidden" name="postcode" id="postcode" value="${postcode.postcode}"/>
               </td>
          </tr>
          <tr><td colspan="4">&nbsp;</td></tr>
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
          var _POSTCODE_ID = "<c:out value="${postcode.postcodeId}"/>";
          var _SUBURB = "${postcode.suburb}";
          var _POSTCODE = "${postcode.postcode}";
          var ACTION_MODE = "MODIFY";
     </script>      
</body>
</html>