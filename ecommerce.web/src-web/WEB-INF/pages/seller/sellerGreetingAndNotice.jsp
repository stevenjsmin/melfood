<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<head>
	<script type="text/javascript">
		function goOrder(prodId){
		  document.location.href = "/shop/order/product.yum?prodId=" + prodId;
		}
		
		function sendMessage() {
		      var writeTo = '${seller.userId}';
		      var contents = $('#message').val();

		   	if(contents == '' || contents == null || '${seller.userId}' == '') {
		   		return;
		 	}
		   	
		      
		      $.ajax({
		           url  : "/shop/discussSave.yum",
		           data      : {
		        	   writeTo : writeTo,
		        	   contents : contents,
		        	   actionMode : 'ADD'
		           },
		           success : callbackSave
		      });  			
		}
		
		function callbackSave(data) {
		      var message = data.message;
		      var resultCode = data.resultCode;

		      if (resultCode != "0") {
		           warningPopup(data.message);
		      } else {
			  		BootstrapDialog.confirm({
			            title: 'INFO  :: 호주가 즐거운 이유, 멜푸드!!',
			            message: '정상적으로 메시지가 전송되었습니다. 빠른시일내에 답변드리겠습니다. <br/>감사합니다.',
			            type: BootstrapDialog.TYPE_INFO, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
			            closable: true, // Default value is false
			            draggable: true, // Default value is false
			            btnOKLabel: 'OK', // Default value is 'OK',
			            btnOKClass: 'btn-warning', // If you didn't specify it, dialog type will be used,
			            callback: function(result) {
			                if(result) {
			                	location.reload();
			                }
			            }
			        });    		    	  
		      }
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

<body>
    <div class="row">
        <div class="col-md-12" style="padding: 0px;">
		   	<table style="width: 100%;" class="rouned-table">
		   		<tr>
		   			<td colspan="2" style="padding: 5px;text-align: center;background-color: #D7D7D7;" class="rouned-td">
		   				<b><span style="color: #7F7F7F;"><b>${seller.sellerBusinessName}</b></span></b>
		   			</td>
		   		</tr>
		   		<tr>
		   			<td colspan="2" style="padding: 5px;">
		   				<c:choose>
		   					<c:when test="${sessionUser != null }">
				   				<table style="width: 100%;">
				   					<tr>
				   						<td><img src="/img/?f=${seller.profilePhotoId}" style="width: 90px;" class="img-circle"></td>
				   						<td style="vertical-align: middle;padding-left: 10px;">
				   							<br>안녕하세요. <b>${seller.sellerBusinessName}</b>을 찾아주셔서 감사합니다.<br><br>
				   							<span style="color: #8B8B8B;">
				   							제품 구입에관한 문의 사항이 있으시면 언제든지 전화/메일/메신저를 통해 연락 주시면 빠른시간내에 답변드리도록 하겠습니다.<br>
				   							<ul>
				   								<li>전화 : ${seller.mobile }</li>
				   								<li>메일 : ${seller.email }</li>
				   							</ul>
				   							</span>
				   						</td>
				   					</tr>
				   				</table>
		   					</c:when>
		   					<c:otherwise>
				   				<table style="width: 100%;">
				   					<tr>
				   						<td style="vertical-align: middle;padding-left: 10px;">
				   							<br>안녕하세요. <b>${seller.sellerBusinessName}</b>을 찾아주셔서 감사합니다.<br><br>
				   							<span style="color: #8B8B8B;">
				   							제품 구입에관한 문의 사항이 있으시면 언제든지 전화/메일/메신저를 통해 연락 주시면 빠른시간내에 답변드리도록 하겠습니다.<br>
				   							</span>
				   							<br/>
				   							<span style="font-size: 10px;padding-left: 100px;padding-top: 50px;padding-bottom: 50px;">
				   								<a href="javascript:openLoginPopup();" style="font-size: 10px">로그인</a> 후 판매자 연락처/상세 정보 보기.
				   								<br/>
				   								<br/>
				   							</span>
				   						</td>
				   					</tr>
				   				</table>
		   					
		   					</c:otherwise>
		   				</c:choose>
		   				
		   			</td>
		   		</tr>
		   	</table>
        </div>
    </div>

      
    <div class="row">
        <div class="col-md-12" style="padding: 0px;">
        	<!--  판매자 공지사항 -->
			<c:if test="${not empty noticeDiscussList}">
				<ul>
					<c:forEach var="entry" items="${noticeDiscussList}" varStatus="count" begin="0">
							<li><span style="color: #B7141C;">[공지] ${entry.contents}</span> </li>
					</c:forEach>	
				</ul>
			</c:if>
		</div>
	</div>
	
	
	<c:choose>
		<c:when test="${sessionUser != null }">
		    <div class="row">
		        <div class="col-md-12" style="padding: 0px;">
		        	<!--  대화내용입력 -->
		        	<table style="width: 100%;" border="0">
		        	    <tr>
		        	    	<td style="text-align: right;"><img src="/resources/image/talk.png" style="height: 80px;"></td>
		        	    	<td></td>
		        	    </tr>
		        		<tr>
		        			<td style="width: 300px;padding-left: 10px;"><input class="form-control" style="width: 300px;" id="message" name="message" placeholder="대화내용입력 (50자)" value='' maxlength="50"></td>
		        			<td> &nbsp; <button type="button" class="btn btn-default" onclick="sendMessage()">전송</button></td>
		        		</tr>
		        	</table>
				</div>
			</div>
		</c:when>
	</c:choose>		
    <div class="row">
        <div class="col-md-12" style="padding: 0px;">
        	<!-- 대화내용 -->
			<c:forEach var="entry" items="${conversationList}" varStatus="count" begin="0">
				      <c:choose>
				      		<c:when test="${seller.userId == entry.writeFrom}">
								<div class="chat-dialogbox">
									    <div class="chat-body" style="background-color: #F5F5F5;">
									      <span class="chat-tip chat-tip-left"></span>
									      <div class="chat-message">
									        <table style="width: 100%;">
									        	<tr><td style="font-size: 11px;color: #727272;">${entry.contents}</td></tr>
									        	<tr><td style="text-align: right;"><span style="color: #AEACAF; font-size: 10px;">${entry.createDatetime}</span></td></tr>
									        </table>
									      </div>
									    </div>
								</div>					      		
				      		</c:when>
				      		<c:otherwise>
								<div class="chat-dialogbox">
									    <div class="chat-body">
									      <span class="chat-tip chat-tip-right"></span>
									      <div class="chat-message">
									        <table style="width: 100%;">
									        	<tr><td style="font-size: 11px;color: #727272;">${entry.contents}</td></tr>
									        	<tr><td style="text-align: right;"><span style="color: #AEACAF; font-size: 10px;">${entry.createDatetime}</span></td></tr>
									        </table>
									      </div>
									    </div>
								</div>				      		
				      		</c:otherwise>
				      </c:choose>	
			</c:forEach>			
		</div>
	</div>	
	
	<c:choose>
		<c:when test="${sessionUser != null }">
		    <div class="row">
	      		<div class="col-md-12" style="text-align: right;padding-right: 10px;">
	      			<a href="javascript:allNotice();"><span style="font-size: 9px; color: #DAC20D;">모든 대화내용보기</span></a>
	      		</div>
			</div>
			<br/>
		</c:when>
	</c:choose>	
	
</body>
</html>