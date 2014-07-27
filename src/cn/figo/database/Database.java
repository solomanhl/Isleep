package cn.figo.database;

import java.util.Date;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

	private int dbversion = 1;
	private String db_name = "sleep.db";
	private String table_list = "sleeplist";// 已点菜单数据表名称
	private String table_info = "info";// info表，存放安装时间
	private Context mCtx = null;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase SQLdb;

	public String username, password, waitername;
	public Date installDate;

	public class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
			System.out.println("DB databasehelper(context,name,factory,version)");
		}

		public DatabaseHelper(Context context) {
			super(context, db_name, null, dbversion);
			// TODO Auto-generated constructor stub
			System.out.println("DB databasehelper(context)");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			System.out.println("DB onCreate");
			// 只在第一次创建的时候进入
			db.execSQL("create table IF NOT EXISTS " + table_info
					+ "(installDate long)"); // 
			SQLdb = db;
			System.out.println("DB onCreate --- 2");

			//varchar(20)
			db.execSQL("create table IF NOT EXISTS " + table_list
					+ 
					"(id int ," + //序号
					"startTime long, " + //
					"endTime long )"); //
			SQLdb = db;
			System.out.println("DB onCreate --- 1");

			// 第一次创建的时候添加安装时间
			installDate = new Date(System.currentTimeMillis());
			ContentValues args = new ContentValues();
			args.put("installDate", installDate.getTime());
			SQLdb.insert(table_info, null, args);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}
	}

	/** 构造函数 */
	public Database(Context ctx) {
		this.mCtx = ctx;
		System.out.println("DB构造函数");
	}

	public Database open() throws SQLException {
		System.out.println("DB Open");
		dbHelper = new DatabaseHelper(mCtx);
		// 只有调用getReadableDatabase或者getWriteableDatabase方法，才会创建数据库对象
		SQLdb = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

//	public long addUser(String uname, String pass, String wname) {
//		ContentValues args = new ContentValues();
//
//		args.put("username", username);
//		args.put("password", password);
//		args.put("waitername", waitername);
//
//		System.out.println("DB.add " + table_user);
//		return SQLdb.insert(table_user, null, args);
//	}

	public long addSleeplist(int id, Date startTime, Date endTime) {
		ContentValues args = new ContentValues();

		args.put("id", id);
		args.put("startTime", startTime.getTime());
		args.put("endTime", endTime.getTime());

		System.out.println("DB.add " + table_list);
		return SQLdb.insert(table_list, null, args);
	}
	
	public long delFoodlist(int id) {
		return SQLdb.delete(table_list, "id = " + id, null);
	}

	public long getinstallDate(){
		Cursor cursor = null;
		try {
			cursor = SQLdb.query(table_info, // table名
					new String[] { "installDate" }, // 字段
					null, // 条件
					null, null, null, null);
			cursor.moveToFirst();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return cursor.getLong(cursor.getColumnIndex("installDate"));
	}
	
	public Cursor queryTable(String tablename) {
		Cursor cursor = null;
		try {
			if (tablename.equals(table_list) ) {
				cursor = SQLdb.query(table_list, // table名
						new String[] { "id," + "startTime", "endTime" }, // 字段
						null, // 条件
						null, null, null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return cursor;
	}
	
	public Cursor queryTable(String tablename, Date startTime, Date endTime) {
		Cursor cursor = null;
		try {
			if (tablename.equals(table_list) ) {
				cursor = SQLdb.query(table_list, // table名
						new String[] { "id," + "startTime", "endTime" }, // 字段
						"startTime >= '" + startTime.getTime() + "' and startTime <= '" + endTime.getTime() + "'", // 条件
						null, null, null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return cursor;
	}

	/*
	 * public Cursor getAll(){ return SQLdb.rawQuery("select * from " +
	 * table_list, null); }
	 * 
	 * public Cursor getThis(String tableid){ return SQLdb.query(table_list, new
	 * String [] {"tableid","foodname","num"}, "tableid = " + tableid, null,
	 * null, null, null); }
	 */
	public void clearThis(String tableid) {
		SQLdb.delete(table_list, null, null);
	}
}
