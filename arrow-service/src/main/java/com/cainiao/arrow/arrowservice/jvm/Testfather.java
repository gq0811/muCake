package com.cainiao.arrow.arrowservice.jvm;

public class Testfather {


    static {
        System.out.println("Testfather,静态代码块");
    }

    {
        System.out.println("Testfather,非静态代码块");
    }
    Testfather(){
        System.out.println("Testfather,非静态代码块");
    }

}
