package com.makers.pairingapp.bucket;

public enum BucketName {

    PROFILE_IMAGE("profilebucketpair");

    private final String bucketName;

    BucketName(String bucketName){
        this.bucketName = bucketName;
    }
    public String getBucketName() {
        return bucketName;
    }
}
