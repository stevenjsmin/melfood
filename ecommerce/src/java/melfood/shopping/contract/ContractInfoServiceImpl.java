/** 
 * 2016 ContractInfoServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.contract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.Ctx;
import melfood.framework.attachement.AttachmentFile;
import melfood.framework.attachement.AttachmentFileService;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;

/**
 * Seller와의 계약 정보를 관리하는 서비스 인터페이스 구현체
 *
 * @author steven.min
 *
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService {

	@Autowired
	private ContractInfoDAO contractInfoDAO;

	@Autowired
	private AttachmentFileService attachmentFileService;

	@Override
	public ContractInfo getContractInfo(ContractInfo contractInfo) throws Exception {
		List<ContractInfo> contractInfos = this.getContractInfos(contractInfo);
		if (contractInfos != null && contractInfos.size() > 0) {
			return contractInfos.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<ContractInfo> getContractInfos(ContractInfo contractInfo) throws Exception {
		return contractInfoDAO.getContractInfos(contractInfo);
	}

	@Override
	public Integer getTotalCntForContractInfos(ContractInfo contractInfo) throws Exception {
		return contractInfoDAO.getTotalCntForContractInfos(contractInfo);
	}

	@Override
	public Integer deleteContractInfo(ContractInfo contractInfo) throws Exception {
		return contractInfoDAO.deleteContractInfo(contractInfo);
	}

	@Override
	public Integer insertContractInfo(ContractInfo contractInfo) throws Exception {
		return contractInfoDAO.insertContractInfo(contractInfo);
	}

	@Override
	public Integer modifyContractInfo(ContractInfo contractInfo) throws Exception {
		return contractInfoDAO.modifyContractInfo(contractInfo);
	}

	@Override
	public Integer modifyContractInfoForNotNull(ContractInfo contractInfo) throws Exception {
		return contractInfoDAO.modifyContractInfoForNotNull(contractInfo);
	}

	@Override
	public List<Option> getSellers(User user) throws Exception {
		return this.getSellers(user, null);
	}

	@Override
	public List<Option> getSellers(User user, String selectedUser) throws Exception {
		if (StringUtils.isBlank(user.getUseYn())) user.setUseYn("Y");
		if (StringUtils.isBlank(user.getApplyStatus())) user.setApplyStatus("COMPLETE");

		List<Option> options = new ArrayList<Option>();
		List<User> users = contractInfoDAO.getSellers(user);

		String value = "";
		String name = "";

		for (User aUser : users) {
			value = aUser.getUserId();
			name = aUser.getUserName() + " ( id:" + aUser.getUserId() + " )";
			if (StringUtils.equalsIgnoreCase(value, selectedUser)) {
				options.add(new Option(value, name, true));
			} else {
				options.add(new Option(value, name, false));
			}
		}

		return options;
	}

	@Override
	public List<Option> getAllSellers() throws Exception {
		return this.getAllSellers(null);
	}

	@Override
	public List<Option> getAllSellers(String selectedUser) throws Exception {
		List<Option> options = new ArrayList<Option>();
		List<User> users = contractInfoDAO.getSellers(new User());

		String value = "";
		String name = "";

		for (User aUser : users) {
			value = aUser.getUserId();
			name = aUser.getUserName() + " ( id:" + aUser.getUserId() + " )";

			if (StringUtils.equalsIgnoreCase(value, selectedUser)) {
				options.add(new Option(value, name, true));
			} else {
				options.add(new Option(value, name, false));
			}
		}
		return options;
	}

	@Override
	public String generateCmbx(List<Option> options, Properties property, boolean placeholder) throws Exception {
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
	public List<ContractFile> transferFileToAttachementFileDb(String userId, int contractSeq) throws Exception {

		String uploadFileDir = Ctx.APP_DATA_DIR + Ctx.getVar("CONTRACTFILE.TEMP.UPLOAD.DIR");
		File tempUploadDir = new File(uploadFileDir);
		File[] files = tempUploadDir.listFiles();

		List<ContractFile> contractFiles = new ArrayList<ContractFile>();

		Integer insertedFileId = 0;
		ContractFile contractFile = null;

		if (files != null) {
			for (File f : files) {
				if (f.isFile()) {
					insertedFileId = attachmentFileService.transToAttachmentFileFrom(f, Ctx.getVar("CONTRACT.ATTACHMENT.FILE.DIR"), true);
					contractFile = new ContractFile(userId, contractSeq, insertedFileId);
					contractFiles.add(contractFile);
				}
			}

			if (contractFiles.size() > 0) {
				// contract_file 테이블에 기록
				contractInfoDAO.insertContractFile(contractFiles);
			}
		}
		return contractFiles;
	}

	@Override
	public Integer deleteContractAttachedFile(String userId) throws Exception {
		return this.deleteContractAttachedFile(userId, 0);
	}

	@Override
	public Integer deleteContractAttachedFile(String userId, int contractSeq) throws Exception {
		return this.deleteContractAttachedFile(userId, contractSeq, 0);
	}

	@Override
	public Integer deleteContractAttachedFile(String userId, int contractSeq, int fileId) throws Exception {
		ContractFile contractFile = new ContractFile(userId, contractSeq, fileId);
		List<ContractFile> fileList = this.getContractFiles(contractFile);

		int updateCnt = 0;
		for (ContractFile file : fileList) {
			updateCnt++;
			contractInfoDAO.deleteContractFile(new ContractFile(userId, contractSeq, file.getFileId()));
			attachmentFileService.deleteAttachmentFile(new AttachmentFile(file.getFileId()));
		}

		return updateCnt;
	}

	@Override
	public List<ContractFile> getContractFiles(ContractFile contractFile) throws Exception {
		return contractInfoDAO.getContractFiles(contractFile);
	}

}
