package org.talan.cafe;

public class Order {
    public final String client;
    public final String coffee;

    public Order(String client, String coffee) {
        this.client = client;
        this.coffee = coffee;
    }

    public String getClient() {
        return client;
    }
    public String getOrderName() {
        return coffee;
    }

    @Override
    public String toString() {
        return "[" + client + ": " + coffee + "]";
    }

    public String orderIsReady() {
        return client + "'s "+ coffee + " coffee is ready";
    }
}
