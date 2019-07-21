package org.third.message.activemq;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.basic.common.bean.BooleanLock;
import org.basic.common.bean.CommonLogger;
import org.basic.common.util.StringUtils;

/**
 * Created on Mar 10, 2014, 4:40:51 PM
 */
public class MessageUtil implements Runnable {
	public enum JmsServerType {
		IMQ, //
		ACTIVE_MQ// http://vesna3:8161/
	}

	static {
		Runtime.getRuntime().addShutdownHook(new Thread(new MessageUtil()));
	}
	static final CommonLogger logger = CommonLogger.getLog(MessageUtil.class);
	// public static final String imq_host = ""; //
	// ConnectionConfiguration.imqBrokerHostName,
	// ConnectionConfiguration.imqBrokerHostPort
	public static final String imq_connection_type = "NORMAL";// ADMIN
	public static final String imq_broker_url = "mq://vesna3:7676";// mq://localhost:7676,mq://localhost:7677,mq://localhost:7678
																	// //
																	// ConnectionConfiguration.imqAddressList
//	public static final String imq_user = ConnectionConfiguration.imqDefaultUsername; // admin
//	public static final String imq_password = ConnectionConfiguration.imqDefaultPassword;// admin

	public static final String activemq_host = "";
	public static final String activmq_broker_url = "tcp://vesna3:61616";// ActiveMQConnection.DEFAULT_BROKER_URL;;
	public static final String activmq_user = ActiveMQConnection.DEFAULT_USER; // system
	public static final String activmq_password = ActiveMQConnection.DEFAULT_PASSWORD; // manager

	ConnectionFactory imqConnFactory;
	org.apache.activemq.ActiveMQConnectionFactory activeMqConnectionFactory;
	java.util.Queue<javax.jms.Connection> connections = new ArrayBlockingQueue(100);
	javax.jms.Connection imqConnection;
	javax.jms.Connection activeMqConnection;

	AtomicBoolean uninitialized_imq = new AtomicBoolean(true);
	AtomicBoolean uninitialized_active_mq = new AtomicBoolean(true);

	private static final MessageUtil instance = new MessageUtil();

	public static final MessageUtil getInstance() {

		return instance;
	}

	public javax.jms.Connection getConnection(JmsServerType jmsType, boolean durable) throws JMSException {
		return getConnection(jmsType, durable, null);
	}

	/**
	 * @param imq
	 * @param b
	 * @param string
	 * @return
	 * @throws JMSException
	 */
	public Connection getConnection(JmsServerType jmsType, boolean durable, String clientID) throws JMSException {
		initJmsConnectionFactory(jmsType);
		javax.jms.Connection jmsConn = null;
		jmsConn = jmsType == JmsServerType.IMQ ? imqConnFactory.createConnection()
				: activeMqConnectionFactory.createConnection();
		// 如果你要使用DurableSubScription 方式,你必须为connection设置一个ClientID

		if (durable) {
			if (!StringUtils.hasText(clientID)) {
				clientID = "ClientID_" + java.util.UUID.randomUUID().toString().replaceAll("-", "_");

			}
			jmsConn.setClientID(clientID);
		}
		logger.info(toString(jmsConn));
		connections.add(jmsConn);
		jmsConn.start();
		return jmsConn;
	}

	private void initJmsConnectionFactory(JmsServerType jmsServerType) throws JMSException {
		switch (jmsServerType) {
		case ACTIVE_MQ:
			if (uninitialized_active_mq.get()) {
				initActiveMqConnection();
				uninitialized_active_mq.set(false);
			}
		default:
			if (uninitialized_imq.get()) {
//				initImqConnection();
				uninitialized_imq.set(false);
			}
		}
	}

	public javax.jms.Session createSession(JmsServerType jmsType, boolean transaction, int transactionMode)
			throws JMSException {
		return getSession(jmsType, transaction, transactionMode);
	}

	public javax.jms.Session getSession(JmsServerType jmsType, boolean transaction, int transactionMode)
			throws JMSException {
		initJmsConnectionFactory(jmsType);
		if (jmsType == JmsServerType.ACTIVE_MQ) {
			activeMqConnection = activeMqConnectionFactory.createConnection();
			activeMqConnection.setExceptionListener(new ExceptionListener() {
				public void onException(JMSException exception) { // try
																	// reconnect
					System.err.println("JMS exception" + exception);
				}
			});
			// activeMqConnection.setClientID("Active_MQ_TEST");
			logger.info(toString(activeMqConnection));

			activeMqConnection.start();
			connections.add(activeMqConnection);
		} else {
			imqConnection = imqConnFactory.createConnection();
			imqConnection.setExceptionListener(new ExceptionListener() {

				public void onException(JMSException exception) {

					System.err.println("JMS exception" + exception);

				}
			});
			logger.info(toString(imqConnection));
			imqConnection.start();
			connections.add(imqConnection);

		}
		return jmsType == JmsServerType.ACTIVE_MQ ? activeMqConnection.createSession(transaction, transactionMode)
				: imqConnection.createSession(transaction, transactionMode);
	}

	/**
	 * @param activeMqConnection2
	 * @return
	 * @throws JMSException
	 */
	private String toString(Connection jmsConnection) throws JMSException {
		StringBuffer sb = new StringBuffer();
		sb.append("ClientID: " + jmsConnection.getClientID());
		sb.append(toString(jmsConnection.getMetaData()));
		return sb.toString();

	}

	/**
	 * 
	 */

//	private void initImqConnection() throws JMSException {
//		// String CONNECTIONTYPE_TYPE_NORMAL = "NORMAL";
//		// String CONNECTIONTYPE_TYPE_ADMIN = "ADMIN";
//		//
//		// String CONNECTIONTYPE_ADMINKEY = "ADMINKEY";
//		// String TEMPORARY_DESTINATION_URI_PREFIX = "temporary_destination://";
//		// String TEMPORARY_QUEUE_URI_NAME = "queue/";
//		// String TEMPORARY_TOPIC_URI_NAME = "topic/";
//		// int DESTINATION_TYPE_UNKNOWN = 0;
//		// int DESTINATION_TYPE_QUEUE = 1;
//		// int DESTINATION_TYPE_TOPIC = 2;
//
//		imqConnFactory = new ConnectionFactory();
//		imqConnFactory.setConnectionType(imq_connection_type);
//		imqConnFactory.setProperty(ConnectionConfiguration.imqAddressList, imq_broker_url);
//		imqConnFactory.setProperty(com.sun.messaging.ConnectionConfiguration.imqDefaultUsername, imq_user);
//		imqConnFactory.setProperty(ConnectionConfiguration.imqDefaultPassword, imq_password);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString(javax.jms.ConnectionMetaData metaData) throws JMSException {

		return "ConnectionMetaData: [Name=" + metaData.getJMSProviderName() + "],ProviderVersion="
				+ metaData.getProviderVersion() + "[ProviderMinorVersion=" + metaData.getProviderMinorVersion()
				+ "[ProviderMinorVersion=" + metaData.getProviderMinorVersion() + "[JmsVersion="
				+ metaData.getJMSVersion() + ",JmsMajorVersion=" + metaData.getJMSMajorVersion() + ",JmsMinorVersion="
				+ metaData.getJMSMinorVersion() + "]";
	}

	public javax.jms.Session getSession(JmsServerType jmsType) throws JMSException {
		return getSession(jmsType, false, javax.jms.Session.AUTO_ACKNOWLEDGE);
	}

	public final static String SERVER = "localhost";

	/**
	 * <pre>
	 *  常见的有
	 *  vm://host:port     //vm 
	 *       Client connect to each other within the same JVM. This does use an asynchronous channel and a separate worker thread. You can enable sync sending using a query parameter, such as
	 *       vm://localhost?async=false
	 *  tcp://host:port    //tcp
	 *  ssl://host:port    //SSL
	 *  stomp://host:port  //stomp协议可以跨语言,目前有很多种stomp client 库(java,c#,c/c++,ruby,python...);
	 *  http://host:port    //Client connects to the broker using HTTP tunnelling, with XML payloads suitable for going through firewalls
	 * 
	 * </pre>
	 * 
	 * @throws JMSException
	 */
	private void initActiveMqConnection() throws JMSException {

		String user = activmq_user;
		String password = activmq_password;
		String url = activmq_broker_url;// ActiveMQConnection.DEFAULT_BROKER_URL;
										// // tcp://localhost:61616
		// 设置用户名和密码，这个用户名和密码在conf目录下的credentials.properties文件中，也可以在activemq.xml中配置
		// connectionFactory.setUserName("system");
		// connectionFactory.setPassword("manager");
		// 创建连接
		activeMqConnectionFactory = new ActiveMQConnectionFactory(user, password, url);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		closeQuitely(connections);

	}

	private void closeQuitely(java.util.Queue<Connection> connections2) {
		connections2.forEach(c -> {
			try {
				c.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		});
	}

	public static final String getMessageType(Message m) {

		if (m instanceof TextMessage) {
			return "TextMessage";
		} else if (m instanceof BytesMessage) {
			return "BytesMessage";
		} else if (m instanceof MapMessage) {
			return "MapMessage";
		} else if (m instanceof ObjectMessage) {
			return "ObjectMessage";
		} else if (m instanceof StreamMessage) {
			return "StreamMessage";
		} else if (m instanceof Message) {
			return "Message";
		} else {
			// Unknown Message type
			String type = m.getClass().getName();
			StringTokenizer st = new StringTokenizer(type, ".");
			String s = null;
			while (st.hasMoreElements()) {
				s = st.nextToken();
			}
			return s;
		}
	}

	/**
	 * Takes a buffer of bytes and returns a hex dump. Each hex digit represents 4
	 * bits. The hex digits are formatted into groups of 4 (2 bytes, 16 bits). Each
	 * line has 8 groups, so each line represents 128 bits.
	 */
	public static String[] pad = { "", "0", "00", "000", "0000" };

	private static String toHexDump(byte[] buf, int length) {

		// Buffer must be an even length
		if (buf.length % 2 != 0) {
			throw new IllegalArgumentException();
		}

		int value;
		StringBuffer sb = new StringBuffer(buf.length * 2);

		/*
		 * Assume buf is in network byte order (most significant byte is buf[0]).
		 * Convert two byte pairs to a short, then display as a hex string.
		 */
		int n = 0;
		while (n < buf.length && n < length) {
			value = buf[n + 1] & 0xFF; // Lower byte
			value |= (buf[n] << 8) & 0xFF00; // Upper byte
			String s = Integer.toHexString(value);
			// Left bad with 0's
			sb.append(pad[4 - s.length()]);
			sb.append(s);
			n += 2;

			if (n % 16 == 0) {
				sb.append("\n");
			} else {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	/**
	 * Return a string representation of a JMS message body
	 */
	public static String toString(Message m) {

		if (m instanceof TextMessage) {
			try {
				return ((TextMessage) m).getText();
			} catch (JMSException ex) {
				return ex.toString();
			}
		} else if (m instanceof BytesMessage) {

			byte[] body = new byte[1024];
			int n = 0;

			try {
				((BytesMessage) m).reset();
				n = ((BytesMessage) m).readBytes(body);
			} catch (JMSException ex) {
				return (ex.toString());
			}

			if (n <= 0) {
				return "<empty body>";
			} else {
				return (toHexDump(body, n) + ((n >= body.length) ? "\n. . ." : ""));
			}

		} else if (m instanceof MapMessage) {
			MapMessage msg = (MapMessage) m;
			HashMap props = new HashMap();
			// Get all MapMessage properties and stuff into a hash table
			try {
				for (Enumeration enu = msg.getMapNames(); enu.hasMoreElements();) {
					String name = (enu.nextElement()).toString();
					props.put(name, (msg.getObject(name)).toString());
				}
				return props.toString();
			} catch (JMSException ex) {
				return (ex.toString());
			}
		} else if (m instanceof ObjectMessage) {
			ObjectMessage msg = (ObjectMessage) m;
			Object obj = null;
			try {
				obj = msg.getObject();
				if (obj != null) {
					return obj.toString();
				} else {
					return "null";
				}
			} catch (Exception ex) {
				return (ex.toString());
			}
		} else if (m instanceof StreamMessage) {

			byte[] body = new byte[1024];
			int n = 0;

			try {
				((StreamMessage) m).reset();
				n = ((StreamMessage) m).readBytes(body);
			} catch (JMSException ex) {
				return (ex.toString());
			}

			if (n <= 0) {
				return "<empty body>";
			} else {
				return (toHexDump(body, n) + ((n >= body.length) ? "\n. . ." : ""));
			}
		} else if (m instanceof Message) {
			return "Can't get body for message of type Message";
		}
		return "Unknown message type " + m;
	}

	/**
	 * Takes the JMS header fields of a JMS message and puts them in a HashMap
	 */
	public static HashMap getHashMapFromJmsHeader(Message m) throws JMSException {

		HashMap hdrs = new HashMap();
		String s = null;

		s = m.getJMSCorrelationID();
		hdrs.put("JMSCorrelationID", s);

		s = String.valueOf(m.getJMSDeliveryMode());
		hdrs.put("JMSDeliverMode", s);

		Destination d = m.getJMSDestination();
		if (d != null) {
			if (d instanceof Queue) {
				s = ((Queue) d).getQueueName();
			} else {
				s = ((Topic) d).getTopicName();
			}
		} else {
			s = "";
		}
		hdrs.put("JMSDestination", s);

		s = String.valueOf(m.getJMSExpiration());
		hdrs.put("JMSExpiration", s);

		s = m.getJMSMessageID();
		hdrs.put("JMSMessageID", s);

		s = String.valueOf(m.getJMSPriority());
		hdrs.put("JMSPriority", s);

		s = String.valueOf(m.getJMSRedelivered());
		hdrs.put("JMSRedelivered", s);

		d = m.getJMSDestination();
		if (d != null) {
			if (d instanceof Queue) {
				s = ((Queue) d).getQueueName();
			} else {
				s = ((Topic) d).getTopicName();
			}
		} else {
			s = "";
		}
		hdrs.put("JMSReplyTo", s);

		s = String.valueOf(m.getJMSTimestamp());
		hdrs.put("JMSTimestamp", s);

		s = m.getJMSType();
		hdrs.put("JMSType", s);

		return hdrs;
	}

	// static {
	// try {
	// connectJmsServer();
	// } catch (JMSException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			final QueueSession qs1 = getQueueSession();
			Queue q1 = createJmsQueue("yongjie1");

			final MessageProducer myMsgProducer = qs1.createProducer(q1);
			new Thread(new Runnable() {

				public void run() {

					TextMessage myTextMsg;
					int countOfMessageSent = 0;
					while (countOfMessageSent < 10) {
						try {
							myTextMsg = qs1.createTextMessage();
							myTextMsg.setText("Hello World");
							System.out.println("Sending Message: " + myTextMsg.getText());
							myMsgProducer.send(myTextMsg);
							Thread.sleep(3000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();

			final MessageConsumer myMsgConsumer = qs1.createConsumer(q1);
			new Thread(new Runnable() {

				public void run() {

					while (true) {
						try {
							// Step 10:
							// Receive a message from the queue.
							Message msg = myMsgConsumer.receive();

							// Step 11:
							// Retreive the contents of the message.
							if (msg instanceof TextMessage) {
								TextMessage txtMsg = (TextMessage) msg;
								System.out.println("Read Message: " + txtMsg.getText());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	static class JMSExceptionListener implements javax.jms.ExceptionListener {

		public BooleanLock reconnectFlag;
		public BooleanLock jmsFlag;

		/**
		 * @return get method for the field jmsFlag
		 */
		public BooleanLock getJmsFlag() {

			return this.jmsFlag;
		}

		/**
		 * @param jmsFlag the jmsFlag to set
		 */
		public void setJmsFlag(BooleanLock jmsFlag) {

			this.jmsFlag = jmsFlag;
		}

		/**
		 * @return get method for the field reconnectFlag
		 */
		public BooleanLock getReconnectFlag() {

			return this.reconnectFlag;
		}

		/**
		 * @param reconnectFlag the reconnectFlag to set
		 */
		public void setReconnectFlag(BooleanLock reconnectFlag) {

			this.reconnectFlag = reconnectFlag;
		}

		public void onException(JMSException e) {

			if (reconnectFlag.isFalse()) {
				jmsFlag.setValue(false);
				reconnectFlag.setValue(true);
				MessageUtil.reconnectJmsServer();
			}
		}

	}

	final static JMSExceptionListener listener = new JMSExceptionListener();
	protected static final int reconnectInterval = 2;

	private static QueueConnection queueConnection = null;
	private static TopicConnection topicConnection = null;

	private static QueueSession queueSession = null;
	private static TopicSession topicSession = null;

	public final static Queue createJmsQueue(String queueName) throws JMSException {

		if (queueSession == null) {
			queueSession = getQueueSession();
		}
		return queueSession.createQueue(queueName);
	}

	public final static Topic createJmsTopic(String topicName) throws JMSException {

		if (topicSession == null) {
			topicSession = getTopicSession();
		}
		return topicSession.createTopic(topicName);
	}

	public final static QueueSession getQueueSession() throws JMSException {

		if (queueConnection == null) {
			connectJmsServer();
		}
		return queueConnection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
	}

	public final static TopicSession getTopicSession() throws JMSException {

		if (queueConnection == null) {
			connectJmsServer();
		}
		return topicConnection.createTopicSession(false, Session.CLIENT_ACKNOWLEDGE);
	}

	final static void connectJmsServer() throws JMSException {

//		com.sun.messaging.QueueConnectionFactory queueFactory = new com.sun.messaging.QueueConnectionFactory();
//		queueFactory.setProperty(ConnectionConfiguration.imqAddressList, imq_broker_url);
//		// queueFactory.setProperty("imqBrokerHostPort", imq_host.getImqPort());
//
//		com.sun.messaging.TopicConnectionFactory topicFactory = new com.sun.messaging.TopicConnectionFactory();
//		topicFactory.setProperty(ConnectionConfiguration.imqAddressList, imq_broker_url);
//		// topicFactory.setProperty("imqBrokerHostPort",
//		// ImqConfiguration.getImqPort());
//
//		queueConnection = queueFactory.createQueueConnection();
//		topicConnection = topicFactory.createTopicConnection();
//		// Start the connection in order to MessageConsumer can receive the
//		// messages.
//		queueConnection.start();
//		queueConnection.setExceptionListener(listener);
//		topicConnection.setExceptionListener(listener);
	}

	public final static void reconnectJmsServer() {

		Thread thread = new Thread(new Runnable() {

			public void run() {

				while (listener.getJmsFlag().isFalse()) {
					try {
						connectJmsServer();
						startBrokerConnections();
						listener.getJmsFlag().setValue(true);
						listener.getReconnectFlag().setValue(false);
						System.out.println("Successful to reconnect with jms server.");
						// resetNbiQueue();
						// resetNbiTopic();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							Thread.sleep(reconnectInterval * 1000);
						} catch (Exception e) {

						}
					}
				}
			}

			private void startBrokerConnections() {

			}
		});
		thread.start();
	}

}
