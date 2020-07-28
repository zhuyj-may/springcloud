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
public class TwoFilter extends ZuulFilter {
    Logger logger = LoggerFactory.getLogger(TwoFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.info("sendZuulResponse:" + ctx.sendZuulResponse());
        if (!ctx.sendZuulResponse()) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        logger.info("Method:" + request.getMethod());
        logger.info("PathInfo:" + request.getPathInfo());
        logger.info("RequestURI:" + request.getRequestURI().toString());
        logger.info("ContextPath:" + request.getContextPath());
        if ("zhuyingjun1".equals(request.getParameter("name"))) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", "10000");
            map.put("msg", "name is " + request.getParameter("name"));
            map.put("data", new HashMap<>());
            requestContext.setSendZuulResponse(false);//false表示不进行路由
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
