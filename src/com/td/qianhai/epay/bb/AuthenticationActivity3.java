package com.td.qianhai.epay.bb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.td.qianhai.epay.bb.NewRealNameAuthenticationActivity.RealNameAuthTaskAll;
import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.Entity;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.mail.utils.GetImageUtil;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;
import com.td.qianhai.epay.bb.views.AnimationUtil;
import com.td.qianhai.epay.bb.views.dialog.OneButtonDialogWarn;
import com.td.qianhai.epay.bb.views.dialog.interfaces.OnMyDialogClickListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat") public class AuthenticationActivity3 extends BaseActivity{
	
	private TextView tv_au_button;
	private String bankProvinceid = "",bankCityid = "",bankareid = "",name = "",id = "",banknum;
	private ImageView button1,button2,iv_name_auth_front,iv_name_auth_scene,flow_chart;
	// 身份证照片Code
	private final int FRONT_CODE = 100;
	// 情景照片Code
	private final int SCENS_CODE = 101;
	// 银行卡照片code
//	private final int BANK_CODE = 102;
	// 文件名
	private String localTempImgFileName = "";
	private String custpic, idcardpic, mercnum, bankpic;
	private File custPicFile, idcardPicFile, bankFile;
	private String idFrontPic, scenePic, bancardPic;
	
	private OneButtonDialogWarn warnDialog;
	
	private Editor editor;
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentication_activity3);
		AppContext.getInstance().addActivity(this);
		Intent it = getIntent();
		editor = MyCacheUtil.setshared(this);
		mercnum = MyCacheUtil.getshared(this).getString("MercNum", "");
		bankProvinceid = it.getStringExtra("bankProvinceid");
		bankCityid = it.getStringExtra("bankCityid");
		bankareid = it.getStringExtra("bankareid");
		banknum = it.getStringExtra("banknum");
		name = it.getStringExtra("name");
		id = it.getStringExtra("id");
		
		initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.tv_title_contre)).setText("实名认证");
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		tv_au_button = (TextView) findViewById(R.id.tv_au_button);
		button1 = (ImageView) findViewById(R.id.button1);
		button2 = (ImageView) findViewById(R.id.button2);
		iv_name_auth_scene = (ImageView) findViewById(R.id.iv_name_auth_scene);
		iv_name_auth_front = (ImageView) findViewById(R.id.iv_name_auth_front);
		flow_chart = (ImageView) findViewById(R.id.flow_chart);
		
		tv_au_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				completedata("");
			}
		});
		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				takePicture(FRONT_CODE);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				takePicture(SCENS_CODE);
				
			}
		});
		
		custpic = HttpUrls.REAL_NAME_AUTHENTICATION + "_" + getStringDateMerge() + "_"
				+ "C.jpg";
		idcardpic = HttpUrls.REAL_NAME_AUTHENTICATION + "_" + getStringDateMerge() + "_"
				+ "I.jpg";
		bankpic = HttpUrls.REAL_NAME_AUTHENTICATION + "_" + getStringDateMerge() + "_"
				+ "B.jpg";
	}
	
	public String getStringDateMerge() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	/*
	 * 拍照返回图片
	 */
	private void takePicture(int code) {

		


		File dir = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/DCIM/");// 设置临时文件的存放目录
		if (!dir.exists()) {
			dir.mkdir();
		}
		// 系统相机
		 Intent intent = new Intent(
		 android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		if (code == FRONT_CODE) {
			localTempImgFileName = idcardpic;

			Log.e("", "localTempImgFileName1 = = =" + localTempImgFileName);
		} else if (code == SCENS_CODE) {

			localTempImgFileName = custpic;
			Log.e("", "localTempImgFileName2 = = =" + localTempImgFileName);
		} else {
			localTempImgFileName = bankpic;
			Log.e("", "localTempImgFileName2 = = =" + localTempImgFileName);
		}
		
		File f = new File(dir, localTempImgFileName);
		Log.e("fileNam", localTempImgFileName);
		Uri u = Uri.fromFile(f);
		 intent.putExtra(MediaStore.Images.Media.ORIENTATION, 10);
//		 intent.setDataAndType(u, "image/*"); // 格式
		 try {
//			 intent.putExtra("crop", "true"); // 发送裁剪信号

		} catch (Exception e) {
			// TODO: handle exception
		}
		 intent.putExtra("aspectX", 4); // X方向上的比例
		 intent.putExtra("aspectY", 3); // Y方向上的比例
		 intent.putExtra("outputX", 800f); // 裁剪区的宽
		 intent.putExtra("outputY", 400f); // 裁剪区的高
		 intent.putExtra("scale", false); // 是否保留比例

		 intent.putExtra(MediaStore.EXTRA_OUTPUT, u); // 将URI指向相应的file:///
		 intent.putExtra("return-data", false); // 是否将数据保留在Bitmap中返回
		 startActivityForResult(intent, code);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent data) {
		super.onActivityResult(arg0, arg1, data);

		if (arg1 != RESULT_OK) {
			return;
		} else if (arg0 == FRONT_CODE && arg1 == RESULT_OK) {
			handler.sendEmptyMessage(FRONT_CODE);
		} else if (arg0 == SCENS_CODE && arg1 == RESULT_OK) {
			handler.sendEmptyMessage(SCENS_CODE);
		} else {
//			handler.sendEmptyMessage(BANK_CODE);
		}

	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {

			case FRONT_CODE:
				Drawable drawable = new BitmapDrawable(getBitmap(FRONT_CODE));
				iv_name_auth_front.setBackgroundDrawable(drawable);
				iv_name_auth_front.setImageBitmap(null);
				break;

			case SCENS_CODE:
				Drawable drawabless = new BitmapDrawable(getBitmap(SCENS_CODE));
				iv_name_auth_scene.setBackgroundDrawable(drawabless);
				iv_name_auth_scene.setImageBitmap(null);
//				Drawable drawables = new BitmapDrawable(getBitmap());
//				ivScene.setBackgroundDrawable(drawables);

				break;

//			case BANK_CODE:
//
//				break;

			default:
				break;
			}
		}
	};
	
	/*
	 * 得到拍摄的图片
	 */
	private Bitmap getBitmap(int code) {
		File dir = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/DCIM/" + localTempImgFileName);// 设置存放目录
		Log.e("", "      = = = = = = =dir = = = = =" + dir);
		Log.e("", "      = = = = = = =filenames = = = = ="
				+ localTempImgFileName);
		// File f = new File(dir.getAbsoluteFile(), localTempImgFileName);
		if (dir.isFile()) {
			Log.e("", "有文件");
			
			
//			Bitmap bitmap = getimage(Environment.getExternalStorageDirectory()
//					.getAbsolutePath() + "/DCIM/" + localTempImgFileName);
			
			Bitmap bitmap = getSmallBitmap(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/DCIM/" + localTempImgFileName);
			saveBitmap2file(bitmap, localTempImgFileName);
		} else {
			Log.e("", "没有文件");
		}
		BitmapFactory.Options opts = new BitmapFactory.Options();// 获取缩略图显示到屏幕
		opts.inSampleSize = 4;
		/* 下面两个字段需要组合使用 */
		// opts.inJustDecodeBounds = false;
		// opts.inPurgeable = true;
		// opts.inInputShareable = true;
		Bitmap cbitmap = BitmapFactory.decodeFile(dir.getAbsolutePath());
		if (code == FRONT_CODE) {
			custPicFile = dir;
			idFrontPic = "ok";
		} else if (code == SCENS_CODE) {
			idcardPicFile = dir;
			scenePic = "ok";
		} else {
			bankFile = dir;
			bancardPic = "ok";
		}
		return cbitmap;
	}
	
	
	
	
	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}
	
	
	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
	
	
	
	/**
	 * 图片按比例大小压缩方法（根据路径获取图片并压缩）
	 * 
	 * @param srcPath
	 * @return
	 */
	private Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 600f;// 这里设置高度为800f
		float ww = 400f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = 4;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		newOpts.inPurgeable = true;
		try {
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		} catch (Exception e) {
			// TODO: handle exception
			newOpts.inSampleSize = 1;
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		}
		 return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
//		return bitmap;
	}
	
	/**
	 * 图片质量压缩
	 * 
	 * @param image
	 * @return
	 */
	
	private Bitmap compressImage(Bitmap image) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
			  Log.e("", ""+"asdasdasdasdasdasd");

            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    }

	private static boolean saveBitmap2file(Bitmap bmp, String filename) {
		CompressFormat format = Bitmap.CompressFormat.JPEG;
		int quality = 40;
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ "/DCIM/"
					+ filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bmp.compress(format, quality, stream);
	}

	
	/**
	 * 补全资料(全部信息上传)
	 */
	private void completedata(String a) {
		if (idFrontPic == null) {
			AnimationUtil.BtnSpecialAnmations1(this, iv_name_auth_front, 500);
			Toast.makeText(getApplicationContext(), "请拍摄身份证正面照片",
					Toast.LENGTH_SHORT).show();
			// ToastCustom.showMessage(this, "请拍摄身份证正面照片");
			return;
		}

		if (scenePic == null) {
			AnimationUtil.BtnSpecialAnmations1(this, iv_name_auth_scene, 500);
			Toast.makeText(getApplicationContext(), "请拍摄情景照片",
					Toast.LENGTH_SHORT).show();
			// ToastCustom.showMessage(this, "请拍摄情景照片");
			return;
		}
		RealNameAuthTaskAll realtask = new RealNameAuthTaskAll();
		Log.e("", " = = = "+idcardpic);
		Log.e("", " = = = "+custpic);
		realtask.execute(HttpUrls.REAL_NAME_AUTHENTICATION+"", mercnum, idcardpic, custpic,
				banknum, name, id, bankProvinceid, bankCityid,
				bankareid);

	}
	
	/**
	 * 实名认证上传照片
	 * 
	 * @author liangge
	 * 
	 */
	class RealNameAuthTaskAll extends
			AsyncTask<String, Integer, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {
			showLoadingDialog("正在上传中。。。");
			super.onPreExecute();
		}

		@Override
		protected HashMap<String, Object> doInBackground(String... params) {
			String[] values = { params[0], params[1], params[2], params[3],
					params[4], params[5], params[6],params[7],params[8],params[9] };
			File[] fils  =  new File[] { custPicFile, idcardPicFile };
				// fils = files;

			return NetCommunicate.getUpload(HttpUrls.REAL_NAME_AUTHENTICATION,values, fils);
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			loadingDialogWhole.dismiss();
			if (result != null) {
				if (result.get(Entity.RSPCOD).equals(Entity.STATE_OK)) {
					flow_chart.setImageDrawable(getResources().getDrawable(R.drawable.au_fourth));
					editor.putString("STS", "1");
					editor.commit();
					warnDialog = new OneButtonDialogWarn(AuthenticationActivity3.this,
							R.style.CustomDialog, "提示", result.get(Entity.RSPMSG).toString(),
							"确定", new OnMyDialogClickListener() {
								@Override
								public void onClick(View v) {
									warnDialog.dismiss();
									if(AuthenticationActivity1.con!=null){
										AuthenticationActivity1.con.finish();
									}
									if(Authenticationactivity2.con!=null){
										Authenticationactivity2.con.finish();
									}
									
									finish();
								}
							});
					warnDialog.show();

				} else {
					warnDialog = new OneButtonDialogWarn(AuthenticationActivity3.this,
							R.style.CustomDialog, "提示", result.get(Entity.RSPMSG).toString(),
							"确定", new OnMyDialogClickListener() {
								@Override
								public void onClick(View v) {
									warnDialog.dismiss();
								}
							});
					warnDialog.show();
				}
			}
			super.onPostExecute(result);
		}
	}
	

}
