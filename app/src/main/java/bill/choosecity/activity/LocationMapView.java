package bill.choosecity.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.halobear.wedqq.R;

/**
 * 地图
 * 
 * @author Administrator
 * 
 */
public class LocationMapView extends LinearLayout {
	// 高德地图
	private MapView mapView;
	// private LatLng latLng;
	// private GeocodeSearch geocoderSearch;
	// private LatLonPoint latLonPoint;
	// private LocationMapHelper locationMapHelper;
	private AMap aMap;

	private MarkerOptions markerOptions;
	private UiSettings mUiSettings;
	private LinearLayout layout;

	public LocationMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		layout = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.layout_mapview, null);
		mapView = (MapView) layout.findViewById(R.id.map);

		if (aMap == null) {
			aMap = mapView.getMap();

			aMap.moveCamera(CameraUpdateFactory.zoomBy(16.0F));
			mUiSettings = aMap.getUiSettings();
			// mUiSettings.setCompassEnabled(true);
			mUiSettings.setZoomControlsEnabled(false); // 设置地图默认的缩放按钮不显示
			// mUiSettings.setScrollGesturesEnabled(true); // 设置地图是不可以手势滑动
			mUiSettings.setZoomGesturesEnabled(true); // 设置地图是不可以手势缩放大小
			// mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);//
			// 设置地图logo显示在右下方
			// initAMap();
		}

	}

	/**
	 * 加载显示地图
	 * 
	 * @param latLng
	 *            经纬度
	 */
	public void loadingMap(LatLng latLng) {
		markerOptions = new MarkerOptions()
				.position(latLng)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED))
				.draggable(true);
		aMap.addMarker(markerOptions);
		this.aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
		addView(layout);
	}

	public void onCreate(Bundle bundle) {
		mapView.onCreate(bundle);
	}

	public void onResume() {
		mapView.onResume();
	}

	public void onPause() {
		mapView.onPause();
	}

	public void onSaveInstanceState(Bundle outState) {
		mapView.onSaveInstanceState(outState);
	}

	public void release() {
		mapView.onDestroy();
	}

	// @Override
	// public void onMapLoaded() {
	// ToastUtil.show(getContext(), "正在加载地图");
	// if (latLng == null)
	// return;
	// markerOptions = new MarkerOptions()
	// .position(latLng)
	// .icon(BitmapDescriptorFactory
	// .defaultMarker(BitmapDescriptorFactory.HUE_RED))
	// .anchor(0.5f, 0.5f).draggable(true);
	// aMap.addMarker(markerOptions);
	// this.aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
	// }

	/**
	 * 响应逆地理编码
	 */
	// public void getAddress(LatLonPoint latLonPoint) {
	// this.latLonPoint = latLonPoint;
	//
	// RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
	// GeocodeSearch.AMAP);//
	// // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
	// if (geocoderSearch == null) {
	// geocoderSearch = new GeocodeSearch(getContext());
	// geocoderSearch.setOnGeocodeSearchListener(this);
	// }
	// geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
	// // mapView.setVisibility(View.VISIBLE);
	// }
	//
	// @Override
	// public void onGeocodeSearched(GeocodeResult result, int rCode) {
	// }
	//
	// // 根据坐标定位
	// @Override
	// public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
	// if (rCode == 0) {
	// if (result != null && result.getRegeocodeAddress() != null
	// && result.getRegeocodeAddress().getFormatAddress() != null) {
	// locationMapHelper.getAMap().animateCamera(
	// CameraUpdateFactory.newLatLngZoom(
	// locationMapHelper.convertToLatLng(latLonPoint),
	// 15));
	// locationMapHelper.getMarker().setPosition(
	// locationMapHelper.convertToLatLng(latLonPoint));
	//
	// }
	// } else if (rCode == 27) {
	// ToastUtil.show(getContext(), R.string.error_network);
	// } else if (rCode == 32) {
	// ToastUtil.show(getContext(), R.string.error_key);
	// } else {
	// ToastUtil.show(getContext(),
	// getContext().getResources().getString(R.string.error_other)
	// + rCode);
	// }
	// }

}
