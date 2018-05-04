package bean;

import java.util.List;

/**
 * 采购需求，流向采购计划
 * @author mingC
 * @date 2018/3/20
 */
public class Requirement {
	//需求编号
	int needId;

	//食堂编号
	int eateryCode;

	//品种名称
	String varietyName;

	//规格
	String specification = "kg";

	//品种编号
	int varietyCode;

	//产品图片
	String img;

	//数量
	int amount = 0;

	//报价
	double price = 0;

	//提交日期
	String submitTime;

	//档口名称
	String storeName;

	//实收数量
	int actualAmount;

	//冲销记录
	List<WriteOff> writeOffs;

	//状态
	String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getNeedId() {
		return needId;
	}

	public void setNeedId(int needId) {
		this.needId = needId;
	}

	public int getEateryCode() {
		return eateryCode;
	}

	public void setEateryCode(int eateryCode) {
		this.eateryCode = eateryCode;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public int getVarietyCode() {
		return varietyCode;
	}

	public void setVarietyCode(int varietyCode) {
		this.varietyCode = varietyCode;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public int getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(int actualAmount) {
		this.actualAmount = actualAmount;
	}

	public List<WriteOff> getWriteOffs() {
		return writeOffs;
	}

	public void setWriteOffs(List<WriteOff> writeOffs) {
		this.writeOffs = writeOffs;
	}
}