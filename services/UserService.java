package services;

import DAO.UserDAO;
import models.JediBotUser;

public class UserService {
    private UserDAO userDao = new UserDAO();
    public UserService(){

    }
    public JediBotUser findUser(long id){
        return userDao.findById(id);
    }
    public void saveUser(JediBotUser user){
       userDao.save(user);
    }
    public void deleteUser(JediBotUser user){
        userDao.delete(user);
    }
    public void updateUser(JediBotUser user){
        userDao.update(user);
    }
}
