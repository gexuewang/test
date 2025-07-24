package com.gexw.entity;


/**
 * 功能描述：支付对象实体类
 * 作者：zz
 * 时间：2022/5/8 12:21
 */
public class AliPayBean {
    private String out_trade_no;//订单号
    private String subject;//订单名称
    private String total_amount;//订单金额
    private String body;//商品描述
    private String timeout_express="60m";//超时时间
    private String product_code="FAST_INSTANT_TRADE_PAY";//产品编号

    public AliPayBean() {
    }

    public AliPayBean(String out_trade_no, String subject, String total_amount, String body, String timeout_express, String product_code) {
        this.out_trade_no = out_trade_no;
        this.subject = subject;
        this.total_amount = total_amount;
        this.body = body;
        this.timeout_express = timeout_express;
        this.product_code = product_code;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    @Override
    public String toString() {
        return "AliPayBean{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", subject='" + subject + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", body='" + body + '\'' +
                ", timeout_express='" + timeout_express + '\'' +
                ", product_code='" + product_code + '\'' +
                '}';
    }
}
