package org.basic.pattern.creational.Prototype.test3;

/**
 * * *
 * <p>
 * Title:
 * </p> * *
 * <p>
 * Description: 客户端使用prototype模式
 * </p> * *
 * <p>
 * Copyright: Copyright (c) 2005
 * </p> * *
 * <p>
 * Company: www.hhzskj.com
 * </p> * *
 * 
 * @author meconsea *
 * @version 1.0
 */
public class PrototypeClient {

    PrototypeManager pm = null;

    public PrototypeClient() {

        pm = PrototypeManager.getPrototypeManager();
    }

    public static void main(String args[]) {

        PrototypeClient pc = new PrototypeClient();
        String className = null;
        className = "org.basic.pattern.创建型.Prototype.test3.PrototypeRam";
        PrototypeRam pr = null;
        pr = (PrototypeRam) (pc.pm.getPrototype(className));
        if (pr != null) {
            PrototypeRam[] pram;
            System.out.println(" for loop before PrototypeRam name == " + pr.getName());
            pram = new PrototypeRam[10];
            for (int i = 0; i < 10; i++) {
                /** * 生成一批克隆的Ram,并比较他们和原型的不同 */
                pram[i] = (PrototypeRam) pr.clone();
                System.out.println("loop for PrototypeRam name == " + pram[i].getName());
                pram[i].setName(pram[i].getName() + i);
                System.out.println("clone changes after " + pram[i].getName() + " old "
                        + pr.getName());
            }
        }
    }
}
