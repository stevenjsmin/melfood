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
     <style>
          .bootstrap-tbl {
               color: #26273C;
          }
          .front-tbl-label {
               background-color: #F9FBE4;
               color: #3c3c3c;
               text-align: right;
          }
          .panel-heading {
               text-align: right;
               font-weight: bold;
               color: #26273C;
          }
          .panel-body {
               color: #b3b3b3;
          }
          .circular-square {
               border-top-left-radius: 50% 50%;
               border-top-right-radius: 50% 50%;
               border-bottom-right-radius: 50% 50%;
               border-bottom-left-radius: 50% 50%;
          }
     </style>
</head>

<body>


<div class="row">
     <div class="col-sm-12" style="padding: 0px 0px;">
          <div class="panel panel-default">
               <!-- Default panel contents -->
               <div class="panel-heading">공구 진행자 / 판매자 정보</div>
               <div class="panel-body">
                    <p>Melfood에서는 신원이 확인된 사용자에 한해서 공동구매 진행자 또는 판매자로서 등록될수 있습니다.</p>
               </div>

               <!-- Table -->
               <table class="table bootstrap-tbl">
                    <colgroup>
                         <col width="20%"/>
                         <col width="20%"/>
                         <col width="20%"/>
                         <col width="20%"/>
                         <col width="20%"/>
                    </colgroup>
                    <tr>
                         <td rowspan="5" style="vertical-align: middle;text-align: center;">
                              <c:choose>
                                   <c:when test="${purchaseOrganizer.profilePhotoId != 0 and purchaseOrganizer.profilePhotoId != null}">
                                        <img id="profilePhotoId" src="/img/?f=${purchaseOrganizer.profilePhotoId}" style="height: 90px;" class="circular-square">
                                   </c:when>
                                   <c:otherwise>
                                        <img id="profilePhotoId" src="/resources/image/profile_photo.png" style="width: 70px;">
                                   </c:otherwise>
                              </c:choose>
                         </td>
                    </tr>
                    <tr>
                         <td class="front-tbl-label">이름</td>
                         <td style="font-weight: bold;">${purchaseOrganizer.userName}</td>
                         <td colspan="2"></td>
                    </tr>
                    <tr>
                         <td class="front-tbl-label">모바일번호</td>
                         <td>${purchaseOrganizer.mobile}</td>
                         <td class="front-tbl-label">이메일</td>
                         <td>${purchaseOrganizer.email}</td>
                    </tr>
                    <tr>
                         <td class="front-tbl-label">ABN</td>
                         <td>${purchaseOrganizer.abn}</td>
                         <td class="front-tbl-label">ACN</td>
                         <td>${purchaseOrganizer.acn}</td>
                    </tr>
                    <tr>
                         <td class="front-tbl-label">사업장주소</td>
                         <td colspan="3">
                              ${purchaseOrganizer.addressStreet}
                              ${purchaseOrganizer.addressSuburb}
                              ${purchaseOrganizer.addressState}
                              ${purchaseOrganizer.addressPostcode}

                         </td>
                    </tr>
               </table>
          </div>
     </div>
</div>

<table class="action_button_table">
     <tr>
          <td>
               <button type="button" class="btn btn-primary" onclick="parent.closePurchaseOrganizerPopup();">닫기</button>
          </td>
     </tr>
</table>

</body>
</html>