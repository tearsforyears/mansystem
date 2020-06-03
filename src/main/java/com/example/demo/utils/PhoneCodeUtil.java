package com.example.demo.utils;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;


@Component
public class PhoneCodeUtil {

    private static String ACCESSKEYID;
    private static String ACCESSSECRET;
    private static String TEMPLATENAME;

    public static void setConfig(String ak, String as, String temp) {
        ACCESSKEYID = ak;
        ACCESSSECRET = as;
        TEMPLATENAME = temp;
    }

    private static String genRandomString(int len) {
        StringBuilder randString = new StringBuilder();
        for (int i = 0; i < len; i++) {
            randString.append(Integer.toString((int) (Math.random() * 10)));
        }
        return randString.toString();
    }

    private static String genRandomString() {
        return genRandomString(4);
    }

    public static String sendMsg(String phone) {
        String code = genRandomString();
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESSKEYID, ACCESSSECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "Gauss");
        request.putQueryParameter("TemplateCode", TEMPLATENAME);
        request.putQueryParameter("TemplateParam", "{'code':'" + code + "'}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return code;
    }

}
