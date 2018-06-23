package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.ProductBean;
import bean.Variety;

/**
 * @author mingC
 * @date 2018/3/23
 */
public class VarietyDataHelper extends BaseDataHelper{

	public static List<Variety> cursorToVarietys(ResultSet set) throws SQLException {
		List<Variety> list = new LinkedList<>();
		while (set.next()) {
			Variety variety = new Variety();
			variety.setVarietyCode(set.getInt("id"));
			variety.setName(set.getString("name"));
			variety.setPrice(set.getDouble("price"));
			variety.setSpecification(set.getString("specification"));
			variety.setCategory(set.getString("category_second"));
			variety.setVenderName(set.getString("venderName"));
			variety.setCategoryFirst(set.getString("category_first"));
			variety.setCategorySecond(set.getString("category_second"));
			variety.setCategoryThird(set.getString("category_third"));
			variety.setPic_url(set.getString("pic_url"));
			list.add(variety);
		}
		return list;
	}

	public static List<Variety> getVarietyList(){
		Connection connection = getConnection();
		try {
			ResultSet set = connection.createStatement().executeQuery("SELECT * FROM variety");
			List<Variety> list = cursorToVarietys(set);
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return null;
	}

	/**
	 * 1. 找出一级分类，生成一级ProductBean列表
	 * 2. 遍历一级ProductBean列表，找出每一个一级分类下的二级分类ProductBean，并设为一级ProductBean的subProducts
	 * 3. 遍历所有二级分类下的产品，存入对应的subProducts中的varietyList
	 * @return
	 */
	public static List<ProductBean> getProductBeanList(){
		Connection connection = getConnection();
		try {
			ResultSet setFirst = connection.createStatement().executeQuery("SELECT DISTINCT category_first FROM variety");
			List<ProductBean> list = new LinkedList<>();
			//找出一级
			while (setFirst.next()) {
				ProductBean bean = new ProductBean();
				bean.setTypeName(setFirst.getString(1));
				list.add(bean);
			}
			//遍历一级，找出二级
			for (ProductBean bean : list) {
				ResultSet setSecond = connection.createStatement().executeQuery("SELECT DISTINCT category_second FROM variety WHERE category_first='" + bean.getTypeName()+"'");
				List<ProductBean> subProducts = new LinkedList<>();
				bean.setSubProducts(subProducts);
				while (setSecond.next()) {
					ProductBean bean2 = new ProductBean();
					bean2.setTypeName(setSecond.getString(1));
					subProducts.add(bean2);
				}
				for (ProductBean subProduct : subProducts) {
					//遍历二级，找出产品
					ResultSet setProduct = connection.createStatement().executeQuery("SELECT * FROM variety WHERE category_second='" + subProduct.getTypeName()+"'");
					List<Variety> varieties = cursorToVarietys(setProduct);
					subProduct.setVarietyList(varieties);
				}
			}
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return null;
	}

	/**
	 * 插入新的品种
	 * @param variety
	 * @return 如果成功，返回RET_OK
	 */
	public static String insertVariety(Variety variety) {
		Connection connection = getConnection();
		try {
			//设置分类
			PreparedStatement statement = connection.prepareStatement("INSERT INTO variety(name, category_second, price, venderName, specification, vender_code, category_first, pic_url) VALUES (?,?,?,?,?,?,?,?)");
			statement.setString(1, variety.getName());
			statement.setString(2, variety.getCategorySecond());
			statement.setDouble(3, variety.getPrice());
			statement.setString(4, variety.getVenderName());
			statement.setString(5, variety.getSpecification());
			statement.setInt(6, -1);    //供应商代码
			statement.setString(7, variety.getCategoryFirst());
			statement.setString(8, "null url");
			if (1 == statement.executeUpdate()) {
				return RET_OK;
			}
			return RET_ERROR;
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * 更新品种的**供应商信息**
	 * @param variety
	 * @return
	 */
	public static String updateVariety(Variety variety) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE variety SET venderName=? WHERE id=?");
			statement.setString(1, variety.getVenderName());
			statement.setInt(2, variety.getVarietyCode());
			if (1 == statement.executeUpdate()) {
				return RET_OK;
			}
			return RET_ERROR;
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			closeConnection(connection);
		}
	}

	public static String delVariety(Variety variety) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM variety WHERE id=?");
			statement.setInt(1, variety.getVarietyCode());
			if (1 == statement.executeUpdate()) {
				return RET_OK;
			}
			return "update row != 1";
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			closeConnection(connection);
		}
	}
}
