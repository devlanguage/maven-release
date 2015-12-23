/****************************************************************************
@XmlRootElement(name = "teacher")
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 10:29:01 AM Sep 28, 2014
 *
 *****************************************************************************
 */
package org.third.cxf.service.teacher.dao;

import java.util.Collection;

/**
 * Created on Sep 28, 2014, 10:29:01 AM
 */
public interface TeacherDao {
    public Collection<Teacher> getAllTeachers();

    public Teacher getTeacher(long id);

    public Teacher deleteTeacher(long id);

    public Teacher updateTeacher(long id, String name);

    public Teacher addTeacher(String name);

}
