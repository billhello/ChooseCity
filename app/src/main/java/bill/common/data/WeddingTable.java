package bill.common.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bill.choosecity.bean.ListBean;
import bill.choosecity.bean.RegionBean;

/**
 * 对婚礼表的操作
 *
 * @author Administrator
 */
public class WeddingTable {
    /**
     * 获得省份列表(parent_id=0)
     *
     * @param type
     * @return
     */
    public static List<ListBean> getProvincesList(SQLiteDatabase db,
                                                  String tableName, String type) {
        List<ListBean> list = new ArrayList<ListBean>();
        ListBean regionBean = null;
        Cursor cursor = null;
        try {
            // 检索所有省市区域列表
            cursor = db.query(tableName, new String[]{"name", "type",
                            "cateid"}, "type = ? ", new String[]{type}, null, null,
                    null);
            while (!cursor.isLast()) {
                cursor.moveToNext();
                regionBean = new RegionBean(cursor.getString(2), null,
                        cursor.getString(0), cursor.getString(0), type, null);
                list.add(regionBean);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return list;
    }

    /**
     * @param db
     * @param tableName
     * @param name
     * @return
     */
    public static ListBean getProvince(SQLiteDatabase db, String tableName,
                                       String name) {
        ListBean regionBean = null;
        Cursor cursor = null;
        try {
            // 检索所有省市区域列表
            cursor = db.query(tableName, new String[]{"name", "type",
                            "cateid"}, "name = ? ", new String[]{name}, null, null,
                    null);
            while (!cursor.isLast()) {
                cursor.moveToNext();
                regionBean = new RegionBean(cursor.getString(2), null,
                        cursor.getString(0), "定位城市:" + cursor.getString(0),
                        name, null);
                regionBean.setLocation(true);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return regionBean;
    }


    /**
     * 某个分类下的数目
     *
     * @param db
     * @param tableName
     * @param type
     * @param cateid
     * @return
     */
    public static String getWeddingCount(SQLiteDatabase db, String tableName, String type, String cateid) {
        Cursor cursor = null;
        try {
            // db = databaseHelper.getReadableDatabase();
            cursor = db.query(tableName, new String[]{"id", "name", "count",
                            "type", "cateid"}, "type = ?" + " AND cateid= ? ",
                    new String[]{type, cateid}, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    while (!cursor.isLast()) {
                        cursor.moveToNext();
                        return cursor.getString(2);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }



}
