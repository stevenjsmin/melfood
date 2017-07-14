package melfood.shopping.survey;

import melfood.framework.common.dto.BaseDto;

/**
 * Created by Steven on 14/7/17.
 */
public class PreferGrpPurchaseMarketDay extends BaseDto {
    private Integer preferMarketDayId;
    private String surveyYyyy;
    private String surveyMm;
    private String surveyDd;
    private String answerValue;
    private String answerType;
    private String respondent;
    private String respondentLatitude;
    private String respondentLongitude;
    private String respondentSuburb;
    private String respondentPostcode;

    public PreferGrpPurchaseMarketDay() {
    }

    public PreferGrpPurchaseMarketDay(String answerValue, String answerType) {
        this.answerValue = answerValue;
        this.answerType = answerType;
    }

    public PreferGrpPurchaseMarketDay(Integer preferMarketDayId) {
        this.preferMarketDayId = preferMarketDayId;
    }

    public PreferGrpPurchaseMarketDay(Integer preferMarketDayId, String surveyYyyy, String surveyMm, String surveyDd) {
        this.preferMarketDayId = preferMarketDayId;
        this.surveyYyyy = surveyYyyy;
        this.surveyMm = surveyMm;
        this.surveyDd = surveyDd;
    }

    public PreferGrpPurchaseMarketDay(Integer preferMarketDayId, String surveyYyyy, String surveyMm, String surveyDd, String answerValue, String answerType) {
        this.preferMarketDayId = preferMarketDayId;
        this.surveyYyyy = surveyYyyy;
        this.surveyMm = surveyMm;
        this.surveyDd = surveyDd;
        this.answerValue = answerValue;
        this.answerType = answerType;
    }

    public Integer getPreferMarketDayId() {
        return preferMarketDayId;
    }

    public void setPreferMarketDayId(Integer preferMarketDayId) {
        this.preferMarketDayId = preferMarketDayId;
    }

    public String getSurveyYyyy() {
        return surveyYyyy;
    }

    public void setSurveyYyyy(String surveyYyyy) {
        this.surveyYyyy = surveyYyyy;
    }

    public String getSurveyMm() {
        return surveyMm;
    }

    public void setSurveyMm(String surveyMm) {
        this.surveyMm = surveyMm;
    }

    public String getSurveyDd() {
        return surveyDd;
    }

    public void setSurveyDd(String surveyDd) {
        this.surveyDd = surveyDd;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getRespondent() {
        return respondent;
    }

    public void setRespondent(String respondent) {
        this.respondent = respondent;
    }

    public String getRespondentLatitude() {
        return respondentLatitude;
    }

    public void setRespondentLatitude(String respondentLatitude) {
        this.respondentLatitude = respondentLatitude;
    }

    public String getRespondentLongitude() {
        return respondentLongitude;
    }

    public void setRespondentLongitude(String respondentLongitude) {
        this.respondentLongitude = respondentLongitude;
    }

    public String getRespondentSuburb() {
        return respondentSuburb;
    }

    public void setRespondentSuburb(String respondentSuburb) {
        this.respondentSuburb = respondentSuburb;
    }

    public String getRespondentPostcode() {
        return respondentPostcode;
    }

    public void setRespondentPostcode(String respondentPostcode) {
        this.respondentPostcode = respondentPostcode;
    }

}
