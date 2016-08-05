package com.td.qianhai.epay.bb.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.tsz.afinal.FinalBitmap;

import com.td.qianhai.epay.bb.R;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.mail.utils.GetImageUtil;
import com.td.qianhai.mpay.utils.DateUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyCirecleAdapter1 extends BaseAdapter {

	private Context mContext;
	private ArrayList<HashMap<String, Object>> list;

	public MyCirecleAdapter1(Context context,
			ArrayList<HashMap<String, Object>> list, int tag) {
		this.list = list;
		this.mContext = context;
		

	}
	
//	@Override    
//	public boolean isEnabled(int position) {     
//	   return false;     
//	}  

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int arg0) {

		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.mycircle_list_item2, null);
			// holder.tvTradName = (TextView) convertView
			// .findViewById(R.id.tv_operation);
			
			holder.tv_time = (TextView) convertView
			.findViewById(R.id.tv_time);
			holder.date_tv = (TextView) convertView
					.findViewById(R.id.date_tv);
			holder.name_tv = (TextView) convertView
					.findViewById(R.id.name_tv);
			holder.phonenum_tv = (TextView) convertView
					.findViewById(R.id.phonenum_tv);
			holder.imgs = (ImageView) convertView
					.findViewById(R.id.imgs);
			holder.directCommonSaleAgtMerCount = (TextView) convertView
					.findViewById(R.id.directCommonSaleAgtMerCount);
			holder.directCommonSeniorMerCount = (TextView) convertView
					.findViewById(R.id.directCommonSeniorMerCount);
			holder.directCommonMerCount = (TextView) convertView
					.findViewById(R.id.directCommonMerCount);
			holder.indirectCommonSaleAgtMerCount = (TextView) convertView
					.findViewById(R.id.indirectCommonSaleAgtMerCount);
			holder.indirectCommonSeniorMerCount = (TextView) convertView
					.findViewById(R.id.indirectCommonSeniorMerCount);
			holder.indirectCommonMerCount = (TextView) convertView
					.findViewById(R.id.indirectCommonMerCount);
			holder.call_phone = (ImageView) convertView
					.findViewById(R.id.call_phone);
			holder.user_head_img = (ImageView) convertView.findViewById(R.id.user_head_img);
			 
			holder.directCommonAgentMerCount = (TextView) convertView.findViewById(R.id.directCommonAgentMerCount);
			
			holder.indirectCommonAgentMerCount = (TextView) convertView.findViewById(R.id.indirectCommonAgentmerCount);
			convertView.setTag(holder);
//
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		final HashMap<String, Object> maps = list.get(position);
		
		if(maps.get("mercnam")!=null){
			if(maps.get("mercnam").toString().equals("null")){
				holder.name_tv.setText("未知");
			}else{
				holder.name_tv.setText(maps.get("mercnam").toString());
			}
		}else{
			holder.name_tv.setText("未知");
		}
		
		if(maps.get("merphonenumber")!=null){
//			if(maps.get("merphonenumber").toString().length()>11){
//				String phone = maps.get("merphonenumber").toString();
//				String setphone = phone.substring(0,3);
//				String getphone = phone.substring(phone.length()-4);
//				holder.phonenum_tv.setText(setphone+"****"+getphone);
//			}else{
			if(maps.get("merphonenumber").toString().equals("null")){
				holder.phonenum_tv.setText("未知");
			}else{
				holder.phonenum_tv.setText(maps.get("merphonenumber").toString());
			}
			
//			}
			
		}
		String imgUrl = maps.get("personpic").toString();
		holder.user_head_img.setTag(imgUrl);
		// 预设一个图片
		holder.user_head_img.setImageResource(R.drawable.userhead_bg);

//		// 通过 tag 来防止图片错位
		if (holder.user_head_img.getTag() != null &&!holder.user_head_img.getTag().equals("null")&& holder.user_head_img.getTag().equals(imgUrl)) {
			Bitmap bit = null;
			try {
				 bit = GetImageUtil.iscace(holder.user_head_img,HttpUrls.HOST_POSM+maps.get("personpic").toString());
			} catch (Exception e) {
				// TODO: handle exception\
				Log.e("", ""+e.toString());
			}
			if(bit!=null){
				holder.user_head_img.setImageBitmap(bit);
			}else{
				new GetImageUtil(mContext, holder.user_head_img,HttpUrls.HOST_POSM+maps.get("personpic").toString());
			}
			
		}else{
			holder.user_head_img.setImageResource(R.drawable.userhead_bg);

		}
//		if(!imgUrl.equals("null")){
////			FinalBitmap.create(mContext).display(holder.user_head_img,
////					HttpUrls.HOST_POSM+maps.get("personpic").toString());
//			new GetImageUtil(mContext, holder.user_head_img,HttpUrls.HOST_POSM+maps.get("personpic").toString());
//		}else{
//			holder.user_head_img.setImageResource(R.drawable.head_user_img);
//		}
		
		
		if(maps.get("isgeneralagent")!=null){
			String a = maps.get("isgeneralagent").toString();
			if(a.equals("1")){
				holder.imgs.setImageResource(R.drawable.agent_imgs);
			}else{
				if(maps.get("issaleagt")!=null){
					String b = maps.get("issaleagt").toString();
					if(a.equals("1")){
						holder.imgs.setImageResource(R.drawable.distribution_img1);
					}else{
						if(maps.get("isretailers")!=null){
							String c = maps.get("isretailers").toString();
							if(c.equals("1")){
								holder.imgs.setImageResource(R.drawable.retailer_imgs);
							}else{
								if(maps.get("isseniormember")!=null){
									String d = maps.get("isseniormember").toString();
									if(d.equals("1")){
										holder.imgs.setImageResource(R.drawable.senior_img);
									}else{
										holder.imgs.setImageResource(R.drawable.ordinary_img1);
									}
								}
							}
						}
					}
				}
			}
		}
		
//		if(maps.get("issaleagt")!=null){
//			String a = maps.get("issaleagt").toString();
//			if(a.equals("1")){
//				holder.imgs.setImageResource(R.drawable.distribution_img);
//			}else{
//				if(maps.get("isseniormember")!=null){
//					String b = maps.get("isseniormember").toString();
//					if(b.equals("1")){
//						holder.imgs.setImageResource(R.drawable.senior_img);
//					}else{
//						holder.imgs.setImageResource(R.drawable.ordinary_img);
//					}
//				}
//			}
//		}
		
//		tv_time.
		
		if(maps.get("applydat")!=null){
			try {
				String date = DateUtil.strToDateToLong(maps.get("applydat")
						.toString());
				holder.tv_time.setText(date);
				
			} catch (Exception e) {
				// TODO: handle exception
				holder.tv_time.setText(maps.get("applydat").toString());
			}
			
		}
//		holder.directCommonSaleAgtMerCount.setText(maps.get("directCommonSaleAgtMerCount").toString());
		holder.directCommonSeniorMerCount.setText(maps.get("directCommonMerCount").toString());
		holder.directCommonMerCount.setText(maps.get("directCommonRetailersMerCount").toString());
		holder.indirectCommonSaleAgtMerCount.setText(maps.get("indirectCommonSaleAgtMerCount").toString());
		holder.indirectCommonSeniorMerCount.setText(maps.get("indirectCommonSeniorMerCount").toString());
		holder.indirectCommonMerCount.setText(maps.get("indirectCommonRetailersMerCount").toString());
		holder.directCommonAgentMerCount.setText(maps.get("directCommonAgentMerCount").toString());
		holder.indirectCommonAgentMerCount.setText(maps.get("indirectCommonAgentMerCount").toString());
		holder.call_phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String phone = maps.get("merphonenumber").toString();
				
				Uri uri = Uri.parse("tel:"+phone);   
				Intent it = new Intent(Intent.ACTION_DIAL, uri);  
				mContext.startActivity(it);
				
//				 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
//						mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView date_tv;
		TextView name_tv,tv_time;
		TextView phonenum_tv;
		ImageView imgs,call_phone,user_head_img;
		TextView directCommonSaleAgtMerCount;
		TextView directCommonSeniorMerCount;
		TextView directCommonMerCount;
		TextView indirectCommonSaleAgtMerCount;
		TextView indirectCommonSeniorMerCount;
		TextView indirectCommonMerCount;
		TextView directCommonAgentMerCount;
		TextView indirectCommonAgentMerCount;
		
	}
}
