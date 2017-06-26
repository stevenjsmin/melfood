<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="row" style="padding-top: 10px;">
    <div class="col-sm-4">
        <!-- 회사소개 -->
        <!-- 기타안내 -->
        <table style="width: 100%;">
            <tr>
                <td style="font-size: 13px;font-weight: bold;color: #A2A4A4;">
                    <span style="font-size: 13px;color: #BCBCBC;">Melfood </span>@ 우와한 가족들의 협동조합
                </td>
            </tr>
            <tr><td style="height: 30px;"></td></tr>
            <tr>
                <td>
                    <img src="/resources/image/logo_melfood_2.png" width="120px;">

                </td>
            </tr>
            <tr><td style="height: 20px;"></td></tr>
            <tr>
                <td style="color: #A2A4A4;font-size: 13px">
                    4 Torresdale Road, South Morang VIC 3752, Australia<br/>
                    ABN : 1234 54 5554 333<br/><br/>
                    1300 599 888 / (Monday to Friday 8.30am to 5:30pm (AEST/AEDT)
                </td>
            </tr>
            <tr><td style="height: 20px;"></td></tr>
            <tr>
                <td style="padding-top: 20px;color: #A2A4A4;">
                        Copyright © 2017 우와한 가족들의 협동조합
                </td>
            </tr>
        </table>


    </div>
    <div class="col-sm-5">
        <!-- 문의사항 -->
        <table style="width: 100%;">
            <colgroup>
                <col width="50px"/>
                <col width="*"/>
            </colgroup>
            <tr>
                <td colspan="2">
                    <span style="font-size: 13px;color: #A2A4A4;font-weight: bold;">문의 . Question ? </span>
                </td>
            </tr>
            <tr>
                    <td></td>
                    <td style="padding: 5px;"><input class="form-control" type="text" id="customerMobile" name="customerMobile" value='' placeholder="모바일 번호" maxlength="10" style="width: 200px;background-color: #6e6e6e;color: #D4D4D4;border-color: #5e5e5e;"/></td>
            </tr>
            <tr>
                <td></td>
                <td style="padding: 5px;"><input class="form-control" type="text" id="customerEmail" name="customerEmail" value='' placeholder="이메일 주소" style="background-color: #6e6e6e;color: #D4D4D4;border-color: #5e5e5e;"/></td>
            </tr>
            <tr>
                <td></td>
                <td style="padding: 5px;"><textarea class="form-control" rows="3" id="customerQuestion" name="customerQuestion" style="background-color: #6e6e6e;color: #D4D4D4;border-color: #5e5e5e;"></textarea></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: right;padding: 5px;"><button type="submit" class="btn btn-default" style="background-color: #7E7C7F;border-color: #5e5e5e;">SEND MESSAGE</button></td>
            </tr>

        </table>
    </div>
</div>
