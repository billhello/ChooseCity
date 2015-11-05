package bill.choosecity.listener;

/**
 * Created by daxiongzaici on 10/29/15.
 * 选择某个城市
 */
public interface OnSelectLocationListener {
    /**
     * 选择某个城市
     */
    void onSelectCity(String cityName, String cityID);
}
