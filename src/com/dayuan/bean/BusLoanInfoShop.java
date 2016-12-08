package com.dayuan.bean;

/**实体或店铺*/
public class BusLoanInfoShop{
	private Integer id;
	private Integer bid;
	private String shopName;
	private String platformName;
	private String shopLevel;
	private String operatingPeriod;
	private String shopOwner;
	private String subAccount;
	private String sbuPassword;
	private String businessOpera;
	private String businessAddress;
	private String warehouseAddress;
	private String salesIncome;
	private String totalLiability;
	private String bankLiabilities;
	private String netProfit;
	
	
	/**扩展属性，更新时临时存放主键id*/
	private Integer tempShopId;
	
	
	public Integer getTempShopId() {
		return tempShopId;
	}
	public void setTempShopId(Integer tempShopId) {
		this.tempShopId = tempShopId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getShopName(){
		return this.shopName!=null?shopName:"";
	}
	public void setShopName(String shopName){
		if(shopName==null){
			this.shopName="";
		}else{
			this.shopName=shopName;
		}
		
	}
	public String getPlatformName(){
		return this.platformName!=null?platformName:"";
	}
	public void setPlatformName(String platformName){
		if(platformName==null){
			this.platformName="";
		}else{
			this.platformName=platformName;
		}
		
	}
	public String getShopLevel(){
		return this.shopLevel;
	}
	public void setShopLevel(String shopLevel){
		this.shopLevel=shopLevel;
	}
	public String getOperatingPeriod(){
		return this.operatingPeriod;
	}
	public void setOperatingPeriod(String operatingPeriod){
		this.operatingPeriod=operatingPeriod;
	}
	public String getShopOwner(){
		return this.shopOwner;
	}
	public void setShopOwner(String shopOwner){
		this.shopOwner=shopOwner;
	}
	public String getSubAccount(){
		return this.subAccount!=null?subAccount:"";
	}
	public void setSubAccount(String subAccount){
		if(subAccount==null){
			this.subAccount="";
		}else{
			this.subAccount=subAccount;
		}
		
	}
	public String getSbuPassword(){
		return this.sbuPassword!=null?sbuPassword:"";
	}
	public void setSbuPassword(String sbuPassword){
		if(sbuPassword==null){
			this.sbuPassword="";
		}else{
			this.sbuPassword=sbuPassword;
		}
		
	}
	public String getBusinessOpera(){
		return this.businessOpera;
	}
	public void setBusinessOpera(String businessOpera){
		this.businessOpera=businessOpera;
	}
	public String getBusinessAddress(){
		return this.businessAddress;
	}
	public void setBusinessAddress(String businessAddress){
		this.businessAddress=businessAddress;
	}
	public String getWarehouseAddress(){
		return this.warehouseAddress;
	}
	public void setWarehouseAddress(String warehouseAddress){
		this.warehouseAddress=warehouseAddress;
	}
	public String getSalesIncome(){
		return this.salesIncome;
	}
	public void setSalesIncome(String salesIncome){
		this.salesIncome=salesIncome;
	}
	public String getTotalLiability(){
		return this.totalLiability;
	}
	public void setTotalLiability(String totalLiability){
		this.totalLiability=totalLiability;
	}
	public String getBankLiabilities(){
		return this.bankLiabilities;
	}
	public void setBankLiabilities(String bankLiabilities){
		this.bankLiabilities=bankLiabilities;
	}
	public String getNetProfit(){
		return this.netProfit;
	}
	public void setNetProfit(String netProfit){
		this.netProfit=netProfit;
	}

}
