package gov.iti.jets.testing.demo.day2.lab;

import gov.iti.jets.testing.day2.shopping.domain.Order;
import gov.iti.jets.testing.day2.shopping.domain.User;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.Database;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.UserDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegisterUserUnitTest {

    @Test
    void test_password_is_lessthan_8_digits(){
        Assertions.assertThatThrownBy( () ->  new User( "Kareem Alaa", "011208812" , "12389" ) )
                .hasMessage( "Your password must be above 8 characters" )
                .isInstanceOf( IllegalArgumentException.class );

    }

}
