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

     </style>
</head>

<body>


<div class="row">
     <div class="col-sm-12" style="padding: 0px 0px;">
          <div class="panel panel-default">
               <!-- Default panel contents -->
               <div class="panel-heading">${deliveryDate} 공.구 진행자 배송일정</div>
               <div class="panel-body">
                    <p>고객님의 지역에 배송일정이 없어도 배달 서비스를 받고자 하시는 경우 공.구 진행자에게 연락해보세요.</p>
               </div>

               <!-- Table -->
               <table class="table table-striped bootstrap-tbl">
                    <thead>
                    <tr>
                         <th style="text-align: right;">지역</th>
                         <th style="text-align: center;">일정</th>
                         <th>배달예상시간</th>
                    </tr>
                    </thead>

                    <c:choose>
                         <c:when test="${fn:length(deliverySchedules) gt 0}">
                              <c:forEach var="deliverySchedule" items="${deliverySchedules}" varStatus="count" begin="0">
                                   <tr>
                                        <td class="front-tbl-label">${deliverySchedule.addressSuburb} ${deliverySchedule.addressPostcode}</td>
                                        <td style="text-align: center;">${deliverySchedule.yyyyMmDd}</td>
                                        <td>${deliverySchedule.btwnFromHhmm} ~ ${deliverySchedule.btwnToHhmm}</td>
                                   </tr>
                              </c:forEach>
                         </c:when>
                         <c:otherwise>
                              <tr style="height: 50px;">
                                   <td style="text-align: center;vertical-align: middle;color: #8D9999;" colspan="3"> 등록된 일정이 없습니다.</td>
                              </tr>
                         </c:otherwise>
                    </c:choose>

               </table>
          </div>
     </div>
</div>

<table class="action_button_table">
     <tr>
          <td>
               <button type="button" class="btn btn-primary" onclick="parent.closeDeliverySchedulePopup();">닫기</button>
          </td>
     </tr>
</table>

</body>
</html>