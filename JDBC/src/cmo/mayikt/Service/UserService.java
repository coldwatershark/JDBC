package cmo.mayikt.Service;

import cmo.mayikt.Dao.UserDao;
import cmo.mayikt.Dao.UserDao2;
import cmo.mayikt.Dao.UserDao3;
import cmo.mayikt.entity.UserEntity;

public class UserService {
    private UserDao userDao = new UserDao();
    private UserDao2 userDao2 = new UserDao2();
    private UserDao3 userDao3 = new UserDao3();

    public int registerUser(UserEntity uerEntity) {
        String phone = uerEntity.getPhone();
        UserEntity dbuerEntity = userDao.getByIdPhone(phone);
        if (dbuerEntity != null) {
            System.out.println("该号码已经注册过了，无法重复注册");
            return 0;
        }
        int result = userDao.registerUser(uerEntity);
        return result;
    }

    public UserEntity loginUser(UserEntity userEntity) {

        return userDao.loginUser2(userEntity);
    }

    public UserEntity selectUser(Long id) {

        return userDao3.getUserEntity(id);
    }
}
