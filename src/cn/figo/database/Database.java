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
	private String table_list = "sleeplist";// �ѵ�˵����ݱ�����
	//private String table_user = "usermanage";// ��Ա�������ݱ�����
	private Context mCtx = null;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase SQLdb;

	public String username, password, waitername;

	public class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
			System.out
					.println("DB databasehelper(context,name,factory,version)");
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
			// ֻ�ڵ�һ�δ�����ʱ�����
//			db.execSQL("create table IF NOT EXISTS " + table_user
//					+ "(username varchar(20) ," + // �û���
//					"password varchar(20), " + // ����
//					"waitername varchar(20))"); // Ա������
//			SQLdb = db;
//			System.out.println("DB onCreate --- 2");

			db.execSQL("create table IF NOT EXISTS " + table_list
					+ 
					"(id int ," + //���
					"startTime varchar(20), " + //
					"endTime varchar(20) )"); //
			SQLdb = db;
			System.out.println("DB onCreate --- 1");

			// ��һ�δ�����ʱ������Ĭ���û�admin@3369
//			try {
//				username = new String("admin".getBytes(), "utf-8");
//				password = new String("3369".getBytes(), "utf-8");
//				waitername = new String("ϵͳ����Ա".getBytes(), "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}			
//			addUser(username, password, waitername);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}
	}

	/** ���캯�� */
	public Database(Context ctx) {
		this.mCtx = ctx;
		System.out.println("DB���캯��");
	}

	public Database open() throws SQLException {
		System.out.println("DB Open");
		dbHelper = new DatabaseHelper(mCtx);
		// ֻ�е���getReadableDatabase����getWriteableDatabase�������Żᴴ�����ݿ����
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

	public Cursor queryTable(String tablename) {
		Cursor cursor = null;
		try {
			if (tablename.equals(table_list) ) {
				cursor = SQLdb.query(table_list, // table��
						new String[] { "id," + "startTime", "endTime" }, // �ֶ�
						null, // ����
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
				cursor = SQLdb.query(table_list, // table��
						new String[] { "id," + "startTime", "endTime" }, // �ֶ�
						"startTime >= '" + startTime.getTime() + "' and startTime <= '" + endTime.getTime() + "'", // ����
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