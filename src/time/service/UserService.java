package time.service;

import java.util.List;

import time.domain.User;

public interface UserService {
	public int registe(User user);

	public List<User> checkLoginname(String loginname);

	public User login(String loginname, String password);
}
