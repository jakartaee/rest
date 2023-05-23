/*
 * Copyright (c) 2018 Markus KARG. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package jakarta.ws.rs;

import javax.net.ssl.SSLContext;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ext.RuntimeDelegate;

/**
 * Bootstrap class used to startup a JAX-RS application in Java SE environments.
 * <p>
 * The {@code SeBootstrap} class is available in a Jakarta EE container environment as well; however, support for the Java SE
 * bootstrapping APIs is <em>not required</em> in container environments.
 * </p>
 * <p>
 * In a Java SE environment an application is getting started by the following command using default configuration
 * values (i. e. mounting application at {@code http://localhost:80/} <em>or a different port</em> (there is <em>no
 * particular default port</em> mandated by this specification). As the JAX-RS implementation is free to choose any port
 * by default, the caller will not know the actual port unless explicitly checking the actual configuration of the
 * instance started:
 * </p>
 *
 * <pre>
 * Application app = new MyApplication();
 * SeBootstrap.Configuration config = SeBootstrap.Configuration.builder().build();
 * SeBootstrap.start(app, config).thenAccept(instance -&gt; instance.configuration().port());
 * </pre>
 *
 * <p>
 * Running instances can be instructed to stop serving the application:
 * </p>
 *
 * <pre>
 * SeBootstrap.start(app, config).thenAccept(instance -&gt; { ... instance.stop(); } );
 * </pre>
 *
 * <p>
 * A shutdown callback can be registered which will get invoked once the implementation stops serving the application:
 * </p>
 *
 * <pre>
 * instance.stop().thenAccept(stopResult -&gt; ...));
 * </pre>
 *
 * {@code stopResult} is not further defined but solely acts as a wrapper around a native result provided by the
 * particular JAX-RS implementation. Portable applications should not assume any particular data type or value.
 *
 * <p>
 * Protocol, host address, port and root path can be overridden explicitly. As the JAX-RS implementation is bound to
 * that values, no querying of the actual configuration is needed in that case:
 * </p>
 *
 * <pre>
 * SeBootstrap.Configuration.builder().protocol("HTTPS").host("0.0.0.0").port(8443).rootPath("api").build();
 * </pre>
 *
 * <p>
 * TLS can be configured by explicitly passing a customized {@link SSLContext}:
 * </p>
 *
 * <pre>
 * SSLContext tls = SSLContext.getInstance("TLSv1.2");
 * // ...further initialize context here (see JSSE API)...
 * SeBootstrap.Configuration.builder().protocol("HTTPS").sslContext(tls).build();
 * </pre>
 *
 * <p>
 * In case of HTTPS, client authentication can be enforced to ensure that only <em>trustworthy</em> clients can connect:
 * </p>
 *
 * <pre>
 * SeBootstrap.Configuration.builder().protocol("HTTPS").sslClientAuthentication(SSLClientAuthentication.MANDATORY).build();
 * </pre>
 *
 * <p>
 * Implementations are free to support more use cases by native properties, which effectively render the application
 * non-portable:
 * </p>
 *
 * <pre>
 * SeBootstrap.Configuration.builder().property("productname.foo", "bar").build()
 * </pre>
 *
 * <p>
 * Bulk-loading allows to attach configuration storages easily without the need to write down all properties to be
 * transferred. Hence, even properties unknown to the application author will get channeled into the implementation.
 * This can be done both, explicitly (hence portable) and implicitly (hence <em>not necessarily</em> portable as no
 * particular configuration mechanics are required to be supported by compliant implementations):
 * </p>
 *
 * <pre>
 * // Explicit use of particular configuration mechanics is portable
 * SeBootstrap.Configuration.builder().from((name, type) -&gt; externalConfigurationSystem.getValue(name, type)).build();
 *
 * // Implicitly relying on the support of particular configuration mechanics by
 * // the actual JAX-RS implementation is not necessarily portable
 * SeBootstrap.Configuration.builder().from(externalConfigurationSystem).build();
 * </pre>
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 3.1
 */
public interface SeBootstrap {

    /**
     * Starts the provided application using the specified configuration.
     *
     * <p>
     * This method is intended to be used in Java SE environments only. The outcome of invocations in Jakarta EE container
     * environments is undefined.
     * </p>
     *
     * @param application The application to start up.
     * @param configuration Provides information needed for bootstrapping the application.
     * @return {@code CompletionStage} (possibly asynchronously) producing handle of the running application
     * {@link SeBootstrap.Instance instance}.
     * @see Configuration
     * @since 3.1
     */
    static CompletionStage<Instance> start(final Application application, final Configuration configuration) {
        return RuntimeDelegate.getInstance().bootstrap(application, configuration);
    }

    /**
     * Provides information needed by the JAX-RS implementation for bootstrapping an application.
     * <p>
     * The configuration essentially consists of a set of parameters. While the set of actually effective keys is product
     * specific, the key constants defined by the {@link SeBootstrap.Configuration} interface MUST be effective on all compliant
     * products. Any unknown key MUST be silently ignored.
     * </p>
     *
     * @author Markus KARG (markus@headcrashing.eu)
     * @since 3.1
     */
    public static interface Configuration {

        /**
         * Configuration key for the protocol an application is bound to.
         * <p>
         * A compliant implementation at least MUST accept the strings {@code "HTTP"} and {@code "HTTPS"} if these protocols are
         * supported.
         * </p>
         * <p>
         * The default value is {@code "HTTP"}.
         * </p>
         *
         * @since 3.1
         */
        static final String PROTOCOL = "jakarta.ws.rs.SeBootstrap.Protocol";

        /**
         * Configuration key for the hostname or IP address an application is bound to.
         * <p>
         * A compliant implementation at least MUST accept string values bearing hostnames, IP4 address text representations,
         * and IP6 address text representations. If a hostname string, the special IP4 address string {@code "0.0.0.0"} or
         * {@code "::"} for IP6 is provided, the application MUST be bound to <em>all</em> IP addresses assigned to that
         * hostname. If the hostname string is {@code "localhost"} the application MUST be bound to the local host's loopback
         * adapter <em>only</em>.
         * </p>
         * <p>
         * The default value is {@code "localhost"}.
         * </p>
         *
         * @since 3.1
         */
        static final String HOST = "jakarta.ws.rs.SeBootstrap.Host";

        /**
         * Configuration key for the TCP port an application is bound to.
         *
         * <p>
         * A compliant implementation MUST accept {@code java.lang.Integer} values.
         * </p>
         * <p>
         * There is no default <em>port</em> mandated by this specification, but the default <em>value</em> of this property is
         * {@link #DEFAULT_PORT} (i. e. <code>-1</code>). A compliant implementation MUST use its own default <em>port</em> when
         * the <em>value</em> <code>-1</code> is provided, and MAY apply (but is not obligated to) auto-selection and
         * range-scanning algorithms.
         * </p>
         *
         * @since 3.1
         */
        static final String PORT = "jakarta.ws.rs.SeBootstrap.Port";

        /**
         * Configuration key for the root path an application is bound to.
         * <p>
         * The default value is {@code "/"}.
         * </p>
         *
         * @since 3.1
         */
        static final String ROOT_PATH = "jakarta.ws.rs.SeBootstrap.RootPath";

        /**
         * Configuration key for the secure socket configuration to be used.
         * <p>
         * The default value is {@link SSLContext#getDefault()}.
         * </p>
         *
         * @since 3.1
         */
        static final String SSL_CONTEXT = "jakarta.ws.rs.SeBootstrap.SSLContext";

        /**
         * Configuration key for the secure socket client authentication policy.
         *
         * <p>
         * A compliant implementation MUST accept {@link SSLClientAuthentication} enums.
         * </p>
         * <p>
         * The default value is {@code SSLClientAuthentication#NONE}.
         * </p>
         *
         * @since 3.1
         */
        static final String SSL_CLIENT_AUTHENTICATION = "jakarta.ws.rs.SeBootstrap.SSLClientAuthentication";

        /**
         * Secure socket client authentication policy
         *
         * <p>
         * This policy is used in secure socket handshake to control whether the server <em>requests</em> client authentication,
         * and whether <em>successful</em> client authentication is <em>mandatory</em> (i. e. connection attempt will fail for
         * invalid clients).
         * </p>
         *
         * @author Markus KARG (markus@headcrashing.eu)
         * @since 3.1
         */
        public enum SSLClientAuthentication {

            /**
             * Server will <em>not request</em> client authentication.
             *
             * @since 3.1
             */
            NONE,

            /**
             * Client authentication is performed, but invalid clients are <em>accepted</em>.
             *
             * @since 3.1
             */
            OPTIONAL,

            /**
             * Client authentication is performed, and invalid clients are <em>rejected</em>.
             *
             * @since 3.1
             */
            MANDATORY
        }

        /**
         * Special value for {@link #PORT} property indicating that the implementation MUST scan for a free port.
         *
         * @since 3.1
         */
        static final int FREE_PORT = 0;

        /**
         * Special value for {@link #PORT} property indicating that the implementation MUST use its default port.
         *
         * @since 3.1
         */
        static final int DEFAULT_PORT = -1;

        /**
         * Returns the value of the property with the given name, or {@code null} if there is no property of that name.
         *
         * @param name a {@code String} specifying the name of the property.
         * @return an {@code Object} containing the value of the property, or {@code null} if no property exists matching the
         * given name.
         * @since 3.1
         */
        Object property(String name);

        /**
         * Returns whether the property with the given name is configured, either explicitly or by default.
         *
         * @param name a {@code String} specifying the name of the property.
         * @return {@code false} if no property exists matching the given name, {@code true} otherwise.
         * @since 3.1
         */
        default boolean hasProperty(String name) {
            return property(name) != null;
        }

        /**
         * Convenience method to get the {@code protocol} to be used.
         * <p>
         * Same as if calling {@link #property(String) (String) property(PROTOCOL)}.
         * </p>
         *
         * @return protocol to be used (e. g. {@code "HTTP")}.
         * @throws ClassCastException if protocol is not a {@link String}.
         * @see SeBootstrap.Configuration#PROTOCOL
         * @since 3.1
         */
        default String protocol() {
            return (String) property(PROTOCOL);
        }

        /**
         * Convenience method to get the {@code host} to be used.
         * <p>
         * Same as if calling {@link #property(String) (String) property(HOST)}.
         * </p>
         *
         * @return host name or IP address to be used (e. g. {@code "localhost"} or {@code "0.0.0.0"}).
         * @throws ClassCastException if host is not a {@link String}.
         * @see SeBootstrap.Configuration#HOST
         * @since 3.1
         */
        default String host() {
            return (String) property(HOST);
        }

        /**
         * Convenience method to get the actually used {@code port}.
         * <p>
         * Same as if calling {@link #property(String) (int) property(PORT)}.
         * </p>
         * <p>
         * If the port was <em>not explicitly</em> given, this will return the port chosen implicitly by the JAX-RS
         * implementation.
         * </p>
         *
         * @return port number <em>actually</em> used (e. g. {@code 8080}).
         * @throws ClassCastException if port is not an {@code Integer}.
         * @see SeBootstrap.Configuration#PORT
         * @since 3.1
         */
        default int port() {
            return (int) property(PORT);
        }

        /**
         * Convenience method to get the {@code rootPath} to be used.
         * <p>
         * Same as if calling {@link #property(String) (String) property(ROOT_PATH)}.
         * </p>
         *
         * @return root path to be used, e. g. {@code "/"}.
         * @throws ClassCastException if root path is not a {@link String}.
         * @see SeBootstrap.Configuration#ROOT_PATH
         * @since 3.1
         */
        default String rootPath() {
            return (String) property(ROOT_PATH);
        }

        /**
         * Convenience method to get the {@code sslContext} to be used.
         * <p>
         * Same as if calling {@link #property(String) (SSLContext) property(SSL_CONTEXT)}.
         * </p>
         *
         * @return root path to be used, e. g. {@code "/"}.
         * @throws ClassCastException if sslContext is not a {@link SSLContext}.
         * @see SeBootstrap.Configuration#SSL_CONTEXT
         * @since 3.1
         */
        default SSLContext sslContext() {
            return (SSLContext) property(SSL_CONTEXT);
        }

        /**
         * Convenience method to get the secure socket client authentication policy.
         * <p>
         * Same as if calling {@link #property(String) (SSLClientAuthentication) property(SSL_CLIENT_AUTHENTICATION)}.
         * </p>
         *
         * @return client authentication mode, e. g. {@code NONE}.
         * @throws ClassCastException if sslClientAuthentication is not a {@link SSLClientAuthentication}.
         * @see SeBootstrap.Configuration#SSL_CLIENT_AUTHENTICATION
         * @since 3.1
         */
        default SSLClientAuthentication sslClientAuthentication() {
            return (SSLClientAuthentication) property(SSL_CLIENT_AUTHENTICATION);
        }

        /**
         * Creates a new bootstrap configuration builder instance.
         *
         * @return {@link Builder} for bootstrap configuration.
         * @since 3.1
         */
        static Builder builder() {
            return RuntimeDelegate.getInstance().createConfigurationBuilder();
        };

        /**
         * Builder for bootstrap {@link Configuration}.
         *
         * @author Markus KARG (markus@headcrashing.eu)
         * @since 3.1
         */
        static interface Builder {

            /**
             * Builds a bootstrap configuration instance from the provided property values.
             *
             * @return {@link Configuration} built from provided property values.
             * @since 3.1
             */
            Configuration build();

            /**
             * Sets the property {@code name} to the provided {@code value}.
             * <p>
             * This method does not check the validity, type or syntax of the provided value.
             * </p>
             *
             * @param name name of the parameter to set.
             * @param value value to set, or {@code null} to use the default value.
             * @return the updated builder.
             * @since 3.1
             */
            Builder property(String name, Object value);

            /**
             * Convenience method to set the {@code protocol} to be used.
             * <p>
             * Same as if calling {@link #property(String, Object) property(PROTOCOL, value)}.
             * </p>
             *
             * @param protocol protocol parameter of this configuration, or {@code null} to use the default value.
             * @return the updated builder.
             * @see SeBootstrap.Configuration#PROTOCOL
             * @since 3.1
             */
            default Builder protocol(String protocol) {
                return property(PROTOCOL, protocol);
            }

            /**
             * Convenience method to set the {@code host} to be used.
             * <p>
             * Same as if calling {@link #property(String, Object) property(HOST, value)}.
             * </p>
             *
             * @param host host parameter (IP address or hostname) of this configuration, or {@code null} to use the default value.
             * @return the updated builder.
             * @see SeBootstrap.Configuration#HOST
             * @since 3.1
             */
            default Builder host(String host) {
                return property(HOST, host);
            }

            /**
             * Convenience method to set the {@code port} to be used.
             * <p>
             * Same as if calling {@link #property(String, Object) property(PORT, value)}.
             * </p>
             *
             * @param port port parameter of this configuration, or {@code null} to use the default value.
             * @return the updated builder.
             * @see SeBootstrap.Configuration#PORT
             * @since 3.1
             */
            default Builder port(Integer port) {
                return property(PORT, port);
            }

            /**
             * Convenience method to set the {@code rootPath} to be used.
             * <p>
             * Same as if calling {@link #property(String, Object) property(ROOT_PATH, value)}.
             * </p>
             *
             * @param rootPath rootPath parameter of this configuration, or {@code null} to use the default value.
             * @return the updated builder.
             * @see SeBootstrap.Configuration#ROOT_PATH
             * @since 3.1
             */
            default Builder rootPath(String rootPath) {
                return property(ROOT_PATH, rootPath);
            }

            /**
             * Convenience method to set the {@code sslContext} to be used.
             * <p>
             * Same as if calling {@link #property(String, Object) property(SSL_CONTEXT, value)}.
             * </p>
             *
             * @param sslContext sslContext parameter of this configuration, or {@code null} to use the default value.
             * @return the updated builder.
             * @see SeBootstrap.Configuration#SSL_CONTEXT
             * @since 3.1
             */
            default Builder sslContext(SSLContext sslContext) {
                return property(SSL_CONTEXT, sslContext);
            }

            /**
             * Convenience method to set SSL client authentication policy.
             * <p>
             * Same as if calling {@link #property(String, Object) property(SSL_CLIENT_AUTHENTICATION, value)}.
             * </p>
             *
             * @param sslClientAuthentication SSL client authentication mode of this configuration
             * @return the updated builder.
             * @see SeBootstrap.Configuration#SSL_CLIENT_AUTHENTICATION
             * @since 3.1
             */
            default Builder sslClientAuthentication(SSLClientAuthentication sslClientAuthentication) {
                return property(SSL_CLIENT_AUTHENTICATION, sslClientAuthentication);
            }

            /**
             * Convenience method for bulk-loading configuration from a property supplier.
             * <p>
             * Implementations ask the passed provider function for the actual values of all their supported properties, before
             * returning from this configuration method. For each single request the implementation provides the name of the
             * property and the expected data type of the value. If no such property exists (i. e. either the name is unknown or
             * misspelled, or the type does not exactly match), the {@link Optional} is {@link Optional#empty() empty}.
             * </p>
             *
             * @param <T> Type of the requested property value.
             * @param propertiesProvider Retrieval function of externally managed properties. MUST NOT return {@code null}.
             * @return the updated builder.
             * @since 3.1
             */
            <T> Builder from(BiFunction<String, Class<T>, Optional<T>> propertiesProvider);

            /**
             * Optional convenience method to bulk-load external configuration.
             * <p>
             * Implementations are free to support any external configuration mechanics, or none at all. It is completely up to the
             * implementation what set of properties is effectively loaded from the provided external configuration, possibly none
             * at all.
             * </p>
             * <p>
             * If the passed external configuration mechanics is unsupported, this method MUST simply do nothing.
             * </p>
             * <p>
             * Portable applications should not call this method, as the outcome is completely implementation-specific.
             * </p>
             *
             * @param externalConfig source of externally managed properties
             * @return the updated builder.
             * @since 3.1
             */
            default Builder from(Object externalConfig) {
                return this;
            }

        }
    }

    /**
     * Handle of the running application instance.
     *
     * @author Markus KARG (markus@headcrashing.eu)
     * @since 3.1
     */
    public interface Instance {

        /**
         * Provides access to the configuration <em>actually</em> used by the implementation used to create this instance.
         * <p>
         * This may, or may not, be the same instance passed to {@link SeBootstrap#start(Application, Configuration)}, not even an
         * equal instance, as implementations MAY create a new intance and MUST update at least the {@code PORT} property with
         * the actually used value. Portable applications should not make any assumptions but always explicitly read the actual
         * values from the configuration returned from this method.
         * </p>
         *
         * @return The configuration actually used to create this instance.
         * @since 3.1
         */
        public Configuration configuration();

        /**
         * Initiate immediate shutdown of running application instance.
         *
         * @return {@code CompletionStage} asynchronously shutting down this application instance.
         * @since 3.1
         */
        public CompletionStage<StopResult> stop();

        /**
         * Result of stopping the application instance.
         *
         * @author Markus KARG (markus@headcrashing.eu)
         * @since 3.1
         */
        public interface StopResult {

            /**
             * Provides access to the wrapped native shutdown result.
             * <p>
             * Implementations may, or may not, have native shutdown results. Portable applications should not invoke this method,
             * as the outcome is undefined.
             * </p>
             *
             * @param <T> Requested type of the native result to return.
             * @param nativeClass Requested type of the native result to return.
             * @return Native result of shutting down the running application instance or {@code null} if the implementation has no
             * native result.
             * @throws ClassCastException if the result is not {@code null} or is not assignable to the type {@code T}.
             * @since 3.1
             */
            public <T> T unwrap(Class<T> nativeClass);
        }

        /**
         * Provides access to the wrapped native handle of the application instance.
         * <p>
         * Implementations may, or may not, have native handles. Portable applications should not invoke this method, as the
         * outcome is undefined.
         * </p>
         *
         * @param <T> Requested type of the native handle to return.
         * @param nativeClass Requested type of the native handle to return.
         * @return Native handle of the running application instance or {@code null} if the implementation has no native handle.
         * @throws ClassCastException if the handle is not {@code null} and is not assignable to the type {@code T}.
         * @since 3.1
         */
        public <T> T unwrap(Class<T> nativeClass);
    }

}
