package melfood.framework.qna;

import melfood.framework.common.dto.BaseDto;

/**
 * Created by Steven on 26/6/17.
 */
public class QnA extends BaseDto {

    private int id;
    private String customerMobile;
    private String customerEmail;
    private String customerQuestion;
    private String qnaStatus;

    public QnA() {
    }

    public QnA(int id) {
        this.id = id;
    }

    public QnA(String id) {
        this.id = Integer.parseInt(id);
    }

    public QnA(String customerMobile, String customerEmail, String customerQuestion, String qnaStatus) {
        this.customerMobile = customerMobile;
        this.customerEmail = customerEmail;
        this.customerQuestion = customerQuestion;
        this.qnaStatus = qnaStatus;
    }

    public QnA(String id, String customerMobile, String customerEmail, String customerQuestion, String qnaStatus) {
        this.id = Integer.parseInt(id);
        this.customerMobile = customerMobile;
        this.customerEmail = customerEmail;
        this.customerQuestion = customerQuestion;
        this.qnaStatus = qnaStatus;
    }

    public QnA(int id, String customerMobile, String customerEmail, String customerQuestion, String qnaStatus) {
        this.id = id;
        this.customerMobile = customerMobile;
        this.customerEmail = customerEmail;
        this.customerQuestion = customerQuestion;
        this.qnaStatus = qnaStatus;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerQuestion() {
        return customerQuestion;
    }

    public void setCustomerQuestion(String customerQuestion) {
        this.customerQuestion = customerQuestion;
    }

    public String getQnaStatus() {
        return qnaStatus;
    }

    public void setQnaStatus(String qnaStatus) {
        this.qnaStatus = qnaStatus;
    }
}
