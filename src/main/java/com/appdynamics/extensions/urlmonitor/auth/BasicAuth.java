/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.extensions.urlmonitor.auth;

import com.ning.http.client.Realm;

public class BasicAuth {

    private String username;
    private String password;
    private String encryptedPassword;
    private String encryptionKey;

    public BasicAuth(String username, String password, String encryptedPassword, String encryptionKey) {
        this.username = username;
        this.password = password;
        this.encryptedPassword = encryptedPassword;
        this.encryptionKey = encryptionKey;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public Realm.RealmBuilder realmBuilderBase() {
        return new Realm.RealmBuilder()
                .setScheme(Realm.AuthScheme.BASIC)
                .setPrincipal(getUsername())
                .setPassword(AuthSchemeFactory.getPassword(getPassword(),getEncryptedPassword(),getEncryptionKey()));
    }
}
