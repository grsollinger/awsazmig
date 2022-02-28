package com.grsollinger.migration.secrets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

@Service
public class GetKeyValueSecret {

    private static final Logger LOGGER = LogManager.getLogger(GetKeyValueSecret.class);

    @Autowired
    private CreateSecretClient client;

    public String getValue(String key) {
        LOGGER.info("Resolving value for Key = " + key);
        String secret = null;
        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                    .secretId(key)
                    .build();

            GetSecretValueResponse valueResponse = client.createClient().getSecretValue(valueRequest);
            secret = valueResponse.secretString();
        } catch (SecretsManagerException e) {
            LOGGER.error(e.awsErrorDetails().errorMessage());
        }
        return secret;
    }
}
