<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="melfood.framework.Ctx" %>

<link href="/resources/css/root.css" rel="stylesheet" media="screen">
<link href="/resources/css/kendo.common.min.css" rel="stylesheet">
<link href="/resources/css/kendo.rtl.min.css" rel="stylesheet">
<link href="/resources/css/kendo.default.min.css" rel="stylesheet">
<link href="/resources/css/kendo.dataviz.min.css" rel="stylesheet">
<link href="/resources/css/kendo.dataviz.default.min.css" rel="stylesheet">
<link href="/resources/css/jquery-ui.css" rel="stylesheet" >
<link href="/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/resources/css/bootstrap-dialog.min.css" rel="stylesheet" media="screen">

<link href="/resources/css/melfood-bootstrap.css?ver=<%=Ctx.releaseVersion%>" rel="stylesheet" media="screen">
<link href="/resources/css/melfood-kendo.css?ver=<%=Ctx.releaseVersion%>" rel="stylesheet" media="screen">
<link href="/resources/css/melfood.css?ver=<%=Ctx.releaseVersion%>" rel="stylesheet" media="screen">

<link rel="icon" type="image/png" href="/resources/image/favicon.png" />

<!-- If call jquery.min.js form local resource,some javascript error found : HTTP404: NOT FOUND - The server has not found anything matching the requested URI (Uniform Resource Identifier). -->
<!-- <script src="/resources/js/jquery.min.js"></script> -->
<!-- <script src="http://code.jquery.com/jquery-1.9.1.js"></script> -->
<script src="/resources/js/jquery.min.js"></script>
<script src="/resources/js/kendo.all.min.js"></script>
<script src="/resources/js/jquery-ui.js"></script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA_yzEiQN5aOoYAd4NfgP1FX-PycjdqSBk&libraries=visualization"></script>
<script src="/resources/js/jszip.min.js?ver=<%=Ctx.releaseVersion%>"></script>

<script src="/resources/js/bootstrap.min.js?ver=<%=Ctx.releaseVersion%>"></script>
<script src="/resources/js/bootstrap-dialog.min.js?ver=<%=Ctx.releaseVersion%>"></script>

<!-- Uss RPC Framework Resources -->
<script type="text/javascript" src="/resources/js/melfood_core.js?ver=<%=Ctx.releaseVersion%>"></script>
<script type="text/javascript" src="/resources/js/melfood_util.js?ver=<%=Ctx.releaseVersion%>"></script>
<script type="text/javascript" src="/resources/js/melfood_gmap.js?ver=<%=Ctx.releaseVersion%>"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

