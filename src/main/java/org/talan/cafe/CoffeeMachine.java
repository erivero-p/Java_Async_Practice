package org.talan.cafe;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class CoffeeMachine {

    // async but blocking version
    public static void makeCoffee(Order order) {
        System.out.println("Coffee Machine Preparing: " + order);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(order.orderIsReady());

    }

    // async and non-blocking version
    public static Mono<String> makeCoffeeReactively(Order order) {
        return Mono.fromCallable(() -> {
            System.out.println("Coffee Machine Preparing: " + order);
            Thread.sleep(2000);
            return order.orderIsReady();
        });
    }

}
