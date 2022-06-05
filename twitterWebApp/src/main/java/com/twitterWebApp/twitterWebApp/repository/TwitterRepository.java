package com.twitterWebApp.twitterWebApp.repository;

import com.twitterWebApp.twitterWebApp.model.TwitterModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitterRepository extends ReactiveMongoRepository<TwitterModel, String> {
}
