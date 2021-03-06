package test;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

/**
* Example from {@see http://mrhaki.blogspot.ru/2009/11/groovy-goodness-running-groovy-scripts.html}
**/
public class GroovyRun {
    public static void main(final String[] args) throws IllegalAccessException, InstantiationException, IOException, ResourceException, ScriptException {
        // Create GroovyClassLoader.
        final GroovyClassLoader classLoader = new GroovyClassLoader();

        // Create a String with Groovy code.
        final StringBuilder groovyScript = new StringBuilder();
        groovyScript.append("class Sample {");
        groovyScript.append("  String sayIt(name) { \"Groovy says: Cool $name!\" }");
        groovyScript.append("}");

        // Load string as Groovy script class.
        Class groovy = classLoader.parseClass(groovyScript.toString());
        GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
        String output = (String)groovyObj.invokeMethod("sayIt", new Object[] { "mrhaki" });
        assert "Groovy says: Cool mrhaki!".equals(output);

        // Load Groovy Class file.
        groovy = classLoader.parseClass(new File("SampleClass.groovy"));
        groovyObj = (GroovyObject) groovy.newInstance();
        output = (String)groovyObj.invokeMethod("scriptSays", new Object[] { "mrhaki", new Integer(2) });
        System.out.println("Groovy class output: " + output);
        assert "Hello mrhaki, from Groovy. Hello mrhaki, from Groovy".equals(output);

        // Load Groovy Script file.
        GroovyScriptEngine engine = new GroovyScriptEngine(".");
        output = (String)engine.run("SampleScript.groovy", new Binding(new HashMap(){{
            put("name", "mrhaki");
            put("num", 3);
        }}));

        System.out.println("Groovy Script output: " + output);
        assert "Hello mrhaki, from Groovy. Hello mrhaki, from Groovy".equals(output);
    }
}
