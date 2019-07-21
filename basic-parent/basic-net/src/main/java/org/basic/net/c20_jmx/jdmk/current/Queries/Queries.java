package org.basic.net.c20_jmx.jdmk.current.Queries;
/*
 * @(#)file      Queries.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.6
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// JMX imports
//
import javax.management.Query;
import javax.management.QueryExp;
import javax.management.ValueExp;

/**
 * The Queries class shows how to build some simple JMX queries.
 *
 * More complex queries can be built easily by combining the appropriate
 * calls to the methods of the Query class as shown in the simple
 * examples described below. 
 */

public class Queries {

    /**
     * Static class: it makes no sense to instantiate it
     */
    private Queries() {
    }

    /**
     * Build some sample queries.
     */
    public static QueryExp query(int i) {

        switch(i) {

            // Find all objects whose value of attribute "Name" matches
	    // the string "get*"
	    //
        case 1 :
            return Query.match(Query.attr("Name"), Query.value("get*"));

            // Find all objects whose value of attribute "Name" appears
	    // alphabetically after "java" inclusive
	    //
        case 2 :
            return Query.geq(Query.attr("Name"), Query.value("java"));

            // Find all objects whose value of attribute "NbParams" is
	    // lower than 2 (in fact methods that have 0 or 1 parameter)
	    //
        case 3 :
            return Query.lt(Query.attr("NbParams"), Query.value(2));

            // Find all objects whose value of attribute "Static" is true
	    //
        case 4 :
            return Query.eq(Query.attr("Static"), Query.value(true));

            // Find all objects of class "MethodMB" whose attribute "Name"
	    // appears before "equals" inclusive
	    //
        case 5 :
            return Query.leq(Query.attr("MethodMB", "Name"),
			     Query.value("equals"));

            // Find all objects of class "ClassMB" whose value of attribute
	    // "Public" is true, i.e. find all public classes
	    //
        case 6 :
            return Query.eq(Query.attr("ClassMB", "Public"), Query.value(true));

            // Find all objects whose value of attribute "Params" is equal to 2
	    // and whose value of attribute "Name" is "wait", 
            // i.e. find all methods named "wait" with 2 parameters
	    //
        case 7 :
            return Query.and(Query.eq(Query.attr("Name"), Query.value("wait")),
                             Query.eq(Query.attr("NbParams"), Query.value(2)));

            // Find all objects whose value of attribute "ClassName" is equal
	    // to "java.lang.String" or "java.io.File", i.e. find all methods
	    // of the 2 classes "java.lang.String" and "java.io.File"
	    //
        case 8 :
            return Query.or(Query.eq(Query.attr("ClassName"),
				     Query.value("java.lang.String")),
                            Query.eq(Query.attr("ClassName"),
				     Query.value("java.io.File")));

            // Find all objects for which the statement:
	    // `the value of attribute "NbParams" is greater than 0' is false
            // i.e. all methods with no parameter
	    //
        case 9 :
            return Query.not(Query.gt(Query.attr("NbParams"), Query.value(0)));

            // Find all objects whose value of attribute "NbParams" is between
	    // 2 and 3 (in fact methods that have 2 or 3 parameters)
	    //
        case 10 :
            return Query.between(Query.attr("NbParams"),
				 Query.value(2), Query.value(3));

            // Find all objects whose value of attribute "Params" is "int" or
	    // "java.lang.String"
            // i.e. all methods with 1 parameter of type int or String
        case 11 :
            ValueExp[] valExps = new ValueExp[2];
            valExps[0] = Query.value("int");
            valExps[1] = Query.value("java.lang.String");
            return Query.in(Query.attr("Params"), valExps);

            // Find all objects whose value of attribute "Name" starts with "to"
	    //
        case 12 :
            return Query.initialSubString(Query.attr("Name"),
					  Query.value("to"));

            // Find all objects whose value of attribute "Name" ends with "Case"
	    //
        case 13 :
            return Query.finalSubString(Query.attr("Name"),
					Query.value("Case"));

            // Find all objects whose value of attribute "Name" contains "lang"
	    //
        case 14 :
            return Query.anySubString(Query.attr("Name"), Query.value("lang"));

            // Find all objects whose value of attribute "Name" starts with
	    // "to" and ends with "Case"
	    //
        case 15 :
            return Query.and(Query.initialSubString(Query.attr("Name"),
						    Query.value("to")),
                             Query.finalSubString(Query.attr("Name"),
						  Query.value("Case")));

        default :
            return null;
        }
    }
}
