package org.basic.pattern.creational.Prototype.test1;

/*
 * A Symbol Loader to register all prototype instance
 */
import java.util.Hashtable;

public class SymbolLoader {

    private Hashtable<String, IGraphic> symbols = new Hashtable<String, IGraphic>();

    public SymbolLoader() {

        symbols.put("Line", new LineSymbol());
        symbols.put("Note", new NoteSymbol());
    }

    public Hashtable getSymbols() {

        return symbols;
    }
}