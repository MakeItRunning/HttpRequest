package com.td.qianhai.epay.bb.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.td.qianhai.epay.bb.R;

public class CreditListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<HashMap<String, Object>> list;

	public CreditListAdapter(Context context,
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
					R.layout.credit_list_item, null);
			// holder.tvTradName = (TextView) convertView
			// .findViewById(R.id.tv_operation);
			holder.bankname_tv = (TextView) convertView
					.findViewById(R.id.bankname_tv);
			holder.cardnum_tv = (TextView) convertView
					.findViewById(R.id.cardnum_tv);
			holder.cardname_tv = (TextView) convertView
					.findViewById(R.id.cardname_tv);
			holder.creditcard_img = (ImageView) convertView
					.findViewById(R.id.creditcard_img);
			holder.mian_bg = (LinearLayout) convertView.findViewById(R.id.mian_bg);
			convertView.setTag(holder);
//
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(position%2==0){
//			holder.mian_bg.setb
			holder.mian_bg.setBackgroundResource(R.drawable.credit_item_selector1);
		}
		
				HashMap<String, Object> maps = list.get(position);
				if(maps.get("ISSNAM")!=null){
					holder.bankname_tv.setText(maps.get("ISSNAM").toString());
					String cardname = maps.get("ISSNAM").toString();
					Log.e("", "  = = ="+cardname.substring(0,1));
					if (cardname.substring(0,2).equals("招商")) {
						holder.creditcard_img.setImageResource(R.drawable.ps_cmb);
					}else if(cardname.substring(0,2).equals("农业")) {
						holder.creditcard_img.setImageResource(R.drawable.ps_abc);
					}else if(cardname.substring(0,2).equals("农行")) {
						holder.creditcard_img.setImageResource(R.drawable.ps_abc);
					}else if(cardname.substring(0,2).equals("北京")){
						holder.creditcard_img.setImageResource(R.drawable.ps_bjb);
					}else if(cardname.substring(0,2).equals("中国")){
						if(cardname.substring(0,4).equals("中国建设")){
							holder.creditcard_img.setImageResource(R.drawable.ps_ccb);
						}else{
							holder.creditcard_img.setImageResource(R.drawable.ps_boc);
						}
					}else if(cardname.substring(0,2).equals("建设")){
						holder.creditcard_img.setImageResource(R.drawable.ps_ccb);
					}else if(cardname.substring(0,2).equals("光大")){
						holder.creditcard_img.setImageResource(R.drawable.ps_cebb);
					}else if(cardname.substring(0,2).equals("兴业")){
						holder.creditcard_img.setImageResource(R.drawable.ps_cib);
					}else if(cardname.substring(0,2).equals("中信")){
						holder.creditcard_img.setImageResource(R.drawable.ps_citic);
					}else if(cardname.substring(0,2).equals("民生")){
						holder.creditcard_img.setImageResource(R.drawable.ps_cmbc);
					}else if(cardname.substring(0,2).equals("交通")){
						holder.creditcard_img.setImageResource(R.drawable.ps_comm);
					}else if(cardname.substring(0,2).equals("华夏")){
						holder.creditcard_img.setImageResource(R.drawable.ps_hxb);
					}else if(cardname.substring(0,4).equals("广东发展")){
						holder.creditcard_img.setImageResource(R.drawable.ps_gdb);
					}else if(cardname.substring(0,2).equals("广发")){
						holder.creditcard_img.setImageResource(R.drawable.ps_gdb);
					}else if(cardname.substring(0,2).equals("邮政")){
						holder.creditcard_img.setImageResource(R.drawable.ps_psbc);
					}else if(cardname.substring(0,2).equals("邮储")){
						holder.creditcard_img.setImageResource(R.drawable.ps_psbc);
					}else if(cardname.substring(0,2).equals("工商")){
						holder.creditcard_img.setImageResource(R.drawable.ps_icbc);
					}else if(cardname.substring(0,2).equals("平安")){
						holder.creditcard_img.setImageResource(R.drawable.ps_spa);
					}else if(cardname.substring(0,2).equals("浦东")){
						holder.creditcard_img.setImageResource(R.drawable.ps_spdb);
					}else if(cardname.substring(0,2).equals("工商")){
						holder.creditcard_img.setImageResource(R.drawable.ps_icbc);
					}else{
						holder.creditcard_img.setImageResource(R.drawable.ps_unionpay);
					}
					
					
				}else{
					
					holder.creditcard_img.setImageResource(R.drawable.ps_unionpay);
				}
				if(maps.get("ISSNAM")!=null){
					holder.bankname_tv.setText(maps.get("ISSNAM").toString());
				}else{
					holder.bankname_tv.setText("");
				}
				
				if(maps.get("CARDNO")!=null){
					String cardno = maps.get("CARDNO").toString();
					holder.cardnum_tv.setText("尾号   "+cardno.substring(cardno.length()-4));
				}
				if(maps.get("CARDNAME")!=null){
					
					holder.cardname_tv.setText(maps.get("CARDNAME").toString());
				}
				
				
				
				

		return convertView;
	}

	class ViewHolder {
		TextView bankname_tv;
		TextView cardnum_tv;
		TextView cardname_tv;
		ImageView creditcard_img;
		LinearLayout mian_bg;
		
	}
}
