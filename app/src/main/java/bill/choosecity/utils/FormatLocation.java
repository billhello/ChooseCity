package bill.choosecity.utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2015/6/5.
 * 规范地区名等
 */
public class FormatLocation {

    /**
     * 去除城市名后缀
     *
     * @param cityName
     * @return
     */
    public static String getFormatByCity(String cityName) {
        if (!TextUtils.isEmpty(cityName)) {
            if (cityName.contains("市")) {
                cityName = cityName.substring(0, cityName.lastIndexOf('市'));
            } else if (cityName.contains("自治州")) {
                cityName = cityName.substring(0, cityName.lastIndexOf("自治州"));
            } else if (cityName.contains("地区")) {
                cityName = cityName.substring(0, cityName.lastIndexOf("地区"));
            }
            return cityName;
        }
        return "";
    }
}
