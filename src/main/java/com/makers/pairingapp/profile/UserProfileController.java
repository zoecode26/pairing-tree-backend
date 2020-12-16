package com.makers.pairingapp.profile;


import com.makers.pairingapp.profile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.makers.pairingapp.profile.UserProfileService;
import com.makers.pairingapp.profile.UserProfileDataAccessService;
import com.makers.pairingapp.model.ApplicationUser;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user-profile")
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileService userProfileService;


    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<ApplicationUser> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @PostMapping(
            path = "{imageid}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("imageid") UUID imageid,
                                       @RequestParam("file") MultipartFile file) {
        userProfileService.uploadUserProfileImage(imageid, file);
    }

    @GetMapping("{imageid}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("imageid") UUID imageid) {
        return userProfileService.downloadUserProfileImage(imageid);
    }

}

