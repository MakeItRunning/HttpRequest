package com.td.qianhai.fragmentmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.td.qianhai.epay.bb.R;

/**
 * 第二个tab页 （附近）
 * 
 * @author Administrator
 * 
 */
public class TabAFm extends Fragment {
	/** 头部标题 */
	private TextView title_name;
	/** 视图 */
	private View view;
	
	private View titleview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.tab_a, null, false);
		initview();
	}

	@Override  
	   public void setUserVisibleHint(boolean isVisibleToUser) {  
	       super.setUserVisibleHint(isVisibleToUser);  
	       if (isVisibleToUser) {  
	           //相当于Fragment的onResume  
	    	 
	       } else {  
	           //相当于Fragment的onPause  
	       }  
	   } 
	
	private void initview() {
		
		
		
	}

}
