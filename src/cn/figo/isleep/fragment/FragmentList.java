package cn.figo.isleep.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import cn.figo.isleep.GlobalVar;
import cn.figo.isleep.R;
import android.app.DatePickerDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class FragmentList extends Fragment{

	public GlobalVar appState; // ���ȫ�ֱ���
	public Button btn_fragmentlist_start, btn_fragmentlist_end;
	public String pressButton;
	public GridView grid_listTop;
	
	private static final int DATA_PICKER_ID = 1;
	private int year;
    private int month;
    private int day;

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
		//return inflater.inflate(R.layout.fragment_list, container, false);
		
		View view = inflater.inflate(R.layout.fragment_list, container, false);
		findView(view);
		updateGridView();
		
		searchList(new Date(0,6,26), appState.getCurTime());	//0��1900��
		
        return view;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		stopPlayRec();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopPlayRec();
	}

	public void findView(View view){
		btn_fragmentlist_start = (Button) view.findViewById(R.id.btn_fragmentlist_start);
		btn_fragmentlist_end = (Button) view.findViewById(R.id.btn_fragmentlist_end);
		grid_listTop = (GridView) view.findViewById(R.id.grid_listTop);
		
		btn_fragmentlist_start.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//��ʼ��Calendar��������
		        Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
		        Date mydate=new Date(); //��ȡ��ǰ����Date����
		        mycalendar.setTime(mydate);////ΪCalendar��������ʱ��Ϊ��ǰ����
		        
		        year=mycalendar.get(Calendar.YEAR); //��ȡCalendar�����е���
		        month=mycalendar.get(Calendar.MONTH);//��ȡCalendar�����е���
		        day=mycalendar.get(Calendar.DAY_OF_MONTH);//��ȡ����µĵڼ���
		        
		        pressButton = "start";
				showDialog(DATA_PICKER_ID);
			}
		});
		
		btn_fragmentlist_end.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
		        Date mydate=new Date(); //��ȡ��ǰ����Date����
		        mycalendar.setTime(mydate);////ΪCalendar��������ʱ��Ϊ��ǰ����
		        
		        year=mycalendar.get(Calendar.YEAR); //��ȡCalendar�����е���
		        month=mycalendar.get(Calendar.MONTH);//��ȡCalendar�����е���
		        day=mycalendar.get(Calendar.DAY_OF_MONTH);//��ȡ����µĵڼ���
		        
		        pressButton = "end";
				showDialog(DATA_PICKER_ID);
			}
		});
		
		DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener(){

	        @Override
	        public void onDateSet(DatePicker view, int year, int month, int day) {
	            // TODO Auto-generated method stub
	            System.out.println(year + "-" + month +"-" + day);
	        }
	        
	    };
	    
	}

	
	public void showDialog(int i) {
		switch (i) {
		case DATA_PICKER_ID:
			/**
             * ���캯��ԭ�ͣ�
             * public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, 
             * int year, int monthOfYear, int dayOfMonth) 
             * content�������Activity��
             * DatePickerDialog.OnDateSetListener��ѡ�������¼�
             * year����ǰ�������ʾ���꣬monthOfYear����ǰ�������ʾ���£�dayOfMonth����ǰ�������ʾ�ĵڼ���
             * 
             */
            //����DatePickerDialog����
            DatePickerDialog dpd=new DatePickerDialog(getActivity(), Datelistener,year,month,day);
            dpd.show();//��ʾDatePickerDialog���
			break;
		}

		
		
	}

	private DatePickerDialog.OnDateSetListener Datelistener=new DatePickerDialog.OnDateSetListener()
    {
        /**params��view�����¼����������
         * params��myyear����ǰѡ�����
         * params��monthOfYear����ǰѡ�����
         * params��dayOfMonth����ǰѡ�����
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
            
            
            //�޸�year��month��day�ı���ֵ���Ա��Ժ󵥻���ťʱ��DatePickerDialog����ʾ��һ���޸ĺ��ֵ
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //��������
            updateDate();
            
        }
        //��DatePickerDialog�ر�ʱ������������ʾ
        private void updateDate()
        {
			if (pressButton == "start") {
				// ��TextView����ʾ����
				// showdate.setText("��ǰ���ڣ�"+year+"-"+(month+1)+"-"+day);
				btn_fragmentlist_start.setText(String.valueOf(year) + "/"
								+ String.valueOf(month + 1) + "/"
								+ String.valueOf(day));
			}else if (pressButton == "end"){
				btn_fragmentlist_end.setText(String.valueOf(year) + "/"
						+ String.valueOf(month + 1) + "/"
						+ String.valueOf(day));
			}
        }
    };

    
	private void updateGridView() {
		// TODO Auto-generated method stub
		//���ɶ�̬���飬����ת������  
	      ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();  
	      for(int i=0;i<10;i++)  
	      {  
	        HashMap<String, Object> map = new HashMap<String, Object>();  
	        map.put("ItemImage", R.drawable.icon);//���ͼ����Դ��ID  
	    map.put("ItemText", "NO."+String.valueOf(i));//�������ItemText  
	        lstImageItem.add(map);  
	      }  
	      //������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ  
	      SimpleAdapter saImageItems = new SimpleAdapter(getActivity(), //ûʲô����  
	                                                lstImageItem,//������Դ   
	                                                R.layout.grid_head,//night_item��XMLʵ��  
	                                                  
	                                                //��̬������ImageItem��Ӧ������          
	                                                new String[] {"ItemImage","ItemText"},   
	                                                  
	                                                //ImageItem��XML�ļ������һ��ImageView,����TextView ID  
	                                                new int[] {R.id.imgV_gridHead,R.id.tv_gridHead});  
	      //��Ӳ�����ʾ  
	      grid_listTop.setAdapter(saImageItems);  
	      //�����Ϣ����  
	      grid_listTop.setOnItemClickListener(new ItemClickListener());  
	}
	
	// ��AdapterView������(���������߼���)���򷵻ص�Item�����¼�
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			// �ڱ�����arg2=arg3
			HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
			// ��ʾ��ѡItem��ItemText
			String s = (String) item.get("ItemText");
			//���������ط�
			s = "figo.amr";
			playRec(s);
		}

	}

	public MediaPlayer mPlayer;
	public void playRec(String recfile) {
		// TODO Auto-generated method stub		
		
		try {
			stopPlayRec();	//��ֹͣ��һ�ε�
			
			mPlayer = new MediaPlayer();
			mPlayer.setDataSource(appState.extStorage + appState.appsdPath + appState.recPath + recfile);
			//mPlayer.setLooping(true);//����ѭ������
			mPlayer.prepare();
			mPlayer.start();//��������    
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	} 
	
	public void stopPlayRec(){
		if (mPlayer != null) {
			mPlayer.stop();
			mPlayer.reset();
			mPlayer.release();
			mPlayer = null;
		}
	}
	
	public void searchList(Date start, Date end){
		appState.queryTable(appState.sleepTableName, start, end);
		//appState.queryTable(appState.sleepTableName);
		int id = 0;
		long starttime, endtime;
		appState.cursor.moveToFirst();
		while(appState.cursor.getCount() > 0 && !appState.cursor.isAfterLast()){
			id = appState.cursor.getInt(appState.cursor.getColumnIndex("id") );
			starttime = appState.cursor.getLong(appState.cursor.getColumnIndex("startTime") );
			endtime = appState.cursor.getLong(appState.cursor.getColumnIndex("endTime") );
			
			appState.cursor.moveToNext();
		}
	}
}
