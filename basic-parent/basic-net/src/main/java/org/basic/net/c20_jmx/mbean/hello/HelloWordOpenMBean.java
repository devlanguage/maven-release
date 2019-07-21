package org.basic.net.c20_jmx.mbean.hello;

import java.util.Arrays;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.KeyAlreadyExistsException;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
import javax.management.openmbean.OpenMBeanConstructorInfoSupport;
import javax.management.openmbean.OpenMBeanInfoSupport;
import javax.management.openmbean.OpenMBeanOperationInfoSupport;
import javax.management.openmbean.OpenMBeanParameterInfo;
import javax.management.openmbean.OpenMBeanParameterInfoSupport;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import org.basic.net.c20_jmx.mbean.BaseMBean;

/**
 * Created on Feb 21, 2014, 5:41:42 PM
 */
public class HelloWordOpenMBean implements DynamicMBean, BaseMBean {
    // Open MBean Info
    private OpenMBeanInfoSupport OMBInfo;
    // 被管理的属性
    // 成绩表
    private TabularDataSupport scores;
    // 修改的次数
    private int nbChanges = 0;
    // 此Open MBean类使用的open types 和相关信息
    // 3项：学生名字，科目，分数
    private static String[] itemNames = { "name", "subject", "score" };
    // 各个字段的注释
    private static String[] itemDescriptions = { "name of student", "subject", "score" };
    // 数据类型
    private static OpenType[] itemTypes = { SimpleType.STRING, SimpleType.STRING, SimpleType.FLOAT };
    // 分数类型
    private static CompositeType scoreType = null;
    // 分数可以根据名字和科目来索引
    private static String[] indexNames = { "name", "subject" };
    private static TabularType scoresType = null;
    // 有效值
    private static String[] legalNames = { "Li", "Wang" };
    private static OpenMBeanParameterInfoSupport nameParamInfo;
    // 科目有效值， 语文，数学，物理
    private static String[] legalSubjects = { "Chinese", "Math", "Physics" };
    private static OpenMBeanParameterInfoSupport subjectParamInfo;
    // 分数有效值的范围
    private static float minScore = 0.0f;
    private static float maxScore = 100.0f;
    private static OpenMBeanParameterInfoSupport scoreParamInfo;
    /**** 静态初始化块 *** */
    static {
        // 初始化 OpenType 实例和ParameterInfo实例
        try {
            // 为分数创建CompositeType实例
            scoreType = new CompositeType("Score", "a score", itemNames, itemDescriptions, itemTypes);
            // 为分数表创建TabularType实例
            scoresType = new TabularType("Scores", "all scores", scoreType, // row type
                    indexNames);
            // 创建参数信息
            nameParamInfo = new OpenMBeanParameterInfoSupport("name", "valid name: " + Arrays.asList(legalNames).toString(),
                    SimpleType.STRING, "Li", // 缺省为 Li
                    legalNames); // array of legal models
            subjectParamInfo = new OpenMBeanParameterInfoSupport("subject", "valid subject: " + Arrays.asList(legalSubjects).toString(),
                    SimpleType.STRING, "Chinese", // 缺省为 Chinese
                    legalSubjects); // array of legal colors
            scoreParamInfo = new OpenMBeanParameterInfoSupport("score", "valid score (From " + minScore + " to " + maxScore + ")",
                    SimpleType.FLOAT, null, // 无缺省值
                    new Float(minScore), // 最小分数
                    new Float(maxScore)); // 最大分数
        } catch (OpenDataException e) {
            // should not happen
        }
    }

    /* *** 构造函数 *** */
    /**
     * Constructs a HelloWordOpenMBean instance containing an empty scores list
     */
    public HelloWordOpenMBean() throws OpenDataException {
        buildMBeanInfo();
        // 创建空的分数列表
        scores = new TabularDataSupport(scoresType);
    }

    /**
     * 得到分数列表的克隆对象
     */
    public TabularData getScores() {
        return (TabularData) scores.clone();
    }

    /**
     * 得到分数列表被更改的次数
     */
    public Integer getNbChanges() {
        return new Integer(nbChanges);
    }

    /* *** 操作方法 *** */
    /**
     * 增加一个分数，成功返回Boolean.TRUE if succesful,失败返回Boolean.FALSE.
     */
    public Boolean addScore(CompositeData score) {
        try {
            scores.put(score); // 如何已经存在，则抛出KeyAlreadyExistsException
            nbChanges++;
            return Boolean.TRUE;
        } catch (KeyAlreadyExistsException e) {
            return Boolean.FALSE;
        }
    }

    /**
     * 验证传入的参数是否有效并且返回参数值，如果传入参数为null，则返回缺省值
     */
    protected Object checkParam(OpenMBeanParameterInfo paramInfo, Object param) throws OpenDataException {
        Object result;
        if (!paramInfo.isValue(param)) {
            throw new OpenDataException("parameter " + paramInfo.getName() + "'s value [" + param + "] is not valid");
        } else if (param == null && paramInfo.hasDefaultValue()) {
            result = paramInfo.getDefaultValue();
        } else {
            result = param;
        }
        return result;
    }

    /**
     * 使用传入的参数创建并且返回一个分数CompositeData实例 如果参数无效，则抛出异常
     */
    public CompositeData buildScore(String name, String subject, Float score) throws OpenDataException {
        // 检查参数值是否有效, 如果必要，赋予缺省值，或者抛出异常
        name = (String) checkParam(nameParamInfo, name);
        subject = (String) checkParam(subjectParamInfo, subject);
        score = (Float) checkParam(scoreParamInfo, score);
        Object[] itemValues = { name, subject, score };
        CompositeData result = new CompositeDataSupport(scoreType, itemNames, itemValues);
        return result;
    }

    /**
     * 从列表中，移去一个分数
     */
    public void removeScore(CompositeData score) {
        // 如果这个分数存在在列表中，计算这个对象的索引
        Object[] index = scores.calculateIndex(score);
        // 把要删除的分数返回,如果没找到,则什么也不做, 如果删掉,则对更新次数进行加以操作
        CompositeData removed = scores.remove(index);
        if (removed != null) {
            nbChanges++;
        }
    }

    /* *** DynamicMBean接口的方法实现 *** */
    public Object getAttribute(String attribute_name) throws AttributeNotFoundException, MBeanException, ReflectionException {
        if (attribute_name == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"),
                    "Cannot call getAttribute with null attribute name");
        }
        if (attribute_name.equals("Scores")) {
            return getScores();
        }
        if (attribute_name.equals("NbChanges")) {
            return getNbChanges();
        }
        throw new AttributeNotFoundException("Cannot find " + attribute_name + " attribute ");
    }

    /**
     *
     */
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException,
            ReflectionException {
        throw new AttributeNotFoundException("No attribute can be set in this MBean");
    }

    /**
     *
     */
    public AttributeList getAttributes(String[] attributeNames) {
        if (attributeNames == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("attributeNames[] cannot be null"),
                    "Cannot call getAttributes with null attribute names");
        }
        AttributeList resultList = new AttributeList();
        if (attributeNames.length == 0)
            return resultList;
        for (int i = 0; i < attributeNames.length; i++) {
            try {
                Object value = getAttribute((String) attributeNames[i]);
                resultList.add(new Attribute(attributeNames[i], value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (resultList);
    }

    /**
     *
     */
    public AttributeList setAttributes(AttributeList attributes) {
        return new AttributeList(); // always empty
    }

    /**
     *
     */
    public Object invoke(String operationName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        if (operationName == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Operation name cannot be null"),
                    "Cannot call invoke with null operation name");
        }
        // 如果调用addScore方法
        if (operationName.equals("addScore")) {
            // 检查参数
            if ((params.length != 1) || !(params[0] instanceof CompositeData)) {
                throw new RuntimeOperationsException(new IllegalArgumentException("cannot invoke addScore: "
                        + "expecting params[i] instanceof CompositeData for i = 0"),
                        "Wrong content for array Object[] params to invoke addscore method");
            }
            // 调用addScore方法
            try {
                return addScore((CompositeData) params[0]);
            } catch (Exception e) {
                throw new MBeanException(e, "invoking addScore: " + e.getClass().getName() + "caught [" + e.getMessage() + "]");
            }
        }
        // 如果是调用removeScore方法
        else if (operationName.equals("removeScore")) {
            // 检查参数
            if ((params.length != 1) || !(params[0] instanceof CompositeData)) {
                throw new RuntimeOperationsException(new IllegalArgumentException("cannot invoke removeScore: "
                        + "expecting params[i] instanceof CompositeData for i = 0"),
                        "Wrong content for array Object[] params to invoke removescore method");
            }
            // 调用removeScore方法
            try {
                removeScore((CompositeData) params[0]);
                return null;
            } catch (Exception e) {
                throw new MBeanException(e, "invoking removeScore: " + e.getClass().getName() + "caught [" + e.getMessage() + "]");
            }
        }
        // 如果是调用buildScore方法
        else if (operationName.equals("buildScore")) {
            // 检查参数
            if ((params.length != 3) || !(params[0] instanceof String) || !(params[1] instanceof String) || !(params[2] instanceof Float)) {
                throw new RuntimeOperationsException(new IllegalArgumentException("cannot invoke buildScore: "
                        + "expecting params[i] instanceof SimpleData for i = 0 to 2"),
                        "Wrong content for array Object[] params to invoke buildscore method");
            }
            // 调用buildScore
            try {
                return buildScore((String) params[0], (String) params[1], (Float) params[2]);
            } catch (Exception e) {
                throw new MBeanException(e, "invoking buildScore: " + e.getClass().getName() + "caught [" + e.getMessage() + "]");
            }
        } else {
            throw new ReflectionException(new NoSuchMethodException(operationName), "Cannot find the operation " + operationName);
        }
    } // invoke

    /**
     *
     */
    public MBeanInfo getMBeanInfo() {
        return OMBInfo;
    }

    /* *** Open MBean Info *** */
    /**
     *
     */
    private void buildMBeanInfo() throws OpenDataException {
        OpenMBeanAttributeInfoSupport[] attributes = new OpenMBeanAttributeInfoSupport[2];
        OpenMBeanConstructorInfoSupport[] constructors = new OpenMBeanConstructorInfoSupport[1];
        OpenMBeanOperationInfoSupport[] operations = new OpenMBeanOperationInfoSupport[3];
        MBeanNotificationInfo[] notifications = new MBeanNotificationInfo[0];
        // attribute scores (no default or legal values: not supported for tabular types anyway)
        attributes[0] = new OpenMBeanAttributeInfoSupport("Scores", "Score List", scoresType, true, false, false);
        // attribute NbChanges (no default or legal values)
        attributes[1] = new OpenMBeanAttributeInfoSupport("NbChanges", "Number of update times.", SimpleType.INTEGER, true, false, false);
        // 构造函数
        constructors[0] = new OpenMBeanConstructorInfoSupport("HelloWordOpenMBean",
                "Constructs a HelloWordOpenMBean instance containing an empty scores list.", new OpenMBeanParameterInfoSupport[0]);
        // addscore方法
        OpenMBeanParameterInfo[] params_add = new OpenMBeanParameterInfoSupport[1];
        params_add[0] = new OpenMBeanParameterInfoSupport("score", "a score", scoreType);
        operations[0] = new OpenMBeanOperationInfoSupport("addScore", "Adds the score given in parameter to the list of available scores.",
                params_add, SimpleType.BOOLEAN, MBeanOperationInfo.ACTION);
        // removescore方法
        OpenMBeanParameterInfo[] params_remove = params_add;
        operations[1] = new OpenMBeanOperationInfoSupport("removeScore",
                "Removes the score given in parameter to the list of available scores.", params_remove, SimpleType.VOID,
                MBeanOperationInfo.ACTION);
        // buildscore方法
        OpenMBeanParameterInfo[] params_build = new OpenMBeanParameterInfoSupport[3];
        params_build[0] = nameParamInfo;
        params_build[1] = subjectParamInfo;
        params_build[2] = scoreParamInfo;
        operations[2] = new OpenMBeanOperationInfoSupport("buildScore",
                "Builds and returns a CompositeData score instance from the specified parameters.", params_build, scoreType,
                MBeanOperationInfo.INFO);
        // OpenMBeanInfo对象
        OMBInfo = new OpenMBeanInfoSupport(this.getClass().getName(), "Sample Open MBean", attributes, constructors, operations,
                notifications);
    }

    /**
     * @return
     */
    public String getMBeanName() {
        return this.getClass().getName();
    }
}