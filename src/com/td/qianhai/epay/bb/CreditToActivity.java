package com.td.qianhai.epay.bb;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.td.qianhai.epay.bb.adapter.CreditListAdapter;
import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.CreditListBean;
import com.td.qianhai.epay.bb.beans.Entity;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;
import com.td.qianhai.epay.bb.views.dialog.ChooseDialog;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnBackDialogClickListener;

public class CreditToActivity extends BaseActivity {
	
	private LinearLayout add_creditcard;
	
	private ArrayList<HashMap<String, Object>> mlist;//
	
	private String mercnum;

	private ListView listview;
	
	private CreditListAdapter adapter;
	
	private ChooseDialog chooseDialog;
	
	private TextView delete_propty;
	
	private String carcatno;
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppContext.getInstance().addActivity(this);
		setContentView(R.layout.activity_credit_card);
		
//		mercnum = ((AppContext)getApplication()).getMercNum();
		mercnum = MyCacheUtil.getshared(this).getString("MercNum", "");

		initview();
		mlist = new ArrayList<HashMap<String,Object>>();
		adapter = new CreditListAdapter(CreditToActivity.this, mlist, 0);
		listview.setAdapter(adapter);
		
		initchargelist();
		
		initadapter();

	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(AppContext.iscreditcardlist){
			mlist.clear();
			initchargelist();
			AppContext.iscreditcardlist = false;
		}
		
	}

	private void initadapter() {
		

		
	}

	private void initview() {
		((TextView) findViewById(R.id.tv_title_contre)).setText("信用卡还款");
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
		add_creditcard = (LinearLayout) findViewById(R.id.add_creditcard);
		listview = (ListView) findViewById(R.id.mycirclist);
		delete_propty = (TextView) findViewById(R.id.delete_propty);
		add_creditcard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent it = new Intent(CreditToActivity.this,CreditToDetileActivity.class);
				
				startActivity(it);
				
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				String nam = mlist.get((int)id).get("CARDNAME").toString();
				String car = mlist.get((int)id).get("CARDNO").toString();
				String ids = mlist.get((int)id).get("CARDACTID").toString();
				Intent it = new Intent(CreditToActivity.this,CreditToDetileActivity1.class);
				it.putExtra("car", car);
				it.putExtra("nam", nam);
				it.putExtra("ids",ids);
				startActivity(it);
			}
		});
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					int arg2, long id) {
				carcatno = mlist.get((int)id).get("CARDACTID").toString();
				// TODO Auto-generated method stub
//				TextView tv =  (TextView) v.findViewById(R.id.remove_img);
//				tv.setVisibility(View.VISIBLE);
				chooseDialog = new ChooseDialog(
						CreditToActivity.this,
						R.style.CustomDialog,
						new OnBackDialogClickListener() {

							@Override
							public void OnBackClick(View v, String str,
									int position) {
								// TODO Auto-generated method stub

								if (position == 0) {
									
									SpannableString msp = new SpannableString("您要删除该信用卡?");
									showDoubleWarnDialog(msp);
									
								} else {
									
								}
								chooseDialog.dismiss();
							}
						}, "功能选择", CreditToActivity.this
								.getResources().getStringArray(
										R.array.credititem));
				chooseDialog.show();
				
				return true;
			}
		});
	}
	
	
	
	private void initchargelist() {		

		showLoadingDialog("正在查询中...");
		new Thread(run).start();
		
	}
	
	Runnable run = new Runnable() {

		@Override
		public void run() {
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			String[] values = { String.valueOf(HttpUrls.CREDITQUERY),mercnum};
			CreditListBean entitys = NetCommunicate.getPay2(
					HttpUrls.CREDITQUERY, values);

			Message msg = new Message();
			if (entitys != null) {
				list = entitys.list;
						mlist.addAll(list);
					if(mlist.size()<=0||mlist==null){
						
						msg.what = 2;
					}else{
						msg.what = 1;
					}

			} else {
				loadingDialogWhole.dismiss();
				msg.what = 3;
			}
			loadingDialogWhole.dismiss();
			handler.sendMessage(msg);
		}
	};
	
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				
				adapter.notifyDataSetChanged();
				delete_propty.setVisibility(View.VISIBLE);
				
				break;
			case 2:
				delete_propty.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();
				break;
			case 3:
				delete_propty.setVisibility(View.GONE);
				Toast.makeText(getApplicationContext(),"网络异常",
						Toast.LENGTH_SHORT).show();
//				ToastCustom.showMessage(CreditToActivity.this,
//						"网络异常");
				break;
			default:
				break;
			}
		};
	};
	
	
	@Override
	protected void doubleWarnOnClick(View v) {
		// TODO Auto-generated method stub
		super.doubleWarnOnClick(v);
		switch (v.getId()) {
		case R.id.btn_left:
			doubleWarnDialog.dismiss();

			break;
		case R.id.btn_right:
			DeleteCreditToPay payTask = new DeleteCreditToPay();
			payTask.execute(HttpUrls.DELETECREDITCARD+ "", carcatno);
			doubleWarnDialog.dismiss();
			break;

		default:
			break;
		}
	}
	
	
	/**
	 * 解绑
	 * 
	 * 
	 */
	class DeleteCreditToPay extends
			AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			String[] values = { params[0], params[1]};
			return NetCommunicate.getPay(HttpUrls.DELETECREDITCARD,
					values);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			if (result != null) {
				if (result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {
					mlist.clear();
					initchargelist();
					Toast.makeText(getApplicationContext(),result.get("RSPMSG").toString(),
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(CreditToActivity.this,result.get("RSPMSG").toString());
					
				} else {
					Toast.makeText(getApplicationContext(),result.get("RSPMSG").toString(),
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(CreditToActivity.this,result.get("RSPMSG").toString());

				}
			} else {
				Toast.makeText(getApplicationContext(),"fail",
						Toast.LENGTH_SHORT).show();
//				ToastCustom.showMessage(CreditToActivity.this, "fail");
			}
			super.onPostExecute(result);
		}
	}

}
