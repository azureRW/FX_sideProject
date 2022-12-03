package security;

import mappingObj.dao.jpaEntranceForUsers;
import model.deep.semiPersistence;
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
public class authenticationFilter extends OncePerRequestFilter {
    @Autowired
    semiPersistence semi;
    @Autowired
    jpaEntranceForUsers entrance;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> op = Optional.ofNullable(request.getHeader("id"));
        System.out.println("filter is run");
        //if id it not in map
        if (!op.isPresent()) {
            System.out.println("i wish i will be a login or register method");
            filterChain.doFilter(request, response);
            return;
        }
        if (!semi.idOrAccountIsExist(op.get())) {
            throw new RuntimeException("does not login");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(entrance.findByUserAccount(semi.uuidToAcount(op.get())),null,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request, response);
    }
}