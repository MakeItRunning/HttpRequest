package com.td.qianhai.fragmentmanager;

import java.util.List;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.td.qianhai.epay.bb.R;
import com.td.qianhai.epay.bb.UserActivity;
import com.td.qianhai.epay.bb.WeChatRegistrActivity;
import com.td.qianhai.epay.bb.mail.utils.MyCacheUtil;
import com.td.qianhai.epay.bb.views.ToastCustom;

/**
 * Created with IntelliJ IDEA. Author: wangjie email:tiantian.china.2@gmail.com
 * Date: 13-10-10 Time: 上午9:25
 */
public class FragmentTabAdapter implements RadioGroup.OnCheckedChangeListener {
	private List<Fragment> fragments; // 一个tab页面对应一个Fragment
	private RadioGroup rgs; // 用于切换tab
	private FragmentActivity fragmentActivity; // Fragment所属的Activity
	private int fragmentContentId; // Activity中所要被替换的区域的id
	private List<RadioButton> view;
	private int currentTab; // 当前Tab页面索引
	private String tagsts = "",wecname = "";

	private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener; // 用于让调用者在切换tab时候增加新的功能

	public FragmentTabAdapter(FragmentActivity fragmentActivity,
			List<Fragment> fragments, int fragmentContentId, RadioGroup rgs, List<RadioButton> radioview) {
		this.fragments = fragments;
		this.rgs = rgs;
		this.view = radioview;
		this.fragmentActivity = fragmentActivity;
		this.fragmentContentId = fragmentContentId;
		tagsts = MyCacheUtil.getshared(fragmentActivity).getString("STS", "");
		wecname = MyCacheUtil.getshared(fragmentActivity).getString("NICKNAME", "");

		// 默认显示第一页
		FragmentTransaction ft = fragmentActivity.getSupportFragmentManager()
				.beginTransaction();
		ft.add(fragmentContentId, fragments.get(0));
		ft.commit();

		rgs.setOnCheckedChangeListener(this);

	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		for (int i = 0; i < rgs.getChildCount(); i++) {
			if (rgs.getChildAt(i).getId() == checkedId) {
				Fragment fragment = fragments.get(i);
				FragmentTransaction ft = obtainFragmentTransaction(i);

				getCurrentFragment().onPause(); // 暂停当前tab
				// getCurrentFragment().onStop(); // 暂停当前tab

				if (fragment.isAdded()) {
					// fragment.onStart(); // 启动目标tab的onStart()
					fragment.onResume(); // 启动目标tab的onResume()
				} else {
					ft.add(fragmentContentId, fragment);
				}
				// showTab(i); // 显示目标tab
				// ft.commit();
				switch (i) {
				case 0:
//					FmMainActivity.title_name.setText("主页");
					showTab(i); // 显示目标tab
					ft.commit();
					view.get(0).setTextColor(fragmentActivity.getResources().getColor(R.color.b_gay1));
					view.get(1).setTextColor(fragmentActivity.getResources().getColor(R.color.b_gay));
					view.get(2).setTextColor(fragmentActivity.getResources().getColor(R.color.b_gay));
					break;
				case 1:
//					FmMainActivity.title_name.setText("附近");
					Toast.makeText(fragmentActivity.getApplicationContext(), "此功能即将开通！",
							Toast.LENGTH_SHORT).show();
					break;
				case 2:
					tagsts = MyCacheUtil.getshared(fragmentActivity).getString("STS", "");
					wecname = MyCacheUtil.getshared(fragmentActivity).getString("NICKNAME", "");
					if (tagsts==null||tagsts.equals("")) {
						if(wecname==null||wecname.equals("")){
							FmMainActivity.context.finish();
							Intent it = new Intent(fragmentActivity,
									UserActivity.class);
							fragmentActivity.startActivity(it);
							return;
						}else{
							Intent it = new Intent(fragmentActivity,
									WeChatRegistrActivity.class);
							fragmentActivity.startActivity(it);
							return;
						}

//						Toast.makeText(con.getApplicationContext(), "请先登录",
//								Toast.LENGTH_SHORT).show();
					}
					FmMainActivity.my_remind.setVisibility(View.INVISIBLE);
//					FmMainActivity.title_name.setText("我的");
					 showTab(i); // 显示目标tab
					 ft.commit();
						view.get(0).setTextColor(fragmentActivity.getResources().getColor(R.color.b_gay));
						view.get(1).setTextColor(fragmentActivity.getResources().getColor(R.color.b_gay1));
						view.get(2).setTextColor(fragmentActivity.getResources().getColor(R.color.b_gay));
//					ToastCustom.showMessage(fragmentActivity, "此功能即将开通！",
//							Toast.LENGTH_SHORT);
					break;
				case 3:
					 showTab(i); // 显示目标tab
					 ft.commit();
//					FmMainActivity.title_name.setText("更多");
//					ToastCustom.showMessage(fragmentActivity, "此功能即将开通！",
//							Toast.LENGTH_SHORT);
						view.get(0).setTextColor(fragmentActivity.getResources().getColor(R.color.b_gay));
						view.get(1).setTextColor(fragmentActivity.getResources().getColor(R.color.b_gay));
						view.get(2).setTextColor(fragmentActivity.getResources().getColor(R.color.b_gay1));
					break;

				default:
					break;
				}

				// 如果设置了切换tab额外功能功能接口
				if (null != onRgsExtraCheckedChangedListener) {
					onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(
							radioGroup, checkedId, i);
				}

			}
		}

	}

	/**
	 * 切换tab
	 * 
	 * @param idx
	 */
	public void showTab(int idx) {
		for (int i = 0; i < fragments.size(); i++) {
			Fragment fragment = fragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(idx);

			if (idx == i) {
				ft.show(fragment);
			} else {
				ft.hide(fragment);
			}
			ft.commit();
		}
		currentTab = idx; // 更新目标tab为当前tab
	}

	/**
	 * 获取一个带动画的FragmentTransaction
	 * 
	 * @param index
	 * @return
	 */
	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = fragmentActivity.getSupportFragmentManager()
				.beginTransaction();
		// 设置切换动画
		if (index > currentTab) {
			ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
		} else {
			ft.setCustomAnimations(R.anim.slide_right_in,
					R.anim.slide_right_out);
		}
		return ft;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public Fragment getCurrentFragment() {
		return fragments.get(currentTab);
	}

	public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
		return onRgsExtraCheckedChangedListener;
	}

	public void setOnRgsExtraCheckedChangedListener(
			OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
		this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
	}

	/**
	 * 切换tab额外功能功能接口
	 */
	static class OnRgsExtraCheckedChangedListener {
		public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,
				int checkedId, int index) {

		}
	}

}
