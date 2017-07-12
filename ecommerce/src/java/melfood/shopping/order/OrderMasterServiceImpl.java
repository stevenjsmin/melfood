package melfood.shopping.order;

import melfood.framework.Ctx;
import melfood.framework.attachement.AttachmentFile;
import melfood.framework.attachement.AttachmentFileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Steven on 11/7/17.
 */
@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterDAO orderMasterDAO;

    @Autowired
    private OrderMasterProductDAO orderMasterProductDAO;

    @Autowired
    private OrderMasterProductService orderMasterProductService;

    @Autowired
    private OrderMasterProductOptionDAO orderMasterProductOptionDAO;

    @Autowired
    private AttachmentFileService attachmentFileService;

    /**
     * 주문마스터상세 정보를 가져온다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public OrderMaster getOrderMaster(OrderMaster orderMaster) throws Exception {
        List<OrderMaster> list = this.getOrderMasters(orderMaster);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 주문마스터 정보 목록을 가져온다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderMaster> getOrderMasters(OrderMaster orderMaster) throws Exception {

        OrderMaster aOrder = null;
        OrderMasterProduct orderMasterProduct = null;
        List<OrderMasterProduct> orderMasterProducts = null;
        List<OrderMaster> orderMasters = orderMasterDAO.getOrderMasters(orderMaster);

        if (!orderMaster.isLazyLoad()) {

            for (int i = 0; i < orderMasters.size(); i++) {
                // 소속된 모든 하위 상품 목록을 가저온다.
                orderMasterProduct = new OrderMasterProduct();
                orderMasterProduct.setOrderMasterId(orderMasters.get(i).getOrderMasterId());
                orderMasterProducts = orderMasterProductService.getOrderMasterProducts(orderMasterProduct);

                orderMasters.get(i).setOrderMasterProduct(orderMasterProducts);
            }
        }

        return orderMasters;
    }

    /**
     * 주문마스터 정보 목록의 레코드 갯수를 가져온다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer getTotalCntForGetOrderMasters(OrderMaster orderMaster) throws Exception {
        return orderMasterDAO.getTotalCntForGetOrderMasters(orderMaster);
    }

    /**
     * 주문마스터 정보를 등록한다.<br/>
     * 등록된후 등록된 주문 번호를 반환한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertOrderMaster(OrderMaster orderMaster) throws Exception {
        int updateCnt = orderMasterDAO.insertOrderMaster(orderMaster);
        int insertedId = orderMaster.getOrderMasterId();

        List<OrderMasterProduct> orderMasterProduct = orderMaster.getOrderMasterProduct();
        for (int i = 0; i < orderMasterProduct.size(); i++) {
            orderMasterProduct.get(i).setOrderMasterId(insertedId);
        }
        orderMasterProductService.insertOrderMasterProducts(orderMasterProduct);

        return insertedId;
    }

    /**
     * 주문마스터 정보를 삭제한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteOrderMaster(OrderMaster orderMaster) throws Exception {
        return orderMasterDAO.deleteOrderMaster(orderMaster);
    }

    /**
     * * 주문 영수증을 데이터베이스에 기록한다.
     *
     * @param orderMasterId
     * @return
     * @throws Exception
     */
    @Override
    public int transferPaymentReceiptToAttachementFileDb(int orderMasterId) throws Exception {
        String uploadTempSubdir = Ctx.getVar("PAYMENT.RECEIPT.TEMP.UPLOAD.DIR");
        String uploadTempFileDir = Ctx.APP_DATA_DIR + uploadTempSubdir;
        File tempUploadDir = new File(uploadTempFileDir);

        File[] files = tempUploadDir.listFiles();
        File userProfilePhoto = null;

        if (files.length != 1) {
            throw new Exception("영수증 파일 첨부는 한개의 파일만 허용합니다.");
        } else {
            userProfilePhoto = files[0];
        }

        Integer insertedFileId = 0;

        DateFormat df = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String subDirectory = Ctx.getVar("PAYMENT.RECEIPT.ATTACHMENT.DIR") + df.format(cal.getTime()) + "/";

        BufferedImage bimg = null;
        try {

            if (userProfilePhoto != null && isAllowedType(userProfilePhoto)) {
                // File userProfilePhoto = files[0];
                bimg = ImageIO.read(userProfilePhoto);
                int width = bimg.getWidth();
                int height = bimg.getHeight();

                if (width > 3000 || height > 3000) {
                    throw new Exception("이미지의 크기가 세로 및 가로크기가 3000 픽셀을 넘을 수 없습니다.");
                }

                insertedFileId = attachmentFileService.transToAttachmentFileFrom(userProfilePhoto, subDirectory, true);

                OrderMaster orderMaster = new OrderMaster(orderMasterId);
                orderMaster.setPaymentAccTransferReceipt(insertedFileId);
                orderMasterDAO.modifyPaymentReceiptFileInfo(orderMaster);
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

    /**
     * 첨부된 Payment 영수증 파일 정보를 삭제한다.
     *
     * @param orderMasterId
     * @return
     * @throws Exception
     */
    @Override
    public Integer removePaymentReceiptFileInfo(int orderMasterId) throws Exception {
        OrderMaster orderMaster = new OrderMaster(orderMasterId);
        return orderMasterDAO.modifyPaymentReceiptFileInfo(orderMaster);
    }

    /**
     * 배송상태 수정<br/>
     * orderMasterId와 statusDelivery를 설정해야한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyStatusDelivery(OrderMaster orderMaster) throws Exception {
        return orderMasterDAO.modifyStatusDelivery(orderMaster);
    }

    /**
     * 결재상태 수정<br/>
     * orderMasterId와 statusPayment를 설정해야한다.
     *
     * @param orderMaster
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyStatusPayment(OrderMaster orderMaster) throws Exception {
        return orderMasterDAO.modifyStatusPayment(orderMaster);
    }
}
