package com.martel.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public boolean adm;
    public boolean verified;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, boolean adm, boolean verified) {
        this.username = username;
        this.email = email;
        this.adm = adm;
        this.verified = verified;
    }
}
// [END blog_user_class]
