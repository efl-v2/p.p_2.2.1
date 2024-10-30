package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
   void add(User user);
   List<User> getAllUsers();
   SessionFactory getSessionFactory();
   User getUserByCar(String model, int series) throws SQLException;
}