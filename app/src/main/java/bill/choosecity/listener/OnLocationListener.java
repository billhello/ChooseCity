package bill.choosecity.listener;

/**
 * Created by daxiongzaici on 10/29/15.
 * 监听定位是否成功
 */
public interface OnLocationListener {
    /**
     * 定位成功
     */
    void onLocationSuccess();

    /**
     * 定位失败
     */
    void onLocationFailed();
}
