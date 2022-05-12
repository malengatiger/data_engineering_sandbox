package com.boha.sandbox5.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

@Service
public class SubscriberService {
    static final Logger LOGGER = LoggerFactory.getLogger(SubscriberService.class.getSimpleName());
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();
    static final String mx = "\uD83C\uDD7F️ \uD83C\uDD7F️ \uD83C\uDD7F️ ";
    static final String my = "\uD83C\uDF4E ";
    static final String mm = " \uD83D\uDD35 \uD83D\uDD35 \uD83D\uDD35 \uD83D\uDD35 \uD83D\uDD35  SubscriberService: ";

   public void subscribe() {

   }
}
