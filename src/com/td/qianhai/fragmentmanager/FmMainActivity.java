package com.td.qianhai.fragmentmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.mime.Header;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.td.qianhai.epay.bb.BalanceDetailsAcitvity1;
import com.td.qianhai.epay.bb.MenuActivity;
import com.td.qianhai.epay.bb.R;
import com.td.qianhai.epay.bb.RichTreasureDealRecordsActivity;
import com.td.qianhai.epay.bb.ScreeningActivity;
import com.td.qianhai.epay.bb.UserActivity;
import com.td.qianhai.epay.bb.advertising.AdGallery;
import com.td.qianhai.epay.bb.advertising.AdGalleryHelper;
import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.Entity;
import com.td.qianhai.epay.bb.beans.HttpKeys;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.contacts1.SortModel;
import com.td.qianhai.epay.bb.mail.utils.GetImageUtil;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;
import com.td.qianhai.epay.bb.unlock.StringUtil;
import com.td.qianhai.epay.bb.unlock.UnlockLoginActivity;
import com.td.qianhai.epay.bb.views.SelectPicPopupWindow;
import com.td.qianhai.epay.bb.views.SystemBarTintManager;
import com.td.qianhai.epay.bb.views.dialog.ButtonDialogStyleNotice;
import com.td.qianhai.epay.bb.views.dialog.OneButtonDialogWarn;
import com.td.qianhai.epay.bb.views.dialog.TwoButtonDialogStyle2;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnMyDialogClickListener;
import com.td.qianhai.epay.utils.AppManager;

public class FmMainActivity extends FragmentActivity {
	/**
	 * Called when the activity is first created.
	 */
	private RadioGroup rgs;
	public List<Fragment> fragments = new ArrayList<Fragment>();

//	private  TextView title_name;
	
	public String hello = "hello ";
	
	public static Activity context;
	
	private ScreenBroadcastReceiver mScreenReceiver;
	
	private TwoButtonDialogStyle2 doubleWarnDialog;
	
	private HashMap<String, Object> result = null; 
	
	public static  LinearLayout  my_remind;
	
	private Editor editor;
	
	private String mobilelist = "";
	
	private OneButtonDialogWarn warnDialog;
	
	int i = 0;
	
	//测试图片

	private RelativeLayout adContainer;
	private AdGalleryHelper adGalleryHelper;
	private AdGallery adGallery;
	private TextView bt_main_title_right,tv_level;
	private FragmentTabAdapter tabAdapter;
	private RadioButton tab_host_work,tab_host_production,tab_host_semi;
	private List<RadioButton> radioview;
	private String tagsts,isretailers,isareaagent,issaleagt,isgeneralagent,isvip,personpic,phone="",mercnum;
	private SharedPreferences share;
	private ImageView headimg;
	private int mBorderWidth = 2;  
	private int mBorderColor = Color.parseColor("#f2f2f2"); 
	private SelectPicPopupWindow menuWindow;
	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private static final String PHOTO_FILE_NAME = "photo.jpg";
	private String idcardpic = "",wecname,wecimg;
	private File tempFile;
	private File cardPicFile ;
	private String content = "",time = "";
	private ButtonDialogStyleNotice noticedialog;
	private RelativeLayout bt_main_title_right1;
	private boolean isrun = false;
	private List<String> list;
	private List<String> listnum;
	 MyHandler myHandler;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		context = this;
		AppContext.getInstance().addActivity(this);
		editor = MyCacheUtil.setshared(context);
		share = MyCacheUtil.getshared(context);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
//		  myHandler=MyHandler(context.getMainLooper());

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.apptitle);
		
		personpic = MyCacheUtil.getshared(this).getString("PERSONPIC", "");
		isretailers = MyCacheUtil.getshared(this).getString("ISRETAILERS", "");
		issaleagt = MyCacheUtil.getshared(this).getString("ISSALEAGT", "");
		isareaagent = MyCacheUtil.getshared(this).getString("ISAREAAGENT", "");
		isgeneralagent = MyCacheUtil.getshared(this).getString("ISGENERALAGENT", "");
		phone = MyCacheUtil.getshared(this).getString("Mobile", "");
		mercnum = MyCacheUtil.getshared(this).getString("MercNum", "");
		isvip = MyCacheUtil.getshared(this).getString("ISSENIORMEMBER", "");
		content = MyCacheUtil.getshared(this).getString("NCONTENT", "");
		time = MyCacheUtil.getshared(this).getString("NCREATETIM", "");
		wecname = MyCacheUtil.getshared(this).getString("NICKNAME", "");
		wecimg = MyCacheUtil.getshared(this).getString("HEADIMGURL", "");
//		AppContext.getInstance().addActivity(context);
		tagsts = MyCacheUtil.getshared(this).getString("STS", "");
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				if(content==null||content.equals("")){

				}else{
					SpannableString msps = new SpannableString("");
					showDoubleWarnDialogs(msps,content,time);
				}
			}
		}, 2000);
//		if(MainActivity.cache.getString("isone")==null||MainActivity.cache.getString("isone").equals("")){
//			MainActivity.cache.putString("isone","1");
//			MainActivity.cache.commit();
//		}else{
//		if(share.getBoolean("isfirst",false)==false){
//			warnDialog = new OneButtonDialogWarn(FmMainActivity.this,
//					R.style.CustomDialog, "提示", "理财需主动购买才会产生收益",
//					"确定", new OnMyDialogClickListener() {
//						@Override
//						public void onClick(View v) {
//							warnDialog.dismiss();
//						}
//					});
//			warnDialog.setCancelable(false);
//			warnDialog.setCanceledOnTouchOutside(false);
//			warnDialog.show();
//		}
		editor.putBoolean("isfirst", true);
		editor.commit();
//		}

		startScreenBroadcastReceiver();
		headimg = (ImageView) findViewById(R.id.head_por);
		bt_main_title_right = (TextView) findViewById(R.id.bt_main_title_right);
		bt_main_title_right1 = (RelativeLayout) findViewById(R.id.bt_main_title_right1);
		tab_host_semi = (RadioButton) findViewById(R.id.tab_host_semi);
		tab_host_work = (RadioButton) findViewById(R.id.tab_host_work);
		tv_level = (TextView) findViewById(R.id.tv_level);
		tab_host_production = (RadioButton) findViewById(R.id.tab_host_production);
		radioview = new ArrayList<RadioButton>();
		radioview.add(tab_host_work);
		radioview.add(tab_host_production);
		radioview.add(tab_host_semi);
		
		my_remind = (LinearLayout) findViewById(R.id.my_remind);
//		if(tagsts.equals("2")){
//			my_remind.setVisibility(View.VISIBLE);
//			new Handler().postDelayed(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					AnimationUtil.BtnSpecialAnmations1(FmMainActivity.this, my_remind,700);
//				}
//			}, 2000);
//			
//		}else{
//			my_remind.setVisibility(View.GONE);
//		}
		setfirstimg();
		
		if(isareaagent!=null){
			if(isareaagent.equals("1")){
				tv_level.setText("区级代理");
			}else if(isareaagent.equals("2")){
				tv_level.setText("市级代理");
			}else if(isareaagent.equals("3")){
				tv_level.setText("省级代理");
			}else if(isareaagent.equals("4")){
				tv_level.setText("大区代理");
			}else if(isareaagent.equals("5")){
				tv_level.setText("全国代理");
			}
		}else{
			if(wecname!=null){
				tv_level.setText(wecname);
			}
		}
		

		
//		if(isareaagent!=null&&isareaagent.equals("1")){
//			tv_level.setText("省级代理");
//		}else if(isareaagent!=null&&isareaagent.equals("2")){
//			tv_level.setText("市级代理");
//		}else if(isareaagent!=null&&isareaagent.equals("3")){
//			tv_level.setText("区级代理");
//		}else{
//			if(isgeneralagent.equals("1")){
//				tv_level.setText("代理商");
//			}else{
//				if(issaleagt.equals("1")){
//					tv_level.setText("分销商");
//				}else{
//					if(isretailers.equals("1")){
//						tv_level.setText("零售商");
//					}else{
//						if(tagsts.equals("3")||tagsts.equals("0")||tagsts.equals("4")){
//							if(isvip.equals("1")){
//								tv_level.setText("高级会员");
//							}else{
//								tv_level.setText("普通会员");
//							}
//							
//						}else{
//							tv_level.setText("未实名认证");
//						}
//					}
//				}
//			}
//		}
		
		headimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(phone!=null){
					setheadimg();
				}else{
//					Toast.makeText(getApplicationContext(), "不可更改", Toast.LENGTH_SHORT).show();
				}
				
			}
		});

		bt_main_title_right.setVisibility(View.GONE);
//		bt_main_title_right1.setVisibility(View.VISIBLE);
		bt_main_title_right1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent   = new Intent();
				intent.setClass(context,
				RichTreasureDealRecordsActivity.class);
				startActivity(intent);
//				
//				SpannableString msps = new SpannableString("确定退出?");
//				showDoubleWarnDialog(msps);
			}
		});
//		title_name = (TextView) findViewById(R.id.title_names);xl
		fragments.add(new MenuActivity());
		fragments.add(new TabAFm());
		fragments.add(new TabBFm());
		fragments.add(new TabCFm());
		// fragments.add(new TabEFm());
		rgs = (RadioGroup) findViewById(R.id.tabs_rg);

		 tabAdapter = new FragmentTabAdapter(this, fragments,
				R.id.tab_content, rgs,radioview);
		tabAdapter
				.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
					@Override
					public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,
							int checkedId, int index) {
						System.out.println("Extra---- " + index
								+ " checked!!! ");
					}
				});
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				loadContacts();
			}
		}, 3000);
		
	}
	
	


	@TargetApi(19) 
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		AppContext.isunlock = false;
		personpic = MyCacheUtil.getshared(this).getString("PERSONPIC", "");
		isretailers = MyCacheUtil.getshared(this).getString("ISRETAILERS", "");
		issaleagt = MyCacheUtil.getshared(this).getString("ISSALEAGT", "");
		isareaagent = MyCacheUtil.getshared(this).getString("ISAREAAGENT", "");
		isgeneralagent = MyCacheUtil.getshared(this).getString("ISGENERALAGENT", "");
		phone = MyCacheUtil.getshared(this).getString("Mobile", "");
		isvip = MyCacheUtil.getshared(this).getString("ISSENIORMEMBER", "");
		content = MyCacheUtil.getshared(this).getString("NCONTENT", "");
		time = MyCacheUtil.getshared(this).getString("NCREATETIM", "");
		wecname = MyCacheUtil.getshared(this).getString("NICKNAME", "");
		wecimg = MyCacheUtil.getshared(this).getString("HEADIMGURL", "");
//		AppContext.getInstance().addActivity(context);
		tagsts = MyCacheUtil.getshared(this).getString("STS", "");
	}

	private boolean isExit;

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//
//			if(tabAdapter.getCurrentTab()==0){
//				if (!isExit) {
//					isExit = true;
//					Toast.makeText(getApplicationContext(), "再按一次退出程序",
//							Toast.LENGTH_SHORT).show();
//					new Handler().postDelayed(new Runnable() {
//						public void run() {
//							isExit = false;
//						}
//					}, 2000);
//					;
//					return false;
//				} else {
//					((AppContext) getApplication()).setCustId(null); // 商户ID赋为空
//					((AppContext) getApplication()).setPsamId(null);
//					((AppContext) getApplication()).setMacKey(null);
//					((AppContext) getApplication()).setPinKey(null);
//					((AppContext) getApplication()).setMerSts(null);
//					((AppContext) getApplication()).setMobile(null);
//					((AppContext) getApplication()).setEncryPtkey(null);
//					((AppContext) getApplication()).setStatus(null);
//					((AppContext) getApplication()).setCustPass(null);
//					((AppContext) getApplication()).setVersionSerial(null);
//					AppContext.getInstance().setStateaudit(null);
//					editor.putString("MERSTS","");
//					editor.putString("AGENTID","");
//					editor.putString("PERSONPIC","");
//					editor.commit();
//					AppManager.getAppManager().AppExit(FmMainActivity.this);
//					AppContext.getInstance().exit();
//			}
//			
//			}else{
//				tabAdapter.showTab(0);
//				tab_host_work.setChecked(true);
//			}
//			return false;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
	
	 @Override
	 public void onBackPressed()
	 {
	    //按返回键返回桌面
	    moveTaskToBack(true);
	 }
	
	
//	/**
//	 * 监听黑屏
//	 *
//	 */
	public class ScreenBroadcastReceiver extends BroadcastReceiver{
		 private String action = null;
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
	        action = intent.getAction();
	        if (Intent.ACTION_SCREEN_ON.equals(action)) {
	            // 开屏
	        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { 
	        	if(StringUtil.isEmpty(share.getString("usermobile",""))){
	        		
	        	}else{
	        		
	        		
//	        		Log.e("", "isunlock = = "+AppContext.isunlock);
//	        		if(!AppContext.isunlock){
//			        	Intent it = new Intent(FmMainActivity.this,UnlockLoginActivity.class);
//			        	it.putExtra("islogin", "1");
//			        	AppContext.isunlock = true;
//			        	startActivity(it);
//	        		}

	        	}
	            // 锁屏
	        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
	            // 解锁
	        }
			
		}
	}
	
	
	private void startScreenBroadcastReceiver() {
		 mScreenReceiver = new ScreenBroadcastReceiver();
	    IntentFilter filter = new IntentFilter();
	    filter.addAction(Intent.ACTION_SCREEN_ON);
	    filter.addAction(Intent.ACTION_SCREEN_OFF);
	    filter.addAction(Intent.ACTION_USER_PRESENT);
	    getApplication().registerReceiver(mScreenReceiver, filter);
	}
//	
	
	public void setheadimg(){
		menuWindow = new SelectPicPopupWindow(context, itemsOnClick);
		LinearLayout lin = (LinearLayout)context.findViewById(R.id.fm_main);
		menuWindow.showAtLocation(lin,
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		menuWindow.setbutton2("图库");
		menuWindow.settitile();
//	    WindowManager.LayoutParams params=this.getWindow().getAttributes();  
//	    params.alpha=0.7f;  
//	    this.getWindow().setAttributes(params);
	}
	
	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_take_photo:
				camera();
				break;
			case R.id.btn_pick_photo:
				gallery();
				break;
			default:
				break;
			}
		}
	};
	
	
	/*
	 * 从相册获取
	 */
	public void gallery() {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}
	/*
	 * 从相机获取
	 */
	public void camera() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
//		if (hasSdcard()) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
//		}
		startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
	}
	
	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	private void setfirstimg(){
		Bitmap bitmap = null;
//		
		if (personpic==null||personpic.equals("")) {
			if(wecimg!=null||wecimg.equals("")){
				new GetImageUtil(this, headimg,wecimg);
				return;
			}
			Resources res = getResources();
			bitmap = BitmapFactory.decodeResource(res, R.drawable.head_portrait);
			headimg.setImageBitmap(getRoundedCornerBitmap(bitmap));
		}else{
			new GetImageUtil(this, headimg,HttpUrls.HOST_POSM+personpic);
//			new ImageLoadTask(this).execute(HttpUrls.HOST_POSM+personpic);
		}

		}
		
		/**
		 * 圆形图片裁剪
		 * @param bitmap
		 * @return
		 */
		private  Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
	        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);

	        final Paint paint = new Paint();
	        //保证是方形，并且从中心画
	        int width = bitmap.getWidth();
	        int height = bitmap.getHeight();
	        int w;
	        int deltaX = 0;
	        int deltaY = 0;
	        if (width <= height) {
	            w = width;
	            deltaY = height - w;
	        } else {
	            w = height;
	            deltaX = width - w;
	        }
	        final Rect rect = new Rect(deltaX, deltaY, w, w);
	        final RectF rectF = new RectF(rect);

	        paint.setAntiAlias(true);
	        canvas.drawARGB(0, 0, 0, 0);
	        //圆形，所有只用一个
	        
	        int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
	        canvas.drawRoundRect(rectF, radius, radius, paint);

	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	        canvas.drawBitmap(bitmap, rect, rect, paint);
	        drawBorder(canvas, width, height); 
	        return output;
	    }
		
		private void drawBorder(Canvas canvas, final int width, final int height) {  
	        if (mBorderWidth == 0) {  
	            return;  
	        }  
	        final Paint mBorderPaint = new Paint();  
	        mBorderPaint.setStyle(Paint.Style.STROKE);  
	        mBorderPaint.setAntiAlias(true);  
	        mBorderPaint.setColor(mBorderColor);  
	        mBorderPaint.setStrokeWidth(mBorderWidth);  
	        /** 
	         * 坐标x：view宽度的一般 坐标y：view高度的一般 半径r：因为是view的宽度-border的一半 
	         */  
	        canvas.drawCircle(width >> 1, height >> 1, (width - mBorderWidth) >> 1, mBorderPaint);  
	        canvas = null;  
	    }  
		
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getApplication().unregisterReceiver(mScreenReceiver);  
	}
	/***
	 * 双按钮提示dialog
	 * 
	 * @param msg
	 */
	protected void showDoubleWarnDialog(SpannableString msg) {
		 doubleWarnDialog = new TwoButtonDialogStyle2(context,
				R.style.CustomDialog, "提示", msg, "退出帐号", "退出应用",
				new OnMyDialogClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						doubleWarnOnClick(v);
					}
				});
		doubleWarnDialog.setCancelable(true);
//		doubleWarnDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//			@Override
//			public boolean onKey(DialogInterface dialog, int keyCode,
//					KeyEvent event) {
//				if (keyCode == KeyEvent.KEYCODE_SEARCH) {
//					return true;
//				} else {
//					return true; // 默认返回 false
//				}
//			}
//		});
		doubleWarnDialog.setCanceledOnTouchOutside(true);
		doubleWarnDialog.show();
	}
	
	protected void doubleWarnOnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			doubleWarnDialog.dismiss();
			AppContext.getInstance().setCustId(null);
			AppContext.getInstance().setPsamId(null);
			AppContext.getInstance().setMacKey(null);
			AppContext.getInstance().setPinKey(null);
			AppContext.getInstance().setMerSts(null);
//			AppContext.getInstance().setMobile(null);
			AppContext.getInstance().setEncryPtkey(null);
			AppContext.getInstance().setStatus(null);
			AppContext.getInstance().setCustPass(null);
			AppContext.getInstance().setVersionSerial(null);
			AppContext.getInstance().setStateaudit(null);
			AppContext.getInstance().exit();
		break;
		case R.id.btn_right:
			AppContext.getInstance().setCustId(null);
			AppContext.getInstance().setPsamId(null);
			AppContext.getInstance().setMacKey(null);
			AppContext.getInstance().setPinKey(null);
			AppContext.getInstance().setMerSts(null);
//			AppContext.getInstance().setMobile(null);
			AppContext.getInstance().setUsername(null);
			AppContext.getInstance().setEncryPtkey(null);
			AppContext.getInstance().setStatus(null);
			AppContext.getInstance().setCustPass(null);
			AppContext.getInstance().setVersionSerial(null);
			AppContext.getInstance().setStateaudit(null);
			doubleWarnDialog.dismiss();
			AppContext.getInstance().exit();
			
			Intent it = new Intent(context,UserActivity.class);
			startActivity(it);
			break;
		default:
			break;
		}
	}
	



//	@Override
//	protected void onResume() {
//		isForeground = true;
//		super.onResume();
//	}
//
//
//	@Override
//	protected void onPause() {
//		isForeground = false;
//		super.onPause();
//	}
//
//
//	@Override
//	protected void onDestroy() {
//		unregisterReceiver(mMessageReceiver);
//		super.onDestroy();
//	}
	
//	/**
//	 * 测试：使用AdGlleryHelper来实现对外提供自定义的布局视图
//	 */
//	public void realizeFunc2(){
//		adContainer = (RelativeLayout)findViewById(R.id.ad_container);
//		adGalleryHelper = new AdGalleryHelper(this, dataids, 3000,true);
//		adContainer.addView(adGalleryHelper.getLayout());
//		adGallery = adGalleryHelper.getAdGallery();
//		adGallery.setAdOnItemClickListener(this);
//	}
//	
//	/**
//	 * 实现AdGallery的OnAdItemOnClickListener
//	 */
//	@Override
//	public void setItemClick(int position) {
//		System.out.println("you had clicked position="+position);
		//调用系统浏览器访问对应广告图的链接
//		Intent intent= new Intent();        
//	    intent.setAction("android.intent.action.VIEW");    
//	    Uri content_url = Uri.parse(data[position].getLinkUrl());   
//	    intent.setData(content_url);  
//	    startActivity(intent);
//	}
//	@Override
//	public void onDestroy() {
//		if(adGalleryHelper!=null){
//			adGalleryHelper.stopAutoSwitch();
//		}
//		super.onDestroy();
//	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		idcardpic = phone +"_" + getStringDateMerge() + ".jpg";
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if (data != null) {
				
				
				// 得到图片的全路径
				Uri uri = data.getData();
				crop(uri);
				

				
				
			}

		} else if (requestCode == PHOTO_REQUEST_CAMERA) {
			if (hasSdcard()) {
			 tempFile = new File(Environment.getExternalStorageDirectory(),
						PHOTO_FILE_NAME);
				crop(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(context, "未找到存储卡，无法存储照片！", 0).show();
			}

		} else if (requestCode == PHOTO_REQUEST_CUT) {
			if (data != null) {

			
			try {
				Bitmap bitmap = data.getParcelableExtra("data");
				headimg.setImageBitmap(getRoundedCornerBitmap(bitmap));
//				boolean delete = tempFile.delete();
				
				File file = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/myImage/");
				file.mkdirs();// 创建文件夹
				String fileName = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/myImage/"+idcardpic;
				FileOutputStream b = null;
				try {
					b = new FileOutputStream(fileName);
					Log.e("", ""+fileName);

					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				
				cardPicFile = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/myImage/",idcardpic);
				
				RealNameAuthTask task = new RealNameAuthTask();

				task.execute(HttpUrls.AVATARUPLOAD + "",phone, idcardpic);
			}
		}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public String getStringDateMerge() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 125);
		intent.putExtra("outputY", 125);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}
	
	
	/***
	 * 双按钮提示dialog
	 * 
	 * @param msg
	 */
	protected void showDoubleWarnDialogs(SpannableString msg,String content,String info) {
		noticedialog = new ButtonDialogStyleNotice(context,
				R.style.CustomDialog, "提示", msg,content,info,"我知道了", "我知道了",
				new OnMyDialogClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						doubleWarnOnClicks(v);
					}
				});
		noticedialog.setCancelable(false);
//		doubleWarnDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//			@Override
//			public boolean onKey(DialogInterface dialog, int keyCode,
//					KeyEvent event) {
//				if (keyCode == KeyEvent.KEYCODE_SEARCH) {
//					return true;
//				} else {
//					return true; // 默认返回 false
//				}
//			}
//		});
		noticedialog.setCanceledOnTouchOutside(false);
		noticedialog.show();
	}
	
	
	protected void doubleWarnOnClicks(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			noticedialog.dismiss();
		break;
		case R.id.btn_right:
			noticedialog.dismiss();
			break;
		default:
			break;
		}
	}
	
	
	class RealNameAuthTask extends
			AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			String[] values = { params[0], params[1], params[2] };
			File[] files = { cardPicFile };
			return NetCommunicate.getUpload(HttpUrls.AVATARUPLOAD,
			// 198110,
					values, files);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			if (result != null) {
				if (result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {
					Toast.makeText(getApplicationContext(),
							result.get(Entity.RSPMSG).toString(),
							Toast.LENGTH_SHORT).show();

				} else {
					Toast.makeText(getApplicationContext(),
							result.get(Entity.RSPMSG).toString(),
							Toast.LENGTH_SHORT).show();
				}
			}
			super.onPostExecute(result);
		}
	}
	
//	Runnable run = new Runnable() {
//
//		@Override
//		public void run() {
//			ArrayList<HashMap<String, Object>> list = null;
//			
//			try {
//				
//				String[] values = {mobile};
//				result = NetCommunicate.executeHttpPostnulls(HttpUrls.SCREENINGURL,
//						HttpKeys.SCREENINGURL_BACK,HttpKeys.SCREENINGURL_ASK,values);
//			} catch (HttpHostConnectException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ClientProtocolException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalStateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			Message msg = new Message();
//			if(result!=null){
//				if(result.get("flag")!=null&&result.get("flag").toString().equals("00")){
//					msg.what = 1;
//				}else if(result.get("flag")!=null&&result.get("flag").toString().equals("03")){
//					msg.what = 2;
//				}else{
//					msg.what = 5;
//				}
//			}else{
//				msg.what = 5;
//			}
//			loadingDialogWhole.dismiss();
//			handler.sendMessage(msg);
//			
//		}
//	};
//	
//	private Handler handler = new Handler() {
//	public void handleMessage(Message msg) {
//		switch (msg.what) {
//		case 1:
//			re_qrcode.setVisibility(View.VISIBLE);
//			lin_edit.setVisibility(View.GONE);
//			if(result.get("qrcode_url")!=null){
//				setimg(result.get("qrcode_url").toString());
//			}
//			
//			break;
//		case 2:
//			lin_edit.setVisibility(View.VISIBLE);
//			
//			break;
//		case 5:
//			warnDialog = new OneButtonDialogWarn(ScreeningActivity.this,
//					R.style.CustomDialog, "提示", result.get("message").toString(), "确定",
//					new OnMyDialogClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							finish();
//							warnDialog.dismiss();
//						}
//					});
//			warnDialog.show();
//			break;
//		default:
//			break;
//		}
//	};
//};
	private void  loadContacts1(){
		list = new ArrayList<String>();
		for (int i = 0; i < 3000; i++) {
		
		list.add("13164745006");

		
		}
		for (int j = 0; j < list.size(); j++) {
			 mobilelist =mobilelist+ list.get(j)+",";
		}
		 load1();
	}
	
private void loadContacts() {
	list = new ArrayList<String>();
	new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				ContentResolver resolver = getApplicationContext().getContentResolver();
				Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, new String[] { Phone.DISPLAY_NAME, Phone.NUMBER, "sort_key" }, null, null, "sort_key COLLATE LOCALIZED ASC");
				if (phoneCursor == null || phoneCursor.getCount() == 0) {
					Toast.makeText(getApplicationContext(), "未获得读取联系人权限 或 未获得联系人数据", Toast.LENGTH_SHORT).show();
					return;
				}
				int PHONES_NUMBER_INDEX = phoneCursor.getColumnIndex(Phone.NUMBER);
				int PHONES_DISPLAY_NAME_INDEX = phoneCursor.getColumnIndex(Phone.DISPLAY_NAME);
				int SORT_KEY_INDEX = phoneCursor.getColumnIndex("sort_key");
				int a = 0;
				if (phoneCursor.getCount() > 0) {
					
					while (phoneCursor.moveToNext()) {
						String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
						if (TextUtils.isEmpty(phoneNumber))
							continue;
						mobilelist = mobilelist+phoneNumber.replace(" ", "")+",";
						
					}
				}
//				if(phoneCursor.getCount() > 500){
//					list = new ArrayList<String>();
//					while (phoneCursor.moveToNext()) {
//						String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
//						if (TextUtils.isEmpty(phoneNumber))
//							continue;
//						list.add(phoneNumber.replace(" ", ""));
//
////						mobilelist = mobilelist+phoneNumber.replace(" ", "")+",";
//						
//					}
//					listnum = new ArrayList<String>();
//					String plist1 = "";
//					String plist2 = "";
//					String plist3 = "";
//					String plist4 = "";
//					String plist5 = "";
//					String plist6 = "";
//					for (int i = 0; i < list.size(); i++) {
//						if(i<500){
//							plist1 = plist1+list.get(i)+",";
//						}else if(i>=500&&i<1000){
//							plist2 = plist2+list.get(i)+",";
//						}else if(i>=1000&&i<=1500){
//							plist3 = plist3+list.get(i)+",";
//						}else if(i>=1500&&i<=2000){
//							plist4 = plist4+list.get(i)+",";
//						}else if(i>=2000&&i<=2500){
//							plist5 = plist5+list.get(i)+",";
//						}else if(i>=2500&&i<=3000){
//							plist6 = plist6+list.get(i)+",";
//						}
//						
//					}
//					if(!plist1.equals("")){
//						listnum.add(plist1);
//					}
//					if(!plist2.equals("")){
//
//						listnum.add(plist2);
//					}
//					if(!plist3.equals("")){
//						listnum.add(plist3);
//					}
//					if(!plist4.equals("")){
//						listnum.add(plist4);
//					}
//					if(!plist5.equals("")){
//						listnum.add(plist5);
//					}
//					if(!plist6.equals("")){
//						listnum.add(plist6);
//					}
//				}
					
				phoneCursor.close();
				runOnUiThread(new Runnable() {
					public void run() {
//						if(listnum==null){
							load1();
//						}else{
////							for (int i = 0; i < listnum.size(); i++) {
//								Log.e("", "numm = =  = = = "+ listnum.size());
//										
//										 final Timer timer = new Timer();
//									      TimerTask task = new TimerTask(){
//									         public void run(){
//									        	 mobilelist = listnum.get(i);
//									        	 Log.e("", "i = = = = "+i);
//									        	 i++;
//									        	 load1();
//									        	 if(i==listnum.size()){
//									        		 timer.cancel();
//									        	 }
//									        	
//									         }
//									      };
////									       
//									      timer.schedule(task, 2000,2000);
//									      
////							}
//						}
						
//						Collections.sort(mAllContactsList, pinyinComparator);
//						adapter.updateListView(mAllContactsList);
						
					}
				});
//				}
			} catch (Exception e) {
				Log.e("xbc", e.getLocalizedMessage());
			}
		}
	}).start();
}

private void load1() {
	// TODO Auto-generated method stub
	
//    MyThread m = new MyThread();
//        new Thread(m).start();
	
//	if(isrun == false){
//		isrun =true;
		new Thread(runs).start();
//	}
	
//	SyncThread st1 = new SyncThread(); 
//	new Thread(st1).start();
	
}
	
Runnable runs = new Runnable() {

	@Override
	public void run() {
		ArrayList<HashMap<String, Object>> list = null;
		
		try {
			Log.e("", ""+mobilelist);
			String[] values = {mercnum,mobilelist};
			result = NetCommunicate.HttpPostnulls(HttpUrls.SYNCFRIEND,
					HttpKeys.SYNCFRIEND_BACK,HttpKeys.SYNCFRIEND_ASK,values);
		} catch (HttpHostConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Message msg = new Message();
		if(result!=null){
			if(result.get("RSPCOD")!=null&&result.get("RSPCOD").toString().equals("000000")){
				msg.what = 1;
			}else if(result.get("flag")!=null&&result.get("flag").toString().equals("03")){
				msg.what = 2;
			}else{
				msg.what = 5;
			}
		}else{
			msg.what = 5;
		}
		isrun = false;
		handler.sendMessage(msg);
		
	}
};

private Handler handler = new Handler() {
public void handleMessage(Message msg) {
	switch (msg.what) {
	case 1:
		
		break;
	case 2:
		
		break;
	case 5:
//		warnDialog = new OneButtonDialogWarn(context,
//				R.style.CustomDialog, "提示", result.get("message").toString(), "确定",
//				new OnMyDialogClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						finish();
//						warnDialog.dismiss();
//					}
//				});
//		warnDialog.show();
		break;
	default:
		break;
	}
};
};

	class MyHandler extends Handler {
          public MyHandler() {
          }
   
          public MyHandler(Looper L) {
              super(L);          }
 
        // 必须重写这个方法，用于处理message
          @Override
          public void handleMessage(Message msg) {
              // 这里用于更新UI
              Bundle b = msg.getData();
          }
      }
  
      class MyThread implements Runnable {
          public void run() {
              //从消息池中取出一个message
//              Message msg = myHandler.obtainMessage();
          	ArrayList<HashMap<String, Object>> list = null;
    		
    		try {
    			
    			String[] values = {mercnum,mobilelist};
    			result = NetCommunicate.executeHttpPostnulls(HttpUrls.SYNCFRIEND,
    					HttpKeys.SYNCFRIEND_BACK,HttpKeys.SYNCFRIEND_ASK,values);
    		} catch (HttpHostConnectException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (ClientProtocolException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IllegalStateException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

//    		Message msg = new Message();
    		if(result!=null){
    			if(result.get("RSPCOD")!=null&&result.get("RSPCOD").toString().equals("000000")){
//    				msg.what = 1;
    			}else if(result.get("flag")!=null&&result.get("flag").toString().equals("03")){
//    				msg.what = 2;
    			}else{
//    				msg.what = 5;
    			}
    		}else{
//    			msg.what = 5;
    		}
//              myHandler.sendMessage(msg); // 向Handler发送消息,更新UI
          }
      }
      
      public class SyncThread extends Thread {  
    	  
    	    @Override  
    	    public void run() {  
    	        synchronized (this) {  
    	        	ArrayList<HashMap<String, Object>> list = null;
    	    		
    	    		try {
    	    			
    	    			String[] values = {mercnum,mobilelist};
    	    			result = NetCommunicate.executeHttpPostnulls(HttpUrls.SYNCFRIEND,
    	    					HttpKeys.SYNCFRIEND_BACK,HttpKeys.SYNCFRIEND_ASK,values);
    	    		} catch (HttpHostConnectException e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		} catch (ClientProtocolException e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		} catch (IllegalStateException e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		} catch (IOException e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		} catch (Exception e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		}

//    	    		Message msg = new Message();
    	    		if(result!=null){
    	    			if(result.get("RSPCOD")!=null&&result.get("RSPCOD").toString().equals("000000")){
//    	    				msg.what = 1;
    	    			}else if(result.get("flag")!=null&&result.get("flag").toString().equals("03")){
//    	    				msg.what = 2;
    	    			}else{
//    	    				msg.what = 5;
    	    			}
    	    		}else{
//    	    			msg.what = 5;
    	    		}
//    	              myHandler.sendMessage(msg); // 向Handler发送消息,更新UI
    	        }  
    	  
    	    }  
      }

}
