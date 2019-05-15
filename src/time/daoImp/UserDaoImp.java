package time.daoImp;






import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import time.dao.UserDao;
import time.domain.User;
import time.utils.HibernateUtils;



public class UserDaoImp implements UserDao {
	
	// 用来注册
	@Override
	public int register(User user) {
		Session session = HibernateUtils.getSession();	
		Transaction tran = session.beginTransaction();
		int save = 0;
		try {
			save = (int) session.save(user);
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		tran.commit();
		session.close();
		return save;
	}
	
	
	// 根据用户名获取用户信息
	public List<User> getUser(String name) {
		Session session = HibernateUtils.getSession();
		Transaction tran = session.beginTransaction();
 		String hql = "select loginname from User where loginname = ?";
		Query query = session.createQuery(hql);
		query.setString(0,name);
		System.out.println("**********");
		List<User> list = query.list();
		
		System.out.println("**********-----------");
		tran.commit();
		session.close();
		return list;
	}

	// 登录功能
	@Override
	public User login(String loginname, String password) {		
		Session session = HibernateUtils.getSession();
		Transaction tran = session.beginTransaction();
 		String hql = "from User where loginname = ? and password = ?";
		Query query = session.createQuery(hql);
		query.setString(0,loginname);
		query.setString(1,password);
		List<User> list = query.list();
		
		try {
			User user = list.get(0);
			user.setNum(user.getNum() + 1);
			return user;
			
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("异常");
		}finally {
			tran.commit();
			session.close();
		}
		return new User();
	}
}
