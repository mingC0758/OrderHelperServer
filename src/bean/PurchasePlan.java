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

	//食堂编号
	int eateryCode = 0;

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

	public PurchasePlan(int eateryCode, String status, List<Requirement> requirementList,
	                    String submitTime, String receiverName, String receiveAddress,
	                    String receiverPhone) {
		this.eateryCode = eateryCode;
		this.status = status;
		this.requirementList = requirementList;
		this.submitTime = submitTime;
		this.receiverName = receiverName;
		this.receiveAddress = receiveAddress;
		this.receiverPhone = receiverPhone;
	}

	public int getEateryCode() {
		return eateryCode;
	}

	public void setEateryCode(int eateryCode) {
		this.eateryCode = eateryCode;
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

	@Override
	public String toString() {
		return "PurchasePlan{" + "planCode=" + planCode + ", eateryCode=" + eateryCode + ", status='" + status + '\'' + ", requirementList=" + requirementList + ", submitTime='" + submitTime + '\'' + ", receiverName='" + receiverName + '\'' + ", receiveAddress='" + receiveAddress + '\'' + ", receiverPhone='" + receiverPhone + '\'' + '}';
	}
}