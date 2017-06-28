<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!doctype html>
<head>
    <style>
        .content {
            width: 100%;
            padding: 0px 0px 0px 0px
        }
        .venueInfoWrapper {
            background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)),url(/resources/image/generic_backdrop.png);
            background-size: cover;
            background-repeat: no-repeat;
            background-color: #797876;
            box-sizing: border-box;
            padding: 20px 0;
            height: 210px;
        }
    </style>
    <script type="text/javascript">
        function groupPurchaseOrganizer() {
            var organizerId = '${groupPurchase.purchaseOrganizer}';

            message = "멜푸드에서는 인적확인을 거친사용자만 판매자로 활동할수있습니다";

            infoPopup(message);
        }

    </script>
    <script type="text/javascript">
        // document.location.href = "/common/auth/welcome.yum";
    </script>

    <script type="text/javascript">
        function groupPurchaseOrganizer() {

            $("#purchaseOrganizerPopup").kendoWindow({
                content: "/grouppurchase/purchaseOrganizerInfo.yum?purchaseOrganizer=" + "${groupPurchase.purchaseOrganizer}",
                actions: ["Minimize", "Maximize", "Close"],
                title: "내가잘사는 방법:: MelFood",
                modal: true,
                iframe: true,
                position:{ top:"200", left:"25%"}
            });

            var popup_dialog = $("#purchaseOrganizerPopup").data("kendoWindow");
            popup_dialog.setOptions({
                width: 800,
                height: 350
            });
            // popup_dialog.center();

            $("#purchaseOrganizerPopup").data("kendoWindow").open();
        }
        function closePurchaseOrganizerPopup() {
            var win_dialog = $("#purchaseOrganizerPopup").data("kendoWindow");
            win_dialog.close();
        }
    </script>
</head>

<body>
<div id="purchaseOrganizerPopup"></div>

<div class="row">
    <div class="col-sm-12" style="padding: 0px 0px;">
        <div class="venueInfoWrapper">
            <table align="center">
                <tr>
                    <td style="padding: 0px 30px 0px 0px;">
                        <img src="/img/?f=${firstImageId}" style="height: 100px;"/>
                    </td>
                    <td>
                        <table align="center"  style="color: #FFFFFF;">
                            <tr>
                                <td>
                                    <div style="font-weight: bold;font-size: 25px;">
                                        ${groupPurchase.groupPurchaseTitle}
                                    </div>
                                    <i class="fa fa-map-marker fa-lg" aria-hidden="true"></i>&nbsp;&nbsp;
                                    <span style="font-size: 13px;font-weight: bold;">
                                ${groupPurchase.marketAddressStreet}
                                ${groupPurchase.marketAddressSuburb}
                                ${groupPurchase.marketAddressState}
                                ${groupPurchase.marketAddressPostcode}
                        </span>
                                </td>
                            </tr>

                            <c:choose>
                                <c:when test="${groupPurchase.marketAddressComment != '' && groupPurchase.marketAddressComment != null}">
                                    <tr>
                                        <td style="padding-top: 5px;padding-left: 20px;color: #b3b3b3;">${groupPurchase.marketAddressComment}</td>
                                    </tr>
                                </c:when>
                            </c:choose>

                            <tr>
                                <td>
                                    <table style="color: #FFFFFF;margin-top: 20px;width: 100%;">
                                        <tr style="height: 50px;">
                                            <td style="padding-right: 20px;">
                                                <table style="color: #FFFFFF;width: 100%;">
                                                    <tr>
                                                        <td style="width: 25px;text-align: center;"><i class="fa fa-clock-o fa-lg" aria-hidden="true"></i></td>
                                                        <td>
                                                            ${groupPurchase.marketOpenStartDate}
                                                            <span style="text-decoration: underline;">${groupPurchase.marketOpenStartTime} ~ ${groupPurchase.marketOpenEndTime}</span>
                                                        </td>
                                                    </tr>

                                                    <c:choose>
                                                        <c:when test="${groupPurchase.discountMethod != '' && groupPurchase.discountMethod != null}">
                                                            <c:choose>
                                                                <c:when test="${groupPurchase.discountMethod == 'FIXED' && groupPurchase.discountFixedAmount != '' && groupPurchase.discountFixedAmount != null}">
                                                                    <tr>
                                                                        <td style="width: 25px;text-align: center;"><i class="fa fa-usd" aria-hidden="true"></i></td>
                                                                        <td style="padding-top: 5px;"><fmt:formatNumber type="number" pattern="###.00" value="${groupPurchase.discountFixedAmount}" /> Discount</td>
                                                                    </tr>
                                                                </c:when>
                                                                <c:when test="${groupPurchase.discountMethod == 'RATE' && groupPurchase.discountRateValue != '' && groupPurchase.discountRateValue != null}">
                                                                    <tr>
                                                                        <td style="width: 25px;text-align: center;"><i class="fa fa-percent" aria-hidden="true"></i></td>
                                                                        <td style="padding-top: 5px;"><fmt:formatNumber type="number" pattern="###" value="${groupPurchase.discountRateValue * 100}" /> (Percent) Discount</td>
                                                                    </tr>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:when>
                                                    </c:choose>

                                                    <c:choose>
                                                        <c:when test="${groupPurchase.deliverable == 'Y'}">
                                                            <tr>
                                                                <td style="width: 25px;text-align: center;"><i class="fa fa-truck" aria-hidden="true"></i></td>
                                                                <td style="padding-top: 5px;color: #AFB1B1;">배달 가능</td>
                                                            </tr>
                                                        </c:when>
                                                    </c:choose>

                                                </table>


                                            </td>
                                            <td style="width: 2px;background-color: #AFB1B1;"></td>
                                            <td style="padding: 20px 20px;"><a href="javascript:groupPurchaseOrganizer()" style="color: #69B7F5;">공구진행자 / 판매자정보</a></td>
                                            <td style="width: 2px;background-color: #AFB1B1;"></td>
                                            <td style="padding: 20px 20px;">
                                                <div class="alert alert-danger" role="alert" style="padding: 5px 20px 5px 20px;">
                                        <span style="color: #606060;">Minimum order $ <fmt:formatNumber type="number" pattern="###.00" value="${groupPurchase.minimumPurchaseAmount}" />
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>


        </div>

    </div>
</div>

</body>
</html>