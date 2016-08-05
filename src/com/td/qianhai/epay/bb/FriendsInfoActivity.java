package com.td.qianhai.epay.bb;

import net.tsz.afinal.FinalBitmap;

import com.td.qianhai.epay.bb.beans.HttpUrls;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsInfoActivity extends BaseActivity{
	private TextView tv_name,tv_phone,tv_gender;
	private ImageView  img_head,img_authentication,img_call,img_msg,img_gender;
	private String pohone,idcard,stat,personpic,name;
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_info_activity);
		Intent it = getIntent();
		pohone = it.getStringExtra("pohone");
		idcard = it.getStringExtra("idcard");
		stat = it.getStringExtra("stat");
		name = it.getStringExtra("name");
		personpic = it.getStringExtra("personpic");
		initview();
	}
	
	
	

	private void initview() {
		
		((TextView) findViewById(R.id.tv_title_contre)).setText("我的好友");
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

		// TODO Auto-generated method stub
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_gender = (TextView) findViewById(R.id.tv_gender);
		img_head = (ImageView) findViewById(R.id.img_head);
		img_authentication = (ImageView) findViewById(R.id.img_authentication);
		img_call = (ImageView) findViewById(R.id.img_call);
		img_msg = (ImageView) findViewById(R.id.img_msg);
		img_gender = (ImageView) findViewById(R.id.img_gender);
		
		if(personpic!=null&&!personpic.equals("null")&&!personpic.equals("")){
			FinalBitmap.create(this).display(img_head,
					HttpUrls.HOST_POSM + personpic,
					img_head.getWidth(),
					img_head.getHeight(), null, null);
		}
		
		if(name!=null){
			Log.e("name", " = = = "+name);
			if(name.equals("null")){
				tv_name.setText("未知");
			}else{
				tv_name.setText(name);
			}
			
		}
		if(idcard!=null){
			tv_gender.setText(gender(idcard));
		}
		if(stat!=null){
			if(stat.equals("0")){
				img_authentication.setImageResource(R.drawable.authentication_ok);
			}else{
				img_authentication.setImageResource(R.drawable.authentication_no);
			}
//			tv_name.setText(name);
		}
		if(pohone!=null){
			tv_phone.setText(pohone);
		}
		
		img_call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(tv_phone.getText()!=null&&!tv_phone.getText().equals("")){
					String phone = tv_phone.getText().toString();
					Uri uri = Uri.parse("tel:"+phone);   
					Intent it = new Intent(Intent.ACTION_DIAL, uri);  
					startActivity(it);
				}
			}
		});
		img_msg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(tv_phone.getText()!=null&&!tv_phone.getText().equals("")){
					String phone = tv_phone.getText().toString();
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.putExtra("address", phone);
					intent.setType("vnd.android-dir/mms-sms");
					startActivity(intent);
				}
			}
		});
	}
	
	private String gender(String idcard){
		String sex = "";
		if(idcard!=null&&!idcard.equals("")&&!idcard.equals("null")){
			
		
//		String birth = idcard.substring(6, 14);
		String s= idcard.substring(16, 17);
		Log.e("", " - - - ==   "  +idcard);
		Log.e("", " - - -"  +Integer.parseInt(s));
		if(Integer.parseInt(s)%2==0){
			sex = "女";
			img_gender.setImageResource(R.drawable.man_img);
		}else if(Integer.parseInt(s)%2==1){
			
			sex ="男";
			img_gender.setImageResource(R.drawable.girl);
		}else{
			sex = "未知";
		}
		}
		return sex;
	}
}
