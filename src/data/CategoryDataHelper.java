package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.Category;

/**
 * @author mingC
 * @date 2018/5/31
 */
public class CategoryDataHelper extends BaseDataHelper{

	public static List<Category> getCategoryList() {
		List<Category> list = new LinkedList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM category");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Category category = new Category();
				category.setId(resultSet.getInt("id"));
				category.setFirst(resultSet.getString("first_cat"));
				category.setSecond(resultSet.getString("second_cat"));
				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return list;
	}

	public static boolean delCategory(int catId) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM category WHERE id=?");
			statement.setInt(1, catId);
			if (statement.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return false;
	}

	public static boolean addCategory(Category category) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO category(first_cat, second_cat) VALUES (?,?)");
			statement.setString(1, category.getFirst());
			statement.setString(2, category.getSecond());
			if (statement.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return false;
	}
}
