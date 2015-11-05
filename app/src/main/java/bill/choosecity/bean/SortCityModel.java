package bill.choosecity.bean;

import java.util.List;

/**
 * 排序城市
 */
public class SortCityModel {
    private List<RegionBean> cityRegions; //城市
    private String cityRegionSortLetter;  //显示城市名首字母

    public SortCityModel() {
    }

    public SortCityModel(List<RegionBean> cityRegions, String cityRegionSortLetter) {
        this.cityRegions = cityRegions;
        this.cityRegionSortLetter = cityRegionSortLetter;
    }

    public List<RegionBean> getCityRegions() {
        return cityRegions;
    }

    public void setCityRegions(List<RegionBean> cityRegions) {
        this.cityRegions = cityRegions;
    }

    public String getCityRegionSortLetter() {
        return cityRegionSortLetter;
    }

    public void setCityRegionSortLetter(String cityRegionSortLetter) {
        this.cityRegionSortLetter = cityRegionSortLetter;
    }
}
