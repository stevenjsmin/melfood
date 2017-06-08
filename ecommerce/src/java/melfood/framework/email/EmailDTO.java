/** 
 * 2015 EmailDTO.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.email;

import java.util.List;

import melfood.framework.common.dto.BaseDto;
import melfood.framework.document.DocumentTemplate;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public class EmailDTO extends BaseDto {
	private String emailId;
	private String seq;
	private String subject;
	private String sender;
	private String reciver;
	private String cc;
	private String bcc;
	private String contents;
	private String sendDatetime;
	private String attachedDocumentId1;
	private String attachedDocumentId2;
	private String attachedDocumentId3;
	private String attachedDocumentId4;
	private String attachedDocumentId5;
	private String attachedFileId1;
	private String attachedFileId2;
	private String attachedFileId3;
	private String attachedFileId4;
	private String attachedFileId5;
	private String resultCode;
	private String resultMessage;
	private String belongingEmailCnt;
	private String createDatetime;
	private String creator;

	private List<DocumentTemplate> documentDTOList;
	private List<EmailAttachedFileDTO> emailAttachedFileDTOList;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getSendDatetime() {
		return sendDatetime;
	}

	public void setSendDatetime(String sendDatetime) {
		this.sendDatetime = sendDatetime;
	}

	public String getAttachedDocumentId1() {
		return attachedDocumentId1;
	}

	public void setAttachedDocumentId1(String attachedDocumentId1) {
		this.attachedDocumentId1 = attachedDocumentId1;
	}

	public String getAttachedDocumentId2() {
		return attachedDocumentId2;
	}

	public void setAttachedDocumentId2(String attachedDocumentId2) {
		this.attachedDocumentId2 = attachedDocumentId2;
	}

	public String getAttachedDocumentId3() {
		return attachedDocumentId3;
	}

	public void setAttachedDocumentId3(String attachedDocumentId3) {
		this.attachedDocumentId3 = attachedDocumentId3;
	}

	public String getAttachedDocumentId4() {
		return attachedDocumentId4;
	}

	public void setAttachedDocumentId4(String attachedDocumentId4) {
		this.attachedDocumentId4 = attachedDocumentId4;
	}

	public String getAttachedDocumentId5() {
		return attachedDocumentId5;
	}

	public void setAttachedDocumentId5(String attachedDocumentId5) {
		this.attachedDocumentId5 = attachedDocumentId5;
	}

	public String getAttachedFileId1() {
		return attachedFileId1;
	}

	public void setAttachedFileId1(String attachedFileId1) {
		this.attachedFileId1 = attachedFileId1;
	}

	public String getAttachedFileId2() {
		return attachedFileId2;
	}

	public void setAttachedFileId2(String attachedFileId2) {
		this.attachedFileId2 = attachedFileId2;
	}

	public String getAttachedFileId3() {
		return attachedFileId3;
	}

	public void setAttachedFileId3(String attachedFileId3) {
		this.attachedFileId3 = attachedFileId3;
	}

	public String getAttachedFileId4() {
		return attachedFileId4;
	}

	public void setAttachedFileId4(String attachedFileId4) {
		this.attachedFileId4 = attachedFileId4;
	}

	public String getAttachedFileId5() {
		return attachedFileId5;
	}

	public void setAttachedFileId5(String attachedFileId5) {
		this.attachedFileId5 = attachedFileId5;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public List<DocumentTemplate> getDocumentDTOList() {
		return documentDTOList;
	}

	public void setDocumentDTOList(List<DocumentTemplate> documentDTOList) {
		this.documentDTOList = documentDTOList;
	}

	public List<EmailAttachedFileDTO> getEmailAttachedFileDTOList() {
		return emailAttachedFileDTOList;
	}

	public void setEmailAttachedFileDTOList(List<EmailAttachedFileDTO> emailAttachedFileDTOList) {
		this.emailAttachedFileDTOList = emailAttachedFileDTOList;
	}

	public String getBelongingEmailCnt() {
		return belongingEmailCnt;
	}

	public void setBelongingEmailCnt(String belongingEmailCnt) {
		this.belongingEmailCnt = belongingEmailCnt;
	}

	@Override
	public String toString() {
		return "EmailDTO [emailId=" + emailId + ", seq=" + seq + ", subject=" + subject + ", sender=" + sender + ", reciver=" + reciver + ", cc=" + cc + ", bcc=" + bcc + ", contents=" + contents + ", sendDatetime=" + sendDatetime
				+ ", attachedDocumentId1=" + attachedDocumentId1 + ", attachedDocumentId2=" + attachedDocumentId2 + ", attachedDocumentId3=" + attachedDocumentId3 + ", attachedDocumentId4=" + attachedDocumentId4 + ", attachedDocumentId5="
				+ attachedDocumentId5 + ", attachedFileId1=" + attachedFileId1 + ", attachedFileId2=" + attachedFileId2 + ", attachedFileId3=" + attachedFileId3 + ", attachedFileId4=" + attachedFileId4 + ", attachedFileId5=" + attachedFileId5
				+ ", resultCode=" + resultCode + ", resultMessage=" + resultMessage + ", belongingEmailCnt=" + belongingEmailCnt + ", createDatetime=" + createDatetime + ", creator=" + creator + ", documentDTOList=" + documentDTOList
				+ ", emailAttachedFileDTOList=" + emailAttachedFileDTOList + "]";
	}

}
