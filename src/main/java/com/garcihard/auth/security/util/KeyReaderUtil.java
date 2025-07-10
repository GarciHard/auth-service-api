package com.garcihard.auth.security.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyReaderUtil {

    public static PrivateKey readPrivateKey(InputStream inputStream) throws Exception {
        byte[] keyBytes = readAllBytes(inputStream);
        String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    private static byte[] readAllBytes(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Secret not found");
        }
        return inputStream.readAllBytes();
    }
}
