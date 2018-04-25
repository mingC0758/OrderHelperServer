package data;

import java.sql.Connection;
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
			variety.setPic_url(set.getString("pic_url"));
			list.add(variety);
		}
		return list;
	}

	public static List<Variety> getVarietyList(){
		try {
			Connection connection = getConnection();
			ResultSet set = connection.createStatement().executeQuery("SELECT * FROM variety");
			List<Variety> list = cursorToVarietys(set);
			connection.close();
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
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
		try {
			Connection connection = getConnection();
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
			connection.close();
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static void main(String[] args) {
		System.out.println(getProductBeanList());
	}
}
