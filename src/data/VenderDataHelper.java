package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.Vender;

/**
 * @author mingC
 * @date 2018/5/17
 */
public class VenderDataHelper extends BaseDataHelper{

	public static List<Vender> getVenderList() {
		List<Vender> list = new LinkedList<>();
		Connection connection = getConnection();
		try {
			ResultSet resultSet = connection.prepareStatement("SELECT * FROM vender").executeQuery();
			while (resultSet.next()) {
				Vender vender = new Vender();
				vender.setId(resultSet.getInt("id"));
				vender.setCode(resultSet.getString("code"));
				vender.setName(resultSet.getString("name"));
				list.add(vender);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
