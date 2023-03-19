package gov.iti.jets.testing.demo.day2.lab;

import gov.iti.jets.testing.day2.shopping.domain.Order;
import gov.iti.jets.testing.day2.shopping.domain.User;
import gov.iti.jets.testing.day2.shopping.infrastructure.gateway.SmsGateway;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.Database;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.UserDao;
import gov.iti.jets.testing.day2.shopping.presentation.RegisterServlet;
import gov.iti.jets.testing.day2.shopping.presentation.RequestAttributes;
import gov.iti.jets.testing.day2.shopping.presentation.SessionAttributes;
import gov.iti.jets.testing.day2.shopping.service.RegisterService;
import gov.iti.jets.testing.demo.day2.extensions.DatabaseTest;
import jakarta.servlet.ServletException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import java.io.IOException;
import java.util.List;

@DatabaseTest
public class RegisterUserIntegrationTest {

    @Test
    void register_user_to_database(){
        SmsGateway smsGatewayMock = Mockito.mock();
        RegisterService registerService = new RegisterService(smsGatewayMock);

        User user = new User("Kareeeem" , "01120992347" , "123456789");

//        Database.doInTransactionWithoutResult(em -> {
//            UserDao.save(user , em);
//        });
        //Act
        User getUser = registerService.createUser(user);

        //Assert
        List<User> allUsers = Database.doInTransaction(em -> {
            return em.createQuery("from User u",User.class).getResultList();
        });

        Assertions.assertThat(allUsers.get(0)).usingRecursiveComparison().isEqualTo(user);

        Mockito.verify( smsGatewayMock ).sendSms(
                user.getPhoneNumber(),
                "SMS sent" );
        Mockito.verifyNoMoreInteractions( smsGatewayMock );


    }
    @Test
    void register_user_to_database_servlets() throws ServletException, IOException {
        //Arrange
        SmsGateway smsGatewayMock = Mockito.mock();
        RegisterService registerService = new RegisterService(smsGatewayMock);
        RegisterServlet servlet = new RegisterServlet(registerService);

        User user = new User("Kareeeem" , "01120992347" , "123456789");

        Database.doInTransactionWithoutResult(em -> {
            UserDao.save(user , em);
        });

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute(SessionAttributes.LOGGED_IN_USER.name() , user);

        var mockRequest = new MockHttpServletRequest();
        mockRequest.setSession( mockSession );

        mockRequest.setMethod( "POST" ); // GET for demo only, should be POST

        var mockResponse = new MockHttpServletResponse();

        //Act
        servlet.service(mockRequest,mockResponse);
        User getUser = RequestAttributes.REGISTER_USER.get(mockRequest);

        List<User> allUsers = Database.doInTransaction(em -> {
           return em.createQuery("from User u",User.class).getResultList();
        });

        Assertions.assertThat(allUsers.get(0)).usingRecursiveComparison().isEqualTo(getUser);

    }
}
