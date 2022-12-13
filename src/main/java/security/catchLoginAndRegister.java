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
import java.util.Optional;

@Component
public class catchLoginAndRegister extends OncePerRequestFilter {
    private Logger log = LoggerFactory.getLogger(catchLoginAndRegister.class);
    @Autowired
    semiPersistence semi;
    @Autowired
    jpaEntranceForUsers entrance;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> op = Optional.ofNullable(request.getHeader("id"));
        //if id it not in map
        if (!op.isPresent()) {

//            log.info("get non header request");
            log.info("收到無header訊息");
            filterChain.doFilter(request, response);
            return;
        }

//        log.info("user '{}' access broke ",op.get());

        //if request has header but it is wrong
        if (!semi.idOrAccountIsExist(op.get())) {
            try {

                throw new RuntimeException("does not login");
            }catch (Exception e){
                log.info("非法ID{}嘗試登入",op.get());
                return;
            }

        }

        log.info("用戶{}通過第一過濾器",op.get());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(entrance.findByUserAccount(semi.uuidToAcount(op.get())),null,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}