package bill.choosecity.bean;

import java.io.Serializable;

/**
 * 列表基类
 * 
 * @author Administrator
 * 
 * @param
 */
public class ListBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String nameDown;
	private boolean isSelected;
	private boolean isLocation;

	public ListBean(String name, String nameDown, boolean isSelected,
					boolean isLocation) {
		super();
		this.name = name;
		this.nameDown = nameDown;
		this.isSelected = isSelected;
		this.isLocation = isLocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameDown() {
		return nameDown;
	}

	public void setNameDown(String nameDown) {
		this.nameDown = nameDown;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isLocation() {
		return isLocation;
	}

	public void setLocation(boolean isLocation) {
		this.isLocation = isLocation;
	}
}
