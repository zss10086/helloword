package cn.nsyr.hello.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author ZhouSs
 * @Mail: zhoushengshuai@ufenqi.com
 * @date:2017/9/28 下午5:04
 * @version: 1.0
 **/
@XmlRootElement(name="hotel")
public class Hotel {

    @XmlElementWrapper(name = "RoomTypeVOs")
    @XmlElement(name = "RoomTypeVO")
    public List<RoomTypeVO> getRoomTypeVOs() {
        return RoomTypeVOs;
    }
    public void setRoomTypeVOs(List<RoomTypeVO> roomTypeVOs) {
        RoomTypeVOs = roomTypeVOs;
    }
    private List<RoomTypeVO> RoomTypeVOs;

    /////@XmlElement(name = "id")
    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @XmlElement(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private int id;
    private String name;
}
