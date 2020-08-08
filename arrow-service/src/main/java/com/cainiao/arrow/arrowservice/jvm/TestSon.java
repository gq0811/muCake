package com.cainiao.arrow.arrowservice.jvm;

public class TestSon extends Testfather{

    static {
        System.out.println("TestSon,静态代码块");
    }

    {
        System.out.println("TestSon,非静态代码块");
    }
    TestSon(){
        System.out.println("TestSon,非静态代码块");
    }

    public static void main(String[] args) {
        TestSon testSon = new TestSon();

    }

}
