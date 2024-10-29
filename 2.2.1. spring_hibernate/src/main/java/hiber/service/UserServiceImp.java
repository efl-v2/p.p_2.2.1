package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

   private final UserDao userDao;

   @Autowired
   public UserServiceImp(UserDao userDao) {
      this.userDao = userDao;
   }

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional(readOnly = true)
   @Override
   public User getCarInfo(String model, int series) throws SQLException {
      TypedQuery<User> query = userDao.getSessionFactory().getCurrentSession()
              .createQuery("from User user where user.userCar.model = :param1 and user.userCar.series = :param2", User.class);

      query.setParameter("param1", model);
      query.setParameter("param2", series);

      List<User> results = query.getResultList();

      if (results.isEmpty()) {
         throw new SQLException("User not found");
      } else if (results.size() > 1) {
         System.out.println("There are more than one users");
      }

      return results.get(0);
   }

}