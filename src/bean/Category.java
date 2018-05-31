package bean;

/**
 * @author mingC
 * @date 2018/5/31
 */
public class Category {
	int id;
	String first;
	String second;

	public Category() {
	}

	public Category(String first, String second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString() {
		return "Category{" + "id=" + id + ", first='" + first + '\'' + ", second='" + second + '\'' + '}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}
}
