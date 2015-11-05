package bill.choosecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bill.choosecity.R;
import bill.choosecity.bean.RegionBean;
import bill.choosecity.bean.SortCityModel;
import bill.choosecity.listener.OnSelectLocationListener;
import bill.common.view.NestListView;


/**
 * 城市字母分类
 */
public class SortTitleAdapter extends BaseAdapter {
    private List<SortCityModel> list = new ArrayList<>();
    /*    private List<SortCityModel> cityList = new ArrayList<>();*/
    private Context context;
    private OnSelectLocationListener onSelectLocationListener;

    public SortTitleAdapter(Context context, List<SortCityModel> list, OnSelectLocationListener onSelectLocationListener) {
        this.context = context;
        this.list = list;
        this.onSelectLocationListener = onSelectLocationListener;
    }

    @Override
    public int getCount() {
        if (list == null)
            return 0;
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_location_title, null);
            viewHolder.city_letter = (TextView) view.findViewById(R.id.city_letter);
            viewHolder.city_list = (NestListView) view.findViewById(R.id.city_list);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //根据position获取分类的首字母的Char ascii值
   /*     int section = getSectionForPosition(position);
        cityTotal++;
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.city_letter.setVisibility(View.VISIBLE);
            viewHolder.city_letter.setText(mContent.getCityRegionSortLetter());
            if (cityList.size() < cityTotal) {
                sortCityAdapter.notifyDataSetChanged();
                cityTotal = 0;
                cityList.clear();
            }
        } else {
            viewHolder.city_letter.setVisibility(View.GONE);
            cityList.add(mContent);
        }*/

        viewHolder.city_letter.setText(list.get(position).getCityRegionSortLetter());

        viewHolder.city_list.setAdapter(new SortCityAdapter(context, list.get(position).getCityRegions()));
        viewHolder.city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int localposition, long id) {
                RegionBean regionBean = list.get(position).getCityRegions().get(localposition);
              /*  AddressManager.saveAddress(context, AddressManager.CHOOSE_CITY_NAME, regionBean.getName());
                AddressManager.saveAddress(context, AddressManager.CHOOSE_CITY_ID, regionBean.getRegionId());*/

               /* Message msg = Message.obtain();
                msg.what = 3;
                Bundle bundle = new Bundle();
                bundle.putString(LocationSelectActivity.REGION_CITY_NAME, regionBean.getName());
                bundle.putString(LocationSelectActivity.REGION_CITY_ID, regionBean.getRegionId());
                msg.setData(bundle);
                handler.sendMessage(msg);*/

                onSelectLocationListener.onSelectCity(regionBean.getName(), regionBean.getRegionId());
            }
        });
       /* SetViewHeight.setListViewHeight(viewHolder.city_list);*/
        return view;
    }

    private static class ViewHolder {
        TextView city_letter;
        NestListView city_list;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     *
     * @param section
     * @return
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getCityRegionSortLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
 /*   public int getSectionForPosition(int position) {
        return list.get(position).getCityRegionSortLetter().charAt(0);
    }



    *//**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     *//*
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }
*/

}