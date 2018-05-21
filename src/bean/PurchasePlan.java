package bean;

import java.util.List;
/**
 * 采购计划
 * @author mingC
 * @date 2018/3/14
 */
public class PurchasePlan {
	//采购计划代码
	int planCode = 0;

	//食堂名称
	String eateryName;

	//状态
	String status = "草";

	//包含的需求列表
	List<Requirement> requirementList;

	//提交时间
	String submitTime;

	/**联系信息**/
	String receiverName = "xxxx";
	String receiveAddress = "xxxxxx";
	String receiverPhone = "xxxxx";

	public PurchasePlan() {
	}

	public String getEateryName() {
		return eateryName;
	}

	public void setEateryName(String eateryName) {
		this.eateryName = eateryName;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
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

	public List<Requirement> getRequirementList() {
		return requirementList;
	}

	public void setRequirementList(List<Requirement> requirementList) {
		this.requirementList = requirementList;
	}

	public java.lang.String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(java.lang.String receiverName) {
		this.receiverName = receiverName;
	}

	public java.lang.String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(java.lang.String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public java.lang.String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(java.lang.String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

}