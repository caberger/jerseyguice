package at.aberger.jerseyguice.demo.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Singleton;

import at.aberger.jerseyguice.demo.model.User;

@Singleton
public class MyInMemoryUserDemoDatabase extends UserDao {
	private static Logger log = Logger.getLogger(MyInMemoryUserDemoDatabase.class.getName());
	Map<Integer, User> users;
	int sequence = 0;
	
	public MyInMemoryUserDemoDatabase() {
		users = new HashMap<>();
		addUser(new Integer(1), "Joe Sixpack", "joe.sixpack@example.com");
		addUser(2, "John Doe", "john.dow@example.com");
		addUser(3, "Jane Roe", "jane.roe@example.com");
	}
	User addUser(int id, String name, String email) {
		User user = new User();
		user.id = ++sequence;
		user.name = name;
		user.email = email;
		users.put(id, user);
		return user;
	}
	public List<User> getAll() {
		List<User> sortedUsers = new ArrayList<User>(users.values());

		Collections.sort(sortedUsers, new Comparator<User>() {
			public int compare(User lhs, User rhs) {
				return lhs.id - rhs.id;
			}
		});
		return sortedUsers;
	}
	public User getById(int id) {
		return users.get(id);
	}
	public User add(User user) {
		return addUser(users.size() + 1, user.name, user.email);
	}
	@Override
	public void delete(User user) {
		log.info("remove user " + user);
		users.remove(user.id);
	}
	@Override
	public boolean update(User user) {
		boolean ok = false;
		User existingUser = users.get(user.id);
		if (existingUser != null) {
			existingUser.name = user.name;
			existingUser.email = user.email;
			ok = true;
		}
		return ok;
		
	}
}
