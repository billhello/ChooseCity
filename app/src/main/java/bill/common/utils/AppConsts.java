package bill.common.utils;

import android.os.Build;

/**
 * 应用程序的静态变量
 *
 * @author
 */
public class AppConsts {

    /**
     * 调试状态
     */
    public final static int LOG_DEGUB = 0;

    /**
     * 调试基本信息
     */
    public final static int LOG_INFO = 1;

    /**
     * 调试出错
     */
    public final static int LOG_ERROR = 2;

    public final static int LOG_NOTHING = 3;

    /**
     * 项目目前的状态
     */
    public static int LEVEL = LOG_NOTHING;
    /**
     * 应用程序的版本
     */
    public static int APPVERSION = Build.VERSION.SDK_INT;

    /**
     * 用户的种类商户
     */
    public static int WEDDING_USER_COMMERCIAL = 0;
    /**
     * 用户的种类消费者
     */
    public static int WEDDING_USER_CONSUMER = 1;
    /**
     * 用户的种类其他
     */
    public static int WEDDING_USER_OTHER = 2;
    /**
     * 下拉框显示的数目
     */
    public static int DROPDOWN_NUMBER = 6;
    /**
     * 是不是大屏幕手机
     */
    public static  boolean IS_IMAGE_M = false;
}
