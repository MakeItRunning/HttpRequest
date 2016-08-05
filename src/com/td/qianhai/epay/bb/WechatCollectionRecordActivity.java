package com.td.qianhai.epay.bb;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.HttpHostConnectException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.HttpKeys;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.beans.WechatListBean;
import com.td.qianhai.epay.bb.dateutil.ScreenInfo;
import com.td.qianhai.epay.bb.dateutil.WheelMain;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;

public class WechatCollectionRecordActivity extends BaseActivity{
	
//	private ArrayList<HashMap<String, Object>> mList;
	private List<WechatListBean>  WechatListBeanlist= null;
	private ExpandableListView listView;
	private String mobile;
//	private WechatListAdapter adapter;
	private MyAdapter adapter;
	private CheckBox cb_title_contre;
	private RelativeLayout lin_1;
	private View view;
	
	private WheelMain wheelMain;
	
	private LayoutInflater inflater;
	
	private PopupWindow mPopupWindowDialog;
	
	private Button determine,cacel;
	
	private String setdate,subMchId;
	
	private TextView tv_pro;
	
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wechat_collection_record_activity);
		AppContext.getInstance().addActivity(this);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mobile = MyCacheUtil.getshared(this).getString("Mobile", "");
		setdate = initdate(1);
		Intent it = getIntent();
		subMchId = it.getStringExtra("subMchId");
		initview();
	}

	private String initdate(int a){
		SimpleDateFormat formatter = null; 
		if(a==1){
			formatter = new SimpleDateFormat ("yyyy-MM");
		}else{
			formatter = new SimpleDateFormat ("yyyy年MM月"); 
		}
		
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间 
		String str = formatter.format(curDate); 
		return str;
	}
	
	private void initview() {
		
				// TODO Auto-generated method stub
//		((TextView) findViewById(R.id.tv_title_contre)).setText("二维码收款记录");
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		listView = (ExpandableListView) findViewById(R.id.mylist);
		cb_title_contre = (CheckBox) findViewById(R.id.cb_title_contre);
//		mList = new ArrayList<HashMap<String,Object>>();
		tv_pro = (TextView) findViewById(R.id.tv_pro);
		WechatListBeanlist = new ArrayList<WechatListBean>();
		lin_1 = (RelativeLayout) findViewById(R.id.lin_1);
//		adapter = new WechatListAdapter(this, mList);
		adapter = new MyAdapter();
		listView.setAdapter(adapter);
		cb_title_contre.setText(initdate(2));

		if (WechatListBeanlist.size() == 0) {
			// 加载数据
			loadMore();
		    }
		cb_title_contre.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showdate();
			}
		});
	}

	private void loadMore() {
		WechatListBeanlist.clear();
		new Thread(run).start();
		
		
	}
	
	Runnable run = new Runnable() {

		@Override
		public void run() {
			
			try {
				String[] values = {mobile,setdate,subMchId};
				List<WechatListBean>  list
				 = NetCommunicate.executeHttpPostgetjson(HttpUrls.WECHATLIST,
						HttpKeys.GETWECHATLIST_BACK,HttpKeys.GETWECHATLIST_ASK,values);
				WechatListBeanlist.addAll(list);
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

//			if (list != null) {
//				mList.addAll(list);
				if (WechatListBeanlist.size() <= 0 || WechatListBeanlist == null) {

					msg.what = 2;
				} else {
					msg.what = 1;
				}
//			} else {
//				
//				msg.what = 3;
//			}
//			isThreadRun = false;
			
			handler.sendMessage(msg);
		}
	};

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				adapter.notifyDataSetChanged();
				tv_pro.setVisibility(View.GONE);
				break;
			case 2:
				adapter.notifyDataSetChanged();
				tv_pro.setVisibility(View.VISIBLE);
				break;
			case 3:
				break;
			default:
				break;
			}
		};
	};
	
    class MyAdapter extends BaseExpandableListAdapter {
        
        //得到子item需要关联的数据
        @Override
        public Object getChild(int groupPosition, int childPosition) {
//            String key = WechatListBeanlist.get(groupPosition).getPayTime();
           
            return ( WechatListBeanlist.get(groupPosition).list.get(childPosition));
        }
 
        //得到子item的ID
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }
 
        //设置子item的组件
        @Override
        public View getChildView(int groupPosition, int childPosition,
                boolean isLastChild, View convertView, ViewGroup parent) {
        	Log.e("", "asdas    "+childPosition);
        	Log.e("", "     asda   "+(WechatListBeanlist.get(groupPosition).list.get(childPosition).get("totalFee").toString()));
            String info = WechatListBeanlist.get(groupPosition).list.get(childPosition).get("totalFee").toString();
//            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) WechatCollectionRecordActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.wechat_list_item, null);
//            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.tv_money);
            TextView tv_money = (TextView) convertView
                    .findViewById(R.id.tv_money);
            TextView tv_time = (TextView) convertView
                    .findViewById(R.id.tv_time);
            tv_money.setText("微信支付");
            if(WechatListBeanlist.get(groupPosition).list.get(childPosition).get("payTime")!=null){
            	tv_time.setText(WechatListBeanlist.get(groupPosition).list.get(childPosition).get("payTime").toString());
            }
            tv.setText("￥"+info);
            return convertView;
        }
 
        //获取当前父item下的子item的个数
        @Override
        public int getChildrenCount(int groupPosition) {
            int size= WechatListBeanlist.get(groupPosition).list.size();
            return size;
        }
      //获取当前父item的数据
        @Override
        public Object getGroup(int groupPosition) {
            return WechatListBeanlist.get(groupPosition);
        }
 
        @Override
        public int getGroupCount() {
            return WechatListBeanlist.size();
        }
 
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
       //设置父item组件
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                View convertView, ViewGroup parent) {
//            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) WechatCollectionRecordActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.expandable_item, null);
//            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.tv_date);
            TextView tv_money= (TextView) convertView
                    .findViewById(R.id.tv_money);
            TextView tv_num= (TextView) convertView
                    .findViewById(R.id.tv_num);
            TextView tv_week= (TextView) convertView
                    .findViewById(R.id.tv_week);
         String aa = null;
            if(WechatListBeanlist.get(groupPosition).getPayDate()!=null){
            	   tv.setText(WechatListBeanlist.get(groupPosition).getPayDate());
            	   aa = WechatListBeanlist.get(groupPosition).getPayDate();
            }
            if(WechatListBeanlist.get(groupPosition).getTotalFee()!=null){
            	   tv_money.setText("￥"+WechatListBeanlist.get(groupPosition).getTotalFee());
            }
            if(WechatListBeanlist.get(groupPosition).getOrderCount()!=null){
            	tv_num.setText("共"+WechatListBeanlist.get(groupPosition).getOrderCount()+"笔");
            }
            if(aa!=null&&aa.length()>=1){
            	aa = aa.replace("-", "");
            	 tv_week.setText(getWeek(aa+"000000"));
            }
           
            return convertView;
        }
 
        @Override
        public boolean hasStableIds() {
            return true;
        }
 
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
 
    }
    
    private String getWeek(String date) {
        String Week = "周";   
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//也可将此值当参数传进来
//        try {
//			format.parse(date);
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//        Date  curDate = new Date(System.currentTimeMillis());
        String pTime = date;//format.format(curDate);  
        Calendar c = Calendar.getInstance();
        try {
         c.setTime(format.parse(date));
        } catch (ParseException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
        }
        switch(c.get(Calendar.DAY_OF_WEEK)){
        case 1:
            Week += "日";
            break;
        case 2:
            Week += "一";
            break;
        case 3:
            Week += "二";
            break;
        case 4:
            Week += "三";
            break;
        case 5:
            Week += "四";
            break;
        case 6:
            Week += "五";
            break;
        case 7:
            Week += "六";
            break;
        default:
            break;          
        }           
        Log.e("", ""+Week);
        return Week;
       }
    
    
	private void showdate() {
//		setLayoutY(lin_1, 0);
		
//		Calendar calendar = Calendar.getInstance();
//
//		int year = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH);
//		int day = 1;
//		int hour = calendar.get(Calendar.HOUR_OF_DAY);
//		int min = calendar.get(Calendar.MINUTE);
		
		
		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		
		view = inflater.inflate(R.layout.choose_dialog, null);
		setPopupWindowDialog();

		ScreenInfo screenInfo = new ScreenInfo(WechatCollectionRecordActivity.this);
		wheelMain = new WheelMain(view, 10);
		wheelMain.screenheight = screenInfo.getHeight();
		wheelMain.initDateTimePicker(year, month, day, hour, min);

		if (mPopupWindowDialog != null) {
			mPopupWindowDialog.showAtLocation(
					findViewById(R.id.lin_1), Gravity.BOTTOM
							| Gravity.CENTER_HORIZONTAL, 0, 0);
		}

		bottomBtn();
		
	}
	
	protected void setPopupWindowDialog() {
		// TODO Auto-generated method stub
		determine = (Button) view.findViewById(R.id.textview_dialog_album);
		cacel = (Button) view.findViewById(R.id.textview_dialog_cancel);

		mPopupWindowDialog = new PopupWindow(view, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		mPopupWindowDialog.setAnimationStyle(R.style.popwin_anim_style);  
		mPopupWindowDialog.setFocusable(false);
		mPopupWindowDialog.update();
		mPopupWindowDialog.setBackgroundDrawable(new BitmapDrawable(
				getResources(), (Bitmap) null));
//		mPopupWindowDialog.setBackgroundDrawable(getResources().getDrawable(R.drawable.air_city_button));
		mPopupWindowDialog.setOutsideTouchable(true);
//		mPopupWindowDialog.setClippingEnabled(false);
	}
	
	
	protected void bottomBtn() {
		// TODO Auto-generated method stub
		determine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if (mPopupWindowDialog != null
						&& mPopupWindowDialog.isShowing()) {
					mPopupWindowDialog.dismiss();
//					setLayoutY(lin_1, 0);
				}
				
			}
		});

		cacel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String dates = wheelMain.getTime();
				if(wheelMain.getTime().length()>=8){
					String a = dates.substring(0, 4);
					String b = dates.substring(4, 6);
					String c = dates.substring(dates.length()-2);
					cb_title_contre.setText(a+"年"+b+"月");
					setdate = a+"-"+b;
					loadMore();
				}
				
				if (mPopupWindowDialog != null
						&& mPopupWindowDialog.isShowing()) {
					mPopupWindowDialog.dismiss();
//					setLayoutY(lin_1, 0);
				}
			}
		});
	}
	
	public static void setLayoutY(View view,int y) 
	{ 
	MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams()); 
	margin.setMargins(margin.leftMargin,y, margin.rightMargin, y+margin.height); 
	LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin); 
	view.setLayoutParams(layoutParams); 
	}

}
