<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link href="/resources/owlcarousel/owl.carousel.min.css" rel="stylesheet" media="screen">
<link href="/resources/owlcarousel/owl.theme.default.min.css" rel="stylesheet" media="screen">
<script src="/resources/owlcarousel/owl.carousel.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $(".amountOfOrder").kendoNumericTextBox({
            change: parseProductOrderValue,
            spin: parseProductOrderValue,
            max: 99999,
            min: 1,
            format: "n0"
        });
    }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
</script>
<script type="application/javascript">
    function openImageInNewTab(fileId) {
        var url = '/img/?f=' + fileId;
        var win = window.open(url, '_blank');
        win.focus();
    }
</script>
<script type="text/javascript">
    function parseProductOrderValue() {
        var amount = 0;
        var totalProdAmount = 0;
        var deliveryFee = 0;
        var subTotal = 0.0;
        var toBeDiscountAmount = 0.0;
        var finalAmount = 0.0;
    }
</script>


<div class="row">
    <div class="col-sm-12" style="padding-right: 10px;padding-left: 10px;">


        <div class="row">
            <div class="col-sm-10" style="padding-bottom: 20px;padding-top: 20px;">
                <p style="font-size: 15px;font-weight: bold;color: #575758;">${product.name}</p>
            </div>
        </div>

        <div class="row">
            <!-- 상품이미지 -->
            <div class="col-sm-4" style="padding-right: 10px;padding-left: 10px;">
                    <c:choose>
                        <c:when test="${not empty product.productionImages}">
                            <div class="owl-carousel" style="width: 250px; height: 250px;">
                                <c:forEach var="entry" items="${product.productionImages}" varStatus="count" begin="0">
                                    <div class="item" style="text-align: center;height: 250px;">
                                        <a href="javascript:openImageInNewTab('${entry.imageFileId}');" >
                                            <c:choose>
                                                <c:when test="${entry.height > 250}">
                                                    <c:choose>
                                                        <c:when test="${entry.width > 250}">
                                                            <img src="/img/?f=${entry.imageFileId}" style="height: 250px;width: 250px;"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="/img/?f=${entry.imageFileId}" style="height: 250px;width: ${entry.width}px;"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>

                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${entry.width > 150}">
                                                            <img src="/img/?f=${entry.imageFileId}" style="height: ${entry.height}px;width: 250px;"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="/img/?f=${entry.imageFileId}" style="height: ${entry.height}px;width: ${entry.width}px;"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                    </div>

                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <img src="/resources/image/default_goods4.png"  style="width:130px;" />
                        </c:otherwise>
                    </c:choose>

            </div>
            <!-- 상품 옵션/기타정보 -->
            <div class="col-sm-7" style="padding-right: 10px;padding-left: 10px;">

                <table class="table table-hover" style="margin-left: 50px;border: solid 0px #FFFFFF;">
                    <tbody>
                    <tr>
                        <td style="border-top-color: #FFFFFF;text-align: right;font-size: 15px;color: #738182;font-weight: bold;">가격</td>
                        <td style="width: 20px;border-top-color: #FFFFFF;"></td>
                        <td style="border-top-color: #FFFFFF;font-size: 15px;color: #738182;font-weight: bold;">$ <fmt:formatNumber type="number" pattern="###.00" value="${product.unitPrice}" /></td>
                        <td style="border-top-color: #FFFFFF;"></td>
                    </tr>
                    <c:choose>
                        <c:when test="${product.productOptionGroups != null && fn:length(product.productOptionGroups) gt 0 }">
                            <c:forEach var="productOptionGroup" items="${product.productOptionGroups}" varStatus="count1" begin="0">
                                <tr>
                                    <td style="color: #797979; text-align: right; border-top-color: #FFFFFF;">${productOptionGroup.optionLabel}</td>
                                    <td style="border-top-color: #FFFFFF;"></td>
                                    <td style="padding: 5px 5px;color: #505050; border-top-color: #FFFFFF;"><c:out value="${productOptionGroup.optionCmbx}" escapeXml="false"/></td>
                                    <td style="border-top-color: #FFFFFF;"></td>
                                </tr>
                            </c:forEach>
                        </c:when>
                    </c:choose>

                    <tr>
                        <td style="border-top-color: #FFFFFF;text-align: right;color: #738182;padding-bottom: 30px;padding-top: 25px;">주문수량</td>
                        <td style="width: 20px;border-top-color: #FFFFFF;"></td>
                        <td style="border-top-color: #FFFFFF;padding-bottom: 30px;padding-top: 20px;"><input type="hidden"  class="amountOfOrder" id="amountOfOrder" name="amountOfOrder" value='1'/></td>
                        <td style="border-top-color: #FFFFFF;padding-bottom: 30px;padding-top: 20px;">
                            <button type="button" class="btn btn-sm" onclick="parent.closeOpenProductOrderPopup()"><i class="fa fa-times" aria-hidden="true"></i> 취소</button>
                            &nbsp;&nbsp;
                            <button type="button" class="btn btn-warning btn-sm" onclick="goList()"><i class="fa fa-cart-plus" aria-hidden="true" style="color: #FFFFFF;"></i> 주문</button>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="4" style="padding-top: 30px;">${product.description}</td>
                    </tr>
                    </tbody>
                </table>


            </div>
        </div>

    </div>
</div>



<script type="text/javascript">
    $('.owl-carousel').owlCarousel({
        loop:false,
        margin:10,
        nav:true,
        dots:true,
        center:true,
        items: 1
    })
    $(".owl-carousel").owlCarousel();

</script>