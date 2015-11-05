package bill.choosecity.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import bill.choosecity.R;
import bill.choosecity.adapter.HotCityAdapter;
import bill.choosecity.bean.HotCityData;
import bill.choosecity.data.AddressManager;
import bill.choosecity.listener.OnSelectLocationListener;
import bill.choosecity.utils.FormatLocation;
import bill.common.view.NestGridView;


/**
 * Created by Administrator on 2015/6/5.
 * 定位城市和热门城市
 */
public class HotCityView extends View {
    private View layout;

    public HotCityView(Context context) {
        super(context);
        layout = LayoutInflater.from(context).inflate(
                R.layout.layout_location_hot_city, null);
    }


    /**
     * 显示定位城市和热门城市
     *
     * @param context
     * @param hotCityDatas
     * @return
     */
    public View getHotCityView(Context context, final List<HotCityData> hotCityDatas, final OnSelectLocationListener onSelectLocationListener) {
        /*显示全国*/
        showAllCountry(onSelectLocationListener);
        /*显示定位城市*/
        showLocationCity(onSelectLocationListener);
        HotCityAdapter hotCityAdapter = new HotCityAdapter(context, hotCityDatas);
        NestGridView gridView = (NestGridView) layout.findViewById(R.id.grid_hot_city);
        gridView.setAdapter(hotCityAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HotCityData hotCityData = hotCityDatas.get(position);
              /*  AddressManager.saveAddress(getContext(), AddressManager.CHOOSE_CITY_NAME, hotCityData.regionName);
                AddressManager.saveAddress(getContext(), AddressManager.CHOOSE_CITY_ID, hotCityData.regionId);*/
            /*    Message msg = Message.obtain();
                msg.what = 3;
                Bundle bundle = new Bundle();
                bundle.putString(LocationSelectActivity.REGION_CITY_NAME, hotCityData.regionName);
                bundle.putString(LocationSelectActivity.REGION_CITY_ID, hotCityData.regionId);
                msg.setData(bundle);
                mHandler.sendMessage(msg);*/
                onSelectLocationListener.onSelectCity(hotCityData.regionName, hotCityData.regionId);
            }
        });
        return layout;
    }

    private void showAllCountry(final OnSelectLocationListener onSelectLocationListener) {
        layout.findViewById(R.id.btn_all_location).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*将全国设为选择位置*/
          /*      AddressManager.saveAddress(getContext(), AddressManager.CHOOSE_CITY_NAME, "全国");
                AddressManager.saveAddress(getContext(), AddressManager.CHOOSE_CITY_ID, "0");*/
                /*Message msg = Message.obtain();
                msg.what = 3;
                Bundle bundle = new Bundle();
                bundle.putString(LocationSelectActivity.REGION_CITY_NAME, "全国");
                bundle.putString(LocationSelectActivity.REGION_CITY_ID, "0");
                msg.setData(bundle);
                mHandler.sendMessage(msg);*/

                onSelectLocationListener.onSelectCity("全国", "0");
            }
        });
    }

    /**
     * 显示定位城市
     */
    private void showLocationCity(final OnSelectLocationListener onSelectLocationListener) {
        String currentCityName = AddressManager.getAddress(getContext(), AddressManager.CITY_NAME);

        if (!TextUtils.isEmpty(currentCityName)) {
         /*  if (currentLocation.contains("市"))
                currentLocation = currentLocation.substring(0, currentLocation.lastIndexOf('市'));*/
            currentCityName = FormatLocation.getFormatByCity(currentCityName);
            ((TextView) layout.findViewById(R.id.btn_current_location)).setText(currentCityName);
            layout.findViewById(R.id.btn_current_location).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*将定位位置设为选择位置*/
                   /* AddressManager.saveAddress(getContext(), AddressManager.CHOOSE_CITY_NAME, AddressManager.getAddress(getContext(), AddressManager.CITY_NAME));
                    AddressManager.saveAddress(getContext(), AddressManager.CHOOSE_CITY_ID, AddressManager.getAddress(getContext(), AddressManager.CITY_ID));*/
                  /*  Message msg = Message.obtain();
                    msg.what = 3;
                    Bundle bundle = new Bundle();
                    bundle.putString(LocationSelectActivity.REGION_CITY_NAME, AddressManager.getAddress(getContext(), AddressManager.CITY_NAME));
                    bundle.putString(LocationSelectActivity.REGION_CITY_ID, AddressManager.getAddress(getContext(), AddressManager.CITY_ID));
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);*/

                    onSelectLocationListener.onSelectCity(AddressManager.getAddress(getContext(), AddressManager.CITY_NAME), AddressManager.getAddress(getContext(), AddressManager.CITY_ID));
                }
            });
        } else {
            ((TextView) layout.findViewById(R.id.btn_current_location)).setText("定位失败");
        }
    }

    /**
     * 定位成功，刷新定位
     */
    public void refreshLocationCity(OnSelectLocationListener onSelectLocationListener) {
        showLocationCity(onSelectLocationListener);
    }
}
