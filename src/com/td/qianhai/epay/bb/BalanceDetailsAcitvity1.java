package com.td.qianhai.epay.bb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.td.qianhai.epay.bb.adapter.MoneyListAdapter;
import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.Entity;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.beans.LoginTasks;
import com.td.qianhai.epay.bb.beans.MoneyListBean;
import com.td.qianhai.epay.bb.beans.RichTreasureBean;
import com.td.qianhai.epay.bb.interfaces.onMyaddTextListener;
import com.td.qianhai.epay.bb.mail.utils.GetImageUtil;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;
import com.td.qianhai.epay.bb.views.AnimationUtil;
import com.td.qianhai.epay.bb.views.PullToRefreshLayout;
import com.td.qianhai.epay.bb.views.PullToRefreshLayout.OnRefreshListener;
import com.td.qianhai.epay.bb.views.dialog.EidtDialog;
import com.td.qianhai.epay.bb.views.dialog.MyEditDialog;
import com.td.qianhai.epay.bb.views.dialog.OneButtonDialogWarn;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnEditDialogChlicListener;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnMyDialogClickListener;
import com.td.qianhai.fragmentmanager.FmMainActivity;

public class BalanceDetailsAcitvity1 extends BaseActivity {

	private String money;

	private int state;

	private TextView tvs_1,tvs_3,bt_title_right1,bt_title_right,me_result_money,me_result_money1,me_result_money2, me_result, bt_title_left,
			tv_title_contre,me_profit_money,user_state_img,lin_profit,me_deposits,tv_exchange,tvs_2,me_result_date,me_result_money3,me_result_money4;

	private RelativeLayout  btn_accounts;

	private LinearLayout me_data,lin_info;
	
	private RelativeLayout btn_recharge,btn_mention;

	private Intent it;

	private ImageView result_img,img_head;
	
	private String phone;
	
	private RichTreasureBean treasureBean;
	
	private OneButtonDialogWarn warnDialog;
	
	private boolean tag = false;
	
	private String aa,results,lognum;
	
	private String attStr,sts;
	
	private  EidtDialog doubleWarnDialogs;
	
	private String astring,personpic;
	
	private MyEditDialog doubleWarnDialog1;
	
	
	private int page = 1; // 页数
	private int allPageNum = 0; // 总页数
	private int PAGE_SIZE = 10;
	private boolean isThreadRun = false; // 加载数据线程运行状态
	private ListView listView;
	private MoneyListAdapter adapter;
	private ArrayList<HashMap<String, Object>> mList;
	private PullToRefreshLayout refreshlaout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {//balance_info_activity1

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance);
		AppContext.getInstance().addActivity(this);
		phone = MyCacheUtil.getshared(this).getString("Mobile", "");
		
		refreshlaout =  (PullToRefreshLayout) findViewById(R.id.refresh_view);
		
		refreshlaout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				GetWalletInfo  walletinfo = new GetWalletInfo();
				
				walletinfo.execute(HttpUrls.RICH_TREASURE_INFO + "",
						phone);
			}
			
			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				refreshlaout.loadmoreFinish(0);
			}
		});
		final ScaleAnimation anima = new ScaleAnimation(1f, 2f, 1f, 2f, 
				Animation.RELATIVE_TO_SELF, 3f, Animation.RELATIVE_TO_SELF, 0f); 
				anima.setDuration(500);
				anima.setFillAfter(true);
				final ScaleAnimation anima2 = new ScaleAnimation(2f, 1f, 2f, 1f, 
						Animation.RELATIVE_TO_SELF, 3f, Animation.RELATIVE_TO_SELF, 0f); 
						anima2.setDuration(500);
//				user_state_img = (TextView) findViewById(R.id.user_state_img);
//				
//				user_state_img.setOnTouchListener(new OnTouchListener() {
//					
//					@Override
//					public boolean onTouch(View arg0, MotionEvent event) {
//						// TODO Auto-generated method stub
//						
//						if(event.getAction() == MotionEvent.ACTION_DOWN){
//					        user_state_img.startAnimation(anima);
//					        if(aa!=null){
//					        	user_state_img.setTextSize(8);
//					        	user_state_img.setText(treasureBean.getMerNam()+"\n"+"状态:"+aa);
//					        	
//					        }
//						}
//						if(event.getAction() == MotionEvent.ACTION_UP){
//							user_state_img.startAnimation(anima2);
//							user_state_img.setTextSize(13);
//							user_state_img.setText(treasureBean.getMerNam());
//							
//						}
//						
//						return true;
//						
//						
//						
//					}
//				});	
			

				}
	
//	public static Date getTimesWeekmorning() {
//		Calendar cal = Calendar.getInstance();
//		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
//		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//		return cal.getTime();
//		}
//
//	
//	public static Date getTimesWeeknight() {
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(getTimesWeekmorning());
//		cal.add(Calendar.DAY_OF_WEEK, 7);
//		return cal.getTime();
//		}
	
    // 获得本周星期日的日期    
    public String getCurrentWeekday() {  
        int mondayPlus = this.getMondayPlus();  
        GregorianCalendar currentDate = new GregorianCalendar();  
        currentDate.add(GregorianCalendar.DATE, mondayPlus+6);  
        Date monday = currentDate.getTime();  
          
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        df.format(new Date())
        Date bdate = new Date(); 
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
        {
        	return "今天到期";
        }
        
        DateFormat df = DateFormat.getDateInstance();  
        String preMonday = df.format(monday);  
        return preMonday;  
    } 
    
    // 获得当前日期与本周日相差的天数  
    private int getMondayPlus() {  
        Calendar cd = Calendar.getInstance();  
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......  
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1;         //因为按中国礼拜一作为第一天所以这里减1  
        if (dayOfWeek == 1) {  
            return 0;  
        } else {  
            return 1 - dayOfWeek;  
        }  
    }
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		phone = ((AppContext)getApplication()).getMobile();
		phone = MyCacheUtil.getshared(this).getString("Mobile", "");
		
//		attStr = ((AppContext) getApplication()).getMerSts();
//		
//		sts = ((AppContext) getApplication()).getSts();
		attStr =  MyCacheUtil.getshared(this).getString("MERSTS", "");
		sts = MyCacheUtil.getshared(this).getString("STS", "");
		lognum = MyCacheUtil.getshared(this).getString("LOGNUM", "");

		
			GetWalletInfo  walletinfo = new GetWalletInfo();
			
			walletinfo.execute(HttpUrls.RICH_TREASURE_INFO + "",
					phone);
			
//			if(!sts.equals("0")){
//				LoginTasks tt= new LoginTasks(this);
//				results = tt.logininfo();
//			}
			
			
			mList = new ArrayList<HashMap<String, Object>>();
			listView = (ListView) findViewById(R.id.money_list);
			adapter = new MoneyListAdapter(this, mList);
			listView.setAdapter(adapter);

			
			initview();
			
			setfirstimg();
	}
	
	private void setfirstimg(){
		personpic = MyCacheUtil.getshared(this).getString("PERSONPIC", "");
		if(personpic!=null){
			new GetImageUtil(this, img_head,HttpUrls.HOST_POSM+personpic);
		}
	}
	
	
	@SuppressLint("NewApi") private void initview() {
		btn_recharge = (RelativeLayout) findViewById(R.id.btn_recharge);
		me_profit_money =(TextView) findViewById(R.id.me_profit_money);
		btn_accounts = (RelativeLayout) findViewById(R.id.btn_accounts);
		me_result_money4 = (TextView) findViewById(R.id.me_result_money4);
		me_result_money = (TextView) findViewById(R.id.me_result_money);
		me_result_money1 = (TextView) findViewById(R.id.me_result_money1);
		me_result_money2 = (TextView) findViewById(R.id.me_result_money2);
		me_result_money3 = (TextView) findViewById(R.id.me_result_money3);
		tv_exchange = (TextView) findViewById(R.id.tv_exchange);
		img_head = (ImageView) findViewById(R.id.img_head);
		bt_title_right1 = (TextView) findViewById(R.id.bt_title_right1);
		bt_title_right = (TextView) findViewById(R.id.bt_title_right);
		bt_title_right.setVisibility(View.GONE);
		bt_title_right1.setVisibility(View.VISIBLE);
		
		lin_info = (LinearLayout) findViewById(R.id.point);
		tvs_2 = (TextView) findViewById(R.id.tvs_2);
		tvs_1 = (TextView) findViewById(R.id.tvs_1);
		tvs_3 = (TextView) findViewById(R.id.tvs_3);
		me_result_date = (TextView) findViewById(R.id.me_result_date);
		me_result_date.setText(getCurrentWeekday());
//		me_deposits = (TextView) findViewById(R.id.me_deposits);
		tv_title_contre = (TextView) findViewById(R.id.tv_title_contre);
		tv_title_contre.setText("钱包");
		result_img = (ImageView) findViewById(R.id.result_img);
		btn_mention = (RelativeLayout) findViewById(R.id.btn_mentionss);
		me_result = (TextView) findViewById(R.id.me_results);
		lin_profit = (TextView) findViewById(R.id.lin_profit);
		bt_title_left = (TextView) findViewById(R.id.bt_title_left);
//		AnimationUtil.ScaleAnimations(me_profit_money);
//		AnimationUtil.ScaleAnimations(me_result_money);
//		AnimationUtil.ScaleAnimations(me_result_money1);
//		AnimationUtil.ScaleAnimations(me_result_money2);
		bt_title_right1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it = new Intent(BalanceDetailsAcitvity1.this,RecommendeddetailActivity.class);
				startActivity(it);
			}
		});
		lin_info.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it = new Intent(BalanceDetailsAcitvity1.this,PointActivity.class);
				it.putExtra("tag", me_result_money4.getText().toString());
				startActivity(it);
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long id) {
				// TODO Auto-generated method stub
				String oderid = mList.get((int)id).get("MIDOEMID").toString(); // 订单id
				String odertyp = mList.get((int)id).get("MIDOEMTYP").toString(); //订单typ
				String odernam = mList.get((int)id).get("MIDOEMNAM").toString();
				
				double d = Double.parseDouble(treasureBean.getAvaamt());//总金额
				String r = String .format("%.2f",d/100);
				
//				String instatus  = mList.get((int)id).get("INSTATUS").toString(); //转入
//				String outstatus  = mList.get((int)id).get("OUTSTATUS").toString(); //转出
//				
//				String mininamt  = String .format("%.2f",Double.parseDouble(mList.get((int)id).get("MININAMT").toString())/100); //转入限额
//				String maxinamt   = String .format("%.2f",Double.parseDouble(mList.get((int)id).get("MAXINAMT").toString())/100); 
//				
//				String minoutamt  = String .format("%.2f",Double.parseDouble(mList.get((int)id).get("MINOUTAMT").toString())/100); //转出限额
//				String maxoutamt  = String .format("%.2f",Double.parseDouble(mList.get((int)id).get("MAXOUTAMT").toString())/100); 
//				
//				String tot = String .format("%.2f",Double.parseDouble(mList.get((int)id).get("TOTPOINTS").toString())/100);
//				String pop = String .format("%.2f",Double.parseDouble(mList.get((int)id).get("OPOINTS").toString())/100);
//				
//				String inday = String .format("%.2f",Double.parseDouble(mList.get((int)id).get("DAYSUMAMT").toString())/100);
//				String outday = String .format("%.2f",Double.parseDouble(mList.get((int)id).get("DAYOUTSUMAMT").toString())/100);
//				String savedamt  = String .format("%.2f",Double.parseDouble(mList.get((int)id).get("SAVEDAMT").toString())/100); 
//				
//				
//				String dayinnum  = mList.get((int)id).get("DAYINNUM").toString(); //转入次数
//				String dayoutnum  = mList.get((int)id).get("DAYOUTNUM").toString(); //转出次数
				
				Intent it = new Intent(BalanceDetailsAcitvity1.this,FinancialProductsActivity.class);
				it.putExtra("balace", r);
//				it.putExtra("instatus", instatus);
//				it.putExtra("outstatus", outstatus);
//				it.putExtra("mininamt", mininamt);
//				it.putExtra("maxinamt", maxinamt);
//				it.putExtra("minoutamt", minoutamt);
//				it.putExtra("maxoutamt", maxoutamt);
				it.putExtra("oderid", oderid);
				it.putExtra("odernam", odernam);
//				it.putExtra("inday", inday);
//				it.putExtra("outday", outday);
				it.putExtra("odertyp", odertyp);
//				it.putExtra("tot", tot);
//				it.putExtra("savedamt", savedamt);
//				it.putExtra("pop", pop);
//				it.putExtra("dayinnum", dayinnum);
//				it.putExtra("dayoutnum", dayoutnum);
				startActivity(it);
				
			}
		});
		tv_exchange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
//				doubleWarnDialogs = new EidtDialog(BalanceDetailsAcitvity1.this, R.style.MyEditDialog, "", "请输入兑换宝币数量", "确认", "取消", "", new OnEditDialogChlicListener() {
//					
//					@Override
//					public void onClick(View v, String a) {
//						// TODO Auto-generated method stub
//						switch (v.getId()) {
//						case R.id.btn_right:
//							doubleWarnDialogs.dismiss();
//							InputMethodManager m=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//							m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//							break;
//						case R.id.btn_left:
//							if(a.equals("0")||a.length()<=0){
//								Toast.makeText(getApplicationContext(), "请输入正确宝币数量",
//										Toast.LENGTH_SHORT).show();
////								ToastCustom.showMessage(getApplicationContext(), "请输入正确划拨数量");
//							}else{
//								
//								shwpaypwd((int)(Double.parseDouble(a)*100)+"");
//								doubleWarnDialogs.dismiss();
//							}
//							
//							break;
//						default:
//							break;
//						}
//					}
//				}, 1);
//				doubleWarnDialogs.setCancelable(false);
//				doubleWarnDialogs.setCanceledOnTouchOutside(false);
//				doubleWarnDialogs.show();
				SpannableString msps = new SpannableString("确定兑换?");
				showDoubleWarnDialog(msps);
				
				
			}
		});
		

//		me_deposits.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent it = new Intent(BalanceDetailsAcitvity1.this,RichTreasureDealRecordsActivity.class);
//				it.putExtra("tag","0");
//				startActivity(it);
//			}
//		});
				
		bt_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		lin_profit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent it = new Intent(BalanceDetailsAcitvity1.this,CurrentAccountInfoActivity.class);
				startActivity(it);
			}
		});
		
		btn_recharge.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent it = new Intent(BalanceDetailsAcitvity1.this,OrderPayActivity.class);
				startActivity(it);
			}
		});
//		if (money.length() == 1) {
//			me_result_money.setText("0.0" + money);
//		} else if (money.length() == 2) {
//			me_result_money.setText("0." + money);
//		} else {
//			me_result_money.setText(money.substring(0, money.length() - 2)
//					+ "." + money.substring(money.length() - 2));
//		}
		btn_mention.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
//				if(((AppContext)getApplication()).getTxnsts().equals("0")){
//				if(treasureBean.getAvaamt().equals("0")){
//					Toast.makeText(getApplicationContext(), "无可用余额",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				
				if(sts.equals("0")&&lognum!=null&&lognum.equals("0")){
					warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
							R.style.CustomDialog, "提示",
							"您的资料审核已通过,请重新登录并设置支付密码", "确定",
							new OnMyDialogClickListener() {
								@Override
								public void onClick(View v) {
									FmMainActivity.context.finish();
									Intent it = new Intent(BalanceDetailsAcitvity1.this,
											UserActivity.class);
									startActivity(it);
									warnDialog.dismiss();
								}
							});
					warnDialog.setCancelable(false);
					warnDialog.setCanceledOnTouchOutside(false);
					warnDialog.show();
					return;
				}
				
					if(attStr.equals("0")&&sts.equals("0")){
						Intent it = new Intent(BalanceDetailsAcitvity1.this,
								WithdrawalActivity.class);
						it.putExtra("tag", "2");
						startActivity(it);
					}else if (sts.equals("4")) {
						
						warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
								R.style.CustomDialog, "提示", "手机号变更正在审核中,待审核通过后重试",
								"确定", new OnMyDialogClickListener() {
									@Override
									public void onClick(View v) {
										
										finish();
										warnDialog.dismiss();

									}
								});
						warnDialog.show();
					}else if(sts.equals("1")){
						warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
								R.style.CustomDialog, "提示", "用户信息正在审核中,待审核通过后重试",
								"确定", new OnMyDialogClickListener() {
									@Override
									public void onClick(View v) {
										warnDialog.dismiss();
										finish();

									}
								});
						warnDialog.show();
					}else if (sts.equals("-1")){
						warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
								R.style.CustomDialog, "提示",LoginTasks.results, "确定",
								new OnMyDialogClickListener() {
									@Override
									public void onClick(View v) {
										warnDialog.dismiss();
										Intent it = new Intent(BalanceDetailsAcitvity1.this,
												NewRealNameAuthenticationActivity.class);
										it.putExtra("intentObj",
												"MenuActivity");
										startActivity(it);
										finish();
									}
								});
						warnDialog.show();
						}else{
							
							Intent it = new Intent(BalanceDetailsAcitvity1.this,AuthenticationActivity1.class);
							startActivity(it);
//						warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
//						R.style.CustomDialog, "提示", "为了您账户安全！请补全资料进行实名认证再进行操作", "确定",
//						new OnMyDialogClickListener() {
//							@Override
//							public void onClick(View v) {
//								Intent it = new Intent(BalanceDetailsAcitvity1.this,NewRealNameAuthenticationActivity.class);
//								startActivity(it);
//								warnDialog.dismiss();
//								finish();
//							}
//						});
//						warnDialog.show();
					}
				

//						}else if(sts.equals("1")){
//							Toast.makeText(getApplicationContext(), "用户信息正在审核中,暂无法提现",
//									Toast.LENGTH_SHORT).show();
//							
//						}else if(sts.equals("3")){
//							Toast.makeText(getApplicationContext(), "您的银行卡信息修改正在审核中,暂无法提现",
//									Toast.LENGTH_SHORT).show();
//							
//						}else if(sts.equals("4")){
//							Toast.makeText(getApplicationContext(), "您的手机号修改正在审核中,暂无法提现",
//									Toast.LENGTH_SHORT).show();
//						}else{
//							warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
//									R.style.CustomDialog, "提示", "为了您账户安全！请补全资料进行实名认证再进行操作", "确定",
//									new OnMyDialogClickListener() {
//										@Override
//										public void onClick(View v) {
//											Intent it = new Intent(BalanceDetailsAcitvity1.this,NewRealNameAuthenticationActivity.class);
//											startActivity(it);
//											warnDialog.dismiss();
//										}
//									});
//							warnDialog.show();
//						}
//				}else{
//					warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
//							R.style.CustomDialog, "提示",
//							"尊敬的用户,请先充值再操作", "确定",
//							new OnMyDialogClickListener() {
//								@Override
//								public void onClick(View v) {
//									Intent it = new Intent(
//											BalanceDetailsAcitvity1.this,
//											OrderPayActivity.class);
//									startActivity(it);
//									warnDialog.dismiss();
//								}
//							});
//					warnDialog.show();
//				}


//						if(attStr.equals("0")&&!sts.equals("0")){
//
//						}else if(!attStr.equals("0")&&sts.equals("1")){
//							ToastCustom.showMessage(BalanceDetailsAcitvity1.this,
//									"用户信息正在审核中,暂无法提现");
//						}else{
//
//						}
//					}
				
			}
		});

		btn_accounts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

//				if(((AppContext)getApplication()).getTxnsts().equals("0")){
//					if(attStr.equals("0")&&sts.equals("0")){
				
//				if(treasureBean.getAvaamt().equals("0")){
//					Toast.makeText(getApplicationContext(), "无可用余额",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
				if(sts.equals("0")&&lognum!=null&&lognum.equals("0")){
					warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
							R.style.CustomDialog, "提示",
							"您的资料审核已通过,请重新登录并设置支付密码", "确定",
							new OnMyDialogClickListener() {
								@Override
								public void onClick(View v) {
									FmMainActivity.context.finish();
									Intent it = new Intent(BalanceDetailsAcitvity1.this,
											UserActivity.class);
									startActivity(it);
									warnDialog.dismiss();
								}
							});
					warnDialog.setCancelable(false);
					warnDialog.setCanceledOnTouchOutside(false);
					warnDialog.show();
					return;
				}
				if(attStr.equals("0")&&sts.equals("0")){
					Intent it = new Intent(BalanceDetailsAcitvity1.this,
							TransferAccountsActivity.class);
					startActivity(it);
				}else if (sts.equals("4")) {
					warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
							R.style.CustomDialog, "提示", "手机号变更正在审核中,待审核通过后重试",
							"确定", new OnMyDialogClickListener() {
								@Override
								public void onClick(View v) {
									warnDialog.dismiss();
									finish();
								}
							});
					warnDialog.show();
				}else if(sts.equals("1")){
					warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
							R.style.CustomDialog, "提示", "用户信息正在审核中,待审核通过后重试",
							"确定", new OnMyDialogClickListener() {
								@Override
								public void onClick(View v) {
									warnDialog.dismiss();
									finish();
								}
							});
					warnDialog.show();
				}else if (sts.equals("-1")){
					warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
							R.style.CustomDialog, "提示",LoginTasks.results, "确定",
							new OnMyDialogClickListener() {
								@Override
								public void onClick(View v) {
									warnDialog.dismiss();
									Intent it = new Intent(BalanceDetailsAcitvity1.this,
											NewRealNameAuthenticationActivity.class);
									it.putExtra("intentObj",
											"MenuActivity");
									startActivity(it);
									finish();
								}
							});
					warnDialog.show();
					
				}else{
					
					Intent it = new Intent(BalanceDetailsAcitvity1.this,AuthenticationActivity1.class);
					startActivity(it);
//					warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
//					R.style.CustomDialog, "提示", "为了您账户安全！请补全资料进行实名认证后操作", "确定",
//					new OnMyDialogClickListener() {
//						@Override
//						public void onClick(View v) {
//							Intent it = new Intent(BalanceDetailsAcitvity1.this,NewRealNameAuthenticationActivity.class);
//							startActivity(it);
//							warnDialog.dismiss();
//							finish();
//						}
//					});
//					warnDialog.show();
				}

//						}else if(sts.equals("1")){
//							ToastCustom.showMessage(BalanceDetailsAcitvity1.this,
//									"用户信息正在审核中,暂无法转账");
//						}else if(sts.equals("4")){
//							ToastCustom.showMessage(BalanceDetailsAcitvity1.this,
//									"您的手机号修改正在审核中,暂无法转账");
//						}else{
//							
//							warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
//									R.style.CustomDialog, "提示", "为了您账户安全！请补全资料进行实名认证再进行操作", "确定",
//									new OnMyDialogClickListener() {
//										@Override
//										public void onClick(View v) {
//											Intent it = new Intent(BalanceDetailsAcitvity1.this,NewRealNameAuthenticationActivity.class);
//											startActivity(it);
//											warnDialog.dismiss();
//										}
//									});
//							warnDialog.show();
//						}
//				}else{
//					warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
//							R.style.CustomDialog, "提示",
//							"尊敬的用户,请先充值再操作", "确定",
//							new OnMyDialogClickListener() {
//								@Override
//								public void onClick(View v) {
//									Intent it = new Intent(
//											BalanceDetailsAcitvity1.this,
//											OrderPayActivity.class);
//									startActivity(it);
//									warnDialog.dismiss();
//								}
//							});
//					warnDialog.show();
//				}
//
//
//				// ToastCustom.showMessage(BalanceDetailsAcitvity1.this, "即将开通");
			}
		});
	}
	
	
	protected void shwpaypwd(final String num) {
			doubleWarnDialog1 = new MyEditDialog(BalanceDetailsAcitvity1.this,
					R.style.MyEditDialog, "宝币兑换", "请输入支付密码", "确认", "取消", "",
					new OnMyDialogClickListener() {

						@Override
						public void onClick(View v) {

							switch (v.getId()) {
							case R.id.btn_right:
								doubleWarnDialog1.dismiss();
								InputMethodManager m=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
								m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
								break;
							case R.id.btn_left:
								String paypwd = doubleWarnDialog1.getpaypwd();

								if (paypwd == null || paypwd.equals("")) {
									Toast.makeText(getApplicationContext(),"请输入支付密码",
											Toast.LENGTH_SHORT).show();
//									ToastCustom.showMessage(
//											WithdrawalActivity.this,
//											"请输入支付密码！");
									return;
								}
								if (paypwd.length() < 6 || paypwd.length() > 15) {
//									ToastCustom.showMessage(
//											WithdrawalActivity.this,
//											"输入的密码长度有误,请输入6～15个数字、字母或特殊符号！");
									return;
								}
								break;
							default:
								break;
							}
						}

					},
			new onMyaddTextListener() {
				
				@Override
				public void refreshActivity(String paypwd) {
					
					if (paypwd == null || paypwd.equals("")) {
						Toast.makeText(getApplicationContext(),"请输入支付密码",
								Toast.LENGTH_SHORT).show();
//						ToastCustom.showMessage(
//								WithdrawalActivity.this,
//								"请输入支付密码！");
						return;
					}
					if (paypwd.length() < 6 || paypwd.length() > 15) {
//						ToastCustom.showMessage(
//								WithdrawalActivity.this,
//								"输入的密码长度有误,请输入6个数字、字母或特殊符号！");
						return;
					}
					
					IntegralexchangeTask walletinfo = new IntegralexchangeTask();

					walletinfo.execute(HttpUrls.INTEGRALEXCHANGE + "", phone,num,paypwd);
					
				}
			});
			doubleWarnDialog1.setCancelable(false);
			doubleWarnDialog1.setCanceledOnTouchOutside(false);
			doubleWarnDialog1.show();
			
	}
	
	
	class IntegralexchangeTask extends
			AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoadingDialog("正在加载...");
		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {

			String[] values = { params[0], params[1], params[2], params[3] };
			return NetCommunicate.getMidatc(HttpUrls.INTEGRALEXCHANGE, values);

		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			loadingDialogWhole.dismiss();
			if (result != null) {
				if (result.get(Entity.RSPCOD) != null
						&& result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {

					warnDialog = new OneButtonDialogWarn(
							BalanceDetailsAcitvity1.this, R.style.CustomDialog,
							"提示", result.get(Entity.RSPMSG).toString(), "确定",
							new OnMyDialogClickListener() {
								@Override
								public void onClick(View v) {
									finish();
									warnDialog.dismiss();
								}
							});
					warnDialog.show();
				} else {

					if (result.get(Entity.RSPMSG) != null) {
						// loadingDialogWhole.dismiss();

						warnDialog = new OneButtonDialogWarn(
								BalanceDetailsAcitvity1.this,
								R.style.CustomDialog, "提示", result.get(
										Entity.RSPMSG).toString(), "确定",
								new OnMyDialogClickListener() {
									@Override
									public void onClick(View v) {
										warnDialog.dismiss();
									}
								});
						warnDialog.show();
					} else {
						// loadingDialogWhole.dismiss();

						warnDialog = new OneButtonDialogWarn(
								BalanceDetailsAcitvity1.this,
								R.style.CustomDialog, "提示", "网络异常请重试", "确定",
								new OnMyDialogClickListener() {
									@Override
									public void onClick(View v) {
										warnDialog.dismiss();
									}
								});
						warnDialog.show();
					}

				}
			} else {
				Toast.makeText(getApplicationContext(), "数据获取失败,请检查网络连接",
						Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);

		}

	}
	
	
	
	class GetWalletInfo extends AsyncTask<String , Integer, HashMap<String, Object>>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			showLoadingDialog("正在加载...");
		}
		
		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			
			String[] values = { params[0], params[1] };
			return NetCommunicate
					.getMidatc(HttpUrls.RICH_TREASURE_INFO, values);
			
		}
		
		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			refreshlaout.refreshFinish(0);
			if (result != null) {
				if (result.get(Entity.RSPCOD)!=null&&result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {
//					loadingDialogWhole.dismiss();
					treasureBean = new RichTreasureBean();
					treasureBean.setLogsts(result.get("LOGSTS").toString());
					Log.e("", "result.gettoString() = = "+result.get("LOGSTS").toString());
					treasureBean.setActsts(result.get("ACTSTS").toString());
					treasureBean.setAvaamt(result.get("AVAAMT").toString());
					treasureBean.setYesterincom(result.get("YESTERINCOM")
							.toString());
					treasureBean.setTotamt(result.get("TOTAMT").toString());
					treasureBean.setFixamt(result.get("FIXAMT").toString());
					treasureBean.setCheckamt(result.get("CHECKAMT").toString());
					treasureBean.setFrzamt(result.get("FRZAMT").toString());
					treasureBean.setDptrate(result.get("DPTRATE").toString());
					treasureBean.setCumulative(result.get("CUMULATIVE")
							.toString());
//					treasureBean.setCustid(result.get("CARDID").toString());
					treasureBean.setMilincom(result.get("MILINCOM").toString());
					treasureBean.setWeekincom(result.get("WEEKINCOM")
							.toString());
					treasureBean.setMonthincom(result.get("MONTHINCOM")
							.toString());
					if(result.get("MERNAM")!=null&&!result.get("MERNAM").equals("")){
						treasureBean.setMerNam(result.get("MERNAM").toString());
					}else{
						treasureBean.setMerNam("");
					}
					treasureBean.setBanknam(result.get("BANKNAM").toString());
					treasureBean.setActcard(result.get("ACTCARD").toString());
					treasureBean.setCrdflg(result.get("CRDFLG").toString());
					treasureBean.setIsActpwout(result.get("ISACTPWOUT")
							.toString());
					((AppContext)getApplication())
							.setTreasureBean(treasureBean);
					
					
					
					if(treasureBean.getActsts().equals("0")){
						aa = "不可用";
					}else{
						aa = "可用";
						}
					
//					user_state_img.setText(treasureBean.getMerNam());
					
//					tv_name.setText(treasureBean.getMerNam());
//					if (treasureBean.getActsts().equals("0")) {
//						tv_account_state.setText("不可用");
//					} else {
//						tv_account_state.setText("可用");
//					}
					
//					double d = Double.parseDouble(treasureBean.getAvaamt());
//					String r = String .format("%.2f",d/100);
//					me_result_money.setText(r);
					if(result.get("FEEOFPOINT")!=null){
						double d = Double.parseDouble(result.get("FEEOFPOINT").toString());
						String r = String .format("%.2f",d/100);
						me_result_money1.setText(r);
					}
					if(result.get("FEEOFPRESTORE")!=null){
						double d = Double.parseDouble(result.get("FEEOFPRESTORE").toString());
						String r = String .format("%.2f",d/100);
						me_result_money3.setText(r);
					}
					if(result.get("POINT")!=null){
						
						double d = Double.parseDouble(result.get("POINT").toString());
						String r = String .format("%.2f",d/100);
						me_result_money4.setText(r);
//						me_result_money4.setText(result.get("POINT").toString());
					}
					
					
					
					if(result.get("EXTENDCOUNTBASE")!=null&&result.get("EXTENDCOUNT")!=null){
						int c = Integer.parseInt(result.get("EXTENDCOUNTBASE").toString());
						int b = Integer.parseInt(result.get("EXTENDCOUNT").toString());
						if(b==c){
							tvs_2.setText("恭喜!您已当前已享有0费率");
							tvs_1.setVisibility(View.GONE);
							tvs_3.setVisibility(View.GONE);
						}else if(b>c){
							tvs_2.setText("恭喜！您已超额贡献"+(b-c)+"个用户");
							tvs_1.setVisibility(View.GONE);
							tvs_3.setVisibility(View.GONE);
						}else {
//							int a = Integer.parseInt(result.get("EXTENDCOUNTBASE").toString())-Integer.parseInt(result.get("EXTENDCOUNT").toString());
							tvs_2.setText(c-b+"");
						}

					}
					
					double f = Double.parseDouble(treasureBean.getTotamt());
					String fstring = String .format("%.2f",f/100);
//					me_result_money.setText(d/100+"");
					me_result_money.setText(fstring);
					
					if(result.get("LEFTPOINTSTPOINTS")!=null){
							
						double a = Double.parseDouble(result.get("LEFTPOINTSTPOINTS").toString());
						if(a<=0){
							tv_exchange.setEnabled(false);
						}
						 astring = String .format("%.2f",a/100);
						me_result_money2 .setText(astring);
					}else{
						tv_exchange.setEnabled(false);
					}

					
					
//					if (treasureBean.getTotamt().length() == 1) {
//						me_result_money.setText("0.0" + treasureBean.getTotamt());
//					} else if (treasureBean.getTotamt().length() == 2) {
//						me_result_money.setText("0." + treasureBean.getTotamt());
//					} else {
//						me_result_money.setText(treasureBean.getTotamt().substring(0,
//								treasureBean.getTotamt().length() - 2)
//								+ "."
//								+ treasureBean.getTotamt().substring(
//										treasureBean.getTotamt().length() - 2));
//					}
					
					
//					if (treasureBean.getCumulative().length() == 1) {
//						me_profit_money.setText("0.0"
//								+ treasureBean.getCumulative());
//					} else if (treasureBean.getCumulative().length() == 2) {
//						me_profit_money
//								.setText("0." + treasureBean.getCumulative());
//					} else {
//						me_profit_money.setText(treasureBean.getCumulative()
//								.substring(0, treasureBean.getCumulative().length() - 2)
//								+ "."
//								+ treasureBean.getCumulative().substring(
//										treasureBean.getCumulative().length() - 2));
//					}
					
					double c = Double.parseDouble(treasureBean.getCumulative());
					String rc = String .format("%.2f",c/100);
					me_profit_money.setText(rc);
//					if (mList.size() == 0) {
						// 加载数据
//					try {
//						mList.clear();
//						loadMore();
//					} catch (Exception e) {
//						// TODO: handle exception
//						Toast.makeText(getApplicationContext(), "网络不给力,请重试", Toast.LENGTH_SHORT).show();
//						finish();
//					}
						
//						}else{
//							adapter.notifyDataSetChanged();
//						}

					
					if (treasureBean.getLogsts().equals("1")) {
						
					} else if (treasureBean.getLogsts().equals("0")) {
//						loadingDialogWhole.dismiss();
						Toast.makeText(getApplicationContext(), "账户暂未开通该功能",
								Toast.LENGTH_SHORT).show();
					} else {
//						loadingDialogWhole.dismiss();
						Toast.makeText(getApplicationContext(), "账户暂未开通该功能",
								Toast.LENGTH_SHORT).show();
//						ToastCustom.showMessage(BalanceDetailsAcitvity1.this, "账户暂未开通该功能!"
//								+ treasureBean.getLogsts());
					}
				} else {
					
					if(result.get(Entity.RSPCOD).equals("02042")){
						warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
								R.style.CustomDialog, "提示", "手机号变更成功,请重新登录",
								"确定", new OnMyDialogClickListener() {
									@Override
									public void onClick(View v) {
										AppContext.getInstance().exit();
										Intent it = new Intent(BalanceDetailsAcitvity1.this,
												UserActivity.class);
										startActivity(it);
										warnDialog.dismiss();
									}
								});
						warnDialog.setCancelable(false);
						warnDialog.setCanceledOnTouchOutside(false);
						warnDialog.show();
						return;
					}
					
					if( result.get(Entity.RSPMSG)!=null){
//						loadingDialogWhole.dismiss();
						
						warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
								R.style.CustomDialog, "提示", result.get(Entity.RSPMSG).toString(), "确定",
								new OnMyDialogClickListener() {
									@Override
									public void onClick(View v) {
										finish();
										warnDialog.dismiss();
									}
								});
						warnDialog.show();
					}else{
//						loadingDialogWhole.dismiss();
						
						warnDialog = new OneButtonDialogWarn(BalanceDetailsAcitvity1.this,
								R.style.CustomDialog, "提示", "网络异常请重试", "确定",
								new OnMyDialogClickListener() {
									@Override
									public void onClick(View v) {
										finish();
										warnDialog.dismiss();
									}
								});
						warnDialog.show();
					}

//					ToastCustom.showMessage(RichTreasureActivity.this,
//							result.get(Entity.RSPMSG).toString());
				}
			} else {
				Toast.makeText(getApplicationContext(), "数据获取失败,请检查网络连接",
						Toast.LENGTH_SHORT).show();
//				ToastCustom.showMessage(BalanceDetailsAcitvity1.this, "数据获取失败,请检查网络连接");
//				loadingDialogWhole.dismiss();
			}
			super.onPostExecute(result);
			
				
		}
		
	}
	
	private void loadMore() {
//		if (page != 1 && page > allPageNum) {
////			ToastCustom.showMessage(this, "没有更多记录了");
//			return;
//		}
		if (!isThreadRun) {
			isThreadRun = true;
			showLoadingDialog("正在查询中...");
			new Thread(run).start();
		}

	}
	
	Runnable run = new Runnable() {

		@Override
		public void run() {
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			String[] values = { String.valueOf(HttpUrls.FINANCIALPRODUCTS),phone,page+"",PAGE_SIZE+""};
			MoneyListBean entitys = NetCommunicate.getMoneyListBean(
					HttpUrls.FINANCIALPRODUCTS, values);

			Message msg = new Message();
			if (entitys != null) {
				list = entitys.list;
						mList.addAll(list);
					if(mList.size()<=0||mList==null){
						
						msg.what = 2;
					}else{
						msg.what = 1;
					}

					page++;
			} else {
				loadingDialogWhole.dismiss();
				msg.what = 3;
			}
			isThreadRun = false;
			loadingDialogWhole.dismiss();
			handler.sendMessage(msg);
		}
	};

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			refreshlaout.refreshFinish(0);
			switch (msg.what) {
			case 1:
				
				adapter.notifyDataSetChanged();
				break;
			case 2:
				
				Toast.makeText(getApplicationContext(),"理财产品暂无更新",
						Toast.LENGTH_SHORT).show();
//				ToastCustom.showMessage(RateListActivity.this,
//						"没有获取到您的订单信息");
				break;
			case 3:
				Toast.makeText(getApplicationContext(),"网络异常，请检查网络设置",
						Toast.LENGTH_SHORT).show();
//				ToastCustom.showMessage(RateListActivity.this,
//						"订单信息获取失败");
				break;
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(loadingDialogWhole!=null){
		loadingDialogWhole.dismiss();
		}
	}
	
	protected void doubleWarnOnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			doubleWarnDialog.dismiss();

		break;
		case R.id.btn_right:
			IntegralexchangeTask walletinfo = new IntegralexchangeTask();

			walletinfo.execute(HttpUrls.INTEGRALEXCHANGE + "", phone,"","");
			doubleWarnDialog.dismiss();
			break;
		default:
			break;
		}
	}
}
