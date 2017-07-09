package melfood.framework.communication;

import melfood.framework.common.dto.BaseDto;

/**
 * Created by Steven on 7/7/17.
 */
public class Communication extends BaseDto {

    private int id;
    private String category;
    private String subject;
    private String contents;
    private String writer;
    private String writerMobile;
    private String writerEmail;
    private String writeFrom;
    private String writeFromName;
    private String writeTo;
    private String writeToName;
    private String isForAllSeller;
    private String isForAllCustomer;
    private String progressStatus;
    private String notifySmsNo;
    private String notifyEmail;

    public Communication() {
    }

    public Communication(int id) {
        this.id = id;
    }

    public Communication(String id) {
        this.id = Integer.parseInt(id);
    }

    public Communication(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public Communication(String id, String category) {
        this.id = Integer.parseInt(id);
        this.category = category;
    }


    public Communication(int id, String category, String writer) {
        this.id = id;
        this.category = category;
        this.writer = writer;
    }

    public Communication(String id, String category, String writer) {
        this.id = Integer.parseInt(id);
        this.category = category;
        this.writer = writer;
    }

    public Communication(int id, String category, String contents, String writer) {
        this.id = id;
        this.category = category;
        this.contents = contents;
        this.writer = writer;
    }

    public Communication(String id, String category, String contents, String writer) {
        this.id = Integer.parseInt(id);
        this.category = category;
        this.contents = contents;
        this.writer = writer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriterMobile() {
        return writerMobile;
    }

    public void setWriterMobile(String writerMobile) {
        this.writerMobile = writerMobile;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
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

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getNotifySmsNo() {
        return notifySmsNo;
    }

    public void setNotifySmsNo(String notifySmsNo) {
        this.notifySmsNo = notifySmsNo;
    }

    public String getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(String notifyEmail) {
        this.notifyEmail = notifyEmail;
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
