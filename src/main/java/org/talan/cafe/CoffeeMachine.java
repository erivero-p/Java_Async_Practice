package org.talan.cafe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoffeeMachine {


    public static void makeCoffee(Order order) {
        System.out.println(order);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(order.orderIsReady());

    }

}
