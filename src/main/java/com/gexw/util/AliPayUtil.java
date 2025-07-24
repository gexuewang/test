package com.gexw.util;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.gexw.entity.AliPayBean;


import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 功能描述：支付工具类
 * 作者：zz
 * 时间：2022/5/8 12:24
 */
public class AliPayUtil {
    //appID
    private static final String APP_ID = "9021000150624275";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";
    //私钥
    private static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCc9L+zLOTBo5xqVGRuPaC7nrDE/lO5DOUW7wb0gCEfuT7HOaTV47CYcLfIY5AP50NRxfE4R4K2XO1wLy1vmDHfGll112Sa6rDJX7f8mHrhKVMvtnM9grAUNMaYedGWMxiY+G/1zjhGFOSZcEJVl+fnE6YPEOGMulVTUXvrtjhXASMBkAtuYS+mg30idVQ8GPGgXSLebeTP09uTtPEM+2TjGTHj43HK1Vtz5td9x6okdKT2J8axNLLFVTucLSn8SmAPsLrzARP2chDoHmYtm0e+okfoyuQs828D60crZl/P2un6BtN3FXdY1sM/3Bs1aiPi03okPGv4TvCEdNYG9R0PAgMBAAECggEAEPaWvVutwYvYPNmwHQYGGCD3g+Lk04wkOk1NGCG88rvHoCqHuSIplOAhzMON71oUOYNGJcn8w47ZjpVauMPfJ/imEIld8yxIPQteTCuCpFEeD3sNi2lTdGubxyDrWOtezjGTkCJgjJp3dmnekXegdaX2/YvahKYi0KVu2jPQdP6cH8e4d1v7uTHSfzK/wKbgF8L9/iotSreTofPoeEcr4M0bWdtnD8by0RSAlspDDG7mQHVADnqZFChX7dCczu68aEVdhQM6ZTPehbNXHxOJnjckgJ9Rm39FOlC6cgPw5I7JR3d37EfJWP3Ad7PpNmJ1XdiBwlascxbGGgkiYGJo2QKBgQDLJwtyq6lNhCxcF3YzQ9pgW4r66IjJBv+jdJPWi9168igMcjkCGHttrCuWu5xttqzbhDGuTAizGG7xFzkUikK4DX9SSCcKuCinlTqUXRIu532iTwSWDIGvnBWR6nJU/3U9h7tYvgj5NgV8TUujCPGvCCiPDO8EAUBuhAqSYmSLxQKBgQDFyT9NSplpcldI6GYjRLM1siZp/OaJqaR2O2jeNhjiY2gyBeWIaXNjWhk8hOl/fCGKgesW2dFCjG2SHNenJ2AMqo4DtOiWQdr3/spYZUaa7XOzmLJh1M08ONCDGL+aLMD59rtBVkfjj5qcznrlK4GukDfICbuviVre7o1O1BBuwwKBgHFF/g4cRTRhpFJdCp0fEl5z7JRmoA5nBpdfYydXqVDqQQZfkgXnHLtUKqHLTjemic+EcCcdYiRqVUG8jykyxmzVqc55SLujX5fBj342PvNERDsCEC6SHqWKRESuEr3uCrMaQY4DuAEs6/YZKzlmvUu5ItRQg90ZzZFRsotYrXN5AoGBALA88/AOQgcIwKgRlTll9P4qwTn+BPqzs/06UOZ4c9H+XBD+2hmkRRJJbWVU1a8oT8bYnz9cVa+j69qfYrXqSxbePu8km3+QLOqAGZOMos5YOZnVvVWZRkucIIhAXDKoIEhbx12f4SzvgAiroCxmiQagMWRI7/mpPdSZ4kZUhlP7AoGAfiSAaaAnh2gbBwWuhClL9MS+WVNVZJ/QukiWEfMS4igUtxYBS7tdCGgGvcrliOGz8jdnrlC+/frbfbaMI9KLggYP9DpJMb9FiIn9TD4rpyYYehRcv2098p1+uNuDJZ5DZQJAZdelTHJALI6mh6BwJozfLQsEks5mTNo1wthu81I=";
    private static final String CHARSET = "UTF-8";
    //公钥
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1EoXdk430dPHGwoWhdQ0FniSiFB0eLZhW+TWi6Ydpgm2U1e12uTodXcbXwifR+U41J/jWAMWWmP47nS4RU5Kg9LVU6I8rAjszq8KR26SvykJydZqhEAFF+o8LX03bNFUjJhQgVEzS8ta3KCHUEupYzyHMCM49BK1ckW34ofqeF0ytZK14xHAJy5hDsokwcqpBtABi2/jlPxQ0/QIqnxJloDhAmthL/tA9jykaUg6vOWeUBpJFJVjMI2x8161nLZkiRDWHsKD3VzdIIj5JJkOfJ5riNMqmlgFewucV3SZ6DE44g19mOSKG/UV+pDjLIVaLtogtOvSCRUFbR3oJ2CiTwIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    //https://openapi-sandbox.dl.alipaydev.com/gateway.do
    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";

    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private static final String NOTIFY_URL = "http://127.0.0.1/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private static final String RETURN_URL = "http://localhost:8080/alipay/payFinish.action";
    //买家支付宝账号：epqirk8896@sandbox.com 登录支付密码：111111
    //furthu0314@sandbox.com

    /**
     * 方法描述：传参数调用支付接口方法
     * 方法参数：
     * 返回值类型：
     * 作者：zz
     * 时间：2022/5/8 15:11
     */
    public static String payOrder(AliPayBean aliPayBean) {
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);
        //提交到支付宝的参数
        request.setBizContent(JSON.toJSONString(aliPayBean));
        System.out.println("JSON.toJSONString(aliPayBean) = " + JSON.toJSONString(aliPayBean));
        System.out.println("请求支付宝付款参数：" + JSON.toJSONString(request));
        try {
            String result = alipayClient.pageExecute(request).getBody();
            System.out.println("请求支付宝返回参数：" + result);
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 方法描述：获取支付宝返回的结果
     * 方法参数：
     * 返回值类型：
     * 作者：zz
     * 时间：2022/5/8 15:12
     */
    public static Map<String, String> getPayFinishOrderResult(HttpServletRequest request){
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            try {
                valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }
        //查看参数都有哪些
        System.out.println(params);
        return params;
    }

    /**
     * 方法描述：验证签名
     * 方法参数：
     * 返回值类型：
     * 作者：zz
     * 时间：2022/5/8 15:12
     */
    public static boolean signVerified(Map<String, String> params){
        // 调用SDK验证签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return signVerified;
    }

    public static void main(String[] args) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi-sandbox.dl.alipaydev.com/gateway.do","2021000147652317","MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUmMhAzK73RrXqcbg6zHrjf0qzWk2VJIPBQQnKozlmpeGL0uwr6bYIDSlAUIuSyrvU7+6qftVB3nXoW7lm4x6m67XNOar5dF0Yx5QSBZwGDthJ+Qb3s1ptrnywgmtAw4GHyh5lBotizv487KxAa/RwqVg3NxWzKNLDZXhmelMyP322+AI2ITGR56Mhi4miWX1JQrZxwm97Z8N7QeNCw4JL69fy6ZCFtpvxbstCZRV+u7H/DQNaetIP4lAT/7ZvrsXgNn2udY2y74FyvlvvIDbs37h5iLiJR0FT9jSXcZ6mYgBqSpWh5w679PQnRGodNWrraWwTewNFSLI/tR9Lt0knAgMBAAECggEAalsMAW0+0de3fszUGqmeT1QdaE969gODlf9x/apj/DdAPq7BwRBu7EZXPyQnMfKPC/KK9VcAVjveUmfMRHrMxpYwsmnTGOaTd5fpQBjpumBHqHJn7UReteBMeJ1wHJOSE1mz9YUAoMEcsQiIpTdfZn+ely6DBo9ygs4OpfJpt60hdgPagiUdlRR9o+YD8oh2aPlfsGdJAfu4JDc/XzSQwoKtUmfgMolNqHCbH9umVWoE5wdFZ3yGGW8kAr8VkZLmHz976L8nqNOwk/Aqt4GKDfKve0tQdLDXAo7/MwK3qlmRgl+LYwMCyoITDJ1Du8/5ylsjc5EQXANdxnhI6L+dGQKBgQD0gpVIe4q7Qh+CL0fqhvyfUXi2YOhpuvQg8R+DME62Y5soTe+NpgLQf7MoG5SHwk7n7so2AZAlbgtf+a6E7R96I7MxEnUoy9sqBJlw7E9uXsc6GnR1jVOB7NZJv24l3WZFWL8LY+JLhz2neJVBkwfNnE+x/LR7KGDoccP7WH1OFQKBgQCblGC22OVZEDHcRs9QtfgWUh/jcoVpMuYzSV9Hauh0TpgEmNwRR5iXyhcWXVkq3ZxZwxbksNsDZcACEHzMR1j9JWIKLDO8swR2j/5iO/X1JBxAZ+TwqsTDxxSOUiklv0oR69Us5G4Hqc9Jw6Up4F8r9q092+r4+GlyImSEhE8FSwKBgGDP94XcjMC6XLlyORpuWkZGvvIPitaaVKFad6y9Ct2CsOOTgD9otQKTHX/K89ORJycEUIJzufxoBw74UJaHQQhebEwIz+TYWq3XZl3k40qDMoUaBnSpAaCXAv3uUxGhCSZ6DiLXaLmZEdtmjkqJifRqaM/DjD6bbUt8CCpUDkFFAoGAPrKWIznXe4P+RAI5TBJ5Ty17cmLVhuafRK3d+t8HnM+Ljda4YTJUOV+U3Et0QsUaXEY1tKXdLUpVDb/tOznJkAL/to/HrdULCGjRIG9/mHIb/IyiA6ceMrPJAe30bSTgycYbm8LW4Lm7MsPojK7CS+pWOKam0/zwKbHYhlr3qW8CgYEAjUD4gF2AoA2JlVY0tzC8QrqNCWNl09VR1W1Ddye9oOFMPvLs4zvFvyg+pIZpQbH+MPLlW6perV0PucYB9LKJlCENn/Dwne3rTFoyfz2aETk3Do5CVZ5SoCSIHQIbuAPDXxJ68OTzLe8LxyY+ohLqGKXNlXThUaV5AIrX/B1L/8w=","json","utf-8","MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoBQhKzuhwbLa1kHfkclcX1CrfNX4ycZu3z3ROEPk1dhpqlr2uHjPR7pqGXGYYaAXBnDPYxf0sbi4CyG+Apn+Z2f1jxM8ffekNg540JtiPjiHUs3Aud4NSnRTVdIPeVk93+c2NeL/dJ2FSQq3PPgn5+jZ+RKk/QPcCew/y7gMZMCBzBhOPVVuHhdTudu5aQOmNkHcj6IVvwZPFhN7S4/ir0fxUpoQnHEk17J+eLUtbvGPa1fEz7QaD9D19KLpKsy78OVclo6M9lxTaf+X56PHc7014UjFAQA9nUWYO2EVYAzyshzntNxGrM3smJ3bUOxR/iL7HBkCf1MTdCPboPy3xwIDAQAB","RSA2");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        request.setBizModel(model);
        model.setOutTradeNo(String.valueOf(System.currentTimeMillis()));
        model.setTotalAmount("88.88");
        model.setSubject("Iphone6 16G");
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        System.out.print(response.getBody());
        System.out.print(response.getQrCode());
    }
}
