package com.ppb;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
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
     * 支付宝pc支付
     * @param out_trade_no
     * @param total_amount
     * @param subject
     * @param body
     * @param returnUrl
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("/alipayTradePagePay")
    public String alipayTradePagePay(String out_trade_no, String total_amount, String subject, String body,String returnUrl) throws AlipayApiException {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://domain.com/alipayNotifyTradeWapPay");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+out_trade_no+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+total_amount+"," +
                "    \"subject\":\""+subject+"\"," +
                "    \"body\":\""+body+"\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," + //公用回传参数
                "  }");//填充业务参数
        String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        return form;
    }

    /**
     * 支付宝wap支付
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
     * 支付宝app支付
     * @param body
     * @param subject
     * @param out_trade_no
     * @param total_amount
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("/alipayTradeAppPay")
    public AlipayTradeAppPayResponse alipayTradeAppPay(String body,String subject,String out_trade_no,String total_amount) throws AlipayApiException {
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(out_trade_no);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(total_amount);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("http://domain.com/alipayNotifyTradeWapPay");
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        return response;
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
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no"));
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no"));
            //TODO 校验订单    修改订单状态
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
    public AlipayTradeQueryResponse alipayTradeQuery(String out_trade_no, String trade_no) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + out_trade_no + "\"," +
                "    \"trade_no\":\"" + trade_no + "\"" +
                "  }");//设置业务参数
        AlipayTradeQueryResponse response = alipayClient.execute(request); //通过alipayClient调用API，获得对应的response类
        return response;
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
    public AlipayTradeRefundResponse alipayTradeRefund(String out_trade_no, String trade_no, String out_request_no, String refund_amount) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();//创建API对应的request类
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + out_trade_no + "\"," +
                "    \"trade_no\":\"" + trade_no + "\"," +
                "    \"out_request_no\":\"" + out_request_no + "\"," +
                "    \"refund_amount\":\"" + refund_amount + "\"" +
                "  }");//设置业务参数
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        return response;
    }

    /**
     * 支付宝退款单查询
     * @param trade_no
     * @param out_trade_no
     * @param out_request_no
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("/alipayTradeFastpayRefundQuery")
    public AlipayTradeFastpayRefundQueryResponse alipayTradeFastpayRefundQuery(String trade_no,String out_trade_no,String out_request_no) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizContent("{" +
                "    \"trade_no\":\""+trade_no+"\"," +
                "    \"out_trade_no\":\""+out_trade_no+"\"," +
                "    \"out_request_no\":\""+out_request_no+"\"" +
                "  }");
        AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
        return response;
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
