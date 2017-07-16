<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="/resources/js/melfood/admin/shopmaster.js?ver=<%=Ctx.releaseVersion%>"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#fileUpload").kendoUpload({
                async: {
                    saveUrl: "/admin/shopmastermgt/shopGateImageUpload.yum?shopId=${shopMaster.shopId}",
                    removeUrl: "/admin/shopmastermgt/shopGateImageRemove.yum?shopId=${shopMaster.shopId}",
                    removeField: "removeFile",
                    autoUpload: true,
                    batch: true,
                },
                showFileList: false,
                localization: {
                    statusFailed: "Failed to file upload. Please check it again and retry.",
                    uploadSelectedFiles: "Upload file(s)"
                },
                multiple: false,
                complete: onComplete,
                error: onError,
                success: onSuccess
            });

            function onComplete(e) {
                //search();
            }

            function onSuccess(e) {
                var data = e.response;

                if (data.resultCode != '0') {
                    var htmlMessage = "<b>샵대표이미지 갱신 실패 : </b>" + data.message;
                    warningPopup(htmlMessage);
                    $("#shopGateImageFileIdMessage").html(htmlMessage);
                    $("#shopGateImageFileId").html("");

                } else {
                    $("#shopGateImageFileIdMessage").html("");

                    if (data.attachedFileNo != undefined && data.attachedFileNo != '') {
                        infoPopup("정상적으로 파일이 등록되었습니다.");
                        var htmlMessage = "업로드된 샵 대표이미지 : " + data.attachedFileName
                            + " &nbsp;&nbsp; "
                            + "<a href=\"javascript:downloadFile('" + data.attachedFileNo + "');\"><img src=\"\/resources\/css/images\/gic\/ic_file_download_black_18dp_1x.png\"/><\/a>"
                            + "&nbsp;&nbsp;"
                            + "<a href=\"javascript:deleteShopGateImageFile();\">delete<\/a>";

                        $("#shopGateImageFileId").html(htmlMessage);
                    }
                }
            }

            function onError(e) {
                var files = e.files;
                if (e.operation == "upload") {
                    warningPopup("Failed to upload : " + files[0].name);
                }
            }

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>

    <script type="text/javascript">
        function deleteShopGateImageFile() {

            progress(true);

            $.ajax({
                url: "/admin/shopmastermgt/shopGateImageRemove.yum",
                data: {
                    shopId: "${shopMaster.shopId}"
                },
                success: callbackShopGateImageFile
            });
        }
        function callbackShopGateImageFile(data) {
            var message = data.message;
            var resultCode = data.resultCode;
            var shopId = data.shopId;

            progress(false);

            if (resultCode != "0") {
                $("#shopGateImageFileIdMessage").html("파일을 삭제하는 도중 문제가 발생하였습니다:" + message);
            } else {
                $("#shopGateImageFileId").html("");
                $("#shopGateImageFileIdMessage").html("");
            }
        }
    </script>

    <style>
        /** To force height of kendoEditor */
        .k-content {
            height: 100px !important;
        }
    </style>
</head>


<body>
<table>
    <tr>
        <td valign="top">
            <table class="detail_table">
                <colgroup>
                    <col width="250px"/>
                    <col width="250px"/>
                    <col width="250px"/>
                    <col width="250px"/>
                </colgroup>

                <tr>
                    <td colspan="4">
                        <span class="subtitle"> 기본정보</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">Shop 이름</td>
                    <td class="value" colspan="3">${shopMaster.shopName}</td>
                </tr>
                <tr>
                    <td class="label">Shop 운영자</td>
                    <td class="value">${shopMaster.shopOwnerName}</td>
                    <td class="label">Shop Template</td>
                    <td class="value">${shopMaster.templateName}</td>
                </tr>
                <tr>
                    <td class="label">최소 참여금액</td>
                    <td class="value"><fmt:formatNumber type="number" pattern="##0.00" value="${shopMaster.minimumPurchaseAmount}"/> $</td>
                    <td class="label">최대 참여금액</td>
                    <td class="value"><fmt:formatNumber type="number" pattern="##0.00" value="${shopMaster.maximumPurchaseAmount}"/> $</td>
                </tr>
                <tr>
                    <td class="label">할인비율</td>
                    <td class="value"><fmt:formatNumber type="number" pattern="##0.00" value="${shopMaster.discountRateValue * 100}"/> %</td>
                    <td class="label">Shop 크리딧</td>
                    <td class="value">${shopMaster.shopCredit}</td>
                </tr>
                <tr>
                    <td class="label">샵 대표이미지</td>
                    <td class="value" colspan="2"><input type="file" name="files" id="fileUpload"/></td>
                    <td class="value"></td>
                </tr>
                <tr>
                    <td class="value"></td>
                    <td class="value" colspan="3">
                        <div id="shopGateImageFileId" style="font-size: 10px;">
                            <c:choose>
                                <c:when test="${shopMaster.shopGateImageFileId != null && shopMaster.shopGateImageFileId != null}">
                                    업로드된 이미지 파일이 있습니다 : ${attachedFileName} <a href="javascript:downloadFile('${shopMaster.shopGateImageFileId}');"><img src="/resources/css/images/gic/ic_file_download_black_18dp_1x.png"/></a>
                                    &nbsp;&nbsp;
                                    <a href="javascript:deleteShopGateImageFile();">delete</a>
                                </c:when>
                                <c:otherwise></c:otherwise>
                            </c:choose>
                        </div>
                        <div id="shopGateImageFileIdMessage"></div>
                    </td>
                </tr>


                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle"> Shop 주소</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">주소</td>
                    <td class="value" colspan="3">${shopMaster.addressFormattedAddress}</td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br/>
                        <br/>
                        <span class="subtitle"> 배송서비스</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">배달가능여부</td>
                    <td class="value">
                        <c:choose>
                            <c:when test="${shopMaster.deliveryService == 'Y'}">배송가능</c:when>
                            <c:when test="${shopMaster.deliveryService == 'N'}">새송서비스 없음</c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">기본배달 서비스비</td>
                    <td class="value"><fmt:formatNumber type="number" pattern="##0.00" value="${shopMaster.deliveryBaseCharge}"/> $</td>
                    <td class="label">배달비 / Km</td>
                    <td class="value"><fmt:formatNumber type="number" pattern="##0.00" value="${shopMaster.deliveryFeePerKm}"/> $</td>
                </tr>
                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle" style="color: #b3b3b3;font-weight: normal;"> 배송비 Base주소 : 배송비 계산을 위한 기준 주소(입력하지 않으면 Shop주소가 사용됩니다.)</span>
                    </td>
                </tr>
                <tr>
                    <td class="label">주소</td>
                    <td class="value" colspan="3">${shopMaster.forDeliverCalcAddressFormattedAddress}</td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle"> 샵 안내(Notice)</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="value" colspan="4">${shopMaster.notice}</td>
                </tr>
                <tr style="height: 10px;">
                    <td colspan="4"></td>
                </tr>


            </table>
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
                        <a href="javascript:goList();" class="btn btn-info">&nbsp;&nbsp; 목록 &nbsp;&nbsp;</a>
                        <a href="javascript:goModify('${shopMaster.shopId}');" class="btn btn-primary">수정</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

</table>

<script type="text/javascript">
    var SHOP_ID = "${shopMaster.shopId}";
    var ACTION_MODE = "MODIFY";
</script>
</body>
</html>