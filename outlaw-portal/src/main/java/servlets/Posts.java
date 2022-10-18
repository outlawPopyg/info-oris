package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Post;
import models.User;
import repositories.EntityRepository;
import repositories.PostsRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/posts")
public class Posts extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityRepository repository = new PostsRepository();
        List<Post> posts = (List<Post>) repository.findAll();
        User authUser = (User) req.getSession().getAttribute("authUser");
        posts = posts.stream().filter(Post::isChecked).collect(Collectors.toList());

        req.setAttribute("posts", posts);
        req.setAttribute("isChecked", true);
        req.setAttribute("authUser", authUser);

        req.getRequestDispatcher("/WEB-INF/jsp/check-posts.jsp").forward(req, resp);
    }
}
