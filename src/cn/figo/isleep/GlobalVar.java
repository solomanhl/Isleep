package cn.figo.isleep;

import java.io.File;
import java.util.Date;

import cn.figo.database.Database;
import android.app.Application;
import android.database.Cursor;
import android.os.Environment;


public class GlobalVar extends Application{
	public String extStorage;
	public String appsdPath = "figo/";
	public String recPath = "rec/";
	public Date startTime, endTime;
	public int curSleepHour, curSleepMin;	//当前、本次睡眠计时 hour min
	public boolean sleeping, recTag;	//正在睡觉      录音按钮按下
	
	private Database database; 
	public Cursor cursor = null;
	public String sleepTableName = "sleeplist";
	
	public String getextStorage() {
		extStorage = Environment.getExternalStorageDirectory() + "/";
		return extStorage;
	}
	
	public void creatDir(String path){
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs(); // 多级目录
	}
	
	public Date getCurTime(){
		return new Date(System.currentTimeMillis());
	}
	
	public void timeDiff(Date t1, Date t2) { // 时间差，分钟
		try {
			long diff = t2.getTime() - t1.getTime();// 这样得到的差值是微秒级别

/*			int days = (int) diff / (1000 * 60 * 60 * 24);
			int hours = (int) (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			long minutes = (diff - (days * (1000 * 60 * 60 * 24)) - (hours * (1000 * 60 * 60))) / (1000 * 60);							
			System.out.println("" + days + "天" + hours + "小时" + minutes + "分");*/
			
			
			curSleepHour = (int) diff / (1000 * 60 * 60);
			curSleepMin = (int) (diff - (curSleepHour * (1000 * 60 * 60))) / (1000 * 60);
			System.out.println("" + curSleepHour + "小时" + curSleepMin + "分");
		} catch (Exception e) {
			curSleepHour = 0;
			curSleepMin = 0;
		}		
	}
	
	public Database getDB()
	{
		System.out.println("全局getDB");
		database = new Database(this);
		database.open();
		return database;
	}
	
	public Cursor queryTable(String tablename){	
		cursor = database.queryTable(tablename);
		return cursor;
	}
	
	public Cursor queryTable(String tablename, Date startTime, Date endTime){	
		cursor = database.queryTable(tablename, startTime, endTime);
		return cursor;
	}
	
	public long addSleeplist(int id, Date startTime, Date endTime){
		return database.addSleeplist(id, startTime, endTime);
	}
	
	public Date getinstallDate(){
		Date dt = new Date(database.getinstallDate());
		return dt;
	}
}
