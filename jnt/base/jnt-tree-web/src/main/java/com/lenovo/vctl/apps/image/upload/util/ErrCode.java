package com.lenovo.vctl.apps.image.upload.util;

public final class ErrCode {



    public static final String ERROR_USER_NOT_EXIST = "ERROR_00001"; //用户不存在
    public static final String ERROR_USER_NOT_EXIST_INFO = "user not found";
    public static final String ERROR_USER_EXIST = "ERROR_00002"; //用户已存在
    public static final String ERROR_USER_EXIST_INFO = "user already exists";
    public static final String ERROR_NULL_RESULT = "ERROR_00003"; //网络错误
    public static final String ERROR_NULL_RESULT_INFO = "network error";
    public static final String ERROR_CHANGE_PWD_RESULT = "ERROR_00004"; //修改密码错误
    public static final String ERROR_CHANGE_PWD_INFO = "change password failed";
    public static final String ERROR_BAN_WORD_RESULT = "ERROR_00005"; //含有敏感词
    public static final String ERROR_BAN_WORD_INFO = "有敏感词"; //含有敏感词
    public static final String ERROR_INVALID_MOBILENO = "ERROR_00006"; //非运营商手机号段或非合法邮箱
    public static final String ERROR_MOBILENO_EXIST = "ERROR_00007"; //手机号码已存在，请更换其它号码绑定。
    public static final String ERROR_CHECKCODE_INVALID = "ERROR_00008"; //验证码不正确。
    public static final String ERROR_SETTING_NOT_EXIST = "ERROR_00009"; //用户个人设置不存在
    public static final String ERROR_UNBIND_MOBILENO = "ERROR_00010"; //请绑定手机号
    public static final String ERROR_CHECKCODE_SEND_FAILED = "ERROR_00011"; //短信验证码发送失败
    public static final String ERROR_PASSPORT_EXIST = "ERROR_00012"; //登陆账号已存在 // 联想账号未验证请先验证
    public static final String ERROR_MOBILENO_IS_BINDED = "ERROR_00013";//手机号已被其它账号绑定
    public static final String ERROR_INVALID_PARAM = "ERROR_00014";//参数不合法
    public static final String ERROR_INVALID_PARAM_INFO = "params error";//参数不合法
    public static final String ERROR_INVALID_PASSPORT = "ERROR_00015";//注册账号非合法手机号
    public static final String ERROR_INVALID_CAPTCHA = "ERROR_00016"; //无效的图片验证码
    public static final String ERROR_CACHE_REGISTER_FAILED = "ERROR_00017"; //缓存注册用户信息失败
    public static final String ERROR_REGISTER_TIMEOUT = "ERROR_00018";//注册账号超时,请重新注册
    public static final String ERROR_SEND_SMG_LIMIT = "ERROR_00019";//同一手机号24小时内超过获取验证码次数
    public static final String ERROR_INVALID_PASSWORD = "ERROR_00020";//密码错误
    public static final String ERROR_INVALID_PASSWORD_E2E = "USS-0101";//密码错误
    public static final String ERROR_INVALID_PASSWORD_E2E_LOCK = "USS-0151";//帐号多次登录失败，已被锁定，稍后再登录尝试
    public static final String ERROR_INVALID_WEAVER_TOKEN = "ERROR_00021";//无效的token
    public static final String ERROR_NEED_CAPTCHA = "ERROR_00022"; //登陆次数过多,请输入验证码
    public static final String ERROR_SEND_SMSG_TOO_FAST = "ERROR_00023";//发送短信验证码频率过快
    public static final String ERROR_BINDING_MOBILENO_INVALID = "ERROR_00024"; //手机账号需绑定对应的手机号
    public static final String ERROR_TWICE_PWD_ERROR = "ERROR_00025"; //两次输入密码不一致
    public static final String ERROR_UPDATE_PWD_ERROR = "ERROR_00026"; //修改密码失败
    public static final String ERROR_LOCK_ACCOUNT_ERROR = "ERROR_00027"; //帐号多次登录失败，已被锁定，稍后再登录尝试
    public static final String ERROR_BINDING_ACCOUNT_ERROR = "ERROR_00028";//账号已绑定
    public static final String ERROR_EMAIL_LENGTH_ERROR = "ERROR_00029";//姐啊输这么长的邮箱你不嫌累啊
    public static final String ERROR_INVALID_EMAIL_ERROR = "ERROR_00030";//不是合法邮箱
    public static final String ERROR_PWD_EMPTY_ERROR = "ERROR_00031";//密码不能为空
    public static final String ERROR_RANDOM_CODE_ERROR = "ERROR_00032";//随机数不正确
    public static final String ERROR_INVALID_BIRTHDAY = "ERROR_00033";//出生日期不能大于当前时间
    public static final String ERROR_INVALID_PHONE = "ERROR_00034";//手机号不合法
    public static final String ERROR_INVALID_DEVICESN = "ERROR_00035";//设备号不合法
    public static final String ERROR_PASSWORD_TOO_SHORT = "ERROR_00036";//密码长度不能少于6位
    public static final String ERROR_PASSWORD_TOO_LANG = "ERROR_00037";//密码长度不能大于20位
    public static final String ERROR_INVALID_HEAD_PICTURE = "ERROR_00038"; //非法头像地址





    public static final String ERROR_INVALID_APPID = "ERROR_00039"; // 未传appID

    public static final String ERROR_UNAUTHORIZED_CLIENT = "ERROR_00040"; // 未授权的客户端
    public static final String ERROR_INVALID_SINA_TOKEN = "ERROR_00041"; //无效的sina access token

    public static final String ERROR_PUBLISH_WEIBO_ERROR = "ERROR_00042";//发表微博失败

    public static final String ERROR_SET_SINA_TOKEN_ERROR = "ERROR_00043";//存储新浪token失败

    public static final String ERROR_CHECKCODE_BECOME_INVALID = "ERROR_00044"; //验证码已失效

    public static final String ERROR_REGIST_TOO_FAST = "ERROR_00045"; //请求过于频繁，请稍后再试

    public static final String ERROR_INVALID_APP_ID = "ERROR_00046";//该APP非法

    public static final String ERROR_INVALID_APP_AUTHORIZE = "ERROR_00047"; //无效的app授权

    //   public static final String ERROR_EXPIRED_TOKEN = "ERROR_00048"; //token已过期

    public static final String ERROR_ACCESS_DENIED = "ERROR_00049"; //访问未授权

    public static final String ERROR_SERVER_ERROR = "ERROR_10000"; //server 内部错误
    //public static final String ERROR_SERVER_STOP_ERROR = "ERROR_00995"; //停机维护
    public static final String ERROR_SERVER_STOP_ERROR = "ERROR_10001"; //停机维护
    public static final String ERROR_CLIENT_NEED_UPDATE_ERROR = "ERROR_10002"; //客户端版本需要更新

    public static final String ERROR_REGIST_FORBID =  "ERROR_00050"; //注册被禁止

    public static final String ERROR_INVALID_PARAM_MAX_LENGTH = "ERROR_00051";//参数不合法
    public static final String ERROR_INVALID_PARAM_INFO_MAX_LENGTH = "params error, over length limited";//参数不合法
    public static final String ERROR_ACCOUNT_FREEZE = "ERROR_00052"; //用户已冻结
    public static final String ERROR_CHANGE_PWD_TOKEN = "ERROR_00053"; //用户密码已修改，请重新登陆
    public static final String ERROR_ACCESS_UNBIND = "ERROR_00054"; //账号未绑定
    public static final String ERROR_PROTECT_IS_NULL = "ERROR_00055"; //密保不能为空
    public static final String ERROR_RANDOM_CODE_INVALID = "ERROR_00056"; //随机数不存在或已失效
    public static final String ERROR_PROTECT_UNSET = "ERROR_00057"; //密保未设置
    public static final String ERROR_INVALID_CHARACTOR = "ERROR_00058"; //含有非法字符
    public static final String ERROR_INVALID_CHARACTOR_INFO = "has invalid chars";

    //数字、英文字母、特殊字符组成的6-20位字符串，不包括汉字及全角字符
    public static final String ERROR_PWD_INCLUDE_INVALID_CHARACTOR = "ERROR_00059"; //密码由数字、英文字母、特殊字符组成的6-20位字符串，不包括汉字及全角字符
    public static final String ERROR_CONNECT_E2E_EXCEPTION = "ERROR_00060"; //调用e2e服务异常
    public static final String ERROR_INVALID_APP_ORIGIN = "ERROR_00061";// 暂时未被支持的app
    public static final String ERROR_CONNECT_THIRDAPP_EXCEPTION = "ERROR_00062";// 调用第三方服务异常
    public static final String ERROR_PASSPORT_IS_BINDED = "ERROR_00063";// 账号已绑定
    public static final String ERROR_SERIALNO_MISMATCHING = "ERROR_00064"; //序列号不匹配
    public static final String ERROR_SERIALNO_NOT_FIND = "ERROR_00065"; //序列号不存在
    public static final String ERROR_UP_SMS_UNRECEIVED = "ERROR_00066"; //用户上行短信尚未注册成功
    public static final String ERROR_SERIALNO_PARAM_INVALID = "ERROR_00067"; //序列号不合法

    public static final String ERROR_THIRD_ACCOUNT_NEED_ADD_PWD = "ERROR_00068"; //thirdapp绑定的手机号登陆友约需完善密码
    public static final String ERROR_CACHE_COMPLETE_PWD_FAILED = "ERROR_00069"; //缓存完善密码失败
    public static final String ERROR_CACHED_PWD_INVALID = "ERROR_00070"; //缓存完善密码已失效
    public static final String ERROR_SINGLE_IP_SEND_SMS_OUT_TIMES = "ERROR_00071"; //单个ip一小时内发送短信超过次数限制
    public static final String ERROR_NEED_VALID_E2E_PWD = "ERROR_00072"; //请输入正确的联想通行证密码
    public static final String ERROR_INVALID_SIMID = "ERROR_00073"; //simId不合法
    public static final String ERROR_SERIALNO_BECOME_INVALID = "ERROR_00074"; //序列号已失效

    public static final String ERROR_INVALID_PASSWORD_2 = "ERROR_00075";//528 版本新添加密码不正确错误码


    public static final String ERROR_INVALID_AGE = "ERROR_00076";//EXT对象中字段不合法
    public static final String ERROR_INVALID_CONSTELLATION = "ERROR_00077";//星座参数不合法
    public static final String ERROR_INVALID_MARITALSTATUS = "ERROR_00078";//星座参数不合法



    //oauth error

    public static final String OAUTH_invalid_request = "invalid_request";
    public static final String OAUTH_invalid_client = "invalid_client";
    public static final String OAUTH_invalid_grant = "invalid_grant";
    public static final String OAUTH_unauthorized_client = "unauthorized_client";
    public static final String OAUTH_unsupported_grant_type = "unsupported_grant_type";
    public static final String OAUTH_invalid_scope = "invalid_scope";



    public static final String SUCCESS_CHECKCODE_SEND_SUCCESS = "SUCCESS_00001"; //短信验证码发送成功


    public static final String SUCCESS_USER_REGISTER_INFO = "registration success";
    public static final String SUCCESS_USER_LOGOUT_INFO = "logout success";
    public static final String SUCCESS_MODIFY_PWD_INFO = "change password success";
    public static final String SUCCESS_UNLOADING_REASON_INFO = "unloading success";


    //pic or video error code
    public static final String NO_ERROR = "0";//无错误正常返回
    public static final String ERROR_UPLOAD_USER_TOKEN_ERROR = "ERROR_00200";//token异常
    public static final String ERROR_UPLOAD_LPS_ERROR = "ERROR_00201";//token异常
    public static final String ERROR_UPLOAD_PARAM_ERROR = "ERROR_00202";//参数异常
    public static final String ERROR_UPLOAD_UPDATE_PIC_ERROR = "ERROR_00203";//更新自己头像失败
    public static final String ERROR_UPLOAD_FTP_ERROR = "ERROR_00204";//ftp错误
    public static final String ERROR_UPLOAD_CODEC_ERROR = "ERROR_00205";//转码队列错误
    public static final String ERROR_UPLOAD_CREATE_RECORD_ERROR = "ERROR_00206";
    public static final String ERROR_UPLOAD_CREATE_ALBUM_ERROR = "ERROR_00207";//创建 相册失败
    public static final String ERROR_UPLOAD_ALBUM_NOEXIST_ERROR = "ERROR_00208";//创建 相册失败
    public static final String ERROR_UPLOAD_CREATE_PIC_ERROR = "ERROR_00209";//创建 图片失败
    public static final String ERROR_UPLOAD_PIC_NOEXIST_ERROR = "ERROR_00210";//图片不存在
    public static final String ERROR_UPLOAD_CREATE_ALBUM_AND_PIC_ERROR = "ERROR_00211";//创建 相册图片关联表失败
    public static final String ERROR_UPLOAD_ALBUM_AND_PIC_NOEXIST_ERROR = "ERROR_00212";//图片和相册关联关系不存在
    public static final String ERROR_UPLOAD_UNKNOWEN_ERROR = "ERROR_00299";//其他错误


    //public static final String ERROR_UPLOAD_AUDIO_ERROR = "ERROR_00207";//上传音频失败


    //邀请相关
    public static final String ERROR_FIRNED_INVITE = "ERROR_00300";
    public static final String ERROR_FIRNED_INVITE_INFO = "operation error"; //邀请异常，未知异常
    public static final String ERROR_FIRNED_INVITE_OVER_LIMIT_SHOTMSG = "ERROR_00302";
    public static final String ERROR_FIRNED_INVITE_OVER_LIMIT_SHOTMSG_INFO = "over limit shot message"; //邀请超过短信次数限制
    public static final String ERROR_FIRNED_INVITE_OVER_LIMIT_EMAIL = "ERROR_00303";
    public static final String ERROR_FIRNED_INVITE_OVER_LIMIT_EMAIL_INFO = "over limit email"; //邀请超过邮件次数限制
    public static final String ERROR_FIRNED_INVITE_IS_TIME_LIMITED = "ERROR_00304";
    public static final String ERROR_FIRNED_INVITE_IS_TIME_LIMITED_INFO = "is time limited"; //不能在一分钟内连续发邀请

    //联系人相关
    public static final String ERROR_RELATION_USERMOBILEEMPTY = "ERROR_00400";
    public static final String ERROR_RELATION_USERMOBILEEMPTY_INFO = "usermobile is empty"; //联系人手机号为空

    //添加联系人相关错误
    public static final String ERROR_RELATION_ID_BY_MAX = "ERROR_00401";//因为好友数超过限制导致添加失败时返回的好友ID;
    public static final String ERROR_RELATION_ID_BY_MAX_INFO = "user's relation extends max number";

    public static final String ERROR_RELATION_ID_BY_EXIST = "ERROR_00402"; //因为好友关系已经存在导致添加好友失败
    public static final String ERROR_RELATION_ID_BY_EXIST_INFO = "relationship exists";

    public static final String ERROR_RELATION_ADD_SELF = "ERROR_00403";  //不能加自己为好友
    public static final String ERROR_RELATION_ADD_SELF_INFO = "add userself not permitted";

    public static final String ERROR_RELATION_BY_SETTING = "ERROR_00404"; // 因为好友设置了隐私设置，所以不能加他为好友
    public static final String ERROR_RELATION_BY_SETTING_INFO = "user private setting";

    public static final String ERROR_RELATION_BY_DB = "ERROR_00405";   //数据库操作失败
    public static final String ERROR_RELATION_BY_DB_INFO = "db failed";

    public static final String ERROR_RELATION_CREATE_FAILED = "ERROR_00406";   //创建好友失败
    public static final String ERROR_RELATION_CREATE_FAILED_INFO = "create relation failed";  //

    public static final String ERROR_RELATION_INVALID_FRIENDMOBILE = "ERROR_00407";   //帐号不合法
    public static final String ERROR_RELATION_INVALID_FRIENDMOBILE_INFO = "invalid friend mobile";  //

    public static final String ERROR_RELATION_INVALID_ALIAS = "ERROR_00408";   //备注名不合法
    public static final String ERROR_RELATION_INVALID_ALIAS_INFO = "invalid alias";  //

    public static final String ERROR_RELATION_DEFAULT_USER = "ERROR_00409";   //不能对客服做操作
    public static final String ERROR_RELATION_DEFAULT_USER_INFO = "is default user";  //不能对客服做操作

    //查找，更新联系人
    public static final String ERROR_RELATION_NOT_EXIST = "ERROR_00100"; //联系人不存在
    public static final String ERROR_RELATION_NOT_EXIST_INFO = "friend doesn't exist";

    public static final String ERROR_CMMCONTACT_OVER_LIMIT = "ERROR_00501"; //超过常用联系人个数限制
    public static final String ERROR_CMMCONTACT_OVER_LIMIT_INFO = "over limit";

    public static final String ERROR_RECOMMEND_OVER_LIMIT = "ERROR_00502";
    public static final String ERROR_RECOMMEND_OVER_LIMIT_INFO = "over limit";

    public static final String ERROR_SMS_CONTENT_OVER_LIMIT = "ERROR_00503";//短信内容过长
    public static final String ERROR_SMS_CONTENT_OVER_LIMIT_INFO = "sms content over limit";

    public static final String ERROR_SMS_COUNT_OVER_LIMIT = "ERROR_00504";//短信条数超过限制
    public static final String ERROR_SMS_COUNT_OVER_LIMIT_INFO = "sended sms count over limit";
    public static final String ERROR_SCORE_EVENT_INVALID = "ERROR_00505";//无效的积分事件
    public static final String ERROR_SCORE_EVENT_INVALID_INFO = "invalid event";
    public static final String ERROR_RANDOMCALL_UNREGIST = "ERROR_00506";//随机呼叫未注册
    public static final String ERROR_RANDOMCALL_UNREGIST_INFO = "unregisted";

    //约会相关
    public static final String NO_ERROR_DATE = "ERROR_00000";//无错误正常返回
    public static final String NO_ERROR_DATE_INFO = "success";//无错误正常返回
    public static final String ERROR_DATE_ERROR = "ERROR_00601";
    //    public static final String ERROR_DATE_ERROR_USER_SERVICE_ERROR = "ERROR_00602";
    public static final String ERROR_DATE_ERROR_NO_DATE_TIME = "ERROR_00602";
    public static final String ERROR_DATE_ERROR_NO_DATE_LOCATION = "ERROR_00603";
    public static final String ERROR_DATE_ERROR_NO_DATE_PARTNER = "ERROR_00604";
    public static final String ERROR_DATE_ERROR_ONE_SESSION_CAN_CREATE_ONE = "ERROR_00605";
    public static final String ERROR_DATE_ERROR_NO_PRIVILEGE = "ERROR_00606";
    public static final String ERROR_DATE_ERROR_ERROR_PARAM_LENGTH = "ERROR_00607";
//    public static final String ERROR_DATE_ERROR_CREATE_RECORD = "ERROR_00606";//创建历史记录失败

    //Push相关
    public static final String ERROR_PUSH_CLIENT_EXPIRE_TOO_CLOSE = "ERROR_00701";
    public static final String ERROR_PUSH_CLIENT_EXPIRE_TOO_CLOSE_INFO = "expire time almost invalid";


    // 黑名单相关

    public static final String ERROR_ADD_BLACKLIST = "ERROR_00801";
    public static final String ERROR_ADD_BLACKLIST_INFO = "添加黑名单失败";

    public static final String ERROR_REMOVE_BLACKLIST = "ERROR_00802";
    public static final String ERROR_REMOVE_BLACKLIST_INFO = "删除黑名单失败";

    public static final String ERROR_ADD_SELF_BLACKLIST = "ERROR_00803";
    public static final String ERROR_ADD_SELF_BLACKLIST_INFO = "不能把自己添加进黑名单";


    // 动态相关
    public static final String ERROR_USERSHARE_NOT_EXIST = "ERROR_00901";
    public static final String ERROR_USERSHARE_NOT_EXIST_INFO = "not exist";

    public static final String ERROR_USERSHARE_CANNOT_EDIT = "ERROR_00902";
    public static final String ERROR_USERSHARE_CANNOT_EDIT_INFO = "not allow edit";

}
