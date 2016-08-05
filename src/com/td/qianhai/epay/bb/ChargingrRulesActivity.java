package com.td.qianhai.epay.bb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ChargingrRulesActivity extends BaseActivity{

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_chargingr);
		
		initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.tv_title_contre)).setText("收费规则");
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		}
}
