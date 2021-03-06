/*
 * Copyright 2014. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

package com.appdynamics.extensions.urlmonitor.auth;

import com.appdynamics.extensions.urlmonitor.config.SiteConfig;
import com.appdynamics.extensions.crypto.CryptoUtil;
import com.appdynamics.TaskInputArgs;
import com.ning.http.client.Realm;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory to generate the RealmBuilder based on the chosen authType
 */
public class AuthSchemeFactory {

    public static Realm.RealmBuilder getAuth(AuthTypeEnum authType, SiteConfig siteConfig){

        Realm.RealmBuilder realmBuilder = null;
        switch (authType){
            case NTLM:
                NTLMAuth ntlmAuth = new NTLMAuth(siteConfig.getUsername(),siteConfig.getPassword(),siteConfig.getUrl(),siteConfig.getEncryptedPassword(), siteConfig.getEncryptionKey());
                realmBuilder = ntlmAuth.realmBuilderBase();
                break;
            case BASIC:
                BasicAuth basicAuth = new BasicAuth(siteConfig.getUsername(),siteConfig.getPassword(),siteConfig.getEncryptedPassword(), siteConfig.getEncryptionKey());
                realmBuilder = basicAuth.realmBuilderBase();
                break;
            case SSL:
                SSLCertAuth sslAuth = new SSLCertAuth();
                realmBuilder = sslAuth.realmBuilderBase();
                break;
            case NONE:
                realmBuilder = new Realm.RealmBuilder()
                        .setScheme(Realm.AuthScheme.NONE);
                break;
        }
        return realmBuilder;
    }

    public static String getPassword(String password, String encryptedPassword, String encryptionKey) {

        Map<String, String> map = new HashMap<String, String>();
        if (password != null) {
            map.put(TaskInputArgs.PASSWORD, password);
        }
        if (encryptedPassword != null) {
            map.put(TaskInputArgs.PASSWORD_ENCRYPTED, encryptedPassword);
            map.put(TaskInputArgs.ENCRYPTION_KEY, encryptionKey);
        }
        String plainPassword = CryptoUtil.getPassword(map);
               return plainPassword;
    }
}
