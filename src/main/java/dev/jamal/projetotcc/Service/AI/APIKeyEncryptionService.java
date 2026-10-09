package dev.jamal.projetotcc.Service.AI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class APIKeyEncryptionService {

    private static final String ALGORITHM =
            "AES/GCM/NoPadding";

    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    private final SecretKeySpec secretKey;
    private final SecureRandom secureRandom =
            new SecureRandom();


    public APIKeyEncryptionService(
            @Value("${security.ai-key-encryption-secret}")
            String secretBase64
    ) {

        byte[] keyBytes =
                Base64.getDecoder()
                        .decode(secretBase64);

        if (keyBytes.length != 32) {
            throw new IllegalStateException(
                    "AI_KEY_ENCRYPTION_SECRET deve possuir 32 bytes."
            );
        }

        this.secretKey =
                new SecretKeySpec(
                        keyBytes,
                        "AES"
                );
    }


    public String encrypt(String value) {

        try {

            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher =
                    Cipher.getInstance(ALGORITHM);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    secretKey,
                    new GCMParameterSpec(
                            TAG_LENGTH,
                            iv
                    )
            );

            byte[] encrypted =
                    cipher.doFinal(
                            value.getBytes(
                                    java.nio.charset.StandardCharsets.UTF_8
                            )
                    );


            byte[] result =
                    new byte[
                            iv.length +
                                    encrypted.length
                            ];

            System.arraycopy(
                    iv,
                    0,
                    result,
                    0,
                    iv.length
            );

            System.arraycopy(
                    encrypted,
                    0,
                    result,
                    iv.length,
                    encrypted.length
            );


            return Base64.getEncoder()
                    .encodeToString(result);

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Não foi possível proteger a chave de API.",
                    exception
            );
        }
    }


    public String decrypt(String encryptedValue) {

        try {

            byte[] data =
                    Base64.getDecoder()
                            .decode(encryptedValue);


            byte[] iv =
                    new byte[IV_LENGTH];

            byte[] encrypted =
                    new byte[
                            data.length -
                                    IV_LENGTH
                            ];


            System.arraycopy(
                    data,
                    0,
                    iv,
                    0,
                    IV_LENGTH
            );

            System.arraycopy(
                    data,
                    IV_LENGTH,
                    encrypted,
                    0,
                    encrypted.length
            );


            Cipher cipher =
                    Cipher.getInstance(ALGORITHM);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey,
                    new GCMParameterSpec(
                            TAG_LENGTH,
                            iv
                    )
            );


            byte[] decrypted =
                    cipher.doFinal(encrypted);


            return new String(
                    decrypted,
                    java.nio.charset.StandardCharsets.UTF_8
            );

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Não foi possível acessar a chave de API.",
                    exception
            );
        }
    }
}