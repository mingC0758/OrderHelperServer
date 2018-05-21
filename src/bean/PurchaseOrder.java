package bean;

import java.util.List;

/**
 * 采购订单
 * @author mingC
 * @date 2018/3/17
 */
public class PurchaseOrder{

	//食堂名
	String eateryName;

	//订单编号（用于索引）
	int orderCode;

	int planCode; //是由哪个计划切割出来的

	String status; //代发货、已发货、已签收

	//采购列表
	List<Requirement> purchaseList;

	//提交时间
	String submitTime;

	//提交人
	String submitUser;

	/** 收货联系信息*/
	String receiverName;
	String receiveAddress;
	String receiverPhone;

	//用户签名
	String signPicUrl;

	//备注文字
	String noteText;

	//备注图片url
	List<String> notePicUrls;

//	//发货时间
//	String dispatchTime;
//
//	//收货时间
//	String receiveTime;

	public String getEateryName() {
		return eateryName;
	}

	public void setEateryName(String eateryName) {
		this.eateryName = eateryName;
	}

	public int getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}

	public int getPlanCode() {
		return planCode;
	}

	public void setPlanCode(int planCode) {
		this.planCode = planCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Requirement> getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(List<Requirement> purchaseList) {
		this.purchaseList = purchaseList;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getSubmitUser() {
		return submitUser;
	}

	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getSignPicUrl() {
		return signPicUrl;
	}

	public void setSignPicUrl(String signPicUrl) {
		this.signPicUrl = signPicUrl;
	}

	public String getNoteText() {
		return noteText;
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}

	public List<String> getNotePicUrls() {
		return notePicUrls;
	}

	public void setNotePicUrls(List<String> notePicUrls) {
		this.notePicUrls = notePicUrls;
	}
}
