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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/posts/add/check")
public class Check extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostsService postsService = new PostsService();
        List<Post> uncheckedPosts = postsService.getAllPosts();
        uncheckedPosts = uncheckedPosts.stream().filter(post -> !post.isChecked()).collect(Collectors.toList());

        req.setAttribute("isChecked", false);
        req.setAttribute("posts", uncheckedPosts);

        req.getRequestDispatcher("/WEB-INF/jsp/check-posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = req.getParameter("res");
        Long postId = Long.parseLong(req.getParameter("postId"));
        PostsService postsService = new PostsService();

        Post post = postsService.getPost(postId);

        if (result.equals("accepted")) {
            post.setChecked(true);
            postsService.updatePost(post);
        } else {
            postsService.deletePost(postId);
        }


        resp.sendRedirect("/posts/add/check");
    }
}
