package security;

import mappingObj.dao.jpaEntranceForUsers;
import mappingObj.tradeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Component
public class userDetail implements UserDetailsService {

    @Autowired
    jpaEntranceForUsers entrance;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Boolean exist = entrance.existsByUserAccount(username);
        if(!exist) throw new RuntimeException("username does not exist");
        return new implUserDetail(entrance.findByUserAccount(username));
    }
}
