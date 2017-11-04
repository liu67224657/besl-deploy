package com.fivewh.deploy.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * http://blog.csdn.net/chals115/article/details/9387239
 * Created by zhimingli on 2015/12/14 0014.
 */
public class UrlFilter implements Filter {
    //需要过滤的post字符
    private static String sqlStr = "',<,>,and,insert,select,%20,delete,update,count,*,%,chr,mid,master,truncate,char,like,declare,&,#,(,),/**/,=,script,<script,\\u0023,redirect:,xwork2,getRuntime,getWriter";

    //需要过滤的url字符
    private static String urlStr = "',<,>,master,truncate,char,script,java.lang.ProcessBuilder,java.lang.String,\\u0023,redirect:,xwork2,getRuntime,getWriter";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Enumeration names = req.getParameterNames();//获取所有的post参数名
        String queryString = req.getQueryString();
        while (names.hasMoreElements()) {
            String st = names.nextElement().toString();
            if (strInj(st, sqlStr) || strInj2(st, urlStr)) {
                req.getSession().setAttribute("msgStr", "请不要输入非法参数：" + req.getParameter(st) + " !");
                res.sendRedirect(req.getContextPath() + "/error/404.htm");
                return;
            }
        }

        Iterator values = req.getParameterMap().values().iterator();//获取所有的post参数值
        while (values.hasNext()) {
            String[] value = (String[]) values.next();
            for (int i = 0; i < value.length; i++) {
                if (strInj(value[i], sqlStr)) {
                    request.setAttribute("msgStr", "请不要输入非法参数：" + value[i] + " !");
                    res.sendRedirect(req.getContextPath() + "/error/404.htm");
                    return;
                }
            }
        }

        //判断访问的url中是否有非法参数
        if (queryString != null && strInj2(queryString, urlStr)) {
            req.getSession().setAttribute("msgStr", "请不要输入非法参数 !");
            res.sendRedirect(req.getContextPath() + "/error/404.htm");
            return;
        }
        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }

    /**
     * 判断字符是否包含非法字符,没有空格
     *
     * @param str
     * @return
     */
    public boolean strInj2(String str, String standStr) {
        if (str == null || str.length() == 0) return false;
        String[] inj_stra = standStr.split(",");
        for (int i = 0; i < inj_stra.length; i++) {
            if (str.toLowerCase().indexOf(inj_stra[i]) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符是否包含非法字符
     *
     * @param str
     * @return
     */
    public boolean strInj(String str, String standStr) {
        if (str == null || str.length() == 0) return false;
        String[] inj_stra = standStr.split(",");
        for (int i = 0; i < inj_stra.length; i++) {
            if (str.toLowerCase().indexOf(" " + inj_stra[i] + " ") >= 0) {
                return true;
            }
        }
        return false;
    }
}
