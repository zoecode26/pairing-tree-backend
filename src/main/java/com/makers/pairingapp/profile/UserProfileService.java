package com.makers.pairingapp.profile;

import com.amazonaws.services.opsworks.model.UserProfile;
import com.makers.pairingapp.bucket.BucketName;
import com.makers.pairingapp.filestore.FileStore;
import com.makers.pairingapp.model.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.makers.pairingapp.dao.ApplicationUserDAO;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
                              FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<ApplicationUser> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }
   // UUID Id = ApplicationUser.getId(); // need to get the current user

    void uploadUserProfileImage(UUID imageid, MultipartFile file) {
        // 1. Check if image is not empty
        isFileEmpty(file);
        // 2. If file is an image
        isImage(file);

        // 3. The user exists in our database
        ApplicationUser user = getUserProfileOrThrow(imageid);

        // 4. Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        // 5. Store the image in s3 and update database (userProfileImageLink) with s3 image link
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            user.setUserProfileImageLink(filename);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    byte[] downloadUserProfileImage(UUID imageid) {

        ApplicationUser user = getUserProfileOrThrow(imageid);

        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getimageId());

        return user.getUserProfileImageLink()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);

    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

//    private ApplicationUser getUserProfileOrThrow(long id) {
//        Stream stream = userProfileDataAccessService
//                .getUserProfiles()
//                .stream()
//                .filter(applicationUser -> Objects.equals(ApplicationUserDAO.getUserProfileId(), id)
//                        .findFirst()
//                        .orElseThrow(() -> new IllegalStateException(("User profile %s not found" + id))));
//        return (ApplicationUser) stream;
//    }
private ApplicationUser getUserProfileOrThrow(UUID imageid) {
    return userProfileDataAccessService
            .getUserProfiles()
            .stream()
            .filter(applicationUser -> applicationUser.getActive().equals(imageid))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", imageid)));
}

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(
                IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType(),
                IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }
}
