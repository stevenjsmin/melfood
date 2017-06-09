/** 
 * 2016 Notice.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.notice;

import melfood.framework.common.dto.BaseDto;

/**
 * DTO class for Notice
 *
 * @author steven.min
 *
 */
public class NoticeDiscuss extends BaseDto {

	private int id;
	private String category;
	private String subject;
	private String contents;
	private int attachedFile;
	private String writerName;
	private String writeFrom;
	private String writeFromName;
	private String writeTo;
	private String writeToName;
	private String isForAllSeller;
	private String isForAllCustomer;
	private String isForNotice;

	public NoticeDiscuss() {
	}

	public NoticeDiscuss(int id) {
		this.id = id;
	}

	public NoticeDiscuss(String id) {
		this.id = Integer.parseInt(id);
	}

	public NoticeDiscuss(int id, String category) {
		this.id = id;
		this.category = category;
	}

	public NoticeDiscuss(String id, String category) {
		this.id = Integer.parseInt(id);
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getAttachedFile() {
		return attachedFile;
	}

	public void setAttachedFile(int attachedFile) {
		this.attachedFile = attachedFile;
	}

	public String getWriteFrom() {
		return writeFrom;
	}

	public void setWriteFrom(String writeFrom) {
		this.writeFrom = writeFrom;
	}

	public String getWriteTo() {
		return writeTo;
	}

	public void setWriteTo(String writeTo) {
		this.writeTo = writeTo;
	}

	public String getIsForAllSeller() {
		return isForAllSeller;
	}

	public void setIsForAllSeller(String isForAllSeller) {
		this.isForAllSeller = isForAllSeller;
	}

	public String getIsForAllCustomer() {
		return isForAllCustomer;
	}

	public void setIsForAllCustomer(String isForAllCustomer) {
		this.isForAllCustomer = isForAllCustomer;
	}

	public String getIsForNotice() {
		return isForNotice;
	}

	public void setIsForNotice(String isForNotice) {
		this.isForNotice = isForNotice;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public String getWriteFromName() {
		return writeFromName;
	}

	public void setWriteFromName(String writeFromName) {
		this.writeFromName = writeFromName;
	}

	public String getWriteToName() {
		return writeToName;
	}

	public void setWriteToName(String writeToName) {
		this.writeToName = writeToName;
	}

}
