package com.boha.sandbox5;

import com.boha.sandbox5.services.DataService;
import com.google.cloud.spring.core.DefaultGcpProjectIdProvider;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.core.publisher.PubSubPublisherTemplate;
import com.google.cloud.spring.pubsub.core.subscriber.PubSubSubscriberTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.google.cloud.spring.pubsub.support.DefaultPublisherFactory;
import com.google.cloud.spring.pubsub.support.DefaultSubscriberFactory;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.logging.Logger;

@SpringBootApplication
@EnableMongoRepositories()
@EnableScheduling
@EnableCaching
@Configuration
@EnableAutoConfiguration
@PropertySource(value = "classpath:application.properties")
public class Sandbox5Application implements ApplicationListener<ApplicationReadyEvent>, CommandLineRunner, WebMvcConfigurer {

	private static final Logger LOGGER = Logger.getLogger(Sandbox5Application.class.getName());

	public static void main(String[] args) {
		LOGGER.info(mmz + " Sandbox5 Application: Starting ..." + mmz);

		SpringApplication.run(Sandbox5Application.class, args);
	}



	@Override
	public void run(String... args) throws Exception {
		LOGGER.info(mmz+ "\uD83C\uDF3F \uD83C\uDF3F \uD83C\uDF3F Sandbox5Application: command line running? ...");

	}

	static final String mmx = "\uD83E\uDD6C \uD83E\uDD6C \uD83E\uDD6C ", mmz = "\uD83C\uDF4E \uD83C\uDF4E \uD83C\uDF4E \uD83C\uDF4E";
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		LOGGER.info(mmx +" onApplicationEvent: ApplicationReadyEvent ...");
		LOGGER.info(mmx +" onApplicationEvent: ApplicationReadyEvent: getSpringApplication : "
				+ event.getSpringApplication().toString());
		LOGGER.info(mmx +" onApplicationEvent: initialize DataService for MongoDB access ...");
		dataService.initialize();

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
				new DefaultPublisherFactory(new DefaultGcpProjectIdProvider()));
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
//		adapter.setPublishCallback(
//				new ListenableFutureCallback<Object>() {
//					@Override
//					public void onFailure(@NotNull Throwable throwable) {
//						LOGGER.info(mm + "onFailure: \uD83D\uDD34 \uD83D\uDD34 " +
//								"There was an error sending the message.");
//						LOGGER.info(throwable.getMessage());
//					}
//
//					@Override
//					public void onSuccess(String result) {
//						LOGGER.info(mm + " message onSuccess: \uD83E\uDD6C\uD83E\uDD6C " +
//								"Data was sent to topic: " + defaultTopic +
//								"\n"+ mm + " result: " + result);
//					}
//				});
		adapter.setPublishCallback(new ListenableFutureCallback<String>() {
			@Override
			public void onFailure( Throwable throwable) {
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
	@Value("${mongoString}")
	private String mongoString;
	@Autowired
	private DataService dataService;


	@Bean
	public MongoClient mongoClient() {
		LOGGER.info(vv + vv + vv + vv + " Setting up MongoClient Bean ... ");

		ConnectionString connectionString = new ConnectionString(mongoString);
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.build();

		MongoClient cl =  MongoClients.create(mongoClientSettings);
		MongoCursor<String> cur = cl.listDatabaseNames().cursor();
		LOGGER.info(vv + vv + vv + " MongoDB Atlas Database Listing ");
		while (cur.hasNext()) {
			String db = cur.next();
			LOGGER.info(vv + vv +" MongoDB Database: " + db);
		}
		LOGGER.info(vv + vv + vv + vv + " MongoClient set up OK! \uD83E\uDD6C \uD83E\uDD6C");
		return cl;
	}
	static final String vv = "\uD83D\uDC9C\uD83D\uDC9C";

}

