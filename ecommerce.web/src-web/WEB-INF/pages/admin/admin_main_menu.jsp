<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<head>
     <script type="text/javascript">
         $(document).ready(function () {
             setFocus();
         }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     </script>

     <script type="text/javascript">
         function setFocus() {
             var pathName = window.location.pathname;

             if (pathName.startsWith("/admin/categorymgt")) {
                 $("#product-000").addClass("homer");
                 $("#product-110").addClass("homer");
             } else if(pathName.startsWith("/admin/productmgt")){
                 $("#product-000").addClass("homer");
                 $("#product-120").addClass("homer");
             } else if(pathName.startsWith("/admin/checkbeforebuy")){
                 $("#product-000").addClass("homer");
                 $("#product-130").addClass("homer");


             } else if(pathName.startsWith("/admin/contractmgt")){
                 $("#seller-000").addClass("homer");
                 $("#seller-110").addClass("homer");
             } else if(pathName.startsWith("/admin/deliverycalendarmgt")) {
                 $("#seller-000").addClass("homer");
                 $("#seller-120").addClass("homer");
             } else if(pathName.startsWith("/admin/paymentmethodmgt")) {
                 $("#seller-000").addClass("homer");
                 $("#seller-130").addClass("homer");


             } else if(pathName.startsWith("/admin/grouppurchase")) {
                 $("#grouppurchase-000").addClass("homer");


             } else if(pathName.startsWith("/admin/ordermgt")) {
                 $("#ordermgt-000").addClass("homer");


             } else if(pathName.startsWith("/framework/communicationmanager")) {
                 $("#communication-000").addClass("homer");

             } else if (pathName.startsWith("/framework/codemanager")) {
                 $("#setting-000").addClass("homer");
                 $("#setting-110").addClass("homer");
             } else if (pathName.startsWith("/framework/usermanager")) {
                 $("#setting-000").addClass("homer");
                 $("#setting-120").addClass("homer");
             } else if (pathName.startsWith("/framework/postcodemanager")) {
                 $("#setting-000").addClass("homer");
                 $("#setting-130").addClass("homer");
             } else if (pathName.startsWith("/framework/sysconfigmanager")) {
                 $("#setting-000").addClass("homer");
                 $("#setting-140").addClass("homer");
             } else {
                 $("#home-000").addClass("homer");
             }
         }
     </script>

</head>

<body>
     <div class="row" style="margin-right: 0px;">
          <div class="col-md-12" style="padding: 0px;">
               <!-- 메인메뉴을 구성한다 -->
               <nav>
                    <a id="resp-menu" class="responsive-menu" href="#"><i class="fa fa-reorder"></i> Menu</a>

                    <ul class="menu">
                         <li><a id="home-000" href="#"><i class="fa fa-home fa-lg"></i>&nbsp; HOME</a>
                         <li><a id="product-000" href="#"><i class="fa fa-gift fa-lg" aria-hidden="true"></i>&nbsp; 상품관리</a>
                              <ul class="sub-menu">
                                   <li><a id="product-110" href="/admin/categorymgt/Main.yum"><i class="fa fa-tag" aria-hidden="true"></i>&nbsp; 카테고리 관리</a></li>
                                   <li><a id="product-120" href="/admin/productmgt/Main.yum"><i class="fa fa-gift" aria-hidden="true"></i>&nbsp; 상품 관리</a></li>
                                   <li><a id="product-130" href="/admin/checkbeforebuy/Main.yum"><i class="fa fa-check-square-o" aria-hidden="true"></i>&nbsp;  구매전확인사항 관리</a></li>
                              </ul>
                         </li>

                         <li><a id="seller-000" href="#"><i class="fa fa-address-card" aria-hidden="true"></i>&nbsp; 판매자관리</a>
                              <ul class="sub-menu">
                                   <li><a id="seller-110" href="/admin/contractmgt/Main.yum"><i class="fa fa-handshake-o" aria-hidden="true"></i>&nbsp; 계약 관리</a></li>
                                   <li><a id="seller-120" href="/admin/deliverycalendarmgt/Main.yum"><i class="fa fa-truck" aria-hidden="true"></i>&nbsp; 배송일정 관리</a></li>
                                   <li><a id="seller-130" href="/admin/paymentmethodmgt/Main.yum"><i class="fa fa-credit-card-alt" aria-hidden="true"></i>&nbsp;  결재수단 관리</a></li>
                              </ul>
                         </li>


                         <li><a id="grouppurchase-000" href="/admin/grouppurchase/Main.yum"><i class="fa fa-users" aria-hidden="true"></i>&nbsp; 공동구매 관리</a>


                         <li><a id="ordermgt-000" href="#"><i class="fa fa-calculator fa-lg"></i>&nbsp; 주문 관리</a>
                              <ul class="sub-menu">
                                   <li><a id="ordermgt-110" href="/admin/ordermgt/grouppurchase/Main.yum"><i class="fa fa-users" aria-hidden="true"></i>&nbsp; 공.구 주문</a></li>
                                   <li><a id="ordermgt-120" href="/admin/ordermgt/Main.yum"><i class="fa fa-calculator" aria-hidden="true"></i>&nbsp;  모든 주문</a></li>
                              </ul>
                         </li>



                         <li><a id="communication-000" href="#"><i class="fa fa-cogs fa-lg"></i>&nbsp; Communication 관리</a>
                              <ul class="sub-menu">
                                   <li><a id="communication-110" href="/framework/communicationmanager/Main.yum?category=QNA"><i class="fa fa-question-circle" aria-hidden="true"></i>&nbsp; QnA 관리</a></li>
                                   <li><a id="communication-120" href="/framework/communicationmanager/Main.yum?category=NOTICE"><i class="fa fa-bell-o" aria-hidden="true"></i> / <i class="fa fa-comments" aria-hidden="true"></i>&nbsp;  공지/대화 관리</a></li>
                              </ul>
                         </li>

                         <li><a id="setting-000" href="#"><i class="fa fa-cogs fa-lg"></i>&nbsp; 시스템관리</a>
                              <ul class="sub-menu">
                                   <li><a id="setting-110" href="/framework/codemanager/Main.yum"><i class="fa fa-code" aria-hidden="true"></i>&nbsp; 코드 관리</a></li>
                                   <li><a id="setting-120" href="/framework/usermanager/Main.yum"><i class="fa fa-user-o" aria-hidden="true"></i>&nbsp;  사용자 관리</a></li>
                                   <li><a id="setting-130" href="/framework/postcodemanager/Main.yum"><i class="fa fa-envelope-o" aria-hidden="true"></i>&nbsp; 우편번호 관리</a></li>
                                   <li><a id="setting-140" href="/framework/sysconfigmanager/Main.yum"><i class="fa fa-server" aria-hidden="true"></i>&nbsp; 시스템 환경변수 관리</a></li>
                              </ul>
                         </li>
                    </ul>
               </nav>
          </div>  <!--  End of COL -->
     </div>     <!--  End of ROW -->

     <script>
         $(document).ready(function(){
             var touch 	= $('#resp-menu');
             var menu 	= $('.menu');

             $(touch).on('click', function(e) {
                 e.preventDefault();
                 menu.slideToggle();
             });

             $(window).resize(function(){
                 var w = $(window).width();
                 if(w > 767 && menu.is(':hidden')) {
                     menu.removeAttr('style');
                 }
             });
         });
     </script>
</body>
</html>