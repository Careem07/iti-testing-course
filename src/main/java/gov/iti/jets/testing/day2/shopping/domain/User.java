package gov.iti.jets.testing.day2.shopping.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    private String phoneNumber;

    public User(String name, String phoneNumber , String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        validatePassword(password);
    }

    private static void validatePassword(String password) {
        if (password.length() < 8){
            throw  new IllegalArgumentException("Your password must be above 8 characters");
        }
    }

    protected User() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
