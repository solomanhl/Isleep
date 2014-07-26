package cn.figo.isleep.fragment;

import cn.figo.isleep.GlobalVar;
import cn.figo.isleep.R;
import android.app.AlarmManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class FragmentSleep extends Fragment {

	public GlobalVar appState; // ���ȫ�ֱ���
	//public AudioCapture mAudioCapture = null;
	TextView tv_sleep_time;	//����˯�ߣ�˯�߸�ծ
	public CheckBox checkB_record, checkB_sleep, checkB_autoClock; //¼��checkbox,�л�������˯�ߡ�/��˯�߸��ء�����������
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		appState = (GlobalVar) getActivity().getApplicationContext(); // ���ȫ�ֱ���
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return inflater.inflate(R.layout.fragment_sleep, container, false);
		
		View view = inflater.inflate(R.layout.fragment_sleep, container, false);
		findView(view);
        return view;       
	}

	
	
	/*
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		if (mAudioCapture != null) {
            mAudioCapture.stop();
            mAudioCapture.release();
            mAudioCapture = null;
        }
	}
	
	public void onClose() {
		if (mAudioCapture != null) {
            mAudioCapture.stop();
            mAudioCapture.release();
            mAudioCapture = null;
        }
	}
	*/
	
	public void findView(View view){
		checkB_record = (CheckBox) view.findViewById(R.id.checkB_record);
		checkB_sleep = (CheckBox) view.findViewById(R.id.checkB_sleep);
		checkB_autoClock = (CheckBox) view.findViewById(R.id.checkB_autoClock);
		tv_sleep_time = (TextView) view.findViewById(R.id.tv_sleep_time);
		
		checkB_record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
                appState.recTag = isChecked;
            } 
        });
        
		checkB_sleep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
                if(isChecked){	//Ĭ��checked���ǽ���˯��
                	checkB_sleep.setText(R.string.today_sleep);
                	showToday();
                }else{
                	checkB_sleep.setText(R.string.sleep_debt);
                	showDept();
                }
            } 
        });
		
		checkB_autoClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
                if(isChecked){
                	alarmOn();
                }else{//Ĭ��unchecked����������
                	
                }
            } 
        });
	}

	public void showToday() {
		String s = "06:06";

		tv_sleep_time.setText(s);
	}

	public void showDept() {
		String s = "30:30";

		tv_sleep_time.setText(s);
	}

	public AlarmManager am;
	
	public void alarmOn(){
		
	}
	
	
	
}



