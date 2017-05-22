package dao;

import java.util.List;

import entities.User;

public interface UserDAO{
	public boolean insert(User user);
	public boolean update(User user);
	public boolean delete(int userId);
	public User find(int userId);
	public int count();
	public int countInactive();
	public List<User> listOfUser();
	public List<User> selectLimit(int start, int end);
	public List<User> selectLimitInTrash(int start, int end);
}
