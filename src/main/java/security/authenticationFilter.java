package security;

import model.dao.jpaEntranceForUsers;
import model.deep.semiPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
public class authenticationFilter extends OncePerRequestFilter {
    private Logger log = LoggerFactory.getLogger(authenticationFilter.class);
    @Autowired
    semiPersistence semi;
    @Autowired
    jpaEntranceForUsers entrance;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> op = Optional.ofNullable(request.getHeader("id"));
        //if id it not in map
        if (!op.isPresent()) {

            log.info("a user login or register");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("user '{}' access broke ",op.get());
        //if request has header but it is wrong
        if (!semi.idOrAccountIsExist(op.get())) {
            throw new RuntimeException("does not login");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(entrance.findByUserAccount(semi.uuidToAcount(op.get())),null,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}