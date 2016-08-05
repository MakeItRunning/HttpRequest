package com.td.qianhai.epay.bb.contacts1;

public class SortModel extends Contact {


	public SortModel(String name, String number, String sortKey,String idcard,String stat,String personpic) {
		super(name, number, sortKey, idcard, stat, personpic);
	}

	public String sortLetters; //显示数据拼音的首字母

	public SortToken sortToken=new SortToken();
}
