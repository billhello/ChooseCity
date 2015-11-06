package bill.common.utils;

/**
 * 程序运行时的静态变量
 *
 * @author Administrator
 */
public class ConfigData {

    // 婚礼场地城市列表
    public final static int WEDDING_WEDDINGHOTEL_TYPE = 1;

    // 结婚服务列表
    public final static int WEDDING_WEDDINGCOMPANY_TYPE = 2;

    // 婚礼执行列表
    public final static int WEDDING_WEDDINGTEAM_TYPE = 3;

    // 灵感素材列表
    public final static int WEDDING_WEDDINGFLOWER_TYPE = 4;

    public final static int WEDDING_WEDDINGPLA_TYPE = 5;
/*    // 精选婚礼
    public static int WEDDING_WEDDINGCASE_TYPE = 5;

    // 精选视频
    public static int WEDDING_VIDEOCASE_TYPE = 6;*/


    public final static String WEDDING_WEDDINGSER = "ser";
    public final static String WEDDING_WEDDINGINS = "ins";
    public final static String WEDDING_WEDDINGPLA = "pla";

    /**
     * rootUrl
     */
    // public static String rootUrl = "http://218.244.137.89/wedqq/";
    public static String rootUrl = "";

    /**
     * 请求url
     */
    public static String url = (AppConsts.LEVEL == AppConsts.LOG_DEGUB) ? "http://114.215.149.229/wedqq/mapi/index.php"
            : "http://data.halobear.cn/mapi/index.php";

    /**
     * 圈子的url
     */
    public static String groupUrl = (AppConsts.LEVEL == AppConsts.LOG_DEGUB) ? "http://114.215.149.229/wedbbs/api/mobile/index.php"
            : "http://circle.halobear.cn/api/mobile/index.php"; // 正式地址

    /**
     * 登录url
     */
    public static String loginUrl = groupUrl
            + "?loginfield=auto&charset=utf-8&version=3&loginsubmit=yes&mobile=yes&module=login";
    public static String passwordUrl = groupUrl
            + "?module=sms&version=4";
    /**
     * 注册
     */
    public static String registerUrl = groupUrl
            + "?module=register&mod=register&version=4";
    /*1.0.0*/
    public static String groupImgUrl = "http://circle.halobear.cn/data/attachment/wed/group/g";
    /*2.0.0*/
    public static String groupImgUrl2 = "http://circle.halobear.cn/data/attachment/wed/group2/g";
    /**
     * 视频url
     */
    public static String videoUrl = "http://data.halobear.cn/api/youku/mp4.php?vid=";

    public static boolean flag = false;

    /**
     * 保存确认身份信息图片
     */
    public static final String SAVE_SURE_ID = "auth_img.png";

    public static class Config {
        public static final boolean DEVELOPER_MODE = false;
    }

    // 酒店
    public static String ShareHotelUrl = "http://www.halobear.cn/mp/hotel/";
    // 公司
    public static String ShareCompanyUrl = "http://www.halobear.cn/mp/company/";
    // 团队
    public static String ShareTeamUrl = "http://www.halobear.cn/mp/team/";
    //案例
    public static String ShareCaseUrl = "http://www.halobear.cn/mp/product/";
    // 图片
    public static String SharePictureUrl = "http://m.halobear.cn/picture.html?url=";


    //精选婚礼
    public static String ShareChoiceWeddingUrl = "http://www.halobear.cn/mp/hunli/";

    // 精选图片
    public static String ShareChoicePictureUrl = "http://www.halobear.cn/mp/photo/";
    // 精选视频
    public static String ShareChoiceVideoUrl = "http://www.halobear.cn/mp/video/";
    //专题
    public static String ShareChoiceZhuantiUrl = "http://www.halobear.cn/mp/zhuanti/";
    // 优惠活动
    public static String SharePreferentialUrl = "http://www.halobear.cn/mp/youhui/";

    // 线下活动
    public static String ShareOfflineUrl = "http://www.halobear.cn/mp/xianxia/";
}
