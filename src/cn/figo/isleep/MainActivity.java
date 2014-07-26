package cn.figo.isleep;


import java.io.IOException;
import java.util.ArrayList;

import cn.figo.isleep.fragment.CanvasTrend;
import cn.figo.isleep.fragment.FragmentHelp;
import cn.figo.isleep.fragment.FragmentList;
import cn.figo.isleep.fragment.FragmentSleep;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {

	public android.support.v4.app.FragmentTransaction ft;
	public FragmentSleep fragmentSleep;
	public FragmentList fragmentList;
	public CanvasTrend canvasTrend;
	public FragmentHelp fragmentHelp;
	
	public GlobalVar appState; // 获得全局变量;; 
	public MediaRecorder recorder;
	
	public ViewPager viewPager;
	
	public Button switch_sleep;	//睡眠按钮
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		appState = (GlobalVar) getApplicationContext(); // 获得全局变量
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		appState.getextStorage();//获取外部存储路径 带“/”
		appState.sleeping = false;
		appState.recTag = false;
		findView();
		initViewPager();
		
		/*
		android.support.v4.app.FragmentManager fm =  getSupportFragmentManager();
		ft = fm.beginTransaction();
		
		//fragmentSleep = (FragmentSleep) fm.findFragmentById(R.layout.fragment_sleep);
		fragmentList = (FragmentList) fm.findFragmentByTag("list");
		fragmentHelp = (FragmentHelp) fm.findFragmentByTag("help");
		canvasTrend = (CanvasTrend) fm.findFragmentByTag("trend");				
	
		ft.add(R.id.viewPager,new FragmentSleep(), "sleep");					
		
		//ft.replace(R.id.viewPager, fragmentSleep);
		ft.commit();	

	*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		recStop();
	}
	
	public void onClose() {
		recStop();
	}
	
	public void findView(){
		viewPager = (ViewPager) findViewById(R.id.viewPager);		
		switch_sleep = (Button) findViewById(R.id.switch_sleep);		
		

	}

	
	private ArrayList<Fragment> fragmentArryList;  
	public void initViewPager(){
		//viewPager = (ViewPager)findViewById(R.id.viewpager);  
		fragmentArryList = new ArrayList<Fragment>();  
 
		fragmentSleep = new FragmentSleep();
		fragmentList = new FragmentList();		
		canvasTrend = new CanvasTrend();
		fragmentHelp = new FragmentHelp();
        
		fragmentArryList.add(fragmentSleep);  
		fragmentArryList.add(fragmentList);  
		fragmentArryList.add(canvasTrend);  
		fragmentArryList.add(fragmentHelp);  
          
        //给ViewPager设置适配器  
		viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentArryList));  
		viewPager.setCurrentItem(0);//设置当前显示标签页为第一页  
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器  		
	}
	
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub

		}
		
		
		
	}
	
	public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> list;

		public MyFragmentPagerAdapter(FragmentManager fm,
				ArrayList<Fragment> list) {
			super(fm);
			this.list = list;

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}			
	} 
	
	
	public void switch_sleep_onclick(View view) {
		// mAudioCapture = new AudioCapture(AudioCapture.TYPE_PCM, 1024);
		// mAudioCapture.start();
		if (!appState.sleeping) { // 如果没睡觉，开始记录
			if (appState.recTag) {// 录音按下
				// 录音
				String recfile = "figo.amr";
				recSound(recfile);
				
			}

			appState.sleeping = true;
			// 开始时间
			appState.startTime = appState.getCurTime();
		}else{//状态是睡觉，则停止
			//
			recStop();
			appState.sleeping = false;
			//结束时间
			appState.endTime = appState.getCurTime();
			appState.timeDiff(appState.startTime, appState.endTime);//计算睡了多少小时，分钟
		}
	}
	
	
	public void recSound(String recfile){
		try {
			//String recfile = "figo.amr";
			recorder = new MediaRecorder();
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			recorder.setOutputFile(appState.extStorage + recfile);
			recorder.prepare();
			recorder.start(); // 开始录音
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void recStop() {
		// TODO Auto-generated method stub
		if (recorder != null) {
			recorder.stop();
			recorder.reset();
			recorder.release();
			recorder = null;
        }
	}


}
