package gov.iti.jets.testing.day2.shopping.service;

import gov.iti.jets.testing.day2.shopping.domain.User;
import gov.iti.jets.testing.day2.shopping.infrastructure.gateway.SmsGateway;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.Database;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.UserDao;

import javax.xml.crypto.Data;

public class RegisterService {
    private static final RegisterService INSTANCE =
            new RegisterService(SmsGateway.getInstance());

    private final SmsGateway smsGateway;

    public RegisterService(SmsGateway smsGateway) {
        this.smsGateway = smsGateway;
    }

    public User createUser(User user){
        Database.doInTransactionWithoutResult( em -> {
            UserDao.save(user,em);
        });
        smsGateway.sendSms(user.getPhoneNumber() , "SMS sent");

        return user;
    }


    public static RegisterService getInstance() {
        return INSTANCE;
    }
}
