package me.yex.common.core.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Slf4j
@Component
public class ExceptionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            // 异常捕获，发送到error controller
            request.setAttribute("filter.error", e);
            //将异常分发到/error/exception
            request.getRequestDispatcher("/error/exception").forward(request, response);
        }
    }
}
