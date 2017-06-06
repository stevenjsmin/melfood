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

<script type="text/javascript">
$(document).ready(function () {
    $("#fileUpload").kendoUpload({
        async: {
            saveUrl: "//framework/usermanager/profileImageUpload.yum?userId=${user.userId}",
            removeUrl: "//framework/usermanager/removeFile.yum",
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
    	if(data.resultCode != '0'){
    		warningPopup("<b>프로파일 이미지 갱신 실패 : </b>" + data.message);
    		$("#profilePhotoId", parent.document).attr("src","/resources/image/profile_photo.png");
    	} else {
    		infoPopup("정상적으로 프로파일 이미지가 갱신되었습니다. ");
    		$("#profilePhotoId", parent.document).attr("src","/img/?f=" + data.user.profilePhotoId);
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

</head>

<body>

    <span class="subtitle">Attachment Images</span>
    <hr class="subtitle"/>
    <table style="width: 100%;">
          <tr>
               <td>
                    <table class="detail_table">
                         <tr>
                              <td class="label" style="width: 250px;">File Upload</td>
                              <td class="value" style="width: 500px;"><input type="file" name="files" id="fileUpload"/></td>
                         </tr>
                    </table>
               </td>
          </tr>
    </table>
     
    
     
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <!-- Table List -->
    <!-- ++++++++++++++++++++++++++++++++++++++++++ -->
    <div id="grid_panel_main"></div>     
    <br/> 
     
    <table class="action_button_table" width="100%">
         <tr>
              <td style="text-align: right;">
                   <a href="javascript:parent.closeProfilePhotoWindow();" class="btn btn-primary">&nbsp;&nbsp; Done &nbsp;&nbsp;</a>
              </td>
         </tr>
    </table>
               
     <script type="text/javascript">
          var ACTION_MODE = "ADD";
     </script>     
</body>
</html>