package com.td.qianhai.epay.bb;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.td.qianhai.epay.bb.WeChatRegistrActivity.LoginTask;
import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.Entity;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;
import com.td.qianhai.epay.bb.views.ToastCustom;
import com.td.qianhai.epay.bb.views.dialog.OneButtonDialogWarn;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnMyDialogClickListener;
import com.td.qianhai.epay.utils.Checkingroutine;
import com.td.qianhai.epay.utils.Common;
import com.td.qianhai.fragmentmanager.FmMainActivity;

public class WeChatRegistrActivity extends BaseActivity{
	
	private EditText et_register_mobile,et_register_login_passwd,et_register_verif_code;
	private ProgressBar load;
	private ImageView load_img;
	private LinearLayout lin_code;
	private Button btn_register_submit,btn_register_get_verif_code;
	private Editor editor;
	private String mobile;
	private  String trmtyp = "2",termina = "",unionid = "",openid = "";
	private OneButtonDialogWarn warnDialog;
	
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wechat_register);
		AppContext.getInstance().addActivity(this);
		editor = MyCacheUtil.setshared(WeChatRegistrActivity.this);
		unionid = MyCacheUtil.getshared(this).getString("UNIONID", "");
		openid = MyCacheUtil.getshared(this).getString("OPENID", "");
		Log.e("", "unionid = =  "+unionid);
		Log.e("", "OPENID = =  "+openid);
		
	
		initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.tv_title_contre)).setText("绑定手机");
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		btn_register_get_verif_code = (Button) findViewById(R.id.btn_register_get_verif_code);
		et_register_login_passwd =  (EditText) findViewById(R.id.et_register_login_passwd);
		et_register_verif_code = (EditText) findViewById(R.id.et_register_verif_code);
		et_register_mobile = (EditText) findViewById(R.id.et_register_mobile);
		load = (ProgressBar) findViewById(R.id.load_phonr_pro);
		load_img = (ImageView) findViewById(R.id.load_phonr_img);
		lin_code = (LinearLayout) findViewById(R.id.lin_code);
		btn_register_submit = (Button) findViewById(R.id.btn_register_submit);
		btn_register_get_verif_code.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getVerifCode();
				
			}
		});
		btn_register_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(lin_code.getVisibility()==View.GONE){
					Alreadyregistered();
				}else{
					unregistered();
				}
			}
		});
		
		et_register_mobile.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(s.length()==11){
					String  custId = et_register_mobile.getText().toString();
					final BussinessInfoTask task = new BussinessInfoTask();
					task.execute(HttpUrls.BUSSINESS_INFO + "", custId, "4",
							"1");
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
	
	private void Alreadyregistered() {
		// TODO Auto-generated method stub
		mobile = et_register_mobile.getText().toString();
		if (!isMobileNO(mobile)) {
			ToastCustom.showMessage(getApplicationContext(), "手机号码格式有误", Toast.LENGTH_SHORT);
			return;
		}
		if (mobile == null || (mobile != null && mobile.equals(""))) {
			ToastCustom.showMessage(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT);
			return;
		} else if (!Checkingroutine.isNumeric(mobile)) {
			ToastCustom.showMessage(getApplicationContext(), "手机号码只能输入数字", Toast.LENGTH_SHORT);
			return;
		} else if (mobile.length() != 11) {
			ToastCustom.showMessage(getApplicationContext(), "手机号码必须为11位数字", Toast.LENGTH_SHORT);
			return;
		}

		String loginPasswd = et_register_login_passwd.getText().toString();
		if (loginPasswd == null
				|| (loginPasswd != null && loginPasswd.equals(""))) {
			ToastCustom.showMessage(getApplicationContext(), "请输入登录密码", Toast.LENGTH_SHORT);
			return;
		} else if (loginPasswd.length() < 6) {
			ToastCustom.showMessage(getApplicationContext(), "密码长度必须为6-15位", Toast.LENGTH_SHORT);
			return;
		}
		
		String mermod = "3";
		String trmtyp = "2";
		WechtRegisterTask task = new WechtRegisterTask();
		task.execute(HttpUrls.BINDPHONENUM+"",mobile, loginPasswd,openid,unionid);
		
	}
	
	/**
	 * 验证手机合法性
	 * 
	 * @param email
	 * @return
	 */
	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(17[0-9])|(14[0-9])|(15[^4,\\D])|(18[0,1-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	private void unregistered() {
		// TODO Auto-generated method stub
		mobile = et_register_mobile.getText().toString();
		if (!isMobileNO(mobile)) {
			ToastCustom.showMessage(getApplicationContext(), "手机号码格式有误", Toast.LENGTH_SHORT);
			return;
		}
		if (mobile == null || (mobile != null && mobile.equals(""))) {
			ToastCustom.showMessage(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT);
			return;
		} else if (!Checkingroutine.isNumeric(mobile)) {
			ToastCustom.showMessage(getApplicationContext(), "手机号码只能输入数字", Toast.LENGTH_SHORT);
			return;
		} else if (mobile.length() != 11) {
			ToastCustom.showMessage(getApplicationContext(), "手机号码必须为11位数字", Toast.LENGTH_SHORT);
			return;
		}

		String loginPasswd = et_register_login_passwd.getText().toString();
		if (loginPasswd == null
				|| (loginPasswd != null && loginPasswd.equals(""))) {
			ToastCustom.showMessage(getApplicationContext(), "请输入登录密码", Toast.LENGTH_SHORT);
			return;
		} else if (loginPasswd.length() < 6) {
			ToastCustom.showMessage(getApplicationContext(), "密码长度必须为6-15位", Toast.LENGTH_SHORT);
			return;
		}


		String verifCode = et_register_verif_code.getText().toString();

		if (verifCode == null || (verifCode != null && verifCode.equals(""))) {
			ToastCustom.showMessage(getApplicationContext(), "请输入手机验证码", Toast.LENGTH_SHORT);
			return;
		}

		String mermod = "3";
		String trmtyp = "2";
		RegisterTask task = new RegisterTask();
		task.execute(mobile, loginPasswd, "", mermod, verifCode, trmtyp,HttpUrls.APPNUM,"","",openid,unionid);
		
	}
	
	
	private void getVerifCode() {


		mobile = et_register_mobile.getText().toString();


		if (!isMobileNO(mobile)) {
			ToastCustom.showMessage(getApplicationContext(), "手机号码格式有误", Toast.LENGTH_SHORT);
			return;
		}
		if (mobile == null || (mobile != null && mobile.equals(""))) {
			ToastCustom.showMessage(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT);
			return;
		} else if (!Checkingroutine.isNumeric(mobile)) {
			ToastCustom.showMessage(getApplicationContext(), "手机号码只能输入数字", Toast.LENGTH_SHORT);
			return;
		} else if (mobile.length() != 11) {
			ToastCustom.showMessage(getApplicationContext(), "手机号码必须为11位数字", Toast.LENGTH_SHORT);
			return;
		}

		String loginPasswd = et_register_login_passwd.getText().toString();
		if (loginPasswd == null
				|| (loginPasswd != null && loginPasswd.equals(""))) {
			ToastCustom.showMessage(getApplicationContext(), "请输入登录密码", Toast.LENGTH_SHORT);
			return;
		} else if (loginPasswd.length() != 6) {
			// ToastCustom.showMessage(getApplicationContext(), "密码长度必须为6位", Toast.LENGTH_SHORT);
			// return;
		}

		btn_register_get_verif_code.setEnabled(false);
		
		final String[] values = { "199018", mobile, "100001", termina, trmtyp,HttpUrls.APPNUM};

		new Thread(new Runnable() {

			@Override
			public void run() {

				HashMap<String, Object> result = NetCommunicate.get(
						HttpUrls.REGISTER_VERIFCODE, values);
				Message msg = new Message();
				if (result == null) {
					msg.what = 10;
					msg.obj = result;
				} else {
					msg.what = 11;
					msg.obj = result;
				}
				mHandler.sendMessage(msg);
			}
		}).start();
		// Common.
	}
	
	class BussinessInfoTask extends
			AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {
			load.setVisibility(View.VISIBLE);
		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			String[] values = { params[0], params[1], params[2], params[3] };
			return NetCommunicate.get(HttpUrls.BUSSINESS_INFO, values);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			if (result != null) {

				if (result.get("RSPCOD").toString().equals("000000")) {
					lin_code.setVisibility(View.VISIBLE);
					load.setVisibility(View.GONE);
					load_img.setVisibility(View.VISIBLE);
				} else {
					lin_code.setVisibility(View.GONE);
					
					load.setVisibility(View.GONE);
					load_img.setVisibility(View.GONE);
				}
				super.onPostExecute(result);
			}
		}
	}
	
	
	class WechtRegisterTask extends
			AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoadingDialog("正在绑定中...");
		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			String[] values = { params[0], params[1], params[2], params[3],params[4] };
			return NetCommunicate.get(HttpUrls.BINDPHONENUM, values);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			loadingDialogWhole.dismiss();
			if (result != null) {
				ToastCustom.showMessage(WeChatRegistrActivity.this,
						result.get(Entity.RSPMSG).toString(),
						Toast.LENGTH_SHORT);
				// 注册成功
				if (result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {

					LoginTask task = new LoginTask();
					// task.execute("199002", "13917830795","111111",
					// "18917114556");
					String pcsim = "11111111";
					String aa = JPushInterface
							.getRegistrationID(WeChatRegistrActivity.this);
					String idd = MyCacheUtil.getshared(
							WeChatRegistrActivity.this).getString("IDD", "");
					String passWord = et_register_login_passwd.getText()
							.toString();
					// PushManager.startWork(context, userName,
					// "Q8htwUGPqpfpU5uFo4zGdp4C");
					editor.putString("pwd", passWord);
					editor.commit();
					task.execute("199002", mobile, passWord, pcsim, "", "2",
							"", "", "1", HttpUrls.APPNUM, "", idd, aa + "",unionid,
							openid, "", "");
				}
			} else {
				ToastCustom.showMessage(WeChatRegistrActivity.this, "fail",
						Toast.LENGTH_SHORT);
			}
			super.onPostExecute(result);
		}
	}
	class RegisterTask extends
			AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoadingDialog("正在注册中...");
		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			String[] values = { params[0], params[1], params[2], params[3],
					params[4], params[5], params[6], params[7], params[8], params[9], params[10] };
			return NetCommunicate.get(HttpUrls.REGISTER, values);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			loadingDialogWhole.dismiss();
			if (result != null) {
				ToastCustom.showMessage(WeChatRegistrActivity.this,
						result.get(Entity.RSPMSG).toString(),
						Toast.LENGTH_SHORT);
				// 注册成功
				if (result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {
					
					LoginTask task = new LoginTask();
					// task.execute("199002", "13917830795","111111",
					// "18917114556");
					String pcsim = "11111111";
					String aa = JPushInterface.getRegistrationID(WeChatRegistrActivity.this);
					String idd = MyCacheUtil.getshared(WeChatRegistrActivity.this).getString("IDD", "");
					String  passWord = et_register_login_passwd.getText().toString();
					editor.putString("pwd", passWord);
					editor.commit();
//					PushManager.startWork(context, userName, "Q8htwUGPqpfpU5uFo4zGdp4C");
					task.execute("199002", mobile, passWord, pcsim, "", "2","","","1",HttpUrls.APPNUM,"",idd,aa+"",unionid,
							openid,"","");
				}
			} else {
				ToastCustom.showMessage(WeChatRegistrActivity.this, "fail",
						Toast.LENGTH_SHORT);
			}
			super.onPostExecute(result);
		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 10:
				ToastCustom.showMessage(getApplicationContext(), "获取验证码失败",
						Toast.LENGTH_SHORT);
				btn_register_get_verif_code.setEnabled(true);
				break;
			case 11:
				@SuppressWarnings("unchecked")
				// HashMap<String, Object> map = (HashMap<String, Object>)
				// msg.obj;
				// ToastCustom.showMessage(getApplicationContext(),
				// map.get("RSPMSG").toString(), Toast.LENGTH_SHORT);
				HashMap<String, Object> map = (HashMap<String, Object>) msg.obj;
				Toast.makeText(getApplicationContext(),map.get("RSPMSG").toString(),
						Toast.LENGTH_SHORT).show();
//				ToastCustom.showMessage(getApplicationContext(),
//						map.get("RSPMSG").toString());
				if (map.get("RSPCOD").toString().equals("000000")) {
					Common.timing(btn_register_get_verif_code);
				} else if (map.get("RSPCOD").toString().equals("400002")) {
					btn_register_get_verif_code.setEnabled(true);
				} else if (map.get("RSPCOD").toString().equals("099999")) {
					btn_register_get_verif_code.setEnabled(true);
				}else{
					btn_register_get_verif_code.setEnabled(true);
				}


				break;
			case 6:
				break;
			default:
				break;
			}
		};
	};
	
	/**
	 * 登录异步类
	 * 
	 * @author liangge
	 * 
	 */
	class LoginTask extends AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			showLoadingDialog("正在努力加载...");
		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			String[] values = { params[0], params[1], params[2], params[3],
					params[4], params[5],params[6] ,params[7],params[8],params[9],params[10],params[11],params[12],params[13],params[14],params[15],params[16]};
			return NetCommunicate.get(HttpUrls.LOGIN, values);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			loadingDialogWhole.dismiss();
			final HashMap<String, Object> results = result;
			Intent intent ;
			if (result != null) {
//				Log.e("", "登录返回值 = = =" + result);
				
				if (result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {
					
//					if(MainActivity.cache==null){
//						MainActivity.cache = new MyCacheUtil(WeChatRegistrActivity.this);
//					}
					if(result.get("HEADIMGURL")!=null){
					editor.putString("HEADIMGURL",results.get("HEADIMGURL").toString());
					}
					if(result.get("NICKNAME")!=null){
					editor.putString("NICKNAME",results.get("NICKNAME").toString());
					}
					if(result.get("NOTICEMESSAGE")!=null){
						editor.putString("NOTICEMESSAGE", result.get("NOTICEMESSAGE").toString());
					}else{
						
					}
					if(result.get("AGENTID")!=null){
					editor.putString("AGENTID",results.get("AGENTID").toString());
					}
					
					if(result.get("ISAREAAGENT")!=null){
						editor.putString("ISAREAAGENT", result.get("ISAREAAGENT").toString());
					}
					if(result.get("NCONTENT")!=null){
						editor.putString("NCONTENT", result.get("NCONTENT").toString());
						editor.putString("NCREATETIM", result.get("NCREATETIM").toString());
					}else{
						editor.putString("NCONTENT", "");
						editor.putString("NCREATETIM", "");
					}
					
					if(result.get("NOCARDFEERATE")!=null){
						((AppContext) WeChatRegistrActivity.this.getApplication())
						.setNocein(result.get("NOCARDFEERATE").toString());
						
						editor.putString("nocardfeerate", result.get("NOCARDFEERATE").toString());
						editor.putString("oemfeerate", result.get("OEMFEERATE").toString());
						editor.commit();
					}else{
						editor.putString("nocardfeerate", "0.69");
						
						editor.commit();
					}
					if(result.get("IDCARDPICSTA")!=null&&result.get("CUSTPICSTA")!=null&&result.get("FRYHKIMGPATHSTA")!=null){
						String a = result.get("IDCARDPICSTA").toString();
						String b = result.get("CUSTPICSTA").toString();
						String c = result.get("FRYHKIMGPATHSTA").toString();
						((AppContext) getApplication()).setStateaudit(a+b+c);
					}else{
						
					}
					if(result.get("PERSONPIC")!=null){
						editor.putString("PERSONPIC", result.get("PERSONPIC").toString());
					}
					editor.putString("pwd", et_register_login_passwd.getText().toString());
					
					if(result.get("LOEMID")!=null){
						editor.putString("OEMID", result.get("LOEMID").toString());
					}
					if(result.get("ISRETAILERS")!=null){
						editor.putString("ISRETAILERS", result.get("ISRETAILERS").toString());
					}
					if(result.get("ISSALEAGT")!=null){
						editor.putString("ISSALEAGT", result.get("ISSALEAGT").toString());
					}
					if(result.get("ISGENERALAGENT")!=null){
						editor.putString("ISGENERALAGENT", result.get("ISGENERALAGENT").toString());
					}
					if(result.get("ISSENIORMEMBER")!=null){
						editor.putString("ISSENIORMEMBER", result.get("ISSENIORMEMBER").toString());
					}
					if(result.get("TXNSTS")!=null){
						editor.putString("Txnsts", result.get("TXNSTS").toString());
					}
					if(result.get("MERCNUM")!=null){
						editor.putString("MercNum",result.get("MERCNUM").toString());
					}
					if(result.get("STS")!=null){
						editor.putString("STS",result.get("STS").toString());
					}
					if(result.get("MERSTS")!=null){
						editor.putString("MERSTS",result.get("MERSTS").toString());
					}
					
					if(result.get("PHONENUMBER")!=null){
						editor.putString("userp",result.get("PHONENUMBER").toString());
						editor.putString("CustId", result.get("PHONENUMBER").toString());
						
						editor.putString("Mobile", result.get("PHONENUMBER").toString());
					
//						((AppContext) getApplicationContext()).setCustId(result
//								.get("PHONENUMBER").toString());
////						((AppContext) getApplicationContext()).setMobile(result
////								.get("PHONENUMBER").toString());
//						((AppContext) getApplicationContext()).setMerSts(result
//								.get("MERSTS").toString());
//						((AppContext) getApplicationContext())
//								.setCustPass(passWord);
//						((AppContext) getApplicationContext()).setTxnsts(result
//								.get("TXNSTS").toString());
//						((AppContext) getApplicationContext()).setSts(result
//								.get("STS").toString());
//						((AppContext) WeChatRegistrActivity.this.getApplication())
//								.setMercNum(result.get("MERCNUM").toString());
					}
					editor.commit();
					

					
					if(results.get("MERSTS")!=null&&results.get("MERSTS").toString().equals("0")){
						if(results.get("CURROL")!=null){
							((AppContext) getApplicationContext()).setCurrol(results.get("CURROL").toString());
							editor.putString("CURROL",results.get("CURROL").toString());
							
							editor.commit();
//							if(results.get("CURROL").toString().equals("0")){
//
//							}else if(results.get("CURROL")!=null){
//								editor.putString("AGENTID",results.get("AGENTID").toString());
//								
//								editor.commit();
//								((AppContext) getApplicationContext()).setAgentid(results.get("AGENTID").toString());
//							}
						}


						
							intent = new Intent(WeChatRegistrActivity.this,
									FmMainActivity.class);
							
							String lognum = null;
							if(result.get("LOGNUM")!=null){
								lognum = result.get("LOGNUM").toString();
							}

					
					
						if (lognum != null && lognum.equals("0")) {
							editor.putString("usermobile","");
							editor.putString("userpwd", "");
							editor.commit();
							Intent it = new Intent(WeChatRegistrActivity.this,
									SetPayPassActivity.class);
							startActivity(it);
							Toast.makeText(getApplicationContext(),"请设置支付密码",
									Toast.LENGTH_SHORT).show();
//							ToastCustom.showMessage(WeChatRegistrActivity.this, "请设置支付密码!");
							finish();
							
						} else {
							
//							startActivity(intent);
							finish();
//							ToastCustom.showMessage(WeChatRegistrActivity.this, "欢迎登录移动支付");
						}
					}else{
						if (results != null) {
//							((AppContext) getApplicationContext()).setSts(result
//									.get("STS").toString());
//							((AppContext) getApplicationContext()).setTxnsts(result
//									.get("TXNSTS").toString());
//							if(result.get("NOCARDFEERATE")!=null){
//								((AppContext) WeChatRegistrActivity.this.getApplication())
//								.setNocein(result.get("NOCARDFEERATE").toString());
//							}

//							if (results.get("PHONENUMBER") != null) {
//								editor.putString("oemfeerate", result.get("OEMFEERATE").toString());
//								editor.putString("Txnsts", result.get("TXNSTS").toString());
//								editor.putString("CustId", result.get("PHONENUMBER").toString());
//								editor.putString("Mobile", result.get("PHONENUMBER").toString());
//								editor.putString("STS",result.get("STS").toString());
//								editor.putString("MERSTS",result.get("MERSTS").toString());
//								if(results.get("CURROL")!=null){
//									editor.putString("CURROL",results.get("CURROL").toString());
//								}
//								editor.commit();
//								((AppContext) getApplicationContext())
//										.setCustId(results.get("PHONENUMBER").toString());
//								((AppContext) getApplicationContext())
//								.setCustPass(passWord);
////								((AppContext) getApplicationContext())
////										.setMobile(results.get("PHONENUMBER").toString());
//							}
//
//							if (results.get("MERCNUM") != null) {
//								editor.putString("MercNum",result.get("MERCNUM").toString());
//								editor.commit();
//								((AppContext) WeChatRegistrActivity.this.getApplication())
//								.setMercNum(results.get("MERCNUM").toString());
//							}


//						if (results.get("MERSTS") != null) {
//							((AppContext) getApplicationContext())
//									.setMerSts(results.get("MERSTS").toString());
							
							Intent intent1 = new Intent(WeChatRegistrActivity.this,
									FmMainActivity.class);
//							editor.putString("MERSTS",result.get("MERSTS").toString());
//							editor.putString("usermobile",
//									"");
//							editor.putString("userpwd", "");
//							editor.commit();
//							startActivity(intent1);
							finish();
//						}else{
//							warnDialog = new OneButtonDialogWarn(WeChatRegistrActivity.this,
//									R.style.CustomDialog, "提示", result.get(
//											Entity.RSPMSG).toString(), "确定",
//									new OnMyDialogClickListener() {
//										@Override
//										public void onClick(View v) {
//											// TODO Auto-generated method stub
//
//											warnDialog.dismiss();
//										}
//									});
//
//							warnDialog.show();
//						}
						}
					}
	

				} else {
					warnDialog = new OneButtonDialogWarn(WeChatRegistrActivity.this,
							R.style.CustomDialog, "提示", result.get(
									Entity.RSPMSG).toString(), "确定",
							new OnMyDialogClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									warnDialog.dismiss();
								}
							});

					warnDialog.show();
				}

			} else {
				warnDialog = new OneButtonDialogWarn(WeChatRegistrActivity.this,
						R.style.CustomDialog, "提示", "请求服务器失败！！！", "确定",
						new OnMyDialogClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								warnDialog.dismiss();
							}
						});
				warnDialog.show();
			}

			// Intent intent = new Intent(WeChatRegistrActivity.this,
			// MenuActivity.class);
			// startActivity(intent);
			// finish();
			super.onPostExecute(result);

		}
	}
}
