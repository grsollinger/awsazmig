package com.grsollinger.migration.secrets;

import com.grsollinger.migration.aws.AWSConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Service
public class CreateSecretClient {

    private static final Logger LOGGER = LogManager.getLogger(CreateSecretClient.class);

    @Autowired
    private AWSConfiguration awsConfiguration;

    public SecretsManagerClient createClient() {
        String region = awsConfiguration.getRegion();
        LOGGER.info("List Secrets for Region = " + region);
        SecretsManagerClient client = SecretsManagerClient.builder().region(Region.of(region)).build();
        return client;
    }

}
