
package jaxrs.examples.resource;

import java.util.concurrent.atomic.AtomicReference;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetBean {

    private final AtomicReference<String> message = new AtomicReference<>();

    public GreetBean() {
        this.message.set("Hello");
    }

    String getMessage(String lang) {
        return message.get();
    }

    void setMessage(String message) {
        this.message.set(message);
    }
}
