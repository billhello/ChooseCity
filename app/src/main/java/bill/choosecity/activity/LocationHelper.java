package bill.choosecity.activity;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import bill.choosecity.bean.LngLatBean;
import bill.choosecity.data.AddressManager;
import bill.choosecity.listener.OnLocationListener;
import bill.choosecity.utils.FormatLocation;
import bill.common.data.WeddingHelper;
import bill.common.utils.MyLog;

/**
 * 定位地理位置单例类
 *
 * @author Administrator
 */
public class LocationHelper implements AMapLocationListener {
    private static LocationHelper locationHelper;
    private LocationManagerProxy aMapLocManager = null;
    private Context context;
    private OnLocationListener onLocationListener;

    public static LocationHelper getInstance(Context context) {
        if (locationHelper == null) {
            synchronized (LocationHelper.class) {
                if (locationHelper == null) {
                    locationHelper = new LocationHelper(context.getApplicationContext());
                }
            }
        }
        return locationHelper;
    }

    private LocationHelper(Context context) {
        this.context = context;
    }

    /*
         * mAMapLocManager.setGpsEnable(false);// 1.0
         * .2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位 ，默认是true Location
         * API定位采用GPS和网络混合定位方式 ，第一个参数是定位provider ，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米
         * ，第四个参数是定位监听者
         *
         *
     */
    public void requestLocationUpdates(OnLocationListener onLocationListener) {
        /*  String city = AddressManager.getAddress(context, AddressManager.CITY_ID);
        *//*如果定位城市不存在，则继续定位；否则不定位*//*
        if (TextUtils.isEmpty(city) || isLocation) {*/
        this.onLocationListener = onLocationListener;
        aMapLocManager = LocationManagerProxy.getInstance(context);
        aMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork,
                60 * 1000, 15, this);
        /*}*/
    }


    @Override
    public void onLocationChanged(Location arg0) {
        MyLog.i(context, arg0.toString());
    }

    @Override
    public void onProviderDisabled(String arg0) {
        MyLog.i(context, arg0);
    }

    @Override
    public void onProviderEnabled(String arg0) {
        MyLog.i(context, arg0);
    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        MyLog.i(context, arg0);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getAMapException().getErrorCode() == 0) {
                LngLatBean lngLatBean = new LngLatBean();
                lngLatBean.lng = String.valueOf(amapLocation.getLongitude());
                lngLatBean.lat = String.valueOf(amapLocation.getLatitude());
                String cityCode = "";
                String desc = "";
                Bundle locBundle = amapLocation.getExtras();
                if (locBundle != null) {
                    cityCode = locBundle.getString("citycode");
                    desc = locBundle.getString("desc");
                }
                lngLatBean.citycode = cityCode;
                String locationCode = amapLocation.getAdCode();
                if (locationCode.length() == 6) {
                    lngLatBean.city_id = WeddingHelper.getInstance(context).getRegionIdByCode(locationCode.substring(0, 4) + "00");
                    MyLog.e(context, lngLatBean.desc);
                    /* 两次定位，如果不是同一个城市,则覆盖以前的位置*/
                    if (!AddressManager.getAddress(context, AddressManager.CITY_ID)
                            .equals(lngLatBean.city_id)) {
                        lngLatBean.province_name = FormatLocation.getFormatByCity(amapLocation.getProvince());
                        lngLatBean.province_id = WeddingHelper.getInstance(context).getRegionIdByCode(locationCode.substring(0, 3) + "000");
                        lngLatBean.city_name = FormatLocation.getFormatByCity(amapLocation.getCity());
                        lngLatBean.district_name = FormatLocation.getFormatByCity(amapLocation.getDistrict());
                        lngLatBean.district_id = WeddingHelper.getInstance(context).getRegionIdByCode(locationCode);
                        lngLatBean.adCode = locationCode;
                        lngLatBean.desc = desc;
                        /*请求用户是否使用目前定位城市*/
                        AddressManager.saveAddress(context, lngLatBean);
                        onLocationListener.onLocationSuccess();
                    }
                }
                stopLocation();
            } else {
                //上一次定位在在上海，这次没有定位成功，还是在上海
                // 一直没有定位成功，默认北京
              /*if (TextUtils.isEmpty(AddressManager.getAddressProvinceId(context, AddressManager.PROVINCE_ID))) {
                LngLatBean lngLatBean = new LngLatBean();
                lngLatBean.province_id = ((RegionBean) WeddingHelper.getInstance(context)
                        .getRegionParentLocation(AddressManager.DEFAULT_ADCODE)).getParentId();
                AddressManager.saveAddress(context, lngLatBean);
            }*/
                onLocationListener.onLocationFailed();
                MyLog.e(context, amapLocation.getAMapException().getErrorMessage());
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopLocation();
            }
        }, 60000);
    }

    /**
     * 停止定位
     */

    public void stopLocation() {
        if (aMapLocManager != null) {
            aMapLocManager.removeUpdates(this);
            aMapLocManager.destory();
        }
        aMapLocManager = null;
    }
}
