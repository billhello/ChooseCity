package bill.choosecity.bean;

/**
 * 地区列表
 * 
 * @author Administrator
 * @param <T>
 * 
 */
public class RegionBean extends ListBean {
	/**
	 * 地区id
	 */
	private String regionId;
	/**
	 * 地区编码
	 */
	private String regionCode;

	/**
	 * 上一级地区id
	 */
	private String parentId;
	/**
	 * 排序
	 */
	private String sortOrder;

	public RegionBean(String regionId, String regionCode, String regionName,
					  String regionNameDown, String parentId, String sortOrder) {
		super(regionName, regionNameDown, false, false);
		this.regionId = regionId;
		this.regionCode = regionCode;
		this.parentId = parentId;
		this.sortOrder = sortOrder;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}
