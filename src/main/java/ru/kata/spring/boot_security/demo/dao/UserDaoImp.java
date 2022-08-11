package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUser(Long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public User getUserByName(String email) {
        User user = (User) entityManager.createQuery("from User where email = :email")
                .setParameter("email", email).getSingleResult();
        return user;
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void remove(long id) {
        entityManager.createQuery("DELETE from User where id = :id").setParameter("id", id).executeUpdate();
    }

    @Override
    public List<User> listUsers() {
        Query query = entityManager.createQuery("from User");
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public List<Role> getAllRoles() {
        Query query = entityManager.createQuery("from Role");
        List<Role> roles = query.getResultList();
        return roles;
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = (Role) entityManager.createQuery("from Role where name = :name")
                .setParameter("name", name).getSingleResult();
        return role;
    }

    @Override
    public User initUser(String email) {
        User user = (User) entityManager.createQuery("from User user JOIN FETCH user.roles where email = :email")
                .setParameter("email", email).getSingleResult();
        return user;
    }
}
