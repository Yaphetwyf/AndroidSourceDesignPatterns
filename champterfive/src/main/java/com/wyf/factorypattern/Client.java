package com.wyf.factorypattern;

public class Client {
    public static void main(String[] args) {
       /* Factory factory=new ConcreteFactory();
        Product product = factory.creteProduct();
        product.method();*/

        FactoryO factoryO = new ConcreteFactoryO();
        Product product = factoryO.createProduct(ContreteProductB.class);
        product.method();
    }
}
