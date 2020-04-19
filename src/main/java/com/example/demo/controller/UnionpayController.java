package com.example.demo.controller;

import com.example.demo.service.UnionpayService;
import com.unionpay.acp.demo.DemoBase;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UnionpayController {

    @Resource
    private UnionpayService unionpayService;

    @RequestMapping("/pay")
    public void pay(HttpServletResponse response)throws Exception{
        String result=unionpayService.pay("w63342325444446","30000");

        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.getWriter().write(result);
    }

    @RequestMapping("/pay2")
    public String pay2(){
        return unionpayService.pay2("w63342325444446","30000");
    }

    @RequestMapping("/query")
    public Map<String,String> query() {
        return unionpayService.query("w63342325444446");
    }

    @RequestMapping("/rollback")
    public Map<String,String> rollback(){
        return unionpayService.rollBack("w63342325444446");
    }

    @RequestMapping("/cancel")
    public Map<String,String> cancel(@RequestParam("queryId") String queryId){
        return unionpayService.cancel("w63342325444446",queryId);
    }

    @RequestMapping("/refund")
    public Map<String,String> refund(@RequestParam("queryId") String queryId){
        return unionpayService.refund("w63342325444446",queryId);
    }

    @RequestMapping("/downloadBill")
    public String downloadBill() {
        return unionpayService.downloadBill("0119");
    }

    @RequestMapping("/return")
    public String fun(HttpServletRequest request){
        Map<String,String> result=new HashMap<>();

        System.out.println("====== 前台通知 ======");
        Enumeration<String> names=request.getParameterNames();
        if (names!=null){
            while (names.hasMoreElements()){
                String name=names.nextElement();
                String value=request.getParameter(name);
                result.put(name,value);
                System.out.println(name+" ==> "+value);

                if (result.get(name)==null||"".equals(result.get(name))){
                    result.remove(name);
                }
            }
        }

        if (AcpService.validate(result, SDKConstants.UTF_8_ENCODING)){
            return "success";
        }else {
            return "验签失败";
        }
    }

    @RequestMapping("/hello2")
    public void hello2(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Map<String,String> result=new HashMap<>();

        System.out.println("======= 后台通知 ========");
        Enumeration<String> names=request.getParameterNames();
        if (names!=null){
            while (names.hasMoreElements()){
                String name=names.nextElement();
                String value=request.getParameter(name);
                result.put(name,value);
                System.out.println(name+" ==> "+value);

                if (result.get(name)==null||"".equals(result.get(name))){
                    result.remove(name);
                }
            }
        }

        response.getWriter().print("ok");
    }

}
