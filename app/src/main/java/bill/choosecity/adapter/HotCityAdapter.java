package bill.choosecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bill.choosecity.R;
import bill.choosecity.bean.HotCityData;

/**
 * Created by Administrator on 2015/6/5.
 * 热门城市
 */
public class HotCityAdapter extends BaseAdapter {
    private List<HotCityData> list = null;
    private Context context;

    public HotCityAdapter(Context context, List<HotCityData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null)
            return 0;
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list == null)
            return 0;
        return list.get(position);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_hot_city, null);
            viewHolder.hot_city = (TextView) view.findViewById(R.id.hot_city);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.hot_city.setText(this.list.get(position).regionName);
        return view;
    }

    private static class ViewHolder {
        TextView hot_city;
    }
}
