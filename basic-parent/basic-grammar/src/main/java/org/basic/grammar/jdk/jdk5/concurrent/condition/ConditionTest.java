package org.basic.grammar.jdk.jdk5.concurrent.condition;

import java.text.DecimalFormat;

public class ConditionTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        int a = 23;
        int b = 123;
        a = a ^ b;
        b = b ^ a;
        a = a ^ b;
        // ��һ�������� "1$" ���ã��ڶ��������� "2$" ��
        // �������͡��ַ����ͺ���ֵ���͵ĸ�ʽ˵�������﷨���£�
        // %[argument_index$][flags][width][.precision]conversion
        // ��ѡ�� flags ���޸������ʽ���ַ�������Ч��־�ļ���ȡ����ת�����͡�
        // width: �������д��������ַ���;precision:���������ַ���
        // ������ʾ���ں�ʱ�����͵ĸ�ʽ˵�������﷨���£�
        // %[argument_index$][flags][width]conversion
        // ���������Ӧ�ĸ�ʽ˵�������﷨���£�
        // %[flags][width]conversion

        // ת������
        // 'b', 'B' ���� ������� arg Ϊ null������Ϊ "false"����� arg ��һ�� boolean ֵ�� Boolean������Ϊ
        // String.valueOf() ���ص��ַ�����������Ϊ "true"��
        // 'h', 'H' ���� ������� arg Ϊ null������Ϊ "null"�����򣬽��Ϊ���� Integer.toHexString(arg.hashCode())
        // �õ��Ľ����
        // 's', 'S' ���� ������� arg Ϊ null������Ϊ "null"����� arg ʵ�� Formattable������� arg.formatTo�����򣬽��Ϊ����
        // arg.toString() �õ��Ľ����
        // 'c', 'C' �ַ� �����һ�� Unicode �ַ�
        // 'd' ���� �������ʽ��Ϊʮ��������
        // 'o' ���� �������ʽ��Ϊ�˽�������
        // 'x', 'X' ���� �������ʽ��Ϊʮ����������
        // 'e', 'E' ���� �������ʽ��Ϊ�ü������ѧ��������ʾ��ʮ������
        // 'f' ���� �������ʽ��Ϊʮ������
        // 'g', 'G' ���� ���ݾ��Ⱥ�����������ֵ��ʹ�ü������ѧ������ʽ��ʮ���Ƹ�ʽ�Խ�����и�ʽ����
        // 'a', 'A' ���� �������ʽ��Ϊ����Чλ����ָ����ʮ�����Ƹ�����
        // 't', 'T' ����/ʱ�� ���ں�ʱ��ת���ַ���ǰ׺�����������/ʱ��ת����
        // '%' �ٷֱ� ���Ϊ����ֵ '%' ('\u0025')
        // 'n' �зָ��� ���Ϊ�ض���ƽ̨���зָ���

        System.out.printf("Duke's Birthday: %0$s, %1$s, %2$s, %3$23.2f ", new Object[] { a, b,
                233.4 });
        System.out.println();
        System.out.printf("Float: %1$10.2f", 234.52323);
        System.out.println();

        // NumberFormat nf = NumberFormat.getInstance();
        // nf.setMinimumIntegerDigits(10);
        // nf.setMaximumIntegerDigits(4);
        // nf.setMinimumFractionDigits(3);
        // nf.setMaximumFractionDigits(12);

        DecimalFormat df = new DecimalFormat("#######.###");
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                System.out.println();
            } else {
                System.out.print("\t");
            }
            System.out.print(df.format(234.52323));
        }
    }

}
