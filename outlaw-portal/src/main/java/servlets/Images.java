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
import java.util.List;

@WebServlet(urlPatterns = "/images/*")
public class Images extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getRequestURI().substring(req.getRequestURI().lastIndexOf('/')+1);

        EntityRepository repository = new PostsRepository();
        List<Post> posts = (List<Post>) repository.findAll();

        for (Post post : posts) {
            if (post.getImageName() != null && post.getImageName().equals(fileName)) {
                byte[] content = post.getImg();
                resp.setContentType(getServletContext().getMimeType(fileName));
                resp.setContentLength(content.length);
                resp.getOutputStream().write(content);
                return;
            }
        }

        req.setAttribute("errorMessage", "Изображение " + fileName + " отсутсвует");
        req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
    }
}
