package com.td.qianhai.epay.bb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.bitmap.download.Downloader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.HttpHostConnectException;

import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.HttpKeys;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.mail.utils.GetImageUtil;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.mail.utils.GetImageUtil.ImageLoadTask2;
import com.td.qianhai.epay.bb.net.NetCommunicate;
import com.td.qianhai.epay.bb.views.dialog.OneButtonDialogWarn;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnMyDialogClickListener;
import com.td.qianhai.epay.utils.AppManager;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScreeningActivity extends BaseActivity{
	
	private String mobile;
	private LinearLayout lin_edit;
	private EditText edit;
	private TextView btn_edit,btn_scree;
	private RelativeLayout re_qrcode;
	private ImageView myqrcode_imgs;
	private HashMap<String, Object> result = null; 
	private HashMap<String, Object> result1 = null; 
	private OneButtonDialogWarn warnDialog;
	private Bitmap bitmap;
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppContext.getInstance().addActivity(this);
		setContentView(R.layout.activity_screening);
		mobile = MyCacheUtil.getshared(this).getString("Mobile", "");
		initview();
		load();
		
	}

	private void load() {
		// TODO Auto-generated method stub
		showLoadingDialog("请稍候...");
		new Thread(run).start();
		
	}

	private void initview() {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.tv_title_contre)).setText("商家收款");
		((TextView) findViewById(R.id.bt_title_right)).setVisibility(View.GONE);
		((TextView) findViewById(R.id.bt_title_right1)).setVisibility(View.VISIBLE);
		((TextView) findViewById(R.id.bt_title_right1)).setText("查询");
		findViewById(R.id.bt_title_right1).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						String subMchId = "";
						if(result.get("subMchId")!=null){
							 subMchId = result.get("subMchId").toString();
						}
						Intent intent = new Intent();
						intent.putExtra("subMchId", subMchId);
						intent.setClass(ScreeningActivity.this, WechatCollectionRecordActivity.class);
						startActivity(intent);
					}
				});
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
		lin_edit = (LinearLayout) findViewById(R.id.lin_edit);
		edit = (EditText) findViewById(R.id.edit);
		btn_edit = (TextView) findViewById(R.id.btn_edit);
		btn_scree = (TextView) findViewById(R.id.btn_scree);
		re_qrcode = (RelativeLayout) findViewById(R.id.re_qrcode);
		myqrcode_imgs = (ImageView) findViewById(R.id.myqrcode_imgs);
		
		edit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(s.length()>=1){
					btn_edit.setEnabled(true);
				}else{
					btn_edit.setEnabled(false);
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
		
		btn_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SpannableString msp = new SpannableString("您的商户名是:"+edit.getText().toString());
				showDoubleWarnDialog(msp);
				
			}
		});
		btn_scree.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			
				
//				Bitmap obmp = convertViewToBitmap(myqrcode_imgs);
				
//				BitmapFactory.Options newOpts = new BitmapFactory.Options();    
//				newOpts.outWidth = 400;
//				newOpts.outHeight = 600;
//				  newOpts.inSampleSize = 1;
//				MediaStore.Images.Media.insertImage(getContentResolver(), obmp, "title", "description");
				
				FileOutputStream m_fileOutPutStream = null;
				String filepath = Environment.getExternalStorageDirectory() +File.separator
						+ "mywechatqrcode.png";
				Log.e("", ""+filepath);
				try {
					m_fileOutPutStream = new FileOutputStream(filepath);
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				}
//				obmp = BitmapFactory.decodeFile(filepath, newOpts);
				bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
				bitmap.compress(CompressFormat.PNG, 100, m_fileOutPutStream);
				try {
					m_fileOutPutStream.flush();
					m_fileOutPutStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				warnDialog = new OneButtonDialogWarn(ScreeningActivity.this,
						R.style.CustomDialog, "提示", "保存成功", "确定",
						new OnMyDialogClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								warnDialog.dismiss();
								myqrcode_imgs.setDrawingCacheEnabled(false);
							}
						});
				warnDialog.show();

			}
		});
	}
	
	
	public static Bitmap convertViewToBitmap(View view)  
	{  
//	    view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));  
//	    view.layout(view.getPaddingLeft(), view.getPaddingLeft(), view.getMeasuredWidth(), view.getMeasuredHeight());  
	    view.buildDrawingCache();  
	    Bitmap bitmap = view.getDrawingCache();  
	  
	    return bitmap;  
	} 
	
	Runnable run = new Runnable() {

		@Override
		public void run() {
			ArrayList<HashMap<String, Object>> list = null;
			
			try {
				
				String[] values = {mobile};
				result = NetCommunicate.executeHttpPostnulls(HttpUrls.SCREENINGURL,
						HttpKeys.SCREENINGURL_BACK,HttpKeys.SCREENINGURL_ASK,values);
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
				if(result.get("flag")!=null&&result.get("flag").toString().equals("00")){
					msg.what = 1;
				}else if(result.get("flag")!=null&&result.get("flag").toString().equals("03")){
					msg.what = 2;
				}else{
					msg.what = 5;
				}
			}else{
				msg.what = 5;
			}
			loadingDialogWhole.dismiss();
			handler.sendMessage(msg);
			
		}
	};
	
	private Handler handler = new Handler() {
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:
			re_qrcode.setVisibility(View.VISIBLE);
			lin_edit.setVisibility(View.GONE);
			if(result.get("qrcode_url")!=null){
				setimg(result.get("qrcode_url").toString());
			}
			
			break;
		case 2:
			lin_edit.setVisibility(View.VISIBLE);
			
			break;
		case 5:
			warnDialog = new OneButtonDialogWarn(ScreeningActivity.this,
					R.style.CustomDialog, "提示", result.get("message").toString(), "确定",
					new OnMyDialogClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							finish();
							warnDialog.dismiss();
						}
					});
			warnDialog.show();
			break;
		default:
			break;
		}
	};
};

	@Override
	protected void doubleWarnOnClick(View v) {
		// TODO Auto-generated method stub
		super.doubleWarnOnClick(v);
		switch (v.getId()) {
		case R.id.btn_left:
			doubleWarnDialog.dismiss();
			break;
		case R.id.btn_right:
			doubleWarnDialog.dismiss();
			load1();
			break;

		default:
			break;
		}
	}
	
	
	private void load1() {
		// TODO Auto-generated method stub
		showLoadingDialog("请稍候...");
		new Thread(run1).start();
	}


	Runnable run1 = new Runnable() {

		@Override
		public void run() {
			ArrayList<HashMap<String, Object>> list = null;
			
			try {
				
				String[] values = {mobile,edit.getText().toString()};
				result1 = NetCommunicate.executeHttpPostnulls(HttpUrls.SCREENINGURLFA,
						HttpKeys.SCREENINGURLFA_BACK,HttpKeys.SCREENINGURLFA_ASK,values);
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
			if(result1!=null){
				if(result1.get("flag")!=null&&result1.get("flag").toString().equals("00")){
					msg.what = 1;
				}else if(result1.get("flag")!=null&&result1.get("flag").toString().equals("03")){
					msg.what = 2;
				}else{
					msg.what = 5;
				}
			}else{
				msg.what = 5;
			}
			loadingDialogWhole.dismiss();
			handler1.sendMessage(msg);
			
		}
	};
	
	private Handler handler1 = new Handler() {
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:

			
			warnDialog = new OneButtonDialogWarn(ScreeningActivity.this,
					R.style.CustomDialog, "提示", result1.get("message").toString(), "确定",
					new OnMyDialogClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							warnDialog.dismiss();
							lin_edit.setVisibility(View.GONE);
							re_qrcode.setVisibility(View.VISIBLE);
							result1 = null;
							load();
						}
					});
			warnDialog.show();
			
			break;
		case 2:
			warnDialog = new OneButtonDialogWarn(ScreeningActivity.this,
					R.style.CustomDialog, "提示", result1.get("message").toString(), "确定",
					new OnMyDialogClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							warnDialog.dismiss();
						}
					});
			warnDialog.show();
			break;
		case 5:
			warnDialog = new OneButtonDialogWarn(ScreeningActivity.this,
					R.style.CustomDialog, "提示", result1.get("message").toString(), "确定",
					new OnMyDialogClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							finish();
							warnDialog.dismiss();
						}
					});
			warnDialog.show();
			break;
		default:
			break;
		}
	}
};

private void setimg(String url) {
	// TODO Auto-generated method stub
	
//    Bitmap bitmap = BitmapFactory.decodeStream(
//            HandlerData(url));
//    myqrcode_imgs.setImageBitmap(bitmap);
	new ImageLoadTask2(this).execute(url);
//	FinalBitmap.create(this).display(myqrcode_imgs,
//			url,
//			myqrcode_imgs.getWidth(),
//			myqrcode_imgs.getHeight(), null, null);
	
	}

public class ImageLoadTask2 extends AsyncTask<String, Void, Void> {
	 
    public ImageLoadTask2(Context context) {
    }

    @Override
    protected Void doInBackground(String... params) {
       String url = params[0];// 获取传来的参数，图片uri地址
             bitmap = BitmapFactory.decodeStream(
                    HandlerData(url));
            Message msg = new Message();
            if(bitmap!=null){
//        		if (getbitmap(Imageurl)) {
//            	headimg.setImageBitmap(bitmap);
//   			 bitmap = BitmapFactory.decodeFile(Imageurl);
            	msg.what = 1;
   			
            }else{
                bitmap = BitmapFactory.decodeStream(
                        HandlerData(url));
            	msg.what = 1;
//        			Resources res = context.getResources();
//        			bitmap = BitmapFactory.decodeResource(res, R.drawable.head_portrait);
//        			headimg.setImageBitmap(bitmap);
            }
//            publishProgress(); // 通知去更新UI
            hander2.sendMessage(msg);
        return null;
}
    
    

    public void onProgressUpdate(Void... voids) {
            return;
    }
}

private Handler hander2 = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
       // button.setText(msg.getData().getString("data"));
        if(msg.what==1){
        	try {
        		myqrcode_imgs.setImageBitmap(bitmap);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
    }
};

public static InputStream HandlerData(String url) {
    InputStream inStream = null;

    try {
        URL feedUrl = new URL(url);
        URLConnection conn = feedUrl.openConnection();
        conn.setConnectTimeout(10 * 1000);
        inStream = conn.getInputStream();
    } catch (Exception e) {
       e.printStackTrace();
   }

    return inStream;
}
}
