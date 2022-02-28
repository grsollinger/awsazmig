package com.grsollinger.migration.secrets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.model.GetResourcePolicyRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetResourcePolicyResponse;

@Service
public class GetSecretPolicy {

    private static final Logger LOGGER = LogManager.getLogger(GetSecretPolicy.class);

    @Autowired
    private CreateSecretClient client;

    public String getPolicy(String key) {
        LOGGER.info("getPolicy(String) - key = " + key);
        GetResourcePolicyRequest request = GetResourcePolicyRequest.builder().secretId(key).build();
        GetResourcePolicyResponse policy = client.createClient().getResourcePolicy(request);
        return policy.resourcePolicy();
    }
}
