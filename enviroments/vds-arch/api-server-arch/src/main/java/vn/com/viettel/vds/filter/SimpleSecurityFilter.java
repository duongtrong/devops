package vn.com.viettel.vds.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.com.viettel.vds.service.SimpleSecurityService;
import vn.com.viettel.vds.util.CachedBodyHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Configuration
@Slf4j
//@Order(4)
public class SimpleSecurityFilter extends OncePerRequestFilter {

    private final SimpleSecurityService simpleSecurityService;

    public SimpleSecurityFilter(SimpleSecurityService simpleSecurityService) {
        this.simpleSecurityService = simpleSecurityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = request.getRequestURI();
        if (StringUtils.substringMatch(path,0, "/api/vault")){
            chain.doFilter(request, response);
            return;
        }
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                new CachedBodyHttpServletRequest(request);
        String verifyString = request.getHeader("Signature");
        byte[] bytes = StreamUtils.copyToByteArray(cachedBodyHttpServletRequest.getInputStream());
        if (simpleSecurityService.verify(bytes,verifyString)){
            chain.doFilter(cachedBodyHttpServletRequest, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
