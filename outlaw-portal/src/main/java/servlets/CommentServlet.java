package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Comment;
import models.User;
import repositories.CommentRepository;
import repositories.EntityRepository;
import services.CommentService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long postId = Long.parseLong(req.getParameter("postId"));
            CommentService commentService = new CommentService();
            User user = (User) req.getSession().getAttribute("authUser");

            List<Comment> comments = commentService.getAllComments();

            req.setAttribute("comments", comments.stream()
                    .filter(comment -> comment.getPostId().equals(postId))
                    .collect(Collectors.toList())
            );

            req.setAttribute("isAuth", user != null);
            req.getRequestDispatcher("/WEB-INF/jsp/comment.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("comment");
        Long postId = Long.parseLong(req.getParameter("postId"));

        if (text.length() > 0) {
            CommentService commentService = new CommentService();
            User user = (User) req.getSession().getAttribute("authUser");

            Comment comment = Comment.builder()
                    .text(text)
                    .userId(user.getId())
                    .postId(postId)
                    .build();

            commentService.saveComment(comment);
        }

        resp.sendRedirect("/comment?postId=" + postId);
    }
}
