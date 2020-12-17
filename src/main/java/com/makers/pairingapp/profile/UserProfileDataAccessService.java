package com.makers.pairingapp.profile;


import com.makers.pairingapp.dao.ApplicationUserDAO;

import com.makers.pairingapp.model.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class UserProfileDataAccessService {

    private final ApplicationUserDAO applicationUserDAO;

    @Autowired
    public UserProfileDataAccessService(ApplicationUserDAO applicationUserDAO) {
        this.applicationUserDAO = applicationUserDAO;
    }

    List<ApplicationUser> getUserProfiles() {
        return applicationUserDAO.findAll();
    }

//    List<ApplicationUser> finduserbyId() {
//        return applicationUserDAO.findById();
//    }

}
