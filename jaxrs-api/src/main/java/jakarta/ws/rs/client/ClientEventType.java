package jakarta.ws.rs.client;

/**
 * Enumeration of client lifecycle event types.
 * <p>
 * These event types represent the various states and transitions that can occur
 * during the lifecycle of a {@link Client}.
 * </p>
 *
 * @since 4.0
 */
public enum ClientEventType {

    /**
     * Event fired when the client has been closed.
     * <p>
     * This event is triggered when the {@link Client#close()} method is invoked.
     * </p>
     */
    CLOSED,

    /**
     * Event fired when the client has successfully established a connection.
     */
    CONNECTED,

    /**
     * Event fired when a connection attempt has failed.
     * <p>
     * For this event type, the {@link ClientEvent#getCause()} method will typically
     * return the exception that caused the failure.
     * </p>
     */
    CONNECTION_FAILED,

    /**
     * Event fired when the client is attempting to reconnect after a connection loss.
     */
    RECONNECTING

}
