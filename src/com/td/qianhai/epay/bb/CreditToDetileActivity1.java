package com.td.qianhai.epay.bb;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.Entity;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.interfaces.onMyaddTextListener;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;
import com.td.qianhai.epay.bb.views.dialog.MyEditDialog;
import com.td.qianhai.epay.bb.views.dialog.OneButtonDialogWarn;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnMyDialogClickListener;

public class CreditToDetileActivity1 extends BaseActivity {


	private TextView btn_credit_confirm,card_tvs,name_tvs,balance_tv;
	
	private EditText et_credit_balance;

	private MyEditDialog doubleWarnDialog;

	private String money, mercnum;
	
	private OneButtonDialogWarn warnDialog;
	
	private String  ids,car,nam;
	
	private String mobile;
	

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cretdit_detile1);
		AppContext.getInstance().addActivity(this);
//		mercnum = ((AppContext) getApplication()).getMercNum();
//		mobile = ((AppContext) getApplication()).getMobile();
		
		mercnum = MyCacheUtil.getshared(this).getString("MercNum", "");
		mobile = MyCacheUtil.getshared(this).getString("Mobile", "");
		
		Intent it = getIntent();
		
		nam = it.getStringExtra("nam");
		
		car = it.getStringExtra("car");
		
		ids = it.getStringExtra("ids");
		
		GetWalletInfo  walletinfo = new GetWalletInfo();
		
		walletinfo.execute(HttpUrls.RICH_TREASURE_INFO + "",
				mobile);
		
		initview();
		
		

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

		btn_credit_confirm = (TextView) findViewById(R.id.btn_credit_confirm);
		et_credit_balance = (EditText) findViewById(R.id.et_credit_balance);
		card_tvs = (TextView) findViewById(R.id.card_tvs);
		name_tvs = (TextView) findViewById(R.id.name_tvs);
		balance_tv = (TextView) findViewById(R.id.balance_tv);
		if(car!=null&&!car.equals("")){
			card_tvs.setText("尾号"+car.substring(car.length()-4));
		}
		if(nam!=null&&!nam.equals("")){
			name_tvs.setText(nam);
		}
		btn_credit_confirm.setEnabled(false);
		

		
		et_credit_balance.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
				if(s.length()>0){
						btn_credit_confirm.setEnabled(true);
					
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
		
		
		btn_credit_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String money = et_credit_balance.getText().toString();
				
				int a = (int) (Double.parseDouble(money) * 100);
				final String moneys = String.valueOf(a);
				
				doubleWarnDialog = new MyEditDialog(
						CreditToDetileActivity1.this, R.style.MyEditDialog,
						"还款", "请输入支付密码", "确认", "取消", moneys,
						new OnMyDialogClickListener() {

							@Override
							public void onClick(View v) {

								switch (v.getId()) {
								case R.id.btn_right:
									doubleWarnDialog.dismiss();
									InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
									m.toggleSoftInput(0,
											InputMethodManager.HIDE_NOT_ALWAYS);
									break;
								case R.id.btn_left:
									String paypwd = doubleWarnDialog
											.getpaypwd();

									if (paypwd == null || paypwd.equals("")) {
										Toast.makeText(getApplicationContext(),"请输入支付密码",
												Toast.LENGTH_SHORT).show();
//										ToastCustom.showMessage(
//												CreditToDetileActivity1.this,
//												"请输入支付密码！");
										return;
									}
									if (paypwd.length() < 6
											|| paypwd.length() > 15) {
										Toast.makeText(getApplicationContext(),"输入的密码长度有误,请输入6～15个数字、字母或特殊符号",
												Toast.LENGTH_SHORT).show();
//										ToastCustom
//												.showMessage(
//														CreditToDetileActivity1.this,
//														"输入的密码长度有误,请输入6～15个数字、字母或特殊符号！");
										return;
									}

									break;
								default:
									break;
								}
							}
						}, new onMyaddTextListener() {

							@Override
							public void refreshActivity(String paypwd) {

								if (paypwd == null || paypwd.equals("")) {
									Toast.makeText(getApplicationContext(),"请输入支付密码",
											Toast.LENGTH_SHORT).show();
//									ToastCustom.showMessage(
//											CreditToDetileActivity1.this,
//											"请输入支付密码！");
									return;
								}
								if (paypwd.length() < 6 || paypwd.length() > 15) {
									Toast.makeText(getApplicationContext(),"输入的密码长度有误,请输入6个数字、字母或特殊符号",
											Toast.LENGTH_SHORT).show();
//									ToastCustom.showMessage(
//											CreditToDetileActivity1.this,
//											"输入的密码长度有误,请输入6个数字、字母或特殊符号！");
									return;
								}
								CreditToPay payTask = new CreditToPay();
								payTask.execute(HttpUrls.CREDITPAY+ "", mercnum,"","", moneys,paypwd,ids);

							}
						});
				doubleWarnDialog.setCancelable(false);
				doubleWarnDialog.setCanceledOnTouchOutside(false);
				doubleWarnDialog.show();

			}
		});

	}

	/**
	 * 还款
	 * 
	 * 
	 */
	class CreditToPay extends
			AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoadingDialog("正在操作中...");
		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			String[] values = { params[0], params[1], params[2], params[3],params[4],params[5],params[6]};
			return NetCommunicate.getPay(HttpUrls.CREDITPAY,
					values);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			loadingDialogWhole.dismiss();
			if (result != null) {
				if (result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {
					Intent it = new Intent(CreditToDetileActivity1.this,CreditCardResultActivity.class);
					it.putExtra("tag", "2");
					it.putExtra("result", result.get("RSPMSG").toString());
					startActivity(it);
					finish();
					
				} else {
					doubleWarnDialog.dismiss();
					Intent it = new Intent(CreditToDetileActivity1.this,CreditCardResultActivity.class);
					it.putExtra("tag", "1");
					it.putExtra("result", result.get("RSPMSG").toString());
					startActivity(it);

				}
			} else {
				Toast.makeText(getApplicationContext(),"fail",
						Toast.LENGTH_SHORT).show();
//				ToastCustom.showMessage(CreditToDetileActivity1.this, "fail");
			}
			super.onPostExecute(result);
		}
	}
	
class GetWalletInfo extends AsyncTask<String , Integer, HashMap<String, Object>>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			
			String[] values = { params[0], params[1] };
			return NetCommunicate
					.getMidatc(HttpUrls.RICH_TREASURE_INFO, values);
			
		}
		String avaamt ="0";
		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			if (result != null) {
				if (result.get(Entity.RSPCOD)!=null&&result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {
					
					if(result.get("AVAAMT").toString()!=null){
						avaamt = result.get("AVAAMT").toString();
						
						}
					
					if (avaamt.length() == 1) {
						balance_tv.setText("0.0" + avaamt);
					} else if (avaamt.length() == 2) {
						balance_tv.setText("0." + avaamt);
					} else {
						balance_tv.setText(avaamt.substring(0,
								avaamt.length() - 2)
								+ "."
								+ avaamt.substring(
										avaamt.length() - 2));
					}
					
				} else {
					
					warnDialog = new OneButtonDialogWarn(CreditToDetileActivity1.this,
							R.style.CustomDialog, "提示", result.get(Entity.RSPMSG).toString(), "确定",
							new OnMyDialogClickListener() {
								@Override
								public void onClick(View v) {
									finish();
									warnDialog.dismiss();
								}
							});
					if(warnDialog!=null){
						warnDialog.show();
					}
				}
			} else {
				Toast.makeText(getApplicationContext(),"数据获取失败,请检查网络连接",
						Toast.LENGTH_SHORT).show();
//				ToastCustom.showMessage(CreditToDetileActivity1.this, "数据获取失败,请检查网络连接");
			}
			super.onPostExecute(result);
			
			}
				
		}


}
