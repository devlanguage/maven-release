package org.basic.jdk.core.jdk.jdk7.e10_scriptengine;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

/**
 * <pre>
 *
 * </pre>
 */
public class ScriptEngineJS {
    static ScriptEngine engine = null;
    static {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("JavaScript");
        if (engine == null) {
            throw new RuntimeException("找不到JavaScript语言执行引擎。");
        }
    }

    private ScriptEngine getJavaScriptEngine() {
        return engine;
    }

    @Test
    public void invokeFunction() throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = getJavaScriptEngine();
        String scriptText = "function greet(name) { print('Hello, ' + name); } ";
        engine.eval(scriptText);
        Invocable invocable = (Invocable) engine;
        invocable.invokeFunction("greet", "Alex");
    }

    @Test
    public void greet() throws ScriptException {
        engine.eval("print('Hello!');");
    }

    static class ClassA {
        private ClassA() {
            System.out.println("empty");
        }

        private ClassA(String a) {
            System.out.println("string");
        }
    }

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        try {
            ClassA xdf = new ClassA();
            for (java.lang.reflect.Constructor method : xdf.getClass().getDeclaredConstructors()) {
                System.out.println(method);
                method.setAccessible(true);
                // System.out.println(method + "," + method.get(xdf));
                if(method.getParameterTypes().length==1){
                    method.newInstance("");
                }else{
                    method.newInstance(method.getParameterTypes());    
                }
                
                System.out.println(33);
            }

            ScriptEngineJS js = new ScriptEngineJS();
            for (Method method : js.getClass().getMethods()) {
                method.setAccessible(true);
                for (Annotation ant : method.getAnnotations()) {
                    if (ant.annotationType() == org.junit.Test.class) {
                        try {
                            method.invoke(js, null);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
