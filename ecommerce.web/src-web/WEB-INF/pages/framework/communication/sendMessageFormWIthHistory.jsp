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
<script src="/resources/js/melfood/customer/customer.mypage.myinfo.js?ver=<%=Ctx.releaseVersion%>"></script>
<script type="text/javascript">
    $(document).ready(function () {
    }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

</script>

<script type="text/javascript">
    function goList(){
        var searchDateFrom = $("#searchDateFrom").val();
        var searchDateTo = $("#searchDateTo").val();
        document.location.href = "/customer/mypage/myCommunication.yum?searchDateFrom=" + searchDateFrom + "&searchDateTo=" + searchDateTo;
    }
</script>

<style>
     .img-circle {
          border-radius: 50%;
     }
     .rouned-table {
          margin: 0px;
          border-collapse: separate;
          border-spacing: 0px;

     }
     .rouned-td {
          border-radius: 5px;
          background-color: #369;
          color: white;
          border: 5px solid #C8C8C8;
     }​
</style>
</head>

<script type="text/javascript">

    var checkObject = [];

    function validateForm() {

        var count;
        var elementObj = "";
        var validation = true;

        // 값검증 결과 초기화
        for (count = 0; count < checkObject.length; count++) {
            elementObj = "#" + checkObject[count];
            $(elementObj).css({'background': '#ECF5FF', 'border-color': '#DFDFDF'});
        }
        checkObject = [];
        var prefix = "- &nbsp;&nbsp;";
        var message = "";


        var contents = $('#contents').val();

        if (contents == "") {
            message = message + prefix + "Message 입력은 필수입니다.<br>";
            checkObject[checkObject.length] = "contents";
            validation = false;
        }

        // 검증된 필드들을 마킹한다.
        for (count = 0; count < checkObject.length; count++) {
            elementObj = "#" + checkObject[count];
            $(elementObj).css({'background': '#fffacd', 'border-color': '#DF0000', 'border': '1px solid #f00'});
        }
        if (validation == false) {
            // 오류가 있는 경우 경고 창을 보여준다.
            warningPopup(message);
        }
        return validation;
    }


    function sendMessage() {
        var contents = $('#contents').val();
        var sendEmail = false;
        var sendSMS = false;

        if (validateForm() == false) return;

        progress(true);
        $.ajax({
            url: "/framework/communicationmanager/sendMessage.yum",
            data: {
                contents: contents,
                sendEmail: sendEmail,
                sendSMS: sendSMS,
                receiverUserId : '${receiverUser.userId}'
            },
            success: callbackSave
        });
    }

    function callbackSave(data) {

        var message = data.message;
        var resultCode = data.resultCode;

        progress(false);
        if (resultCode != "0") {
            warningPopup(data.message);
        } else {
            // infoPopup(data.message);
            BootstrapDialog.alert({
                title: 'INFO  :: 호주가 즐거운 이유, 멜푸드!!',
                message: "메시지가 정상적으로 발송되었습니다.",
                type: BootstrapDialog.TYPE_PRIMARY, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
                closable: true, // Default value is false
                draggable: true, // Default value is false
                buttonLabel: '닫기', // Default value is 'OK',
                btnOKClass: 'btn-warning', // If you didn't specify it, dialog type will be used,
                callback: function(result) {
                    if(result) {
                        parent.closeAskQuestionPopupPopup();
                    }
                }
            });

        }
    }
</script>


<body>

<div class="row">
     <div class="col-sm-12">
          <table class="detail_table" style="width: 100%;">
               <tr>
                    <td class="value" style="padding-bottom: 3px;color: #ACADAF;">
                         TO : <span style="color: #3c3c3c;">${receiverUser.userName}</span>
                         ${receiverUser.userId} / ${receiverUser.email}
                    </td>
               </tr>
               <tr>
                    <td class="value"><textarea class="form-control" rows="3" id="contents" name="contents" maxlength="80"></textarea></td>
               </tr>
               <tr>
                    <td class="value" style="padding-bottom: 3px;color: #ACADAF;text-align: right;">80자</td>
               </tr>
          </table>

          <table class="action_button_table" width="100%">
               <tr>
                    <td>
                         <a href="javascript:parent.closeAskQuestionPopupPopup();" class="btn btn-info">&nbsp;&nbsp; Cancel &nbsp;&nbsp;</a>
                         <a href="javascript:sendMessage();" class="btn btn-primary">Send</a>
                    </td>
               </tr>
          </table>
     </div>
</div>

<div class="row">
     <div class="col-md-12" style="padding: 0px;">
          <!-- 대화내용 -->
          <c:forEach var="entry" items="${communicationList}" varStatus="count" begin="0">
               <c:choose>
                    <c:when test="${sessionUserId == entry.writeFrom}">
                         <table style="width: 600px;">
                              <tr>
                                   <td>
                                        <div class="chat-dialogbox">
                                             <div class="chat-body" style="max-width: 400px;">
                                                  <span class="chat-tip chat-tip-right"></span>
                                                  <div class="chat-message">
                                                       <table style="width: 100%;">
                                                            <tr>
                                                                 <td style="font-size: 13px;color: #1E1E1E;">
                                                                      <c:choose>
                                                                           <c:when test="${entry.category == 'QNA'}">[QnA]</c:when>
                                                                      </c:choose>
                                                                      ${entry.contents}
                                                                 </td>
                                                            </tr>
                                                            <tr>
                                                                 <td style="text-align: left;font-size: 10px;padding-top: 5px;">To :
                                                                      <c:choose>
                                                                           <c:when test="${entry.writeToName == '' || entry.writeToName == null}">_(삭제된사용자)</c:when>
                                                                           <c:otherwise>${entry.writeToName}</c:otherwise>
                                                                      </c:choose>
                                                                 </td>
                                                            </tr>
                                                            <tr><td style="text-align: left;"><span style="color: #AEACAF; font-size: 10px;">${entry.createDatetime}</span></td></tr>
                                                       </table>
                                                  </div>
                                             </div>
                                        </div>
                                   </td>
                                   <td style="width: 150px;">&nbsp;</td>
                              </tr>
                         </table>
                    </c:when>

                    <c:otherwise>
                         <table style="width: 600px;">
                              <tr>
                                   <td style="width: 150px;">&nbsp;</td>
                                   <td>
                                        <div class="chat-dialogbox">
                                             <div class="chat-body" style="background-color: #F5F5F5;max-width: 400px;">
                                                  <span class="chat-tip chat-tip-left"></span>
                                                  <div class="chat-message">
                                                       <table style="width: 100%;">
                                                            <tr><td style="font-size: 13px;color: #727272;">${entry.contents}</td></tr>
                                                            <tr>
                                                                 <td style="text-align: right;font-size: 10px;">From :
                                                                      <c:choose>
                                                                           <c:when test="${entry.writeFromName == '' || entry.writeFromName == null}">_(삭제된사용자)</c:when>
                                                                           <c:otherwise>${entry.writeFromName}</c:otherwise>
                                                                      </c:choose>
                                                                 </td>
                                                            </tr>
                                                            <tr><td style="text-align: right;"><span style="color: #AEACAF; font-size: 10px;"><fmt:formatDate pattern = "yyyy-MM-dd" value = "${now}" />${entry.createDatetime}</span></td></tr>
                                                       </table>
                                                  </div>
                                             </div>
                                        </div>
                                   </td>
                              </tr>
                         </table>



                    </c:otherwise>
               </c:choose>
          </c:forEach>
     </div>
</div>







     <br />
     <br />
     <br />
     
     <script type="text/javascript">
     </script>
</body>
</html>