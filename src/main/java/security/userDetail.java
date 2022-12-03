package security;

import mappingObj.dao.jpaEntranceForUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
