<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="melfood.framework.Ctx" %>

<!doctype html>
<head>
     <script src="/resources/js/melfood/melfood_slick.js?ver=<%=Ctx.releaseVersion%>"></script>
     <link rel="stylesheet" type="text/css" href="/resources/slick/slick.css"/>
     <link rel="stylesheet" type="text/css" href="/resources/slick/slick-theme.css"/>
     <script type="text/javascript" src="/resources/slick/slick.min.js"></script>

     <script type="text/javascript">
         $(document).ready(function() {
         }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     </script>



     <style>
          .content {
               width: 100%;
               padding: 10px 0px 0px 0px
          }
          h2 {
               color: #6e6e6e;
               font-family: verdana;
               font-size: 300%;
               text-align: center;
               font-weight: bold;
          }
          .gppurchase {
               border:2px solid;
               color: #FBFBFB;
          }
          .gppurchase:hover {
               border-color: #69B7F5;
          }
          .fa {
               color: #606060;
          }
          .circular-square {
               border-top-left-radius: 50% 50%;
               border-top-right-radius: 50% 50%;
               border-bottom-right-radius: 50% 50%;
               border-bottom-left-radius: 50% 50%;
          }
     </style>
     <style type="text/css">
          .slider {
               width: 60%;
               margin-bottom: 10px;
               margin-left: auto;
               margin-right: auto;
          }
          .slick-slide {
               margin: 0px 20px;
          }

          .slick-slide img {
               width: 100%;
          }
          .slick-prev:before,
          .slick-next:before {
               color: black;
          }
     </style>


</head>

</head>

<body>
<div class="row">
     <div class="col-sm-12" align="right">
          <table>
               <tr>
                    <td><a href="#"><i class="fa fa-step-backward" aria-hidden="true"></i></a></td>
                    <td style="padding-left: 10px;padding-right: 10px;">공구일정</td>
                    <td><a href="#"><i class="fa fa-step-forward" aria-hidden="true"></i></a></td>
               </tr>
          </table>
     </div>
</div>
<div class="row gppurchase" style="height: 150px;">
     <div class="col-sm-4">
          <table style="width: 100%;color: #606060;">
               <tr style="height: 30px;"><td colspan="3" style="font-size: 15px;font-weight: bold;color: #2A2A2A;">맛있는 수제 한국빵 공구합니다.</td></tr>
               <tr style="height: 25px;">
                    <td style="width: 35px;text-align: center;"><i class="fa fa-map-marker fa-lg" aria-hidden="true"></i></td>
                    <td style="font-weight: bold;height: 25px;">BoxHill 3752</td>
                    <td rowspan="3">
                         <table style="width: 100%;">
                              <tr><td><img src="/resources/image/good_grp_buy.jpg" style="width: 80px;"></td></tr>
                              <tr><td style="text-align: right;padding-top: 5px;"><a href="#">공동구매 참여하기 <img src="/resources/image/click-here.png" style="width: 40px;"> </a></td></tr>
                         </table>

                    </td>
               </tr>
               <tr style="height: 25px;">
                    <td style="width: 35px;text-align: center;"><i class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
                    <td style="font-weight: bold;"> MM-DD 11:00AM ~ 11:59AM</td>
               </tr>
               <tr style="height: 25px;">
                    <td></td>
                    <td style="font-weight: bold;color: #FF5832;">Minimum order $20.00</td>
               </tr>
          </table>
     </div>
     <div class="col-sm-8">
          <section class="regular slider">
               <div>
                    <img src="/resources/image/sample/1.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/2.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/3.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/3.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/4.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/5.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/6.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/7.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/8.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/9.jpg">
               </div>
          </section>
     </div>

</div>

<br/>
<div class="row gppurchase" style="height: 150px;">
     <div class="col-sm-4">
          <table style="width: 100%;color: #606060;">
               <tr style="height: 30px;"><td colspan="3" style="font-size: 15px;font-weight: bold;color: #2A2A2A;">깊은 맛이느껴지는 족발 맛보세요.</td></tr>
               <tr style="height: 25px;">
                    <td style="width: 35px;text-align: center;"><i class="fa fa-map-marker fa-lg" aria-hidden="true"></i></td>
                    <td style="font-weight: bold;height: 25px;">Ringwood 3652</td>
                    <td rowspan="4">
                         <table style="width: 100%;">
                              <tr><td><img src="/resources/image/close-order.png" style="width: 90px;"></td></tr>
                              <tr><td style="text-align: right;padding-top: 5px;color: #606060;">감사합니다. 마감되었습니다.</td></tr>
                         </table>

                    </td>
               </tr>
               <tr style="height: 25px;">
                    <td style="width: 35px;text-align: center;"><i class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
                    <td style="font-weight: bold;"> MM-DD 11:00AM ~ 11:59AM</td>
               </tr>
               <tr style="height: 25px;">
                    <td style="width: 35px;text-align: center;"><i class="fa fa-truck" aria-hidden="true"></i></td>
                    <td style="font-weight: bold;color: #b3b3b3;">배달 가능</td>
               </tr>
               <tr style="height: 25px;">
                    <td></td>
                    <td style="font-weight: bold;color: #FF5832;">Minimum order $40.00</td>
               </tr>
          </table>
     </div>
     <div class="col-sm-8">
          <section class="regular slider">
               <div>
                    <img src="/resources/image/sample/jock_1.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/jock_3.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/jock_3.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/jock_4.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/jock_5.jpg">
               </div>
               <div>
                    <img src="/resources/image/sample/jock_6.jpg">
               </div>
          </section>
     </div>

</div>

<div class="row" align="center" style="background-color: #1E1E1E;margin-top: 40px;padding-top: 10px;padding-bottom: 10px;">
     <div class="col-sm-12">
          <table style="width: 100%;">
               <tr>
                    <td style="text-align: left;vertical-align: top;color: #797979;padding-top: 5px;width: 120px;">협동조합 파트너스</td>
                    <td>
                         <table align="center">
                              <tr>
                                   <td style="text-align: center;" id="mousefollow"><img class="circular-square" title="로즈 베이커리" src="/resources/image/sample/partner_1.jpg"/></td>
                                   <td style="width: 10px;">&nbsp;</td>
                                   <td style="text-align: center"><img class="circular-square" title="조선김치" src="/resources/image/sample/partner_2.jpg" /></td>
                                   <td style="width: 10px;">&nbsp;</td>
                                   <td style="text-align: center"><img class="circular-square" title="족과의 동침" src="/resources/image/sample/partner_3.jpg" /></td>
                              </tr>
                         </table>
                    </td>
               </tr>
          </table>
     </div>
</div>


<div class="row">
     <div class="col-sm-12" style="padding-right: 0px;padding-left: 0px">
          <img src="/resources/image/pizza-desktop.jpg" style="width: 100%;">
     </div>
</div>



</body>
</html>