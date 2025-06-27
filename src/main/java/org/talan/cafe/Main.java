package org.talan.cafe;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        Barista barista = new Barista();

        Order orderOne = new Order("Anna", "Solo");
        Order orderTwo = new Order("Jane", "Flat White");
        Order orderThree = new Order("Jude", "Capuccino");

        CompletableFuture<Void> futureOne =  barista.takeOrder(orderOne);
        CompletableFuture<Void> futureTwo =  barista.takeOrder(orderTwo);
        CompletableFuture<Void> futureThree =  barista.takeOrder(orderThree);

        CompletableFuture.allOf(futureOne, futureTwo, futureThree).join();
        System.out.println("All coffees are ready. Shop is closing.");

    }
}