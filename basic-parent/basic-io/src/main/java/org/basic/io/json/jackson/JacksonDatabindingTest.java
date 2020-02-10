package org.basic.io.json.jackson;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;

@JsonDeserialize(using=JsonUserDeserializer.class)
class JsonUser{
    private String name;
    private int age;
    private String icon;
    private List<String> messages;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public List<String> getMessages() {
        return messages;
    }
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
public class JacksonDatabindingTest {
    public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
        com.fasterxml.jackson.databind.ObjectMapper  om = new ObjectMapper();
        String jsonData = "{"
                + "\"name\":\"mkyong\","
                + "\"age\":29,"
                + "\"icon\":\"bmV3\","
                + "\"messages\":["
                + "  \"msg 1\",\"msg 2\",\"msg 3\""
                +   "]"
                + "}";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule()); //new JaxbAnnotationModule()
        com.fasterxml.jackson.databind.JsonNode jsonNode = objectMapper.readTree(jsonData);
        System.out.println(jsonNode.get("name").asText());
//        jsonReader(args);
        JsonUser u1=   objectMapper.readValue(jsonData, JsonUser.class);
        System.out.println(u1);
        
        
    }

    public static void jsonWrite() {

        JsonFactoryBuilder jfb = new JsonFactoryBuilder();
        jfb.configure(JsonWriteFeature.WRITE_NAN_AS_STRINGS, true);
        JsonFactory jf = jfb.build();
        jf.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, true);
        try {
            JsonGenerator jGenerator = jf.createGenerator(System.out);
            jGenerator.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.IGNORE_UNKNOWN, true);

            jGenerator.writeStartObject();

            jGenerator.writeStringField("name", "mkyong"); // "name" : "mkyong"
            jGenerator.writeNumberField("age", 29); // "age" : 29

            jGenerator.writeFieldName("icon");
            jGenerator.writeBinary("new".getBytes());

            jGenerator.writeFieldName("messages"); // "messages" :
            jGenerator.writeStartArray(); // [

            jGenerator.writeString("msg 1"); // "msg 1"
            jGenerator.writeString("msg 2"); // "msg 2"
            jGenerator.writeString("msg 3"); // "msg 3"

            jGenerator.writeEndArray(); // ]

            jGenerator.writeEndObject();
            jGenerator.flush();
            jGenerator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void jsonReader(String[] args) {

        String jsonData = "{"
                + "\"name\":\"mkyong\","
                + "\"age\":29,"
                + "\"icon\":\"bmV3\","
                + "\"messages\":["
                + "  \"msg 1\",\"msg 2\",\"msg 3\""
                +   "]"
                + "}";
        try {
            JsonFactory jfactory = new JsonFactory();
            JsonParser jParser = jfactory.createParser(jsonData);

            // loop until token equal to "}"
            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldname = jParser.getCurrentName();
                if ("name".equals(fieldname)) {
                    // 当前结点为name
                    jParser.nextToken();
                    System.out.println(jParser.getText()); // 输出 mkyong
                }

                if ("age".equals(fieldname)) {
                    // 当前结点为age
                    // move to next, which is "name"'s value
                    jParser.nextToken();
                    System.out.println(jParser.getIntValue()); // display 29
                }
                if ("messages".equals(fieldname)) {

                    jParser.nextToken();
                    while (jParser.nextToken() != JsonToken.END_ARRAY) {
                        // display msg1, msg2, msg3
                        System.out.println(jParser.getText());
                    }
                }
            }
            jParser.close();

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
