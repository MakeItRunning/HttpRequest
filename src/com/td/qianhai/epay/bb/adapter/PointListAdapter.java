package com.td.qianhai.epay.bb.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.R.color;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.td.qianhai.epay.bb.R;
import com.td.qianhai.mpay.utils.DateUtil;

/**
 * 结算方式的dialog
 * 
 * @author liangge
 * 
 */
public class PointListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, Object>> list;

	public PointListAdapter(Context context,
			ArrayList<HashMap<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		RadioViewHolder holder = null;
		if (convertView != null) {
			holder = (RadioViewHolder) convertView.getTag();
		} else {
			holder = new RadioViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.point_detail_item, null);
			holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
			holder.tv_2 = (TextView) convertView.findViewById(R.id.tv_2);
			convertView.setTag(holder);
		}

		HashMap<String, Object> item = list.get(position);

		if (item.get("POINT") != null) {
			double d = Double.parseDouble(item.get("POINT").toString());
			String r = String .format("%.2f",d/100);
			holder.tv_1.setText("+ "+r);
		}

		if (item.get("TXTIME") != null) {
			String time = list.get(position).get("TXTIME").toString();
			try {
				String date = DateUtil.formatDateLong(time);
//				String date = DateUtil.strToDateToLong(time);
				holder.tv_2.setText(date);
			} catch (Exception e) {
				// TODO: handle exception
				holder.tv_2.setText(time);
			}

		}

		return convertView;
	}

	class RadioViewHolder {
		TextView tv_1, tv_2;
	}

}
