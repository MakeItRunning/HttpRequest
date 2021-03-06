package com.td.qianhai.epay.bb.views.dialog;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.td.qianhai.epay.bb.R;
import com.td.qianhai.epay.bb.adapter.ClearingAdapter;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnBackDialogClickListener;

/**
 * 结算方式选择专用dialog
 * 
 * @author liangge
 * 
 */
public class ChooseDialog extends Dialog {

	private TextView tvTitle;

	private ListView listView;

	private ClearingAdapter clearingAdapter;

	private Context context;

	private String[] tempArr;

	private ArrayList<HashMap<String, String>> list;

	private HashMap<String, String> itemMap;

	private String title;

	private OnBackDialogClickListener backDialogClickListener;

	public ChooseDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public ChooseDialog(Context context, int theme,
			OnBackDialogClickListener backDialogClickListener, String title,
			String[] tempArr) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.backDialogClickListener = backDialogClickListener;
		this.title = title;
		this.tempArr = tempArr;
	}

	public ChooseDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.radio_dialog);
		tvTitle = (TextView) findViewById(R.id.radio_dialog_title);
		tvTitle.setText(title);
		listView = (ListView) findViewById(R.id.lv_filter);
		// tempArr = context.getResources().getStringArray(R.array.clearing);
		list = new ArrayList<HashMap<String, String>>();
		
		if(tempArr!=null){
			for (int i = 0; i < tempArr.length; i++) {
				itemMap = new HashMap<String, String>();
				itemMap.put("itemName", tempArr[i]);
				list.add(itemMap);
				clearingAdapter = new ClearingAdapter(context, list);
				listView.setAdapter(clearingAdapter);
			}

		}

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v,
					final int position, long arg3) {
				// TODO Auto-generated method stub
				backDialogClickListener.OnBackClick(v, tempArr[position],
						position);
			}
		});
	}
}
