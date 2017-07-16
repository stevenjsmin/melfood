package melfood.shopping.shop;

import melfood.framework.Ctx;
import melfood.framework.attachement.AttachmentFile;
import melfood.framework.attachement.AttachmentFileService;
import melfood.framework.uitl.html.Option;
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
 * Created by Steven on 16/7/17.
 */
@Service
public class ShopMasterServiceImpl implements ShopMasterService {
    @Autowired
    ShopMasterDAO shopMasterDAO;

    @Autowired
    AttachmentFileService attachmentFileService;

    @Override
    public ShopMaster getShopMaster(ShopMaster shopMaster) throws Exception {
        List<ShopMaster> list = this.getShopMasters(shopMaster);
        if (list.size() > 0) return list.get(0);
        else
            return null;
    }

    @Override
    public List<ShopMaster> getShopMasters(ShopMaster shopMaster) throws Exception {
        return shopMasterDAO.getShopMasters(shopMaster);
    }

    @Override
    public Integer getTotalCntForGetShopMasters(ShopMaster shopMaster) {
        return shopMasterDAO.getTotalCntForGetShopMasters(shopMaster);
    }

    /**
     * 샵 정보를 저장 후, 저장된 샵ID를 반환한다.
     *
     * @param shopMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertShopMaster(ShopMaster shopMaster) throws Exception {
        return shopMasterDAO.insertShopMaster(shopMaster);
    }

    @Override
    public Integer modifyShopMaster(ShopMaster shopMaster) throws Exception {
        return shopMasterDAO.modifyShopMaster(shopMaster);
    }

    @Override
    public Integer deleteShopMaster(ShopMaster shopMaster) throws Exception {
        return shopMasterDAO.deleteShopMaster(shopMaster);
    }

    @Override
    public List<ShopTemplate> getShopTemplates(ShopTemplate shopTemplate) throws Exception {
        return shopMasterDAO.getShopTemplates(shopTemplate);
    }


    @Override
    public List<Option> getShopTemplateOptions(ShopTemplate shopTemplate) throws Exception {
        return getShopTemplateOptions(shopTemplate, 0);
    }

    @Override
    public List<Option> getShopTemplateOptions(ShopTemplate shopTemplate, int selectedTemplateId) throws Exception {

        List<Option> options = new ArrayList<Option>();
        List<ShopTemplate> templates = this.getShopTemplates(shopTemplate);

        String value = "";
        String name = "";

        for (ShopTemplate template : templates) {
            value = Integer.toString(template.getTemplateId());
            name = template.getTemplateName();
            if (StringUtils.equalsIgnoreCase(value, Integer.toString(selectedTemplateId)) && selectedTemplateId != 0) {
                options.add(new Option(value, name, true));
            } else {
                options.add(new Option(value, name, false));
            }
        }

        return options;
    }

    /**
     * 이미지파일을 데이터베이스에 기록한다.
     *
     * @param shopId
     * @return
     * @throws Exception
     */
    @Override
    public int transferToAttachementFileDb(int shopId) throws Exception {
        String uploadTempSubdir = Ctx.getVar("SHOP.GATEIMAGE.TEMP.UPLOAD.DIR");
        String uploadTempFileDir = Ctx.APP_DATA_DIR + uploadTempSubdir;
        File tempUploadDir = new File(uploadTempFileDir);

        File[] files = tempUploadDir.listFiles();
        File gateImageFile = null;

        if (files.length != 1) {
            throw new Exception("영수증 파일 첨부는 한개의 파일만 허용합니다.");
        } else {
            gateImageFile = files[0];
        }

        Integer insertedFileId = 0;

        DateFormat df = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String subDirectory = Ctx.getVar("SHOP.GATEIMAGE.ATTACHMENT.DIR") + df.format(cal.getTime()) + "/";

        BufferedImage bimg = null;
        try {

            if (gateImageFile != null && isAllowedType(gateImageFile)) {
                // File gateImageFile = files[0];
                bimg = ImageIO.read(gateImageFile);
                int width = bimg.getWidth();
                int height = bimg.getHeight();

                if (width > 3000 || height > 3000) {
                    throw new Exception("이미지의 크기가 세로 및 가로크기가 3000 픽셀을 넘을 수 없습니다.");
                }

                insertedFileId = attachmentFileService.transToAttachmentFileFrom(gateImageFile, subDirectory, true);

                ShopMaster shopMaster = new ShopMaster(shopId);
                shopMaster.setShopGateImageFileId(insertedFileId);
                shopMasterDAO.modifyShopGateImageFileInfo(shopMaster);
            } else {
                throw new Exception("영수증파일에 문제가 있습니다. 파일 형식이나 파일에 문제가 있는지 확인해주세요. :" + Ctx.getVarAsArray("ALLOWED.PRODUCT.IMG.TYPES").toString());
            }
        } catch (Exception e) {
            attachmentFileService.deleteAttachmentFile(new AttachmentFile(insertedFileId));
            attachmentFileService.deleteAllfilesForTempUploadDir(uploadTempSubdir); // 업로드된 임시 파일들을 삭제해준다.
            throw e;
        }

        return insertedFileId;
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
    public Integer removeShopGateImageFileInfo(int shopId) throws Exception {
        return shopMasterDAO.modifyShopGateImageFileInfo(new ShopMaster(shopId));
    }
}
