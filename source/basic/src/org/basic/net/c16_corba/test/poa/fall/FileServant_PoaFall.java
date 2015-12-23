package org.basic.net.c16_corba.test.poa.fall;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.basic.net.c16_corba.poa.fall.FileInterfacePOA;
import org.basic.net.c16_corba.poa.fall.FileInterfacePackage.Data;
import org.basic.net.c16_corba.poa.fall.entity.IconSizes;
import org.basic.net.c16_corba.poa.fall.entity.Sex;
import org.basic.net.c16_corba.poa.fall.entity.Student;
import org.basic.net.c16_corba.test.oldImplBase.OrbHelper_oldImplBase;

public class FileServant_PoaFall extends FileInterfacePOA {

    @Override
    public Data[] downloadFile(String fileName) {

        File file = new File(fileName);
        byte buffer[] = new byte[(int) file.length()];
        try {
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
            input.read(buffer, 0, buffer.length);
            input.close();
        } catch (Exception e) { 
            System.out.println("FileServant Error: " + e.getMessage());
            e.printStackTrace();
        }
        return new org.basic.net.c16_corba.poa.fall.FileInterfacePackage.Data[] {};
    }

    @Override
    public Student sayHi(String user) {

        Student stu = new Student();
        stu.sex = Sex.Female;
        stu.iconSize = IconSizes.Large;
        stu.stuName = "服务器";
        java.sql.Connection conn = null;
        try {
            conn = OrbHelper_oldImplBase.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select id, student_name from corba_student");
            if (rs.next()) {
                stu.stuName = stu.stuName+", Oracle: "+rs.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return stu;
    }

}
