package com.td.qianhai.epay.bb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddNewFriendActivity extends BaseActivity implements OnClickListener{
	
	private LinearLayout add_newfriends,add_wechat,add_qq;
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_friend_activity);
		initview();
		
	}

	private void initview() {
		// TODO Auto-generated method stub
		add_newfriends = (LinearLayout) findViewById(R.id.add_newfriends);
		add_wechat = (LinearLayout) findViewById(R.id.add_wechat);
		add_qq = (LinearLayout) findViewById(R.id.add_qq);
		add_newfriends.setOnClickListener(this);
		add_wechat.setOnClickListener(this);
		add_qq.setOnClickListener(this);
		((TextView) findViewById(R.id.tv_title_contre)).setText("添加");
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_newfriends:
			Intent it = new Intent(AddNewFriendActivity.this,ContactsActivity.class);
			startActivity(it);
			break;
		case R.id.add_wechat:
			
			break;
		case R.id.add_qq:
			
			break;

		default:
			break;
		}
		
	}
}
