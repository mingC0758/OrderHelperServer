package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.User;

/**
 * 用户管理、帐号管理
 * @author mingC
 * @date 2018/5/14
 */
public class UserDataHelper extends BaseDataHelper{

	public static String ACCOUNT_NOT_FOUNT = "Account not found";
	public static String PWD_INCORRECT = "Password incorrect";
	public static String CORRECT = "correct";

	public static boolean insertUser(User user) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO user(account, password, name, role, duty,higher) VALUES(?,?,?,?,?,?) ");
			statement.setString(1, user.getAccount());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getName());
			statement.setString(4, user.getRole());
			statement.setString(5, user.getDuty());
			statement.setString(6, user.getHigher());
			if (1 == statement.executeUpdate()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/***
	 * 更新用户信息，账号不能更改
	 * @param user
	 * @return
	 */
	public static boolean updateUser(User user) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE user SET password=? WHERE id=?");
			statement.setString(1, user.getPassword());
			statement.setInt(2, user.getId());
			if (1 == statement.executeUpdate()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean userExists(User user) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * from user WHERE id=?");
			statement.setInt(1, user.getId());
			if (statement.executeQuery().next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String checkAccount(String account, String password) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * from user WHERE account=?");
			statement.setString(1, account);
			ResultSet resultSet = statement.executeQuery();
			String pwd = null;
			if (resultSet.next()) {
				pwd = resultSet.getString("password");
			}
			//如果没找到pwd为null
			if (pwd == null) {
				return ACCOUNT_NOT_FOUNT;   //帐号未找到
			}
			if (! pwd.equals(password)) {
				return PWD_INCORRECT;   //密码不对
			}
			return CORRECT; //正确
		}catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public static List<User> getUserList() {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		List<User> userList = new LinkedList<>();
		try {
			statement = connection.prepareStatement("SELECT * from user WHERE account <> 'root'");  //屏蔽超级管理用户
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				userList.add(rsToUser(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	public static User getUser(String account) {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		User user = null;
		try {
			statement = connection.prepareStatement("SELECT * from user WHERE account=?");  //屏蔽超级管理用户
			statement.setString(1, account);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				user = rsToUser(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	private static User rsToUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt("id"));
		user.setAccount(resultSet.getString("account"));
		user.setAddress(resultSet.getString("address"));
		user.setPassword(resultSet.getString("password"));
		user.setName(resultSet.getString("name"));
		user.setPicUrl(resultSet.getString("pic_url"));
		user.setDuty(resultSet.getString("duty"));
		user.setRole(resultSet.getString("role"));
		user.setHigher(resultSet.getString("higher"));
		return user;
	}
}
