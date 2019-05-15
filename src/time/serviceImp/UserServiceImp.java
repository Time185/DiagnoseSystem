package time.serviceImp;

import java.util.List;

import time.dao.UserDao;
import time.daoImp.UserDaoImp;
import time.domain.User;
import time.service.UserService;

public class UserServiceImp implements UserService {

	@Override
	public int registe(User user) {
		// TODO Auto-generated method stub
		UserDao dao = new UserDaoImp();
		return dao.register(user);
	}
	public List<User> checkLoginname(String name) {
		// TODO Auto-generated method stub
		UserDao dao = new UserDaoImp();
		return dao.getUser(name);
	}
	@Override
	public User login(String loginname, String password) {
		// TODO Auto-generated method stub
		UserDao dao = new UserDaoImp();
		return dao.login(loginname, password);
		 
	}
}
