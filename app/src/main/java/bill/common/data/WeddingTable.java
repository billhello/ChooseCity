package bill.common.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.halobear.awedqq.home.ui.common.bean.CateBean;
import com.halobear.awedqq.home.ui.common.bean.ListBean;
import com.halobear.awedqq.home.ui.common.bean.RegionBean;
import com.halobear.awedqq.home.ui.common.bean.WeddingItemBean;

import java.util.ArrayList;
import java.util.List;

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
     * @param db
     * @param tableName
     * @param type
     * @param cateId
     * @return
     */
    public static WeddingItemBean getWeddingItemName(SQLiteDatabase db, String tableName, String type, String cateId) {
        Cursor cursor = null;
        try {
            // db = databaseHelper.getReadableDatabase();
            cursor = db.query(tableName, new String[]{"name",
                            "type", "cateid"}, "type = ?" + " AND cateid= ? ",
                    new String[]{type, cateId}, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    while (!cursor.isLast()) {
                        cursor.moveToNext();
                        WeddingItemBean weddingItemBean = new WeddingItemBean("",
                                cursor.getString(0), "", "",
                                cursor.getString(2), cursor.getInt(1));
                        return weddingItemBean;
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

    /**
     * 获得指定类别的数据列表
     *
     * @param db
     * @param type 列表
     * @return
     */
    public static List<WeddingItemBean> getListWeddingItemBeanByType(
            SQLiteDatabase db, String tableName, int type) {
        List<WeddingItemBean> list = new ArrayList<>();
        WeddingItemBean resourceBean = null;
        // SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            // db = databaseHelper.getReadableDatabase();
            cursor = db.query(tableName, new String[]{"name", "count",
                            "picname", "type", "cateid"}, "type = ? ",
                    new String[]{Integer.toString(type)}, null, null, null);
            while (!cursor.isLast()) {
                cursor.moveToNext();
                resourceBean = new WeddingItemBean(cursor.getString(2),
                        cursor.getString(0), cursor.getString(1), "",
                        cursor.getString(4), type);
                list.add(resourceBean);
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

    public static List<WeddingItemBean> getListWeddingItemBean(
            SQLiteDatabase db, String tableName, String info1) {
        List<WeddingItemBean> list = new ArrayList<>();
        WeddingItemBean resourceBean = null;
        // SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            // db = databaseHelper.getReadableDatabase();
            cursor = db.query(tableName, new String[]{"name", "count",
                            "picname", "type", "cateid", "info1"}, "info1 = ? ",
                    new String[]{info1}, null, null, null);
            while (!cursor.isLast()) {
                cursor.moveToNext();
                resourceBean = new WeddingItemBean(cursor.getString(2),
                        cursor.getString(0), cursor.getString(1), "",
                        cursor.getString(4), cursor.getInt(3));
                list.add(resourceBean);
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
     * 将获取的精选婚礼数据写入指定数据表中
     *
     * @param content
     */
    public static void writeWeddingCateToDb(SQLiteDatabase db,
                                            String tableName, List<CateBean.CateData> content, int type) {
        // SQLiteDatabase db = null;
        try {
            // 从数据中删除type=6的数据
            // db = databaseHelper.getWritableDatabase();
            db.delete(tableName, "type= ?",
                    new String[]{Integer.toString(type)});
            // "name", "info1",
            // "info2", "picname", "type"
            // 在数据中增加type=6的数据
            for (int i = 0; i < content.size(); i++) {
                // content.get(i).;
                ContentValues values = new ContentValues();
                values.put("id", i);
                values.put("name", content.get(i).cate_name);
                values.put("count", content.get(i).count);
                values.put("picname", content.get(i).cate_logo);
                values.put("type", Integer.toString(type));
                values.put("cateid", content.get(i).cate_id);
                values.put("info1", "无");
                values.put("info2", "无");
                db.insert("wedding", null, values);// 返回新添记录的行号，与主键id无关
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * @param db
     * @param content
     * @param type
     */
    public static void updateWeddingCateToDb(SQLiteDatabase db,
                                             List<CateBean.CateData> content, int type) {
        // 恢复默认
        try {
            ContentValues prevalues = new ContentValues();
            prevalues.put("count", 0);
            db.update("wedding", prevalues, "type=?",
                    new String[]{String.valueOf(type)});

            for (int i = 0; i < content.size(); i++) {
                ContentValues values = new ContentValues();
                // values.put("name", content.get(i).cate_name);
                values.put("count", content.get(i).count);
                // values.put("count", "无");
                // values.put("picname", content.get(i).cate_logo);
                // values.put("type", Integer.toString(index));
                // db.update("wedding", null, values);// 返回新添记录的行号，与主键id无关
                db.update("wedding", values, "type=? AND cateid=?",
                        new String[]{String.valueOf(type),
                                content.get(i).cate_id});
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    /**
     * 向wedding表中插入数据
     *
     * @param db
     * @param table_name
     * @param id
     * @param name
     * @param count
     * @param picname
     * @param type
     * @param cateid
     * @param info1
     * @param info2
     */
    public static void insertWedding(SQLiteDatabase db, String table_name, String id,
                                     String name, String count, String picname, String type, String cateid, String info1, String info2) {
        try {
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("name", name);
            values.put("count", count);
            values.put("picname", picname);
            values.put("type", type);
            values.put("cateid", cateid);
            values.put("info1", info1);
            values.put("info2", info2);
            // 向数据表中插入数据
            db.insert(table_name, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
