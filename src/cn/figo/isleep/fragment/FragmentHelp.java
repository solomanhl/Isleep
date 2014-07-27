package cn.figo.isleep.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.figo.isleep.GlobalVar;
import cn.figo.isleep.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class FragmentHelp extends Fragment{

	public GlobalVar appState; // 获得全局变量

	private ExpandableListView expandableListView_classify;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return inflater.inflate(R.layout.fragment_help, container, false);
		
		View view = inflater.inflate(R.layout.fragment_help, container, false);
			findView(view);					
			updateClassify();
		
        return view;       
	}

	private void findView(View view) {
		// TODO Auto-generated method stub
		expandableListView_classify = (ExpandableListView) view.findViewById(R.id.expandL_sleep_more);
	}

	
	
	ExpandableAdapter adapter = null;
	List<String> bigArray = new ArrayList<String>();	//第一级
	List<List<String>> smallArray = new ArrayList<List<String>>();;	//第二级
	List<String> tempArray01 = new ArrayList<String>();
	
	private void updateClassify() {
		// TODO Auto-generated method stub				
		
		if (adapter == null){
			bigArray.add("一、宝宝睡眠");
			tempArray01.add(getString(R.string.sleep0));
			tempArray01.add(getString(R.string.sleep1));
			tempArray01.add(getString(R.string.sleep2));
			tempArray01.add(getString(R.string.sleep3));
			tempArray01.add(getString(R.string.sleep4));
			tempArray01.add(getString(R.string.sleep5));
			tempArray01.add(getString(R.string.sleep6));
			tempArray01.add(getString(R.string.sleep7));
			smallArray.add(tempArray01);
			adapter = new ExpandableAdapter(getActivity().getBaseContext(), getActivity(), bigArray, smallArray , 1.0f);
		}
		expandableListView_classify.setAdapter(adapter);
		
		//expandableListView_classify.expandGroup(0); // 展开第一个

		// ExpandListView父菜单点击事件
		expandableListView_classify.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {

						//不管三七二十一，全部收起再说
						/*for (int k = 0; k<expandableListView_classify.getCount() -1; k++)
							expandableListView_classify.collapseGroup(k); // 收起
*/						
						if(expandableListView_classify.isGroupExpanded(groupPosition) ){
							expandableListView_classify.collapseGroup(groupPosition); // 收起
						}else{
							expandableListView_classify	.expandGroup(groupPosition); // 展开	
						}
						/*
						// 这段很很重要，展开group用的
						if (expandableListView_classify.isGroupExpanded(groupPosition)) // 如果展开
						{
							//expandableListView_classify.collapseGroup(groupPosition); // 收起
							
						} else {
							expandableListView_classify	.expandGroup(groupPosition); // 展开
							// TODO Auto-generated method stub
							System.out.println("GroupOnClick"+ String.valueOf(groupPosition) + id);
							
							//客户要求不显示大分类，暂时屏蔽
							//Big = appState.bigArray.get(groupPosition);
							//display_Food(Big);
							
						}
					*/

						return true;
					}
				});

		// ExpandListView子菜单点击事件
		expandableListView_classify
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
					/**
					 * parent The ExpandableListView where the click happened v
					 * The view within the expandable list/ListView that was
					 * clicked groupPosition The group position that contains
					 * the child that was clicked childPosition The child
					 * position within the group id The row id of the child that
					 * was clicked
					 * 
					 * @return
					 **/
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						System.out.println("ChildOnClick"
								+ String.valueOf(groupPosition)
								+ String.valueOf(childPosition) + id);
						
						
						//恢复所有child的颜色
						for (int i=0;i<parent.getChildCount();i++){
							parent.getChildAt(i).setBackgroundColor(0x00000000);
						}
						//更改当前子项更改颜色
						v.setBackgroundColor(0xc8eea532); //c8=80%透明度，eea532橙色
						
						
						// 得到大分类和小分类的名字
						String Big = bigArray.get(groupPosition);
						String Small = (smallArray.get(groupPosition)).get(childPosition);
						System.out.println(Big + Small);
						// 显示对应分类的菜单listview
						//display_Food(Big, Small);
						return true;
					}
				});
	}
	
}
