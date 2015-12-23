package org.basic.grammar.bytecode;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.Constants;
import com.sun.org.apache.bcel.internal.classfile.Attribute;
import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;
import com.sun.org.apache.bcel.internal.classfile.Constant;
import com.sun.org.apache.bcel.internal.classfile.ConstantClass;
import com.sun.org.apache.bcel.internal.classfile.ConstantDouble;
import com.sun.org.apache.bcel.internal.classfile.ConstantFieldref;
import com.sun.org.apache.bcel.internal.classfile.ConstantFloat;
import com.sun.org.apache.bcel.internal.classfile.ConstantInteger;
import com.sun.org.apache.bcel.internal.classfile.ConstantInterfaceMethodref;
import com.sun.org.apache.bcel.internal.classfile.ConstantLong;
import com.sun.org.apache.bcel.internal.classfile.ConstantMethodref;
import com.sun.org.apache.bcel.internal.classfile.ConstantNameAndType;
import com.sun.org.apache.bcel.internal.classfile.ConstantPool;
import com.sun.org.apache.bcel.internal.classfile.ConstantString;
import com.sun.org.apache.bcel.internal.classfile.ConstantUtf8;
import com.sun.org.apache.bcel.internal.classfile.Field;
import com.sun.org.apache.bcel.internal.classfile.Method;

/**
 * {@link com.sun.org.apache.bcel.internal.classfile.ClassParser#parse}
 * 
 * @author ygong
 *
 */
public class ClassParser {
    public static void main(String[] args) {
        String file_name = "JavaBasic_output0/org/basic/grammar/bytecode/BcelClass2HTML.class";
        try {
            // com.sun.org.apache.bcel.internal.classfile.ClassParser cp1 = new
            // com.sun.org.apache.bcel.internal.classfile.ClassParser(file_name);
            DataInputStream file = new DataInputStream(new BufferedInputStream(new FileInputStream(file_name), 8192));

            int magic = -889275714;
            // s1: readID();
            System.out.println(file.readInt());

            // s2: readVersion();
            int minor = file.readUnsignedShort();
            int major = file.readUnsignedShort();
            System.out.println("minor=" + minor + ",major=" + major);

            // s3: readConstantPool();

            byte tag;

            int constant_pool_count = file.readUnsignedShort();
            com.sun.org.apache.bcel.internal.classfile.Constant[] constant_pool = new Constant[constant_pool_count];
            /*
             * constant_pool[0] is unused by the compiler and may be used freely by the implementation.
             */
            for (int i = 1; i < constant_pool_count; i++) {

                constant_pool[i] = readConstant(file);

                /*
                 * Quote from the JVM specification: "All eight byte constants take up two spots in the constant pool.
                 * If this is the n'th byte in the constant pool, then the next item will be numbered n+2"
                 * 
                 * Thus we have to increment the index counter.
                 */
                tag = constant_pool[i].getTag();
                if ((tag == Constants.CONSTANT_Double) || (tag == Constants.CONSTANT_Long))
                    i++;
            }
            ConstantPool constantPool = new ConstantPool(constant_pool);

            // s4: readClassInfo();
            {
                int access_flags = file.readUnsignedShort();

                /*
                 * Interfaces are implicitely abstract, the flag should be set according to the JVM specification.
                 */
                if ((access_flags & Constants.ACC_INTERFACE) != 0)
                    access_flags |= Constants.ACC_ABSTRACT;

                if (((access_flags & Constants.ACC_ABSTRACT) != 0) && ((access_flags & Constants.ACC_FINAL) != 0))
                    throw new ClassFormatException("Class can't be both final and abstract");

                int class_name_index = file.readUnsignedShort();
                int superclass_name_index = file.readUnsignedShort();
            }

            // s5: readInterfaces();
            {
                int interfaces_count;
                interfaces_count = file.readUnsignedShort();
                int[] interfaces = new int[interfaces_count];

                for (int i = 0; i < interfaces_count; i++) {
                    interfaces[i] = file.readUnsignedShort();
                }
            }
            // s6: readFields();
            int fields_count;
            fields_count = file.readUnsignedShort();
            com.sun.org.apache.bcel.internal.classfile.Field[] fields = new Field[fields_count];
            for (int i = 0; i < fields_count; i++) {
                int access_flags = file.readUnsignedShort();
                int name_index = file.readUnsignedShort();
                int signature_index = file.readUnsignedShort();

                int attributes_count = file.readUnsignedShort();
                Attribute[] attributes = new Attribute[attributes_count];
                for (int k = 0; k < attributes_count; k++) {
                    attributes[k] = Attribute.readAttribute(file, constantPool);
                }

                fields[i] = new Field(access_flags, name_index, signature_index, attributes, constantPool);
            }

            // s7: readMethods();
            {
                int methods_count;

                methods_count = file.readUnsignedShort();
                Method[] methods = new Method[methods_count];

                for (int i = 0; i < methods_count; i++) {
                    int access_flags = file.readUnsignedShort();
                    int name_index = file.readUnsignedShort();
                    int signature_index = file.readUnsignedShort();

                    int attributes_count = file.readUnsignedShort();
                    Attribute[] attributes = new Attribute[attributes_count];
                    for (int k = 0; k < attributes_count; k++) {
                        attributes[k] = Attribute.readAttribute(file, constantPool);
                    }
                    
                    methods[i] = new Method(access_flags, name_index, signature_index, attributes, constantPool);
                }
            }
            // s8: readAttributes();
            {
                int attributes_count;

                attributes_count = file.readUnsignedShort();
                Attribute[]   attributes       = new Attribute[attributes_count];

                for(int i=0; i < attributes_count; i++){
                  attributes[i] = Attribute.readAttribute(file, constantPool);
                }
            }
            System.out.println(0);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static Constant readConstant(DataInputStream file) throws IOException {

        byte b = file.readByte(); // Read tag byte

        switch (b) {
            case Constants.CONSTANT_Class:
                return new ConstantClass(file.readUnsignedShort());
            case Constants.CONSTANT_Fieldref: {
                int class_index = file.readUnsignedShort();
                int name_and_type_index = file.readUnsignedShort();
                return new ConstantFieldref(class_index, name_and_type_index);
            }
            case Constants.CONSTANT_Methodref: {
                int class_index = file.readUnsignedShort();
                int name_and_type_index = file.readUnsignedShort();
                return new ConstantMethodref(class_index, name_and_type_index);
            }
            case Constants.CONSTANT_InterfaceMethodref: {
                int class_index = file.readUnsignedShort();
                int name_and_type_index = file.readUnsignedShort();
                return new ConstantInterfaceMethodref(class_index, name_and_type_index);
            }
            case Constants.CONSTANT_String: {
                int string_index = (int) file.readUnsignedShort();
                return new ConstantString(string_index);
            }
            case Constants.CONSTANT_Integer: {
                int bytes = file.readInt();
                return new ConstantInteger(bytes);
            }
            case Constants.CONSTANT_Float: {
                float bytes = file.readFloat();
                return new ConstantFloat(bytes);
            }
            case Constants.CONSTANT_Long: {
                long bytes = file.readLong();
                return new ConstantLong(bytes);
            }
            case Constants.CONSTANT_Double: {
                double bytes = file.readDouble();
                return new ConstantDouble(bytes);
            }
            case Constants.CONSTANT_NameAndType: {
                int name_index = (int) file.readUnsignedShort();
                int signature_index = (int) file.readUnsignedShort();
                return new ConstantNameAndType(name_index, signature_index);
            }
            case Constants.CONSTANT_Utf8: {
                String bytes = file.readUTF();
                return new ConstantUtf8(bytes);
            }
            default:
                throw new ClassFormatException("Invalid byte tag in constant pool: " + b);
        }
    }
}
