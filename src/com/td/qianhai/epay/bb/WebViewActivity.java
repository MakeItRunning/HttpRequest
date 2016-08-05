package com.td.qianhai.epay.bb;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.td.qianhai.epay.bb.beans.AppContext;
import com.td.qianhai.epay.bb.interfaces.JavaScriptinterface;

@SuppressLint("JavascriptInterface") public class WebViewActivity extends BaseActivity{
	private String URLs;
	private String datas;  
	private WebView wb_epos;
	
	@Override
	@SuppressLint({ "NewApi", "JavascriptInterface", "SetJavaScriptEnabled" })
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_activity);
		AppContext.getInstance().addActivity(this);
		Intent it = getIntent();
		URLs = it.getStringExtra("URLs");
		webview();
	}
	
	private void webview() {
		wb_epos = (WebView) findViewById(R.id.wv_epos);
		wb_epos.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		wb_epos.getSettings().setJavaScriptEnabled(true);
		wb_epos.getSettings().setSupportMultipleWindows(true);
		wb_epos.loadUrl(URLs);
		// 监听有需要再修改
		OnlineWebViewClient viewClient = new OnlineWebViewClient();
		wb_epos.setWebViewClient(viewClient);
		
	}
	
	public class OnlineWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			// TODO Auto-generated method stub
			handler.proceed();
		}
		
	}

}
