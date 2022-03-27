package ru.kata.spring.boot_security.demo.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import java.util.Set;

public interface RoleService {

    Set<Role> getRoles();

    void addRole(Role role);

    Role getRole(long id);

    void update(long id, Role role);

    void delete(long id);

    Role getRoleByName(String name);


}
