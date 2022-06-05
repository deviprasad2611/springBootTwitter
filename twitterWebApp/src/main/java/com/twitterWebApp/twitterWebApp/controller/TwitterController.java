package com.twitterWebApp.twitterWebApp.controller;

import com.twitterWebApp.twitterWebApp.repository.TwitterRepository;
import com.twitterWebApp.twitterWebApp.model.TwitterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class TwitterController {
    @Autowired
    private TwitterRepository twitterRepo; // created object - handles by spring-boot

    @GetMapping("/tweets")
    public Flux<TwitterModel> getAllTweets() {
        return twitterRepo.findAll();
    }

    @PostMapping("/tweets")
    public Mono<TwitterModel> createTweets(@Valid @RequestBody TwitterModel tweet) {
        return twitterRepo.save(tweet);
    }

    @GetMapping("/tweets/{id}")
    public Mono<ResponseEntity<TwitterModel>> getTweetById(@PathVariable(value = "id") String tweetId) {
        return twitterRepo.findById(tweetId)
                .map(savedTweet -> ResponseEntity.ok(savedTweet))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/tweets/{id}")
    public Mono<ResponseEntity<TwitterModel>> updateTweet(@PathVariable(value = "id") String tweetId,
                                                          @Valid @RequestBody TwitterModel tweet) {
        return twitterRepo.findById(tweetId)
                .flatMap(existingTweet -> {
                    existingTweet.setText(tweet.getText());
                    return twitterRepo.save(existingTweet);
                })
                .map(updatedTweet -> new ResponseEntity<>(updatedTweet, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/tweets/{id}")
    public Mono<ResponseEntity<Void>> deleteTweet(@PathVariable(value = "id") String tweetId) {

        return twitterRepo.findById(tweetId)
                .flatMap(existingTweet ->
                        twitterRepo.delete(existingTweet)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Tweets are Sent to the client as Server Sent Events
    @GetMapping(value = "/stream/tweets", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TwitterModel> streamAllTweets() {
        return twitterRepo.findAll();
    }
}
