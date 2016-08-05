package com.td.qianhai.epay.bb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.unlock.SetUnlockPasswordActivity;
import com.td.qianhai.epay.bb.unlock.UnlockLoginActivity;
import com.td.qianhai.epay.bb.views.ToastCustom;
import com.td.qianhai.epay.bb.views.dialog.OneButtonDialogWarn;
import com.td.qianhai.epay.bb.views.dialog.TwoButtonDialogStyle2;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnMyDialogClickListener;
import com.td.qianhai.epay.utils.DateUtil;

/**
 * 帐号管理界面
 * 
 * @author liangge
 * 
 */
public class AccountManageActivity extends BaseActivity implements
		OnClickListener {
	/** Activity */
	public static Activity activity;
	/** 商户id、用户状态 */
	private String attStr,tagsts;// custId,
	/** 开户银行信息修改、手机号码变更申请、商户信息修改、银行卡信息变更申请、实名认证 */
	private RelativeLayout updateBankInfo, updateDealerData,
			requestBankInfoChange, realNameAuthentication, btn_touch_password,
			btn_uppay_password, btn_reg_pay_password,btn_touch_bankphone,btn_unbindkard;
	private TextView tv_content;
	private OneButtonDialogWarn warnDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_account_manage_new);
		
		AppContext.getInstance().addActivity(this);
		activity = this;
		// custId = ((AppContext) this.getApplication()).getCustId();
		attStr =  MyCacheUtil.getshared(this).getString("MERSTS", "");
		tagsts = MyCacheUtil.getshared(this).getString("STS", "");
		findViewById(R.id.btn_menu_account_manage_query_info)
				.setOnClickListener(this);
//		p_out = (TextView) findViewById(R.id.p_out);
//		p_out.setOnClickListener(this);
		btn_touch_password = (RelativeLayout) findViewById(R.id.btn_touch_password);
		btn_touch_password.setOnClickListener(this);
		updateBankInfo = (RelativeLayout) findViewById(R.id.btn_menu_account_manage_bank_info_update);
		updateBankInfo.setOnClickListener(this);
		updateDealerData = (RelativeLayout) findViewById(R.id.btn_menu_account_manage_update_dealer_data);
		updateDealerData.setOnClickListener(this);
		requestBankInfoChange = (RelativeLayout) findViewById(R.id.btn_menu_bank_info_update_request);
		requestBankInfoChange.setOnClickListener(this);
		realNameAuthentication = (RelativeLayout) findViewById(R.id.btn_menu_account_manage_real_name_authentication);
		realNameAuthentication.setOnClickListener(this);
		btn_uppay_password = (RelativeLayout) findViewById(R.id.btn_uppay_password);
		btn_uppay_password.setOnClickListener(this);
		btn_unbindkard = (RelativeLayout) findViewById(R.id.btn_unbindkard);
		btn_unbindkard.setOnClickListener(this);
		btn_reg_pay_password = (RelativeLayout) findViewById(R.id.btn_reguppay_password);
		btn_reg_pay_password.setOnClickListener(this);
		btn_touch_bankphone = (RelativeLayout) findViewById(R.id.btn_touch_bankphone);
		btn_touch_bankphone.setOnClickListener(this);
		((TextView) findViewById(R.id.tv_title_contre)).setText("账户管理");
		
//		if(((AppContext)getApplication()).getTxnsts().equals("0")){
			
//			new Handler().postDelayed(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					Intent it = new Intent(AccountManageActivity.this,IntroductionActivity.class);
//					it.putExtra("title", "温馨提示");
//					it.putExtra("description", "您的账户暂未进行实名认证,为了您的账户安全和对资金的管理或操作,请在账户管理中进行资料补全.");
//					startActivity(it);
//				}
//			}, 1000);

//		}
//		if(!UnlockLoginActivity.isjp){
//			btn_touch_password.setVisibility(View.GONE);
//		}else{
//			btn_touch_password.setVisibility(View.VISIBLE);
//		}
		
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		findViewById(R.id.btn_menu_account_manage_update_login_password)
				.setOnClickListener(this);
		// 根据登录成功后的认证状态来显示和隐藏功能按钮
		if (attStr != null) {
			if (attStr.equals("0")) {
				updateBankInfo.setVisibility(View.GONE);
				requestBankInfoChange.setVisibility(View.VISIBLE);
				updateDealerData.setVisibility(View.VISIBLE);
				realNameAuthentication.setVisibility(View.GONE);
			}else {
				if(MyCacheUtil.getshared(AccountManageActivity.this).getString("Txnsts", "").equals("1")){
					realNameAuthentication.setVisibility(View.GONE);
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (DateUtil.isFastDoubleClick()) {
			return;
		} else {
			final Intent intent = new Intent();
			switch (v.getId()) {
			// 商户信息查询
			case R.id.btn_menu_account_manage_query_info:
				intent.setClass(this, BussinessInfoActivity.class);
				startActivity(intent);
				break;
			// 开户信息修改
			case R.id.btn_menu_account_manage_bank_info_update:
				if (!attStr.equals("0")) {
					Toast.makeText(getApplicationContext(), "用户权限！请补全资料审核通过后重试",
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(activity, "用户权限！请补全资料审核通过后重试");
				}
				break;
			// 实名认证
			case R.id.btn_menu_account_manage_real_name_authentication:
				if (attStr.equals("0")) {
					ToastCustom.showMessage(AccountManageActivity.this,
							"实名认证已通过", Toast.LENGTH_SHORT);

				}else if(tagsts.equals("1")){
					ToastCustom.showMessage(AccountManageActivity.this,
							"资料正在审核中，请勿重复操作", Toast.LENGTH_SHORT);
				} else {
//					if(((AppContext)getApplication()).getSts().equals("1")){
//						ToastCustom.showMessage(AccountManageActivity.this,
//								"用户信息正在审核中", Toast.LENGTH_SHORT);
//					}else{
						intent.setClass(this, NewRealNameAuthenticationActivity.class);
						startActivity(intent);
//					}

				}
				break;
			// 修改登录密码
			case R.id.btn_menu_account_manage_update_login_password:
				if (attStr==null||attStr.equals("")) {
					Toast.makeText(getApplicationContext(), "请先登录",
							Toast.LENGTH_SHORT).show();
					finish();
					return;
				}
				if (attStr.equals("0")) {
					intent.setClass(this, RevisePasswordActivity.class);
					startActivity(intent);
				} else {
//					ToastCustom.showMessage(activity, "用户权限！请补全资料审核通过后重试");
					Toast.makeText(getApplicationContext(), "用户权限！请补全资料审核通过后重试",
							Toast.LENGTH_SHORT).show();
				}

				break;
			// 手机号码更改申请
			case R.id.btn_menu_account_manage_update_dealer_data:
				if(tagsts.equals("3")){
					Toast.makeText(getApplicationContext(), "银行卡和手机号不可同时修改，待审核通过重试",
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(AccountManageActivity.this, "银行卡和手机号不可同时修改，待审核通过重试");
					return;
				}
				
				 doubleWarnDialog = new TwoButtonDialogStyle2(AccountManageActivity.this,
							R.style.CustomDialog, "提示", new SpannableString("手机号码变更申请需要审核,未审核前不可进行出款操作，请知悉"), "确定", "取消",
							new OnMyDialogClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									switch (v.getId()) {
									case R.id.btn_left:
										doubleWarnDialog.dismiss();

										break;
									case R.id.btn_right:
										intent.setClass(AccountManageActivity.this, RequestPhonenumberChengesActivity.class);
										startActivity(intent);
										finish();
										doubleWarnDialog.dismiss();
										break;
									default:
										break;
									}
								}
							});
					doubleWarnDialog.setCancelable(true);
					doubleWarnDialog.show();
				
				break;
			// 银行卡信息变更申请
			case R.id.btn_menu_bank_info_update_request:
				if(tagsts.equals("4")){
					Toast.makeText(getApplicationContext(), "银行卡和手机号不可同时修改，待审核通过重试",
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(AccountManageActivity.this, "银行卡和手机号不可同时修改，待审核通过重试");
					return;
				}
				
				 doubleWarnDialog = new TwoButtonDialogStyle2(AccountManageActivity.this,
							R.style.CustomDialog, "提示", new SpannableString("银行卡信息变更申请需要审核,未审核前不允许使用提现功能，请知悉"), "确定", "取消",
							new OnMyDialogClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									switch (v.getId()) {
									case R.id.btn_left:
										doubleWarnDialog.dismiss();
										break;
									case R.id.btn_right:
										intent.setClass(AccountManageActivity.this, RequestCardInfoChangeActivity.class);
										startActivity(intent);
										finish();
										doubleWarnDialog.dismiss();
										
										break;
									default:
										break;
									}
								}
							});
					doubleWarnDialog.setCancelable(true);
					doubleWarnDialog.show();
				
				break;
			case R.id.btn_touch_password:
				
				if (attStr.equals("0")) {
					intent.setClass(this, SetUnlockPasswordActivity.class);
					intent.putExtra("refresh", "refresh");
					startActivity(intent);
					finish();

				} else {
					Toast.makeText(getApplicationContext(), "未设手势密码",
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(activity, "未设手势密码");
				}
			
				break;
//			case R.id.p_out:
				
//
//				SpannableString msp = new SpannableString("您确定要退出当前账户?");
//				showDoubleWarnDialog(msp);
//				break;
			case R.id.btn_uppay_password:
				if (attStr==null||attStr.equals("")) {
					Toast.makeText(getApplicationContext(), "请先登录",
							Toast.LENGTH_SHORT).show();
					finish();
					return;
				}
				if (attStr.equals("0")) {
					intent.setClass(AccountManageActivity.this,
							UpdatePayPassActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), "用户权限！请补全资料审核通过后重试",
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(activity, "用户权限！请补全资料审核通过后重试");
				}

				break;
			case R.id.btn_reguppay_password:
				if (attStr==null||attStr.equals("")) {
					Toast.makeText(getApplicationContext(), "请先登录",
							Toast.LENGTH_SHORT).show();
					
					finish();
					return;
				}
				if (attStr.equals("0")) {
					intent.setClass(AccountManageActivity.this,
							RegetPayPwActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), "用户权限！请补全资料审核通过后重试",
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(activity, "用户权限！请补全资料审核通过后重试");
				}
				break;
				
			case R.id.btn_touch_bankphone:
				intent.setClass(AccountManageActivity.this,
						PayMainActivity.class);
				intent.putExtra("tag", "0");
				startActivity(intent);
				break;
			case R.id.btn_unbindkard:
				intent.setClass(AccountManageActivity.this,
						PayMainActivity.class);
				intent.putExtra("tag", "1");
				startActivity(intent);
				break;
			default:
				break;
			}

		}
	}
	
//	@Override
//	protected void doubleWarnOnClick(View v) {
//		// TODO Auto-generated method stub
//		super.doubleWarnOnClick(v);
//		switch (v.getId()) {
//		case R.id.btn_left:
//			((AppContext) getApplication()).setCustId(null); // 商户ID赋为空
//			((AppContext) getApplication()).setPsamId(null);
//			((AppContext) getApplication()).setMacKey(null);
//			((AppContext) getApplication()).setPinKey(null);
//			((AppContext) getApplication()).setMerSts(null);
//			((AppContext) getApplication()).setMobile(null);
//			((AppContext) getApplication()).setEncryPtkey(null);
//			((AppContext) getApplication()).setStatus(null);
//			((AppContext) getApplication()).setCustPass(null);
//			((AppContext) getApplication()).setVersionSerial(null);
//			doubleWarnDialog.dismiss();
//			AppContext.getInstance().exit();
//			
//			Intent it = new Intent(AccountManageActivity.this,UserActivity.class);
//			
//			startActivity(it);
//			break;
//		case R.id.btn_right:
//			doubleWarnDialog.dismiss();
//			break;
//
//		default:
//			break;
//		}
//	}
}
