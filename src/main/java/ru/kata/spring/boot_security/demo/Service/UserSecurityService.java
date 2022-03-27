package ru.kata.spring.boot_security.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.UserDAOCrudRepo;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserDAOCrudRepo userDAOCrudRepo;

    public User findUserByName(String name){
        return userDAOCrudRepo.findUserByName(name);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = findUserByName(username);
       if(user == null) {
           throw new UsernameNotFoundException(String.format("User %s not exist", username));
       }
       return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
               mapRolesToAuthorities(user.getRoles()));
    }

    private Set<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(r->new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());

    }
}
