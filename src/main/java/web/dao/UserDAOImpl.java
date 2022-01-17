package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@ComponentScan("config")
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    //@Transactional
    @Override
    public List<User> index() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    //@Transactional
    @Override
    public User show(int id){
        return entityManager.find(User.class, id);
    }

//
//    @Override
//    public User getUser(long id) {
//        return (User) entityManager.createQuery("from User u JOIN fetch u.roles where u.id=:id")
//                .setParameter("id", id).getSingleResult();
//    }

    /**внимание
    //Удалить этот метод, есть Метод create для администратора????????????????????????????????????????????????????????????????????
     */
    @Transactional
    public void save (User user){
        entityManager.persist(user);
        entityManager.close();
    }

    //Метод для администратора
    @Transactional
    public void update (int id, User updatePerson) {
        User personToBeUpdate =  entityManager.getReference(User.class,id);

        personToBeUpdate.setUsername(updatePerson.getUsername());
        personToBeUpdate.setLastName(updatePerson.getLastName());
        personToBeUpdate.setPassword(updatePerson.getPassword());
    }

    //Метод для администратора
    @Transactional
    public void delete (int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
    /**внимание
    // Недопилен,????????????????????????????????????????????????????????????????????????????????????
     */
    @Override
    public User getUserByName(String username) {
        return (User) entityManager.createQuery("from User u where u.username=:username")
                .setParameter("username", username).getSingleResult();
    }




}
