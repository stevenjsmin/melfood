/** 
 * 2016 ProductImageServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.Ctx;
import melfood.framework.attachement.AttachmentFile;
import melfood.framework.attachement.AttachmentFileService;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	private ProductImageDAO productImageDAO;

	@Autowired
	private AttachmentFileService attachmentFileService;

	@Override
	public ProductImage getProductImage(ProductImage productImage) throws Exception {
		List<ProductImage> productOptions = this.getProductImages(productImage);
		if (productOptions != null && productOptions.size() > 0) {
			return productOptions.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<ProductImage> getProductImages(ProductImage productImage) throws Exception {
		return productImageDAO.getProductImages(productImage);
	}

	@Override
	public Integer getTotalCntForProductImages(ProductImage productImage) {
		return productImageDAO.getTotalCntForProductImages(productImage);
	}

	@Override
	public Integer deleteProductImage(int prodId, int imageSeq) throws Exception {

		ProductImage productImage = new ProductImage(prodId, imageSeq);

		ProductImage prodImg = this.getProductImage(productImage);
		int updateCnt = productImageDAO.deleteProductImage(productImage);

		// 물리적인 위치의 파일을 삭제하고, 첨부파일을 관리하는 테이블에서 또한 삭제해 준다.
		attachmentFileService.deleteAttachmentFile(new AttachmentFile(prodImg.getImageFileId()));

		return updateCnt;
	}

	@Override
	public Integer deleteAllProductImages(int prodId) throws Exception {
		ProductImage productImage = new ProductImage(prodId);

		int deleteCnt = 0;
		// For Pagination
		productImage.setPagenationPage(0);
		productImage.setPagenationPageSize(99999);

		List<ProductImage> list = this.getProductImages(productImage);
		for (ProductImage img : list) {
			this.deleteProductImage(prodId, img.getImageSeq());
			deleteCnt++;
		}

		return deleteCnt;
	}

	@Override
	public Integer insertProductImage(ProductImage productImage) throws Exception {
		return productImageDAO.insertProductImage(productImage);
	}

	@Override
	public Integer insertProductImages(List<ProductImage> productImages, int prodId) throws Exception {
		return productImageDAO.insertProductImages(productImages, prodId);
	}

	@Override
	public Integer modifyProductImage(ProductImage productImage) throws Exception {
		return productImageDAO.modifyProductImage(productImage);
	}

	@Override
	public Integer modifyProductImageForNotNull(ProductImage productImage) throws Exception {
		return productImageDAO.modifyProductImageForNotNull(productImage);
	}

	@Override
	public List<ProductImage> transferFileToAttachementFileDb(int prodId) throws Exception {
		return this.transferFileToAttachementFileDb(prodId, null);
	}

	@Override
	public List<ProductImage> transferFileToAttachementFileDb(int prodId, String creator) throws Exception {
		String uploadFileDir = Ctx.APP_DATA_DIR + Ctx.getVar("PRODUCTIMAGE.TEMP.UPLOAD.DIR");
		File tempUploadDir = new File(uploadFileDir);
		File[] files = tempUploadDir.listFiles();

		List<ProductImage> productImages = new ArrayList<ProductImage>();

		Integer insertedFileId = 0;
		ProductImage productImage = null;

		DateFormat df = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String subDirectory = Ctx.getVar("PRODUCTIMAGE.ATTACHMENT.FILE") + df.format(cal.getTime()) + "/";

		BufferedImage bimg = null;
		if (files != null) {
			for (File f : files) {
				if (f.isFile() && isAllowedType(f)) {
					bimg = ImageIO.read(f);
					int width = bimg.getWidth();
					int height = bimg.getHeight();

					insertedFileId = attachmentFileService.transToAttachmentFileFrom(f, subDirectory, true);

					productImage = new ProductImage(prodId);
					productImage.setImageFileId(insertedFileId);
					productImage.setCreator(creator);
					productImage.setWidth(Integer.toString(width));
					productImage.setHeight(Integer.toString(height));

					productImages.add(productImage);
				}
			}

			if (productImages.size() > 0) {
				// product_image 테이블에 기록
				productImageDAO.insertProductImages(productImages, prodId);
			}
		}
		return productImages;
	}

	/**
	 * 허용된 이미지파일 형식인지 체크한다.
	 * 
	 * @param f
	 * @return
	 */
	private boolean isAllowedType(File f) {
		boolean retValue = false;
		String allowedImageTypes[] = Ctx.getVarAsArray("ALLOWED.PRODUCT.IMG.TYPES");
		String fileType = StringUtils.substringAfterLast(f.getName(), ".");

		for (String type : allowedImageTypes) {
			if (StringUtils.equalsIgnoreCase(fileType, type)) {
				retValue = true;
				break;
			}
		}
		return retValue;
	}

}
