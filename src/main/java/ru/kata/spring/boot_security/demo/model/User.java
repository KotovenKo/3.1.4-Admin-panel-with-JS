package ru.kata.spring.boot_security.demo.model;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", unique = true)
    @Size(min = 2, message = "Поле должно содержать минимум 2 символа")
    @NotBlank(message = "Поле не должно быть пустым")
    private String name;

    @Column(name = "secondname")
    @Size(min = 2, message = "Поле должно содержать минимум 2 символа")
    @NotBlank(message = "Поле не должно быть пустым")
    private String secondName;

    @Column(name = "password")
    private String password;

    @NotNull
    @Positive(message = "Возраст должен быть больше нуля")
    @Column(name = "age")
    private int age;

    @NotBlank(message = "Поле не должно быть пустым")
    @Email(message = "Синтакиси не соответствует адресу электронной почты")
    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
        private Set<Role> roles = new HashSet<>();


    public User() {
    }

    public User(long id, String name, String secondName, String password, int age, String email, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.password = password;
        this.age = age;
        this.email = email;
        this.roles = roles;
    }

    public User(String name, String secondName, String password, int age, String email) {
        this.name = name;
        this.secondName = secondName;
        this.password = password;
        this.age = age;
        this.email = email;

    }

    public User(String name, String secondName, String password, int age, String email, Set<Role> roles) {
        this.name = name;
        this.secondName = secondName;
        this.password = password;
        this.age = age;
        this.email = email;
        this.roles = roles;
    }

    public User(String name, String secondName, int age, String email) {
        this.name = name;
        this.secondName = secondName;
        this.age = age;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
