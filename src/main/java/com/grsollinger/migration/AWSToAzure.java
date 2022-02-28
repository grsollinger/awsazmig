package com.grsollinger.migration;

import com.grsollinger.EService;
import com.grsollinger.migration.aws.AWSConfiguration;
import com.grsollinger.migration.secrets.ListSecrets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

@Service
public class AWSToAzure {

    private static final Logger LOGGER = LogManager.getLogger(AWSToAzure.class);

    @Autowired
    private ApplicationArguments applicationArguments;

    @Autowired
    private AWSConfiguration awsConfiguration;

    @Autowired
    private ListSecrets listSecrets;

    public void migrate() {
        LOGGER.info("Migration From AWS to Azure Started - Region = " + awsConfiguration.getRegion());
        LOGGER.info("Migration From AWS to Azure Started - Arguments = " + applicationArguments.getSourceArgs()[0]);
        EService service = EService.valueOf(applicationArguments.getSourceArgs()[0]);
        switch (service) {
            case SECRETS:
                LOGGER.info("AWS Secrets Manager to Azure Vault");
                this.listSecrets.retrieveInfo();
                break;
            case LAMBDA:
                LOGGER.info("AWS Lambda to Azure Functions");
                break;
        }
    }

}
