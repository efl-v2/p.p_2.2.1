package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car("Car1", 2018);
      Car car2 = new Car("Car2", 2013);
      Car car3 = new Car("Car3", 2022);
      Car car4 = new Car("Car4", 2024);

      User user1 = new User("User1", "Lastname1", "user1@mail.ru", car1);
      User user2 = new User("User2", "Lastname2", "user2@mail.ru", car2);
      User user3 = new User("User3", "Lastname3", "user3@mail.ru", car3);
      User user4 = new User("User4", "Lastname4", "user4@mail.ru", car4);

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);
      userService.add(user4);

      List<User> users = userService.getListUsers();
      for (User user : users) {
         System.out.println(user);
         System.out.println();
      }

      try {
         System.out.println(userService.getUserByCar("Car2", 2013));
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

      context.close();
   }
}