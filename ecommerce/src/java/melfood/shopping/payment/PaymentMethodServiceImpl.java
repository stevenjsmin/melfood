/**
 * 2016 PaymentMethodServiceImpl.java
 * Created by steven.min
 * <p>
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.payment;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: 설명
 *
 * @author steven.min
 */
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodDAO paymentMethodDAO;

    @Override
    public PaymentMethod getPaymentMethod(PaymentMethod paymentMethod) throws Exception {
        List<PaymentMethod> paymentMethods = this.getPaymentMethods(paymentMethod);
        if (paymentMethods != null && paymentMethods.size() > 0) {
            return paymentMethods.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<PaymentMethod> getPaymentMethods(PaymentMethod paymentMethod) throws Exception {
        return paymentMethodDAO.getPaymentMethods(paymentMethod);
    }

    @Override
    public Integer getTotalCntForPaymentMethods(PaymentMethod paymentMethod) {
        return paymentMethodDAO.getTotalCntForPaymentMethods(paymentMethod);
    }

    @Override
    public Integer deletePaymentMethod(PaymentMethod paymentMethod) throws Exception {
        return paymentMethodDAO.deletePaymentMethod(paymentMethod);
    }

    @Override
    public Integer insertPaymentMethod(PaymentMethod paymentMethod) throws Exception {
        return paymentMethodDAO.insertPaymentMethod(paymentMethod);
    }

    @Override
    public Integer modifyPaymentMethod(PaymentMethod paymentMethod) throws Exception {
        return paymentMethodDAO.modifyPaymentMethod(paymentMethod);
    }

    @Override
    public Integer modifyPaymentMethodForNotNull(PaymentMethod paymentMethod) throws Exception {
        return paymentMethodDAO.modifyPaymentMethodForNotNull(paymentMethod);
    }

    @Override
    public List<Option> getCmbxOptions(String userId) throws Exception {
        return this.getCmbxOptions(userId, true);
    }

    @Override
    public List<Option> getCmbxOptions(String userId, boolean useYn) throws Exception {
        return this.getCmbxOptions(userId, useYn, null);
    }

    @Override
    public List<Option> getCmbxOptions(String userId, boolean useYn, String defaultSelectedValue) throws Exception {
        return this.getCmbxOptions(userId, useYn, false, defaultSelectedValue);
    }

    /**
     * 상품판매자가 제공하는 모든 결재수단을 <code>Opiton</code>객체로 각각구성하여 List객체로 Wrapping하여 가저온다.
     *
     * @param userId
     * @param useYn                'Y' 인경우 현재 활성화 되어있는 판매자의 결재수단만을 조회한다.
     * @param defaultSelectedValue Option Label에 Account Deatil정보를 포함할것인지를 결정(False인경우 은행이름(카드사인경우 카드사명)만 포함된다.
     * @return
     * @throws Exception
     */
    @Override
    public List<Option> getCmbxOptions(String userId, boolean useYn, boolean includeDetailAccount, String defaultSelectedValue) throws Exception {
        PaymentMethod paymentMethod = new PaymentMethod(userId);
        paymentMethod.setUseYn(useYn ? "Y" : "N");
        List<PaymentMethod> paymentMethods = this.getPaymentMethods(paymentMethod);

        List<Option> options = new ArrayList<Option>();
        String optionValue = "";
        String optionLabel = "";
        // StringBuffer optionName = new StringBuffer("");

        for (PaymentMethod aPaymentMethod : paymentMethods) {
            optionValue = aPaymentMethod.getPaymentMethod();
            optionLabel = aPaymentMethod.getPaymentMethodCodeName();
            if (StringUtils.equalsIgnoreCase(optionValue, "ACCOUNT_TRANSFER")) {
                // optionName.append(aPaymentMethod.getPaymentMethod());
                if (includeDetailAccount) {
                    optionLabel = optionLabel + " [ " + aPaymentMethod.getBankNameCodeName() + "-  BSB: " + aPaymentMethod.getBankBsb() + ",  Account: " + aPaymentMethod.getBankAccountNo() + ",  Account holder: " + aPaymentMethod.getBankAccountOwnerName() + " ]";
                } else {
                    optionLabel = optionLabel + " [ " + aPaymentMethod.getBankNameCodeName() + "]";
                }
            }
            options.add(new Option(optionValue, optionLabel, (StringUtils.equalsIgnoreCase(optionValue, defaultSelectedValue))));
        }

        return options;
    }


    @Override
    public String generateCmbx(List<Option> options, Properties property) {
        return generateCmbx(options, property, true);
    }

    @Override
    public String generateCmbx(List<Option> options, Properties property, boolean placeholder) {
        StringBuffer selectHtml = new StringBuffer("<select ");
        if (StringUtils.isNotBlank(property.getId())) selectHtml.append("id='" + property.getId() + "' ");
        if (StringUtils.isNotBlank(property.getName())) selectHtml.append("name='" + property.getName() + "' ");
        if (StringUtils.isNotBlank(property.getCssClass())) selectHtml.append("class='" + property.getCssClass() + "' ");
        if (StringUtils.isNotBlank(property.getCssStyle())) selectHtml.append("style='" + property.getCssStyle() + "' ");
        if (StringUtils.isNotBlank(property.getOnclick())) selectHtml.append("onclick='" + property.getOnclick() + "' ");
        if (StringUtils.isNotBlank(property.getOnchange())) selectHtml.append("onchange='" + property.getOnchange() + "' ");
        if (property.isMultipleSelect()) selectHtml.append("multiple='multiple' ");

        selectHtml.append("> \n");

        if (placeholder) selectHtml.append("<option value=''> - SELECT - </option>\n");

        if (options != null) {

            for (Option option : options) {
                if (option.isSelected()) {
                    selectHtml.append("<option value='" + option.getValue() + "' selected>" + option.getName() + "</option>\n");
                } else {
                    selectHtml.append("<option value='" + option.getValue() + "'>" + option.getName() + "</option>\n");
                }
            }
        }
        selectHtml.append("</select>");

        return selectHtml.toString();
    }

    @Override
    public boolean existPaymentMethod(PaymentMethod paymentMethod) throws Exception {
        return (this.getPaymentMethods(paymentMethod) != null) ? true : false;
    }

}
