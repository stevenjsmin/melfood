/** 
 * 2016 ProductImage.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.product;

import melfood.framework.attachement.AttachmentFile;
import melfood.framework.common.dto.BaseDto;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class ProductImage extends BaseDto {
	private int prodId;
	private int imageSeq;
	private int imageFileId;
	private String imageDescription;
	private String width;
	private String height;
	private int displayOrder;

	private AttachmentFile attachFile;

	public ProductImage() {
	}

	public ProductImage(int prodId) {
		this.prodId = prodId;
	}

	public ProductImage(String prodId) {
		this.prodId = Integer.parseInt(prodId);
	}

	public ProductImage(int prodId, int imageSeq) {
		this.prodId = prodId;
		this.imageSeq = imageSeq;
	}

	public ProductImage(String prodId, String imageSeq) {
		this.prodId = Integer.parseInt(prodId);
		this.imageSeq = Integer.parseInt(imageSeq);
	}

	/**
	 * @return the prodId
	 */
	public int getProdId() {
		return prodId;
	}

	/**
	 * @param prodId the prodId to set
	 */
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the imageSeq
	 */
	public int getImageSeq() {
		return imageSeq;
	}

	/**
	 * @param imageSeq the imageSeq to set
	 */
	public void setImageSeq(int imageSeq) {
		this.imageSeq = imageSeq;
	}

	/**
	 * @return the imageFileId
	 */
	public int getImageFileId() {
		return imageFileId;
	}

	/**
	 * @param imageFileId the imageFileId to set
	 */
	public void setImageFileId(int imageFileId) {
		this.imageFileId = imageFileId;
	}

	/**
	 * @return the imageDescription
	 */
	public String getImageDescription() {
		return imageDescription;
	}

	/**
	 * @param imageDescription the imageDescription to set
	 */
	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	/**
	 * @return the attachFile
	 */
	public AttachmentFile getAttachFile() {
		return attachFile;
	}

	/**
	 * @param attachFile the attachFile to set
	 */
	public void setAttachFile(AttachmentFile attachFile) {
		this.attachFile = attachFile;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

}
