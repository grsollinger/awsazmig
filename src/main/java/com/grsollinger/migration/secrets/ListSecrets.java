package com.grsollinger.migration.secrets;

import com.grsollinger.migration.IService;
import com.grsollinger.migration.aws.AWSConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.services.secretsmanager.model.*;

import java.util.List;

@Service
public class ListSecrets implements IService {

    private static final Logger LOGGER = LogManager.getLogger(ListSecrets.class);

    @Autowired
    private AWSConfiguration awsConfiguration;

    @Autowired
    private CreateSecretClient client;
    @Autowired
    private GetKeyValueSecret keyValueSecret;
    @Autowired
    private GetSecretPolicy policy;

    public void retrieveInfo() {
        this.listAllKeys();
    }

    private void listAllKeys() {
        try {
            ListSecretsResponse secretsResponse = client.createClient().listSecrets();
            List<SecretListEntry> secrets = secretsResponse.secretList();
            LOGGER.info("Amount of Secrets found = " + secrets.size());
            for (SecretListEntry secret: secrets) {
                SecretInformation sinfo = new SecretInformation();
                DescribeSecretResponse response = client.createClient().
                        describeSecret(DescribeSecretRequest.builder().secretId(secret.name()).build());
                // Here is the majority of the info
                LOGGER.info("Response: " + response);
                sinfo.setKeyName(secret.name());
                sinfo.setDescription(secret.description());
                sinfo.setARN(secret.arn());
                sinfo.setKeyValue(keyValueSecret.getValue(sinfo.getKeyName()));
                sinfo.setKmsKeyId(secret.kmsKeyId());
                sinfo.setPolicy(policy.getPolicy(sinfo.getKeyName()));
                LOGGER.info("The secret name is "+sinfo.getKeyName());
                LOGGER.info("The secret description is "+sinfo.getDescription());
                LOGGER.info("ARN = " + sinfo.getARN());
                LOGGER.info("Secret = " + sinfo.getKeyValue());
                LOGGER.info("KMS Key Id " + sinfo.getKmsKeyId());
                LOGGER.info("Policy = " + sinfo.getPolicy());
            }

        } catch (SecretsManagerException e) {
            LOGGER.error(e.awsErrorDetails().errorMessage());
        }
    }

}
