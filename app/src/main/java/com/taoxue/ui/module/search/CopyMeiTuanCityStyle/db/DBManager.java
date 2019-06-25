package com.taoxue.ui.module.search.CopyMeiTuanCityStyle.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.model.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author Bro0cL on 2016/1/26.
 */
public class DBManager {

    private static final String TABLE_NAME = "resource";
    private static final String NAME = "name";
    private static final String PINYIN = "pinyin";
    private static final String IDS = "ids";
    private static final String SHORT_NAME = "short_name";
    private final MySQLiteHelper myHelper;
    private Context mContext;

    public DBManager(Context context,String DB_NAME) {
        this.mContext = context;

        myHelper = new MySQLiteHelper(mContext, DB_NAME, null, 1);

    }

    /**
     * 从数据库中得到所有城市集合
     *
     * @return
     */
    public ArrayList<City> getAllCities() {
        //获取数据库对象
        SQLiteDatabase db = myHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        ArrayList<City> result = new ArrayList<>();
        City city;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
            String cgs_id = cursor.getString(cursor.getColumnIndex(IDS));
            String short_name = cursor.getString(cursor.getColumnIndex(SHORT_NAME));
            city = new City(name, pinyin,cgs_id,short_name);
            result.add(city);
        }
        cursor.close();
        db.close();
        Collections.sort(result, new CityComparator());
        return result;
    }

    /**
     * 搜索关键词,基于数据的 条件查询, 基于"like 语句"
     *
     * @param keyword
     * @return
     */
    public List<City> searchCity(final String keyword) {
        //获取数据库对象
        SQLiteDatabase db = myHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where "+NAME+" like \"%" + keyword
                + "%\" or "+PINYIN+" like \"%" + keyword + "%\"", null);
        List<City> result = new ArrayList<>();
        City city;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
            String cgs_id = cursor.getString(cursor.getColumnIndex(IDS));
            String short_name = cursor.getString(cursor.getColumnIndex(SHORT_NAME));
            city = new City(name, pinyin,cgs_id,short_name);
            result.add(city);
        }
        cursor.close();
        db.close();
        Collections.sort(result, new CityComparator());
        return result;
    }

    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<City> {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getIndex().substring(0, 1);
            String b = rhs.getIndex().substring(0, 1);
            return a.compareTo(b);
        }
    }


    //向数据库中插入和更新数据
    public void insertAndUpdateData(ArrayList<City> list) {
        //获取数据库对象
        SQLiteDatabase db = myHelper.getWritableDatabase();
        //使用execSQL方法向表中插入数据
        db.execSQL("delete  from "+TABLE_NAME);
        for (City city : list) {
//            update customer set name='AA' where id=1;
            db.execSQL("insert into " + TABLE_NAME + "(" +
                    NAME + ","+
                    IDS + "," +
                    SHORT_NAME + "," +
                    PINYIN + ") " +
                    "values('" +
                    city.getName() + "','"+
                    city.getIds() + "','" +
                    city.getShort_name() + "','" +
                    city.getIndex() + "')");
        }
        db.close();
    }

    //从数据库中查询数据
    public String queryData() {
        String result = "";
        //获得数据库对象
        SQLiteDatabase db = myHelper.getReadableDatabase();
        //查询表中的数据
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, "id asc");
        //获取name列的索引
        int nameIndex = cursor.getColumnIndex(NAME);
        //获取level列的索引
        int levelIndex = cursor.getColumnIndex(PINYIN);
        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
            result = result + cursor.getString(nameIndex) + "\t\t";
            result = result + cursor.getInt(levelIndex) + "       \n";
        }
        cursor.close();//关闭结果集
        db.close();//关闭数据库对象
        return result;
    }

    class MySQLiteHelper extends SQLiteOpenHelper {

        //调用父类构造器
        public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
            super(context, name, factory, version);
        }

        /**
         * 当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行.
         * 重写onCreate方法，调用execSQL方法创建表
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + "("
                    + "id integer primary key,"
                    + NAME + " varchar,"
                    + IDS + " varchar,"
                    + SHORT_NAME + " varchar,"
                    + PINYIN + " varchar)");
        }


        //当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
