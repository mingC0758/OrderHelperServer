package bean;

/**
 * @author mingC
 * @date 2018/5/17
 */
public class Vender {
	int id;
	String code;
	String name;
	String eateryName;

	public String getEateryName() {
		return eateryName;
	}

	public void setEateryName(String eateryName) {
		this.eateryName = eateryName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
