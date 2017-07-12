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
                            <td style="text-align: left;color: #DDDEE0;padding-top: 0px;padding-left: 10px;">우와한 가족들 협동조합 파트너스가 되어주세요</td>
                        </tr>
                        <tr>
                            <td style="text-align: left;color: #DDDEE0;padding-top: 5px;padding-left: 10px;height: 40px;font-size: 15px;">
                                <span style="font-weight: bold;text-decoration: underline">로즈베이커리</span>
                                | <span style="font-weight: bold;text-decoration: underline">금.토일엔 삼겹살</span>
                                | <span style="font-weight: bold;text-decoration: underline">족발과의 동침</span>
                                | <span style="font-weight: bold;text-decoration: underline">순이네 반찬</span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row" style="text-align: center; padding-top: 20px;padding-bottom: 20px;">
    <div class="col-sm-2 col-centered" style="text-align: right;">
        <img src="/resources/image/next-chef-org.jpg" style="width: 200px;">
    </div>
    <div class="col-sm-7 col-centered">
        <div class="panel panel-default">
            <div class="panel-heading" style="padding-top: 20px;padding-bottom: 20px;padding-left: 20px;">
                <h3 class="panel-title" style="font-weight: bold;color: #006F3C;">맛있는 먹거리를 만들수있는 재주를 가지고 계신가요 ?</h3>
            </div>
            <div class="panel-body" style="padding-left: 20px;padding-bottom: 5px;padding-top: 10px;">
                <span style="color: #575758;">그럼 함께 <b>공굴러</b>(<i>공동구매 Organizer</i>)가 되세요.</span>

                <table style="width: 100%;margin-top: 30px;">
                    <tr>
                        <td style="text-align: center;"><img src="/resources/image/map_marker.png" style="width: 30px;"></td>
                        <td style="width: 20px;"></td>
                        <td style="font-size: 15px;vertical-align: middle;font-weight: bold;color: #575758;">
                            멜푸드는 지리기반 데이터에의해 소비자가 원하는 수요를 통계적 분석을 통해 공굴러들이 활동 할 수 있도록 지원합니다.</td>
                    </tr>
                    <tr style="height: 20px;"><td colspan="3"></td></tr>
                    <tr>
                        <td style="text-align: center;"><img src="/resources/image/trends.png" style="width: 30px;"></td>
                        <td style="width: 20px;"></td>
                        <td style="font-size: 15px;vertical-align: middle;font-weight: bold;color: #575758;">
                            멜푸드는 지속적인 소비자 분석을 통해 마켓트랜드를 활용 할 수 있도록 공굴러들의 활동을 지원합니다.
                        </td>
                    </tr>
                    <tr style="height: 30px;"><td colspan="3"></td></tr>
                    <tr>
                        <td style="text-align: center;"><img src="/resources/image/email.png" style="width: 30px;"></td>
                        <td style="width: 20px;"></td>
                        <td style="font-size: 15px;vertical-align: middle;font-weight: bold;color: #575758;">
                            멜푸드는 소비자와 소통할수있는 커뮤니케이션 체널을 다양한 방법으로 지원합니다.
                        </td>
                    </tr>
                    <tr style="height: 20px;"><td colspan="3"></td></tr>
                    <tr>
                        <td colspan="3" style="text-align: right;color: #8B8A8A;">가입 신청 후 일정한 협의를 거처(특히 먹거리 아이템의 경우) 진행 될수 있습니다.</td>
                    </tr>
                    <tr style="height: 20px;"><td colspan="3"></td></tr>
                    <tr>
                        <td style="text-align: center;"><i class="fa fa-hand-o-down fa-2x" aria-hidden="true" style="color: #8B8A8A;"></i></td>
                        <td style="width: 20px;"></td>
                        <td style="text-align: left;color: #8B8A8A;vertical-align: middle"> 아래 "<b>Contact us</b>" Form을 통해 가입문의 해주세요.</td>
                    </tr>

                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>