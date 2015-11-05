package bill.choosecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bill.choosecity.R;
import bill.choosecity.bean.RegionBean;


/**
 * 城市列表
 */
public class SortCityAdapter extends BaseAdapter {
    private List<RegionBean> list = null;
    private Context context;

    public SortCityAdapter(Context context, List<RegionBean> list) {
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
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_location_city, null);
            viewHolder.city_name = (TextView) view.findViewById(R.id.city_name);
            viewHolder.line = view.findViewById(R.id.line);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.city_name.setText(this.list.get(position).getName());
        if (position == list.size() - 1 || list.size() == 1) {
            viewHolder.line.setVisibility(View.GONE);
        } else {
            viewHolder.line.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private static class ViewHolder {
        TextView city_name;
        View line;
    }
}