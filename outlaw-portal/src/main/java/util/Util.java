package util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import models.Entity;
import models.Post;
import models.User;
import repositories.EntityRepository;
import repositories.PostsRepository;
import repositories.UserRepository;
import services.PostsService;
import services.UserService;

import java.io.InputStream;
import java.util.Optional;

public class Util {
    public static String getAuthorName(Long id) {
        UserService userService = new UserService();
        User user = userService.getUser(id);

        return user.getLogin();
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

            PostsService postsService = new PostsService();

            Post post;
            if (isUpdate) {
                Long postId = Long.parseLong(req.getParameter("postId"));
                post = builder.id(postId).isChecked(true).build();
                postsService.updatePost(post);
            } else {
                post = builder.build();
                postsService.savePost(post);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
