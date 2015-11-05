package bill.choosecity.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import bill.choosecity.bean.LngLatBean;


/**
 * 地址位置管理
 *
 * @author Administrator
 */
public class AddressManager {
    private final static String ADDRESSCOOKIE_PREF = "addressCookie";

    public static final String DEFAULT_ADCODE = "110000"; // 默认地域编码
    public static final String LONGITUDE = "longitude"; // 经度
    public static final String LATITUDE = "latitude"; // 纬度
    public static final String CITYCODE = "cityCode"; // 城市编码
    public static final String DESC = "desc"; // 位置描述
    public static final String PROVINCE_NAME = "province_name"; // 省
    public static final String PROVINCE_ID = "province_id"; // 省
    public static final String CITY_NAME = "city_name"; // 市
    public static final String CITY_ID = "city_id"; // 市
    public static final String DISTRICT_NAME = "district_name"; // 区
    public static final String DISTRICT_ID = "district_id"; // 区
    public static final String CHOOSE_CITY_NAME = "choose_city";
    public static final String CHOOSE_CITY_ID = "choose_city_id";
    public static final String CHOOSE_PROVINCE_ID = "choose_province_id";


    public static final String ADCODE = "adCode"; // 地域编码
    public static final String DEFAULT_CITY_NAME = "上海";
    public static final String DEFAULT_CITY_ID = "793";

    /**
     * 将LngLatBean保存在本地
     *
     * @param context
     * @param lngLatBean
     */
    public static void saveAddress(Context context, LngLatBean lngLatBean) {
        SharedPreferences preferences = context.getSharedPreferences(
                ADDRESSCOOKIE_PREF, 0);
        Editor editor = preferences.edit();
        editor.putString(AddressManager.LONGITUDE, lngLatBean.lng);
        editor.putString(AddressManager.LATITUDE, lngLatBean.lat);
        editor.putString(AddressManager.CITYCODE, lngLatBean.citycode);
        editor.putString(AddressManager.DESC, lngLatBean.desc);
        editor.putString(AddressManager.PROVINCE_NAME, lngLatBean.province_name);
        editor.putString(AddressManager.PROVINCE_ID, lngLatBean.province_id);
        editor.putString(AddressManager.CITY_NAME, lngLatBean.city_name);
        editor.putString(AddressManager.CITY_ID, lngLatBean.city_id);
        editor.putString(AddressManager.DISTRICT_NAME, lngLatBean.district_name);
        editor.putString(AddressManager.DISTRICT_ID, lngLatBean.district_id);
        editor.putString(AddressManager.ADCODE, lngLatBean.adCode);
        editor.apply();
    }

    /**
     * 返回LngLatBean对象
     *
     * @param context
     * @return
     */
    public static LngLatBean getLngLat(Context context) {
        LngLatBean lngLatBean = new LngLatBean();
        SharedPreferences preferences = context.getSharedPreferences(
                ADDRESSCOOKIE_PREF, 0);
        lngLatBean.lng = preferences.getString(AddressManager.LONGITUDE, null);
        lngLatBean.lat = preferences.getString(AddressManager.LATITUDE, null);
        lngLatBean.citycode = preferences.getString(AddressManager.CITYCODE,
                null);
        lngLatBean.desc = preferences.getString(AddressManager.DESC, null);
        lngLatBean.province_name = preferences.getString(AddressManager.PROVINCE_NAME,
                null);
        lngLatBean.province_id = preferences.getString(AddressManager.PROVINCE_ID,
                null);
        lngLatBean.city_name = preferences.getString(AddressManager.CITY_NAME, null);
        lngLatBean.city_id = preferences.getString(AddressManager.CITY_ID, null);
        lngLatBean.district_name = preferences.getString(AddressManager.DISTRICT_NAME,
                null);
        lngLatBean.district_id = preferences.getString(AddressManager.DISTRICT_ID,
                null);
        lngLatBean.adCode = preferences.getString(AddressManager.ADCODE, null);
        return lngLatBean;
    }

    /**
     * 根据指定的键名获得值
     *
     * @param context
     * @param name    键名
     * @return
     */
    public static String getAddress(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                ADDRESSCOOKIE_PREF, 0);
        // 如果定位不成功，则默认
        return preferences.getString(name, "");
    }

    /**
     * 根据key保存value值
     *
     * @param context
     * @param key
     * @return
     */
    public static void saveAddress(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(ADDRESSCOOKIE_PREF,
                0);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
/*
    *//**
     * 返回默认省市名
     *
     * @param context
     * @param name
     * @return
     *//*
    public static String getAddressCityName(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(
                ADDRESSCOOKIE_PREF, 0);
        // 如果定位不成功，则默认
        return preferences.getString(name, DEFAULT_CITY_NAME);
    }

    *//**
     * 返回默认省市ID
     *
     * @param context
     * @param id
     * @return
     *//*
    public static String getAddressCityId(Context context, String id) {
        SharedPreferences preferences = context.getSharedPreferences(
                ADDRESSCOOKIE_PREF, 0);
        // 如果定位不成功，则默认
        return preferences.getString(id, DEFAULT_CITY_ID);
    }*/
}
