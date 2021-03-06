package com.td.qianhai.epay.bb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.HttpHostConnectException;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.td.qianhai.epay.bb.adapter.MyCirecleAdapter1;
import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.beans.HttpKeys;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.beans.MyCircleBean1;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;

public class SubMyCircleActivity1 extends BaseActivity implements
		OnScrollListener{
	
	private int page = 1; // 页数
	private int allPageNum = 0; // 总页数
	private int PAGE_SIZE = 5;
	private ArrayList<HashMap<String, Object>> mList;
	private ListView listView;
	private String mobile;
	private View moreView;
	private View emptyView;
	private PopupWindow pop;  
	private boolean isThreadRun = false; // 加载数据线程运行状态
	private TextView null_data,tv_propty,query_tv,bt_title_right1;
	private MyCircleBean1 entitys;
	private MyCirecleAdapter1 adapter;
	private LayoutInflater inflater;
	private String querypbone = "";
	private String querypname = "",shrtype = "";
	private EditText query_ed;
	private boolean isrun = false;
	private TextView pro_pop_1,pro_pop_2,pro_pop_3,pro_pop_4,pro_pop;
	private View view;  
	private String name = "",phone = "";


	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppContext.getInstance().addActivity(this);
		setContentView(R.layout.activity_mycircle1);
//		mobile = ((AppContext)getApplication()).getMobile();
		mobile = MyCacheUtil.getshared(this).getString("Mobile", "");
		name = getIntent().getStringExtra("name");
		phone = getIntent().getStringExtra("phone");
		mobile = phone;
		inintview();
		initPopupWindow();
	}
	
	private void inintview() {
		((TextView) findViewById(R.id.tv_title_contre)).setText(name+"的推广");
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		
		inflater = LayoutInflater.from(this);
		mList = new ArrayList<HashMap<String, Object>>();
		null_data = (TextView) findViewById(R.id.null_data);
		listView = (ListView) findViewById(R.id.mylist);
		bt_title_right1 = (TextView) findViewById(R.id.bt_title_right1);
		bt_title_right1.setVisibility(View.GONE);
		((TextView)findViewById(R.id.bt_title_right)).setVisibility(View.GONE);
		query_tv = (TextView) findViewById(R.id.query_tv);
		query_ed = (EditText) findViewById(R.id.query_ed);
		tv_propty = (TextView) findViewById(R.id.tv_propty);
//		im_mycircle.setOnClickListener(new OnClickListener() {
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent it = new Intent(MyCircleActivity.this,PromotionEarningActivity.class);
//				startActivity(it);
//			}
//		});		
		bt_title_right1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	            if (pop.isShowing()) {  
	                pop.dismiss();  
	                
	            } else {  
	                pop.showAsDropDown(v);  
	            }
			}
		});
		moreView = inflater.inflate(R.layout.load, null);
		listView.addFooterView(moreView);
		moreView.setVisibility(View.GONE);
		listView.setOnScrollListener(this);
		
		

		
	if (mList.size() == 0) {
		emptyView = inflater.inflate(R.layout.progress_view, null);
		emptyView.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		((ViewGroup) listView.getParent()).addView(emptyView);
		listView.setEmptyView(emptyView);
		// 加载数据
		loadMore();
	    }
	
		adapter = new MyCirecleAdapter1(this, mList, 0);
		listView.setAdapter(adapter);
		
		
		query_ed.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					query_tv.setEnabled(true);
				} else {
					query_tv.setEnabled(false);
					querypname = "";
					querypbone = "";
					page = 1;
					mList.clear();
					loadMore();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

		query_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String s = query_ed.getText().toString();
				page = 1;
				mList.clear();
				if (gettext(s)) {
					querypbone = "";
					querypname = s;
				} else {
					querypbone = s;
					querypname = "";
				}
				loadMore();

			}
		});
	}
	
	private void initPopupWindow() {  
        view = this.getLayoutInflater().inflate(R.layout.agt_pop, null);  
        pro_pop = (TextView) view.findViewById(R.id.pro_pop);
        pro_pop_1 = (TextView) view.findViewById(R.id.pro_pop_1);
        pro_pop_2 = (TextView) view.findViewById(R.id.pro_pop_2);
        pro_pop_3 = (TextView) view.findViewById(R.id.pro_pop_3);
        pro_pop_4 = (TextView) view.findViewById(R.id.pro_pop_4);
        pop = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT,  
                ViewGroup.LayoutParams.WRAP_CONTENT);  
        pop.setOutsideTouchable(true);
        
        pro_pop.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                pop.dismiss();  
                querypname = "";
                page = 1;
                mList.clear();
                loadMore();
            }  
        });
        pro_pop_1.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                pop.dismiss();  
                querypname = "3";
                page = 1;
                mList.clear();
                loadMore();
            }  
        });
        pro_pop_2.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                pop.dismiss();  
                querypname = "2";
                page = 1;
                mList.clear();
                loadMore();
            }  
        });
        pro_pop_3.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                pop.dismiss();  
                querypname = "1";
                page = 1;
                mList.clear();
                loadMore();
            }  
        });
        pro_pop_4.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                pop.dismiss();  
                querypname = "0";
                page = 1;
                mList.clear();
                loadMore();
            }  
        });
	}
	
	private boolean gettext(String text){
		   Pattern p = Pattern.compile("[0-9]*");   
		     Matcher m = p.matcher(text);   
		     if(m.matches() ){  
		    	 return false;
		    	 
		      }   
//		     p=Pattern.compile("[a-zA-Z]");  
//		     m=p.matcher(text);  
//		     if(m.matches()){  
//		    	 return false;
//		     }  
//		char[] chars = text.toCharArray();
//		for (int i = 0; i < chars.length; i++) {
//			byte[] bytes = ("" + chars[i]).getBytes();
//			if (bytes.length == 2) {
//				int[] ints = new int[2];
//				ints[0] = bytes[0] & 0xff;
//				ints[1] = bytes[1] & 0xff;
//
//				if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
//						&& ints[1] <= 0xFE) {
//					Log.e("", "汉字"+text);
//					return true;
//				}
//			}
//		     } 
		     
		     p=Pattern.compile("[\u4e00-\u9fa5]");  
		     m=p.matcher(text.substring(0, 1));  
		     if(m.matches()){  
		    	 return true;
		     }
			return false;  
	}
	
	private void loadMore() {
		if (!isThreadRun) {
			isThreadRun = true;
		showLoadingDialog("正在查询中...");
		new Thread(run).start();
		}
	}


	Runnable run = new Runnable() {

		@Override
		public void run() {
			ArrayList<HashMap<String, Object>> list = null;
			try {
				
				String[] values = {mobile, page+"", PAGE_SIZE+"", querypbone, "2",querypname,""};
				list = NetCommunicate.executeHttpPostnull(HttpUrls.RECOMMENDATION,
						HttpKeys.RECOMMANDDETAIL,HttpKeys.RECOMMANDDETAILASK,values);
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

			if (list != null) {
				mList.addAll(list);
				if (list.size() <= 0 || list == null) {

					msg.what = 2;
				} else {
					msg.what = 1;
				}
				page ++;
			} else {
				
				msg.what = 3;
			}
			isThreadRun = false;
			
			handler.sendMessage(msg);
		}
	};

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				listView.setVisibility(View.VISIBLE);
				moreView.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();
				loadingDialogWhole.dismiss();
				break;
			case 2:
				null_data.setVisibility(View.VISIBLE);
				adapter.notifyDataSetChanged();
//				if (moreView != null) {
					emptyView.setVisibility(View.GONE);
					moreView.setVisibility(View.GONE);
//				}
				 Toast.makeText(getApplicationContext(),"加载完毕",
				 Toast.LENGTH_SHORT).show();
				 loadingDialogWhole.dismiss();
				break;
			case 3:
				if(isrun==false){
					new Thread(run).start();
					isrun = true;
				}else{
					loadingDialogWhole.dismiss();
					emptyView.setVisibility(View.GONE);
					Toast.makeText(getApplicationContext(), "网络不给力,请检查网络设置",
							Toast.LENGTH_SHORT).show();
					listView.setVisibility(View.GONE);
				}

				break;
			default:
				break;
			}
		};
	};
	
	private int lastItem;// 当前显示的最后一项

	@Override
	public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int arg3) {
		// TODO Auto-generated method stub
		lastItem = firstVisibleItem + visibleItemCount - 1; // 减1是因为上面加了个addFooterView
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == SCROLL_STATE_IDLE) {
			if (lastItem == mList.size()) {
				moreView.setVisibility(View.VISIBLE);
				loadMore();
			}
		}
	}

}
