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
@Order(1)
public class LogCorrelationFilter extends OncePerRequestFilter {

    private final AppConfig appConfig;

    public LogCorrelationFilter(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        long time = System.currentTimeMillis();
        generateCorrelationIdIfNotExists(request.getHeader(TrackingContextEnum.X_CORRELATION_ID.getHeaderKey()));
        response.setHeader(TrackingContextEnum.X_CORRELATION_ID.getHeaderKey(), ThreadContext.get(TrackingContextEnum.X_CORRELATION_ID.getThreadKey()));
        chain.doFilter(request, response);
        log.info("{}: {} ms ", request.getRequestURI(),  System.currentTimeMillis() - time);
        ThreadContext.clearAll();
    }

    private void generateCorrelationIdIfNotExists(String xCorrelationId) {
        String correlationId = StringUtils.isEmpty(xCorrelationId) ? String.format(appConfig.getApplicationShortName() + "-%s", UUID.randomUUID().toString().replace("-", "").toLowerCase()).trim() : xCorrelationId;
        ThreadContext.put(TrackingContextEnum.X_CORRELATION_ID.getThreadKey(), correlationId);
    }
}
