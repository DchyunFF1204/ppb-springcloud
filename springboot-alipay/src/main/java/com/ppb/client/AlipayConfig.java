package com.ppb.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付宝支付配置
 *
 * @author daizy
 * @create 2017-05-18 17:04
 */
@Component
@ConfigurationProperties(prefix = "alipay.config")
public class AlipayConfig {

    private String URL="https://openapi.alipay.com/gateway.do";
    private String APP_ID;
    private String APP_PRIVATE_KEY;
    private String FORMAT="json";
    private String CHARSET="UTF-8";
    private String ALIPAY_PUBLIC_KEY;
    private String SIGN_TYPE="RSA2";

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getAPP_PRIVATE_KEY() {
        return APP_PRIVATE_KEY;
    }

    public void setAPP_PRIVATE_KEY(String APP_PRIVATE_KEY) {
        this.APP_PRIVATE_KEY = APP_PRIVATE_KEY;
    }

    public String getFORMAT() {
        return FORMAT;
    }

    public void setFORMAT(String FORMAT) {
        this.FORMAT = FORMAT;
    }

    public String getCHARSET() {
        return CHARSET;
    }

    public void setCHARSET(String CHARSET) {
        this.CHARSET = CHARSET;
    }

    public String getALIPAY_PUBLIC_KEY() {
        return ALIPAY_PUBLIC_KEY;
    }

    public void setALIPAY_PUBLIC_KEY(String ALIPAY_PUBLIC_KEY) {
        this.ALIPAY_PUBLIC_KEY = ALIPAY_PUBLIC_KEY;
    }

    public String getSIGN_TYPE() {
        return SIGN_TYPE;
    }

    public void setSIGN_TYPE(String SIGN_TYPE) {
        this.SIGN_TYPE = SIGN_TYPE;
    }

}
