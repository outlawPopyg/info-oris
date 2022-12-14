package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Comment;
import models.Post;
import models.User;
import repositories.CommentRepository;
import repositories.EntityRepository;
import repositories.PostsRepository;
import services.CommentService;
import services.PostsService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/posts")
public class Posts extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostsService postsService = new PostsService();
        List<Post> posts = postsService.getAllPosts();

        User authUser = (User) req.getSession().getAttribute("authUser");
        posts = posts.stream().filter(Post::isChecked).collect(Collectors.toList());

        req.setAttribute("posts", posts);
        req.setAttribute("isChecked", true);
        req.setAttribute("authUser", authUser);
        req.setAttribute("isAdmin", authUser != null && authUser.getRole().equals("admin"));

        req.getRequestDispatcher("/WEB-INF/jsp/check-posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long postId = Long.parseLong(req.getParameter("deleteId"));

        PostsService postsService = new PostsService();
        CommentService commentService = new CommentService();

        List<Comment> comments = commentService.getAllComments();

        comments.forEach(comment -> {
            if (comment.getPostId().equals(postId)) {
                commentService.deleteComment(comment.getId());
            }
        });

        postsService.deletePost(postId);

        resp.sendRedirect("/posts");
    }
}
