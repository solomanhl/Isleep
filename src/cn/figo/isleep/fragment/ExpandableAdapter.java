package cn.figo.isleep.fragment;

import java.util.List;


import cn.figo.isleep.R;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableAdapter extends BaseExpandableListAdapter {

	private List<String> groupArray;  
    private List<List<String>> childArray;  
    private Activity activity;  
    private float density;
    private Context context_; 
    
    public ExpandableAdapter(Context context, Activity a,List<String> groupArray,List<List<String>> childArray, float density) {  
  
    	activity = a;  
        this.groupArray = groupArray;  
        this.childArray = childArray;  
        this.density = density;        
        context_ = context;  
    }  
    
    @Override  
    public Object getChild(int groupPosition, int childPosition) {  
        // TODO Auto-generated method stub  
        return childArray.get(groupPosition).get(childPosition);  
    }  
    @Override  
    public long getChildId(int groupPosition, int childPosition) {  
        // TODO Auto-generated method stub  
        return childPosition;  
    }  
    @Override  
    public View getChildView(int groupPosition, int childPosition,  
            boolean isLastChild, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  
        //String string = childArray.get(groupPosition).get(childPosition);  
        //���ñ��������ı���       
        
        //return getGenericView(string);  //����getGenericView����
    	
    	
    	
    	TextView txt_child;  

            convertView = LayoutInflater.from(context_).inflate(R.layout.expandlist_childs, null);   
            txt_child = (TextView)convertView.findViewById(R.id.tv_expandChild);  
            txt_child.setText(childArray.get(groupPosition).get(childPosition));  
            
        /*�ж��Ƿ������һ����һ���������ı���*/  
/*        if(isLastChild){  
            convertView.setBackgroundResource(R.drawable.child_end);  
        } else {  
            convertView.setBackgroundResource(R.drawable.child);  
        }*/  
          
        return convertView;
    }  
    @Override  
    public int getChildrenCount(int groupPosition) {  
        // TODO Auto-generated method stub  
        return childArray.get(groupPosition).size();  
    }  
    @Override  
    public Object getGroup(int groupPosition) {  
        // TODO Auto-generated method stub  
        return groupArray.get(groupPosition);  
    }  
    @Override  
    public int getGroupCount() {  
        // TODO Auto-generated method stub  
        return groupArray.size();  
    }  
    @Override  
    public long getGroupId(int groupPosition) {  
        // TODO Auto-generated method stub  
        return groupPosition;  
    }  
    @Override  
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  
        //String string = groupArray.get(groupPosition);  
        //return getGenericView(string);  //����getGenericView����


    	ImageView img_group;
    	TextView txt_group;  
            convertView = LayoutInflater.from(context_).inflate(R.layout.expandlist_group, null); 
            img_group = (ImageView)convertView.findViewById(R.id.imgV_expand_group);  
            //img_group.setBackgroundResource(R.drawable.index_right);
            txt_group = (TextView)convertView.findViewById(R.id.tv_expand_title);  
            txt_group.setText(groupArray.get(groupPosition));          
            
            /*�ж��Ƿ�group�ſ������ֱ����ñ���ͼ*/  
            if(isExpanded){  
            	img_group.setImageDrawable(context_.getResources().getDrawable(R.drawable.dialog_more_down));        	
            }else{  
            	img_group.setImageDrawable(context_.getResources().getDrawable(R.drawable.index_right));
            }  
        
        return convertView;        
    } 
    
    @Override  
    public boolean hasStableIds() {  
        // TODO Auto-generated method stub  
        return false;  
    }  
    @Override  
    public boolean isChildSelectable(int groupPosition, int childPosition) {  
        // TODO Auto-generated method stub  
        return true;  	//����ѡ������
    }  
      
/****************************************����Ϊ�Զ��巽��*********************************************/   
    /** 
     * Children 's View 
     * @param string 
     * @return 
     */  
    public TextView getGenericView(String string) {  
    	
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 48);  
        TextView text = new TextView(activity);  
        text.setLayoutParams(layoutParams);  
        // Center the text vertically  
        text.setGravity(Gravity.BOTTOM | Gravity.LEFT); 
     
        
        // Set the text starting position  
        
        if (density == 1){
        	text.setPadding(48, 0, 0, 0);
    		text.setTextSize(16);
    	}else{
    		text.setPadding(64, 0, 0, 0);
    		text.setTextSize(12);
    	}
        
        if (string.equals("�Ƽ�") || string.equals("��̨") || string.equals("����")){//big
        	text.setTextColor(0xffc78c26);	//��ɫ
        }else	//small
        	text.setTextColor(0xffd13032);	//���ɫ
        
        return text;  
    }  


}
