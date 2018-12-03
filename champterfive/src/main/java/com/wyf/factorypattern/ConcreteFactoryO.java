package com.wyf.factorypattern;

public class ConcreteFactoryO extends FactoryO{
    @Override
    public <T extends Product> T createProduct(Class<T> clz) {
        Product product=null;
        try {
           product = (Product) Class.forName(clz.getName()).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T) product;
    }
}
