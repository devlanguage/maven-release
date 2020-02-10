package org.basic.io.json.jackson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSON.Feature;

/**
 * Stand-alone data-binding module designed as a light-weight (and -featured)
 * alternative to `jackson-databind`: will only deal with "Maps, Lists, Strings,
 * wrappers and Java Beans" (jr-objects), or simple read-only trees (jr-stree)
 * 
 *
 */
class MyBean {
    private String name;

}

class MyType {

}

public class JacksonJRTest {
    public static void main(String[] args) throws Exception{
     
    }
    public static void jsonWrite() throws Exception {

        String INPUT = "{\"a\":[1,2,{\"b\":true},3],\"c\":3}";
        Object ob = JSON.std.anyFrom(INPUT);
        // or
        Map<String, Object> map = JSON.std.mapFrom(INPUT);
        // or
        MyBean bean = JSON.std.beanFrom(MyBean.class, INPUT);

        String json = JSON.std.asString(map);
        JSON.std.write(ob, new File("/tmp/stuff.json"));
        // and with indentation; but skip writing of null properties
        byte[] bytes = JSON.std.with(Feature.PRETTY_PRINT_OUTPUT).without(Feature.WRITE_NULL_PROPERTIES).asBytes(bean);

        List<MyType> beans = JSON.std.listOfFrom(MyType.class, INPUT);

    }

    public static void jsonReader(String[] args) throws Exception, JsonProcessingException, IOException {

        String json = JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT).without(JSON.Feature.FAIL_ON_DUPLICATE_MAP_KEYS)
                .composeString().startObject().put("a", 1).startArrayField("arr").add(1).add(2).add(3).end()
                .startObjectField("ob").put("x", 3).put("y", 4).startArrayField("args").add("none").end().end()
                .put("last", true).end().finish();
        System.out.println(json);

    }
}
