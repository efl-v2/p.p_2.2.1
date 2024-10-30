package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> getAllUsers() {
      Query<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
      return query.getResultList();
   }

   @Override
   public SessionFactory getSessionFactory() {
      return sessionFactory;
   }

   @Transactional(readOnly = true)
   @Override
   public User getUserByCar(String model, int series) throws SQLException {
      String hql = "SELECT u FROM User u "
              + "JOIN FETCH u.userCar uc "
              + "WHERE uc.model = :model AND uc.series = :series";

      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);

      List<User> results = query.getResultList();

      if (results.isEmpty()) {
         throw new SQLException("User not found");
      } else if (results.size() > 1) {
         System.out.println("There are more than one users");
      }

      return results.get(0);
   }

}