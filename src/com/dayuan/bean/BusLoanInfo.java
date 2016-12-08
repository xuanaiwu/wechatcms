package com.dayuan.bean;

/**商贷主表*/
public class BusLoanInfo extends BaseBean{
	private Integer id;
	private String surveyOrgName;
	private String surveyPersonName;
	private String surveyPhone;
	private String applicationName;
	private String applicationAmount;
	private String applicationTerm;
	private String loanType;
	private String urgentCont;
	private String urgentContPhone;
	private String urgentContAddress;
	private String relationship;
	private String taobaoTreeDiamondMore;
	private String otherPlatform;
	private String operatingPeriodMore;
	private String ifShopOwner;
	private String haveGuarantor;
	private String shopController;
	private String salesOfMore;
	private String than3credit;
	private String notOverdue;
	private String perNotOverdue;
	
	/**2016-11-5新增 */
	private String uName;
	private String uId;
	private String content;
	private String channel;
	private String ifGuaranter;
	private String localPaySocialSecurity;
	private String ifCustomersVIP;
	private String childrenIfLocally;
	private String additionInfo;
	
	
	/**20161117 add*/
	private String applicationGender;
	private String applicationAddress;
	private String applicationIdCard;
	private String applicationPhone;
	
	
	/**20161205*/
	private Integer lId;
	
	/**扩展属性，更新时临时保存主键id*/
	private Integer loanInfoTempId;
	
	public Integer getLoanInfoTempId() {
		return loanInfoTempId;
	}
	public void setLoanInfoTempId(Integer loanInfoTempId) {
		this.loanInfoTempId = loanInfoTempId;
	}
	public Integer getlId() {
		return lId;
	}
	public void setlId(Integer lId) {
		this.lId = lId;
	}
	public String getApplicationGender() {
		return applicationGender;
	}
	public void setApplicationGender(String applicationGender) {
		this.applicationGender = applicationGender;
	}
	public String getApplicationAddress() {
		return applicationAddress;
	}
	public void setApplicationAddress(String applicationAddress) {
		this.applicationAddress = applicationAddress;
	}
	public String getApplicationIdCard() {
		return applicationIdCard;
	}
	public void setApplicationIdCard(String applicationIdCard) {
		this.applicationIdCard = applicationIdCard;
	}
	public String getApplicationPhone() {
		return applicationPhone;
	}
	public void setApplicationPhone(String applicationPhone) {
		this.applicationPhone = applicationPhone;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getChannel() {
		return channel!=null?channel:"";
	}
	public void setChannel(String channel) {
		if(channel==null){
			this.channel="";
		}else{
			this.channel = channel;
		}
		
	}
	public String getIfGuaranter() {
		return ifGuaranter;
	}
	public void setIfGuaranter(String ifGuaranter) {
		this.ifGuaranter = ifGuaranter;
	}
	public String getLocalPaySocialSecurity() {
		return localPaySocialSecurity;
	}
	public void setLocalPaySocialSecurity(String localPaySocialSecurity) {
		this.localPaySocialSecurity = localPaySocialSecurity;
	}
	public String getIfCustomersVIP() {
		return ifCustomersVIP;
	}
	public void setIfCustomersVIP(String ifCustomersVIP) {
		this.ifCustomersVIP = ifCustomersVIP;
	}
	public String getChildrenIfLocally() {
		return childrenIfLocally;
	}
	public void setChildrenIfLocally(String childrenIfLocally) {
		this.childrenIfLocally = childrenIfLocally;
	}
	public String getAdditionInfo() {
		return additionInfo;
	}
	public void setAdditionInfo(String additionInfo) {
		this.additionInfo = additionInfo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSurveyOrgName() {
		return surveyOrgName;
	}
	public void setSurveyOrgName(String surveyOrgName) {
		this.surveyOrgName = surveyOrgName;
	}
	public String getSurveyPersonName() {
		return surveyPersonName;
	}
	public void setSurveyPersonName(String surveyPersonName) {
		this.surveyPersonName = surveyPersonName;
	}
	public String getSurveyPhone() {
		return surveyPhone;
	}
	public void setSurveyPhone(String surveyPhone) {
		this.surveyPhone = surveyPhone;
	}
	public String getApplicationName() {
		return applicationName!=null?applicationName:"";
	}
	public void setApplicationName(String applicationName) {
		if(applicationName==null){
			this.applicationName="";
		}else{
			this.applicationName = applicationName;
		}
		
	}
	public String getApplicationAmount() {
		return applicationAmount;
	}
	public void setApplicationAmount(String applicationAmount) {
		this.applicationAmount = applicationAmount;
	}
	public String getApplicationTerm() {
		return applicationTerm;
	}
	public void setApplicationTerm(String applicationTerm) {
		this.applicationTerm = applicationTerm;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getUrgentCont() {
		return urgentCont!=null?urgentCont:"";
	}
	public void setUrgentCont(String urgentCont) {
		if(urgentCont==null){
			this.urgentCont="";
		}else{
			
			this.urgentCont = urgentCont;
		}
		
	}
	public String getUrgentContPhone() {
		return urgentContPhone;
	}
	public void setUrgentContPhone(String urgentContPhone) {
		this.urgentContPhone = urgentContPhone;
	}
	public String getUrgentContAddress() {
		return urgentContAddress;
	}
	public void setUrgentContAddress(String urgentContAddress) {
		this.urgentContAddress = urgentContAddress;
	}
	public String getRelationship() {
		return relationship!=null?relationship:"" ;
	}
	public void setRelationship(String relationship) {
		if(relationship==null){
			this.relationship = "";
		}else{
			this.relationship = relationship;
		}
		
	}
	public String getTaobaoTreeDiamondMore() {
		return taobaoTreeDiamondMore;
	}
	public void setTaobaoTreeDiamondMore(String taobaoTreeDiamondMore) {
		this.taobaoTreeDiamondMore = taobaoTreeDiamondMore;
	}
	public String getOtherPlatform() {
		return otherPlatform;
	}
	public void setOtherPlatform(String otherPlatform) {
		this.otherPlatform = otherPlatform;
	}
	public String getOperatingPeriodMore() {
		return operatingPeriodMore;
	}
	public void setOperatingPeriodMore(String operatingPeriodMore) {
		this.operatingPeriodMore = operatingPeriodMore;
	}
	public String getHaveGuarantor() {
		return haveGuarantor;
	}
	public void setHaveGuarantor(String haveGuarantor) {
		this.haveGuarantor = haveGuarantor;
	}
	public String getShopController() {
		return shopController;
	}
	public void setShopController(String shopController) {
		this.shopController = shopController;
	}
	public String getSalesOfMore() {
		return salesOfMore;
	}
	public void setSalesOfMore(String salesOfMore) {
		this.salesOfMore = salesOfMore;
	}
	public String getThan3credit() {
		return than3credit;
	}
	public void setThan3credit(String than3credit) {
		this.than3credit = than3credit;
	}
	public String getNotOverdue() {
		return notOverdue;
	}
	public void setNotOverdue(String notOverdue) {
		this.notOverdue = notOverdue;
	}
	public String getPerNotOverdue() {
		return perNotOverdue;
	}
	public void setPerNotOverdue(String perNotOverdue) {
		this.perNotOverdue = perNotOverdue;
	}
	public String getIfShopOwner() {
		return ifShopOwner;
	}
	public void setIfShopOwner(String ifShopOwner) {
		this.ifShopOwner = ifShopOwner;
	}

}
