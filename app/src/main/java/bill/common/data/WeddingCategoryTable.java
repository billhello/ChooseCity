package bill.common.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.halobear.awedqq.home.ui.common.bean.WeddingClassifyBean;

/**
 * 对婚礼类别表的操作
 *
 * @author Administrator
 */
public class WeddingCategoryTable {
    /**
     * 获得分类的名字以及下面子项
     *
     * @param db
     * @param tableName
     * @param otherName
     * @param type
     * @return
     */
    public static WeddingClassifyBean getWeddingClassifytBean(
            SQLiteDatabase db, String tableName, String otherName, int type) {
        WeddingClassifyBean weddingClassifyBeans = null;
        // SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            // db = db.getReadableDatabase();
            cursor = db.query(tableName,
                    new String[]{"category_name", "type"}, "type = ? ",
                    new String[]{Integer.toString(type)}, null, null, null);
            if (!cursor.isLast()) {
                cursor.moveToNext();
                weddingClassifyBeans = new WeddingClassifyBean(
                        cursor.getString(0),
                        WeddingTable.getListWeddingItemBeanByType(db, otherName,
                                type));
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
        return weddingClassifyBeans;
    }

    public static WeddingClassifyBean getWeddingClassifytBean(
            SQLiteDatabase db, String tableName, String otherName, String info1) {
        WeddingClassifyBean weddingClassifyBeans = null;
        // SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            // db = db.getReadableDatabase();
            cursor = db.query(tableName,
                    new String[]{"category_name", "info1"}, "info1 = ? ",
                    new String[]{info1}, null, null, null);
            if (!cursor.isLast()) {
                cursor.moveToNext();
                weddingClassifyBeans = new WeddingClassifyBean(
                        cursor.getString(0),
                        WeddingTable.getListWeddingItemBean(db, otherName,
                                info1));
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
        return weddingClassifyBeans;
    }

    /**
     * 向weddingcategory表中插入数据
     *
     * @param category_name
     * @param type
     * @param type_name
     * @param info1
     * @param info2
     */
    public static void insertWeddingCategory(SQLiteDatabase db, String table_name, String category_name,
                                             String type, String type_name, String info1, String info2) {
        try {
            ContentValues values = new ContentValues();
            values.put("category_name", category_name);
            values.put("type", type);
            values.put("type_name", type_name);
            values.put("info1", info1);
            values.put("info2", info2);
            // 向数据表中插入数据
            db.insert(table_name, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
