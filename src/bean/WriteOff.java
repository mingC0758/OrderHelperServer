package bean;

/**
 * 冲销记录实体
 * @author mingC
 * @date 2018/4/21
 */
public class WriteOff {
	//序号
	int id;

	int needId;

	//冲销类型
	String type;
	//冲销数量
	int amount;
	//提交人
	String submitUser;
	//提交时间
	String submitTime;
	//冲销原因
	String reasonText;

	public int getNeedId() {
		return needId;
	}

	public void setNeedId(int needId) {
		this.needId = needId;
	}

	@Override
	public String toString() {
		return "WriteOff{" + "id=" + id + ", needId=" + needId + ", type='" + type + '\'' + ", amount=" + amount + ", submitUser='" + submitUser + '\'' + ", submitTime='" + submitTime + '\'' + ", reasonText='" + reasonText + '\'' + '}';
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getSubmitUser() {
		return submitUser;
	}

	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}
}
