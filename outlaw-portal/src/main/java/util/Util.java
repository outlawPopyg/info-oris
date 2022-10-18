package util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import models.Entity;
import models.Post;
import models.User;
import repositories.EntityRepository;
import repositories.PostsRepository;
import repositories.UserRepository;

import java.io.InputStream;
import java.util.Optional;

public class Util {
    public static String getAuthorName(Long id) {
        EntityRepository repository = new UserRepository();
        Optional<Entity> entity = repository.findById(id);

        if (entity.isPresent()) {
            User user = (User) entity.get();
            return user.getLogin();
        } else {
            throw new IllegalArgumentException("There's no such user");
        }
    }

    public static void handleAddAndUpdate(HttpServletRequest req, boolean isUpdate) {
        try {
            String title = req.getParameter("title");
            String text = req.getParameter("text");
            Part part = req.getPart("image");
            User authUser = (User) req.getSession().getAttribute("authUser");
            String fileName = part.getSubmittedFileName();

            Post.PostBuilder builder = Post.builder()
                    .title(title)
                    .text(text)
                    .userId(authUser.getId());


            if (!fileName.equals("")) {
                byte[] bytes;

                InputStream inputStream = part.getInputStream();
                bytes = inputStream.readAllBytes();

                builder = builder
                        .imageName(fileName)
                        .img(bytes);
            }

            EntityRepository repository = new PostsRepository();

            Post post;
            if (isUpdate) {
                Long postId = Long.parseLong(req.getParameter("postId"));
                post = builder.id(postId).isChecked(true).build();
                repository.update(post);
            } else {
                post = builder.build();
                repository.save(post);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
