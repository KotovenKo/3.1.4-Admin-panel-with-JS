package ru.kata.spring.boot_security.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.RoleDAORepo;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleDAORepo roleDAORepo;

    @Override
    public Set<Role> getRoles() {
        return null;
    }

    @Override
    public void addRole(Role role) {
        roleDAORepo.save(role);

    }

    @Override
    public Role getRole(long id) {
        return roleDAORepo.findRoleById(id);
    }

    @Override
    public void update(long id, Role role) {
        roleDAORepo.save(role);
    }

    @Override
    public void delete(long id) {
        roleDAORepo.deleteById(id);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDAORepo.findRoleByName(name);
    }


}
