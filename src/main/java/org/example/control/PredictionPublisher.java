package org.example.control;
import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.model.EventPublisher;
import org.example.model.Weather;

import javax.jms.*;

public class PredictionPublisher implements EventPublisher {
    public void publishEvent(Weather weather) throws MyExecutionException {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.160.104:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("prediction.weather");
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage();

            message.setText(jsonMessage(weather));
            producer.send(message);
            connection.close();

        } catch (JMSException e) {
            throw new MyExecutionException("Execution Error");
        }
    }

    private static String jsonMessage(Weather weather){
        Gson gson = new Gson();
        return gson.toJson(weather);
    }
}
