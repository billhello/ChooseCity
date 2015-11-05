package bill.choosecity.activity;


import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.halobear.wedqq.amain.fragment.ChoiceFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bill.choosecity.R;
import bill.choosecity.adapter.SortCityAdapter;
import bill.choosecity.adapter.SortTitleAdapter;
import bill.choosecity.bean.HotCityData;
import bill.choosecity.bean.RegionBean;
import bill.choosecity.bean.SortCityModel;
import bill.choosecity.data.AddressManager;
import bill.choosecity.listener.OnLocationListener;
import bill.choosecity.listener.OnSelectLocationListener;
import bill.choosecity.utils.CharacterParser;
import bill.choosecity.utils.PinyinComparator;
import bill.choosecity.view.HotCityView;
import bill.choosecity.view.SideBar;
import bill.common.base.BaseActivity;
import bill.common.data.WeddingHelper;
import bill.common.utils.LoginConsts;


/**
 * 城市列表选择
 */
public class LocationSelectActivity extends BaseActivity implements OnSelectLocationListener, LoaderManager.LoaderCallbacks<Cursor> {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private static SortTitleAdapter sortTitleAdapter;
    private EditText mClearEditText;
    private SortCityAdapter sortCityHintAdapter;
    /**
     * 汉字转换成拼音的类
     */
    /*private CharacterParser characterParser;*/
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    /*private PinyinComparator pinyinComparator;*/
    private List<SortCityModel> SourceDateList = new ArrayList<>();
    private List<RegionBean> filterDateList = new ArrayList<>();
    private static HotCityView hotCityView;

    private ListView panel_location_city_hint;
    /**
     * 地区城市名
     */
    public final static String REGION_CITY_NAME = "region_city_name";
    /**
     * 地区城市id
     */
    public final static String REGION_CITY_ID = "region_city_id";
 /*   private MyHandler handler = new MyHandler(this);*/

    /*public static class MyHandler extends Handler {
        private WeakReference<LocationSelectActivity> mOuter;

        public MyHandler(LocationSelectActivity activity) {
            mOuter = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LocationSelectActivity outer = mOuter.get();
            if (outer != null)
                switch (msg.what) {
                    case 2:
                        sortTitleAdapter.notifyDataSetChanged();
                        break;
                   *//* case 3:
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            AddressManager.saveAddress(outer, AddressManager.CHOOSE_CITY_NAME, bundle.getString(REGION_CITY_NAME));
                            AddressManager.saveAddress(outer, AddressManager.CHOOSE_CITY_ID, bundle.getString(REGION_CITY_ID));
                        }
                        releaseFinish(outer);
                        break;*//*
                    default:
                        break;
                }
        }
    }*/

    /**
     * 释放activity
     *
     * @param activity
     */
   /* private static void releaseFinish(Activity activity) {
        activity.setResult(LoginConsts.CHOOSE_RESULT_CITY);
        activity.finish();
    }*/

    /**
     * 点击精选地址跳转
     *
     * @param fragment
     * @param requestCode
     */
    public static void startActivityForResult(ChoiceFragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LocationSelectActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 点击精选地址跳转
     *
     * @param activity
     * @param requestCode
     */
    public static void startActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, LocationSelectActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_location_select);
    }

    @Override
    public void initView() {
        initListView();
        findViewById(R.id.top_bar_back).setOnClickListener(this);

        final FrameLayout panel_location_city = (FrameLayout) findViewById(R.id.panel_location_city);

        sideBar = (SideBar) findViewById(R.id.sidebar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                sortListView.requestFocusFromTouch();//获取焦点
                //该字母首次出现的位置
                int position = sortTitleAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position + 1);
                } else if (s.equals("#")) {
                    sortListView.setSelection(0);
                }
            }
        });
        mClearEditText = (EditText) findViewById(R.id.edit_query_city);
        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                if (TextUtils.isEmpty(s)) {
                    panel_location_city.setVisibility(View.VISIBLE);
                    panel_location_city_hint.setVisibility(View.GONE);
                } else {
                    if (panel_location_city_hint.getVisibility() == View.GONE) {
                        panel_location_city.setVisibility(View.GONE);
                        panel_location_city_hint.setVisibility(View.VISIBLE);
                    }
                    filterData(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    /**
     * 初始化listview
     */
    private void initListView() {
        /*所有城市列表*/
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
          /*      Toast.makeText(getApplication(), ((SortCityModel) adapter.getItem(position)).getCityRegion().getName(), Toast.LENGTH_SHORT).show();*/
            }
        });
        sortTitleAdapter = new SortTitleAdapter(this, SourceDateList, this);
        addListViewHeader();
        sortListView.setAdapter(sortTitleAdapter);

        /*符合条件城市名或拼音城市列表*/
        panel_location_city_hint = (ListView) findViewById(R.id.panel_location_city_hint);
        panel_location_city_hint.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 选中地址，然后finish
                 *//*
                AddressManager.saveAddress(LocationSelectActivity.this, AddressManager.CHOOSE_CITY_NAME, filterDateList.get(position).getName());
                AddressManager.saveAddress(LocationSelectActivity.this, AddressManager.CHOOSE_CITY_ID, filterDateList.get(position).getRegionId());*/
             /*   Message msg = Message.obtain();
                msg.what = 3;
                Bundle bundle = new Bundle();
                bundle.putString(REGION_CITY_NAME, filterDateList.get(position).getName());
                bundle.putString(REGION_CITY_ID, filterDateList.get(position).getRegionId());
                msg.setData(bundle);
                handler.sendMessage(msg);*/

                saveSelectCity(filterDateList.get(position).getName(), filterDateList.get(position).getRegionId());
            }
        });
        sortCityHintAdapter = new SortCityAdapter(this, filterDateList);
        panel_location_city_hint.setAdapter(sortCityHintAdapter);
    }

    /**
     * 保存选择的城市
     *
     * @param cityName 城市名
     * @param cityID   城市ID
     */
    private void saveSelectCity(String cityName, String cityID) {
        if (!TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(cityID)) {
            AddressManager.saveAddress(this, AddressManager.CHOOSE_CITY_NAME, cityName);
            AddressManager.saveAddress(this, AddressManager.CHOOSE_CITY_ID, cityID);
        }
        setResult(LoginConsts.CHOOSE_RESULT_CITY);
        finish();
    }

    @Override
    public void initData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SourceDateList.addAll(filledData(WeddingHelper.getInstance(LocationSelectActivity.this).getRegionListByLevel("2")));
                //实例化汉字转拼音类
                /*pinyinComparator = new PinyinComparator();*/
                // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, new PinyinComparator());
                sortTitleAdapter.notifyDataSetChanged();
            }
        });

        LocationHelper.getInstance(this)
                .requestLocationUpdates(new OnLocationListener() {
                    @Override
                    public void onLocationSuccess() {
                        hotCityView.refreshLocationCity(new OnSelectLocationListener() {
                            @Override
                            public void onSelectCity(String cityName, String cityID) {
                                saveSelectCity(cityName, cityID);
                            }
                        });
                    }

                    @Override
                    public void onLocationFailed() {

                    }
                });
    }

    /**
     * 添加头布局
     */
    private void addListViewHeader() {
        hotCityView = new HotCityView(this);
        List<HotCityData> hotCityDatas = new ArrayList<>();
        String[] hot_region_names = getResources().getStringArray(R.array.hot_city_region_name);
        String[] hot_region_ids = getResources().getStringArray(R.array.hot_city_region_id);
        for (int i = 0; i < hot_region_names.length; i++) {
            HotCityData hotCityData = new HotCityData();
            hotCityData.regionName = hot_region_names[i];
            hotCityData.regionId = hot_region_ids[i];
            hotCityDatas.add(hotCityData);
        }

        sortListView.addHeaderView(hotCityView.getHotCityView(this, hotCityDatas, this));
    }


    /**
     * 为ListView填充数据
     *
     * @param regionBeanList
     * @return
     */
    private List<SortCityModel> filledData(List<RegionBean> regionBeanList) {
        List<SortCityModel> sortCityModels = new ArrayList<>();

        int regionBeanLength = regionBeanList.size();
        for (int i = 0; i < regionBeanLength; i++) {
            SortCityModel sortCityModel = new SortCityModel();
            RegionBean regionBean = regionBeanList.get(i);

            //汉字转换成拼音
            String pinyin = CharacterParser.getInstance().getSelling(regionBean.getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                if (sortCityModels.size() == 0) {
                    sortCityModel.setCityRegionSortLetter(sortString);
                    List<RegionBean> regionBeans = new ArrayList<>();
                    regionBeans.add(regionBean);
                    sortCityModel.setCityRegions(regionBeans);
                    sortCityModels.add(sortCityModel);
                    continue;
                }
                int sortCityLength = sortCityModels.size();
                for (int j = 0; j < sortCityLength; j++) {
                    if (sortCityModels.get(j).getCityRegionSortLetter().equals(sortString)) {
                        List<RegionBean> regionBeans = sortCityModels.get(j).getCityRegions();
                        regionBeans.add(regionBean);
                        sortCityModels.get(j).setCityRegions(regionBeans);
                        break;
                    }
                    if (j == sortCityModels.size() - 1) {
                        sortCityModel.setCityRegionSortLetter(sortString);
                        List<RegionBean> regionBeans = new ArrayList<>();
                        regionBeans.add(regionBean);
                        sortCityModel.setCityRegions(regionBeans);
                        sortCityModels.add(sortCityModel);
                    }
                }
            }/* else {
                sortCityModel.setCityRegionSortLetter("#");
                List<RegionBean> regionBeans = new ArrayList<>();
                regionBeans.add(regionBean);
                sortCityModel.setCityRegions(regionBeans);
                sortCityModels.add(sortCityModel);
            }*/
        }

        return sortCityModels;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView(同一个listView 重新写一个)
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {

       /* if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {*/
        filterDateList.clear();
        for (SortCityModel sortCityModel : SourceDateList) {
            List<RegionBean> regionBeans = sortCityModel.getCityRegions();
            for (int i = 0; i < regionBeans.size(); i++) {
                String name = regionBeans.get(i).getName();
                if (name.indexOf(filterStr.toString()) != -1 || CharacterParser.getInstance().getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(regionBeans.get(i));
                }
            }
        /*    }*/
        }
        // 根据a-z进行排序
    /*    Collections.sort(filterDateList, pinyinComparator);*/
        sortCityHintAdapter.notifyDataSetChanged();
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     * <p/>
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     * <p/>
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hotCityView != null) {
            hotCityView = null;
        }
    }

    /**
     * 选择某个城市
     *
     * @param cityName
     * @param cityID
     */
    @Override
    public void onSelectCity(String cityName, String cityID) {
        saveSelectCity(cityName, cityID);
    }
}
