# Asynchronous Cafe

In the following exercise, I’ll be putting into practice the concepts of asynchrony and reactivity in Java.

To understand the concept of blocking vs non-blocking, I thought of a coffee shop, where, while the barista has put a coffee to brew in a coffee machine, they can take other orders (at the risk of burning the coffee, but it works for our example), instead of waiting for the machine to finish and getting “blocked” in the meantime.

[Disclaimer] The reactive version, is still a bit blocking, as I’m still using sleep, but it is for simplicity purpose.

If you want to replicate the exercise, you have a subject (chatGPT made, dont take it too serious) [below](#exercise-subject).
Also, If you struggle with English, Spanish version of the code explanation [available here](#spanish-version).

# Code explanation

### Asynchronous and blocking version

In the first version of the coffee shop, I use asynchronous but blocking operations. The barista launches a task in a new thread using `CompletableFuture.runAsync`, delegating the coffee preparation to the machine. `runAsync` receives as a parameter, through a lambda, the `makeCoffee` function from the `CoffeeMachine`. This function simply simulates the waiting time with `Thread.sleep(2000)`.

Why do I say it’s asynchronous but blocking? Because although the main process keeps running while the coffee is being made, the thread preparing the coffee is blocked until it finishes.

### Reactive version (asynchronous and non-blocking)

In this case, the `CoffeeMachine`, in addition to simulating the waiting time, will return the following: `Mono<String>`

> Mono (for a single value) and Flux (for multiple) are the main types in Java (from the Reactor library) for working with non-blocking data streams. Unlike Future, their execution is reactive and non-blocking.
> 

```java
    // async and non-blocking version
    public static Mono<String> makeCoffeeReactively(Order order) {
        return Mono.fromCallable(() -> {
            System.out.println("Coffee Machine Preparing: " + order);
            Thread.sleep(2000);
            return order.orderIsReady();
        });
    }

```

So, by saying the coffee machine will return a `Mono<String>`, we indicate that the result of completing that task (once the time comes, because it’s Lazy*) will be a String (in this case, indicating the order is ready). It’s Lazy, because when returning the Mono, we are calling `fromCallable()`, which creates a *Publisher*, meaning the task won’t execute immediately when the thread is created, but only once “someone” subscribes to the mono. But, what does it mean to subscribe?

> In reactive programming with Java, we have several components to implement: Publisher, Subscriber, Subscription, and Processor. The publisher produces the items being requested (coffees in this case), the subscriber consumes those items (in this case the barista will pick them up from the coffee machine), and a subscription relationship is needed between them. We’re not using a processor in this case, but it can act both as a publisher and a subscriber by transforming the data.
> 

To subscribe and thus trigger the task’s execution, we’ll use the `.subscribe()` method from the Mono returned by the coffee machine. We’ll pass as a parameter what we want to happen once the task completes, in this case, printing it since it’s a String. (Friendly reminder: Mono and Flux are flow-based objects, similar to Streams, so we can chain operations with them) like this:

```java
    public void takeOrderReactively(Order order) {
        System.out.println("Taking order: " + order);
        CoffeeMachine.makeCoffeeReactively(order)
            .subscribe(System.out::println); // same as result -> { System.out.println(result); }
        System.out.println("Bartender is free for taking other orders.");
    }

```

# Exercise Subject

### **Objective:**

Implement a simple simulation of a coffee shop using Java that explores the differences between asynchronous **blocking** and **non-blocking reactive** programming styles.

---

### 🏗️ Context:

Imagine a barista working at a coffee shop. When a customer places an order, the barista uses a coffee machine to prepare the coffee. While the coffee is being made, the barista should be free to take other orders.

You’ll implement two versions of this scenario:

1. **Asynchronous but blocking version**
2. **Reactive (asynchronous and non-blocking) version**

---

### 🧩 Constraints & Guidelines

### ☕ Domain Objects

- You should have an `Order` class that represents a coffee order. It must include a way to return a message like `"Coffee is ready"` once it’s complete.
- You should have a `CoffeeMachine` class responsible for preparing the coffee.

---

### ✅ Version 1: Asynchronous but Blocking

- Implement a method `makeCoffee(Order order)` inside `CoffeeMachine` that:
    - Simulates a 2-second preparation time (e.g., with `Thread.sleep(2000)`).
- From the barista's side, launch this task asynchronously using `CompletableFuture.runAsync`.
- Although the task is started asynchronously, highlight how the thread preparing the coffee is blocked during the wait.

---

### ✅ Version 2: Reactive (Asynchronous and Non-Blocking)

- Modify `CoffeeMachine` to have a method `makeCoffeeReactively(Order order)` that:
    - Returns a `Mono<String>` (from Project Reactor).
    - Uses `Mono.fromCallable(...)` to simulate the coffee preparation (same 2-second delay).
- Implement the barista logic so that it *subscribes* to the `Mono` result to process the order (e.g., print a message when coffee is ready).
- Show how the main thread can continue taking new orders immediately after subscribing.

---

### 🔎 Learning Goals

- Understand the difference between asynchronous and non-blocking execution.
- Use `CompletableFuture` for asynchronous programming.
- Use `Mono` from Project Reactor to apply reactive programming principles.
- Demonstrate lazy execution and publisher/subscriber relationships.

# Spanish Version

### Versión asíncrona y bloqueante

En la primera versión de la cafetería, utilizo operaciones asíncronas, pero bloqueantes. El barista
lanza una tarea en un nuevo hilo usando `CompletableFuture.runAsync`, delegando la preparación del café a la máquina, `runAsync` recibirá por parámetro, con una lambda, la función , la función `makeCoffee` de la `CoffeeMachine`.  Esta, lo único que hace es simular el tiempo de espera con  `Thread.*sleep*(2000)`.

¿Por qué digo es es asíncrona pero bloqueante? Porque, aunque el proceso principal siga funcionando mientras se hace este café, el hilo que está preparando el café, estará bloqueado hasta que termine.

### Versión reactiva (asíncrona y no bloqueante)

En este caso, la `CoffeeMachine` , además de simular el tiempo de espera, va a devolver lo siguiente: `Mono<String>`

> **Mono** (para un único valor) y **Flux** (para múltiples) son los tipos principales de Java (de la librería Reactor) para trabajar con flujos de datos no bloqueantes. A diferencia de `Future`, su ejecución es *reactiva* y no bloqueante.
> 

```java
    // async and non-blocking version
    public static Mono<String> makeCoffeeReactively(Order order) {
        return Mono.fromCallable(() -> {
            System.out.println("Coffee Machine Preparing: " + order);
            Thread.sleep(2000);
            return order.orderIsReady();
        });
    }

```

Entonces, al decir que la cafetera va a devolver `Mono<String>` indicamos que,  el resultado de completar esa tarea (cuando llegue el momento de completarla, porque es Lazy*), obtendremos una String (en este caso, indicando que el pedido está listo). Es Lazy, porque a la hora de devolver el Mono, estamos llamando a `*fromCallable()`,* con lo que estamos creando un Publisher*,* de forma que la tarea se ejecutará, no inmediatamente al crear el hilo, sino cuando “alguien” se subscriba al mono. Pero, ¿cómo que se suscriba? 

> En programación reactiva con Java, tenemos una serie de componentes a implementar: Publisher, Subscriber, Subscription y Processor. Digamos que, el publisher produce los items que estemos solicitando (cafés en este caso), el subscriber consume esos ítems (en este caso el barista será quien los recoja de la cafetería), para lo cual necesita una relación de subscripción. El processor  no lo estamos usando en este caso, pero puede actuar como publisher y subscriber transformando los datos.
> 

Para subscribirnos, y así, lanzar la ejecución de la tarea, usaremos el método `.subscribe()` del Mono que nos devuelve la cafetera, a la que le pasaremos por parámetro lo que queramos que se haga con el resultado de la tarea, en este caso, imprimirlo porque es una String. (Friendly reminder de que Mono y Flux son objetos de flujo, parecidos a los Streams, por lo que podemos encadenar operaciones con ellos) tal que así:

```java
    public void takeOrderReactively(Order order) {
        System.out.println("Taking order: " + order);
        CoffeeMachine.makeCoffeeReactively(order)
            .subscribe(System.out::println); // same as result -? { System.out.println(result); }
        System.out.println("Bartender is free for taking other orders.");
    }
```
