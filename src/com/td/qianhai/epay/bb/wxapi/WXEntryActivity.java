package com.td.qianhai.epay.bb.wxapi;

import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {
//	private TextView tv_1;
//	private UMWXHandler wxHandler;
//	private  UMSocialService mController;
//  @Override  
//  protected void onCreate(Bundle savedInstanceState) {  
//      // TODO Auto-generated method stub  
//      super.onCreate(savedInstanceState);  
//      setContentView(R.layout.wecaht_layout);  
//      tv_1 = (TextView) findViewById(R.id.tv_1);
//      addWXPlatform();
  }  
	
	
	
	
	
//	/**
//     * @功能描述 : 添加微信平台授权登录
//     * @return
//     */
//    private void addWXPlatform() {
//        // 注意：在微信授权的时候，必须传递appSecret
//        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
//        // 添加微信平台，APP_ID、APP_SECRET都是在微信开放平台，移动应用通过审核后获取到的
//        wxHandler = new UMWXHandler(WXEntryActivity.this, "wx49699f2e27560127", "f4849377be84176abe3d88cb78043a51");
//        wxHandler.setRefreshTokenAvailable(false);
//        wxHandler.addToSocialSDK();
//        login(SHARE_MEDIA.WEIXIN);
// 
//    }
//    /**
//     * 授权。如果授权成功，则获取用户信息
//     *
//     * @param platform
//     */
//    private void login(final SHARE_MEDIA platform) {
//        mController.doOauthVerify(WXEntryActivity.this, platform,
//                new SocializeListeners.UMAuthListener() {
//                    @Override
//                    public void onStart(SHARE_MEDIA platform) {
//                        Toast.makeText(WXEntryActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void onError(SocializeException e,
//                                        SHARE_MEDIA platform) {
//                        Toast.makeText(WXEntryActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
//                        // 获取uid
//                        String uid = value.getString("uid");
//                        if (!TextUtils.isEmpty(uid)) {
//                            // uid不为空，获取用户信息
//                            getUserInfo(platform);
//                            Toast.makeText(WXEntryActivity.this,"uid is - -  "+uid, Toast.LENGTH_LONG).show();
//                            wxHandler.getWXApi().detach();
//                        } else {
//                        	logout(platform);
//                            Toast.makeText(WXEntryActivity.this, "授权失败...", Toast.LENGTH_LONG).show();
//                        }
//                    }
// 
//                    @Override
//                    public void onCancel(SHARE_MEDIA platform) {
////                    	logout(platform);
////                        Toast.makeText(WXEntryActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
// 
//    /**
//     * 获取用户信息
//     *
//     * @param platform
//     */
//    private void getUserInfo(SHARE_MEDIA platform) {
//        mController.getPlatformInfo(WXEntryActivity.this, platform,
//                new SocializeListeners.UMDataListener() {
// 
//                    @Override
//                    public void onStart() {
// 
//                    }
//                    
//                    
//					@Override
//					public void onComplete(int status, Map<String, Object> info) {
//						// TODO Auto-generated method stub
////                         String showText = "";
////                         if (status == StatusCode.ST_CODE_SUCCESSED) {
////                         showText = "用户名：" +
////                         info.get("screen_name").toString();
//////                         Log.d("#########", "##########" + info.toString());
////                         } else {
////                         showText = "获取用户信息失败";
////                         }
////                         tv_1.setText(showText);
// 
//                        if (info != null) {
//                        	  String infoStr = info.toString();
//                            Toast.makeText(WXEntryActivity.this, info.toString(), Toast.LENGTH_SHORT).show();
//                        
////                            CommonUtils.LogWuwei(tag,"info is "+infoStr);
//                        }
//					}
//
//					
//					
// 
//					
////                    @Override
////                    public void onComplete(int status, Map<String, Object> info) {
////                        // String showText = "";
////                        // if (status == StatusCode.ST_CODE_SUCCESSED) {
////                        // showText = "用户名：" +
////                        // info.get("screen_name").toString();
////                        // Log.d("#########", "##########" + info.toString());
////                        // } else {
////                        // showText = "获取用户信息失败";
////                        // }
//// 
////                        if (info != null) {
////                            Toast.makeText(WXEntryActivity.this, info.toString(), Toast.LENGTH_SHORT).show();
////                            String infoStr = info.toString();
//////                            CommonUtils.LogWuwei(tag,"info is "+infoStr);
////                        }
////                    }
//                });
//    }
// 
//    /**
//     * 注销本次登陆
//     * @param platform
//     */
//    private void logout(final SHARE_MEDIA platform) {
//        mController.deleteOauth(WXEntryActivity.this, platform,
//                new SocializeListeners.SocializeClientListener() {
// 
//            @Override
//            public void onStart() {
// 
//            }
//
//			@Override
//			public void onComplete(int status, SocializeEntity arg1) {
//				// TODO Auto-generated method stub
//                String showText = "解除" + platform.toString() + "平台授权成功";
//                if (status != StatusCode.ST_CODE_SUCCESSED) {
//                    showText = "解除" + platform.toString() + "平台授权失败[" + status + "]";
//                }
//                Toast.makeText(WXEntryActivity.this, showText, Toast.LENGTH_SHORT).show();
//			}
// 
////            @Override
////            public void onComplete(int status, SocializeEntity entity) {
////                String showText = "解除" + platform.toString() + "平台授权成功";
////                if (status != StatusCode.ST_CODE_SUCCESSED) {
////                    showText = "解除" + platform.toString() + "平台授权失败[" + status + "]";
////                }
////                Toast.makeText(WXEntryActivity.this, showText, Toast.LENGTH_SHORT).show();
////            }
//        });
//    }
//	
//}


//import com.td.qianhai.epay.bb.R;
//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.modelmsg.SendAuth;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//
//public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
////	private Button checkLogin; 
////	private static final int RETURN_MSG_TYPE_LOGIN = 1; 
////	private static final int RETURN_MSG_TYPE_SHARE = 2; 
////	private static final String TAG = "WXEntryActivity"; 
////	@Override protected void onCreate(Bundle savedInstanceState) { 
////		super.onCreate(savedInstanceState); 
////		setContentView(R.layout.activity_wxentry); 
////		checkLogin = (Button) findViewById(R.id.check_login); 
////		checkLogin.setOnClickListener(new View.OnClickListener() { 
////			@Override 
////			public void onClick(View v) {
////				if (!MTNApplication.api.isWXAppInstalled()) { 
////					AppData.showToast("您还未安装微信客户端"); return; 
////					} 
////				final SendAuth.Req req = new SendAuth.Req(); 
////				req.scope = "snsapi_userinfo";
////				req.state = "diandi_wx_login";
////				MTNApplication.api.sendReq(req); } 
////			}); //如果没回调onResp，八成是这句没有写 MTNApplication.api.handleIntent(getIntent(), this); } 
////		//微信直接发送给app的消息处理回调 @Override public void onReq(BaseReq baseReq) { } 
////		//app发送消息给微信，处理返回消息的回调 
////		@Override public void onResp(BaseResp resp) { 
////			switch (resp.errCode) { 
////			case BaseResp.ErrCode.ERR_AUTH_DENIED: 
////			case BaseResp.ErrCode.ERR_USER_CANCEL: 
////				if (RETURN_MSG_TYPE_SHARE == resp.getType()) 
////					AppData.showToast("分享失败"); 
////				else AppData.showToast("登录失败");
////				break; 
////			case BaseResp.ErrCode.ERR_OK:
////					switch (resp.getType()) { case RETURN_MSG_TYPE_LOGIN: //拿到了微信返回的code,立马再去请求access_token
////						String code = ((SendAuth.Resp) resp).code; //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求 break; 
////						case RETURN_MSG_TYPE_SHARE: AppData.showToast("微信分享成功"); finish(); break; }
////					break;
////					}
////			} 
////		}
////			}
////		}
////	}
//	
//    
//    private IWXAPI api;
//    private String WEIXIN_APP_ID = "wx49699f2e27560127";
//    @Override  
//    protected void onCreate(Bundle savedInstanceState) {  
//        // TODO Auto-generated method stub  
//        super.onCreate(savedInstanceState);  
//        setContentView(R.layout.packagedetailactivity);  
//        
//
////        <span style="color:#3366ff;">api = WXAPIFactory.createWXAPI(this, Property.wxLoginInfo.getAppid(), false);  
////        api.handleIntent(getIntent(), this);</span>  
//        	init();
//    }  
//      
//    @Override  
//    public void onReq(BaseReq arg0) {  
//        // TODO Auto-generated method stub  
//    	 Log.e("", "= = = = =44444   ");
//          
//    }  
//  
//    
//    @Override  
//    public void onResp(BaseResp resp) {  
//    	 Log.e("", "= = = = =44444   ");
//        Bundle bundle = new Bundle();  
//        switch (resp.errCode) {  
//        
//        case BaseResp.ErrCode.ERR_USER_CANCEL: 
//        	Log.e("", "123456789 ");
//            break;  
//        case BaseResp.ErrCode.ERR_OK:  
////      可用以下两种方法获得code  
////      resp.toBundle(bundle);  
////      Resp sp = new Resp(bundle);  
////      String code = sp.code;<span style="white-space:pre">  
////      或者  
//        String code = ((SendAuth.Resp) resp).code;  
//        Log.e("", "= = = = = "+code);
//            //上面的code就是接入指南里要拿到的code  
//              
//            break;  
//  
//        default:  
//            break;  
//        }  
//          
//    }
//   private void init(){
//	   Log.e("", "= = = = =111111   ");
//       if (api == null) {
//       	api = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, false);
//       }
//       
////		final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
//
//		// 将该app注册到微信
//		api.registerApp(WEIXIN_APP_ID);
//
////       if (!api.isWXAppInstalled()) {
////    	   Log.e("", "= = = = =222222  ");
////           //提醒用户没有按照微信
////           return;
////       }
//	    SendAuth.Req req = new SendAuth.Req();  
//	   req.scope = "snsapi_userinfo";  
//	   req.state = "wechat_sdk_demo_test";  
//	   api.sendReq(req);  
//	   
////		final SendAuth.Req req = new SendAuth.Req(); 
////		req.scope = "snsapi_userinfo";
////		req.state = "diandi_wx_login";
//   }
	
//}