package org.basic.pattern.creational.Prototype.test1;

/*
 * As a Test Client to test our pattern
 */
import java.util.Hashtable;

public class GraphicTool {

    public GraphicTool() {

    }

    public static void main(String[] args) {
int i="1063,1063411010101027,1063411011301027,10631063OCH-L-1-1-1-271063OCH-P-2-4-3&10631063OCH-L-1-13-1-271063OCH-P-2-4-5,COMPOSITE,2WAY,OEO REGEN,T247,A,D,OCH-L-1-1-1-27,OCH-L-1-13-1-27,HDTG-2-4,HDTG-2-4,3,5".split(",").length;
System.out.println(i);
        
        // ----- Initial our prototype instance ----------
        SymbolLoader myLoader = new SymbolLoader();
        Hashtable mySymbols = myLoader.getSymbols();

        // ----- Draw a Line -------------------------------
        Graphic myLine = (Graphic) ((Graphic) mySymbols.get("Line")).clone();
        myLine.doSomething();
    }
}