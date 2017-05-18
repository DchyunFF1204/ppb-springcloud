package com.ppb.client;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 支付调用api
 *
 * @author daizy
 * @create 2017-05-18 17:01
 */
@Configuration
public class AlipayClientInstance {

    @Autowired
    public AlipayConfig alipayConfig;

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(alipayConfig.getURL(),alipayConfig.getAPP_ID(),alipayConfig.getAPP_PRIVATE_KEY(),
                alipayConfig.getFORMAT(),alipayConfig.getCHARSET(),alipayConfig.getALIPAY_PUBLIC_KEY());
    }

}
