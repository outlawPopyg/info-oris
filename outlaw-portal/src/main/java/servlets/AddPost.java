package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Post;
import models.User;
import repositories.EntityRepository;
import repositories.PostsRepository;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/posts/add")
@MultipartConfig()
public class AddPost extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/add-post.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String text = req.getParameter("text");
        Part part = req.getPart("image");
        String fileName = part.getSubmittedFileName();
        User authUser = (User) req.getSession().getAttribute("authUser");

        Post.PostBuilder builder = Post.builder()
                .title(title)
                .text(text)
                .userId(authUser.getId());

        if (!fileName.equals("")) {
            InputStream inputStream = part.getInputStream();
            byte[] bytes = inputStream.readAllBytes();

            builder = builder
                    .imageName(fileName)
                    .img(bytes);
        }

        EntityRepository repository = new PostsRepository();
        Post post = builder.build();
        post = (Post) repository.save(post);

        resp.sendRedirect("/home");
    }
}
