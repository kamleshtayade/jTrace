/**
 * 
 */
package com.spring.app.jms;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ktayade
 *
 */

public class ConsumerClient extends AbstractConsumerClient {
	
	private final Logger log = LoggerFactory.getLogger(ConsumerClient.class);
	
	public ConsumerClient(){

	}
	PropertiesValue properties = new PropertiesValue();
	Properties prop = new Properties();
	InputStream input = null;
	javax.jms.Connection connection = null;
	MessageConsumer consumer = null;
	TextMessage message = null;
	String brokerhost = null;
	String masterqueue = null;
	String childqueue = null;
	Queue queue = null;

	public void run() {

		while (!Thread.interrupted()) {
			
			Connection conn = DBConnPostGre.getConnection();

			prop = properties.gerPropertiesValue();
			
			QueueToPostGre queueToPostGre = new QueueToPostGre();
			TextMessage temp1 = jmsQueueSession(prop.getProperty("brokerhost"),
					prop.getProperty("masterqueue"));
			if(null!=temp1){
			queueToPostGre.insertToPostGreMs(conn, temp1);
			}
			
			TextMessage temp2 = jmsQueueSession(prop.getProperty("brokerhost"),
					prop.getProperty("childqueue"));
			if(null!=temp2){
				System.out.println("Calling Child Insert");
			queueToPostGre.insertToPostGreCh(conn, temp2);
			}
			
			// Stop the connection — good practice but redundant here
			try {
				connection.stop();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}try {
				Thread.sleep(5000); //Sleep for 1 min / 60 secs
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public TextMessage jmsQueueSession(String host, String queueName) {
		
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(prop.getProperty("brokerhost"));
		try {
			connection = factory.createConnection();
			connection.start();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			queue = session.createQueue(queueName);
			consumer = session.createConsumer(queue);
			message = (TextMessage) consumer.receive(1000);
			if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                
            } else {
                log.debug("Received: " + message);
            }
			consumer.close();
            session.close();
           			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
}
