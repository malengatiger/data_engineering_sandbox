package com.boha.sandbox5.services;

import com.boha.sandbox5.Sandbox5Application;
import com.boha.sandbox5.models.VehicleLocation;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.TopicName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

/* message schema ....
 {
  "associationId": "ass1",
  "vehicleId": "vehicle1",
  "date": "20220101",
  "latitude": -27.7363736,
  "longitude": 25.43545
}
 */
@Service
public class PubSubMessagingService {
    public static final Logger LOGGER = Logger.getLogger(PubSubMessagingService.class.getName());

    @Value("${vehicleLocation.topic}")
    private String vehicleLocationTopic;

    @Value("${vehicleArrival.topic}")
    private String vehicleArrivalTopic;

    @Value("${vehicleDeparture.topic}")
    private String vehicleDepartureTopic;

    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    public void publish(VehicleLocation vehicleLocation)
            throws IOException, InterruptedException {
        TopicName topicName = TopicName.of(projectId, vehicleLocationTopic);
        Publisher publisher = null;

        try {
            // Create a publisher instance with default settings bound to the topic
            publisher = Publisher.newBuilder(topicName).build();


        } catch (Exception r) {

        }
    }
}
