package bean;

import java.util.List;

/**
 * 服务器返回的货品名，包括分类以及品种
 * @author mingC
 * @date 2018/4/10
 */
public class ProductBean {
	int typeId;
	String typeName;
	List<ProductBean> subProducts;
	List<Variety> varietyList;

	public List<Variety> getVarietyList() {
		return varietyList;
	}

	public void setVarietyList(List<Variety> varietyList) {
		this.varietyList = varietyList;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<ProductBean> getSubProducts() {
		return subProducts;
	}

	public void setSubProducts(List<ProductBean> subProducts) {
		this.subProducts = subProducts;
	}

	@Override
	public String toString() {
		return "ProductBean{" + "typeId=" + typeId + ", typeName='" + typeName + '\'' + ", subProducts=" + subProducts + ", varietyList=" + varietyList + '}';
	}
}
