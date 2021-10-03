/**
 * Java SE bootstrap examples.
 *
 * <ul>
 * <li>{@link BasicJavaSeBootstrapExample} - Basic example solely using defaults</li>
 * <li>{@link ExplicitJavaSeBootstrapExample} - Explicit configuration instead of defaults</li>
 * <li>{@link HttpsJavaSeBootstrapExample} - HTTPS instead of HTTP</li>
 * <li>{@link TlsJavaSeBootstrapExample} - TLS customization</li>
 * <li>{@link ClientAuthenticationJavaSeBootstrapExample} - HTTPS with <em>bidirectional</em> authentication</li>
 * <li>{@link NativeJavaSeBootstrapExample} - Custom (non-portable) properties (using Jersey's Grizzly2 backend)</li>
 * <li>{@link PropertyProviderJavaSeBootstrapExample} - Bulk-loading configuration using a property provider (using
 * Microprofile Config <em>explicitly</em>, hence in a non-optional, portable way)
 * <li>{@link ExternalConfigJavaSeBootstrapExample} - Bulk-loading configuration from external configuration mechanics
 * (using Microprofile Config <em>implicitly</em>, hence in an optional, <em>not necessarily</em> portable way)
 * </ul>
 *
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 3.1
 * @see jakarta.ws.rs.SeBootstrap
 */
package jaxrs.examples.bootstrap;
