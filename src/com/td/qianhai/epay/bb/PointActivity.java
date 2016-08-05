package com.td.qianhai.epay.bb;

import java.util.ArrayList;
import java.util.HashMap;

import com.td.qianhai.epay.bb.adapter.PointListAdapter;
import com.td.qianhai.epay.bb.adapter.RecommendedListAdapter;
import com.td.qianhai.epay.bb.beans.HttpKeys;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PointActivity extends BaseActivity implements OnScrollListener{
	
	private int page = 1; // 页数
	private int allPageNum = 0; // 总页数
	private int PAGE_SIZE = 10;
	private ArrayList<HashMap<String, Object>> mList;
	private ListView mlistview;
	private boolean isThreadRun = false; // 加载数据线程运行状态
	private PointListAdapter adatper;
	private String mercnum;
	private TextView allpiont;
	private String tag;
	private String point = "0";
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.point_detail_ctivity);
		mercnum = MyCacheUtil.getshared(this).getString("MercNum", "");
		tag = getIntent().getStringExtra("tag");
		initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.tv_title_contre)).setText("积分");
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		allpiont = (TextView) findViewById(R.id.allpiont);
			
		mlistview = (ListView) findViewById(R.id.mylist);
		mlistview.setOnScrollListener(this);
		mList = new ArrayList<HashMap<String, Object>>();
		adatper = new PointListAdapter(this, mList);
		mlistview.setAdapter(adatper);

		if (mList.size() == 0) {
			// 加载数据
			loadMore();
		}
		
	}
	
	private void loadMore() {
		// TODO Auto-generated method stub
		if (page != 1 && page > allPageNum) {
			Toast.makeText(getApplicationContext(),"没有更多记录了",
					Toast.LENGTH_SHORT).show();
//			ToastCustom.showMessage(this, "没有更多记录了");
			return;
		}
		if (!isThreadRun) {
			isThreadRun = true;
			showLoadingDialog("正在查询中...");
			new Thread(run).start();
		}
	}
	Runnable run = new Runnable() {

		@Override
		public void run() {
			String[] values = { HttpUrls.POINTLIST + "", mercnum,
					page + "", PAGE_SIZE + "" };
			ArrayList<HashMap<String, Object>> list = NetCommunicate.getList(
					HttpUrls.POINTLIST, values,
					HttpKeys.POINTLIST_BACK);
			Message msg = new Message();
			if (list != null) {
				mList.addAll(list);
				if (mList.size() <= 0 || mList == null) {

					msg.what = 2;
				} else {
					msg.what = 1;
				}
				int allNum = 0;
				if (list.size() > 0) {
					if (list.get(0).get("TOLCNT") != null
							&& !list.get(0).get("TOLCNT").equals("null")) {
						allNum = Integer.parseInt(list.get(0).get("TOLCNT")
								.toString());
					}
					if(list.get(0).get("TOTALPOINT") != null
							&& !list.get(0).get("TOTALPOINT").equals("null")){
						point = list.get(0).get("TOTALPOINT").toString();
					}
					
				}

				if (allNum % PAGE_SIZE != 0) {
					allPageNum = allNum / PAGE_SIZE + 1;
				} else {
					allPageNum = allNum / PAGE_SIZE;
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
			switch (msg.what) {
			case 1:
				adatper.notifyDataSetChanged();
				
				if(point!=null){
					double d = Double.parseDouble(point);
					String r = String .format("%.2f",d/100);
					allpiont.setText(r);
				}
				

				break;
			case 2:
				Toast.makeText(getApplicationContext(), "暂无记录",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), "网络异常，请检查网络设置",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	private int lastItem;// 当前显示的最后一项

	@Override
	public void onScroll(AbsListView arg0, int firstVisibleItem,
			int visibleItemCount, int arg3) {
		// TODO Auto-generated method stub
		lastItem = firstVisibleItem + visibleItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == SCROLL_STATE_IDLE) {
			if (lastItem == mList.size()) {
				loadMore();
			}
		}

	}

}
