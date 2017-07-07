<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<html lang="en">
<head>
    <title><tiles:getAsString name="title"/></title>
    <tiles:insertAttribute name="resource"/>
    <tiles:insertAttribute name="commonhtml"/>

</head>
<body>
<div class="header">
    <tiles:insertAttribute name="header"/>
</div>

<div class="content" id="contentsDiv">
    <div class="row">
        <div class="col-md-2" style="padding-left: 0px;">
            <tiles:insertAttribute name="sub-left-main-menu"/>
        </div>
        <div class="col-md-10" style="padding-left: 0px;">
            <table style="width: 100%;">
                <tr>
                    <td style="font-size: 15px;font-weight: bold;color: #f89e1f;"><tiles:getAsString name="title"/></td>
                    <td style="color: #9c9c9c;text-align: right;"><tiles:getAsString name="navigation"/></td>
                </tr>
                <tr>
                    <td style="padding-top: 10px;" colspan="2"><tiles:insertAttribute name="body"/></td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="footer">
    <tiles:insertAttribute name="footer"/>
</div>
</body>
</html>