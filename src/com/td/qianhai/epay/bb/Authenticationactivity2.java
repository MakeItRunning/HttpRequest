package com.td.qianhai.epay.bb;

import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.mail.utils.GetImageUtil;
import com.td.qianhai.epay.bb.views.AnimationUtil;
import com.td.qianhai.epay.bb.views.MyTextWatcher;
import com.td.qianhai.epay.bb.views.city.CityPicker;
import com.td.qianhai.epay.bb.views.city.onChoiceCytyChilListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Authenticationactivity2 extends BaseActivity {
	private TextView tv_au_button;
	private String name = "",id = "",banknum = "";
	private TextView tv_bank,bank_come,tv_finishs,tv_conmmit;
	private String banks = "";
	private EditText tv_banknum;
	private  CityPicker citypicker;
	private LinearLayout tv_finish;
	public static Activity con;
	private ImageView im_bank;
	private String bankProvinceid,bankCityid,bankareid;
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentication_activity2);
		AppContext.getInstance().addActivity(this);
		Intent intent = getIntent();
		con = this;
		name = intent.getStringExtra("name");
		id = intent.getStringExtra("id");
		initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		tv_bank = (TextView) findViewById(R.id.tv_bank);
		((TextView) findViewById(R.id.tv_title_contre)).setText("实名认证");
		citypicker = (CityPicker) findViewById(R.id.citypicker);
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		tv_au_button = (TextView) findViewById(R.id.tv_au_button);
		tv_banknum = (EditText) findViewById(R.id.tv_banknum);
		tv_banknum.addTextChangedListener(new MyTextWatcher(tv_banknum));
		bank_come = (TextView) findViewById(R.id.bank_come);
		tv_finish = (LinearLayout) findViewById(R.id.tv_finish);
		tv_conmmit = (TextView) findViewById(R.id.tv_comit);
		tv_finishs = (TextView) findViewById(R.id.tv_finishs);
		im_bank = (ImageView) findViewById(R.id.im_bank);
		
		tv_conmmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AnimationUtil.GoButton(Authenticationactivity2.this, tv_finish);
				tv_finish.setVisibility(View.GONE);
				citypicker.setfirstdata();
				if(tv_banknum.getText().toString().length()>=1){
					if(tv_bank.getText().toString().length()>=1){
						if(bank_come.getText().toString().length()>=1){
							tv_au_button.setEnabled(true);
						}else{
							tv_au_button.setEnabled(false);
						}
					}else{
						tv_au_button.setEnabled(false);
					}
				}else{
					tv_au_button.setEnabled(false);
			}
			}
		});
		
		tv_finishs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AnimationUtil.GoButton(Authenticationactivity2.this, tv_finish);
				tv_finish.setVisibility(View.GONE);
				
			}
		});
		
		bank_come.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AnimationUtil.GoTop(Authenticationactivity2.this, tv_finish);
				tv_finish.setVisibility(View.VISIBLE);
			}
		});
		
		
		citypicker.getcity(new onChoiceCytyChilListener() {
			
			@Override
			public void onClick(String i, String v, String a, String ni, String vi,
					String ai) {
				// TODO Auto-generated method stub
				bankProvinceid = i;
				bankCityid = v;
				bankareid = a;
				try {
					bank_come.setText(ni+vi+ai);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
			}
		});
		
		tv_au_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 banknum = tv_banknum.getText().toString().replace(" ", "");
				Intent it = new Intent(Authenticationactivity2.this,
						AuthenticationActivity3.class);
				it.putExtra("bankProvinceid", bankProvinceid);
				it.putExtra("bankCityid", bankCityid);
				it.putExtra("bankareid", bankareid);
				it.putExtra("banknum", banknum);
				it.putExtra("name", name);
				it.putExtra("id", id);
				startActivity(it);
			}
		});
		
		tv_bank.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Authenticationactivity2.this,SelectBankActivity.class);
				it.putExtra("tag", "3");
				startActivityForResult(it, 2);
			}
		});
		
		tv_banknum.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				if(s.length()>=1){
					if(tv_bank.getText().toString().length()>=1){
						if(bank_come.getText().toString().length()>=1){
							tv_au_button.setEnabled(true);
						}else{
							tv_au_button.setEnabled(false);
						}
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
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int arg1, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, arg1, data);
		if(arg1==2){
			  banks = data.getExtras().getString("result");
			  tv_bank.setText(banks);
			  String imgurl =data.getExtras().getString("img");
				Bitmap bit = null;
				try {
					 bit = GetImageUtil.iscace( im_bank,HttpUrls.HOST_POSM+imgurl);
				} catch (Exception e) {
					// TODO: handle exception\
					Log.e("", ""+e.toString());
				}
				if(bit!=null){
					im_bank.setImageBitmap(bit);
				}else{
				}
				if(tv_banknum.getText().toString().length()>=1){
					if(tv_bank.getText().toString().length()>=1){
						if(bank_come.getText().toString().length()>=1){
							tv_au_button.setEnabled(true);
						}else{
							tv_au_button.setEnabled(false);
						}
					}else{
						tv_au_button.setEnabled(false);
					}
				}else{
					tv_au_button.setEnabled(false);
			}
		}
	}

}
