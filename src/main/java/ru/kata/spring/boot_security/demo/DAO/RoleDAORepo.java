package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

@Repository
public interface RoleDAORepo extends CrudRepository<Role, Long> {

    Set<Role> findAll();

    Role findRoleById(long id);

    Role findRoleByName(String name);
}
