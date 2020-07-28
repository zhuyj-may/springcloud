package com.eureka.eurekazuul.Filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyFilter extends ZuulFilter {
    Logger logger = LoggerFactory.getLogger(MyFilter.class);

    /**
     * pre：路由之前
     * routing：路由之时
     * post： 路由之后
     * error：发送错误调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }


    /*过滤排序*/
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 这里可以写逻辑处理，是否需要过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.info("sendZuulResponse:" + ctx.sendZuulResponse());
        if ("/ribbon/helloRibbon".equals(ctx.getRequest().getRequestURI().toString())) {
            return false;
        }
        return true;
    }

    /**
     * 过滤器核心逻辑函数
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        logger.info("Method:" + request.getMethod());
        logger.info("PathInfo:" + request.getPathInfo());
        logger.info("RequestURI:" + request.getRequestURI().toString());
        logger.info("ContextPath:" + request.getContextPath());
        if ("zhuyingjun".equals(request.getParameter("name"))) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", "10000");
            map.put("msg", "name is null");
            map.put("data", new HashMap<>());
            /*
            * false表示不进行路由，设置false后排序在此过滤器之后的其他过滤器可通过此
            * 参数来决定需不需要继续过滤，可参考TwoFilter中的shouldFilter函数
            * */
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseBody(map.toString());
            requestContext.setResponseStatusCode(402);
            PrintWriter printWriter = null;
            try {
                printWriter = requestContext.getResponse().getWriter();
                printWriter.write(map.toString());
            } catch (Exception e) {
                logger.error("错误：{}", e.getMessage());
            } finally {
                //流写出一定要flush和close
                if (printWriter != null) {
                    printWriter.flush();
                    printWriter.close();
                }
            }
        }
        return null;
    }
}
