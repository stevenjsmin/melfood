/**
 * 2016 ShopTemplate.java
 * Created by steven.min
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.shopping.shop;

import melfood.framework.common.dto.BaseDto;

public class ShopTemplate extends BaseDto {

    private int templateId;
    private String templateName;
    private String templateDesc;
    private String tilesId;

    public ShopTemplate() {
    }

    public ShopTemplate(int templateId) {
        this.templateId = templateId;
    }

    public ShopTemplate(String templateId) {
        this.templateId = Integer.parseInt(templateId);
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public String getTilesId() {
        return tilesId;
    }

    public void setTilesId(String tilesId) {
        this.tilesId = tilesId;
    }
}
