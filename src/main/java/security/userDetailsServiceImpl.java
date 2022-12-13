package security;

import model.dao.jpaEntranceForUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class userDetailsServiceImpl implements UserDetailsService {

    @Autowired
    jpaEntranceForUsers entrance;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Boolean exist = entrance.existsByUserAccount(username);
        if(!exist) throw new RuntimeException("username does not exist");
        return new userDetailImpl(entrance.findByUserAccount(username));

}}
