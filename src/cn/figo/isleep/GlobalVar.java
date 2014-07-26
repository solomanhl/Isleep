package cn.figo.isleep;

import java.util.Date;
import android.app.Application;
import android.os.Environment;


public class GlobalVar extends Application{
	public String extStorage;
	public Date startTime, endTime;
	public int curSleepHour, curSleepMin;	//��ǰ������˯�߼�ʱ hour min
	public boolean sleeping, recTag;	//����˯��      ¼����ť����
	
	public String getextStorage() {
		extStorage = Environment.getExternalStorageDirectory() + "/";
		return extStorage;
	}
	
	public Date getCurTime(){
		return new Date(System.currentTimeMillis());
	}
	
	public void timeDiff(Date t1, Date t2) { // ʱ������
		try {
			long diff = t2.getTime() - t1.getTime();// �����õ��Ĳ�ֵ��΢�뼶��

/*			int days = (int) diff / (1000 * 60 * 60 * 24);
			int hours = (int) (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			long minutes = (diff - (days * (1000 * 60 * 60 * 24)) - (hours * (1000 * 60 * 60))) / (1000 * 60);							
			System.out.println("" + days + "��" + hours + "Сʱ" + minutes + "��");*/
			
			
			curSleepHour = (int) diff / (1000 * 60 * 60);
			curSleepMin = (int) (diff - (curSleepHour * (1000 * 60 * 60))) / (1000 * 60);
			System.out.println("" + curSleepHour + "Сʱ" + curSleepMin + "��");
		} catch (Exception e) {
			curSleepHour = 0;
			curSleepMin = 0;
		}
		
	}
}
