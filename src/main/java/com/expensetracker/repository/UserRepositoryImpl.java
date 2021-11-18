package com.expensetracker.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.expensetracker.exceptions.UserNotFoundException;
import com.expensetracker.model.User;

public class UserRepositoryImpl implements IUserRepository {
	static Connection connection;

	@Override
	public void registerUser(User user) {
		connection = ModelDAO.openConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(Queries.REGISTERQUERY);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			ModelDAO.closeConnection();
		}
	}

	@Override
	public void loginUser(User user) throws UserNotFoundException {
		connection = ModelDAO.openConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(Queries.LOGINUSERQUERY);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());

			ResultSet result = statement.executeQuery();

			User foundUser = null;

			while (result.next()) {
				foundUser = new User();
				foundUser.setUserid(result.getInt("userid"));
				foundUser.setEmail(result.getString("email"));
				foundUser.setName(result.getString("name"));
				foundUser.setPassword(result.getString("password"));
			}

			if (foundUser == null) {
				throw new UserNotFoundException();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			ModelDAO.closeConnection();
		}

	}

	@Override
	public void updateUser(String email, String name) throws UserNotFoundException {
		connection = ModelDAO.openConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(Queries.UPDATEUSERQUERY);
			statement.setString(1, name);
			statement.setString(2, email);
			int updateCount = statement.executeUpdate();

			if (updateCount == 0) {
				throw new UserNotFoundException();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			ModelDAO.closeConnection();
		}

	}

	@Override
	public User findUserById(int userId) throws UserNotFoundException {
		connection = ModelDAO.openConnection();
		PreparedStatement statement = null;
		User foundUser = null;

		try {
			statement = connection.prepareStatement(Queries.FINDUSERBYIDQUERY);
			statement.setInt(1, userId);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				foundUser = new User();
				foundUser.setUserid(result.getInt("userid"));
				foundUser.setEmail(result.getString("email"));
				foundUser.setName(result.getString("name"));
				foundUser.setPassword(result.getString("password"));
			}

			if (foundUser == null) {
				throw new UserNotFoundException();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			ModelDAO.closeConnection();
		}
		return foundUser;
	}

	@Override
	public List<User> findAllUser() {
		PreparedStatement statement = null;
		Connection connection = ModelDAO.openConnection();
		List<User> users = new ArrayList<>();

		try {
			statement = connection.prepareStatement(Queries.FINDALLUSERSQUERY);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				User user = new User();
				user.setName(result.getString("name"));
				user.setEmail(result.getString("email"));
				user.setPassword(result.getString("password"));
				user.setUserid(result.getInt("userid"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			ModelDAO.closeConnection();
		}

		return users;
	}

}
