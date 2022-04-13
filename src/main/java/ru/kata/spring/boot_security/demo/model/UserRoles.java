package ru.kata.spring.boot_security.demo.model;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserRoles {

    private Set<Role> allRoles;

    private User user;

    public UserRoles() {
    }

    public UserRoles(Set<Role> allRoles, User user) {
        this.allRoles = allRoles;
        this.user = user;
        setActiveRole();
    }

    public void setActiveRole() {
        for (Role r : allRoles) {
            if (user.getRoles().contains(r)) {
                r.setActive(true);
            }
        }
    }

    public Set<Role> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(Set<Role> allRoles) {
        this.allRoles = allRoles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


