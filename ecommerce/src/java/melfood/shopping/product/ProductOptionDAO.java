/** 
 * 2016 ProductOptionDAO.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import java.util.List;

/**
 * 제품 옵션 오퍼레이션 DAO 인터페이스 정의<br>
 * ProductOptionValue DTO는 ProductOption DTO의 모든 Properties를 포함하기 때문에 여기서는  ProductOptionValue로 통합하여 사용하기로 한다.
 *
 * @author steven.min
 *
 */
public interface ProductOptionDAO {
	public List<ProductOptionValue> getProductOptions(ProductOptionValue productOption) throws Exception;
	public Integer getTotalCntForProductOptions(ProductOptionValue productOption);
	public Integer insertProductOption(ProductOptionValue productOption) throws Exception;
	public Integer modifyProductOption(ProductOptionValue productOption) throws Exception;
	public Integer modifyProductOptionForNotNull(ProductOptionValue productOption) throws Exception;
	public Integer deleteProductOption(ProductOptionValue productOption) throws Exception;
	
	public List<ProductOptionValue> getProductOptionValues(ProductOptionValue productOption) throws Exception;
	public Integer getTotalCntForProductOptionValues(ProductOptionValue productOption);
	public Integer insertProductOptionValue(ProductOptionValue productOption) throws Exception;
	public Integer modifyProductOptionValue(ProductOptionValue productOption) throws Exception;
	public Integer modifyProductOptionValueForNotNull(ProductOptionValue productOption) throws Exception;
	public Integer deleteProductOptionValue(ProductOptionValue productOption) throws Exception;
}
