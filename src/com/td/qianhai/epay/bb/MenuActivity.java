package com.td.qianhai.epay.bb;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.td.qianhai.epay.bb.adapter.VerticalAdapter;
import com.td.qianhai.epay.bb.adapter.VerticalViewPager;
import com.td.qianhai.epay.bb.advertising.AdGallery;
import com.td.qianhai.epay.bb.advertising.AdGalleryHelper;
import com.td.qianhai.epay.bb.advertising.Advertisement;
import com.td.qianhai.epay.bb.advertising.Image3DSwitchView;
import com.td.qianhai.epay.bb.advertising.Image3DView;
import com.td.qianhai.epay.bb.advertising.AdGallery.OnAdItemClickListener;
import com.td.qianhai.epay.bb.advertising.Image3DSwitchView.OnImageSwitchListener;
import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.Entity;
import com.td.qianhai.epay.bb.beans.HttpKeys;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.beans.LevelBean;
import com.td.qianhai.epay.bb.beans.RichTreasureBean;
import com.td.qianhai.epay.bb.mail.utils.DESKey;
import com.td.qianhai.epay.bb.mail.utils.GetImageUtil;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;
import com.td.qianhai.epay.bb.qrcode.CaptureActivity;
import com.td.qianhai.epay.bb.views.ToastCustom;
import com.td.qianhai.epay.bb.views.dialog.OneButtonDialogWarn;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnMyDialogClickListener;
import com.td.qianhai.epay.utils.DateUtil;
import com.td.qianhai.fragmentmanager.FmMainActivity;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * 主菜单页面
 * 
 * @author liangge
 * 
 */
public class MenuActivity extends Fragment implements OnClickListener ,OnAdItemClickListener {
	/** 商户ID、商户认证状态、商户认证状态显示、用户手机、用户密码 */
	private String custid, attStr, attValue, userPsw="", psamId,isvip;
	private ArrayList<View> pageViews;
	private VerticalViewPager viewPager;
	private int getWidgetId;
	/** page第一页 */
	private View firstView;
	/** page第二页 */
	private View secondview;
	/** 界面视图 */
	private View view;
	
	private List<LevelBean> level;

	/** （钱包）致富宝实体 */
	private RichTreasureBean treasureBean;
	/** 用户手机号 */
	private String phone;
	
	private int ImageIt = 0;

	/** 分享 */
	private UMSocialService mController;

	private OneButtonDialogWarn warnDialog;

	private Button btnBalanceQuery, btnBossReceive, btnDealRecord,
			btnRemittance, btnCreditCard, btnHelp, btnRealName,
			btnExpress, btnremittance, phone_recharge, credit_card_payments,
			btn_plane_ticket,btn_menu_deal_records,btn_Intelligent_recharge,btn_real_name,btn_customer_service;
	/** 是否设置支付密码 */
	private String lognum;

	private boolean flags = false;

	private AdGallery adGallery;
	// 网络图片
	private Advertisement[] data;
	// 测试图片
	private static final int[] dataids = { R.drawable.bbbao_banner, R.drawable.bbbao_banner,
			 R.drawable.bbbao_banner};

	private RelativeLayout adContainer,re_pro;
	private AdGalleryHelper adGalleryHelper;

	private LayoutInflater inflater;

	private Activity con;

	private Context context;

	private String sts;

	private boolean usertag = false;

	public static boolean phonetag = false;

	public static boolean bankcardtag = false;

	private String STS,wecname;

	private LinearLayout tv_ss, tv_dow, share_button;

	private Image3DSwitchView imageSwitchView;

	private RadioGroup mRadioGroup; // 滚动标记组件

	private String appid, userid,oemid,isareaagent;

	private boolean isrun = true;
	
	private Editor editor;
	
	private int clickCount = 0;
	
	private TextView tv_textadvertising,tv_dimiss;
	
	private List<String>  aa=  new ArrayList<String>() ;
	
	private ImageView prel_oading;
	
	private int protag = 0;
	
	private ArrayList<HashMap<String, Object>> mlist;
	
	private  String unionid = "",openid = "";

	// private Intent intent;
	// private Bundle bundle;
	/** 1、体验帐号 */
	// private int identify;

	@SuppressLint("HandlerLeak")
	private Handler menHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 6:
				Intent intent = new Intent();
				if (attStr.equals("0")) {
					
					switch (getWidgetId) {
					case R.id.btn_credit_card_payments:
						Toast.makeText(con.getApplicationContext(), "此功能即将开通！", Toast.LENGTH_SHORT).show();
//						intent.setClass(con, ShouKuanBAvtivity.class);
//						intent.putExtra("tag", "0");
//						startActivity(intent);
						return;
					case R.id.btn_intelligent_recharge:
						if(isareaagent.equals("4")||isareaagent.equals("5")){
							intent.setClass(con, HaiGouAvtivity.class);
							intent.putExtra("url",HttpUrls.INTELLIGENTRECHARGE+inithtml2());
							intent.putExtra("tag","1");
							startActivity(intent);
						}else{
							warnDialog = new OneButtonDialogWarn(con,
									R.style.CustomDialog, "提示", "此功能仅对大区以上代理开放",
									"确定", new OnMyDialogClickListener() {
										@Override
										public void onClick(View v) {
											warnDialog.dismiss();
									}
							});
							warnDialog.show();
						}


						return;
					case R.id.btn_real_name:
						if(sts.equals("0")){
							warnDialog = new OneButtonDialogWarn(con,
									R.style.CustomDialog, "提示",
									"已认证通过", "确定",
									new OnMyDialogClickListener() {
										@Override
										public void onClick(View v) {

											warnDialog.dismiss();
										}
									});
							warnDialog.show();
							return;
						}
							intent.setClass(con,AuthenticationActivity1.class);
							startActivity(intent);

						return;
					case R.id.btn_customer_service:
						Toast.makeText(con.getApplicationContext(), "此功能即将开通！", Toast.LENGTH_SHORT).show();
						return;
					case R.id.btn_menu_deal_records:
						intent.setClass(con, HaiGouAvtivity.class);
						intent.putExtra("url",HttpUrls.TOBEANAGENT+inithtml2());
						intent.putExtra("tag","1");
						startActivity(intent);
//						Toast.makeText(con.getApplicationContext(), "此功能即将开通！", Toast.LENGTH_SHORT).show();
						return;
					case R.id.btn_menu_balance_query:
						intent.setClass(con, BalanceDetailsAcitvity1.class);
						startActivity(intent);
						return;
					case R.id.btn_menu_boss_receive:
						intent.setClass(con, OrderPayActivity.class);
						startActivity(intent);
						return;
					case R.id.btn_menu_zd:
						Toast.makeText(con.getApplicationContext(), "此功能即将开通！", Toast.LENGTH_SHORT).show();
//						intent.setClass(con,
//								RichTreasureDealRecordsActivity.class);
//						startActivity(intent);
						return;
						// case R.id.btn_menu_help:
						// intent.setClass(con, HelpActivity.class);
						// startActivity(intent);
						// return;
					case R.id.btn_menu_remittance:
						Toast.makeText(con.getApplicationContext(), "此功能即将开通！", Toast.LENGTH_SHORT).show();
//						intent.setClass(con, HaiGouAvtivity.class);
//						intent.putExtra("url",HttpUrls.HG_URL);
//						intent.putExtra("tag","0");
//						startActivity(intent);
//						intent.setClass(con, TransferAccountsActivity.class);
//						startActivity(intent);
						return;
					case R.id.btn_menu_mobile_recharge:

						ToastCustom.showMessage(con.getApplicationContext(), "此功能即将开通！",
								Toast.LENGTH_SHORT);
						return;
						
					case R.id.tv_ss:
						intent.setClass(con, CaptureActivity.class);
						startActivity(intent);
						return;
					case R.id.share_buttons:
						initshare();
						return;
						
					case R.id.btn_menu_water_rate:
						ToastCustom.showMessage(con.getApplicationContext(), "此功能即将开通！",
								Toast.LENGTH_SHORT);
						return;
					case R.id.btn_menu_power_rate:
						ToastCustom.showMessage(con.getApplicationContext(), "此功能即将开通！",
								Toast.LENGTH_SHORT);
						return;
					case R.id.btn_menu_gas_rate:
//						Toast.makeText(con.getApplicationContext(), "此功能即将开通！", Toast.LENGTH_SHORT).show();
//						if (sts.equals("3")) {
//							warnDialog = new OneButtonDialogWarn(con,
//									R.style.CustomDialog, "提示", "银行卡变更申请正在审核中",
//									"确定", new OnMyDialogClickListener() {
//										@Override
//										public void onClick(View v) {
//											warnDialog.dismiss();
//
//										}
//									});
//							warnDialog.show();
//						} else {
//
//							if (bankcardtag) {
//								ToastCustom.showMessage(con, "您的银行卡变更申请已审核成功");
//								bankcardtag = false;
//							}
//
							intent.setClass(con, MyFriendListActivity.class);
							startActivity(intent);
//						}

						return;
					case R.id.btn_menu_plane_ticket:
						ToastCustom.showMessage(con.getApplicationContext(), "此功能即将开通！",
								Toast.LENGTH_SHORT);
						return;
					case R.id.btn_menu_train_ticket:
						ToastCustom.showMessage(con.getApplicationContext(), "此功能即将开通！",
								Toast.LENGTH_SHORT);
						return;
					case R.id.btn_menu_lottery_ticket:
						ToastCustom.showMessage(con.getApplicationContext(), "此功能即将开通！",
								Toast.LENGTH_SHORT);
						return;

					case R.id.btn_express_ticket:
						ToastCustom.showMessage(con.getApplicationContext(), "此功能即将开通！",
								Toast.LENGTH_SHORT);
						return;
					case R.id.btn_plane_ticket:
//						ToastCustom.showMessage(con, "此功能即将开通！",
//								Toast.LENGTH_SHORT);
						
//						intent.setClass(con, HaiGouAvtivity.class);
//						intent.putExtra("url",HttpUrls.MALL+inithtml1());
//						intent.putExtra("tag","1");
						intent.setClass(con, OnlineWeb.class);
						intent.putExtra("urlStr",
								HttpUrls.MALL+inithtml1());
						intent.putExtra("titleStr","商城");
						startActivity(intent);
						return;
					case R.id.tv_dow:
						intent.setClass(con, MyQrcard.class);
						startActivity(intent);
						return;

					case R.id.phone_recharges:
						intent.setClass(con, PhonereChargeActivity.class);
						startActivity(intent);
//						intent.setClass(con, ConvenienceServicesActivity.class);
//						startActivity(intent);
						return;
					}
				} else {
					intent.setClass(con, UserActivity.class);
					startActivity(intent);
					AppContext.getInstance().exit();
					Toast.makeText(con.getApplicationContext(), "请重新登录",
							Toast.LENGTH_SHORT).show();
				}

				// default:
				// break;
			}
		}
	};

	private String inithtml1() {
		// TODO Auto-generated method stub

		
		JSONObject jsonObj = new JSONObject();
		try {
			
			jsonObj.put("PHONENUMBER", phone);
			jsonObj.put("PASSWORD", userPsw);
			jsonObj.put("OEMID", oemid);
			jsonObj.put("APPMARKNUMBER", HttpUrls.APPNUM);
			jsonObj.put("UNIONID", unionid);
			jsonObj.put("OPENID", openid);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String aa = null;
		try {
			aa = DESKey.AES_Encode(jsonObj.toString(),
					"f15f1ede25a2471998ee06edba7d2e29");
			aa = URLEncoder.encode(aa);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aa;
	}
	
	private String inithtml2() {
		// TODO Auto-generated method stub
		Log.e("", ""+phone+userPsw+oemid);
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("PHONENUMBER", phone);
			jsonObj.put("PASSWORD", userPsw);
			jsonObj.put("OEMID", oemid);
			jsonObj.put("UNIONID", unionid);
			jsonObj.put("OPENID", openid);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String aa = null;
		try {
			aa = DESKey.AES_Encode(jsonObj.toString(),
					"f15f1ede25a2471998ee06edba7d2e29");
			aa = URLEncoder.encode(aa);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aa;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_menu);
		con = getActivity();

		appid = ((AppContext) con.getApplication()).getAppid();

		userid = ((AppContext) con.getApplication()).getUserid();
//		custid = ((AppContext) con.getApplication()).getCustId();
		isvip = MyCacheUtil.getshared(con).getString("ISSENIORMEMBER", "");
		oemid = MyCacheUtil.getshared(con).getString("OEMID", "");
		isareaagent = MyCacheUtil.getshared(con).getString("ISAREAAGENT", "");
		userPsw = MyCacheUtil.getshared(con).getString("pwd", "");
		unionid = MyCacheUtil.getshared(con).getString("UNIONID", "");
		openid = MyCacheUtil.getshared(con).getString("OPENID", "");
		editor = MyCacheUtil.setshared(con);
		custid = MyCacheUtil.getshared(con).getString("Mobile", "");
		
//		phone = ((AppContext) con.getApplication()).getMobile();
		phone = MyCacheUtil.getshared(con).getString("Mobile", "");
		// PushManager.startWork(context, phone, appid);
		context = this.getActivity();
		inflater = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		// inflater = con.getLayoutInflater();
		view = inflater.inflate(R.layout.activity_menu, null);
		loadMore();
		initView();
		playFlipAnimation2();

	}
	
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e("", "onStart");
		attStr =  MyCacheUtil.getshared(con).getString("MERSTS", "");
		sts = MyCacheUtil.getshared(con).getString("STS", "");
		wecname = MyCacheUtil.getshared(con).getString("NICKNAME", "");
		userid = ((AppContext) con.getApplication()).getUserid();
//		custid = ((AppContext) con.getApplication()).getCustId();
		isvip = MyCacheUtil.getshared(con).getString("ISSENIORMEMBER", "");
		oemid = MyCacheUtil.getshared(con).getString("OEMID", "");
		userPsw = MyCacheUtil.getshared(con).getString("pwd", "");
		custid = MyCacheUtil.getshared(con).getString("Mobile", "");
		unionid = MyCacheUtil.getshared(con).getString("UNIONID", "");
		openid = MyCacheUtil.getshared(con).getString("OPENID", "");
		editor = MyCacheUtil.setshared(con);
		custid = MyCacheUtil.getshared(con).getString("Mobile", "");
		attStr =  MyCacheUtil.getshared(con).getString("MERSTS", "");
//		phone = ((AppContext) con.getApplication()).getMobile();
		phone = MyCacheUtil.getshared(con).getString("Mobile", "");
		
		if (userPsw==null||userPsw.equals("")) {
			Log.e("", "111");
			GetMerStsTask1 stsTask = new GetMerStsTask1();
			stsTask.execute("199002", custid, userPsw, "11111111", "",
					"2", "", "", "3",HttpUrls.APPNUM,"","","",unionid,openid,"","");
		}else{
			Log.e("", "222");
			GetMerStsTask1 stsTask = new GetMerStsTask1();
			stsTask.execute("199002", custid, userPsw, "11111111", "",
					"2", "", "", "1",HttpUrls.APPNUM,"","","","","","","");
		}
		
		playFlipAnimation2();

	}
	
	

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sts = MyCacheUtil.getshared(con).getString("STS", "");
	}
	

	private void initView() {
		pageViews = new ArrayList<View>();

		// LayoutInflater inflater = activity.getLayoutInflater();    activity_menu_item_first
		firstView = inflater.inflate(R.layout.activity_menu1, null);
		 secondview = inflater.inflate(R.layout.activity_menu_item_second, null);
		
		tv_ss = (LinearLayout) view.findViewById(R.id.tv_ss);
		tv_ss.setOnClickListener(this);

//		tv_ss.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				
//				if (sts==null||sts.equals("")) {
//					FmMainActivity.context.finish();
//					Intent it = new Intent(con,
//							UserActivity.class);
//					startActivity(it);
//					return;
////					Toast.makeText(con.getApplicationContext(), "请先登录",
////							Toast.LENGTH_SHORT).show();
//				}
//			
//				if(sts.equals("0")||sts.equals("3")||sts.equals("4")){
//					Intent it1 = new Intent(getActivity(), CaptureActivity.class);
//					startActivity(it1);
//					
//				}else{
//					warnDialog = new OneButtonDialogWarn(con,
//							R.style.CustomDialog, "提示",
//							"请补全资料进行实名认证再进行操作", "确定",
//							new OnMyDialogClickListener() {
//								@Override
//								public void onClick(View v) {
//									Intent it = new Intent(
//											con,
//											AuthenticationActivity1.class);
//									startActivity(it);
//									warnDialog.dismiss();
//								}
//							});
//					warnDialog.show();
//				}
//
//			}
//		});
		
//		tv_dimiss = (TextView) view.findViewById(R.id.tv_dimiss);
		re_pro = (RelativeLayout) view.findViewById(R.id.re_pro);
//		tv_dimiss.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				re_pro.setVisibility(View.GONE);
//			}
//		});
		
		prel_oading = (ImageView) view.findViewById(R.id.prel_oading);

		tv_dow = (LinearLayout) view.findViewById(R.id.tv_dow);
		tv_dow.setOnClickListener(this);
		btnBalanceQuery = (Button) firstView
				.findViewById(R.id.btn_menu_balance_query);
		btnBalanceQuery.setOnClickListener(this);
		btnBossReceive = (Button) firstView
				.findViewById(R.id.btn_menu_boss_receive);
		 btn_menu_deal_records = (Button) firstView.findViewById(R.id.btn_menu_deal_records);
		 btn_menu_deal_records.setOnClickListener(this);
		btnBossReceive.setOnClickListener(this);
		btn_Intelligent_recharge = (Button) firstView.findViewById(R.id.btn_intelligent_recharge);
		btn_Intelligent_recharge.setOnClickListener(this);
		btn_real_name = (Button) firstView.findViewById(R.id.btn_real_name);
		btn_real_name.setOnClickListener(this);
		btn_customer_service = (Button) firstView.findViewById(R.id.btn_customer_service);
		btn_customer_service.setOnClickListener(this);
		
		btnDealRecord = (Button) firstView
				.findViewById(R.id.btn_menu_zd);
		btnDealRecord.setOnClickListener(this);
		btnremittance = (Button) firstView
				.findViewById(R.id.btn_menu_remittance);
		btnremittance.setOnClickListener(this);
//		btn_menu_rates = (Button) firstView.findViewById(R.id.btn_menu_rates);
//		btn_menu_rates.setOnClickListener(this);
		phone_recharge = (Button) firstView.findViewById(R.id.phone_recharges);
		phone_recharge.setOnClickListener(this);
		tv_textadvertising = (TextView) view.findViewById(R.id.tv_textadvertising);
		// btnRealName = (Button)
		// firstView.findViewById(R.id.btn_menu_real_name);
		// btnRealName.setOnClickListener(this);
		btnRemittance = (Button) firstView.findViewById(R.id.btn_menu_gas_rate);
		btnRemittance.setOnClickListener(this);
		share_button = (LinearLayout) view.findViewById(R.id.share_buttons);
		share_button.setOnClickListener(this);

		credit_card_payments = (Button) firstView
				.findViewById(R.id.btn_credit_card_payments);
		credit_card_payments.setOnClickListener(this);

		btn_plane_ticket = (Button) firstView
				.findViewById(R.id.btn_plane_ticket);
		btn_plane_ticket.setOnClickListener(this);
		// btnExpress = (Button)
		// firstView.findViewById(R.id.btn_express_ticket);
		// btnExpress.setOnClickListener(this);
		// btnMenuAccount = (Button) firstView
		// .findViewById(R.id.btn_menu_account_manage);
		// btnMenuAccount.setOnClickListener(this);
		// btnHelp = (Button) firstView.findViewById(R.id.btn_menu_help);
		// btnHelp.setOnClickListener(this);
		pageViews.add(firstView);
//		pageViews.add(secondview);
		// pageViews.add(secondview);
		viewPager = (VerticalViewPager) view.findViewById(R.id.guidePages);
		
		VerticalAdapter vAdapter1 = new VerticalAdapter(pageViews);
		viewPager.setAdapter(vAdapter1);
//		viewPager.setAdapter(fragmentAdapter);
		
//		viewPager.setOnPageChangeListener(new GuidePageChangeListener());

//		share_button.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				
//				if (sts==null||sts.equals("")) {
//					FmMainActivity.context.finish();
//					Intent it = new Intent(con,
//							UserActivity.class);
//					startActivity(it);
//					return;
//				}
//				if(sts.equals("0")||sts.equals("3")||sts.equals("4")){
//					initshare();
//				}else{
//					warnDialog = new OneButtonDialogWarn(con,
//							R.style.CustomDialog, "提示",
//							"请补全资料进行实名认证再进行操作", "确定",
//							new OnMyDialogClickListener() {
//								@Override
//								public void onClick(View v) {
//									Intent it = new Intent(
//											con,
//											AuthenticationActivity1.class);
//									startActivity(it);
//									warnDialog.dismiss();
//								}
//							});
//					warnDialog.show();
//				}
//			
//
//			}
//
//		});
		

		if (AppContext.isscreenstatus) {
			if(((AppContext) con.getApplication()).getMsgcontent()!=null&&!((AppContext) con.getApplication()).getMsgcontent().equals("")){
			
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Intent it = new Intent(con, IntroductionActivity.class);
						startActivity(it);
						AppContext.isscreenstatus = false;
					}
				}, 1000);
			}
		}
	}

	// 指引页面数据适配器
	class GuidePageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).removeView(pageViews.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			// TODO Auto-generated method stub
			((ViewPager) arg0).addView(pageViews.get(arg1));
			return pageViews.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}
	}

	// 指引页面更改事件监听器
	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
		}
	}

	@Override
	public void onClick(View v) {
		wecname = MyCacheUtil.getshared(con).getString("NICKNAME", "");
		attStr =  MyCacheUtil.getshared(con).getString("MERSTS", "");
		sts = MyCacheUtil.getshared(con).getString("STS", "");
		if (DateUtil.isFastDoubleClick()) {
			return;
		} else {
			Intent intent = new Intent();
			getWidgetId = v.getId();
			if (custid == null || attStr == null) {
				intent.setClass(con, UserActivity.class);
				startActivity(intent);
//				AppContext.getInstance().exit();
				ToastCustom.showMessage(con, "请重新登录");
				return;

			} else if (custid != null && attStr.equals("0") && sts.equals("0")) {
				

				if(v.getId() ==R.id.btn_plane_ticket){
					if (sts==null||sts.equals("")) {
						if(wecname==null||wecname.equals("")){
//							FmMainActivity.context.finish();
							Intent it = new Intent(con,
									UserActivity.class);
							startActivity(it);
							return;
						}else{
							Intent it = new Intent(con,
									WeChatRegistrActivity.class);
							startActivity(it);
							return;

						}
					}
					intent.setClass(con, OnlineWeb.class);
					intent.putExtra("urlStr",
							HttpUrls.MALL+inithtml1());
					intent.putExtra("titleStr","贝贝宝商城");
					startActivity(intent);
					return;
				}

				
//				if(isvip.equals("0")){
//					warnDialog = new OneButtonDialogWarn(con,
//							R.style.CustomDialog, "提示",
//							"尊敬的用户,请先升级会员再经行操作", "确定",
//							new OnMyDialogClickListener() {
//								@Override
//								public void onClick(View v) {
//									Intent it = new Intent(con,DistributorActivity.class);
//									startActivity(it);
//									warnDialog.dismiss();
//								}
//							});
//					warnDialog.show();
//					return;
//					
//				}
				menHandler.sendEmptyMessage(6);
			} else {
//				if (v.getId() == R.id.btn_menu_boss_receive) {
//					if (MyCacheUtil.getshared(con).getString("Txnsts", "").equals("0")&& sts.equals("0")) {
//						if(isvip.equals("0")){
//							warnDialog = new OneButtonDialogWarn(con,
//									R.style.CustomDialog, "提示",
//									"尊敬的用户,请先升级会员再经行操作", "确定",
//									new OnMyDialogClickListener() {
//										@Override
//										public void onClick(View v) {
//											Intent it = new Intent(con,DistributorActivity.class);
//											startActivity(it);
//											warnDialog.dismiss();
//										}
//									});
//							warnDialog.show();
//							return;
//							
//						}
//						intent.setClass(con, OrderPayActivity.class);
//						startActivity(intent);
//						return;
//
//					} else 
//						if (MyCacheUtil.getshared(con).getString("Txnsts", "").equals("1") && !sts.equals("0")) {
//						if(isvip.equals("0")){
//							warnDialog = new OneButtonDialogWarn(con,
//									R.style.CustomDialog, "提示",
//									"尊敬的用户,请先升级会员再经行操作", "确定",
//									new OnMyDialogClickListener() {
//										@Override
//										public void onClick(View v) {
//											Intent it = new Intent(con,DistributorActivity.class);
//											startActivity(it);
//											warnDialog.dismiss();
//										}
//									});
//							warnDialog.show();
//							return;
//						}
//						intent.setClass(con, OrderPayActivity.class);
//						startActivity(intent);
//						return;
//					}
//				}else 
				
				if(v.getId() ==R.id.share_buttons){
					if (sts==null||sts.equals("")) {
						if(wecname==null||wecname.equals("")){
//							FmMainActivity.context.finish();
							Intent it = new Intent(con,
									UserActivity.class);
							startActivity(it);
							return;
						}else{
							Intent it = new Intent(con,
									WeChatRegistrActivity.class);
							startActivity(it);
							return;

						}
					}
					initshare();
					return;
				}
				
				if(v.getId() ==R.id.btn_plane_ticket){
					if (sts==null||sts.equals("")) {
						if(wecname==null||wecname.equals("")){
//							FmMainActivity.context.finish();
							Intent it = new Intent(con,
									UserActivity.class);
							startActivity(it);
							return;
						}else{
							Intent it = new Intent(con,
									WeChatRegistrActivity.class);
							startActivity(it);
							return;

						}
//						Toast.makeText(con.getApplicationContext(), "请先登录",
//								Toast.LENGTH_SHORT).show();
					}
					
					intent.setClass(con, OnlineWeb.class);
					intent.putExtra("urlStr",
							HttpUrls.MALL+inithtml1());
					intent.putExtra("titleStr","商城");
					startActivity(intent);
					return;
				}
				
				if(v.getId() ==R.id.btn_menu_deal_records){
					if (sts==null||sts.equals("")) {
						if(wecname==null||wecname.equals("")){
//							FmMainActivity.context.finish();
							Intent it = new Intent(con,
									UserActivity.class);
							startActivity(it);
							return;
						}else{
							Intent it = new Intent(con,
									WeChatRegistrActivity.class);
							startActivity(it);
							return;

						}
//						Toast.makeText(con.getApplicationContext(), "请先登录",
//								Toast.LENGTH_SHORT).show();
					}
					
					intent.setClass(con, HaiGouAvtivity.class);
					intent.putExtra("url",HttpUrls.TOBEANAGENT+inithtml2());
					intent.putExtra("tag","1");
					startActivity(intent);
					return;
				}
				if(v.getId() ==R.id.btn_real_name){
					if (sts==null||sts.equals("")) {
						if(wecname==null||wecname.equals("")){
//							FmMainActivity.context.finish();
							Intent it = new Intent(con,
									UserActivity.class);
							startActivity(it);
							return;
						}else{
							Intent it = new Intent(con,
									WeChatRegistrActivity.class);
							startActivity(it);
							return;

						}
//						Toast.makeText(con.getApplicationContext(), "请先登录",
//								Toast.LENGTH_SHORT).show();
					}
					
					intent.setClass(con, AuthenticationActivity1.class);
					startActivity(intent);
					return;
				}
				
//				else if(v.getId() ==R.id.btn_menu_balance_query){
//					intent.setClass(con, BalanceDetailsAcitvity1.class);
//					startActivity(intent);
//					return;
//				}
				
//				else if (v.getId() == R.id.btn_menu_balance_query) {
//					intent.setClass(con, BalanceDetailsAcitvity.class);
//					startActivity(intent);
//					return;
//				} else if (v.getId() == R.id.btn_menu_rates) {
//					intent.setClass(con, RateMianActivity.class);
//					startActivity(intent);
//					return;
//				}
				// else if (v.getId() == R.id.btn_menu_help) {
				// intent.setClass(con, HelpActivity.class);
				// startActivity(intent);
				// }
				// else if(v.getId() == R.id.btn_menu_real_name){
				// intent.setClass(activity,
				// RealNameAuthenticationActivity.class);
				// startActivity(intent);
				// }
				// else{
				Log.e("", "sts - - - - -  "+sts);
					if (sts==null||sts.equals("")) {
						Log.e("", " - -- - wecname - - - "+wecname);
						if(wecname==null||wecname.equals("")){
//							FmMainActivity.context.finish();
							FmMainActivity.context.finish();
							Intent it = new Intent(con,
									UserActivity.class);
							startActivity(it);
							return;
						}else{
							Intent it = new Intent(con,
									WeChatRegistrActivity.class);
							startActivity(it);
							return;

						}

//						Toast.makeText(con.getApplicationContext(), "请先登录",
//								Toast.LENGTH_SHORT).show();
					}
				if (isrun) {
					psamId = ((AppContext) con.getApplication()).getPsamId();
					if (userPsw==null||userPsw.equals("")) {
						GetMerStsTask stsTask = new GetMerStsTask();
						stsTask.execute("199002", custid, userPsw, "11111111", "",
								"2", "", "", "3",HttpUrls.APPNUM,"","","",unionid,openid,"","");
					}else{
						GetMerStsTask stsTask = new GetMerStsTask();
						stsTask.execute("199002", custid, userPsw, "11111111", "",
								"2", "", "", "1",HttpUrls.APPNUM,"","","","","","","");
					}

					isrun = false;
				}

				// }
			}
		}
	}


	class GetMerStsTask extends
			AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			String[] values = { params[0], params[1], params[2], params[3],
					params[4], params[5], params[6], params[7], params[8],params[9],params[10],params[11],params[12],params[13],params[14],params[15],params[16]};
			return NetCommunicate.get(HttpUrls.LOGIN, values);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			if (result != null) {
				isrun = true;
				if (result.get(Entity.RSPCOD).equals(Entity.STATE_OK)
						&& result.get("STS").toString().equals("0")) {
					
					if(result.get("ISSENIORMEMBER")!=null){
						isvip = result.get("ISSENIORMEMBER").toString();
					}
					
					attStr = result.get("MERSTS").toString();

					((AppContext) con.getApplication()).setMerSts(attStr);
					editor.putString("Txnsts", result.get("TXNSTS").toString());
					editor.putString("MERSTS",attStr);
					editor.commit();
					((AppContext) con.getApplication()).setTxnsts(result.get("TXNSTS").toString());
					if (result.get("LOGNUM") != null) {
						lognum = result.get("LOGNUM").toString();
					}
					sts = result.get("STS").toString();
					// ((AppContext)
					// con.getApplicationContext()).setSts(result.get("STS").toString());
					Log.e("", "sta" + result.get("STS").toString());
					if (phonetag) {
						// ToastCustom.showMessage(con, "手机号变更申请已审核成功,请重新登录");
						// FmMainActivity.context.finish();
						// Intent it = new Intent(con,UserActivity.class);
						// startActivity(it);
						// ((AppContext)
						// con.getApplication()).setSts(result.get("STS").toString());
						// phonetag = false;
					}
					// else if(bankcardtag){
					//
					// ToastCustom.showMessage(con, "您的银行卡信息修改已审核成功");
					// ((AppContext)
					// con.getApplication()).setSts(result.get("STS").toString());
					// bankcardtag = false;
					// }

					((AppContext) con.getApplication()).setSts(result
							.get("STS").toString());
					editor.putString("STS",result.get("STS").toString());
					editor.commit();

					if (lognum != null && lognum.equals("0")) {
						warnDialog = new OneButtonDialogWarn(con,
								R.style.CustomDialog, "提示",
								"您的资料审核已通过,请重新登录并设置支付密码", "确定",
								new OnMyDialogClickListener() {
									@Override
									public void onClick(View v) {
										FmMainActivity.context.finish();
										Intent it = new Intent(con,
												UserActivity.class);
										con.finish();
										startActivity(it);
										warnDialog.dismiss();
									}
								});
						warnDialog.setCancelable(false);
						warnDialog.setCanceledOnTouchOutside(false);
						warnDialog.show();

					} else {
//						if(isvip.equals("0")){
//							warnDialog = new OneButtonDialogWarn(con,
//									R.style.CustomDialog, "提示",
//									"尊敬的用户,请先升级普通会员再经行操作", "确定",
//									new OnMyDialogClickListener() {
//										@Override
//										public void onClick(View v) {
//											Intent it = new Intent(con,DistributorActivity.class);
//											startActivity(it);
//											warnDialog.dismiss();
//										}
//									});
//							warnDialog.show();
//							return;
//							
//						}
						menHandler.sendEmptyMessage(6);
					}
				} else {
					
					if(result.get(Entity.RSPCOD).equals("000005")){
						
//						FmMainActivity.context.finish();
						Intent it = new Intent(con,
								UserActivity.class);
						startActivity(it);
						
					}
					
					if(result.get("IDCARDPICSTA")!=null&&result.get("CUSTPICSTA")!=null&&result.get("FRYHKIMGPATHSTA")!=null){
						String a = result.get("IDCARDPICSTA").toString();
						String b = result.get("CUSTPICSTA").toString();
						String c = result.get("FRYHKIMGPATHSTA").toString();
						((AppContext)con.getApplication()).setStateaudit(a+b+c);
					}


					if (result.get(Entity.RSPCOD).toString().equals("000002")) {
						warnDialog = new OneButtonDialogWarn(con,
								R.style.CustomDialog, "提示", "手机号变更申请成功,请重新登录",
								"确定", new OnMyDialogClickListener() {
									@Override
									public void onClick(View v) {
										FmMainActivity.context.finish();
										Intent it = new Intent(con,
												UserActivity.class);
										startActivity(it);
										warnDialog.dismiss();
									}
								});
						warnDialog.setCancelable(false);
						warnDialog.setCanceledOnTouchOutside(false);
						warnDialog.show();

					} else {
						// ToastCustom.showMessage(con,
						// result.get(Entity.RSPMSG).toString());
					}

					if (result.get("STS") != null) {
						if (result.get("STS").equals("1")) {
							usertag = true;
							warnDialog = new OneButtonDialogWarn(con,
									R.style.CustomDialog, "提示", "用户信息正在审核中",
									"确定", new OnMyDialogClickListener() {
										@Override
										public void onClick(View v) {
											warnDialog.dismiss();
										}
									});
							warnDialog.show();

						} else if (result.get("STS").equals("-1")) {
							warnDialog = new OneButtonDialogWarn(con,
									R.style.CustomDialog, "提示", result.get(
											Entity.RSPMSG).toString(), "确定",
									new OnMyDialogClickListener() {
										@Override
										public void onClick(View v) {
											warnDialog.dismiss();
											Intent it = new Intent(
													con,
													AuthenticationActivity1.class);
											it.putExtra("intentObj",
													"MenuActivity");
											startActivity(it);
										}
									});
							warnDialog.show();

						} else if (result.get("STS").equals("3")) {
							bankcardtag = true;
							menHandler.sendEmptyMessage(6);

						} else if (result.get("STS").equals("-2")) {

							warnDialog = new OneButtonDialogWarn(con,
									R.style.CustomDialog, "提示", result.get(
											Entity.RSPMSG).toString(), "确定",
									new OnMyDialogClickListener() {
										@Override
										public void onClick(View v) {
											warnDialog.dismiss();
											Intent it = new Intent(
													con,
													RequestCardInfoChangeActivity.class);
											startActivity(it);
										}
									});
							warnDialog.show();

						} else if (result.get("STS").equals("4")) {
							phonetag = true;
							warnDialog = new OneButtonDialogWarn(con,
									R.style.CustomDialog, "提示", "手机变更申请正在审核中",
									"确定", new OnMyDialogClickListener() {
										@Override
										public void onClick(View v) {
											warnDialog.dismiss();
										}
									});
							warnDialog.show();
						}

						else {
							((AppContext)con.getApplication()).setTxnsts(result.get("TXNSTS").toString());
							editor.putString("Txnsts", result.get("TXNSTS").toString());
							editor.commit();
//							((AppContext)con.getApplication()).setTxnsts(result.get("STS").toString());
							
//							if(isvip.equals("0")){
//								warnDialog = new OneButtonDialogWarn(con,
//										R.style.CustomDialog, "提示",
//										"尊敬的用户,请先升级普通会员再经行操作", "确定",
//										new OnMyDialogClickListener() {
//											@Override
//											public void onClick(View v) {
//												Intent it = new Intent(con,DistributorActivity.class);
//												startActivity(it);
//												warnDialog.dismiss();
//											}
//										});
//								warnDialog.show();
//								return;
//								
//							}
//							if(result.get("TXNSTS").toString().equals("0")){
							
//							Intent it = new Intent(con,AuthenticationActivity.class);
//							startActivity(it);
									warnDialog = new OneButtonDialogWarn(con,
											R.style.CustomDialog, "提示",
											"为了您账户安全！请补全资料进行实名认证再进行操作", "确定",
											new OnMyDialogClickListener() {
												@Override
												public void onClick(View v) {
													Intent it = new Intent(
															con,
															AuthenticationActivity1.class);
													startActivity(it);
													warnDialog.dismiss();
												}
											});
									warnDialog.show();

//							}else {
//								
//								if(isvip.equals("0")){
//									warnDialog = new OneButtonDialogWarn(con,
//											R.style.CustomDialog, "提示",
//											"尊敬的用户,请先升级普通会员再经行操作", "确定",
//											new OnMyDialogClickListener() {
//												@Override
//												public void onClick(View v) {
//													Intent it = new Intent(con,DistributorActivity.class);
//													startActivity(it);
//													warnDialog.dismiss();
//												}
//											});
//									warnDialog.show();
//									return;
//									
//								}
//								
//									warnDialog = new OneButtonDialogWarn(con,
//											R.style.CustomDialog, "提示",
//											"尊敬的用户,请先收款再操作", "确定",
//											new OnMyDialogClickListener() {
//												@Override
//												public void onClick(View v) {
//													Intent it = new Intent(
//															con,
//															OrderPayActivity.class);
//													startActivity(it);
//													warnDialog.dismiss();
//												}
//											});
//									warnDialog.show();
//							}
						}
					}
				}
			} else {
				ToastCustom.showMessage(con, "网络状况不佳");
			}
			super.onPostExecute(result);
		}
	}
	
	class GetMerStsTask1 extends
			AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			String[] values = { params[0], params[1], params[2], params[3],
					params[4], params[5], params[6], params[7], params[8],
					params[9], params[10], params[11], params[12], params[13],
					params[14], params[15], params[16] };
			return NetCommunicate.get(HttpUrls.LOGIN, values);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			if (result != null) {
				isrun = true;
				if (result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {
					if(result.get("NOTICEMESSAGE")!=null){
						editor.putString("NOTICEMESSAGE", result.get("NOTICEMESSAGE").toString());
				        if(result.get("NOTICEMESSAGE").toString().length()>0){
				        	 tv_textadvertising.setText(result.get("NOTICEMESSAGE").toString());
				        }else{
				        	 tv_textadvertising.setText("暂无通知");
				        }
					}else{
						
					}
					
				} else {
//					ToastCustom.showMessage(con, "网络状况不佳");
				}
				super.onPostExecute(result);
			}
		}
	}

	
	private void realizeFunc2() {
		mRadioGroup = (RadioGroup) view
				.findViewById(R.id.home_pop_gallery_mark1);
		imageSwitchView = (Image3DSwitchView) view
				.findViewById(R.id.image_switch_view);
		imageSwitchView.setCurrentImage(0);
		imageSwitchView.settime(5000);

		for (int i = 0; i < level.size(); i++) {
			// RelativeLayout.LayoutParams l = new
			// RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT);
			// l.setMargins(5, 0, 5, 0);

			Image3DView view = new Image3DView(con, null);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			
			
//			FinalBitmap.create(con).display(view,
//					HttpUrls.HOST_POSM + level.get(i).getUrl(),
//					view.getWidth(),
//					view.getHeight(), null, null);
			
			try {
				new GetImageUtil(con,view,HttpUrls.HOST_POSM+level.get(i).getUrl(),"bm");
			} catch (Exception e) {
				// TODO: handle exception
			}
			

			
//			FinalBitmap.create(con).display(view, HttpUrls.HOST_POSM+level.get(i).getUrl());
//			FinalBitmap.create(con).display(view,
//					HttpUrls.HOST_POSM+level.get(i).getUrl());
//			Log.e("", ""+HttpUrls.HOST_POSM+level.get(i).getUrl());
//			view.setBackgroundResource1(dataids[i]);
			view.setScaleType(null);
			
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					String url = level.get(ImageIt).getIntent();
					
					if(url==null||url.equals("null")||url.equals("")){
						return;
					}
					try {
						 Intent intent = new Intent();
						 intent.setAction("android.intent.action.VIEW");
						 Uri content_url1 = Uri.parse(url);
						 intent.setData(content_url1);
						 startActivity(intent);
					} catch (Exception e) {
						// TODO: handle exception
						 Intent intent = new Intent();
						 intent.setAction("android.intent.action.VIEW");
						 Uri content_url1 = Uri.parse("http://"+url);
						 intent.setData(content_url1);
						 startActivity(intent);
					}
				}
			});
			imageSwitchView.addView(view);

			RadioButton _rb = new RadioButton(con);
			_rb.setLayoutParams(new LayoutParams(20, LayoutParams.WRAP_CONTENT));
			_rb.setId(0x1234 + i);
			_rb.setButtonDrawable(con.getResources().getDrawable(
					R.drawable.gallery_selector));
			// _rb.setLayoutParams(l);
			_rb.setPadding(4, 0, 4, 0);
			mRadioGroup.addView(_rb);
		}

		imageSwitchView.setOnImageSwitchListener(new OnImageSwitchListener() {
			@Override
			public void onImageSwitch(int currentImage) {
				mRadioGroup.check(mRadioGroup.getChildAt(currentImage).getId());
				ImageIt = currentImage;
			}
		});
	}
	

	private void realizeFunc1() {
		mRadioGroup = (RadioGroup) view
				.findViewById(R.id.home_pop_gallery_mark1);
		imageSwitchView = (Image3DSwitchView) view
				.findViewById(R.id.image_switch_view);
		imageSwitchView.setCurrentImage(0);
		imageSwitchView.settime(5000);

		for (int i = 0; i < dataids.length; i++) {
			// RelativeLayout.LayoutParams l = new
			// RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT);
			// l.setMargins(5, 0, 5, 0);

			Image3DView view = new Image3DView(con, null);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			view.setBackgroundResource(dataids[i]);
			view.setScaleType(null);
			imageSwitchView.addView(view);

			RadioButton _rb = new RadioButton(con);
			_rb.setLayoutParams(new LayoutParams(20, LayoutParams.WRAP_CONTENT));
			_rb.setId(0x1234 + i);
			_rb.setButtonDrawable(con.getResources().getDrawable(
					R.drawable.gallery_selector));
			// _rb.setLayoutParams(l);
			_rb.setPadding(4, 0, 4, 0);
			mRadioGroup.addView(_rb);
		}

		imageSwitchView.setOnImageSwitchListener(new OnImageSwitchListener() {
			@Override
			public void onImageSwitch(int currentImage) {
				mRadioGroup.check(mRadioGroup.getChildAt(currentImage).getId());
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(imageSwitchView!=null){
			imageSwitchView.clear();
		}
		
	}
	
	private String inithtml() {
		// TODO Auto-generated method stub

//		JSONObject jsonObj = new JSONObject();
//		try {
//			jsonObj.put("PHONENUMBER", phone);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String aa = null;
		try {
			aa = DESKey.AES_Encode(phone,
					"f15f1ede25a2471998ee06edba7d2e29");
//			aa = URLEncoder.encode(aa);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aa;
	}

	private void initshare() {
		// TODO Auto-generated method stub
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		mController.getConfig().setPlatforms(
//				SHARE_MEDIA.RENREN,
//				SHARE_MEDIA.TENCENT, 
				SHARE_MEDIA.QQ, 
//				SHARE_MEDIA.QZONE,
				SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
//				SHARE_MEDIA.SINA, 
				SHARE_MEDIA.SMS);

		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		mController
				.setShareContent("告诉你一个刷卡神器！一起来使用贝贝宝刷卡收款吧！刷卡0费率");
		String appID = "wx49699f2e27560127";
		String appSecret = "f4849377be84176abe3d88cb78043a51";

		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(getActivity(), appID, appSecret);
		// WeiXinShareContent weixinContent = new WeiXinShareContent();
		// weixinContent.setTargetUrl("http://180.166.124.95:8092/posm/upload/QH_W_V1.2.apk");
		wxHandler.addToSocialSDK();
		UMImage aa = new UMImage(getActivity(), R.drawable.ico);
		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(), appID,
				appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		// 添加短信
		SmsHandler smsHandler = new SmsHandler();
		smsHandler.addToSocialSDK();

		String setphone = phone.substring(0,3);
		String getphone = phone.substring(phone.length()-4);
	      //设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent("一起来使用贝贝宝刷卡收款吧！刷卡0费率");   
        circleMedia.setTitle("告诉你一个刷卡神器！");
        circleMedia.setShareImage(aa);
        circleMedia.setTargetUrl(HttpUrls.SHEARURL+inithtml());
        mController.setShareMedia(circleMedia); 
		
		

		WeiXinShareContent weixinContent = new WeiXinShareContent();
		// 设置分享文字
		weixinContent.setShareContent("告诉你一个刷卡神器！一起来使用贝贝宝刷卡收款吧！刷卡0费率");
		// 设置title
		weixinContent.setTitle("告诉你一个刷卡神器！");
		// 设置分享内容跳转URL
		weixinContent
				.setTargetUrl(HttpUrls.SHEARURL+inithtml());
		// 设置分享图片m
		weixinContent.setShareImage(aa);

		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(),
				"1105270923", "3FBhlNjRZF0k2rE3");
		qqSsoHandler.addToSocialSDK();

		// 参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(getActivity(),
				"1105270923", "3FBhlNjRZF0k2rE3");
		qZoneSsoHandler.addToSocialSDK();

		QQShareContent qqShareContent = new QQShareContent();
		// 设置分享文字
		qqShareContent.setShareContent("告诉你一个刷卡神器！一起来使用贝贝宝刷卡收款吧！刷卡0费率");
		// 设置分享title
		qqShareContent.setTitle("告诉你一个刷卡神器！");
		// 设置分享图片
		qqShareContent.setShareImage(aa);
		// 设置点击分享内容的跳转链接
		qqShareContent
				.setTargetUrl(HttpUrls.SHEARURL+inithtml());

		QZoneShareContent qzone = new QZoneShareContent();
		// 设置分享文字
		qzone.setShareContent("告诉你一个刷卡神器！一起来使用贝贝宝刷卡收款吧！刷卡0费率");
		// 设置点击消息的跳转URL
		qzone.setTargetUrl(HttpUrls.SHEARURL+inithtml());
		// 设置分享内容的标题
		qzone.setTitle("告诉你一个刷卡神器！");
		// 设置分享图片
		qzone.setShareImage(aa);

		mController.setShareMedia(qzone);

		mController.setShareMedia(qqShareContent);

		mController.setShareMedia(weixinContent);

		mController.openShare(getActivity(), false);
	}
	
	
	private void loadMore() {
		mlist = new ArrayList<HashMap<String,Object>>();
		new Thread(run).start();
	}

	Runnable run = new Runnable() {

		@Override
		public void run() {
				
				String[] values = {HttpUrls.BANNER+"",phone, oemid,};
				ArrayList<HashMap<String, Object>> list = NetCommunicate.getList(HttpUrls.BANNER,
						 values,HttpKeys.BANNER_BACK);

			Message msg = new Message();

			if (list != null) {
				mlist.addAll(list);
				if (list.size() <= 0 || list == null) {
					msg.what = 2;
				} else {
					msg.what = 1;
				}
			} else {
				
				msg.what = 3;
			}
			handler.sendMessage(msg);
		}
	};

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			loginWithWeixin();
			switch (msg.what) {
			case 1:
				level = new ArrayList<LevelBean>();
				for (int i = 0; i <mlist.size(); i++) {
					LevelBean l = new LevelBean();
					if(mlist.get(i).get("IMG_URL")!=null){
						
						l.setUrl(mlist.get(i).get("IMG_URL").toString());
					}
					if(mlist.get(i).get("FORWARD_URL")!=null){
						
						l.setIntent(mlist.get(i).get("FORWARD_URL").toString());
					}
					if(mlist.get(i).get("CONTENT")!=null){
						l.setIsshowin(true);

					}else{
						l.setIsshowin(false);
					}
					if(mlist.get(i).get("AGTMERCNUM")!=null){
						l.setAgtmercnum(mlist.get(i).get("AGTMERCNUM").toString());
					}
					if(mlist.get(i).get("CONTENT")!=null){
						l.setContent(mlist.get(i).get("CONTENT").toString());
					}
					if(mlist.get(i).get("NICKNAME")!=null){
						l.setNickname(mlist.get(i).get("NICKNAME").toString());
					}
					if(mlist.get(i).get("ADDRESS")!=null){
						l.setAddress(mlist.get(i).get("ADDRESS").toString());
					}
					if(mlist.get(i).get("AGTPHONE")!=null){
						l.setAgtphone(mlist.get(i).get("AGTPHONE").toString());
					}
					if(mlist.get(i).get("FONTCOLOR")!=null){
						l.setFontcolor(mlist.get(i).get("FONTCOLOR").toString());
					}
					if(mlist.get(i).get("LINKURL")!=null){
						l.setUrl(mlist.get(i).get("LINKURL").toString());
					}

					level.add(l);
				}
				if(level.get(0).isIsshowin()){
					realizeFunc();
					
				}else{
					realizeFunc();
//					realizeFunc2();
				}
				
				break;
			case 2:
				prel_oading.setVisibility(View.VISIBLE);
//				realizeFunc1();
				break;
			case 3:
				prel_oading.setVisibility(View.VISIBLE);
//				Toast.makeText(getApplicationContext(), "网络不给力,请检查网络设置",
//						Toast.LENGTH_SHORT).show();
//				listView.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		};
	};
	
	public void realizeFunc(){
		
		imageSwitchView = (Image3DSwitchView) view
				.findViewById(R.id.image_switch_view);
		imageSwitchView.setVisibility(View.GONE);
		adContainer = (RelativeLayout) view.findViewById(R.id.ad_container);
		adGalleryHelper = new AdGalleryHelper(context, level, 5000,true);
		adContainer.addView(adGalleryHelper.getLayout());
		adGallery = adGalleryHelper.getAdGallery();
		adGallery.setAdOnItemClickListener(this);
	}
	
	@Override
	public void setItemClick(int position) {
		String url = level.get(position).getIntent();
		if(url!=null){
			try {
				 Intent intent = new Intent();
				 intent.setAction("android.intent.action.VIEW");
				 Uri content_url1 = Uri.parse(url);
				 intent.setData(content_url1);
				 startActivity(intent);
			} catch (Exception e) {
				// TODO: handle exception
				 Intent intent = new Intent();
				 intent.setAction("android.intent.action.VIEW");
				 Uri content_url1 = Uri.parse("http://"+url);
				 intent.setData(content_url1);
				 startActivity(intent);
			}

		}
		//调用系统浏览器访问对应广告图的链接
	}

	@SuppressLint("NewApi") 
	private void playFlipAnimation2() {
		
        clickCount++;
//        final AnimatorSet animatorSetOut = (AnimatorSet) AnimatorInflater
//                .loadAnimator(con, R.anim.card_flip_left_out);
//
////        final AnimatorSet animatorSetIn = (AnimatorSet) AnimatorInflater
////                .loadAnimator(con, R.anim.card_flip_left_in);
//
//        animatorSetOut.setTarget(tv_textadvertising);
//        animatorSetIn.setTarget(tv_textadvertising);

        String text = MyCacheUtil.getshared(con).getString("NOTICEMESSAGE", "");
        
        if(text.length()>0){
        	 tv_textadvertising.setText(text);
//        if(text.length()<=20){
//        	aa.add(text.substring(0, text.length()));
//        }else if(text.length()>=20&&text.length()<40){
//        	aa.add(text.substring(0, 20));
//        	aa.add(text.substring(20, text.length()));
//        }else if(text.length()>=40&&text.length()<60){
//        	aa.add(text.substring(0, 20));
//        	aa.add(text.substring(20, 40));
//        	aa.add(text.substring(40, text.length()));
//        }else if(text.length()>=60&&text.length()<70){
//        	aa.add(text.substring(0, 20));
//        	aa.add(text.substring(20, 40));
//        	aa.add(text.substring(40, 60));
//        	aa.add(text.substring(60, text.length()));
//        }
        }else{
        	 tv_textadvertising.setText("暂无通知");
        }
        

//        animatorSetOut.addListener(new AnimatorListenerAdapter() {
//
//            @Override
//            public void onAnimationEnd(Animator animation) {// 翻转90度之后，换图
//            	
//            	for (int i = protag; i < aa.size(); i++) {
//            		 tv_textadvertising.setText("  "+aa.get(protag));
//            		 protag ++;
//            		 if(protag==aa.size()){
//            			 protag = 0;
//            		 }
//            		break;
//       			}
//                if (clickCount % 2 == 0) {
//
//                } else {
//
//                }
//               
//            }
//        	
//        });
//        
//        
//        
//    	final Handler handlers = new Handler();
//    	Runnable runnable = new Runnable() {
//    		@Override
//    		public void run() {
//    			animatorSetOut.start();
//    			handlers.postDelayed(this, 5000);// 20是延时时长
//    		}
//
//    	};
//    	handlers.postDelayed(runnable, 0);
    }
	
	
//	private void rewechat(){
//	    private IWXAPI mWeixinAPI;
//	    private String WEIXIN_APP_ID = "wx49699f2e27560127";

	    private void loginWithWeixin() {
//	        if (mWeixinAPI == null) {
//	            mWeixinAPI = WXAPIFactory.createWXAPI(con, WEIXIN_APP_ID, false);
//	        }
//
//	        if (!mWeixinAPI.isWXAppInstalled()) {
//	        	
//	            //提醒用户没有按照微信
//	            return;
//	        }

//	        mWeixinAPI.registerApp(WEIXIN_APP_ID);
//
//	        SendAuth.Req req = new SendAuth.Req();
//	        req.scope = WEIXIN_SCOPE;
//	        req.state = WEIXIN_STATE;
//	        mWeixinAPI.sendReq(req);
//	    }
	}
	
	
}