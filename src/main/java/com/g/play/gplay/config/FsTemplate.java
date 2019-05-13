package com.g.play.gplay.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * File system template to CRUD for files in MongoDB
 */

@Configuration
public class FsTemplate extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.database}")
    private String database;

    /**
     * Create a new instance of the MongoDB GridFS template in order to store and retrieve
     * Large sized files (in this case audio files)
     *
     * @return the gridFSTemplate allowing large file transactions
     * @throws Exception if anything goes wrong with creating the gridFSTemplate
     */
    @Bean
    public GridFsOperations gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    /**
     * Get the name of the database
     *
     * @return the database name
     */
    @Override
    protected String getDatabaseName() {
        return database;
    }

    /**
     * Retrieve the mongoDB Client
     *
     * @return the mongoDB Client object
     */
    @Override
    public MongoClient mongoClient() {
        return new MongoClient(host);
    }
}