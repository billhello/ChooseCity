package bill.choosecity.bean;

import java.io.Serializable;

/**
 * 地理位置信息
 */
public class LngLatBean implements Serializable {

    private static final long serialVersionUID = 1L;
    public String lng;
    public String lat;
    public String citycode;
    public String desc;
    public String province_name;
    public String province_id;
    public String city_name;
    public String city_id;
    public String district_name;
    public String district_id;
    public String adCode;
}
