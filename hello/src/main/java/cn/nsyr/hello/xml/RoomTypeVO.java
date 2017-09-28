package cn.nsyr.hello.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author ZhouSs
 * @Mail: zhoushengshuai@ufenqi.com
 * @date:2017/9/28 下午5:05
 * @version: 1.0
 **/
public class RoomTypeVO {
    ///@XmlElement(name = "typeid")
    @XmlAttribute(name = "typeid")
    public int getTypeid() {
        return typeid;
    }
    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }
    @XmlElement(name = "typename")
    public String getTypename() {
        return typename;
    }
    public void setTypename(String typename) {
        this.typename = typename;
    }
    @XmlElement(name = "price")
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    private int typeid;
    private String typename;
    private String price;
}
