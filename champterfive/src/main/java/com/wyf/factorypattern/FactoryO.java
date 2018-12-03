package com.wyf.factorypattern;

public abstract class FactoryO {
    public abstract <T extends Product> T createProduct(Class<T>clz);
}
