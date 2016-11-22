package at.aberger.jerseyguice.demo.dao;

import java.util.List;

public interface DataAccessObject<T extends Object> {
	T getById(int id);
	List<T> getAll();
	T add(T t);
	void delete(T t);
	boolean update(T t);
}
