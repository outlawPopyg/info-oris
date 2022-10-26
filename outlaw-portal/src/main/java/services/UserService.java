package services;

import models.Entity;
import models.User;
import repositories.EntityRepository;
import repositories.UserRepository;

import java.util.Optional;

public class UserService {

    private static final EntityRepository repository = new UserRepository();

    public static String getUserNameByPostId(Long id) {
        Optional<Entity> optional = repository.findById(id);

        if (optional.isPresent()) {
            User user = (User) optional.get();
            return user.getLogin();
        } else {
            throw new IllegalArgumentException("No such user");
        }
    }
}
