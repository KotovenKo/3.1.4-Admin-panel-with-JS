package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public interface UserDAOCrudRepo extends CrudRepository<User, Long> {

    List<User> findAll();

    User findUserById(long id);

    @Query(value = "SELECT * FROM  users   LIMIT :counter", nativeQuery = true)
    List<User> findFirst(@Param("counter") int counter);

    User findUserByName(String name);


}
