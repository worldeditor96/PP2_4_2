package web.Service;

import web.models.User;

import java.util.List;

public interface UserService {
    List<User> index();
    User show(int id);
    void save(User user);
    void update(int id, User updatePerson);
    void delete(int id);

    //User getUser(long id);
}
