package com.td.qianhai.epay.bb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.Entity;
import com.td.qianhai.epay.bb.beans.HttpKeys;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;
import com.td.qianhai.epay.bb.unlock.StringUtil;
import com.td.qianhai.epay.bb.unlock.UnlockLoginActivity;
import com.td.qianhai.epay.bb.views.AnimationUtil;
import com.td.qianhai.epay.bb.views.dialog.OneButtonDialogWarn;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnMyDialogClickListener;
import com.td.qianhai.epay.utils.AppManager;
import com.td.qianhai.epay.utils.ChineseUtil;
import com.td.qianhai.fragmentmanager.FmMainActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class UserActivity extends BaseActivity implements OnClickListener {

	/* 提示,登录 */
	private LinearLayout level_t, level_b;
	private EditText et_user, et_pass;
	private Button  btn_login;
	private TextView btn_register;
	private OneButtonDialogWarn warnDialog;
	private String userName, passWord;
	private TextView tvRegetPass,e_user_del;
	/** 是否第一次启用致富宝 */
	private String lognum;
	private CheckBox e_pwd;
	public static  Activity context;
	private String appid,userid;
	private ImageView qh_logo_img;
	private Editor editor;
	private SharedPreferences share;
	private  UMSocialService mController;
	private TextView  bt_title_left,tv_wecaht;
	
	
	

	private void initView() {
		e_pwd = (CheckBox) findViewById(R.id.e_pwd);
		level_t = (LinearLayout) findViewById(R.id.level_t);
		level_b = (LinearLayout) findViewById(R.id.level_b);
		qh_logo_img = (ImageView) findViewById(R.id.qh_logo_imgs);
		btn_register = (TextView) findViewById(R.id.btn_register);
		btn_login = (Button) findViewById(R.id.btn_login);
		tvRegetPass = (TextView) findViewById(R.id.account_reget_pass);
		bt_title_left = (TextView) findViewById(R.id.bt_title_left);
		tv_wecaht = (TextView) findViewById(R.id.tv_wecaht);
		tv_wecaht.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent it = new Intent(UserActivity.this,UserActivity.class);
//				startActivity(it);
				addWXPlatform();
			}
		});
		bt_title_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
//		tvRegetPass.setText(Html.fromHtml("<u>忘 记 密 码?</u>"));
		e_pwd.setOnClickListener(this);
		tvRegetPass.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		e_user_del.setOnClickListener(this);
		
//		AnimationUtil.RoteAnimation(this, qh_logo_img);
		
		et_user.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(s.length()>0){
					e_user_del.setVisibility(View.VISIBLE);
				}else{
					e_user_del.setVisibility(View.GONE);
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
		
		et_user.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1){
					if(et_user.getText().toString().length()>0){
						e_user_del.setVisibility(View.VISIBLE);
					}

				}else{
					e_user_del.setVisibility(View.GONE);
				}
			}
		});
		et_pass.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1){
//					e_pwd.setVisibility(View.VISIBLE);
				}else{
					e_pwd.setVisibility(View.GONE);
				}
			}
		});
		
		
		e_pwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				
				CharSequence text = e_pwd.getText();
				 //Debug.asserts(text instanceof Spannable);

				// TODO Auto-generated method stub
				if(arg1){
					et_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				}else{
					et_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});
	}

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AppContext.getInstance().addActivity(this);
		editor = MyCacheUtil.setshared(UserActivity.this);
		share = MyCacheUtil.getshared(UserActivity.this);
		context = this;
		appid = ((AppContext)getApplication()).getAppid();
		userid = ((AppContext)getApplication()).getUserid();
		settitle();
		
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		e_user_del = (TextView) findViewById(R.id.e_user_del);
		
		et_user = (EditText) findViewById(R.id.reg_account_number);
		et_pass = (EditText) findViewById(R.id.reg_serial);
		String usermobile = share.getString("userp","");
		if(usermobile!=null&&!usermobile.equals("")){
			et_user.setText(usermobile);
			e_user_del.setVisibility(View.VISIBLE);
			et_pass.requestFocus();
		}
		initView();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.btn_register:
			intent.setClass(UserActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_login:
			System.out.println();
			if (level_b.getVisibility() == View.GONE) {
				level_t.setVisibility(View.GONE);
				level_b.setVisibility(View.VISIBLE);
			} else {
				userName = et_user.getText().toString();
				if (userName == null || userName.equals("")) {
					Toast.makeText(getApplicationContext(),"手机号码不能为空",
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(UserActivity.this, "手机号码不能为空...");
					return;
				} else if (userName.length() != 11) {
					Toast.makeText(getApplicationContext(),"手机号码长度有误",
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(UserActivity.this, "手机号码长度有误...");
					return;
				}
				passWord = et_pass.getText().toString();
				if (passWord == null || passWord.equals("")) {
					Toast.makeText(getApplicationContext(),"密码不能为空",
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(UserActivity.this, "密码不能为空...");
					return;
				} else if (passWord.length() < 6) {
					Toast.makeText(getApplicationContext(),"密码长度有误",
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(UserActivity.this, "密码长度有误...");
					return;
				}
				
				if(ChineseUtil.checkNameChese(passWord)){
					Toast.makeText(getApplicationContext(),"密码不能为中文",
							Toast.LENGTH_SHORT).show();
//					ToastCustom.showMessage(UserActivity.this, "密码不能为中文");
					return;
				}
				

				LoginTask task = new LoginTask();
				// task.execute("199002", "13917830795","111111",
				// "18917114556");
				String pcsim = "11111111";
				String aa = JPushInterface.getRegistrationID(this);
				String idd = MyCacheUtil.getshared(this).getString("IDD", "");
//				PushManager.startWork(context, userName, "Q8htwUGPqpfpU5uFo4zGdp4C");
				task.execute("199002", userName, passWord, pcsim, "", "2","","","1",HttpUrls.APPNUM,"",idd,aa+"","","","","");
			}
			break;

		case R.id.account_reget_pass:
			intent = new Intent(UserActivity.this, RegetPwActivity.class);
			startActivity(intent);
			break;
		case R.id.e_user_del:
			if(et_user.getText().toString().length()>0){
				et_user.requestFocus();
				et_user.setText("");
			}
			
			break;
		default:
			break;
		}
	}

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
//						MainActivity.cache = new MyCacheUtil(UserActivity.this);
//					}
					
					if(result.get("UNIONID")!=null){
						Log.e("", "oUNIONID");
						editor.putString("UNIONID",results.get("UNIONID").toString());
					}
					if(result.get("OPENID")!=null){
						editor.putString("OPENID",results.get("OPENID").toString());
						Log.e("", "OPENID");
					}
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
						((AppContext) UserActivity.this.getApplication())
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
					editor.putString("pwd", passWord);
					
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
//						((AppContext) UserActivity.this.getApplication())
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
						

							if(FmMainActivity.context!=null){
								FmMainActivity.context.finish();
							}
							intent = new Intent(UserActivity.this,
									FmMainActivity.class);
							
							
							if(result.get("LOGNUM")!=null){
								lognum = result.get("LOGNUM").toString();
							}

					

						if (lognum != null && lognum.equals("0")) {
							editor.putString("usermobile","");
							editor.putString("userpwd", "");
							editor.commit();
							Intent it = new Intent(UserActivity.this,
									SetPayPassActivity.class);
							startActivity(it);
							Toast.makeText(getApplicationContext(),"请设置支付密码",
									Toast.LENGTH_SHORT).show();
//							ToastCustom.showMessage(UserActivity.this, "请设置支付密码!");
							finish();
							
						} else {
							
							startActivity(intent);
							finish();
//							ToastCustom.showMessage(UserActivity.this, "欢迎登录移动支付");
						}
					}else{
						if (results != null) {
//							((AppContext) getApplicationContext()).setSts(result
//									.get("STS").toString());
//							((AppContext) getApplicationContext()).setTxnsts(result
//									.get("TXNSTS").toString());
//							if(result.get("NOCARDFEERATE")!=null){
//								((AppContext) UserActivity.this.getApplication())
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
//								((AppContext) UserActivity.this.getApplication())
//								.setMercNum(results.get("MERCNUM").toString());
//							}


//						if (results.get("MERSTS") != null) {
//							((AppContext) getApplicationContext())
//									.setMerSts(results.get("MERSTS").toString());
							
							Intent intent1 = new Intent(UserActivity.this,
									FmMainActivity.class);
//							editor.putString("MERSTS",result.get("MERSTS").toString());
//							editor.putString("usermobile",
//									"");
//							editor.putString("userpwd", "");
//							editor.commit();
							startActivity(intent1);
							finish();
//						}else{
//							warnDialog = new OneButtonDialogWarn(UserActivity.this,
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
					warnDialog = new OneButtonDialogWarn(UserActivity.this,
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
				warnDialog = new OneButtonDialogWarn(UserActivity.this,
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

			// Intent intent = new Intent(UserActivity.this,
			// MenuActivity.class);
			// startActivity(intent);
			// finish();
			super.onPostExecute(result);

		}
	}
	


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			SpannableString msp = new SpannableString("您确定要退出应用?");
			showDoubleWarnDialog(msp);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void doubleWarnOnClick(View v) {
		// TODO Auto-generated method stub
		super.doubleWarnOnClick(v);
		switch (v.getId()) {
		case R.id.btn_left:

			doubleWarnDialog.dismiss();
			
			break;
		case R.id.btn_right:
			((AppContext) getApplication()).setCustId(null); // 商户ID赋为空
			((AppContext) getApplication()).setPsamId(null);
			((AppContext) getApplication()).setMacKey(null);
			((AppContext) getApplication()).setPinKey(null);
			((AppContext) getApplication()).setMerSts(null);
//			((AppContext) getApplication()).setMobile(null);
			((AppContext) getApplication()).setEncryPtkey(null);
			((AppContext) getApplication()).setStatus(null);
			((AppContext) getApplication()).setCustPass(null);
			((AppContext) getApplication()).setVersionSerial(null);
			AppContext.getInstance().setStateaudit(null);
			AppManager.getAppManager().AppExit(UserActivity.this);
			AppContext.getInstance().exit();
			FmMainActivity.context.finish();
			doubleWarnDialog.dismiss();
			break;

		default:
			break;
		}
	}
	
	/**
     * @功能描述 : 添加微信平台授权登录
     * @return
     */
    private void addWXPlatform() {
        // 注意：在微信授权的时候，必须传递appSecret
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        // 添加微信平台，APP_ID、APP_SECRET都是在微信开放平台，移动应用通过审核后获取到的
        UMWXHandler wxHandler = new UMWXHandler(UserActivity.this, "wx49699f2e27560127", "f4849377be84176abe3d88cb78043a51");
        wxHandler.setRefreshTokenAvailable(false);
        wxHandler.addToSocialSDK();
        login(SHARE_MEDIA.WEIXIN);
 
    }
    /**
     * 授权。如果授权成功，则获取用户信息
     *
     * @param platform
     */
    private void login(final SHARE_MEDIA platform) {
        mController.doOauthVerify(UserActivity.this, platform,
                new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                    	showLoadingDialog("请稍后...");
//                        Toast.makeText(UserActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
                    	loadingDialogWhole.dismiss();
//                        Toast.makeText(UserActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
                        // 获取uid
                        String uid = value.getString("uid");
                        
                        if (!TextUtils.isEmpty(uid)) {
                            // uid不为空，获取用户信息
                            getUserInfo(platform);
                            loadingDialogWhole.dismiss();
//                            Toast.makeText(UserActivity.this,"uid is - -  "+uid, Toast.LENGTH_LONG).show();
                        } else {
                        	logout(platform);
                        	loadingDialogWhole.dismiss();
//                            Toast.makeText(UserActivity.this, "授权失败...", Toast.LENGTH_LONG).show();
                        }
                    }
 
                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
//                    	logout(platform);
//                        Toast.makeText(UserActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
                    }
                });
    }
 
    /**
     * 获取用户信息
     *
     * @param platform
     */
    private void getUserInfo(SHARE_MEDIA platform) {
        mController.getPlatformInfo(UserActivity.this, platform,
                new SocializeListeners.UMDataListener() {
 
                    @Override
                    public void onStart() {
 
                    }
                    
                    
					@Override
					public void onComplete(int status, Map<String, Object> info) {
						// TODO Auto-generated method stub
//                         String showText = "";
//                         if (status == StatusCode.ST_CODE_SUCCESSED) {
//                         showText = "用户名：" +
//                         info.get("screen_name").toString();
////                         Log.d("#########", "##########" + info.toString());
//                         } else {
//                         showText = "获取用户信息失败";
//                         }
//                         tv_1.setText(showText);
 
                        if (info != null) {
                        	
                        	String unionid = info.get("unionid").toString();
                        	String openid = info.get("openid").toString();
                        	String headimgurl = info.get("headimgurl").toString();
                        	String nickname = info.get("nickname").toString();
                        	
                        	
            				LoginTask task = new LoginTask();
            				// task.execute("199002", "13917830795","111111",
            				// "18917114556");
            				String pcsim = "11111111";
            				String aa = JPushInterface.getRegistrationID(UserActivity.this);
            				String idd = MyCacheUtil.getshared(UserActivity.this).getString("IDD", "");
//            				PushManager.startWork(context, userName, "Q8htwUGPqpfpU5uFo4zGdp4C");

            				task.execute("199002", "", "", "", "", "2","","","3",HttpUrls.APPNUM,"",idd,aa+"",unionid,openid,headimgurl,nickname);
                        	
                        	
                        	  String infoStr = info.toString();
                            
                        	  
//                            CommonUtils.LogWuwei(tag,"info is "+infoStr);
                        }
					}

					
					
 
					
//                    @Override
//                    public void onComplete(int status, Map<String, Object> info) {
//                        // String showText = "";
//                        // if (status == StatusCode.ST_CODE_SUCCESSED) {
//                        // showText = "用户名：" +
//                        // info.get("screen_name").toString();
//                        // Log.d("#########", "##########" + info.toString());
//                        // } else {
//                        // showText = "获取用户信息失败";
//                        // }
// 
//                        if (info != null) {
//                            Toast.makeText(UserActivity.this, info.toString(), Toast.LENGTH_SHORT).show();
//                            String infoStr = info.toString();
////                            CommonUtils.LogWuwei(tag,"info is "+infoStr);
//                        }
//                    }
                });
    }
 
    /**
     * 注销本次登陆
     * @param platform
     */
    private void logout(final SHARE_MEDIA platform) {
        mController.deleteOauth(UserActivity.this, platform,
                new SocializeListeners.SocializeClientListener() {
 
            @Override
            public void onStart() {
 
            }

			@Override
			public void onComplete(int status, SocializeEntity arg1) {
				// TODO Auto-generated method stub
                String showText = "解除" + platform.toString() + "平台授权成功";
                if (status != StatusCode.ST_CODE_SUCCESSED) {
                    showText = "解除" + platform.toString() + "平台授权失败[" + status + "]";
                }
                Toast.makeText(UserActivity.this, showText, Toast.LENGTH_SHORT).show();
			}
 
//            @Override
//            public void onComplete(int status, SocializeEntity entity) {
//                String showText = "解除" + platform.toString() + "平台授权成功";
//                if (status != StatusCode.ST_CODE_SUCCESSED) {
//                    showText = "解除" + platform.toString() + "平台授权失败[" + status + "]";
//                }
//                Toast.makeText(UserActivity.this, showText, Toast.LENGTH_SHORT).show();
//            }
        });
    }
    
    @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	super.onRestart();
    	if(loadingDialogWhole!=null){
    		loadingDialogWhole.dismiss();
    	}
    }
}
