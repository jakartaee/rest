package jakarta.ws.rs.client;

/**
 * A listener interface for receiving client lifecycle events.
 * <p>
 * Implementations of this interface can be registered with a {@link ClientBuilder}
 * to receive notifications about client lifecycle events such as connection,
 * disconnection, and failures.
 * </p>
 *
 * @since 4.0
 */
public interface ClientListener {

    /**
     * Called when a client event occurs.
     *
     * @param event the client event containing information about the event type,
     *              the client instance, and optionally a cause for failure events
     */
    void onEvent(ClientEvent event);

}
