package gov.iti.jets.testing.day2.shopping.presentation;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public enum Jsps {
   VIEW_CREATED_ORDER("/WEB-INF/view-created-order.jsp"),
    VIEW_USER("/WEB-INF/register.jsp");

    private final String path;

    public String getPath() {
        return path;
    }

    Jsps( String path) {
        this.path = path;
    }

    public void forward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(this.path).forward(req, resp);
    }
}
