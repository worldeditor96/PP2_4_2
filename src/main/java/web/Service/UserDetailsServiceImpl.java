package web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDAO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //перенесем данные из ЮзерРепозиторий в Юзер ДАО, а здесь Заменим ЮзерДАО. Переменную репозитори везде поменяем на юзерДАО
    //private final UserRepository repository;
    private final UserDAO userDAO;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserDetails user =  userDAO.getUserByName(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User not found!", name));
        }
        return user;
    }
}
