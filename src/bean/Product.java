package bean;

/**
 * @author mingC
 * @date 2018/1/30
 */
public class Product {
	//名称
	private String name;

	public static void send(String str) {
		System.out.println("string");
	}
	public static void send(Object str) {
		System.out.println("object");
	}

	public static void main(String[] args) {
	}


	//数量
	private int num;

	//单位
	private String unit;

	public Product(String name, int num, String unit) {
		this.name = name;
		this.num = num;
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}