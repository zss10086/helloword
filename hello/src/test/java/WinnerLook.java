import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.security.MessageDigest;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import javax.crypto.SecretKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.Cipher;

/**
 * @author ZhouSs
 * @Mail: zhoushengshuai@ufenqi.com
 * @date:2017/9/28 下午3:30
 * @version: 1.0
 **/
public class WinnerLook {

    private static final String userCode = "HCWLYX";
    private static final String userPass = "HCWLyx0826";

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());

        getReport();

    }

    public static void sendMsg() {
        String url = "http://112.124.24.5/api/MsgSend.asmx/sendMsgByEncrypt";
        String sign = "【优分期】";
        String msg = "嗨,大家下午好,该吃饭了,请抓紧时间订餐!";

        String desNo = "13520737357";
        String channel = "52";
        String encryptStr = DESEncrypt("userPass="+ userPass+"&DesNo="+desNo+"&Msg="+msg+sign+"&Channel="+channel,userPass);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("userCode", userCode));
        nvps.add(new BasicNameValuePair("submitInfo", encryptStr));


        String post = httpPost(url, nvps);  //post请求
        System.out.println("发送结果:"+post);
        //        String getparam = "userCode=用户名&submitInfo=" + encryptStr;
//        String result = httpGet(url, getparam); //get请求
//        System.out.println("发送结果:"+result);
    }

    public static void getReport() {
        String url="http://112.124.24.5/api/MsgSend.asmx/GetReport2ByEncrypt";

        String encryptStr=DESEncrypt("userPass=" + userPass,userPass);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("userCode", userCode));
        nvps.add(new BasicNameValuePair("submitInfo",encryptStr));
        String post = httpPost(url,nvps);  //post请求
        System.out.println("报告:" + post);
        //        String getparam="userCode=用户名&submitInfo="+encryptStr;
//        String result=httpGet(url,getparam); //get请求

    }

    public static String DESEncrypt(String encryptStr, String key) {
        String result = "";
        try {
            System.out.println("dddd :"+SHA1(key));
            System.out.println("dddd :"+SHA1(key));
            String encryptKey = SHA1(key).substring(0, 8).toUpperCase();

            DESKeySpec desKeySpec = new DESKeySpec(encryptKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec ivp = new IvParameterSpec(encryptKey.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, securekey, ivp);
            byte[] encryptResult = cipher.doFinal(encryptStr.getBytes("GB2312"));
            result = BinaryToHexString(encryptResult);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static String SHA1(String key) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(key.getBytes("GB2312"));
            byte[] resultsha = md.digest();
            for (byte b : resultsha) {
                int i = b & 0xff;
                if (i < 0xf) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(i));
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }

    public static String BinaryToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (int i = 0; i < bytes.length; i++) {
            //字节高4位
            hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
            //字节低4位
            hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
            result += hex;
        }
        return result;
    }

    public static String httpPost(String url, List<NameValuePair> params) {
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instreams = entity.getContent();
                result = convertStreamToString(instreams);
                System.out.println(result);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static String httpGet(String url, String params) {
        String result = "";
        try {
            HttpClient client = new DefaultHttpClient();
            if (params != "") {
                url = url + "?" + params;
            }
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instreams = entity.getContent();
                result = convertStreamToString(instreams);
                System.out.println(result);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
