package com.td.qianhai.epay.bb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.HttpHostConnectException;

import com.td.qianhai.epay.bb.beans.HttpKeys;
import com.td.qianhai.epay.bb.beans.HttpUrls;
import com.td.qianhai.epay.bb.contacts1.CharacterParser;
import com.td.qianhai.epay.bb.contacts1.ContactsSortAdapter;
import com.td.qianhai.epay.bb.contacts1.PinyinComparator;
import com.td.qianhai.epay.bb.contacts1.SideBar;
import com.td.qianhai.epay.bb.contacts1.SortModel;
import com.td.qianhai.epay.bb.contacts1.SortToken;
import com.td.qianhai.epay.bb.contacts1.ContactsSortAdapter.ViewHolder;
import com.td.qianhai.epay.bb.contacts1.SideBar.OnTouchingLetterChangedListener;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.net.NetCommunicate;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyFriendListActivity extends BaseActivity{
	
	private LinearLayout add_newfriends;
	private String mercnum;
	private ArrayList<HashMap<String, Object>> mList;
	private List<SortModel> mAllContactsList;
	private ContactsSortAdapter adapter;
	private boolean istag = true;
	private boolean ischeck = true;
	ListView mListView;
	EditText etSearch;
	ImageView ivClearText;
	private SideBar sideBar;
	private TextView dialog;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlist_activity);
		mercnum = MyCacheUtil.getshared(this).getString("MercNum", "");
		mList = new ArrayList<HashMap<String, Object>>();
//		initview();
		init();
		
		
		
	}
	
	private void loadMore() {
			showLoadingDialog("正在查询中...");
			new Thread(run).start();

	}

	private void initview() {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.tv_title_contre)).setText("我的好友");
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

	}
	

	Runnable run = new Runnable() {

		@Override
		public void run() {
			ArrayList<HashMap<String, Object>> list = null;
			try {
				
				String[] values = {mercnum};
				list = NetCommunicate.executeHttpPostnull2(HttpUrls.FINDFRIENDLIST,
						HttpKeys.FINDFRIENDLIST_BACK,HttpKeys.FINDFRIENDLIST_ASK,values);
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
				if (mList.size() <= 0 || mList == null) {

					msg.what = 2;
				} else {
					msg.what = 1;
				}
			} else {
				msg.what = 3;
			}
			loadingDialogWhole.dismiss();
			handler.sendMessage(msg);
		}
	};

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				mAllContactsList = new ArrayList<SortModel>();
				for (int i = 0; i < mList.size(); i++) {
					
					String phoneNumber = "";
					String contactName = "";
					String  idcard = "";
					String  stat = "";
					String personpic = "";
					if(mList.get(i).get("phonenumber")!=null){
						phoneNumber = mList.get(i).get("phonenumber").toString();
					}
					if( mList.get(i).get("mercnam")!=null){
						contactName =  mList.get(i).get("mercnam").toString();
					}
					if(mList.get(i).get("status")!=null){
						stat = mList.get(i).get("status").toString();
					}
					if(mList.get(i).get("personpic")!=null){
						personpic = mList.get(i).get("personpic").toString();
					}
					if(mList.get(i).get("corporateidentity")!=null){
						idcard = mList.get(i).get("corporateidentity").toString();
					}
					if (TextUtils.isEmpty(phoneNumber))
						continue;
					String sortKey = null;
					//System.out.println(sortKey);sdsd
					SortModel sortModel = new SortModel(contactName, phoneNumber, sortKey,idcard,stat,personpic);
					//优先使用系统sortkey取,取不到再使用工具取
					String sortLetters = getSortLetterBySortKey(sortKey);
					if (sortLetters == null) {
						sortLetters = getSortLetter(contactName);
					}
					sortModel.sortLetters = sortLetters;
					sortModel.sortToken = parseSortKey(sortKey);
					mAllContactsList.add(sortModel);
					
				}
				runOnUiThread(new Runnable() {
					public void run() {
						Collections.sort(mAllContactsList, pinyinComparator);
						adapter.updateListView(mAllContactsList);
						
					}
				});
				break;
			case 2:
				adapter.notifyDataSetChanged();
				 Toast.makeText(getApplicationContext(),"加载完毕",
				 Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), "网络不给力,请检查网络设置",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};
	
	private void init() {
		initView();
		initListener();
		loadMore();
	}

	private void initListener() {

		/**清除输入字符**/
		ivClearText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				etSearch.setText("");
			}
		});
		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable e) {

				String content = etSearch.getText().toString();
				if ("".equals(content)) {
					ivClearText.setVisibility(View.INVISIBLE);
				} else {
					ivClearText.setVisibility(View.VISIBLE);
				}
				if (content.length() > 0) {
					ArrayList<SortModel> fileterList = (ArrayList<SortModel>) search(content);
					adapter.updateListView(fileterList);
					//mAdapter.updateData(mContacts);
				} else {
					adapter.updateListView(mAllContactsList);
				}
				mListView.setSelection(0);

			}

		});

		//设置右侧[A-Z]快速导航栏触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					mListView.setSelection(position);
				}
			}
		});
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
//				ViewHolder viewHolder = (ViewHolder) view.getTag();
//				if(!istag){
//					viewHolder.cbChecked.performClick();
//					adapter.toggleChecked(position);	
////					Log.e("", "size =  "+adapter.getSelectedList().size());
//				}
//			}
//		});

	}

	private void initView() {
//		((TextView) findViewById(R.id.bt_title_right)).setVisibility(View.GONE);
//		((TextView) findViewById(R.id.bt_title_right1)).setVisibility(View.GONE);
		((TextView) findViewById(R.id.tv_title_contre)).setText("我的好友");
//		((TextView) findViewById(R.id.bt_title_right1)).setText("编辑");
//		cbCheckeds.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				if(arg1){
//
//				}else{
//
//				}
//			}
//		});
		add_newfriends = (LinearLayout) findViewById(R.id.add_newfriends);

		
		add_newfriends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it = new Intent(MyFriendListActivity.this,AddNewFriendActivity.class);
				startActivity(it);
			}
		});
		
		
	
		findViewById(R.id.bt_title_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		
//		findViewById(R.id.bt_title_right1).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				if(istag){
//					istag = false;
//					adapter.visib(true);
//				}else{
//					istag = true;
//					adapter.visib(false);
//				}
//				
//			}
//		});
		
		
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		ivClearText = (ImageView) findViewById(R.id.ivClearText);
		etSearch = (EditText) findViewById(R.id.et_search);
		mListView = (ListView) findViewById(R.id.country_lvcountry);

		/** 给ListView设置adapter **/
		characterParser = CharacterParser.getInstance();
		mAllContactsList = new ArrayList<SortModel>();
		pinyinComparator = new PinyinComparator();
		Collections.sort(mAllContactsList, pinyinComparator);// 根据a-z进行排序源数据
		adapter = new ContactsSortAdapter(this, mAllContactsList,0);
		mListView.setAdapter(adapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long id) {
				// TODO Auto-generated method stub
				
				String  pohone = "";
				String  idcard = "";
				String  stat = "";
				String  name = "";
				String personpic = "";
				
				
				if(mAllContactsList.get((int)id).number!=null){
					pohone = mAllContactsList.get((int)id).number.toString();
				}
				if( mAllContactsList.get((int)id).name!=null){
					name =  mAllContactsList.get((int)id).name.toString();
				}
				if( mAllContactsList.get((int)id).stat!=null){
					stat =mAllContactsList.get((int)id).stat.toString();
				}
				if( mAllContactsList.get((int)id).personpic!=null){
					personpic = mAllContactsList.get((int)id).personpic.toString();
				}
				if( mAllContactsList.get((int)id).idcard!=null){
					idcard =mAllContactsList.get((int)id).idcard.toString();
				}
//				if(mList.get((int)id).get("phonenumber")!=null){
//					pohone = mList.get((int)id).get("phonenumber").toString();
//				}
//				if(mList.get((int)id).get("status")!=null){
//					stat = mList.get((int)id).get("status").toString();
//				}
//				if(mList.get((int)id).get("personpic")!=null){
//					personpic = mList.get((int)id).get("personpic").toString();
//				}
//				if(mList.get((int)id).get("corporateidentity")!=null){
//					idcard = mList.get((int)id).get("corporateidentity").toString();
//				}
//				if(mList.get((int)id).get("mercnam")!=null){
//					name = mList.get((int)id).get("mercnam").toString();
//				}
				Intent it = new Intent(MyFriendListActivity.this,FriendsInfoActivity.class);
				it.putExtra("pohone", pohone);
				it.putExtra("idcard", idcard);
				it.putExtra("stat", stat);
				it.putExtra("personpic", personpic);
				it.putExtra("name", name);
				startActivity(it);
			}
		});
		
		
		
	}

	private void loadContacts() {
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
					if (phoneCursor.getCount() > 0) {
						mAllContactsList = new ArrayList<SortModel>();
						while (phoneCursor.moveToNext()) {
							String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
							if (TextUtils.isEmpty(phoneNumber))
								continue;
							String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
							String sortKey = phoneCursor.getString(SORT_KEY_INDEX);
							//System.out.println(sortKey);
							SortModel sortModel = new SortModel(contactName, phoneNumber, sortKey,"","","");
							//优先使用系统sortkey取,取不到再使用工具取
							String sortLetters = getSortLetterBySortKey(sortKey);
							if (sortLetters == null) {
								sortLetters = getSortLetter(contactName);
							}
							sortModel.sortLetters = sortLetters;
							sortModel.sortToken = parseSortKey(sortKey);
							mAllContactsList.add(sortModel);
//							number.setText("   已选择联系人 0/"+mAllContactsList.size());
						}
					}
					phoneCursor.close();
					runOnUiThread(new Runnable() {
						public void run() {
							Collections.sort(mAllContactsList, pinyinComparator);
							adapter.updateListView(mAllContactsList);
							
						}
					});
				} catch (Exception e) {
					Log.e("xbc", e.getLocalizedMessage());
				}
			}
		}).start();
	}

	/**
	 * 名字转拼音,取首字母
	 * @param name
	 * @return
	 */
	private String getSortLetter(String name) {
		String letter = "#";
		if (name == null) {
			return letter;
		}
		//汉字转换成拼音
		String pinyin = characterParser.getSelling(name);
		String sortString = pinyin.substring(0, 1).toUpperCase(Locale.CHINESE);

//		// 正则表达式，判断首字母是否是英文字母
//		if (sortString.matches("[A-Z]")) {
//			letter = sortString.toUpperCase(Locale.CHINESE);
//		}
		return getPYIndexStr(sortString,true);
	}

	/**
	 * 取sort_key的首字母
	 * @param sortKey
	 * @return
	 */
	private String getSortLetterBySortKey(String sortKey) {
		if (sortKey == null || "".equals(sortKey.trim())) {
			return null;
		}
		String letter = "#";
		//汉字转换成拼音
		String sortString = sortKey.trim().substring(0, 1).toUpperCase(Locale.CHINESE);
//		// 正则表达式，判断首字母是否是英文字母
//		if (sortString.matches("[A-Z]")||sortString.matches("[a-z]")) {
//			Log.e("", "pinyin1 = = "+sortString);
//			letter = sortString.toUpperCase(Locale.CHINESE);
//		}
		return getPYIndexStr(sortString,true);
	}

	/**
	 * 模糊查询
	 * @param str
	 * @return
	 */
	private List<SortModel> search(String str) {
		List<SortModel> filterList = new ArrayList<SortModel>();// 过滤后的list
		//if (str.matches("^([0-9]|[/+])*$")) {// 正则表达式 匹配号码
		if (str.matches("^([0-9]|[/+]).*")) {// 正则表达式 匹配以数字或者加号开头的字符串(包括了带空格及-分割的号码)
			String simpleStr = str.replaceAll("\\-|\\s", "");
			for (SortModel contact : mAllContactsList) {
				if (contact.number != null && contact.name != null) {
					if (contact.simpleNumber.contains(simpleStr) || contact.name.contains(str)) {
						if (!filterList.contains(contact)) {
							filterList.add(contact);
						}
					}
				}
			}
		}else {
			for (SortModel contact : mAllContactsList) {
				if (contact.number != null && contact.name != null) {
					//姓名全匹配,姓名首字母简拼匹配,姓名全字母匹配
					if (contact.name.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))
							|| contact.sortKey.toLowerCase(Locale.CHINESE).replace(" ", "").contains(str.toLowerCase(Locale.CHINESE))
							|| contact.sortToken.simpleSpell.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))
							|| contact.sortToken.wholeSpell.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))) {
						if (!filterList.contains(contact)) {
							filterList.add(contact);
						}
					}
				}
			}
		}
		return filterList;
	}

	String chReg = "[\\u4E00-\\u9FA5]+";//中文字符串匹配

	//String chReg="[^\\u4E00-\\u9FA5]";//除中文外的字符匹配
	/**
	 * 解析sort_key,封装简拼,全拼
	 * @param sortKey
	 * @return
	 */
	public SortToken parseSortKey(String sortKey) {
		SortToken token = new SortToken();
		if (sortKey != null && sortKey.length() > 0) {
			//其中包含的中文字符
			String[] enStrs = sortKey.replace(" ", "").split(chReg);
			for (int i = 0, length = enStrs.length; i < length; i++) {
				if (enStrs[i].length() > 0) {
					//拼接简拼
					token.simpleSpell += enStrs[i].charAt(0);
					token.wholeSpell += enStrs[i];
				}
			}
		}
		return token;
	}

/*
* 返回首字母

* @param strChinese

* @param bUpCase

* @return

*/

   public static String getPYIndexStr(String strChinese, boolean bUpCase){

       try{

           StringBuffer buffer = new StringBuffer();

           byte b[] = strChinese.getBytes("GBK");//把中文转化成byte数组

           for(int i = 0; i < b.length; i++){

               if((b[i] & 255) > 128){

                   int char1 = b[i++] & 255;

                   char1 <<= 8;//左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n位，就相当于乘上2的n次方

                   int chart = char1 + (b[i] & 255);

                   buffer.append(getPYIndexChar((char)chart, bUpCase));

                   continue;

               }

               char c = (char)b[i];

               if(!Character.isJavaIdentifierPart(c))//确定指定字符是否可以是 Java 标识符中首字符以外的部分。

                   c = 'A';

               buffer.append(c);

           }

           return buffer.toString();

       }catch(Exception e){

           System.out.println((new StringBuilder()).append("\u53D6\u4E2D\u6587\u62FC\u97F3\u6709\u9519").append(e.getMessage()).toString());

       }

       return null;

   }

   /**

    * 得到首字母

    * @param strChinese

    * @param bUpCase

    * @return

    */

   private static char getPYIndexChar(char strChinese, boolean bUpCase){

       int charGBK = strChinese;

       char result;

       if(charGBK >= 45217 && charGBK <= 45252)

           result = 'A';

       else

       if(charGBK >= 45253 && charGBK <= 45760)

           result = 'B';

       else

       if(charGBK >= 45761 && charGBK <= 46317)

           result = 'C';

       else

       if(charGBK >= 46318 && charGBK <= 46825)

           result = 'D';

       else

       if(charGBK >= 46826 && charGBK <= 47009)

           result = 'E';

       else

       if(charGBK >= 47010 && charGBK <= 47296)

           result = 'F';

       else

       if(charGBK >= 47297 && charGBK <= 47613)

           result = 'G';

       else

       if(charGBK >= 47614 && charGBK <= 48118)

           result = 'H';

       else

       if(charGBK >= 48119 && charGBK <= 49061)

           result = 'J';

       else

       if(charGBK >= 49062 && charGBK <= 49323)

           result = 'K';

       else

       if(charGBK >= 49324 && charGBK <= 49895)

           result = 'L';

       else

       if(charGBK >= 49896 && charGBK <= 50370)

           result = 'M';

       else

       if(charGBK >= 50371 && charGBK <= 50613)

           result = 'N';

       else

       if(charGBK >= 50614 && charGBK <= 50621)

           result = 'O';

       else

       if(charGBK >= 50622 && charGBK <= 50905)

           result = 'P';

       else

       if(charGBK >= 50906 && charGBK <= 51386)

           result = 'Q';

       else

       if(charGBK >= 51387 && charGBK <= 51445)

           result = 'R';

       else

       if(charGBK >= 51446 && charGBK <= 52217)

           result = 'S';

       else

       if(charGBK >= 52218 && charGBK <= 52697)

           result = 'T';

       else

       if(charGBK >= 52698 && charGBK <= 52979)

           result = 'W';

       else

       if(charGBK >= 52980 && charGBK <= 53688)

           result = 'X';

       else

       if(charGBK >= 53689 && charGBK <= 54480)

           result = 'Y';

       else

       if(charGBK >= 54481 && charGBK <= 55289)

           result = 'Z';

       else

           result = (char)(65 + (new Random()).nextInt(25));

       if(!bUpCase)

           result = Character.toLowerCase(result);

       return result;

   }

	
}
