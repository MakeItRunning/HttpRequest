package com.td.qianhai.epay.bb;

import java.util.HashMap;

import com.td.qianhai.epay.bb.NewRealNameAuthenticationActivity.BussinessInfoTask;
import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.Entity;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class AuthenticationActivity1 extends BaseActivity{
	
	private TextView tv_au_button;
	private EditText re_name,re_id;
	public static Activity con;
	private String custId;
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentication_activity1);
		custId = MyCacheUtil.getshared(this).getString("Mobile", "");
		AppContext.getInstance().addActivity(this);
		con = this;
		initview();
		
		BussinessInfoTask task = new BussinessInfoTask();
		task.execute(HttpUrls.BUSSINESS_INFO + "", custId, "4","0");
	}

	private void initview() {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.tv_title_contre)).setText("实名认证");
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		re_name = (EditText) findViewById(R.id.re_name);
		re_id = (EditText) findViewById(R.id.re_id);
		tv_au_button = (TextView) findViewById(R.id.tv_au_button);
		tv_au_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it = new Intent(AuthenticationActivity1.this,Authenticationactivity2.class);
				it.putExtra("name", re_name.getText().toString());
				it.putExtra("id", re_id.getText().toString());
				startActivity(it);
			}
		});
		
		re_id.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(s.length()>=1){
					if(re_name.getText().toString().length()>=1){
						tv_au_button.setEnabled(true);
					}else{
						tv_au_button.setEnabled(false);
					}
				}else{
					tv_au_button.setEnabled(false);
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		re_name.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(s.length()>=1){
					if(re_id.getText().toString().length()>=1){
						tv_au_button.setEnabled(true);
					}else{
						tv_au_button.setEnabled(false);
					}
				}else{
					tv_au_button.setEnabled(false);
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	class BussinessInfoTask extends
			AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			String[] values = { params[0], params[1], params[2], params[3] };
			return NetCommunicate.getVerificationMidatc(
					HttpUrls.BUSSINESS_INFO, values);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			if (result != null) {
				if (result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {
					if (result.get("CARDID") != null) {
						re_id.setText(result.get("CARDID")
								.toString());
					}
					if (result.get("ACTNAM") != null) {
						re_name.setText(result.get("ACTNAM")
								.toString());
					}
				} else {
					// Toast.makeText(getApplicationContext(),
					// result.get(Entity.RSPMSG).toString(),
					// Toast.LENGTH_SHORT);
				}
			}
			super.onPostExecute(result);
		}
	}

}
