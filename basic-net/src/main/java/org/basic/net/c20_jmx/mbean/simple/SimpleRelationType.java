package org.basic.net.c20_jmx.mbean.simple;
/*
 * @(#)file      SimpleRelationType.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.6
 * @(#)lastedit  04/02/02
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// JMX imports
//
import javax.management.relation.*;

/**
 * The "SimpleRelationType" class shows how to define a relation type, to
 * avoid to have to describe the role infos each time and to create an
 * internal RelationTypeSupport object in the Relation Service.
 * <P>Here an instance of SimpleRelationType will represent the relation type
 * and will be stored in the Relation Service.
 * <P>As any class expected to represent a relation type, it has to implement
 * the RelationType interface. It is achieved by extending the
 * RelationTypeSupport class.
 * <P>Another relation type extending SimpleRelationType would simply extend
 * the class SimpleRelationType, thus inheriting the implementation of
 * RelationType interface.
 */

public class SimpleRelationType extends RelationTypeSupport {

    /*
     * -----------------------------------------------------
     * CONSTRUCTORS
     * -----------------------------------------------------
     */

    public SimpleRelationType(String theRelTypeName) {

        super(theRelTypeName);

        // Defines two role infos:
        // - primary:
        //   - element class: SimpleStandard
        //   - read: y, write: y
        //   - multiplicity: 2,2
        // - secondary:
        //   - element class: SimpleStandard
        //   - read: y, write: n
        //   - multiplicity: 2,2
        try {
            RoleInfo primaryRoleInfo =
                new RoleInfo("primary",
                             "SimpleStandard",
                             true,
                             true,
                             2,
                             2,
                             "Primary :)");
            addRoleInfo(primaryRoleInfo);
            RoleInfo secondaryRoleInfo =
                new RoleInfo("secondary",
                             "SimpleStandard",
                             true,
                             false,
                             2,
                             2,
                             "Secondary");
            addRoleInfo(secondaryRoleInfo);
        } catch (Exception exc) {
            throw new RuntimeException(exc.getMessage());
        }
    }
}
