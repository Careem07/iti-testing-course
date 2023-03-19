package gov.iti.jets.testing.day2.shopping.presentation;

import gov.iti.jets.testing.day2.shopping.domain.User;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.UserDao;
import gov.iti.jets.testing.day2.shopping.service.RegisterService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final RegisterService service ;

    public RegisterServlet() {
        service = RegisterService.getInstance();
    }

    public RegisterServlet(RegisterService service) {
        this.service = service;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User(req.getParameter("username") , req.getParameter("phone") , req.getParameter("pass"));
        //User user = SessionAttributes.LOGGED_IN_USER.get(req);
        User userAtt = service.createUser(user);
        RequestAttributes.REGISTER_USER.set(req,userAtt);
        Jsps.VIEW_USER.forward(req,resp);
    }
}
