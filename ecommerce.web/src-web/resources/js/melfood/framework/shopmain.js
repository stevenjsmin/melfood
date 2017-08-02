

function openProductOrderPopup(prodId) {
    //TODO : 팝업을 띄어서 상품에 상세정보를 볼수있도록 한후 주문을 받도록 한다.
    console.log('do Order.....:' + prodId);

    $("#productOrderPopup").kendoWindow({
        content: "/shop/productOrderPopup.yum?shopId=" + SHOP_ID + "&prodId=" + prodId,
        actions: ["Minimize", "Maximize", "Close"],
        title: false,
        modal: true,
        iframe: true
    });

    var popup_dialog = $("#productOrderPopup").data("kendoWindow");
    popup_dialog.setOptions({
        width: 800,
        height: 500
    });
    popup_dialog.center();

    $('#productOrderPopup').closest(".k-window").css({
        position: 'fixed',
        margin: 'auto',
        top: '5%'
    });

    $("#productOrderPopup").data("kendoWindow").open();
}

function closeOpenProductOrderPopup() {
    var win_dialog = $("#productOrderPopup").data("kendoWindow");
    win_dialog.close();
}