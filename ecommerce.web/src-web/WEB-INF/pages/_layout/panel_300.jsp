<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!-- 
	화면 상.하로 Header와 Footer가 있고
	화면 중앙에는 왼쪽에는 메인메뉴, 오른쪽에는 메인화면이 있는 구조이다.
 -->
 
<html lang="en">
<head>
    <link rel="stylesheet" href="/resources/header-menu/header-menu.css">
    <title><tiles:getAsString name="title" /></title>
    <tiles:insertAttribute name="resource" />
    <tiles:insertAttribute name="commonhtml" />

    <script type="text/javascript">
        $(document).ready(function () {
            setFocus();
        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>
    <script type="text/javascript">
        function setFocus() {
            var pathName = window.location.pathname;
            console.log('pathName:' + pathName);
        }
    </script>
</head>
<body>
     <div class="header">
          <tiles:insertAttribute name="header" />
     </div>



     <div class="row" style="margin-right: 0px;">
         <div class="col-md-12" style="padding-right: 0px;padding-left: 0px;">
             <!-- 메인메뉴을 구성한다 -->
             <nav>
                 <a id="resp-menu" class="responsive-menu" href="#"><i class="fa fa-reorder"></i> Menu</a>
                 <ul class="menu">
                     <li><a id="home-100" href="/rpc/business/mywork/MyDashbord.rpc"><i class="fa fa-home fa-lg"></i>&nbsp; HOME</a>
                         <ul class="sub-menu">
                             <li><a id="home-120" href="/rpc/business/report/reportmanager/Dashbord.rpc"><i class="fa fa-line-chart" aria-hidden="true"></i>&nbsp;  View monthly quote/contract</a></li>
                             <li><a id="home-130" href="/rpc/common/report/sales/planactual/Main.rpc"><i class="fa fa-bar-chart" aria-hidden="true"></i>&nbsp; View sales goals vs actuals</a></li>
                             <li><a id="home-140" href="/rpc/business/report/reportmanager/geographicanalysis/main.rpc"><i class="fa fa-map" aria-hidden="true"></i>&nbsp; View geographic analysis</a></li>
                         </ul>
                     </li>

                     <li><a id="quotation-100" href="/rpc/business/quotationMultiProductsManager/Main.rpc"><i class="fa fa-calculator fa-lg" aria-hidden="true"></i>&nbsp; QUOTATION</a>
                         <ul class="sub-menu">
                             <li><a id="quotation-110" href="/rpc/business/quotationBundledUnbundledManager/Quotation.rpc?id=0"><i class="fa fa-sticky-note-o" aria-hidden="true"></i>&nbsp; Make quotation for single site</a></li>
                             <li><a id="quotation-120" href="/rpc/business/multipleSitesManager/Main.rpc"><i class="fa fa-clone" aria-hidden="true"></i>&nbsp; Make quotation for multiple site</a></li>
                             <!--
                             <li><a id="quotation-120" href="javascript:underDevelopment();"><i class="fa fa-clone" aria-hidden="true"></i>&nbsp; Make quotation for multiple site</a></li>
                             -->
                         </ul>
                     </li>

                     <li><a id="contract-100" href="/rpc/business/contractmanager/Main.rpc"><i class="fa fa-handshake-o fa-lg"></i>&nbsp; CONTRACT</a></li>

                     <li><a id="sales_records-100" href="/rpc/business/salesRecord/Main.rpc"><i class="fa fa-database fa-lg"></i>&nbsp; SALES RECORDS</a></li>

                     <li><a id="biz_prospects-100" href="/rpc/common/bizcustomermanager/Main.rpc"><i class="fa fa-address-card-o fa-lg"></i>&nbsp; BIZ PROSPECTS LISTING</a>
                         <ul class="sub-menu">
                             <li><a id="biz_prospects-110" href="/rpc/common/bizcustomermanager/Main.rpc"><i class="fa fa-pie-chart" aria-hidden="true"></i>&nbsp; View summary</a></li>
                             <li><a id="biz_prospects-120" href="/rpc/common/bizcustomermanager/search.rpc"><i class="fa fa-address-card-o" aria-hidden="true"></i>&nbsp; Search business</a></li>
                             <li><a id="biz_prospects-130" href="/rpc/common/distributormanager/Main.rpc"><i class="fa fa-university" aria-hidden="true"></i>&nbsp; Search service area by distributor</a></li>
                             <li><a id="biz_prospects-140" href="/rpc/common/bizcustomermanager/uploadManualForm.rpc"><i class="fa fa-keyboard-o" aria-hidden="true"></i>&nbsp; Data load : By Manual</a></li>
                             <li><a id="biz_prospects-150" href="/rpc/common/bizcustomermanager/uploadAODFileForm.rpc"><i class="fa fa-upload" aria-hidden="true"></i>&nbsp; Data load : By AOD</a></li>
                         </ul>
                     </li>
                     <!--
                     <li><a id="biz_prospects-100" href="javascript:noPermission();"><i class="fa fa-address-card-o fa-lg"></i>&nbsp; BIZ PROSPECTS LISTING</a>
                         <ul class="sub-menu">
                                 <li><a id="biz_prospects-110" href="javascript:noPermission();"><i class="fa fa-pie-chart" aria-hidden="true"></i>&nbsp; View summary</a></li>
                                 <li><a id="biz_prospects-120" href="javascript:noPermission();"><i class="fa fa-address-card-o" aria-hidden="true"></i>&nbsp; Search business</a></li>
                                 <li><a id="biz_prospects-130" href="javascript:noPermission();"><i class="fa fa-university" aria-hidden="true"></i>&nbsp; Search service area by distributor</a></li>
                                 <li><a id="biz_prospects-140" href="javascript:noPermission();"><i class="fa fa-keyboard-o" aria-hidden="true"></i>&nbsp; Data load : By Manual</a></li>
                                 <li><a id="biz_prospects-150" href="javascript:noPermission();"><i class="fa fa-upload" aria-hidden="true"></i>&nbsp; Data load : By AOD</a></li>
                         </ul>
                     </li>
                     -->

                     <li><a id="setting-100" href="#"><i class="fa fa-cogs fa-lg"></i>&nbsp; SETTING</a>
                         <ul class="sub-menu">
                             <li><a id="setting-110" href="/rpc/common/salespartner/admin/Main.rpc"><i class="fa fa-address-book-o" aria-hidden="true"></i>&nbsp; Sale partner management</a></li>
                             <li><a id="setting-120" href="/rpc/framework/codemanager/Main.rpc"><i class="fa fa-code" aria-hidden="true"></i>&nbsp; Code management</a></li>
                             <li><a id="setting-130" href="/rpc/framework/usermanager/Main.rpc"><i class="fa fa-user-o" aria-hidden="true"></i>&nbsp;  User management</a></li>
                             <li><a id="setting-140" href="/rpc/common/postcodemanager/Main.rpc"><i class="fa fa-envelope-o" aria-hidden="true"></i>&nbsp; Post code management</a></li>
                             <li><a id="setting-150" href="/rpc/common/menumanager/Main.rpc"><i class="fa fa-bars" aria-hidden="true"></i>&nbsp; Menu management</a></li>
                             <li><a id="setting-160" href="/rpc/common/noticemanager/Main.rpc"><i class="fa fa-bell" aria-hidden="true"></i>&nbsp; Notice board management</a></li>
                             <li><a id="setting-170" href="/rpc/common/awscloud/Main.rpc"><i class="fa fa-server" aria-hidden="true"></i>&nbsp; Server ACL management</a></li>
                         </ul>
                     </li>
                 </ul>
             </nav>
         </div>  <!--  End of COL -->
     </div>     <!--  End of ROW -->





     <div class="content" id="contentsDiv">
          <div class="row">
                 <div class="col-md-2"  style="padding-left: 0px;">
                    <table style="width: 100%;vertical-align: top; background-color: #f9f9f9;">
                         <tr><td style="vertical-align: top; text-align: right; padding: 5px 5px 5px 0px;height: 20px;"><span style="color: #3794DA;font-weight: bold;">[ <tiles:getAsString name="leftMenuName" /> ]</span></td></tr>
                         <tr><td style="vertical-align: top;"><tiles:insertAttribute name="left-menu" /></td></tr>
                         <tr><td>&nbsp;</td></tr>
                    </table>
                 </div>
                 <div class="col-md-10" style="padding-left: 0px;">
                    <table style="width: 100%;">
                         <tr>
                              <td style="font-size: 15px;font-weight: bold;color: #f89e1f;"><tiles:getAsString name="title" /></td>
                              <td style="color: #9c9c9c;text-align: right;"><tiles:getAsString name="navigation" /></td>
                         </tr>
                         <tr>
                              <td style="padding-top: 10px;" colspan="2"><tiles:insertAttribute name="body" /></td>
                         </tr>
                    </table>
                 </div>
          </div>
     </div>
     <div class="footer">
          <tiles:insertAttribute name="footer" />
     </div>
</body>
</html>