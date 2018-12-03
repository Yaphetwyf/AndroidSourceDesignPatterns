package com.wyf.factorypattern;

public class ConcreteFactory extends Factory {
    @Override
    public Product creteProduct() {
        return new ConcreteProductA();
    }
}
