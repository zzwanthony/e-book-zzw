package com.zzw.util;

import com.tz.IOPdemo.sysmanage.utils.GetToken;
import com.tz.IOPdemo.sysmanage.utils.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FaceUtil {
    /**
     *
     */
    private static final long serialVersionUID = -3305882716311600114L;
    private static final String API_KEY = "rpETmgyQ70ahjfIlWxjBYPtq";
    private static final String SECRET_KEY = "T4uprWNx8tK2EmbpwLi1TLynG7nHzTAo";
    private static final String MATCH_URL = "https://aip.baidubce.com/rest/2.0/face/v2/match";
    private static String accessToken;
    static{
        accessToken = GetToken.getToken(API_KEY, SECRET_KEY);
    }
    public static Double faceContrast(String faceBase,String dbBase) throws UnsupportedEncodingException {
        String params = URLEncoder.encode("images", "UTF-8")+"="+URLEncoder.encode(faceBase+","+dbBase, "UTF-8");
        //面部对比
        try {
            String result = HttpUtil.post(MATCH_URL, accessToken, params);
            System.out.println(result);
            JSONObject json=new JSONObject(result);
            JSONArray array = json.getJSONArray("result");
            if(array.length()<1){
                return 0.0;
            }else{
                Double score=json.getJSONArray("result").getJSONObject(0).getDouble("score");
                System.out.println(score);
                return score;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1.0;
        }
    }
}
