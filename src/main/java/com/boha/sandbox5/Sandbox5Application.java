package com.boha.sandbox5;

import com.boha.sandbox5.models.City;
import com.boha.sandbox5.models.repositories.CityRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.gcp.core.DefaultGcpProjectIdProvider;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.core.publisher.PubSubPublisherTemplate;
import org.springframework.cloud.gcp.pubsub.core.subscriber.PubSubSubscriberTemplate;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.cloud.gcp.pubsub.support.DefaultPublisherFactory;
import org.springframework.cloud.gcp.pubsub.support.DefaultSubscriberFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
@EnableMongoRepositories()
@EnableScheduling
@EnableCaching
@Configuration
@EnableAutoConfiguration
public class Sandbox5Application implements ApplicationListener<ApplicationReadyEvent>, CommandLineRunner, WebMvcConfigurer {
	@Autowired
	MongoTemplate mongoTemplate;
	private static final Logger LOGGER = Logger.getLogger(Sandbox5Application.class.getName());

	public static void main(String[] args) {
		LOGGER.info("\uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21 \uD83D\uDE21 Sandbox5Application: Starting ...");

		SpringApplication.run(Sandbox5Application.class, args);
	}



	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("\uD83C\uDF3F \uD83C\uDF3F \uD83C\uDF3F Sandbox5Application: command line running? ...");

	}

	static final String mmx = "\uD83E\uDD6C \uD83E\uDD6C \uD83E\uDD6C ";
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		LOGGER.info(mmx +" onApplicationEvent: ApplicationReadyEvent ...");
		LOGGER.info(mmx +" onApplicationEvent: ApplicationReadyEvent: getSpringApplication ..."
				+ event.getSpringApplication().toString());

	}

	// 🍐 🍐 🍐 🍐 setup PubSub 🍐 🍐 🍐 🍐
	@Bean
	public PubSubTemplate pubSubTemplateForSandbox(PubSubPublisherTemplate pubSubPublisherTemplate,
										 PubSubSubscriberTemplate pubSubSubscriberTemplate) {
		return new PubSubTemplate(pubSubPublisherTemplate, pubSubSubscriberTemplate);
	}

	@Bean
	public PubSubPublisherTemplate pubSubPublisherTemplateForSandbox() {
		return new PubSubPublisherTemplate(
				new DefaultPublisherFactory(new org.springframework.cloud.gcp.core.DefaultGcpProjectIdProvider()));
	}
	@Bean
	public PubSubSubscriberTemplate pubSubSubscriberTemplateForSandbox() {
		return new PubSubSubscriberTemplate(
				new DefaultSubscriberFactory(new DefaultGcpProjectIdProvider()));
	}

	@Bean
	@ServiceActivator(inputChannel = "dataTransferChannel")
	public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
		PubSubMessageHandler adapter = new PubSubMessageHandler(pubsubTemplate, defaultTopic);
		adapter.setPublishCallback(
				new ListenableFutureCallback<>() {
					@Override
					public void onFailure(@NotNull Throwable throwable) {
						LOGGER.info(mm + "onFailure: \uD83D\uDD34 \uD83D\uDD34 " +
								"There was an error sending the message.");
						LOGGER.info(throwable.getMessage());
					}

					@Override
					public void onSuccess(String result) {
						LOGGER.info(mm + " message onSuccess: \uD83E\uDD6C\uD83E\uDD6C " +
								"Data was sent to topic: " + defaultTopic +
								"\n"+ mm + " result: " + result);
					}
				});
		return adapter;
	}
	private static final String mm = "\uD83C\uDF4E \uD83C\uDF4E \uD83C\uDF4E";
	@Value("${default.topic}")
	private String defaultTopic;
	@Value("${vehicleLocation.topic}")
	private String vehicleLocationTopic;
	@Value("${spring.data.mongodb.uri}")
	private String mongoString;

	@Bean
	public MongoClient mongoClient() {
		LOGGER.info(vv + vv + vv + vv + " Setting up MongoClient ... ");
		ConnectionString connectionString = new ConnectionString(mongoString);
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.build();

		MongoClient cl =  MongoClients.create(mongoClientSettings);
		MongoCursor<String> cur = cl.listDatabaseNames().cursor();
		LOGGER.info(vv + vv + vv + vv + " MongoDB Database List ");
		while (cur.hasNext()) {
			String db = cur.next();
			LOGGER.info(vv + vv +" Database: " + db);
		}
		return cl;
	}
	static final String vv = "\uD83D\uDC9C\uD83D\uDC9C";
}

