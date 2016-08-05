package com.td.qianhai.epay.bb.beans;

import java.io.Serializable;

public class HttpUrls implements Serializable {
	// public static final String HOST = "http://180.166.124.95:8092/posm/"; //
	// 生产环境
	public static final String APPURL = "https://download.qhno1.com/downBbbao.html";
	public static final String APPNAME = "贝贝宝";
//	public static final String APPURL = "https://download.qhno1.com/down.html";
//	public static final String APPNAME = "钱海钱包";
	
//	public static final String APPURL= "http://180.166.124.95:8092/posm/upload/QH_W_V1.6.apk";
	
//	public static final String APPNUM  = "android_qh_1.0";//测试
	public static final String APPNUM = "android_bbbaopay_1.0.0";//生产
//	public static final String APPNUM = "android_wndz_1.0";//为你定制

	
//	测试
//	public static final String HOST = "http://119.147.71.130:33148/mpay/";
//	public static final String HOST_POSP = "http://119.147.71.130:33148/mpay/";
//	public static final String HOST_PAY = "http://119.147.71.130:33148/mpay/";
//	public static final String HOST_POSM = "http://119.147.71.130:33148/posm/";
//	
//	public static final String SUFFIX = ".tran7";
//	public static final String SUFFIX_POSP = ".tran";
//	public static final String SUFFIX_MIDATC = ".tran7";
	
	//测试
//	public static final String DISTRIBUTOR = "http://120.25.57.194:8080/pmagt/oemRole/";
//	public static final String MEMBERUPGRADE = "http://120.25.57.194:8080/pmagt/oemMember/";
//	public static final String MEMBERUPGRADESUFFIX = "?sign=";
//	public static final String MYPROFIT = "http://120.25.57.194:8080/pmagt/oemRole/incomeList?sign=";
//	public static final String UNDERLINGURL = "http://120.25.57.194:8080/mer/findMerByAgentId?"; //所属下级代理
//	public static final String RECOMMENDATION = "http://120.25.57.194:8080/mer/recommandDetail?";
//	public static final String PROXYRETURN = "http://120.25.57.194:8080/mer/shareDetail?";
//	public static final String MALL = "http://120.25.57.194:13020/app/login?sign=";//商城
//	public static final String TOAVERTISING = "http://120.25.57.194:8080/agtMessage/manageIndex?sign=";//广告发布
//	public static final String WECHATLIST = "http://120.25.57.194:8080/ercode/getErcodeOrderList?";
//	public static final String TOBEANAGENT = "http://120.25.57.194:8083/member/memberManageView?sign=";//我要代理
//	public static final String GROSSINCOME = "http://120.25.57.194:8083/member/userIncomeIndex?sign=";
//	public static final String COMMONPROBLEM = "http://120.25.57.194:8083/usual/faqInf";//常见问题
//	public static final String SYNCFRIEND = "http://120.25.57.194:8083/friend/syncFriend";//同步联系人
//	public static final String FINDFRIENDLIST = "http://120.25.57.194:8083/friend/findFriendList";//好友列表
//	public static final String GENERATESHORTURL = "http://120.25.57.194:8083/common/generateShortUrl";//生成短地址
	
	
	//新生产
	public static final String HOST_POSM = "http://bbbao.qhno1.com/";
	public static final String HOST = "http://ywmpay.qhno1.com/";
	public static final String HOST_POSP = "http://ywmpay.qhno1.com/";
	public static final String HOST_PAY = "http://ywmpay.qhno1.com/";
	public static final String SUFFIX = ".tran7";
	public static final String SUFFIX_POSP = ".tran";
	public static final String SUFFIX_MIDATC = ".tran7";
//	
//	//生产
	public static final String DISTRIBUTOR = "http://ywjavapay.qhno1.com/pmagt/oemRole/";
	public static final String MEMBERUPGRADE = "http://ywjavapay.qhno1.com/pmagt/oemMember/";
	public static final String MEMBERUPGRADESUFFIX = "?sign=";
	public static final String MYPROFIT = "http://ywjavapay.qhno1.com/pmagt/oemRole/incomeList?sign=";
	public static final String UNDERLINGURL = "http://ywjavapay.qhno1.com/mer/findMerByAgentId?"; //所属下级代理
	public static final String MALL = "http://beibeibao.qvs007.com/app/login?sign=";//商城
	public static final String PROXYRETURN = "http://ywjavapay.qhno1.com/mer/shareDetail?";
	public static final String RECOMMENDATION = "http://ywjavapay.qhno1.com/mer/recommandDetail?";
	public static final String TOAVERTISING = "http://ywjavapay.qhno1.com/agtMessage/manageIndex?sign=";//广告发布
	public static final String COMMONPROBLEM = "http://ywjavapay.qhno1.com/usual/faqInf";//常见问题
	public static final String CERTIFICATE = "http://ywjavapay.qhno1.com/html/bbbao/certificate.html";//资质证明
	public static final String HELP = "http://ywjavapay.qhno1.com/usual/operguiInf";//操作指南
//	http://ywjavapay.qhno1.com/html/bbbao/operationGuide.html
	
	public static final String CFINFO = "http://ywjavapay.qhno1.com/html/bbbao/wallet.html";//简介
	public static final String REGISTRATIONAGREEMENT = "http://ywjavapay.qhno1.com/html/bbbao/serviceAgreement.html";//注册协议
	public static final String CHARGERULE = "http://ywjavapay.qhno1.com/html/bbbao/tollRule.html";//收费规则
	public static final String SCREENINGURL = "http://www.qvs007.com/qrcode/genErcode";
	public static final String SCREENINGURLFA = "http://www.qvs007.com/qrcode/saveQrcodeShopName";
	public static final String HG_URL = "http://wap.qianhaihg.com/?/mobile/user/qhepaylogin/";
	public static final String WECHATLIST = "http://ywjavapay.qhno1.com/ercode/getErcodeOrderList?";
	public static final String SHEARURL = "http://ywjavapay.qhno1.com/common/dow?sign=";
	public static final String TOBEANAGENT = "http://ywjavapay.qhno1.com/member/memberManageView?sign=";//我要代理
	public static final String GROSSINCOME = "http://ywjavapay.qhno1.com/member/userIncomeIndex?sign=";
	public static final String GENERATESHORTURL = "http://ywjavapay.qhno1.com/common/generateShortUrl";//生成短地址
	public static final String SYNCFRIEND = "http://ywjavapay.qhno1.com/friend/syncFriend";//同步联系人
	public static final String FINDFRIENDLIST = "http://ywjavapay.qhno1.com/friend/findFriendList";//好友列表
	public static final String INTELLIGENTRECHARGE = "http://ywjavapay.qhno1.com/nocardrech/noCardRechIndex?sign=";//好友列表
	
	

	public static final String SUFFIX_UPLOAD = ".upload4m";

	public static final String SUFFIX_UPLOAD_J = ".upload4j";
	
	public static final String SUFFIX_MID = ".tran8";
	
	public static final String SUFFIX_CIR = ".tran6";

	// 终端号验证
	public static final int LOCALYZZD = 198107;

	public static final int ORDERPAYDETAIL = 199032;
	// 订单查询
	public static final int ORDERPAYQUERY = 199031;
	// 订单支付
	public static final int ORDERPAY = 199030;
	// 注册
	public static final int REGISTER = 199001;

	// 登录
	public static final int LOGIN = 199002;

	// (钱包)致富宝详细信息
	public static final int RICH_TREASURE_INFO = 701122;
	// 支付密码设置
	public static final int SET_PAY_PASS = 701120;
	// 支付密码修改
	public static final int UP_PAY_PASS = 701121;
	// 致富宝账单明细
	public static final int RICH_TREASURE_DETAIL = 701123;

	// 支取致富宝到银行卡
	public static final int WITHDRAWAL_ON_BANK_CARD = 701131;

	/**
	 * 存入定期
	 */
	public static final int BASIS_DEPOSIT = 701132;

	/**
	 * 存款模式列表
	 */
	public static final int BASIS_LIST = 701126;

	// 定期信息列表
	public static final int REGULAR_BASIS_LIST = 701124;

	/**
	 * 定转活
	 */
	public static final int CURRENT_TRANSFER = 701125;

	/**
	 * 找回支付密码验证码
	 */
	public static final int REG_PAY_PW_VCODE = 701493;
	/**
	 * 找回支付密码验证码验证
	 */
	public static final int REG_PAY_PW_VCODE_VD = 701494;

	/**
	 * 支付密码修改
	 */
	public static final int PAY_UPDATE = 701497;

	// 验证码验证
	public static final int VERIF_COMMIT = 198116;

	// 获取找回密码验证码
	public static final int REGET_PW_VERIF = 198115;

	// 修改密码交易码
	public static final int REGET_PASSWORD = 198117;

	// 修改密码
	public static final int REVISE_PASSWORD = 199003;

	// 刷卡查询卡银行名
	public static final int QUERY_BANK_NAME = 199104;

	// 银行卡开户城市
	public static final int QUERY_BANK_CITY = 199108;

	// 银行卡开户支行
	public static final int QUERY_BANK_BRANCH = 199109;

//	// 查看商户信息
	public static final int BUSSINESS_INFO = 199102;

	// 实名认证
	public static final int REAL_NAME_AUTHENTICATION = 199101;

	// 获取注册验证码
	public static final int REGISTER_VERIFCODE = 199018;

	// 修改开户银行信息
	public static final int REVISE_BANK_INFO = 199103;

	// 老板收款
	public static final int BOSS_RECEIVE = 199005;

	// 消费撤销
	public static final int REVOKE_PAY = 199006;

	// 银行卡余额查询
	public static final int BALANCE_QUERY = 199007;

	// 流水查询
	public static final int DEAL_RECORDS = 199008;

	// 主秘钥下载
	public static final int KEY_DOWNLOAD = 199105;

	// 上传电子签名
	public static final int UPLOAD_SIGN = 199107;

	// 签到
	public static final int CHECK = 199020;

//	// 版本检测
	public static final int VERSION_UPDATE = 199021;

	// 下支付订单
	public static final int ORADSEQ_PAY = 203015;

	// 更新支付订单流水状态
	public static final int ORADSEQ_UPDATE = 203016;
	// 银行卡信息查询
	public static final int QUERY_BANK_INFO = 203017;
	// 查询是否注册
	public static final int IS_REGISTER = 203025;
	// 申请银行卡信息修改
	public static final int APPLY_BANKCARD_INFO_CHANGES = 198101;
	// 申请修改商户实名认证
	public static final int APPLY_BANKINFO_REALNAME_CHANGES = 198102;
	// 商户申请手机号码修改
	public static final int APPLY_PHONENUMBER_CHANGES = 198109;
	// 申请修改商户手机实名认证
	public static final int APPLY_PHONENUMBER_REALNAME_CHANGES = 198110;
	// 商户申请手机号码修改获取新手机验证码
	public static final int QUERY_PHONENUMBER_CHANGES_VERIFCODE = 198220;

	// 创建订单
	public static final int ORDER_CREATE = 701613;

	// 订单查询单
	public static final int ORDER_QUERY = 701614;
	
	//已付订单查询
	public static final int ORDER_QUERY_OVER = 701622; 

	// 订单查询多
	public static final int ORDER_QUERY_D = 701615;///////////////////// . . . . . 

	// 订单支付
	public static final int ORDER_PAY = 701616;
	
	public static final int TANSFER_ACCO = 701129;
	
	public static final int RATE_QUERY = 701709; //费率列表
	
	public static final int GO_RATE = 701708; 
	
	public static final int RECHARGE = 701640;
	
	public static final int PHONE_CHARGE = 701639;
	
	public static final int ORDERINFO = 701162;
	
	public static final int MYCIRCLE = 701720;
	
	public static final int CREDITQUERY = 701647;
	
	public static final int CREDITPAY = 701646;
	
	public static final int GETBANKNAME = 701649;
	
	public static final int BINDINGCARD = 701648;
	
	public static final int DELETECREDITCARD = 701650;
	//分组推送
	public static final int GROUPING = 701722;
	//易宝
	public static final int YIBAO = 701723;
	
	public static final int EPAY = 701725;
	
	public static final int EPAYNUM = 701726;
	
	public static final int GETEPAYNUM = 701727;
	
	public static final int ACTIVATIONCODE = 000000;
	
	public static final int BANKCARDLIST = 701730;
	
	public static final int UPLODPIC = 701734;
	
	public static final int NEWAGENT = 701191;
//	
	public static final int ACTCODEMANAGE= 701194;
	
	public static final int CHIRDACT= 701192;
	
	public static final int VERIFICATIONPHONENUM = 701190;
	
	public static final int ACTCODEINIT = 701195;
	
	public static final int AGENTINFO = 701197;
	
	public static final int ACTCODELIST = 701198;
	
	public static final int ACTCODETRANSFER = 701196;
	
	public static final int RATEEDIT = 701193;
	
	public static final int FEEDBACK = 701973;
	
	public static final int INCOMEDETAIL = 701817;
	
	public static final int DEPOSITPURSE = 701816;
	
	public static final int PROMOTIONEARNINGS = 701686;
	
	public static final int RATEWITHDRAWALSEDIT = 701819;
	
	public static final int FINANCIALPRODUCTS = 702131;
	
	public static final int TOCHANGEINTO = 702128;
	
	public static final int TURNOUT = 702129;
	
	public static final int INTEGRALEXCHANGE = 702132;
	
	public static final int EXCHANGEINFO = 702133;
	
	public static final int TRANSACTIONTYPE = 702116;
	
	public static final int EPAYSAV = 701723;
	
	public static final int UNBINDCARD= 702149;
	
	public static final int UPDATEBANKCARD= 702148;
	
	public static final int BUYCODE = 702117;
	
	public static final int BINDAGENT= 702143;
	
	public static final int COMITADRESS = 701835;
	
	public static final int AVATARUPLOAD = 701997;
	
	public static final int CHOOSEBANK = 702005;
	
	public static final int BANNER = 701998;
	
	public static final int COLLECTIONTREASURE = 702007;
	
	public static final int REALNAMEAUTHENTICATION = 702008;
	
	public static final int INTHEQUERY = 703001;
	
	public static final int CODETRANSFER = 702016;
	
	public static final int APPLYCODE = 703010;
	
	public static final int RECOMMENDEDLIST = 703011;
	
	public static final int POINTLIST = 702044;
	
	public static final int BINDPHONENUM = 703005;
	
	
	
}