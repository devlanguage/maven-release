package org.basic.net.c20_jmx.jdmk.current.OpenMBean2;
/*
 * @(#)file      CarOpenMBean.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.5
 * @(#)lastedit  04/02/20
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.io.*;
import java.util.*;

// JMX imports
//
import javax.management.*;
import javax.management.openmbean.*;

/**
 * The data structure of this Open MBean models a concrete example that could be
 * used in car sales applications. The goal is to describe a set of objects that
 * will represent a list of cars available for sale. Basically, we manage a
 * table of car objects setOfCars. A car is described by its maker, its model,
 * its color and its price. A color is defined by its 3 basic colors (red,
 * green and blue). nbSoldCars is an attribute indicating the total number of
 * cars sold, nbAvailableCars is an attribute indicating the total number of
 * cars available in the store, mostUsedColorsData is a CompositeData attribute
 * which represents the most used colors and finally isStoreOpen is an attribute
 * indicating if the store is open or not.
 */
public class CarOpenMBean implements DynamicMBean {

    // Open MBean Info
    //
    private OpenMBeanInfoSupport CarOpenMBeanInfo;

    // Attributes exposed for management
    //
    Long                nbSoldCars;
    Short               nbAvailableCars;
    Boolean             isStoreOpen;
    TabularDataSupport  setOfCars;
    CompositeData       mostUsedColors;

    // Custom open types (and related info) used by this Open MBean class
    //

    // CompositeData which defines an RGB color
    //
    private static String[] compositeTypeColorItemsName = {
        "red",
        "green",
        "blue"
    };
    private static String[] compositeTypeColorItemsDescription = {
        "Red component of the RGB color",
        "Green component of the RGB color",
        "Blue component of the RGB color"
    };
    private static OpenType[] compositeTypeColorItemsType = {
        SimpleType.INTEGER,
        SimpleType.INTEGER,
        SimpleType.INTEGER
    };
    protected static CompositeType compositeTypeColor;

    // CompositeData which defines a Car
    //
    private static String[] compositeTypeCarItemsName = {
        "maker",
        "model",
        "colors",
        "price"
    };
    private static String[] compositeTypeCarItemsDescription = {
        "Maker of the car",
        "Model of the car",
        "Set of available colors for a car in RGB format",
        "Price of a car"
    };
    private static OpenType[] compositeTypeCarItemsType;
    protected static CompositeType compositeTypeCar;

    // TabularData which describes a set of colors
    //
    protected static TabularType tabularTypeListOfColors;
    protected static String[] tabularTypeListOfColorsIndex = {
        "red", "green", "blue"
    }; // the list of items that make the key for accessing rows

    // TabularData which describes a set of cars
    //
    protected static TabularType tabularTypeListOfCars;
    private static String[] tabularTypeListOfCarsIndex = {
        "maker", "model"
    }; // the list of items that make the key for accessing rows

    // Initialization of OpenType instances
    //
    static {
        try {
            // initialize the CompositeType compositeTypeColor
            compositeTypeColor =
                new CompositeType("color",
                                  "an RGB color",
                                  compositeTypeColorItemsName,
                                  compositeTypeColorItemsDescription,
                                  compositeTypeColorItemsType);

            // initialize the TabularType tabularTypeListOfColors
            tabularTypeListOfColors =
                new TabularType("colors",
                                "list of colors available for a car",
                                compositeTypeColor,
                                tabularTypeListOfColorsIndex);

            // initialize the CompositeType compositeTypeCar
            compositeTypeCarItemsType = new OpenType[4];
            compositeTypeCarItemsType[0] = SimpleType.STRING;
            compositeTypeCarItemsType[1] = SimpleType.STRING;
            compositeTypeCarItemsType[2] = tabularTypeListOfColors;
            compositeTypeCarItemsType[3] = SimpleType.FLOAT;

            compositeTypeCar =
                new CompositeType("car",
                                  "a car defined by its maker, " +
                                  "model, color and price",
                                  compositeTypeCarItemsName,
                                  compositeTypeCarItemsDescription,
                                  compositeTypeCarItemsType);

            // initialize the TabularType tabularTypeListOfCars
            tabularTypeListOfCars =
                new TabularType("cars",
                                "list of cars",
                                compositeTypeCar,
                                tabularTypeListOfCarsIndex);
        } catch (OpenDataException e) {
            // should not happen
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            PrintWriter pout = new PrintWriter(bout);
            e.printStackTrace(pout);
            pout.flush();
            throw new RuntimeException(bout.toString());
        }
    }

    /**
     * Constructs a new CarOpenMBean instance.
     */
    public CarOpenMBean() throws OpenDataException {

        // build information for the Open MBean
        buildMBeanInfo();

        // we create an empty list of cars
        setOfCars = new TabularDataSupport(tabularTypeListOfCars);

        // initialization of mostUsedColors
        Object[] colorItemValues = {
            new Integer(255),
            new Integer(255),
            new Integer(255)
        };
        mostUsedColors = new CompositeDataSupport(compositeTypeColor,
                                                  compositeTypeColorItemsName,
                                                  colorItemValues);

        // creation of a new car
        TabularData listOfClioColors =
            new TabularDataSupport(tabularTypeListOfColors);
        try {
            listOfClioColors.put(mostUsedColors);
        } catch (KeyAlreadyExistsException e) {
        }
        Object[] carItemValues = {
            "Renault",
            "Clio",
            listOfClioColors,
            new Float(10000.0)
        };
        CompositeData firstCar =
            new CompositeDataSupport(compositeTypeCar,
                                     compositeTypeCarItemsName,
                                     carItemValues);
        try {
            setOfCars.put(firstCar);
        } catch (KeyAlreadyExistsException e) {
        }

        // initialization of isStoreOpen
        isStoreOpen = Boolean.TRUE;

        // initialization of nbAvailableCars
        nbAvailableCars = new Short((short) 1);

        // initialization of nbSoldCars
        nbSoldCars = new Long(25);
    }

    // setters
    //

    private void setNbSoldCars(Long l) {
        nbSoldCars = l;
    }

    // getters
    //
  
    private Long getNbSoldCars() {
        return nbSoldCars;
    }
    private Short getNbAvailableCars() {
        return nbAvailableCars;
    }
    private Boolean getIsStoreOpen() {
        return isStoreOpen;
    }
    private TabularData getSetOfCars() {
        return (TabularData) setOfCars.clone();
    }
    private CompositeData getMostUsedColors() {
        return mostUsedColors;
    }

    // Operations
    //

    /**
     * Returns a TabularData of type tabularTypeListOfCars representing
     * cars of setOfCars whose price is lower than or equal to price.
     */
    private TabularData carCheaperThan(Float price) {
        Iterator carIterator;
        TabularData carCheaperThanList = (TabularData) setOfCars.clone();
        TabularData newList = new TabularDataSupport(tabularTypeListOfCars);
        Collection carCollection = carCheaperThanList.values();
        for (carIterator = carCollection.iterator(); carIterator.hasNext(); ) {
            Object elem = carIterator.next();
            CompositeData myCar = (CompositeData) elem;
            Object[] key = new Object[2];
            Iterator it = myCar.values().iterator();
            Object carElem = it.next();  // we skip the colors item
            carElem = it.next();         // we skip the maker item
            key[0] = (String) carElem;
            carElem = it.next();         // we skip the model item
            key[0] = (String) carElem;
            carElem = it.next();         // so we get the price
            Float carPrice = (Float) carElem;
            if (carPrice.compareTo(price) <= 0) {
                try {
                    newList.put((CompositeData) myCar);
                } catch (KeyAlreadyExistsException e) {
                    System.out.println("ther e is an exception!!!");
                    e.printStackTrace();
                }
            } else {
                CompositeData toto = carCheaperThanList.remove(key);
            }
        }
        return newList;
    }

    /**
     * Adds the CompositeData car to the setOfCars TabularData object and
     * increments the nbAvailableCars attribute.
     */
    private Boolean addCar(CompositeData car) {
        try {
            setOfCars.put(car);
            nbAvailableCars =
                new Short((short) (nbAvailableCars.shortValue() + 1));
            return Boolean.TRUE;
        } catch (KeyAlreadyExistsException e) {
            return Boolean.FALSE;
        }
    }

    /**
     * addCar: add a new car to the TabularData representing the list of
     * available cars all data representing the car are given in parameters
     * to the operation and the car Composite Data is then built.
     */
    private Boolean addCar(String maker, String model, Integer red,
                           Integer green, Integer blue, Float price)
        throws OpenDataException {

        // initialization of a color
        Object[] colorItemValues = new Object[3];
        colorItemValues[0] = red;
        colorItemValues[1] = green;
        colorItemValues[2] = blue;
        CompositeData newColor =
            new CompositeDataSupport(compositeTypeColor,
                                     compositeTypeColorItemsName,
                                     colorItemValues);

        // add this color to a TabularData representing the list of colors
        TabularData listOfCarColors =
            new TabularDataSupport(tabularTypeListOfColors);
        try {
            listOfCarColors.put(newColor);
        } catch (KeyAlreadyExistsException e) {
            System.out.println("Error this color already exists in the " +
                               "listOfColors for this car");
            return Boolean.FALSE;
        }

        // creation of a new car
        Object[] carItemValues = new Object[4];
        carItemValues[0] = maker;
        carItemValues[1] = model;
        carItemValues[2] = listOfCarColors;
        carItemValues[3] = price;

        CompositeData newCar =
            new CompositeDataSupport(compositeTypeCar,
                                     compositeTypeCarItemsName,
                                     carItemValues);
        try {
            setOfCars.put(newCar);
        } catch (KeyAlreadyExistsException e) {
            System.out.println("Error this car already exists in the " +
                               "setOfCars list of available car");
            return Boolean.FALSE;
        }
        nbAvailableCars = new Short((short) (nbAvailableCars.shortValue() + 1));
        return Boolean.TRUE;
    }

    /**
     * Returns a CompositeData representing the car whose maker is <maker>
     * and model <model>.
     */
    private Boolean removeCar(String maker, String model)
        throws OpenDataException {

        Object[] key = new Object[2];

        // build key
        key[0] = maker;
        key[1] = model;

        //remove the car
        if (setOfCars.remove(key) !=  null) {
            nbAvailableCars =
                new Short((short) (nbAvailableCars.shortValue() - 1));
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * Returns a TabularData representing the list of available colors for
     * the car represented by its maker and model
     */
    private TabularData returnAvailableColors(String maker, String model)
        throws OpenDataException {
        Object[] key = new Object[2];
        key[0] = maker;
        key[1] = model;

        CompositeData car = setOfCars.get(key);
        if (car == null) {
            System.out.println("This car doesn't exist in the set of " +
                               "available cars");
            return (TabularData) null;
        }
        Iterator carIterator = car.values().iterator();
        Object elem = carIterator.next();
        System.out.println((TabularData) elem);
        return (TabularData) elem;
    }

    /**
     * Adds a color to the TabularData representing the list of available
     * colors for the car represented by maker and model.
     */
    private Void addNewColor(String maker, String model,
                             Integer red, Integer green, Integer blue)
        throws OpenDataException {
        Object[] key = new Object[2];
        CompositeData color;

        // build key
        key[0] = maker;
        key[1] = model;

        // build the new color
        Object[] colorItemValues = new Object[3];
        colorItemValues[0] = red;
        colorItemValues[1] = green;
        colorItemValues[2] = blue;
        color = new CompositeDataSupport(compositeTypeColor,
                                         compositeTypeColorItemsName,
                                         colorItemValues);

        //get the desired car
        CompositeData car = setOfCars.get(key);
        if (car == null) {
            System.out.println("This car doesn't exist in the set " +
                               "of available cars");
            return null;
        }

        // get the list of colors tabularData for this car
        Iterator carIterator = car.values().iterator();
        Object elem = carIterator.next();
        TabularData listOfColors = (TabularData) elem;

        try {
            listOfColors.put(color);
        } catch (KeyAlreadyExistsException e) {
            System.out.println("Error this color already exists in " +
                               "the listOfColors for this car");
        }
        return null;
    }

    /**
     * Returns a TabularData representing all cars whose maker is <maker>.
     */
    private TabularData returnByMaker(String maker) throws OpenDataException {

        Iterator carIterator;
        TabularData newList = null;

        for (carIterator = setOfCars.values().iterator();
             carIterator.hasNext(); ) {
            Object elem = carIterator.next();
            CompositeData myCar = (CompositeData) elem;
            Object[] key = new Object[2];
            Iterator it = myCar.values().iterator();
            Object carElem = it.next();  // we skip the colors item 
            carElem = it.next();
            if (((String) carElem).compareTo(maker) != 0) {
                continue;
            }
            if (newList == null) {
                newList = new TabularDataSupport(tabularTypeListOfCars);
            }
            // else the maker corresponds to the expected one
            try {
                newList.put((CompositeData) myCar);
            } catch (KeyAlreadyExistsException e) {
                System.out.println("there is an exception!!!");
                e.printStackTrace();
            }
        }
        return newList;
    }

    /**
     * Returns a CompositeData representing the car whose maker is <maker>
     * and model <model>.
     */
    private CompositeData getCarByMakerAndModel(String maker, String model)
        throws OpenDataException {

        Object[] key = new Object[2];

        // build key
        key[0] = maker;
        key[1] = model;

        // get the desired car
        return setOfCars.get(key);
    }

    //
    // Implementation of the DynamicMBean Interface
    //

    public Object getAttribute(String attribute_name)
        throws AttributeNotFoundException,
               MBeanException,
               ReflectionException {
        
        if (attribute_name == null) {
            throw new RuntimeOperationsException(
                 new IllegalArgumentException("Attribute name cannot be null"), 
                 "Cannot call getAttribute with null attribute name");
        }
        if (attribute_name.equals("nbSoldCars")) {
            return getNbSoldCars();
        }
        if (attribute_name.equals("nbAvailableCars")) {
            return getNbAvailableCars();
        }
        if (attribute_name.equals("isStoreOpen")) {
            return getIsStoreOpen();
        }
        if (attribute_name.equals("setOfCars")) {
            return getSetOfCars();
        }
        if (attribute_name.equals("mostUsedColors")) {
            return getMostUsedColors();
        }
        throw new AttributeNotFoundException("Cannot find " +
                                             attribute_name +
                                             " attribute ");
    }

    public void setAttribute(Attribute attribute)
        throws AttributeNotFoundException,
               InvalidAttributeValueException,
               MBeanException,
               ReflectionException {

        // Check attribute is not null to avoid NullPointerException later on
        if (attribute == null) {
            throw new RuntimeOperationsException(
                  new IllegalArgumentException("Attribute cannot be null"), 
                  "Cannot invoke a setter of CarOpenMBean with null attribute");
        }
        String name = attribute.getName(); // Note: Attribute's constructor
                                           // ensures it is not null
        Object value = attribute.getValue();

        // Check for a recognized attribute name and call the corresponding
        // setter
        //
        if (name.equals("nbSoldCars")) {
            // if null value, try and see if the setter returns any exception
            if (value == null) {
                try {
                    setNbSoldCars(null);
                } catch (Exception e) {
                    throw new InvalidAttributeValueException(
                              "Cannot set attribute " + name + " to null");
                }
            }
            // if non null value, make sure it is assignable to the attribute
            else {
                try {
                    if ((Class.forName("java.lang.Long")).isAssignableFrom(
                                                            value.getClass())) {
                        setNbSoldCars((Long) value);
                    } else {
                        throw new InvalidAttributeValueException(
                                  "Cannot set attribute " + name + " to a " +
                                  value.getClass().getName() +
                                  " object, Long expected");
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        // recognize an attempt to set "NbChanges" attribute (read-only):
        else if (name.equals("setOfCars") ||
                 name.equals("nbAvailableCars") ||
                 name.equals("isStoreOpen") ||
                 name.equals("mostUsedColorsData")) {
            throw new AttributeNotFoundException("Cannot set attribute " +
                                                 name +
                                                 " because it is read-only");
        }
        // unrecognized attribute name:
        else {
            throw new AttributeNotFoundException("Attribute " + name +
                                                 " not found in " +
                                                 this.getClass().getName());
        }
    }

    public AttributeList getAttributes(String[] attributeNames) {

        if (attributeNames == null) {
            throw new RuntimeOperationsException(
                new IllegalArgumentException("attributeNames[] cannot be null"),
                "Cannot call getAttributes with null attribute names");
        }
        AttributeList resultList = new AttributeList();

        if (attributeNames.length == 0)
            return resultList;

        for (int i = 0 ; i < attributeNames.length ; i++) {
            try {
                Object value = getAttribute((String) attributeNames[i]);
                resultList.add(new Attribute(attributeNames[i],value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return(resultList);
    }

    public AttributeList setAttributes(AttributeList attributes) {

        // Check attributes is not null to avoid NullPointerException later on
        if (attributes == null) {
            throw new RuntimeOperationsException(
                new IllegalArgumentException(
                           "AttributeList attributes cannot be null"),
                "Cannot invoke a setter of CarOpenMBean");
        }
        AttributeList resultList = new AttributeList();

        // if attributeNames is empty, nothing more to do
        if (attributes.isEmpty())
            return resultList;

        // for each attribute, try to set it and add to the result list if
        // successful
        for (Iterator i = attributes.iterator(); i.hasNext(); ) {
            Attribute attr = (Attribute) i.next();
            try {
                setAttribute(attr);
                String name = attr.getName();
                Object value = getAttribute(name); 
                resultList.add(new Attribute(name,value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    public Object invoke(String operationName,
                         Object[] params,
                         String[] signature)
        throws MBeanException, ReflectionException {

        if (operationName == null) {
            throw new RuntimeOperationsException(
                new IllegalArgumentException("Operation name cannot be null"),
                "Cannot call invoke with null operation name");
        }

        if (operationName.equals("carCheaperThan")) {

            // check params
            if ((params.length != 1) || !(params[0] instanceof Float)) {
                throw new RuntimeOperationsException(
                    new IllegalArgumentException(
                        "Cannot invoke carCheaperThan: expecting params[i] " +
                        "instanceof SimpleType.FLOAT for i = 0"),
                    "Wrong content for array Object[] params to invoke " +
                    "carCheaperThan method");
            }
            // invoke  carCheaperThan
            try {
                return carCheaperThan((Float) params[0]);
            } catch (Exception e) {
                throw new MBeanException(e, "invoking carCheaperThan: " +
                                         e.getClass().getName() +
                                         " caught [" + e.getMessage() + "]");
            }
        }

        // public void addCar(CompositeData car)
        //
        else if (operationName.equals("addCar")) {

            // check params
            if ((params.length == 1) && (params[0] instanceof CompositeData)) {
                // invoke addCar
                try {
                    return addCar((CompositeData) params[0]);
                } catch (Exception e) {
                    throw
                        new MBeanException(e, "invoking addCar: " +
                                           e.getClass().getName() +
                                           " caught [" + e.getMessage() + "]");
                }
            } else if ((params.length == 6)
                       && (params[0] instanceof String)
                       && (params[1] instanceof String)
                       && (params[2] instanceof Integer)
                       && (params[3] instanceof Integer)
                       && (params[4] instanceof Integer)
                       && (params[5] instanceof Float)) {
                // invoke  addCar
                try {
                    return addCar((String) params[0], (String) params[1],
                                  (Integer) params[2], (Integer) params[3],
                                  (Integer) params[4], (Float) params[5]);
                } catch (Exception e) {
                    throw
                        new MBeanException(e, "invoking addCar: " +
                                           e.getClass().getName() +
                                           " caught [" + e.getMessage() + "]");
                }
            } else {
                throw new RuntimeOperationsException(
                    new IllegalArgumentException("Cannot invoke addCar"),
                    "Wrong content for array Object[] params to " +
                    "invoke addCar method");
            }
        } else if (operationName.equals("removeCar")) {

            // check params
            if ((params.length == 2)
                && (params[0] instanceof String)
                && (params[1] instanceof String)) {
                // invoke  removeCar
                try {
                    return removeCar((String) params[0], (String) params[1]);
                } catch (Exception e) {
                    throw
                        new MBeanException(e, "invoking removeCar: " +
                                           e.getClass().getName() +
                                           " caught [" + e.getMessage() + "]");
                }
            } else {
                throw new RuntimeOperationsException(
                    new IllegalArgumentException("Cannot invoke removeCar"),
                    "Wrong content for array Object[] params to " +
                    "invoke removeCar method");
            }
        } else if (operationName.equals("returnAvailableColors")) {
            if ((params.length == 2)
                && (params[0] instanceof String)
                && (params[1] instanceof String)) {
                try {
                    return returnAvailableColors((String) params[0],
                                                 (String) params[1]);
                } catch (Exception e) {
                    throw
                        new MBeanException(e,
                                           "invoking returnAvailableColors: " +
                                           e.getClass().getName() +
                                           " caught [" + e.getMessage() + "]");
                }
            } else {
                throw new RuntimeOperationsException(
                    new IllegalArgumentException(
                                       "Cannot invoke returnAvailableColors"),
                    "Wrong content for array Object[] params to " +
                    "invoke returnAvailableColors method");
            }
        } else if (operationName.equals("addNewColor")) {
            if ((params.length == 5)
                && (params[0] instanceof String)
                && (params[1] instanceof String)
                && (params[2] instanceof Integer)
                && (params[3] instanceof Integer)
                && (params[4] instanceof Integer)) {
                try {
                    return addNewColor((String) params[0],
                                       (String) params[1],
                                       (Integer) params[2],
                                       (Integer) params[3],
                                       (Integer) params[4]);
                } catch (Exception e) {
                    throw
                        new MBeanException(e, "invoking addNewColor: " +
                                           e.getClass().getName() +
                                           " caught [" + e.getMessage() + "]");
                }
            } else {
                throw new RuntimeOperationsException(
                    new IllegalArgumentException(
                                       "Cannot invoke addNewColor"),
                    "Wrong content for array Object[] params to " +
                    "invoke addNewColor method");
            }
        } else if (operationName.equals("returnByMaker")) {
            if ((params.length == 1)
                && (params[0] instanceof String)) {
                try {
                    return returnByMaker((String) params[0]);
                } catch (Exception e) {
                    throw
                        new MBeanException(e, "invoking returnByMaker: " +
                                           e.getClass().getName() +
                                           " caught [" + e.getMessage() + "]");
                }
            } else {
                throw new RuntimeOperationsException(
                    new IllegalArgumentException("Cannot invoke returnByMaker"),
                    "Wrong content for array Object[] params to " +
                    "invoke returnByMaker method");
            }
        } else if (operationName.equals("getCarByMakerAndModel")) {
            if ((params.length == 2)
                && (params[0] instanceof String)
                && (params[1] instanceof String)) {
                try {
                    return getCarByMakerAndModel((String) params[0],
                                                 (String) params[1]);
                } catch (Exception e) {
                    throw
                        new MBeanException(e, "invoking getCarByMakerAndModel: "
                                           + e.getClass().getName() +
                                           " caught [" + e.getMessage() + "]");
                }
            } else {
                throw new RuntimeOperationsException(
                    new IllegalArgumentException(
                                       "Cannot invoke getCarByMakerAndModel"),
                    "Wrong content for array Object[] params to " +
                    "invoke getCarByMakerAndModel method");
            }
        } else {
            throw new ReflectionException(
                new NoSuchMethodException(operationName),
                "Cannot find the operation " + operationName);
        }
    } // invoke

    public MBeanInfo getMBeanInfo() {
        return CarOpenMBeanInfo;
    }

    /* *** Open MBean Info *** */

    private void buildMBeanInfo() throws OpenDataException {

        OpenMBeanConstructorInfoSupport[] constructors =
            new OpenMBeanConstructorInfoSupport[1];
        OpenMBeanAttributeInfoSupport[] attributes =
            new OpenMBeanAttributeInfoSupport[5];
        OpenMBeanOperationInfoSupport[] operations =
            new OpenMBeanOperationInfoSupport[8];
        MBeanNotificationInfo[] notifications =
            new MBeanNotificationInfo[0];

        // attribute nbSoldCars
        attributes[0] =
            new OpenMBeanAttributeInfoSupport("nbSoldCars",
                                              "Total number of sold cars",
                                              SimpleType.LONG,
                                              true,
                                              true,
                                              false,
                                              new Long(10),
                                              new Long(0),
                                              new Long(10000));

        // attribute nbAvailableCars (no default or legal values)
        attributes[1] =
            new OpenMBeanAttributeInfoSupport("nbAvailableCars",
                                              "Total number of available cars",
                                              SimpleType.SHORT,
                                              true,
                                              false,
                                              false);

        // attribute isStoreOpen (no default or legal values)
        attributes[2] =
            new OpenMBeanAttributeInfoSupport("isStoreOpen",
                                              "true if the store is open",
                                              SimpleType.BOOLEAN,
                                              true,
                                              false,
                                              true);

        // attribute setOfCars ()
        attributes[3] =
            new OpenMBeanAttributeInfoSupport("setOfCars",
                                              "TabularData representing " +
                                              "the list of available cars",
                                              tabularTypeListOfCars,
                                              true,
                                              true,
                                              false);

        // attribute mostUsedColors (no default or legal values)
        attributes[4] =
            new OpenMBeanAttributeInfoSupport("mostUsedColors",
                                              "CompositeData representing " +
                                              "the most used color for cars",
                                              compositeTypeColor,
                                              true,
                                              true,
                                              false);

        // constructor
        constructors[0] =
            new OpenMBeanConstructorInfoSupport(
                                    "carOpenMBean",
                                    "Constructs a carOpenMBean instance " +
                                    "containing an empty cars list",
                                    new OpenMBeanParameterInfoSupport[0]);

        // operation carCheaperThan
        OpenMBeanParameterInfo[] carCheaperThanParameters =
            new OpenMBeanParameterInfoSupport[1];
        carCheaperThanParameters[0] =
            new OpenMBeanParameterInfoSupport("price",
                                              "the maximum price",
                                              SimpleType.FLOAT);
        operations[0] =
            new OpenMBeanOperationInfoSupport("carCheaperThan",
                                              "returns all cars cheaper than " +
                                              "price given in argument",
                                              carCheaperThanParameters,
                                              tabularTypeListOfCars,
                                              MBeanOperationInfo.ACTION);

        // operation removeCar explicit
        OpenMBeanParameterInfo[] removeCarParameters =
            new OpenMBeanParameterInfoSupport[2];
        removeCarParameters[0] =
            new OpenMBeanParameterInfoSupport("maker",
                                              "The maker of the car to add",
                                              SimpleType.STRING);
        removeCarParameters[1] =
            new OpenMBeanParameterInfoSupport("model",
                                              "The model of the car to add",
                                              SimpleType.STRING);

        operations[1] =
            new OpenMBeanOperationInfoSupport("removeCar",
                                              "Removes the car represented by" +
                                              " its name and model from the " +
                                              "listOfAvailableCars and " +
                                              "decrements the list of " +
                                              "available cars",
                                              removeCarParameters,
                                              SimpleType.BOOLEAN,
                                              MBeanOperationInfo.ACTION);

        // operation addCar
        OpenMBeanParameterInfo[] addCarParameters =
            new OpenMBeanParameterInfoSupport[1];
        addCarParameters[0] =
            new OpenMBeanParameterInfoSupport("car",
                                              "The car to add",
                                              compositeTypeCar);
        operations[2] =
            new OpenMBeanOperationInfoSupport("addCar",
                                              "Adds the car given in argument" +
                                              " to the listOfAvailableCars " +
                                              "and increments the list of " +
                                              "available cars",
                                              addCarParameters,
                                              SimpleType.BOOLEAN,
                                              MBeanOperationInfo.ACTION);

        // operation addCar explicit
        OpenMBeanParameterInfo[] addCar2Parameters =
            new OpenMBeanParameterInfoSupport[6];
        addCar2Parameters[0] =
            new OpenMBeanParameterInfoSupport("maker",
                                              "The maker of the car to add",
                                              SimpleType.STRING);
        addCar2Parameters[1] =
            new OpenMBeanParameterInfoSupport("model",
                                              "The model of the car to add",
                                              SimpleType.STRING);
        addCar2Parameters[2] =
            new OpenMBeanParameterInfoSupport("red",
                                              "The red component " +
                                              "of the car color",
                                              SimpleType.INTEGER);
        addCar2Parameters[3] =
            new OpenMBeanParameterInfoSupport("green",
                                              "The green component " +
                                              "of the car color",
                                              SimpleType.INTEGER);
        addCar2Parameters[4] =
            new OpenMBeanParameterInfoSupport("blue",
                                              "The blue component " +
                                              "of the car color",
                                              SimpleType.INTEGER);
        addCar2Parameters[5] =
            new OpenMBeanParameterInfoSupport("price",
                                              "The price of the car",
                                              SimpleType.FLOAT);

        operations[3] =
            new OpenMBeanOperationInfoSupport("addCar",
                                              "Adds the car represented by " +
                                              "its name, model, RGB values " +
                                              "and price to the " +
                                              "listOfAvailableCars and " +
                                              "increments the list of " +
                                              "available cars",
                                              addCar2Parameters,
                                              tabularTypeListOfCars,
                                              MBeanOperationInfo.ACTION);

        // operation returnAvailableColors
        OpenMBeanParameterInfo[] returnAvailableColorsParameters =
            new OpenMBeanParameterInfoSupport[2];
        returnAvailableColorsParameters[0] =
            new OpenMBeanParameterInfoSupport("maker",
                                              "The maker of the car",
                                              SimpleType.STRING);
        returnAvailableColorsParameters[1] =
            new OpenMBeanParameterInfoSupport("model",
                                              "The model of the car",
                                              SimpleType.STRING);

        operations[4] =
            new OpenMBeanOperationInfoSupport("returnAvailableColors",
                                              "Returns a TabularData " +
                                              "representing the list of " +
                                              "available colors for the " +
                                              "car represented by its " +
                                              "maker and model",
                                              returnAvailableColorsParameters,
                                              tabularTypeListOfColors,
                                              MBeanOperationInfo.ACTION);

        // operation addNewColor
        OpenMBeanParameterInfo[] addNewColorParameters =
            new OpenMBeanParameterInfoSupport[5]; 
        addNewColorParameters[0] =
            new OpenMBeanParameterInfoSupport("maker",
                                              "The maker of the car",
                                              SimpleType.STRING);
        addNewColorParameters[1] =
            new OpenMBeanParameterInfoSupport("model",
                                              "The model of the car",
                                              SimpleType.STRING);
        addNewColorParameters[2] =
            new OpenMBeanParameterInfoSupport("red",
                                              "The red component " +
                                              "of the car color",
                                              SimpleType.INTEGER);
        addNewColorParameters[3] =
            new OpenMBeanParameterInfoSupport("green",
                                              "The green component " +
                                              "of the car color",
                                              SimpleType.INTEGER);
        addNewColorParameters[4] =
            new OpenMBeanParameterInfoSupport("blue",
                                              "The blue component " +
                                              "of the car color",
                                              SimpleType.INTEGER);
        operations[5] =
            new OpenMBeanOperationInfoSupport("addNewColor",
                                              "Adds a color to the " +
                                              "TabularData representing the " +
                                              "list of available colors for " +
                                              "the car represented by maker " +
                                              "and model",
                                              addNewColorParameters,
                                              SimpleType.VOID,
                                              MBeanOperationInfo.ACTION);

        // operation returnByMaker
        OpenMBeanParameterInfo[] returnByMakerParameter =
            new OpenMBeanParameterInfoSupport[1];
        returnByMakerParameter[0] =
            new OpenMBeanParameterInfoSupport("maker",
                                              "The maker of the car",
                                              SimpleType.STRING);

        operations[6] =
            new OpenMBeanOperationInfoSupport("returnByMaker",
                                              "Returns a TabularData " +
                                              "representing all the cars " +
                                              "whose maker is <maker>",
                                              returnByMakerParameter,
                                              tabularTypeListOfCars,
                                              MBeanOperationInfo.ACTION);

        // operation getCarByMakerAndModel
        OpenMBeanParameterInfo[] getCarByMakerAndModelParameter =
            new OpenMBeanParameterInfoSupport[2];
        getCarByMakerAndModelParameter[0] =
            new OpenMBeanParameterInfoSupport("maker",
                                              "The maker of the car",
                                              SimpleType.STRING);
        getCarByMakerAndModelParameter[1] =
            new OpenMBeanParameterInfoSupport("model",
                                              "The model of the car",
                                              SimpleType.STRING);

        operations[7] =
            new OpenMBeanOperationInfoSupport("getCarByMakerAndModel",
                                              "Returns a CompositeData " +
                                              "representing the car " +
                                              "whose maker is <maker> " +
                                              "and model <model>",
                                              getCarByMakerAndModelParameter,
                                              compositeTypeCar,
                                              MBeanOperationInfo.ACTION);

        // The OpenMBeanInfo
        CarOpenMBeanInfo = new OpenMBeanInfoSupport(this.getClass().getName(),
                                                    "Car Open MBean",
                                                    attributes,
                                                    constructors,
                                                    operations,
                                                    notifications);
    }
}
