package bill.common.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.halobear.awedqq.home.ui.common.bean.CateBean;
import com.halobear.awedqq.home.ui.common.bean.WeddingClassifyBean;
import com.halobear.awedqq.home.ui.common.bean.WeddingItemBean;
import com.halobear.bwedqq.prepare.ui.bean.LuckDayBean;
import com.halobear.bwedqq.prepare.ui.bean.ScheduleBean;
import com.halobear.wedqq.data.databases.sqltable.LuckyDayTable;
import com.halobear.wedqq.data.databases.sqltable.SchedulePlanTable;

import java.util.List;

import bill.choosecity.bean.ListBean;
import bill.choosecity.bean.RegionBean;

/**
 * 首页婚礼列表数据库
 *
 * @author Administrator
 */
public class WeddingHelper extends SQLiteOpenHelper {

    private static WeddingHelper databaseHelper;
    // 数据库版本
    public static final int VERSION = 3;
    // 首页3个列表的本地数据
    public static final String DBNAME = "Wedding";
    // 黄道吉日
    public static final String TABLELUCKYDAY = "luckday";
    // 待办事项
    public static final String TABLESCHEDULE = "schedule";
    // 地区
    public static final String TABLEREGION = "region";
    // 首页
    public static final String TABLEWEDDING = "wedding";
    // 首页分类
    public static final String TABLEWEDDINGCATEGORY = "weddingcategory";

    /**
     * 获得调试单例
     *
     * @return
     */
    public static synchronized WeddingHelper getInstance(Context context) {
        if (databaseHelper == null) {
            synchronized (WeddingHelper.class) {
                if (databaseHelper == null) {
                    databaseHelper = new WeddingHelper(context.getApplicationContext(), DBNAME);
                }
            }
        }
        return databaseHelper;
    }

    private WeddingHelper(Context context, String dataname) {
        super(context, dataname, null, VERSION);
        DatabaseUtils.importInitDatabase(context, dataname);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1)
            return;
        switch (oldVersion) {
            case 1:
                WeddingCategoryTable.insertWeddingCategory(db, TABLEWEDDINGCATEGORY, "精选视频", "6", "video", "无", "无");
                WeddingTable.insertWedding(db, TABLEWEDDING, "12", "婚礼周边", "0", "btn_dis_ser_circum", "2", "12", "无", "无");
                break;
            case 2:
                WeddingTable.insertWedding(db, TABLEWEDDING, "12", "婚礼周边", "0", "btn_dis_ser_circum", "2", "12", "无", "无");
                break;
            default:
                break;
        }
    }


    /**
     * 返回类型中id对应的名称
     *
     * @param type   team or company
     * @param cateid
     * @return
     */
    public WeddingItemBean getWeddingItemName(String type, String cateid) {
        return WeddingTable.getWeddingItemName(
                databaseHelper.getReadableDatabase(), TABLEWEDDING, type, cateid);
    }

    /**
     * 某个分类下的数目
     *
     * @param type
     * @param id
     * @return
     */
    public String getWeddingCount(String type, String id) {
        return WeddingTable.getWeddingCount(
                databaseHelper.getReadableDatabase(), TABLEWEDDING, type, id);
    }

    /**
     * 返回分类里面单个
     *
     * @param index
     * @return
     */
/*    public List<WeddingItemBean> getListWeddingItemBean(int index) {
        return WeddingTable.getListWeddingItemBean(
                databaseHelper.getReadableDatabase(), TABLEWEDDING, index);
    }*/

    /**
     * 获得城市列表
     *
     * @param provinceName
     * @return
     */
    public ListBean getProvinces(String provinceName) {
        return WeddingTable.getProvince(databaseHelper.getReadableDatabase(),
                TABLEWEDDING, provinceName);
    }

    /**
     * 返回省份列表
     *
     * @param type 类型
     * @return
     */
    public List<ListBean> getProvincesList(String type) {
        return WeddingTable.getProvincesList(
                databaseHelper.getReadableDatabase(), TABLEWEDDING, type);
    }

    /**
     * 将获得的精选婚礼数据写入数据库
     *
     * @param type
     * @param content
     */
    public void writeWeddingCateToDb(List<CateBean.CateData> content, int type) {
        WeddingTable.writeWeddingCateToDb(databaseHelper.getWritableDatabase(),
                TABLEWEDDING, content, type);
    }

    /**
     * 根据指定日期得到黄道节日信息
     *
     * @param day
     * @return
     */
    public LuckDayBean getLuckDayBean(String day) {
        return LuckyDayTable.getLuckDayBean(
                databaseHelper.getReadableDatabase(), TABLELUCKYDAY, day);
    }

    /**
     * 获取所有的日程
     *
     * @return
     */
    public List<ScheduleBean> getScheduleBeans() {
        return SchedulePlanTable.getScheduleBean(
                databaseHelper.getReadableDatabase(), TABLESCHEDULE);
    }

    /**
     * 返回首页的分类
     *
     * @param type
     * @return
     */
    public WeddingClassifyBean getWeddingClassifytBean(int type) {
        return WeddingCategoryTable.getWeddingClassifytBean(
                databaseHelper.getReadableDatabase(), TABLEWEDDINGCATEGORY,
                TABLEWEDDING, type);
    }

    public WeddingClassifyBean getWeddingClassifytBean(String info1) {
        return WeddingCategoryTable.getWeddingClassifytBean(
                databaseHelper.getReadableDatabase(), TABLEWEDDINGCATEGORY,
                TABLEWEDDING, info1);
    }

    /**
     * 获得省份列表
     *
     * @param parentId
     * @return
     */
    public List<ListBean> getListRegionProvinces(String parentId) {
        return WeddingRegionTable.getListRegionProvinces(
                databaseHelper.getReadableDatabase(), TABLEREGION, parentId);
    }

    /**
     * 根据省级id获得城市列表
     *
     * @param parentId
     * @return
     */
    public List<ListBean> getListRegionCitys(String parentId) {
        return WeddingRegionTable
                .getListRegionCitys(databaseHelper.getReadableDatabase(),
                        TABLEREGION, parentId);
    }

    /**
     * 获得城市父级parent_id
     *
     * @param regionId
     * @return
     */
    public ListBean getRegionParentId(String regionId) {
        return WeddingRegionTable
                .getRegionParentId(databaseHelper.getReadableDatabase(),
                        TABLEREGION, regionId);
    }

    /**
     * 返回城市及下属区
     *
     * @param parentName
     * @param parentId
     * @return
     */
    public List<ListBean> getListRegionAndCity(String parentName,
                                               String parentId) {
        return WeddingRegionTable.getListRegionAndCity(
                databaseHelper.getReadableDatabase(), TABLEREGION, parentName,
                parentId);
    }

    /**
     * 将获得的精选婚礼数据更新写入数据库
     *
     * @param type
     * @param content
     */
    public void updateWeddingCateToDb(List<CateBean.CateData> content, int type) {
        WeddingTable.updateWeddingCateToDb(
                databaseHelper.getWritableDatabase(), content, type);
    }

    /**
     * 根据regionId 返回地区列表
     *
     * @param regionId
     * @return
     */
    public List<ListBean> getRegionList(String regionId) {
        return WeddingRegionTable.getRegionListByProvince(
                databaseHelper.getReadableDatabase(), TABLEREGION, regionId);
    }

    /**
     * 根据level 返回地区列表
     *
     * @param level
     * @return
     */
    public List<RegionBean> getRegionListByLevel(String level) {
        return WeddingRegionTable.getRegionListByLevel(
                databaseHelper.getReadableDatabase(), TABLEREGION, level);
    }

    /**
     * 根据区县获得省市
     *
     * @param region_code
     * @return
     */
    public ListBean getRegionParentLocation(String region_code) {
        return WeddingRegionTable.getRegionParentLocation(
                databaseHelper.getReadableDatabase(), TABLEREGION, region_code);
    }

    /**
     * 根据code返回id(截取数据中省市区)
     *
     * @param region_code
     * @return
     */
    public String getRegionIdByCode(String region_code) {
        return WeddingRegionTable.getRegionIdByCode(
                databaseHelper.getReadableDatabase(), TABLEREGION, region_code);
    }


    /**
     * 判断后台广告轮播和精选婚礼中的标志是否改变,如果没有改变则不更新;弱改变则更新数据库
     */
    public void updateAdAndChoiceDb() {
        // 从服务器请求标志

    }

    /**
     * 将获得的广告数据写入指定数据表中
     */
    public void writeListAdToDb() {
    }


}
