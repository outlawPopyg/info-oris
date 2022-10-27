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
import services.PostsService;
import util.Util;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/posts/*")
public class PostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long postId = Long.parseLong(req.getPathInfo().substring(1));

            PostsService postsService = new PostsService();
            Post post = postsService.getPost(postId);

            req.setAttribute("post", post);
            req.setAttribute("author", Util.getAuthorName(post.getUserId()));
            req.getRequestDispatcher("/WEB-INF/jsp/post.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("errorMessage", "Не можем найти пост");
            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long postId = Long.parseLong(req.getPathInfo().substring(1));

            PostsService postsService = new PostsService();
            Post post = postsService.getPost(postId);

            req.setAttribute("post", post);
            req.getRequestDispatcher("/WEB-INF/jsp/add-post.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("errorMessage", "Не можем найти пост");
            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }
}
