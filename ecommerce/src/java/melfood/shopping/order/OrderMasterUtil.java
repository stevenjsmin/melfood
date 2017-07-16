package melfood.shopping.order;

import melfood.framework.code.CodeService;
import melfood.framework.system.BeanHelper;
import melfood.framework.user.User;
import melfood.framework.user.UserService;
import melfood.shopping.grouppurchase.GroupPurchaseService;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import melfood.shopping.order.screenDto.OrderScreenDTO;
import melfood.shopping.order.screenDto.OrderScreenItemDTO;
import melfood.shopping.payment.PaymentMethod;
import melfood.shopping.payment.PaymentMethodService;
import melfood.shopping.product.Product;
import melfood.shopping.product.ProductService;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Steven on 11/7/17.
 */
public class OrderMasterUtil {

    private ProductService productService;
    private GroupPurchaseService groupPurchaseService;
    private UserService userService;
    private CodeService codeService;
    private PaymentMethodService paymentMethodService;


    public OrderMasterUtil() {
        this.productService = BeanHelper.getBean("productService", ProductService.class);
        this.groupPurchaseService = BeanHelper.getBean("groupPurchaseService", GroupPurchaseService.class);
        this.userService = BeanHelper.getBean("userService", UserService.class);
        this.codeService = BeanHelper.getBean("codeService", CodeService.class);
        this.paymentMethodService = BeanHelper.getBean("paymentMethodService", PaymentMethodService.class);
    }

    /**
     * 화면으로부터 주문된 내용을 주문 Object(OrderMaster) 필요한 값들을 설정한다.
     *
     * @param screenDto
     * @return
     * @throws Exception
     */
    public OrderMaster settingOrderMasterInfoForGroupPurchase(OrderScreenDTO screenDto) throws Exception {
        OrderMaster orderMaster = new OrderMaster();

        String groupPurchaseId = screenDto.getGroupPurchaseId();
        List<OrderScreenItemDTO> items = screenDto.getItems(); // 주문된 상품목록

        GroupPurchase groupPurchase = groupPurchaseService.getGroupPurchase(Integer.parseInt(groupPurchaseId));
        orderMaster.setGroupPurchaseId(groupPurchaseId);
        orderMaster.setGroupPurchaseMarketGmapFormattedAddress(groupPurchase.getMarketGmapFormattedAddress());
        orderMaster.setGroupPurchaseOrganizerName(groupPurchase.getPurchaseOrganizerName());
        orderMaster.setGroupPurchaseTitle(groupPurchase.getGroupPurchaseTitle());

        Product product = null;

        List<OrderMasterProduct> orderProductList = new ArrayList<OrderMasterProduct>();
        List<OrderMasterProductOption> orderProductOptionList = null;
        OrderMasterProduct orderProduct = null;
        OrderMasterProductOption orderProductOption = null;
        String prodId = null;
        String prodOrderAmount = null;
        String optionKey = null;
        Iterator optionKeyIter = null;

        Float totalOptionAmount = 0.0f;
        Float totalProductAmount = 0.0f;
        for (OrderScreenItemDTO item : items) {
            orderProductOptionList = new ArrayList<OrderMasterProductOption>();

            Map prodOrder = item.getProductOrder().get(0);
            List<Map<String, String>> options = item.getOptions();

            // 주문된 제품ID
            prodId = (String) prodOrder.keySet().iterator().next();
            prodOrderAmount = (String) prodOrder.get(prodId);
            product = productService.getProduct(new Product(prodId));

            orderProduct = new OrderMasterProduct();
            orderProduct.setProductId(product.getProdId());
            orderProduct.setProductName(product.getName());
            orderProduct.setProductDescription(product.getDescription());
            orderProduct.setProductCategory(product.getCategoryName());

            // 상품에 소속된 옵션을 설정한다.
            for (Map option : options) {
                orderProductOption = new OrderMasterProductOption();
                optionKeyIter = option.keySet().iterator();
                while (optionKeyIter.hasNext()) {
                    optionKey = (String) optionKeyIter.next();
                    orderProductOption.setOptionName(optionKey);
                    orderProductOption.setOptionValue((String) option.get(optionKey));
                    orderProductOption.setAddtionalPrice(0.0f);

                    orderProductOptionList.add(orderProductOption);
                }
            }
            orderProduct.setOrderMasterProductOptionList(orderProductOptionList);
            orderProduct.setHasProductOption(orderProductOptionList.size() > 0 ? "Y" : "N");
            orderProduct.setAmountProductOption(0.0f);

            orderProduct.setNumberOfOrder(Integer.parseInt(prodOrderAmount));
            orderProduct.setSellerId(product.getSeller());
            orderProduct.setSellerName(product.getSellerName());
            orderProduct.setSellerMobile(null);
            orderProduct.setSellerEmail(null);
            orderProduct.setSellerPhone(null);
            orderProduct.setSellerAddressStreet(null);
            orderProduct.setSellerAddressSuburb(null);
            orderProduct.setSellerAddressPostcode(null);
            orderProduct.setSellerAddressState(null);
            orderProduct.setUnitPrice(product.getUnitPrice().floatValue());
            if (StringUtils.equalsIgnoreCase(product.getSellingCommission(), "RATE")) {
                orderProduct.setComissionFee(product.getSellingCommissionRate());
            } else if (StringUtils.equalsIgnoreCase(product.getSellingCommission(), "FIXED")) {
                orderProduct.setComissionFee(product.getSellingCommissionFee().floatValue());
            } else {
                orderProduct.setComissionFee(0.0f);
            }

            if (product.getUnitPrice() != null) {
                totalProductAmount = totalProductAmount + (product.getUnitPrice().floatValue() * Integer.parseInt(prodOrderAmount));
            }

            // 상품에대한 설정이 마무리되었으면, List<OrderMasterProduct> 에 추가한다.
            orderProductList.add(orderProduct);

        } // End of For :: OrderProductList


        orderMaster.setOrderMasterProduct(orderProductList);

        User userCustomer = userService.getUserInfo(screenDto.getCustomer().getUserId()); // 세션으로부터 설정된 ID의 사용자
        User userSeller = userService.getUserInfo(groupPurchase.getPurchaseOrganizer()); // 공동구매자

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        orderMaster.setOrderDatetime(dt.format(new Date()).toString());

        orderMaster.setInvoiceIssue("N");
        orderMaster.setIsRefund("N");
        orderMaster.setStatusPayment("BEFORE_PAYMENT");

        orderMaster.setStatusDelivery("BEFORE");

        orderMaster.setCustomerId(userCustomer.getUserId());
        orderMaster.setCustomerName(userCustomer.getUserName());
        orderMaster.setCustomerMobile(userCustomer.getMobile());
        orderMaster.setCustomerPhone(userCustomer.getTelephone());
        orderMaster.setCustomerEmail(userCustomer.getEmail());
        orderMaster.setCustomerAddressStreet(userCustomer.getAddressBusinessStreet());
        orderMaster.setCustomerAddressSuburb(userCustomer.getAddressBusinessSuburb());
        orderMaster.setCustomerAddressPostcode(userCustomer.getAddressBusinessPostcode());
        orderMaster.setCustomerAddressState(userCustomer.getAddressBusinessState());
        orderMaster.setCustomerOrderNote(screenDto.getCustomerOrderNote());

        orderMaster.setSellerId(groupPurchase.getPurchaseOrganizer());
        orderMaster.setSellerName(userSeller.getUserName());
        orderMaster.setSellerMobile(userSeller.getMobile());
        orderMaster.setSellerPhone(userSeller.getTelephone());
        orderMaster.setSellerEmail(userSeller.getEmail());

        if (!StringUtils.isBlank(userSeller.getAddressBusinessStreet())) {
            orderMaster.setSellerAddressStreet(userSeller.getAddressBusinessStreet());
        } else {
            orderMaster.setSellerAddressStreet(userSeller.getAddressStreet());
        }
        if (!StringUtils.isBlank(userSeller.getAddressBusinessSuburb())) {
            orderMaster.setSellerAddressSuburb(userSeller.getAddressBusinessSuburb());
        } else {
            orderMaster.setSellerAddressSuburb(userSeller.getAddressSuburb());
        }
        if (!StringUtils.isBlank(userSeller.getAddressBusinessPostcode())) {
            orderMaster.setSellerAddressPostcode(userSeller.getAddressBusinessPostcode());
        } else {
            orderMaster.setSellerAddressPostcode(userSeller.getAddressPostcode());
        }
        if (!StringUtils.isBlank(userSeller.getAddressBusinessState())) {
            orderMaster.setSellerAddressState(userSeller.getAddressBusinessState());
        } else {
            orderMaster.setSellerAddressState(userSeller.getAddressState());
        }

        if (StringUtils.equalsIgnoreCase(screenDto.getDeliveryService(), "no")) {
            orderMaster.setPickupAddressStreet(groupPurchase.getMarketAddressStreet());
            orderMaster.setPickupAddressSuburb(groupPurchase.getMarketAddressSuburb());
            orderMaster.setPickupAddressState(groupPurchase.getMarketAddressState());
            orderMaster.setPickupAddressPostcode(groupPurchase.getMarketAddressPostcode());
            orderMaster.setIsPickupOrDelivery("P");
        } else if (StringUtils.equalsIgnoreCase(screenDto.getDeliveryService(), "yes")) {
            orderMaster.setDeliveryToAddrStreet(userCustomer.getAddressBusinessStreet());
            orderMaster.setDeliveryToAddrSuburb(userCustomer.getAddressBusinessSuburb());
            orderMaster.setDeliveryToAddrState(userCustomer.getAddressBusinessState());
            orderMaster.setDeliveryToAddrPostcode(userCustomer.getAddressBusinessPostcode());
            orderMaster.setIsPickupOrDelivery("D");
        }

        orderMaster.setNormalOrGroupOrder("G"); //[N or G]

        String deliveryService = screenDto.getDeliveryService(); // [yes | no]
        String deliveryDistance = screenDto.getDeliveryDistance(); // Km
        Float amountTotalDelivery = 0.0f;

        if (StringUtils.equalsIgnoreCase(deliveryService, "yes")) {
            if (StringUtils.isNotBlank(deliveryDistance)) orderMaster.setDeliveryDistance(Float.parseFloat(deliveryDistance));
            if (groupPurchase.getDeliveryFeePerKm() != null) orderMaster.setDeliveryFeePerKm(groupPurchase.getDeliveryFeePerKm());
            if (groupPurchase.getDeliveryBasicFee() != null) orderMaster.setDeliveryBaseCharge(groupPurchase.getDeliveryBasicFee());

            Float basicFee = (groupPurchase.getDeliveryBasicFee() == null) ? 0.0f : groupPurchase.getDeliveryBasicFee();
            Float feeKm = (groupPurchase.getDeliveryFeePerKm() == null) ? 0.0f : groupPurchase.getDeliveryFeePerKm();
            Float dist = (deliveryDistance == null) ? 0.0f : Float.parseFloat(deliveryDistance);

            amountTotalDelivery = basicFee + (feeKm * dist);

        } else {
            amountTotalDelivery = 0.0f;
        }

        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethod(new PaymentMethod(groupPurchase.getPurchaseOrganizer(), screenDto.getPaymentMethod()));

        if (StringUtils.isNotBlank(screenDto.getPaymentMethod())) {
            if (StringUtils.equalsIgnoreCase(paymentMethod.getPaymentMethod(), "ACCOUNT_TRANSFER")) {
                orderMaster.setPaymentBankName(paymentMethod.getBankName());
                orderMaster.setPaymentBankBsb(paymentMethod.getBankBsb());
                orderMaster.setPaymentAccountNo(paymentMethod.getBankAccountNo());
                orderMaster.setPaymentAccountHolderName(paymentMethod.getBankAccountOwnerName());
            }
            //orderMaster.setPaymentMethod(paymentMethod.getPaymentMethodCodeName());
            orderMaster.setPaymentMethod(paymentMethod.getPaymentMethod());
        } else {
            orderMaster.setPaymentMethod(null);
        }

        Float discountRate = 0.0f;
        Float amountTotalDiscount = 0.0f;
        Float amountTotal = 0.0f;
        if (StringUtils.equalsIgnoreCase(groupPurchase.getDiscountMethod(), "RATE")) {
            discountRate = groupPurchase.getDiscountRateValue();
            if (discountRate != 0) amountTotalDiscount = ((discountRate * 100) / 100) * totalProductAmount;

        } else if (StringUtils.equalsIgnoreCase(groupPurchase.getDiscountMethod(), "FIXED")) {
            amountTotalDiscount = groupPurchase.getDiscountFixedAmount();
        }
        orderMaster.setAmountTotalDiscount(amountTotalDiscount);

        orderMaster.setAmountTotalProduct(totalProductAmount);
        orderMaster.setAmountTotalProductOption(0.0f);
        orderMaster.setAmountTotalDelivery(amountTotalDelivery);

        amountTotal = totalProductAmount + amountTotalDelivery - amountTotalDiscount;
        orderMaster.setAmountTotal(amountTotal);


        return orderMaster;
    }
}
