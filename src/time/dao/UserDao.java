package time.dao;

import java.util.List;

import time.domain.User;

public interface UserDao {
	public int register(User user);


	public List<User> getUser(String name);

	public User login(String loginname, String password);
}
