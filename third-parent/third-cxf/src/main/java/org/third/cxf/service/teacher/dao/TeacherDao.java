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
