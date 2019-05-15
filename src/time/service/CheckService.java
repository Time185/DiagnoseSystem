package time.service;

import java.util.List;

import time.domain.User;

public interface CheckService {
	public List<User> checkLoginname(String loginname);
}
