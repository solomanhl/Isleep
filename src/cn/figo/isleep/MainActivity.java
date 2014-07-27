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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


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
	public RadioGroup radG_mainTitle;
	public RadioButton radG_item1, radG_item2, radG_item3, radG_item4;	
	
	public String recfile = "figo.amr";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		appState = (GlobalVar) getApplicationContext(); // 获得全局变量
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		appState.getextStorage();//获取外部存储路径 带“/”
		appState.creatDir(appState.extStorage + appState.appsdPath + appState.recPath);
		appState.sleeping = false;
		appState.recTag = false;
		appState.getDB();
		
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
		if (appState.cursor != null){
			appState.cursor. close();
		}
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		recStop();
		
		if (appState.cursor != null){
			appState.cursor. close();
		}
	}
	
	public void findView(){
		viewPager = (ViewPager) findViewById(R.id.viewPager);		
		switch_sleep = (Button) findViewById(R.id.switch_sleep);
		
		radG_mainTitle = (RadioGroup) findViewById(R.id.radG_mainTitle);
		radG_item1 = (RadioButton) findViewById(R.id.radG_item1);
		radG_item2 = (RadioButton) findViewById(R.id.radG_item2);
		radG_item3 = (RadioButton) findViewById(R.id.radG_item3);
		radG_item4 = (RadioButton) findViewById(R.id.radG_item4);
		
		radG_mainTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (checkedId == radG_item1.getId()) {
							viewPager.setCurrentItem(0);
						}
						if (checkedId == radG_item2.getId()) {
							viewPager.setCurrentItem(1);
						}
						if (checkedId == radG_item3.getId()) {
							viewPager.setCurrentItem(2);
						}
						if (checkedId == radG_item4.getId()) {
							viewPager.setCurrentItem(3);
						}
					}
				});
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
			switch (arg0) {
			case 0:
				radG_item1.setChecked(true);
				break;
			case 1:
				radG_item2.setChecked(true);
				break;
			case 2:
				radG_item3.setChecked(true);
				break;
			case 3:
				radG_item4.setChecked(true);
				break;
			}
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
			appState.sleeping = true;
			// 开始时间
			appState.startTime = appState.getCurTime();
			
			if (appState.recTag) {// 录音按下    
				recfile = appState.startTime.toString() + ".amr";
				// 录音				
				recSound(recfile);
				
			}
		}else{//状态是睡觉，则停止
			//
			recStop();
			appState.sleeping = false;
			//结束时间
			appState.endTime = appState.getCurTime();
			appState.timeDiff(appState.startTime, appState.endTime);//计算睡了多少小时，分钟
			
			int id = 0;
			appState.queryTable(appState.sleepTableName);
			if (appState.cursor !=null && appState.cursor.getCount() > 0) {	//适合2.3
				appState.cursor.moveToLast();
				id = Integer.parseInt(appState.cursor.getString(appState.cursor.getColumnIndex("id")) ) + 1;
			}
			else{
				id = 0;
			}
			appState.addSleeplist(id, appState.startTime, appState.endTime);
		}
	}
	
	
	public void recSound(String recfile){
		try {
			//String recfile = "figo.amr";
			recorder = new MediaRecorder();
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			recorder.setOutputFile(appState.extStorage + appState.appsdPath + appState.recPath + recfile);
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
