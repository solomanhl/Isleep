package cn.figo.isleep.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import cn.figo.isleep.GlobalVar;
import cn.figo.isleep.R;
import android.app.DatePickerDialog;
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

	public GlobalVar appState; // 获得全局变量
	public Button btn_fragmentlist_start, btn_fragmentlist_end;
	public String pressButton;
	public GridView grid_listTop;
	
	private static final int DATA_PICKER_ID = 1;
	public DatePicker mDatePicker;
	private int year;
    private int month;
    private int day;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		appState = (GlobalVar) getActivity().getApplicationContext(); // 获得全局变量
		
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
		
        return view;
	}



	public void findView(View view){
		btn_fragmentlist_start = (Button) view.findViewById(R.id.btn_fragmentlist_start);
		btn_fragmentlist_end = (Button) view.findViewById(R.id.btn_fragmentlist_end);
		grid_listTop = (GridView) view.findViewById(R.id.grid_listTop);
		
		btn_fragmentlist_start.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//初始化Calendar日历对象
		        Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
		        Date mydate=new Date(); //获取当前日期Date对象
		        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期
		        
		        year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
		        month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
		        day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
		        
		        pressButton = "start";
				showDialog(DATA_PICKER_ID);
			}
		});
		
		btn_fragmentlist_end.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
		        Date mydate=new Date(); //获取当前日期Date对象
		        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期
		        
		        year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
		        month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
		        day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
		        
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
             * 构造函数原型：
             * public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, 
             * int year, int monthOfYear, int dayOfMonth) 
             * content组件运行Activity，
             * DatePickerDialog.OnDateSetListener：选择日期事件
             * year：当前组件上显示的年，monthOfYear：当前组件上显示的月，dayOfMonth：当前组件上显示的第几天
             * 
             */
            //创建DatePickerDialog对象
            DatePickerDialog dpd=new DatePickerDialog(getActivity(), Datelistener,year,month,day);
            dpd.show();//显示DatePickerDialog组件
			break;
		}

		
		
	}

	private DatePickerDialog.OnDateSetListener Datelistener=new DatePickerDialog.OnDateSetListener()
    {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
            
            
            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //更新日期
            updateDate();
            
        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
			if (pressButton == "start") {
				// 在TextView上显示日期
				// showdate.setText("当前日期："+year+"-"+(month+1)+"-"+day);
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
		//生成动态数组，并且转入数据  
	      ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();  
	      for(int i=0;i<10;i++)  
	      {  
	        HashMap<String, Object> map = new HashMap<String, Object>();  
	        map.put("ItemImage", R.drawable.icon);//添加图像资源的ID  
	    map.put("ItemText", "NO."+String.valueOf(i));//按序号做ItemText  
	        lstImageItem.add(map);  
	      }  
	      //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应  
	      SimpleAdapter saImageItems = new SimpleAdapter(getActivity(), //没什么解释  
	                                                lstImageItem,//数据来源   
	                                                R.layout.grid_head,//night_item的XML实现  
	                                                  
	                                                //动态数组与ImageItem对应的子项          
	                                                new String[] {"ItemImage","ItemText"},   
	                                                  
	                                                //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
	                                                new int[] {R.id.imgV_gridHead,R.id.tv_gridHead});  
	      //添加并且显示  
	      grid_listTop.setAdapter(saImageItems);  
	      //添加消息处理  
	      grid_listTop.setOnItemClickListener(new ItemClickListener());  
	}
	
	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			// 在本例中arg2=arg3
			HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
			// 显示所选Item的ItemText
			String s = (String) item.get("ItemText");
		}

	} 
}
