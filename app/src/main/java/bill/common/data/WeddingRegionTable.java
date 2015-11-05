package bill.common.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bill.choosecity.bean.ListBean;
import bill.choosecity.bean.RegionBean;
import bill.choosecity.utils.FormatLocation;

/**
 * 对省市地区表的操作
 *
 * @author Administrator
 */
public class WeddingRegionTable {

    /**
     * 查询出指定省区并返回该省区的信息
     *
     * @param db
     * @param tableName
     * @return
     */
    private static ListBean getRegionParent(SQLiteDatabase db,
                                            String tableName, String regionId) {
        ListBean regionBean = null;
        // SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            // db = databaseHelper.getReadableDatabase();
            cursor = db.query(tableName, new String[]{"region_id",
                            "region_name", "parent_id"},
                    "region_id = ? and parent_id= ? ", new String[]{regionId,
                            Integer.toString(0)}, null, null, null);
            if (cursor.getCount() > 0) {
                while (!cursor.isLast()) {
                    cursor.moveToNext();
                    // regionid = cursor.getString(0);
                    regionBean = new RegionBean(cursor.getString(0), null,
                            cursor.getString(1), cursor.getString(1),
                            cursor.getString(2), null);
                }
            } else {
                regionBean = new RegionBean(cursor.getString(0), null, "城区",
                        "全部城区", cursor.getString(2), null);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // if (db != null) {
            // db.close();
            // }
        }
        return regionBean;
    }

    /**
     * @param db
     * @param regionId
     * @return
     */
    public static ListBean getRegionParentLocation(SQLiteDatabase db,
                                                   String tableName, String region_code) {
        ListBean regionBean = null;
        // SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            // db = databaseHelper.getReadableDatabase();
            cursor = db.query(tableName, new String[]{"region_code",
                            "region_name", "parent_id"}, "region_code = ?  ",
                    new String[]{region_code}, null, null, null);
            if (cursor.getCount() > 0) {
                while (!cursor.isLast()) {
                    cursor.moveToNext();
                    // regionid = cursor.getString(0);
                    regionBean = new RegionBean(cursor.getString(0), null,
                            cursor.getString(1), cursor.getString(1),
                            cursor.getString(2), null);
                }
            } else {
                regionBean = new RegionBean(cursor.getString(0), null, "城区",
                        "全部城区", cursor.getString(2), null);
            }
        } catch (Exception e) {
            // regionBean = new RegionBean();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // if (db != null) {
            // db.close();
            // }
        }
        return regionBean;
    }

    /**
     * @param db
     * @param tableName
     * @param region_code
     * @return
     */
    public static String getRegionIdByCode(SQLiteDatabase db,
                                           String tableName, String region_code) {

        Cursor cursor = null;
        try {
            cursor = db.query(tableName, new String[]{"region_id", "region_code",
                            "parent_id"}, "region_code = ?   ",
                    new String[]{region_code}, null, null, null);
            if (cursor.getCount() > 0) {
                while (!cursor.isLast()) {
                    cursor.moveToNext();
                    return cursor.getString(0);
                }
            }
        } catch (Exception e) {
            // regionBean = new RegionBean();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "2";
    }

    /**
     * 获得省份列表(parent_id=0)
     *
     * @param parentId
     * @return
     */
    public static List<ListBean> getListRegionProvinces(SQLiteDatabase db,
                                                        String tableName, String parentId) {
        List<ListBean> list = new ArrayList<ListBean>();
        ListBean regionBean = null;
        Cursor cursor = null;
        try {
            // 检索所有省市区域列表
            cursor = db.query(tableName, new String[]{"region_id",
                            "region_name", "parent_id"}, "parent_id= ? ",
                    new String[]{parentId}, null, null, null);
            // 此处判断数目大于0,主要是为了香港特别行政区等
            if (cursor.getCount() > 0) {
                // 将全国的这个标志加入
                regionBean = getSpecialCountry("全部省份", "");
                list.add(regionBean);
                while (!cursor.isLast()) {
                    cursor.moveToNext();
                    regionBean = new RegionBean(cursor.getString(0), null,
                            cursor.getString(1), cursor.getString(1),
                            cursor.getString(2), null);
                    list.add(regionBean);
                }
            } else {
                // 将父级城市加入列表
                regionBean = getRegionParent(db, tableName, parentId);
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
     * 获得省份列表(parent_id=0)
     *
     * @param parentId
     * @return
     */
    public static List<ListBean> getProvincesList(SQLiteDatabase db,
                                                  String tableName, String parentId) {
        List<ListBean> list = new ArrayList<ListBean>();
        ListBean regionBean = null;
        Cursor cursor = null;
        try {
            // 检索所有省市区域列表
            cursor = db.query(tableName, new String[]{"region_id",
                            "region_name", "parent_id"}, "parent_id= ? ",
                    new String[]{parentId}, null, null, null);

            while (!cursor.isLast()) {
                cursor.moveToNext();
                regionBean = new RegionBean(cursor.getString(0), null,
                        cursor.getString(1), cursor.getString(1),
                        cursor.getString(2), null);
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
     * 获得城市列表
     *
     * @param db
     * @param parentId
     * @return
     */
    public static List<ListBean> getListRegionCitys(SQLiteDatabase db,
                                                    String tableName, String parentId) {
        List<ListBean> list = new ArrayList<ListBean>();
        ListBean regionBean = null;
        Cursor cursor = null;
        try {
            // 检索所有省市区域列表
            cursor = db.query(tableName, new String[]{"region_id", "region_code",
                            "region_name", "parent_id"}, "parent_id= ? ",
                    new String[]{parentId}, null, null, null);
            // 此处判断数目大于0,主要是为了香港特别行政区等
            if (cursor.getCount() > 0) {
                // 将全国的这个标志加入
                // regionBean = getSpecialCountry("全部城区", parentId);
                // list.add(regionBean);
                while (!cursor.isLast()) {
                    cursor.moveToNext();
                    regionBean = new RegionBean(cursor.getString(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(2),
                            cursor.getString(3), null);
                    list.add(regionBean);
                }
            }
            // else {
            // 将父级城市加入列表
            // regionBean = getRegionParent(db, parentId);
            // list.add(regionBean);
            // 将全国的这个标志加入
            // regionBean = getSpecialCountry("城区", parentId);
            // list.add(regionBean);
            // }
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
     * 获得城市父级parent_id
     *
     * @param db
     * @param regionId
     * @return
     */
    public static ListBean getRegionParentId(SQLiteDatabase db,
                                             String tableName, String regionId) {
        ListBean regionBean = null;
        Cursor cursor = null;
        try {
            // 检索所有省市区域列表
            cursor = db.query(tableName, new String[]{"region_id",
                            "region_name", "parent_id"}, "region_id= ? ",
                    new String[]{regionId}, null, null, null);
            while (!cursor.isLast()) {
                cursor.moveToNext();
                regionBean = new RegionBean(cursor.getString(0), null,
                        cursor.getString(1), cursor.getString(1),
                        cursor.getString(2), null);
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
     * 获得省市以及下属城市
     *
     * @param db
     * @param parentName 父名
     * @param parentId   父id
     * @return
     */
    public static List<ListBean> getListRegionAndCity(SQLiteDatabase db,
                                                      String tableName, String parentName, String parentId) {
        List<ListBean> list = new ArrayList<ListBean>();
        ListBean regionBean = null;
        Cursor cursor = null;
        try {
            // 检索所有省市区域列表
            cursor = db.query(tableName, new String[]{"region_id",
                            "region_name", "parent_id"}, "parent_id= ? ",
                    new String[]{parentId}, null, null, null);
            // 此处判断数目大于0,主要是为了香港特别行政区等
            if (cursor.getCount() > 0) {
                // 将全国的这个标志加入
                regionBean = getSpecialCountry(parentName, parentId);
                list.add(regionBean);
                while (!cursor.isLast()) {
                    cursor.moveToNext();
                    regionBean = new RegionBean(cursor.getString(0), null,
                            cursor.getString(1), cursor.getString(1),
                            cursor.getString(2), null);
                    list.add(regionBean);
                }
            } else {
                // 将父级城市加入列表
                // regionBean = getRegionParent(db, parentId);
                // list.add(regionBean);
                // 将全国的这个标志加入
                regionBean = getSpecialCountry(parentName, parentId);
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
     * 获取城市列表 针对已经确定省级列表
     *
     * @param tableName
     * @return
     */
    public static List<ListBean> getRegionListByProvince(SQLiteDatabase db,
                                                         String tableName, String parentId) {
        List<ListBean> list = new ArrayList<ListBean>();
        ListBean regionBean;
        // SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            // db = databaseHelper.getReadableDatabase();
            // 将父级城市加入列表
            // regionBean = getRegionParent(db, parentId);
            // list.add(regionBean);
            cursor = db.query(tableName, new String[]{"region_id",
                            "region_name", "parent_id"}, "parent_id = ? ",
                    new String[]{parentId}, null, null, null);
            while (!cursor.isLast()) {
                cursor.moveToNext();
                regionBean = new RegionBean(cursor.getString(0), null,
                        cursor.getString(1), cursor.getString(1),
                        cursor.getString(2), null);
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
     * 获取城市列表（包括1级和2级列表）
     *
     * @param db
     * @param tableName
     * @param level
     * @return
     */
    public static List<RegionBean> getRegionListByLevel(SQLiteDatabase db,
                                                        String tableName, String level) {
        List<RegionBean> list = new ArrayList<>();
        RegionBean regionBean;
        // SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            // db = databaseHelper.getReadableDatabase();
            // 将父级城市加入列表
            // regionBean = getRegionParent(db, parentId);
            // list.add(regionBean);
            cursor = db.query(tableName, new String[]{"region_id",
                            "region_name", "level"}, "level = ? ",
                    new String[]{level}, null, null, null);
            while (!cursor.isLast()) {
                cursor.moveToNext();
                regionBean = new RegionBean(cursor.getString(0), null,
                        FormatLocation.getFormatByCity(cursor.getString(1)), null, null, null);
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
     * 特殊地理位置(比如全国)
     *
     * @return 单个地理位置
     */
    private static RegionBean getSpecialCountry(String name, String parentId) {
        // 如果为null,即为全国
        return new RegionBean(parentId, "", name, name, "", "");
    }

}
