package bill.common.utils;

/**
 * 自定义log
 * 
 * @author Administrator
 * 
 */
public class MyLog {

	/**
	 * Tag
	 */
	public final static String TAG = "weddingee";
	/**
	 * LogUtil工具
	 */
	private static MyLog sLogUtil;

	private MyLog() {
	}

	/**
	 * 获得调试单例
	 * 
	 * @return
	 */
	public static MyLog getInstance() {
		if (sLogUtil == null) {
			synchronized (MyLog.class) {
				if (sLogUtil == null) {
					sLogUtil = new MyLog();
				}
			}
		}
		return sLogUtil;
	}

	/**
	 * 打印debug
	 * 
	 * @param pre
	 *            输出前缀
	 * @param object
	 *            输出地方的类的实例，用于打印输出语句效用
	 * @param msg
	 *            输出的值
	 */
	public static void d(Object object, String msg) {
		if (AppConsts.LOG_DEGUB >= AppConsts.LEVEL) {
			android.util.Log.d(TAG, object.getClass().getSimpleName() + "--"
					+ msg);
		}
	}

	/**
	 * 打印info
	 * 
	 * @param pre
	 *            输出前缀
	 * @param object
	 *            输出地方的类的实例，用于打印输出语句效用
	 * @param msg
	 *            输出的值
	 */
	public static void i(Object object, String msg) {
		if (AppConsts.LOG_INFO >= AppConsts.LEVEL) {
			android.util.Log.i(TAG, object.getClass().getSimpleName() + "--"
					+ msg);
		}
	}

	/**
	 * 打印error
	 * 
	 * @param pre
	 *            输出前缀
	 * @param object
	 *            输出地方的类的实例，用于打印输出语句效用
	 * @param msg
	 *            输出的值
	 */
	public static void e(Object object, String msg) {
		if (AppConsts.LOG_ERROR >= AppConsts.LEVEL) {
			android.util.Log.e(TAG, object.getClass().getSimpleName() + "--"
					+ msg);
		}
	}
}
