<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="/resources/js/melfood/framework/grouppurchase.js?ver=<%=Ctx.releaseVersion%>"></script>

    <script type="text/javascript">
        $(document).ready(function () {

            $("#fileUpload").kendoUpload({
                async: {
                    saveUrl: "/admin/productmgt/image/uploadFile.yum?prodId=",
                    removeUrl: "/admin/productmgt/image/removeFile.yum",
                    removeField: "removeFile",
                    autoUpload: true,
                    batch: true,
                },
                showFileList: false,
                localization: {
                    statusFailed: "Failed to file upload. Please check it again and retry.",
                    uploadSelectedFiles: "Upload file(s)"
                },
                complete: onComplete,
                error: onError
            });

            function onComplete(e) {
                search();
            }
            function onError(e) {
                var files = e.files;
                if (e.operation == "upload") {
                    warningPopup("Failed to upload : " + files[0].name);
                }
            }

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>

    <style>
    </style>
</head>


<body>

<table>
    <tr>
        <td valign="top">
            <table class="detail_table">
                <colgroup>
                    <col width="200px" />
                    <col width="300px" />
                    <col width="200px" />
                    <col width="300px" />
                </colgroup>


                <tr>
                    <td colspan="4">
                        <span class="subtitle"> 동동구매 상품이미지</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table style="width: 850px;">
                            <tr>
                                <td>
                                    <table class="detail_table">
                                        <tr>
                                            <td class="label" style="width: 250px;">File Upload</td>
                                            <td class="value" style="width: 500px;"><input type="file" name="files" id="fileUpload"/></td>
                                            <td class="value" style="width: 70px;"><img onclick="openImageViwer();" src="/resources/image/imgviewer.png" style="height: 30px; width: 30px;"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>

                        <div id="grid_panel_main"></div>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br />
                        <br />
                        <span class="subtitle"> 공동구매 기본정보</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>



                <tr>
                    <td class="label">Title :</td>
                    <td class="value" colspan="3">${groupPurchase.groupPurchaseTitle}</td>
                </tr>
                <tr>
                    <td class="label">Subtitle :</td>
                    <td class="value" colspan="2">${groupPurchase.groupPurchaseSubtitle}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">공동구매 주관자 :</td>
                    <td class="value">${groupPurchase.purchaseOrganizer}</td>
                    <td class="label">최소 참여금액 :</td>
                    <td class="value">${groupPurchase.minimumPurchaseAmount}</td>
                </tr>
                <tr>
                    <td class="label">할인비율/금액 :</td>
                    <td class="value">${groupPurchase.discountMethod}</td>
                    <td class="label">비율/금액 :</td>
                    <td class="value">${groupPurchase.discountFixedAmount}/${groupPurchase.discountRateValue}</td>
                </tr>


                <tr>
                    <td class="label"><span class="required">* </span>공동구매 기간 :</td>
                    <td class="value" colspan="3">${groupPurchase.orderingStartDt} ~ ${groupPurchase.orderingEndDt}</td>
                </tr>
                <tr>
                    <td class="label">상태 :</td>
                    <td class="value">${groupPurchase.stopSelling}</td>
                </tr>
                <tr>
                    <td class="label">공동구매 정지이유 :</td>
                    <td class="value" colspan="2">${groupPurchase.stopSellingReason}</td>
                    <td></td>
                </tr>

                <tr>
                    <td class="label">장소</td>
                    <td class="value" colspan="3">${groupPurchase.marketAddressStreet}, ${groupPurchase.marketAddressSuburb}</td>
                </tr>
                <tr>
                    <td class="label">비고</td>
                    <td class="value" colspan="3">${groupPurchase.marketAddressComment}</td>
                    <td></td>
                </tr>

                <tr>
                    <td colspan="4">
                        <br/>
                        <span class="subtitle"> 기타공지 메모</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>
                <tr>
                    <td class="value" colspan="4">${groupPurchase.groupPurchaseNotice}</td>
                </tr>



                <tr>
                    <td colspan="4">
                        <br />
                        <br />
                        <span class="subtitle"> 공동구매 상품목록</span>
                        <hr class="subtitle"/>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table class="action_button_table" width="100%">
                            <tr>
                                <td>
                                    <a href="javascript:save();" class="btn btn-primary">Go BACK</a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<script type="text/javascript">
    var ACTION_MODE = "ADD";
</script>
</body>
</html>