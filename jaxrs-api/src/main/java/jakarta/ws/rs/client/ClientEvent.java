package jakarta.ws.rs.client;

/**
 * Represents a client lifecycle event.
 * <p>
 * This class encapsulates information about events that occur during the lifecycle
 * of a {@link Client}, such as connection establishment, closure, and failures.
 * </p>
 *
 * @since 4.0
 */
public class ClientEvent {

    private final Client client;
    private final ClientEventType type;
    private final Throwable cause; // for failure type events

    /**
     * Constructs a new client event.
     *
     * @param client the client instance associated with this event
     * @param type the type of event
     * @param cause the cause of the event (may be null for non-failure events)
     */
    public ClientEvent(Client client, ClientEventType type, Throwable cause) {
        this.client = client;
        this.type = type;
        this.cause = cause;
    }

    /**
     * Constructs a new client event without a cause.
     *
     * @param client the client instance associated with this event
     * @param type the type of event
     */
    public ClientEvent(Client client, ClientEventType type) {
        this(client, type, null);
    }

    /**
     * Gets the client instance associated with this event.
     *
     * @return the client instance
     */
    public Client getClient() {
        return client;
    }

    /**
     * Gets the type of this event.
     *
     * @return the event type
     */
    public ClientEventType getType() {
        return type;
    }

    /**
     * Gets the cause of this event, if applicable.
     * <p>
     * This is typically non-null only for failure-type events such as
     * {@link ClientEventType#CONNECTION_FAILED}.
     * </p>
     *
     * @return the cause of the event, or null if not applicable
     */
    public Throwable getCause() {
        return cause;
    }
}
