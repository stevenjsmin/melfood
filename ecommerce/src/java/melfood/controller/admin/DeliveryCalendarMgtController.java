/**
 * 2016 DeliveryCalendarMgtController.java
 * Created by steven.min
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

import com.google.maps.model.GeocodingResult;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.gmap.MelfoodGoogleMapService;
import melfood.framework.gmap.gson.dto.GMapResult;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import melfood.shopping.contract.ContractInfoService;
import melfood.shopping.delivery.DeliveryCalendar;
import melfood.shopping.delivery.DeliveryCalendarService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author steven.min
 */
@Controller
@RequestMapping("/admin/deliverycalendarmgt")
public class DeliveryCalendarMgtController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryCalendarMgtController.class);

    @Autowired
    private DeliveryCalendarService deliveryCalendarService;

    @Autowired
    private ContractInfoService contractInfoService;

    @Autowired
    private MelfoodGoogleMapService melfoodGoogleMapService;

    @RequestMapping("/Main")
    public ModelAndView main(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/deliverycalendarmgt/main");

        User seller = new User();
        seller.setUseYn("Y");

        Properties htmlProperty = new Properties();
        List<Option> contractorOptions = contractInfoService.getSellers(seller);
        htmlProperty = new Properties("sellerId");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String searchDateFrom = df.format(cal.getTime());
        mav.addObject("defaultSearchDateFrom", searchDateFrom);

        return mav;
    }

    @RequestMapping(value = "/getDeliveryCalendars", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getDeliveryCalendars(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        DeliveryCalendar deliveryCalendar = new DeliveryCalendar();

        // For Pagination
        deliveryCalendar.setPagenationPage(getPage(request));
        deliveryCalendar.setPagenationPageSize(getPageSize(request));

        String sellerId = request.getParameter("sellerId");
        String searchDateFrom = request.getParameter("searchDateFrom");
        String searchDateTo = request.getParameter("searchDateTo");

        // 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
        if (StringUtils.isBlank(searchDateFrom)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            searchDateFrom = df.format(cal.getTime());
        }
        deliveryCalendar.setSearchDateFrom(searchDateFrom);

        if (StringUtils.isNotBlank(searchDateTo)) deliveryCalendar.setSearchDateTo(searchDateTo);
        if (StringUtils.isNotBlank(searchDateTo)) deliveryCalendar.setSearchDateTo(searchDateTo);
        if (StringUtils.isNotBlank(sellerId)) deliveryCalendar.setSellerId(sellerId);

        Integer totalCount = 0;
        List<DeliveryCalendar> list = deliveryCalendarService.getDeliveryCalendars(deliveryCalendar);
        totalCount = deliveryCalendarService.getTotalCntForDeliveryCalendars(deliveryCalendar);

        model.put("totalCount", totalCount);
        model.put("list", list);

        return model;
    }

    @RequestMapping(value = "/enableAndDisableDiliveryCalendar", produces = "application/json")
    @ResponseBody
    public Map<String, Object> enableAndDisableDiliveryCalendar(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        int updateCnt = 0;

        String deliveryCalendarId = request.getParameter("deliveryCalendarId");
        DeliveryCalendar newDeliveryCalendar =  new DeliveryCalendar(deliveryCalendarId);

        try {
            if (StringUtils.isBlank(deliveryCalendarId)) throw new Exception("[deliveryCalendarId]  이항목(들)은 빈 값이 될 수 없습니다.");

            DeliveryCalendar deliveryCalendar = deliveryCalendarService.getDeliveryCalendar(new DeliveryCalendar(deliveryCalendarId));
            if (StringUtils.equalsIgnoreCase(deliveryCalendar.getUseYn(), "N")) {
                newDeliveryCalendar.setUseYn("Y");
            } else {
                newDeliveryCalendar.setUseYn("N");
            }

            updateCnt = deliveryCalendarService.modifyDeliveryCalendarForNotNull(newDeliveryCalendar);

            model.put("resultCode", "0");
            model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping(value = "/deleteDeliveryCalendar", produces = "application/json")
    @ResponseBody
    public Map<String, Object> deleteDeliveryCalendar(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        int updateCnt = 0;

        String deliveryCalendarId = request.getParameter("deliveryCalendarId");

        try {
            if (StringUtils.isBlank(deliveryCalendarId)) throw new Exception("[deliveryCalendarId]  이항목(들)은 빈 값이 될 수 없습니다.");
            DeliveryCalendar deliveryCalendar = new DeliveryCalendar(deliveryCalendarId);

            updateCnt = deliveryCalendarService.deleteDeliveryCalendar(deliveryCalendar);

            model.put("resultCode", "0");
            model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping("/addCalendarForm")
    public ModelAndView addCalendarForm(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/admin/deliverycalendarmgt/addCalendarForm");
        Properties htmlProperty = new Properties();

        boolean isSessionSeller = StringUtils.equalsIgnoreCase(sessionUser.getSessionRole().getRoleId(), "SELLER");

        User seller = new User();
        seller.setUseYn("Y");
        List<Option> contractorOptions = contractInfoService.getSellers(seller);
        htmlProperty = new Properties("sellerId");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

        List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", "VIC");
        htmlProperty = new Properties("deliveryBaseAddressState");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxDeliveryBaseAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

        List<Option> amPmOptions = codeService.getValueCmbxOptions("DELIVER_MGT", "DELIVER_AMPM");
        htmlProperty = new Properties("amPm");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxAmPm", codeService.generateCmbx(amPmOptions, htmlProperty));

        return mav;
    }

    @RequestMapping(value = "/addCalendar", produces = "application/json")
    @ResponseBody
    public Map<String, Object> addCalendar(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        int updateCnt = 0;
        GMapResult mapResult = null;

        String sellerId = request.getParameter("sellerId");
        String yyyyMmDd = request.getParameter("yyyyMmDd");
        String amPm = request.getParameter("amPm");
        String estDeliverytimeFromHhmm = request.getParameter("estDeliverytimeFromHhmm");
        String estDeliverytimeToHhmm = request.getParameter("estDeliverytimeToHhmm");

        String deliveryBaseAddressPostcode = request.getParameter("deliveryBaseAddressPostcode");
        String deliveryBaseAddressState = request.getParameter("deliveryBaseAddressState");
        String deliveryBaseAddressSuburb = request.getParameter("deliveryBaseAddressSuburb");
        String deliveryBaseAddressStreet = request.getParameter("deliveryBaseAddressStreet");
        String deliveryBaseAddressNote = request.getParameter("deliveryBaseAddressNote");

        String deliveryFeePerKm = request.getParameter("deliveryFeePerKm");
        String deliveryBasicFee = request.getParameter("deliveryBasicFee");
        String deliveryLimitKm = request.getParameter("deliveryLimitKm");

        try {
            if (StringUtils.isBlank(sellerId) || StringUtils.isBlank(yyyyMmDd) || StringUtils.isBlank(deliveryBaseAddressPostcode) || StringUtils.isBlank(deliveryBaseAddressState) || StringUtils.isBlank(deliveryBaseAddressSuburb) || StringUtils.isBlank(deliveryBaseAddressStreet))
                throw new Exception("[SELLER_ID | YYYY_MM_DD | AM_PM | IS_PICKUP | deliveryBaseAddressPostcode | deliveryBaseAddressState | deliveryBaseAddressSuburb | deliveryBaseAddressStreet]  이항목(들)은 빈 값이 될 수 없습니다.");

            DeliveryCalendar deliveryCalendar = new DeliveryCalendar(sellerId, yyyyMmDd);

            if (StringUtils.isNotBlank(amPm)) deliveryCalendar.setAmPm(amPm);
            if (StringUtils.isNotBlank(deliveryBaseAddressPostcode)) deliveryCalendar.setDeliveryBaseAddressPostcode(deliveryBaseAddressPostcode);
            if (StringUtils.isNotBlank(deliveryBaseAddressState)) deliveryCalendar.setDeliveryBaseAddressState(deliveryBaseAddressState);
            if (StringUtils.isNotBlank(deliveryBaseAddressSuburb)) deliveryCalendar.setDeliveryBaseAddressSuburb(deliveryBaseAddressSuburb);
            if (StringUtils.isNotBlank(deliveryBaseAddressStreet)) deliveryCalendar.setDeliveryBaseAddressStreet(deliveryBaseAddressStreet);
            if (StringUtils.isNotBlank(deliveryBaseAddressNote)) deliveryCalendar.setDeliveryBaseAddressNote(deliveryBaseAddressNote);


            if (StringUtils.isNotBlank(amPm)) deliveryCalendar.setAmPm(amPm);
            if (StringUtils.isNotBlank(estDeliverytimeFromHhmm)) deliveryCalendar.setEstDeliverytimeFromHhmm(estDeliverytimeFromHhmm);
            if (StringUtils.isNotBlank(estDeliverytimeToHhmm)) deliveryCalendar.setEstDeliverytimeToHhmm(estDeliverytimeToHhmm);
            if (StringUtils.isNotBlank(deliveryBaseAddressNote)) deliveryCalendar.setDeliveryBaseAddressNote(deliveryBaseAddressNote);

            if (StringUtils.isNotBlank(deliveryFeePerKm)) {
                deliveryCalendar.setDeliveryFeePerKm(Float.parseFloat(deliveryFeePerKm));
            } else {
                deliveryCalendar.setDeliveryFeePerKm(0.0f);
            }
            if (StringUtils.isNotBlank(deliveryBasicFee)) {
                deliveryCalendar.setDeliveryBasicFee(Float.parseFloat(deliveryBasicFee));
            } else {
                deliveryCalendar.setDeliveryBasicFee(0.0f);
            }
            if (StringUtils.isNotBlank(deliveryLimitKm)) {
                deliveryCalendar.setDeliveryLimitKm(Integer.parseInt(deliveryLimitKm));
            } else {
                deliveryCalendar.setDeliveryLimitKm(0);
            }

            GeocodingResult geoResult = null;
            String deliveryBaseAddressGmapLatitude = null;
            String deliveryBaseAddressGmapLongitude = null;
            String deliveryBaseAddressGmapFormattedAddress = null;

            geoResult = melfoodGoogleMapService.lookupGMap(deliveryCalendar.getDeliveryBaseAddressStreet() + " " + deliveryCalendar.getDeliveryBaseAddressSuburb() + " " + deliveryCalendar.getDeliveryBaseAddressState() + " " + deliveryCalendar.getDeliveryBaseAddressPostcode());
            if (geoResult != null) {
                deliveryBaseAddressGmapLatitude = Double.toString(geoResult.geometry.location.lat);
                deliveryBaseAddressGmapLongitude = Double.toString(geoResult.geometry.location.lng);
                deliveryBaseAddressGmapFormattedAddress = geoResult.formattedAddress;

                deliveryCalendar.setDeliveryBaseAddressGmapLatitude(deliveryBaseAddressGmapLatitude);
                deliveryCalendar.setDeliveryBaseAddressGmapLongitude(deliveryBaseAddressGmapLongitude);
                deliveryCalendar.setDeliveryBaseAddressGmapFormattedAddress(deliveryBaseAddressGmapFormattedAddress);
            } else {
                // 상세주소를 제외한 주소로 다시한번 시도해본다.
                geoResult = melfoodGoogleMapService.lookupGMap(deliveryCalendar.getDeliveryBaseAddressSuburb() + " " + deliveryCalendar.getDeliveryBaseAddressState() + " " + deliveryCalendar.getDeliveryBaseAddressPostcode());

                deliveryBaseAddressGmapLatitude = Double.toString(geoResult.geometry.location.lat);
                deliveryBaseAddressGmapLongitude = Double.toString(geoResult.geometry.location.lng);
                deliveryBaseAddressGmapFormattedAddress = geoResult.formattedAddress;

                deliveryCalendar.setDeliveryBaseAddressGmapLatitude(deliveryBaseAddressGmapLatitude);
                deliveryCalendar.setDeliveryBaseAddressGmapLongitude(deliveryBaseAddressGmapLongitude);
                deliveryCalendar.setDeliveryBaseAddressGmapFormattedAddress(deliveryBaseAddressGmapFormattedAddress);
            }

            if (StringUtils.isBlank(deliveryBaseAddressGmapLatitude) || StringUtils.isBlank(deliveryBaseAddressGmapLongitude) || StringUtils.isBlank(deliveryBaseAddressGmapFormattedAddress)) {
                throw new Exception("올바른 배송지역 주소를 입력해주세요.");
            }


            deliveryCalendar.setUseYn("Y");
            updateCnt = deliveryCalendarService.insertDeliveryCalendar(deliveryCalendar);

            model.put("resultCode", "0");
            model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

}
