package melfood.shopping.grouppurchase;

import melfood.framework.Ctx;
import melfood.framework.attachement.AttachmentFile;
import melfood.framework.attachement.AttachmentFileService;
import melfood.framework.uitl.html.Option;
import melfood.framework.user.UserService;
import melfood.shopping.grouppurchase.dao.GroupPurchaseDAO;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import melfood.shopping.product.ProductImage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Steven on 14/6/17.
 */
@Service
public class GroupPurchaseServiceImpl implements GroupPurchaseService {

    @Autowired
    GroupPurchaseDAO groupPurchaseDAO;


    @Autowired
    private AttachmentFileService attachmentFileService;


    @Autowired
    protected UserService userService;

    @Override
    public GroupPurchase getGroupPurchase(int groupPurchaseId) throws Exception {

        List<GroupPurchase> groupPurchase = this.getGroupPurchases(new GroupPurchase(groupPurchaseId));
        if (groupPurchase != null & groupPurchase.size() > 0) {
            return groupPurchase.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<GroupPurchase> getGroupPurchases(GroupPurchase groupPurchase) throws Exception {
        return groupPurchaseDAO.getGroupPurchases(groupPurchase);
    }

    @Override
    public Integer getTotalCntForGroupPurchases(GroupPurchase groupPurchase) {
        return groupPurchaseDAO.getTotalCntForGroupPurchases(groupPurchase);
    }

    @Override
    public Integer deleteGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return groupPurchaseDAO.deleteGroupPurchase(groupPurchase);
    }

    @Override
    public Integer insertGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return groupPurchaseDAO.insertGroupPurchase(groupPurchase);
    }

    @Override
    public Integer modifyGroupPurchase(GroupPurchase groupPurchase) throws Exception {
        return groupPurchaseDAO.modifyGroupPurchase(groupPurchase);
    }

    @Override
    public Integer modifyGroupPurchaseForNotNull(GroupPurchase groupPurchase) throws Exception {
        return groupPurchaseDAO.modifyGroupPurchaseForNotNull(groupPurchase);
    }

    @Override
    public List<Option> getOrganizers() throws Exception {
        return userService.getUsersByRoleId("GROUP_PURCHASER");
    }

    @Override
    public List<Option> getOrganizers(String selectedUserId) throws Exception {
        return null;
    }

    @Override
    public List<Option> getMarketSuburbs() throws Exception {
        return null;
    }

    @Override
    public List<Option> getDiscountMethods() throws Exception {
        return null;
    }

    @Override
    public List<Option> getStopSellingOptions() throws Exception {
        return null;
    }

    @Override
    public List<ProductImage> transferFileToAttachementFileDb(int groupPurchaseId) throws Exception {
        return this.transferFileToAttachementFileDb(groupPurchaseId, null);
    }

    @Override
    public List<ProductImage> transferFileToAttachementFileDb(int groupPurchaseId, String creator) throws Exception {
        String uploadFileDir = Ctx.APP_DATA_DIR + Ctx.getVar("GRPPURCHASE.PRODUCTIMAGE.TEMP.UPLOAD.DIR");
        File tempUploadDir = new File(uploadFileDir);
        File[] files = tempUploadDir.listFiles();

        List<ProductImage> productImages = new ArrayList<ProductImage>();

        Integer insertedFileId = 0;
        ProductImage productImage = null;

        DateFormat df = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String subDirectory = Ctx.getVar("GRPPURCHASE.PRODUCTIMAGE.ATTACHMENT.FILE") + df.format(cal.getTime()) + "/";

        BufferedImage bimg = null;
        if (files != null) {
            for (File f : files) {
                if (f.isFile() && isAllowedType(f)) {
                    bimg = ImageIO.read(f);
                    int width = bimg.getWidth();
                    int height = bimg.getHeight();

                    insertedFileId = attachmentFileService.transToAttachmentFileFrom(f, subDirectory, true);

                    productImage = new ProductImage(groupPurchaseId);
                    productImage.setImageFileId(insertedFileId);
                    productImage.setCreator(creator);
                    productImage.setWidth(Integer.toString(width));
                    productImage.setHeight(Integer.toString(height));

                    productImages.add(productImage);
                }
            }

            if (productImages.size() > 0) {
                // product_image 테이블에 기록
                groupPurchaseDAO.insertGroupPurchaseImages(productImages, groupPurchaseId);
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
        List<ProductImage> images = null;
        try {
            images = groupPurchaseDAO.getProductImages(productImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return images;
    }

    @Override
    public Integer getTotalCntForProductImages(ProductImage productImage) {
        return groupPurchaseDAO.getTotalCntForProductImages(productImage);
    }

    @Override
    public Integer deleteProductImage(int groupPurchaseId, int imageSeq) throws Exception {
        ProductImage productImage = new ProductImage(groupPurchaseId, imageSeq);

        ProductImage prodImg = this.getProductImage(productImage);
        int updateCnt = groupPurchaseDAO.deleteProductImage(productImage);

        // 물리적인 위치의 파일을 삭제하고, 첨부파일을 관리하는 테이블에서 또한 삭제해 준다.
        attachmentFileService.deleteAttachmentFile(new AttachmentFile(prodImg.getImageFileId()));

        return updateCnt;
    }

}
