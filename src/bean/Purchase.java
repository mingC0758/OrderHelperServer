package bean;

/**
 * 采购列表
 * @author mingC
 * @date 2018/3/20
 */
public class Purchase{
	//品种名称
	String varietyName;

	//规格
	String specification;

	//品种编号
	int varietyCode;

	//数量
	int amount;

	//报价
	double price;

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
}
