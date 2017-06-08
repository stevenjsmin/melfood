/** 
 * 2016 ProductOptionService.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import java.util.List;

import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;

/**
 * Class description
 *
 * @author steven.min
 *
 */
public interface ProductOptionService {
	public ProductOptionValue getProductOption(ProductOptionValue productOption) throws Exception;
	public List<ProductOptionValue> getProductOptions(ProductOptionValue productOption) throws Exception;
	public Integer getTotalCntForProductOptions(ProductOptionValue productOption);
	public Integer insertProductOption(ProductOptionValue productOption) throws Exception;
	public Integer modifyProductOption(ProductOptionValue productOption) throws Exception;
	public Integer modifyProductOptionForNotNull(ProductOptionValue productOption) throws Exception;
	public Integer deleteProductOption(ProductOptionValue productOption) throws Exception;
	public boolean existProductOption(ProductOptionValue productOption) throws Exception;
	
	public String generateCmbxForOption(Properties property, int prodId) throws Exception;
	public String generateCmbxForOption(Properties property, int prodId, boolean placeholder) throws Exception;
	public String generateCmbxForOption(Properties property, int prodId, int defaultSelectedValue) throws Exception;
	public String generateCmbxForOption(Properties property, int prodId, int defaultSelectedValue, boolean placeholder) throws Exception;	
	
	public ProductOptionValue getProductOptionValue(ProductOptionValue productOption) throws Exception;
	public List<ProductOptionValue> getProductOptionValues(ProductOptionValue productOption) throws Exception;
	public Integer getTotalCntForProductOptionValues(ProductOptionValue productOption);
	public Integer insertProductOptionValue(ProductOptionValue productOption) throws Exception;
	public Integer modifyProductOptionValue(ProductOptionValue productOption) throws Exception;
	public Integer modifyProductOptionValueForNotNull(ProductOptionValue productOption) throws Exception;
	public Integer deleteProductOptionValue(ProductOptionValue productOption) throws Exception;
	public boolean existProductOptionValue(ProductOptionValue productOption) throws Exception;
	
	public List<Option> getOptionsForOptionValue(int prodid, int optionSeq) throws Exception;
	public List<Option> getOptionsForOptionValue(int prodid, int optionSeq, int defaultSelectedValue) throws Exception;
	public String generateCmbxForOptionValue(List<Option> optionValues, Properties property) throws Exception;
	public String generateCmbxForOptionValue(List<Option> optionValues, Properties property, boolean placeholder) throws Exception;
	
	/**
	 * 지정된 상품에 대하여 모든 상품옵션 정보를 각 옵션항목과 함께 옵션값을 콤보박스로 구성하여 얻어온다.
	 * 
	 * @param property
	 * @param prodid
	 * @return
	 * @throws Exception
	 */
	public List<ProductOptionGroup> generateCmbxForOptionAndValue(Properties property, int prodid) throws Exception;
	
	/**
	 * 지정된 상품에 대하여 모든 상품옵션 정보를 각 옵션항목과 함께 옵션값을 콤보박스로 구성하여 얻어온다.
	 * 
	 * @param property
	 * @param prodid
	 * @param placeholder
	 * @return
	 * @throws Exception
	 */
	public List<ProductOptionGroup> generateCmbxForOptionAndValue(Properties property, int prodid, boolean placeholder) throws Exception;
	
	/**
	 * 지정된 상품에 대하여 지정된 옵션 항목에 대하 상품옵션 정보를 각 옵션항목과 함께 옵션값을 콤보박스로 구성하여 얻어온다.
	 * 
	 * @param property
	 * @param prodid
	 * @param optionSeq
	 * @param placeholder
	 * @return
	 * @throws Exception
	 */
	public List<ProductOptionGroup> generateCmbxForOptionAndValue(Properties property, int prodid, int optionSeq, boolean placeholder) throws Exception;
	
}
