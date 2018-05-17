package bean;

/**
 * @author mingC
 * @date 2018/3/25
 */
public class ResultBean {
	int code;
	String msg;

	public ResultBean() {
	}

	public ResultBean(int code, String msg) {
		this.code = code; //1是成功
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ResultBean{" + "code=" + code + ", msg='" + msg + '\'' + '}';
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
