package bill.common.base;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import bill.common.utils.AppConsts;
import bill.common.utils.ConfigData;
import bill.common.utils.MyImageLoader;

/**
 * application基类
 *
 * @author Administrator
 */
public class BaseApplication extends MultiDexApplication {

    public static Context applicationContext;
    private static BaseApplication instance;
    // login user name
    public final String PREF_USERNAME = "username";

 /*   public static int sEmojiNormal;
    public static int sEmojiMonkey;*/

    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";
    private RefWatcher mRefWatcher;


    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (ConfigData.Config.DEVELOPER_MODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                StrictMode
                        .setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                                .detectAll().penaltyDialog().build());
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectAll().penaltyDeath().build());
            }
        }

        // 如果ImageLoader为空，则初始化
        if (MyImageLoader.imageLoader == null)
            MyImageLoader.imageLoader = ImageLoader.getInstance();

        initImageLoader(getApplicationContext());

        Thread.getDefaultUncaughtExceptionHandler();

        applicationContext = this;
        instance = this;


        //内存泄露监测
        mRefWatcher = LeakCanary.install(this);


     /*   sEmojiNormal = getResources().getDimensionPixelSize(R.dimen.emoji_normal);
        sEmojiMonkey = getResources().getDimensionPixelSize(R.dimen.emoji_monkey);*/
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            Runtime.getRuntime().gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化图片加载配置
     *
     * @param context 当前上下文
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config;
        if (AppConsts.LEVEL == AppConsts.LOG_DEGUB) {
            config = new ImageLoaderConfiguration.Builder(context)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .writeDebugLogs() // 发布app后将 此处移除
                    .build();
        } else {
            config = new ImageLoaderConfiguration.Builder(context)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        }
        MyImageLoader.imageLoader.init(config);
    }


    public static BaseApplication getInstance() {
        return instance;
    }


}
