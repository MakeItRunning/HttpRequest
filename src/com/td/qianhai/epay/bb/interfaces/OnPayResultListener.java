package com.td.qianhai.epay.bb.interfaces;

import java.util.HashMap;

/**
 * 后台返回监听
 * @author liangge
 *
 */
public interface OnPayResultListener {
	public void backResult(HashMap<String, Object> result);
}
