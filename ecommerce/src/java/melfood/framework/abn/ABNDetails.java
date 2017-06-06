package melfood.framework.abn;

public class ABNDetails {

	private String ABN;
	private String ACN;
    private String EntityName;
    private String FirstName;
    private String Surname;
    private String EntityStatus;
    private String ASICNumber;
    private String EntityTypeCode;
    private String EntityDescription;
    private String StateCode;
    private String Postcode;
    private String checkAgainst;
    
    
    public String getABN() {
		return ABN;
	}
	public void setABN(String aBN) {
		ABN = aBN;
	}
	
	public String getACN() {
		return ACN;
	}
	public void setACN(String aCN) {
		ACN = aCN;
	}
	public void setEntityName(String EN) {
        this.EntityName = EN;
    }
    public String getEntityName() {
        return EntityName;
    }

    public void setFirstName(String FN) {
        this.FirstName = FN;
    }
    public String getFirstName() {
        return FirstName;
    }
    
    public void setSurname(String SN) {
        this.Surname = SN;
    }
    public String getSurname() {
        return Surname;
    }
	public String getEntityStatus() {
		return EntityStatus;
	}
	public void setEntityStatus(String entityStatus) {
		EntityStatus = entityStatus;
	}
	public String getASICNumber() {
		return ASICNumber;
	}
	public void setASICNumber(String aSICNumber) {
		ASICNumber = aSICNumber;
	}
	public String getEntityTypeCode() {
		return EntityTypeCode;
	}
	public void setEntityTypeCode(String entityTypeCode) {
		EntityTypeCode = entityTypeCode;
	}
	public String getEntityDescription() {
		return EntityDescription;
	}
	public void setEntityDescription(String entityDescription) {
		EntityDescription = entityDescription;
	}
	public String getStateCode() {
		return StateCode;
	}
	public void setStateCode(String stateCode) {
		StateCode = stateCode;
	}
	public String getPostcode() {
		return Postcode;
	}
	public void setPostcode(String postcode) {
		Postcode = postcode;
	}
	public String getCheckAgainst() {
		return checkAgainst;
	}
	public void setCheckAgainst(String checkAgainst) {
		this.checkAgainst = checkAgainst;
	}

    

} 
