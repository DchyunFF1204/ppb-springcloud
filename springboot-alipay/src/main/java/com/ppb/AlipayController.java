package com.ppb;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ppb.client.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝支付控制中心
 *
 * @author daizy
 * @create 2017-05-18 16:56
 */
@RestController
public class AlipayController {

    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private AlipayConfig alipayConfig;

    /**
     * @param out_trade_no
     * @param total_amount
     * @param subject
     * @param returnUrl
     * @return
     */
    @RequestMapping("/alipayTradeWapPay")
    public String alipayTradeWapPay(String out_trade_no, String total_amount, String subject, String returnUrl) throws AlipayApiException {
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl("http://domain.com/alipayNotifyTradeWapPay");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + out_trade_no + "\"," +     // 订单号
                "    \"total_amount\":\"" + total_amount + "\"," +                // 订单金额
                "    \"subject\":\"" + subject + "\"," +               // 订单名称
                "    \"product_code\":\"QUICK_WAP_PAY\"" +         // 销售码
                "  }");//填充业务参数
        return alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
    }

    /**
     * 支付宝异步通知
     *
     * @param request
     * @return
     */
    @RequestMapping("/alipayNotifyTradeWapPay")
    public String alipayNotify(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean verify_result = AlipaySignature.rsaCheckV1(params, alipayConfig.getALIPAY_PUBLIC_KEY(), alipayConfig.getCHARSET(), alipayConfig.getSIGN_TYPE());
        if (!verify_result) {
            return "failure";
        }
        //交易状态
        String trade_status = new String(request.getParameter("trade_status"));
        //请在这里加上商户的业务逻辑程序代码
        if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
            //TODO 校验订单    修改订单状态
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no"));
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no"));
        }
        return "success";
    }


    /**
     * 查询交易单
     *
     * @param out_trade_no 商户订单号
     * @param trade_no     交易流水号
     * @return
     */
    @RequestMapping("/alipayTradeQuery")
    public String alipayTradeQuery(String out_trade_no, String trade_no) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + out_trade_no + "\"," +
                "    \"trade_no\":\"" + trade_no + "\"" +
                "  }");//设置业务参数
        AlipayTradeQueryResponse response = alipayClient.execute(request); //通过alipayClient调用API，获得对应的response类
        return response.getBody();
    }

    /**
     * 支付宝退款
     *
     * @param out_trade_no
     * @param trade_no
     * @param out_request_no
     * @param refund_amount
     * @return
     */
    @RequestMapping("/alipayTradeRefund")
    public String alipayTradeRefund(String out_trade_no, String trade_no, String out_request_no, String refund_amount) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();//创建API对应的request类
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + out_trade_no + "\"," +
                "    \"trade_no\":\"" + trade_no + "\"," +
                "    \"out_request_no\":\"" + out_request_no + "\"," +
                "    \"refund_amount\":\"" + refund_amount + "\"" +
                "  }");//设置业务参数
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        return response.getBody();
    }

    /**
     * 下载对账单地址
     *
     * 账单文件下载地址，30秒有效
     *
     * @param bill_date yyyy-MM-dd    	需要下载的账单日期，最晚是当期日期的前一天
     * @return
     */
    @RequestMapping("/alipaybillDownloadurlQuery")
    public String alipaybillDownloadurlQuery(String bill_date) throws AlipayApiException {
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();//创建API对应的request类
        request.setBizContent("{" +
                "    \"bill_type\":\"trade\"," +
                "    \"bill_date\":\"" + bill_date + "\"" +
                "  }");//设置业务参数
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        //将接口返回的对账单下载地址传入urlStr
        String urlStr = response.getBody();
        //指定希望保存的文件路径
        String filePath = "/Users/fund_bill_20160405.csv";
        URL url = null;
        HttpURLConnection httpUrlConnection = null;
        InputStream fis = null;
        FileOutputStream fos = null;
        try {
            url = new URL(urlStr);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(5 * 1000);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
            httpUrlConnection.connect();
            fis = httpUrlConnection.getInputStream();
            byte[] temp = new byte[1024];
            int b;
            fos = new FileOutputStream(new File(filePath));
            while ((b = fis.read(temp)) != -1) {
                fos.write(temp, 0, b);
                fos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis!=null) fis.close();
                if(fos!=null) fos.close();
                if(httpUrlConnection!=null) httpUrlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "下载成功";
    }


}
