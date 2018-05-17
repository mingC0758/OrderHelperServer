package bean;

/**
 * @author mingC
 * @date 2018/5/13
 */
public class User {
	private int id;
	private String account;
	private String password;
	private String role;
	private String duty;
	private String name;
	private String address;
	private String tel;
	private String postcode;
	private String email;
	private String createTime;
	private String picUrl;
	private String higher;//上级

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", account='" + account + '\'' + ", password='" + password + '\'' + ", role='" + role + '\'' + ", duty='" + duty + '\'' + ", name='" + name + '\'' + ", address='" + address + '\'' + ", tel='" + tel + '\'' + ", postcode='" + postcode + '\'' + ", email='" + email + '\'' + ", createTime='" + createTime + '\'' + ", picUrl='" + picUrl + '\'' + ", higher='" + higher + '\'' + '}';
	}

	public String getHigher() {
		return higher;
	}

	public void setHigher(String higher) {
		this.higher = higher;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
