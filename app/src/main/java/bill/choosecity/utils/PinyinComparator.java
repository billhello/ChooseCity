package bill.choosecity.utils;


import java.util.Comparator;

import bill.choosecity.bean.SortCityModel;

/**
 * @author xiaanming
 */
public class PinyinComparator implements Comparator<SortCityModel> {

    public int compare(SortCityModel o1, SortCityModel o2) {
        if (o1.getCityRegionSortLetter().equals("@")
                || o2.getCityRegionSortLetter().equals("#")) {
            return -1;
        } else if (o1.getCityRegionSortLetter().equals("#")
                || o2.getCityRegionSortLetter().equals("@")) {
            return 1;
        } else {
            return o1.getCityRegionSortLetter().compareTo(o2.getCityRegionSortLetter());
        }
    }
}
