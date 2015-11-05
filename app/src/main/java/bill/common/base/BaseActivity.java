package bill.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;

import com.halobear.awedqq.home.ui.common.bean.ViewBean;
import com.halobear.wedqq.R;
import com.halobear.wedqq.common.MyImageLoader;
import com.halobear.wedqq.common.tools.ToastUtil;
import com.halobear.wedqq.special.material.MyMaterialDialog;
import com.halobear.wedqq.special.ui.easemob.applib.controller.HXSDKHelper;
import com.halobear.wedqq.special.ui.user.util.LoginConsts;
import com.halobear.wedqq.special.ui.user.util.LoginUtil;
import com.halobear.wedqq.tool.http.MyHttpRequestFinishInterface;
import com.halobear.wedqq.tool.http.MyHttpRequestManager;
import com.halobear.wedqq.tool.http.NetWorkBroadcastInterface;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tendcloud.tenddata.TCAgent;


/**
 * Activity的公用类
 *
 * @author hejian
 */

public abstract class BaseActivity extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 如果ImageLoader为空，则初始化
        if (MyImageLoader.imageLoader == null)
            MyImageLoader.imageLoader = ImageLoader.getInstance();
        setView(savedInstanceState);
        initView();
        initData();
    }

    /**
     * 当加载结束可以点击
     */
    protected void canClick() {

    }

    /**
     * 设置布局文件
     */
    public abstract void setView(Bundle savedInstanceState);

    /**
     * 初始化组件
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * activity界面外调用启动(不含参数)
     *
     * @param className 要启动的界面
     */
    public void startActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

    /**
     * activity界面外调用启动(不含参数)
     *
     * @param context
     * @param className 要启动的界面
     */
    public void startActivity(Context context, Class<?> className) {
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
    }

    /***
     * activity界面外调用启动
     *
     * @param context
     * @param className   要启动的界面
     * @param requestCode 请求码
     */
    public void startActivityForResult(Context context, Class<?> className, int requestCode) {
        Intent intent = new Intent(context, className);
        ((Activity) context).startActivityForResult(intent, requestCode);
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
}
