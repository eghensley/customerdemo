package com.ehens86.customerdemo.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Configuration
@EnableCouchbaseRepositories(basePackages = {"com.ehens86.customerdemo.dao"})
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Override
    protected List getBootstrapHosts() {
        return Collections.singletonList("127.0.0.1");
    }
 
    @Override
    protected String getBucketName() {
        return "customer-info";
    }
 
    @Override
    protected String getBucketPassword() {
        return "customer-info-1234";
    }
}
