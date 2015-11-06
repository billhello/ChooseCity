package bill.choosecity.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps2d.model.LatLng;

import bill.choosecity.R;
import bill.common.base.BaseActivity;
import bill.common.utils.ToastUtil;

/**
 * 显示地理位置的界面
 * 
 * @author Administrator
 * 
 */
public class LocationMapActivity extends BaseActivity {

	// private MapView mapView;
	// private AMap aMap;
	// private MarkerOptions markerOptions;
	// private UiSettings mUiSettings; // 设置ui样式

	// private Marker regeoMarker;
	// private GeocodeSearch geocoderSearch;
	// private LatLonPoint latLonPoint;
	// private LatLng latLng;
	// 经度
	public final static String LOCATION_LNG = "location_lng";
	// 纬度
	public final static String LOCATION_LAT = "location_lat";
	// 标题
	public final static String LOCATION_TITLE = "location_title";
	// 位置
	public final static String LOCATION_ADDRESS = "location_address";

	// 经度
	private double lng;
	// 纬度
	private double lat;
	// 标题
	private String title;
	// 位置
	private String address;

	// private LocationSource.OnLocationChangedListener
	// onLocationChangedListener;
	// private LocationManagerProxy locationManagerProxy;
	private LocationMapView locationMapView;

	private TextView maptitle;

	@Override
	public void setView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_location_map);
		// mapView = (MapView) findViewById(R.id.map);
		// mapView.onCreate(savedInstanceState);// 此方法必须重写
		locationMapView = (LocationMapView) findViewById(R.id.location_mapview);
		locationMapView.onCreate(savedInstanceState);
	}

	/**
	 * 初始化AMap对象
	 */
	// private void init() {
	// if (aMap == null) {
	// aMap = mapView.getMap();
	//
	// aMap.setOnMapLoadedListener(this);
	// aMap.moveCamera(CameraUpdateFactory.zoomBy(9.0F));
	// mUiSettings = aMap.getUiSettings();
	// mUiSettings.setCompassEnabled(true);
	// mUiSettings.setZoomControlsEnabled(false); // 设置地图默认的缩放按钮不显示
	// // mUiSettings.setScrollGesturesEnabled(true); // 设置地图是不可以手势滑动
	// mUiSettings.setZoomGesturesEnabled(true); // 设置地图是不可以手势缩放大小
	// // mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);//
	// // 设置地图logo显示在右下方
	// // initAMap();
	// }
	// // ArrayList<BitmapDescriptor> localArrayList = new
	// // ArrayList<BitmapDescriptor>();
	// // localArrayList.add(BitmapDescriptorFactory.defaultMarker(240.0F));
	// // localArrayList.add(BitmapDescriptorFactory.defaultMarker(0.0F));
	// // localArrayList.add(BitmapDescriptorFactory.defaultMarker(60.0F));
	//
	// // aMap.moveCamera(CameraUpdateFactory
	// // .newCameraPosition(new CameraPosition(latLng, 18.0F, 0.0F,
	// // 30.0F)));
	//
	// }

	@Override
	public void initView() {
		findViewById(R.id.top_bar_back).setOnClickListener(this);

		findViewById(R.id.location_navigation).setOnClickListener(this);

		maptitle = (TextView) findViewById(R.id.top_bar_center_title);
	}

	@Override
	public void initData() {

		lng = getIntent().getDoubleExtra(LOCATION_LNG, 0);
		lat = getIntent().getDoubleExtra(LOCATION_LAT, 0);
		title = getIntent().getStringExtra(LOCATION_TITLE);
		address = getIntent().getStringExtra(LOCATION_ADDRESS);
		if (!TextUtils.isEmpty(title))
			maptitle.setText(title);

		locationMapView.loadingMap(new LatLng(lat, lng));
		// latLonPoint = new LatLonPoint(Double.parseDouble(lngLatBean.lat),
		// Double.parseDouble(lngLatBean.lng));
		// getAddress(latLonPoint);
		// init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_bar_back:
			finish();
			break;
		case R.id.location_navigation:
			try {
				Intent localIntent = new Intent("android.intent.action.VIEW",
						Uri.parse("geo:" + lng + "," + lat + "?q=" + address));
				startActivity(localIntent);

			} catch (ActivityNotFoundException exception) {
				ToastUtil.show(this, "你没有安装任何地图应用");
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			// 是否存在一个包名
			break;
		default:
			break;
		}
	}

	// private void initAMap() {
	// MyLocationStyle localMyLocationStyle = new MyLocationStyle();
	// localMyLocationStyle.strokeColor(-16777216);
	// localMyLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));
	// localMyLocationStyle.strokeWidth(0.1F);
	// this.aMap.setMyLocationStyle(localMyLocationStyle);
	// // this.aMap.setMyLocationRotateAngle(180.0F);
	// this.aMap.setLocationSource(this);
	// this.aMap.getUiSettings().setMyLocationButtonEnabled(true);
	// this.aMap.setMyLocationEnabled(true);
	// }
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		locationMapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		locationMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		locationMapView.release();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		locationMapView.onSaveInstanceState(outState);
	}

	/**
	 * 响应逆地理编码
	 */
	// public void getAddress(LatLonPoint latLonPoint) {
	// RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
	// GeocodeSearch.AMAP);//
	// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
	// geocoderSearch = new GeocodeSearch(this);
	// geocoderSearch.setOnGeocodeSearchListener(this);
	//
	// geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
	// }

	// @Override
	// public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
	//
	// }

	// 根据坐标定位
	// @Override
	// public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
	// if (rCode == 0) {
	// if (result != null && result.getRegeocodeAddress() != null
	// && result.getRegeocodeAddress().getFormatAddress() != null) {
	// aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
	// LocationMapHelper.getInstance(this).convertToLatLng(
	// latLonPoint), 15));
	// regeoMarker.setPosition(LocationMapHelper.getInstance(this)
	// .convertToLatLng(latLonPoint));
	// }
	// } else if (rCode == 27) {
	// ToastUtil.show(LoactionAct.this, R.string.error_network);
	// } else if (rCode == 32) {
	// ToastUtil.show(LoactionAct.this, R.string.error_key);
	// } else {
	// ToastUtil.show(LoactionAct.this, getString(R.string.error_other)
	// + rCode);
	// }
	// }

	// /**
	// * 方法必须重写
	// */
	// @Override
	// protected void onResume() {
	// super.onResume();
	// mapView.onResume();
	// }
	//
	// /**
	// * 方法必须重写
	// */
	// @Override
	// protected void onPause() {
	// super.onPause();
	// mapView.onPause();
	// }

	// /**
	// * 方法必须重写
	// */
	// @Override
	// protected void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// mapView.onSaveInstanceState(outState);
	// }

	/**
	 * 方法必须重写
	 */
	// @Override
	// protected void onDestroy() {
	// super.onDestroy();
	// mapView.onDestroy();
	// }

	// @Override
	// public void onMapLoaded() {
	// // new LatLngBounds.Builder().include(latLng).build();
	// markerOptions = new MarkerOptions()
	// .position(latLng)
	// .icon(BitmapDescriptorFactory
	// .defaultMarker(BitmapDescriptorFactory.HUE_RED))
	// .draggable(true);
	// aMap.addMarker(markerOptions);
	// this.aMap.moveCamera(CameraUpdateFactory
	// .newCameraPosition(new CameraPosition(latLng, 18.0F, 0.0F,
	// 30.0F)));
	// }

	// @Override
	// public void onLocationChanged(Location location) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onProviderDisabled(String provider) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onProviderEnabled(String provider) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onStatusChanged(String provider, int status, Bundle extras) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onRegeocodeSearched(RegeocodeResult arg0, int arg1) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onLocationChanged(AMapLocation paramAMapLocation) {
	// if ((this.onLocationChangedListener == null)
	// || (paramAMapLocation == null))
	// return;
	// this.onLocationChangedListener.onLocationChanged(paramAMapLocation);
	// float f1 = this.aMap.getCameraPosition().bearing;
	// // this.aMap.setMyLocationRotateAngle(f1);
	// }
	//
	// @Override
	// public void activate(
	// OnLocationChangedListener paramOnLocationChangedListener) {
	// this.onLocationChangedListener = paramOnLocationChangedListener;
	// if (this.locationManagerProxy != null)
	// return;
	// this.locationManagerProxy = LocationManagerProxy.getInstance(this);
	// this.locationManagerProxy
	// .requestLocationData("lbs", 2000L, 10.0F, this);
	// }
	//
	// @Override
	// public void deactivate() {
	// this.onLocationChangedListener = null;
	// if (this.locationManagerProxy != null) {
	// this.locationManagerProxy.removeUpdates(this);
	// this.locationManagerProxy.destory();
	// }
	// this.locationManagerProxy = null;
	// }

}
