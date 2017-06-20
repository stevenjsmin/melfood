<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="/resources/js/melfood/framework/grouppurchase.js?ver=<%=Ctx.releaseVersion%>"></script>

    <script type="text/javascript">
        $(document).ready(function () {

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>

    <style>
    </style>
</head>


<body>
<table>
    <tr>
        <td>
            todo something...
        </td>
    </tr>
    <tr>
        <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
        <td>
            <table class="action_button_table" width="100%">
                <tr>
                    <td>
                        <a href="javascript:parent.closeGroupPurchaseRegistPopup();" class="btn btn-info">&nbsp;&nbsp; Close &nbsp;&nbsp;</a>
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