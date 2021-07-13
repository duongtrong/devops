package vn.com.viettel.vds.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.com.viettel.vds.config.AppConfig;
import vn.com.viettel.vds.constant.TrackingContextEnum;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Configuration
@Slf4j
@Order(3)
public class RequestIdFilter extends OncePerRequestFilter {

    private final AppConfig appConfig;

    public RequestIdFilter(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        generateCorrelationIdIfNotExists(request.getHeader(TrackingContextEnum.X_REQUEST_ID.getHeaderKey()));
        response.setHeader(TrackingContextEnum.X_REQUEST_ID.getHeaderKey(), ThreadContext.get(TrackingContextEnum.X_REQUEST_ID.getThreadKey()));
        chain.doFilter(request, response);
    }

    private void generateCorrelationIdIfNotExists(String xRequestId) {
        String requestId = StringUtils.isEmpty(xRequestId) ? UUID.randomUUID().toString().replace("-", "").toLowerCase() : xRequestId;
        ThreadContext.put(TrackingContextEnum.X_REQUEST_ID.getThreadKey(), requestId);
    }
}
