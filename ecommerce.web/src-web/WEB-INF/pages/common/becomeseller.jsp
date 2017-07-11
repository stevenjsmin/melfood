<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="melfood.framework.Ctx" %>
<%@ page import="melfood.framework.auth.SessionUserInfo" %>
<%@ page import="melfood.framework.system.BeanHelper" %>
<%@ page import="melfood.framework.role.Role"%>
<%@ page import="melfood.framework.user.User"%>
<%@ page import="melfood.framework.auth.AuthService"%>
<%@ page import="melfood.framework.auth.AuthServiceImpl"%>

<%
    AuthService userService = new AuthServiceImpl();
    SessionUserInfo sessionUser = BeanHelper.getSessionUser();

    if(sessionUser != null) {
        pageContext.setAttribute("sessionUser", sessionUser);
    } else {
        pageContext.setAttribute("sessionUser", null);
    }

%>

<!doctype html>
<head>
    <style>
        /**
         * 상위에서 정의된 레이아웃 재정의함
         */
        .content {
            width: 100%;
            padding: 0px 0px 0px 0px
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
        .venueInfoWrapper {
            background: linear-gradient(rgba(0,0,0,0.1), rgba(0,0,0,0.7)),url(/resources/image/pizza-desktop.jpg);
            background-size: cover;
            background-repeat: no-repeat;
            background-color: #797876;
            box-sizing: border-box;
            padding: 20px 0;
            height: 150px;
        }
        /* centered columns styles */
        .col-centered {
            display:inline-block;
            float:none;
            /* reset the text-align */
            text-align:left;
            /* inline-block space fix */
            margin-right:-4px;
            vertical-align: top;
        }
    </style>

    <script type="text/javascript">
        $(document).ready(function() {
        })
    </script>

    <script type="text/javascript">

    </script>


</head>

<body>

<!-- 협동조합 파트너스 -->
<div class="row" align="center" style="background-color: #1E1E1E;padding-top: 5px;padding-bottom: 0px;margin-top: 0px;">
    <div class="col-sm-12"></div>
</div>
<div class="row">
    <div class="col-sm-12" style="padding: 0px 0px;">
        <div class="venueInfoWrapper">

            <div class="row">
                <div class="col-sm-6" style="padding: 0px 0px;">
                    <table style="width: 100%;">
                        <tr>
                            <td style="text-align: left;color: #DDDEE0;padding-top: 0px;padding-left: 50px;">우와한 가족들 협동조합 파트너스가 되어주세요</td>
                        </tr>
                        <tr>
                            <td style="text-align: left;color: #DDDEE0;padding-top: 0px;padding-left: 50px;">맛있는 것을 만드는 재주가 있다면 공굴러가 되어보세요. </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-4">
        <div class="well well-lg">
            <img src="/resources/image/next-chef-org.jpg" style="width: 400px;">
        </div>
    </div>
    <div class="col-sm-4">
        <div class="well well-lg">
            가입절자..
        </div>
    </div>
</div>

</body>
</html>