module org.basic.common {
	requires java.xml;
	requires java.xml.bind;
	requires java.activation;
	requires java.base;
	requires java.sql;
	requires java.net.http;
	requires java.desktop;
	requires org.slf4j;
	requires org.apache.commons.compress;
	requires batik.util;
	exports org.basic.common;
}