
import com.rmit.sept.login.model.Business;
import com.rmit.sept.login.model.Role;
import com.rmit.sept.login.model.User;
import com.rmit.sept.login.services.BookService;
import com.rmit.sept.login.services.RequestService;
import com.rmit.sept.login.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
// @Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @Autowired
    RequestService requestService;

    @Override
    public void run(String... strings) throws Exception {
        loadUserData();
        loadBookData();
        loadRequestData();
    }

    public void loadUserData() {
        if (userService.findRepositorySize() == 0) {
            userService.saveUser(new User("johndoe@gmail.com", "JohnDoe", "John Doe", "password", "1 John Street, Doeland"));
            User user = new User("woohoo@email.yeah", "yoyo", "John", "password", "1 John Street, Doeland");
            user.setRole(Role.ADMIN);
            userService.saveUser(user);
            user = new User("email@gmail.com", "true", "John Doe", "password", "1 asd Street, Doeland");
            user.setRole(Role.USER_BUSINESS);
            user.setBusiness(new Business(123, "companyName", user));
            userService.saveUser(user);
        }
    }

    public void loadBookData() {
        // if (bookService.findRepositorySize() == 0) {
        //     //List<BookImage> imageUrl = Arrays.asList(new BookImage(1l, "imageurl", 1));
        //     bookService.saveBook(new Book("title", "authorname", userService.findAllUsers().iterator().next(), 1238, 100, "funny", Quality.NEW, null, 50.0, ServiceType.E_BOOK, BookStatus.AVAILABLE));
        //     bookService.saveBook(new Book("title", "authorname", userService.findAllUsers().iterator().next(), 1238, 100, "not funny", Quality.NEW, null, 502.0, ServiceType.PRE_ORDER, BookStatus.AVAILABLE));
        //     bookService.saveBook(new Book("title", "authorname", userService.findAllUsers().iterator().next(), 1236, 100, "funny", Quality.NEW, null, 50.0, ServiceType.PRINT_ON_DEMAND, BookStatus.AVAILABLE));
        //     bookService.saveBook(new Book("title", "weee woo23", userService.findAllUsers().iterator().next(), 123, 100, "not funny", Quality.NEW, null, 50.0, ServiceType.SUPPLY, BookStatus.AVAILABLE));
        //     bookService.saveBook(new Book("title", "authorname", userService.findAllUsers().iterator().next(), 1235, 0, "funny", Quality.NEW, null, 50.0, ServiceType.E_BOOK, BookStatus.PENDING_APPROVAL));
        //     bookService.saveBook(new Book("title", "authorname", userService.findAllUsers().iterator().next(), 12343, 100, "comedy 2", Quality.USED, null, 502.0, ServiceType.E_BOOK, BookStatus.UNAVAILABLE));
        //     bookService.saveBook(new Book("title", "authorname", userService.findAllUsers().iterator().next(), 123, 200, "funny", Quality.NEW, null, 50.0, ServiceType.E_BOOK, BookStatus.AVAILABLE));
        //     bookService.saveBook(new Book("title", "authorname", userService.findAllUsers().iterator().next(), 1232, 100, "funny", Quality.USED, null, 50.40, ServiceType.E_BOOK, BookStatus.AVAILABLE));
        //     bookService.saveBook(new Book("title", "authorname", userService.findAllUsers().iterator().next(), 12351, 100, "funny", Quality.NEW, null, 50.0, ServiceType.E_BOOK, BookStatus.AVAILABLE));
        //     bookService.saveBook(new Book("title", "authorname", userService.findAllUsers().iterator().next(), 1234, 100, "funny", Quality.USED, null, 150.0, ServiceType.E_BOOK, BookStatus.AVAILABLE));
        // }
    }

    public void loadRequestData() {

    }
}

/*
INSERT INTO users VALUES (20,'tru','$2a$10$lEB1xrf8rfti00nDYDy1iuklqEr3tgILvnDZJX4DtMyo9MFxG4I7u','yeah@woohoo.com','alex alex',0,0,'Address',TIMESTAMP '2021-09-20T12:22:31.028+00:00', NULL, 'ENABLED', 'ADMIN');
INSERT INTO users VALUES (22,'not admin','$2a$10$653wiS/s1aBMeEJUGJcS8eKXpM0V.XQtUJaud4hndTbCPv3lIlzJW','yup@woohoo.com','normal user',0,0,'Address at 2',TIMESTAMP '2021-09-22T02:39:20.593+00:00', NULL, 'ENABLED', 'USER_NORMAL');
INSERT INTO users VALUES (23,'business','$2a$10$JxfmJlvv9g1O7L3yVsKXie7aETfBVdO1rY/lOoZuavn2NjdHaDpi.','yup@woohoo.com','business user',0,0,'Address at 2 street',TIMESTAMP '2021-09-22T02:44:23.945+00:00', NULL, 'ENABLED', 'USER_BUSINESS');

INSERT INTO business_users VALUES (1, 23, 12345, 'business corp');

INSERT INTO books VALUES (1, 20, 'funny', 'full name', 'USED', 'title', 131251231, 59.99, 5, 1, 'PRINT_ON_DEMAND', 100, 'AVAILABLE', TIMESTAMP '2004-10-19 10:43:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (2, 23, 'not funny', 'fullname', 'USED', 'title', 131251231, 59.99, 5, 1, 'E_BOOK', 99, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (3, 23, 'funny', 'fullname', 'USED', 'title', 131251231, 59.99, 5, 1, 'PRE_ORDER', 9, 'UNAVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (4, 20, 'not funny', 'fullname', 'USED', 'title', 131251231, 59.99, 5, 1, 'SUPPLY', 99, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (5, 22, 'funny', 'fullname', 'USED', 'title', 131251231, 109.99, 5, 1, 'SUPPLY', 99, 'UNAVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (6, 22, 'comedy 2', 'fullname', 'USED', 'title', 131251231, 5.99, 5, 1, 'SUPPLY', 99, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (7, 22, 'funny', 'weee woo', 'NEW', 'title', 131251231, 59.99, 5, 1, 'SUPPLY', 0, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (8, 22, 'funny', 'fullname', 'NEW', 'title', 131251231, 9.99, 5, 1, 'SUPPLY', 99, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (9, 22, 'funny', 'fullname', 'NEW', 'title', 131251231, 59.99, 5, 1, 'SUPPLY', 99, 'PENDING_APPROVAL', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');
INSERT INTO books VALUES (10, 22, 'funny', 'fullname', 'USED', 'title', 131251231, 59.99, 5, 1, 'SUPPLY', 99, 'AVAILABLE', TIMESTAMP '2004-10-19 10:23:54', TIMESTAMP '2004-10-19 10:23:54');

INSERT INTO book_images VALUES (1, 1, 'https://assets.teenvogue.com/photos/5cd4384fac4d9e712fe2ebb0/2:3/w_1852,h_2778,c_limit/The%20Gravity%20of%20Us_.jpg', 0);
INSERT INTO book_images VALUES (2, 2, 'https://assets.teenvogue.com/photos/5cd4384fac4d9e712fe2ebb0/2:3/w_1852,h_2778,c_limit/The%20Gravity%20of%20Us_.jpg', 0);
INSERT INTO book_images VALUES (3, 3, 'https://assets.teenvogue.com/photos/5cd4384fac4d9e712fe2ebb0/2:3/w_1852,h_2778,c_limit/The%20Gravity%20of%20Us_.jpg', 0);
INSERT INTO book_images VALUES (4, 4, 'https://assets.teenvogue.com/photos/5cd4384fac4d9e712fe2ebb0/2:3/w_1852,h_2778,c_limit/The%20Gravity%20of%20Us_.jpg', 0);
INSERT INTO book_images VALUES (5, 5, 'https://assets.teenvogue.com/photos/5cd4384fac4d9e712fe2ebb0/2:3/w_1852,h_2778,c_limit/The%20Gravity%20of%20Us_.jpg', 0);
INSERT INTO book_images VALUES (6, 6, 'https://assets.teenvogue.com/photos/5cd4384fac4d9e712fe2ebb0/2:3/w_1852,h_2778,c_limit/The%20Gravity%20of%20Us_.jpg', 0);
INSERT INTO book_images VALUES (7, 7, 'https://assets.teenvogue.com/photos/5cd4384fac4d9e712fe2ebb0/2:3/w_1852,h_2778,c_limit/The%20Gravity%20of%20Us_.jpg', 0);
INSERT INTO book_images VALUES (8, 8, 'https://assets.teenvogue.com/photos/5cd4384fac4d9e712fe2ebb0/2:3/w_1852,h_2778,c_limit/The%20Gravity%20of%20Us_.jpg', 0);
INSERT INTO book_images VALUES (9, 9, 'https://assets.teenvogue.com/photos/5cd4384fac4d9e712fe2ebb0/2:3/w_1852,h_2778,c_limit/The%20Gravity%20of%20Us_.jpg', 0);
INSERT INTO book_images VALUES (10, 10, 'https://assets.teenvogue.com/photos/5cd4384fac4d9e712fe2ebb0/2:3/w_1852,h_2778,c_limit/The%20Gravity%20of%20Us_.jpg', 0);
INSERT INTO book_images VALUES (11, 1, 'https://assets.teenvogue.com/photos/5cd4384fac4d9e712fe2ebb0/2:3/w_1852,h_2778,c_limit/The%20Gravity%20of%20Us_.jpg', 0);

INSERT INTO transactions VALUES (1, 20, 1, 5.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'DELIVERED');
INSERT INTO transactions VALUES (2, 22, 2, 59.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'IN_TRANSIT');
INSERT INTO transactions VALUES (3, 23, 3, 9.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'REFUNDED');
INSERT INTO transactions VALUES (4, 20, 5, 59.09, TIMESTAMP '2004-10-19 10:23:54', NULL, 'CANCELLED');
INSERT INTO transactions VALUES (5, 22, 8, 5.90, TIMESTAMP '2004-10-19 10:23:54', NULL, 'PRE_ORDER');
INSERT INTO transactions VALUES (6, 20, 8, 50.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'PROCESSING');
INSERT INTO transactions VALUES (7, 23, 8, 59.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'DELIVERED');
INSERT INTO transactions VALUES (8, 20, 5, 59.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'DELIVERED');
INSERT INTO transactions VALUES (9, 23, 10, 59.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'DELIVERED');
INSERT INTO transactions VALUES (10, 20, 1, 59.99, TIMESTAMP '2004-10-19 10:23:54', NULL, 'DELIVERED');

INSERT INTO requests VALUES (1, 20, 'request new bus', 'NEW_BUSINESS_USER', '2004-10-19 10:23:54', NULL); 
INSERT INTO requests VALUES (2, 22, 'request to reg', 'TO_REG_USER', '2004-10-19 10:23:54', NULL); 
INSERT INTO requests VALUES (3, 23, 'new book', 'NEW_BOOK_LISTING', '2004-10-19 10:23:54', NULL); 
INSERT INTO requests VALUES (4, 20, 'to bus', 'TO_BUSINESS_USER', '2004-10-19 10:23:54', NULL); 
*/