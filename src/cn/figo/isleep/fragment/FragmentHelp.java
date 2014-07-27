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

	public GlobalVar appState; // ���ȫ�ֱ���

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
	List<String> bigArray = new ArrayList<String>();	//��һ��
	List<List<String>> smallArray = new ArrayList<List<String>>();;	//�ڶ���
	List<String> tempArray01 = new ArrayList<String>();
	
	private void updateClassify() {
		// TODO Auto-generated method stub				
		
		if (adapter == null){
			bigArray.add("һ������˯��");
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
		
		//expandableListView_classify.expandGroup(0); // չ����һ��

		// ExpandListView���˵�����¼�
		expandableListView_classify.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {

						//�������߶�ʮһ��ȫ��������˵
						/*for (int k = 0; k<expandableListView_classify.getCount() -1; k++)
							expandableListView_classify.collapseGroup(k); // ����
*/						
						if(expandableListView_classify.isGroupExpanded(groupPosition) ){
							expandableListView_classify.collapseGroup(groupPosition); // ����
						}else{
							expandableListView_classify	.expandGroup(groupPosition); // չ��	
						}
						/*
						// ��κܺ���Ҫ��չ��group�õ�
						if (expandableListView_classify.isGroupExpanded(groupPosition)) // ���չ��
						{
							//expandableListView_classify.collapseGroup(groupPosition); // ����
							
						} else {
							expandableListView_classify	.expandGroup(groupPosition); // չ��
							// TODO Auto-generated method stub
							System.out.println("GroupOnClick"+ String.valueOf(groupPosition) + id);
							
							//�ͻ�Ҫ����ʾ����࣬��ʱ����
							//Big = appState.bigArray.get(groupPosition);
							//display_Food(Big);
							
						}
					*/

						return true;
					}
				});

		// ExpandListView�Ӳ˵�����¼�
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
						
						
						//�ָ�����child����ɫ
						for (int i=0;i<parent.getChildCount();i++){
							parent.getChildAt(i).setBackgroundColor(0x00000000);
						}
						//���ĵ�ǰ���������ɫ
						v.setBackgroundColor(0xc8eea532); //c8=80%͸���ȣ�eea532��ɫ
						
						
						// �õ�������С���������
						String Big = bigArray.get(groupPosition);
						String Small = (smallArray.get(groupPosition)).get(childPosition);
						System.out.println(Big + Small);
						// ��ʾ��Ӧ����Ĳ˵�listview
						//display_Food(Big, Small);
						return true;
					}
				});
	}
	
}
