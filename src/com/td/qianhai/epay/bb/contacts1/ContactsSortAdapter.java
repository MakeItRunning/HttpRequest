package com.td.qianhai.epay.bb.contacts1;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.tsz.afinal.FinalBitmap;

import com.td.qianhai.epay.bb.R;
import com.td.qianhai.epay.bb.beans.HttpUrls;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;


public class ContactsSortAdapter extends BaseAdapter implements SectionIndexer {
	private List<SortModel> mList;
	private List<SortModel> mSelectedList;
	private Context mContext;
	LayoutInflater mInflater;
	private boolean istag = false;
	private boolean ischeck = false;
	private boolean lock = true;
	private int a = -1;

	public ContactsSortAdapter(Context mContext, List<SortModel> list,int a) {
		this.mContext = mContext;
		this.a = a;
		mSelectedList = new ArrayList<SortModel>();
		if (list == null) {
			this.mList = new ArrayList<SortModel>();
		} else {
			this.mList = list;
		}
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<SortModel> list) {
//		if (list == null) {
//			this.mList = new ArrayList<SortModel>();
//		} else {
			this.mList = list;
//		}
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.mList.size();
	}

	public Object getItem(int position) {
		return mList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModel mContent = mList.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.fragment_phone_constacts_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
//			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvNumber = (TextView) view.findViewById(R.id.phone);
//			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
			
			viewHolder.cbChecked = (CheckBox) view.findViewById(R.id.cbChecked);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.icon.setImageResource(R.drawable.headico_img);
		if(a==0){
			viewHolder.cbChecked .setVisibility(View.GONE);
		}
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.sortLetters);
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		if(this.mList.get(position).name==null||this.mList.get(position).name.equals("null")||this.mList.get(position).name.equals("")){
			viewHolder.tvTitle.setText("未知");
		}else{
			viewHolder.tvTitle.setText(this.mList.get(position).name);
		}
		
		if(this.mList.get(position).number==null||this.mList.get(position).number.equals("null")||this.mList.get(position).number.equals("")){
			viewHolder.tvNumber.setText("");
		}else{
			viewHolder.tvNumber.setText(this.mList.get(position).number);
		}
		
		if(this.mList.get(position).personpic!=null&&!this.mList.get(position).personpic.equals("null")&&!this.mList.get(position).personpic.equals("")){
			FinalBitmap.create(mContext).display(viewHolder.icon,
					HttpUrls.HOST_POSM + mList.get(position).personpic,
					viewHolder.icon.getWidth(),
					viewHolder.icon.getHeight(), null, null);
		}
		
		
		viewHolder.cbChecked.setChecked(isSelected(mContent));
	
		if(ischeck){
//			mSelectedList.clear();
//			if(mSelectedList.size()<=0){
//				for (int i = 0; i < mList.size(); i++) {
//					mSelectedList.add(mList.get(i));
//				}	
//			}
//			viewHolder.cbChecked.setVisibility(View.VISIBLE);
			viewHolder.cbChecked.setChecked(isSelected(mContent));
//			if (!mSelectedList.contains(mList.get(position))) {
//				mSelectedList.add(mList.get(position));
//			}

			
		}else{
//			mSelectedList.clear();
			viewHolder.cbChecked.setChecked(isSelected(mContent));
		
		}
//		if(istag){
//			viewHolder.cbChecked.setVisibility(View.VISIBLE);
//		}else{
//			viewHolder.cbChecked.setVisibility(View.GONE);
//		}
		
//		viewHolder.tvTitle.setText(this.list.get(position).getName());
//		
//		//根据position获取分类的首字母的Char ascii值
//		int section = getSectionForPosition(position);
//
//		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
//		if (position == getPositionForSection(section)) {
//			viewHolder.tvLetter.setVisibility(View.VISIBLE);
//			viewHolder.tvLetter.setText(mContent.sortLetters);
//		} else {
//			viewHolder.tvLetter.setVisibility(View.GONE);
//		}
//
//		viewHolder.tvTitle.setText(this.mList.get(position).name);
//		viewHolder.tvNumber.setText(this.mList.get(position).number);
//		viewHolder.cbChecked.setChecked(isSelected(mContent));

		return view;

	}

	public static class ViewHolder {
		public TextView tvLetter;
		public TextView tvTitle;
		public TextView tvNumber;
		public ImageView icon;
		public CheckBox cbChecked;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return mList.get(position).sortLetters.charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = mList.get(i).sortLetters;
			char firstChar = sortStr.toUpperCase(Locale.CHINESE).charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	private boolean isSelected(SortModel model) {
		return mSelectedList.contains(model);
		//return true;
	}
	
	public  void visib(boolean istags) {
		this.istag = istags;
		this.notifyDataSetChanged();
		//return true;
	}
	
	public  void setcheck(boolean ischeck) {
		this.ischeck = ischeck;
//		this.istag = istags;
		this.notifyDataSetChanged();
		//return true;
	}

	public void toggleChecked(int position) {
		if (isSelected(mList.get(position))) {
			removeSelected(position);
		} else {
			setSelected(position);
		}
		
	}

	private void setSelected(int position) {
		if (!mSelectedList.contains(mList.get(position))) {
			mSelectedList.add(mList.get(position));
		}
	}

	private void removeSelected(int position) {
		if (mSelectedList.contains(mList.get(position))) {
			mSelectedList.remove(mList.get(position));
		}
	}

	public List<SortModel> getSelectedList() {
		return mSelectedList;
	}
}