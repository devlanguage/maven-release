package org.third.cxf.service.teacher.dao;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.basic.common.util.SystemUtils;

/**
 * Created on Sep 28, 2014, 10:29:01 AM
 */
public class TeacherDaoImpl implements TeacherDao {

    static final Map<Long, Teacher> TEACHER_ID_CACHE = new ConcurrentHashMap<Long, Teacher>();

    /*
     * (non-Javadoc)
     * 
     * @see org.third.cxf.rest.teacher.TeacherDao#getAllTeachers()
     */
    public Collection<Teacher> getAllTeachers() {
        if (TEACHER_ID_CACHE.isEmpty()) {
            long teacherId = SystemUtils.nextId();
            TEACHER_ID_CACHE.put(teacherId, new Teacher(teacherId));
            teacherId = SystemUtils.nextId();
            TEACHER_ID_CACHE.put(teacherId, new Teacher(teacherId));
            teacherId = SystemUtils.nextId();
            TEACHER_ID_CACHE.put(teacherId, new Teacher(teacherId));
            teacherId = SystemUtils.nextId();
            TEACHER_ID_CACHE.put(teacherId, new Teacher(teacherId));
            teacherId = SystemUtils.nextId();
            TEACHER_ID_CACHE.put(teacherId, new Teacher(teacherId));
        }
        return TEACHER_ID_CACHE.values();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.third.cxf.rest.teacher.TeacherDao#getTeacher(int)
     */
    public Teacher getTeacher(long id) {
        return TEACHER_ID_CACHE.get(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.third.cxf.rest.teacher.TeacherDao#deleteTeacher(int)
     */
    public Teacher deleteTeacher(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.third.cxf.rest.teacher.TeacherDao#updateTeacher(int, java.lang.String)
     */
    public Teacher updateTeacher(long id, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.third.cxf.rest.teacher.TeacherDao#addTeacher(java.lang.String)
     */
    public Teacher addTeacher(String name) {
        long teacherId = SystemUtils.nextId();

        return TEACHER_ID_CACHE.put(teacherId, new Teacher(teacherId, name));
    }

}
