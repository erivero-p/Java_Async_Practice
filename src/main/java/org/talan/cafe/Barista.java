package org.talan.cafe;

import java.util.concurrent.CompletableFuture;

public class Barista {

    // this takes orders asynchronously but in a blocking way
    public  CompletableFuture<Void>  takeOrder(Order order) {
        System.out.println("Taking order: " + order);
        return CompletableFuture.runAsync(() -> CoffeeMachine.makeCoffee(order));
    }
}
