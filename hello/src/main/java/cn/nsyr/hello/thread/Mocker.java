package cn.nsyr.hello.thread;

import java.sql.SQLException;

/**
 * @author ZhouSs
 * @Mail: zhoushengshuai@ufenqi.com
 * @date:2017/3/15 上午9:39
 * @version: 1.0
 **/
public class Mocker<T extends Exception> {

    private void pleaseThrow(final Exception t) throws T {
        throw (T)t;
    }

    public static void main(String[] args) {
//        try {
            new Mocker<RuntimeException>().pleaseThrow(new SQLException());
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
    }
}