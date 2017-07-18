var checkObject = [];

$(document).ready(function () {

    $("#shopCredit").kendoNumericTextBox({
        format: "0", decimals: 0, min: 0, max: 5000
    });

    $("#deliveryLimitKm").kendoNumericTextBox({
        format: "0", decimals: 0, min: 0, max: 2000
    });

    $("#minimumPurchaseAmount").kendoNumericTextBox({
        max: 99999,
        min: 0.00,
        step: 0.50,
        format: "c2"
    });
    $("#maximumPurchaseAmount").kendoNumericTextBox({
        max: 99999,
        min: 0.00,
        step: 0.50,
        format: "c2"
    });

    $("#discountRateValue").kendoNumericTextBox({
        format: "p1",
        max: 1.0,
        min: 0.00,
        step: 0.01
    });

    $("#deliveryFeePerKm").kendoNumericTextBox({
        max: 99999,
        min: 0.00,
        step: 1.00,
        format: "c2"
    });

    $("#deliveryBaseCharge").kendoNumericTextBox({
        max: 99999,
        min: 0.00,
        step: 1.00,
        format: "c2"
    });

}); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 


function search() {
    $('#grid_panel_main').data('kendoGrid').dataSource.read();
    $('#grid_panel_main').data('kendoGrid').refresh();
}

function goList() {
    document.location.href = "/admin/shopmastermgt/Main.yum";
}

function goDetailInfo(shopId) {
    document.location.href = "/admin/shopmastermgt/detailInfo.yum?shopId=" + shopId;
    //document.location.href = "/admin/shopmastermgt/modifyShopForm.yum?shopId=" + shopId;
}

function goModify(shopId) {
    document.location.href = "/admin/shopmastermgt/modifyShopForm.yum?shopId=" + shopId;
}


function registShopMaster() {
    document.location.href = "/admin/shopmastermgt/registShopForm.yum";
}


function validateForm() {

    var count;
    var elementObj = "";
    var validation = true;

    // Initialize
    for (count = 0; count < checkObject.length; count++) {
        elementObj = "#" + checkObject[count];
        $(elementObj).css({'background': '#ECF5FF', 'border-color': '#DFDFDF'});
    }
    checkObject = [];
    var prefix = "- &nbsp;&nbsp;";
    var message = "";

    var shopName = $('#shopName').val();
    var shopOwner = $('#shopOwner').val();
    var templateId = $('#templateId').val();
    var minimumPurchaseAmount = $('#minimumPurchaseAmount').val();
    var maximumPurchaseAmount = $('#maximumPurchaseAmount').val();
    var discountRateValue = $('#discountRateValue').val();
    var shopCredit = $('#shopCredit').val();
    var addressState = $('#addressState').val();
    var addressPostcode = $('#addressPostcode').val();
    var addressSuburb = $('#addressSuburb').val();
    var addressStreet = $('#addressStreet').val();
    var deliveryService = $('#deliveryService').val();
    var notice = $('#notice').val();


    if (shopName == "") {
        message = message + prefix + "샵이름은 필수입력 항목입니다.<br>";
        checkObject[checkObject.length] = "shopName";
        validation = false;
    }
    if (shopOwner == "") {
        message = message + prefix + "샵운영자는 필수입력 항목입니다.<br>";
        checkObject[checkObject.length] = "shopOwner";
        validation = false;
    }
    if (templateId == "") {
        message = message + prefix + "Template Id는 필수선택 항목입니다.<br>";
        checkObject[checkObject.length] = "templateId";
        validation = false;
    }
    if (minimumPurchaseAmount == "") {
        message = message + prefix + "최소금액 입력은 필수입력 항목입니다.<br>";
        checkObject[checkObject.length] = "minimumPurchaseAmount";
        validation = false;
    }
    if (discountRateValue == "" || discountRateValue == null) {
        message = message + prefix + "할일값(%)은 필수선택 입력 항목입니다.<br>";
        checkObject[checkObject.length] = "discountRateValue";
        validation = false;
    }
    if (shopCredit == "" || shopCredit == null) {
        message = message + prefix + "샵크리딧 입력 항목입니다.<br>";
        checkObject[checkObject.length] = "shopCredit";
        validation = false;
    }
    if (addressState == "") {
        message = message + prefix + "샵주소 State는 필수입력항목입니다<br>";
        checkObject[checkObject.length] = "addressState";
        validation = false;
    }
    if (addressPostcode == "") {
        message = message + prefix + "샵주소 우편번호입력은 필수입력항목입니다<br>";
        checkObject[checkObject.length] = "addressPostcode";
        validation = false;
    }
    if (addressSuburb == "") {
        message = message + prefix + "샵주소 Suburb입력은 필수입력항목입니다<br>";
        checkObject[checkObject.length] = "addressSuburb";
        validation = false;
    }
    if (addressStreet == "") {
        message = message + prefix + "샵주소 세부주소 입력은 필수입력항목입니다<br>";
        checkObject[checkObject.length] = "addressStreet";
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


function save() {
    var shopName = $('#shopName').val();
    var shopOwner = $('#shopOwner').val();
    var templateId = $('#templateId').val();
    var minimumPurchaseAmount = $('#minimumPurchaseAmount').val();
    var maximumPurchaseAmount = $('#maximumPurchaseAmount').val();
    var discountRateValue = $('#discountRateValue').val();
    var shopCredit = $('#shopCredit').val();
    var addressState = $('#addressState').val();
    var addressPostcode = $('#addressPostcode').val();
    var addressSuburb = $('#addressSuburb').val();
    var addressStreet = $('#addressStreet').val();
    var deliveryService = $('#deliveryService').val();
    var deliveryBaseCharge = $('#deliveryBaseCharge').val();
    var deliveryFeePerKm = $('#deliveryFeePerKm').val();
    var deliveryLimitKm = $('#deliveryLimitKm').val();
    var forDeliverCalcAddressState = $('#forDeliverCalcAddressState').val();
    var forDeliverCalcAddressPostcode = $('#forDeliverCalcAddressPostcode').val();
    var forDeliverCalcAddressSuburb = $('#forDeliverCalcAddressSuburb').val();
    var forDeliverCalcAddressStreet = $('#forDeliverCalcAddressStreet').val();
    var notice = $('#notice').val();

    if (validateForm() == false) return;

    progress(true);

    $.ajax({
        url: "/admin/shopmastermgt/saveShopMaster.yum",
        data: {
            shopName: shopName,
            shopOwner: shopOwner,
            templateId: templateId,
            minimumPurchaseAmount: minimumPurchaseAmount,
            maximumPurchaseAmount: maximumPurchaseAmount,
            discountRateValue: discountRateValue,
            shopCredit: shopCredit,
            addressState: addressState,
            addressPostcode: addressPostcode,
            addressSuburb: addressSuburb,
            addressStreet: addressStreet,
            deliveryService: deliveryService,
            deliveryBaseCharge: deliveryBaseCharge,
            deliveryFeePerKm: deliveryFeePerKm,
            deliveryLimitKm: deliveryLimitKm,
            forDeliverCalcAddressState: forDeliverCalcAddressState,
            forDeliverCalcAddressPostcode: forDeliverCalcAddressPostcode,
            forDeliverCalcAddressSuburb: forDeliverCalcAddressSuburb,
            forDeliverCalcAddressStreet: forDeliverCalcAddressStreet,
            notice: notice,
            shopId: SHOP_ID,
            actionMode: ACTION_MODE
        },
        success: callbackSave
    });
}

function callbackSave(data) {
    var message = data.message;
    var resultCode = data.resultCode;
    var shopId = data.shopId;

    progress(false);

    if (resultCode != "0") {
        warningPopup(data.message);
    } else {
        goDetailInfo(shopId);
    }
}

function setSuburbCbx(postcodeObjId, objName) {
    var postcode = $('#' + postcodeObjId).val();
    $.ajax({
        url: "/common/postcode/suburbCmbx.yum?postcode=" + postcode + "&objName=" + objName,
        success: callbackSetSuburbCbx
    });
}
function callbackSetSuburbCbx(data) {
    var objName = data.objName;

    if (data.objValue != "" && data.objValue != null) {
        $('#cbx_' + objName).html(data.objValue);
    } else {
        $('#cbx_' + objName).html('<input class="form-control" type="text" id="' + objName + '" name="' + objName + '" value=""/>');
    }
}



