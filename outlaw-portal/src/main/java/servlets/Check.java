package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Entity;
import models.Post;
import repositories.EntityRepository;
import repositories.PostsRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/posts/add/check")
public class Check extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostsRepository repository = new PostsRepository();
        List<Post> uncheckedPosts = (List<Post>) repository.findAll();
        uncheckedPosts = uncheckedPosts.stream().filter(post -> !post.isChecked()).collect(Collectors.toList());

        req.setAttribute("isChecked", false);
        req.setAttribute("posts", uncheckedPosts);

        req.getRequestDispatcher("/WEB-INF/jsp/check-posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = req.getParameter("res");
        Long postId = Long.parseLong(req.getParameter("postId"));
        EntityRepository repository = new PostsRepository();
        Optional<Entity> optional = repository.findById(postId);

        Post post = null;
        if (optional.isPresent()) {
            post = (Post) optional.get();
            if (result.equals("accepted")) {
                post.setChecked(true);
                repository.update(post);
            } else {
                repository.delete(postId);
            }
        }

        resp.sendRedirect("/posts/add/check");
    }
}
