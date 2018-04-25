package bean;


/**
 * 品种实体，用于供食堂选择
 * @author mingC
 * @date 2018/3/15
 */
public class Variety{
	int varietyCode;
	String name;
	String category;
	double price;
	String venderName;
	String specification;
	String categoryFirst;
	String categorySecond;
	String pic_url;

	@Override
	public String toString() {
		return "Variety{" + "varietyCode=" + varietyCode + ", name='" + name + '\'' + ", category='" + category + '\'' + ", price=" + price + ", venderName='" + venderName + '\'' + ", specification='" + specification + '\'' + ", categoryFirst='" + categoryFirst + '\'' + ", categorySecond='" + categorySecond + '\'' + ", pic_url='" + pic_url + '\'' + '}';
	}

	public int getVarietyCode() {
		return varietyCode;
	}

	public void setVarietyCode(int varietyCode) {
		this.varietyCode = varietyCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getCategoryFirst() {
		return categoryFirst;
	}

	public void setCategoryFirst(String categoryFirst) {
		this.categoryFirst = categoryFirst;
	}

	public String getCategorySecond() {
		return categorySecond;
	}

	public void setCategorySecond(String categorySecond) {
		this.categorySecond = categorySecond;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
}
