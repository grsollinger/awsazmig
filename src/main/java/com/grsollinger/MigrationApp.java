package com.grsollinger;

import com.grsollinger.migration.AWSToAzure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MigrationApp {

    private static final Logger LOGGER = LogManager.getLogger(MigrationApp.class);

    @Autowired
    private AWSToAzure awstoazure;

    //@PostConstruct
    private void execute() {
        this.awstoazure.migrate();
    }

    public static void main(String ... args) {
        LOGGER.info("Starting Migration Process AWS to Azure " + args[0] + "------------------------------");
        ConfigurableApplicationContext ctx = SpringApplication.run(MigrationApp.class, args);
        MigrationApp mapp = ctx.getBean(MigrationApp.class);
        mapp.execute();
    }
}
