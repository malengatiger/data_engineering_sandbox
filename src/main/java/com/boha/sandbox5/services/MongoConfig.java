package com.boha.sandbox5.services;

import com.boha.sandbox5.Sandbox5Application;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCursor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private static final Logger LOGGER = Logger.getLogger(MongoConfig.class.getName());



    @Value("${spring.data.mongodb.uri}")
    private String mongoString;
    @Override
    protected String getDatabaseName() {
        return "ardb";
    }

//    @Override
    public MongoClient mongo() {
        LOGGER.info("\uD83D\uDC9C \uD83D\uDC9C mongo: " + mongoString);
        ConnectionString connectionString = new ConnectionString(mongoString);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        MongoClient cl =  MongoClients.create(mongoClientSettings);
        MongoCursor<String> cur = cl.listDatabaseNames().cursor();
        while (cur.hasNext()) {
            String db = cur.next();
            LOGGER.info("\uD83D\uDC9C \uD83D\uDC9C Database: " + db);
        }
        return cl;
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.baeldung");
    }
}
