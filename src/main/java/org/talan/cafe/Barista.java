package org.talan.cafe;

import java.util.concurrent.CompletableFuture;

public class Barista {

    // this takes orders asynchronously but in a blocking way
    public  CompletableFuture<Void>  takeOrder(Order order) {
        System.out.println("Taking order: " + order);
        return CompletableFuture.runAsync(() -> CoffeeMachine.makeCoffee(order));
    }

    // this takes order in an async and non-blocking way
    public void takeOrderReactively(Order order) {
        System.out.println("Taking order: " + order);
        CoffeeMachine.makeCoffeeReactively(order)
            .subscribe(System.out::println); // same as result -> { System.out.println(result); }
        System.out.println("Bartender is free for taking other orders.");
    }
}
