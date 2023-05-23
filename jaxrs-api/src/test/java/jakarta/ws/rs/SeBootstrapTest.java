package jakarta.ws.rs;

import javax.net.ssl.SSLContext;
import java.util.concurrent.CompletionStage;

import jakarta.ws.rs.SeBootstrap;
import jakarta.ws.rs.SeBootstrap.Configuration;
import jakarta.ws.rs.SeBootstrap.Configuration.SSLClientAuthentication;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ext.RuntimeDelegate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Unit tests for {@link SeBootstrap}
 *
 * @author Markus KARG (markus@headcrashing.eu)
 * @since 3.1
 */
public final class SeBootstrapTest {

    /**
     * Installs a new {@code RuntimeDelegate} mock before each test case, as some test cases need to find a pristine
     * <em>installed</em> RuntimeDelegate.
     */
    @BeforeEach
    public final void setUp() {
        RuntimeDelegate.setInstance(mock(RuntimeDelegate.class));
    }

    /**
     * Uninstalls the {@code RuntimeDelegate} mock after each test case to be sure that the <em>next</em> test case does not
     * use a possibly cluttered instance.
     */
    @AfterEach
    public final void tearDown() {
        RuntimeDelegate.setInstance(null);
    }

    /**
     * Assert that {@link SeBootstrap#start(Application, Configuration)} will delegate to
     * {@link RuntimeDelegate#bootstrap(Application, Configuration)}.
     *
     * @since 3.1
     */
    @Test
    public final void shouldDelegateApplicationStartupToRuntimeDelegate() {
        // given
        final Application application = mock(Application.class);
        final Configuration configuration = mock(Configuration.class);
        @SuppressWarnings("unchecked")
        final CompletionStage<SeBootstrap.Instance> nativeCompletionStage = mock(CompletionStage.class);
        given(RuntimeDelegate.getInstance().bootstrap(application, configuration)).willReturn(nativeCompletionStage);

        // when
        final CompletionStage<SeBootstrap.Instance> actualCompletionStage = SeBootstrap.start(application, configuration);

        // then
        assertThat(actualCompletionStage, is(sameInstance(nativeCompletionStage)));
    }

    /**
     * Assert that {@link SeBootstrap.Configuration#builder()} will delegate to
     * {@link RuntimeDelegate#createConfigurationBuilder()}.
     *
     * @since 3.1
     */
    @Test
    public final void shouldDelegateConfigurationBuilderCreationToRuntimeDelegate() {
        // given
        final SeBootstrap.Configuration.Builder nativeConfigurationBuilder = mock(SeBootstrap.Configuration.Builder.class);
        given(RuntimeDelegate.getInstance().createConfigurationBuilder()).willReturn(nativeConfigurationBuilder);

        // when
        final SeBootstrap.Configuration.Builder actualConfigurationBuilder = SeBootstrap.Configuration.builder();

        // then
        assertThat(actualConfigurationBuilder, is(sameInstance(nativeConfigurationBuilder)));
    }

    /**
     * Assert that {@code Configuration.Builder}'s {@code from(external configuration)} bulk-loading method simply returns
     * {@code this}, but does nothing else, as it is a no-op implementation.
     *
     * @since 3.1
     */
    @Test
    public final void shouldReturnSameConfigurationBuilderInstanceWhenLoadingExternalConfiguration() {
        // given
        final SeBootstrap.Configuration.Builder previousConfigurationBuilder = spy(SeBootstrap.Configuration.Builder.class);
        final Object someExternalConfiguration = mock(Object.class);

        // when
        final SeBootstrap.Configuration.Builder currentConfigurationBuilder = previousConfigurationBuilder
                .from(someExternalConfiguration);

        // then
        assertThat(currentConfigurationBuilder, is(sameInstance(previousConfigurationBuilder)));
        verify(previousConfigurationBuilder).from(someExternalConfiguration);
        verifyNoMoreInteractions(previousConfigurationBuilder);
    }

    /**
     * Assert that {@code Configuration.Builder}'s convenience methods delegate to its generic {@code property(name, value)}
     * method using the <em>right</em> property name.
     *
     * @since 3.1
     */
    @Test
    public final void shouldPushCorrespondingPropertiesIntoConfigurationBuilder() {
        // given
        final String someProtocolValue = mockString();
        final String someHostValue = mockString();
        final int somePortValue = mockInt();
        final String someRootPathValue = mockString();
        final SSLContext someSSLContextValue = mock(SSLContext.class);
        final SSLClientAuthentication someSSLClientAuthenticationValue = SSLClientAuthentication.MANDATORY;
        final SeBootstrap.Configuration.Builder configurationBuilder = spy(SeBootstrap.Configuration.Builder.class);

        // when
        configurationBuilder.protocol(someProtocolValue);
        configurationBuilder.host(someHostValue);
        configurationBuilder.port(somePortValue);
        configurationBuilder.rootPath(someRootPathValue);
        configurationBuilder.sslContext(someSSLContextValue);
        configurationBuilder.sslClientAuthentication(someSSLClientAuthenticationValue);

        // then
        verify(configurationBuilder).property(SeBootstrap.Configuration.PROTOCOL, someProtocolValue);
        verify(configurationBuilder).property(SeBootstrap.Configuration.HOST, someHostValue);
        verify(configurationBuilder).property(SeBootstrap.Configuration.PORT, somePortValue);
        verify(configurationBuilder).property(SeBootstrap.Configuration.ROOT_PATH, someRootPathValue);
        verify(configurationBuilder).property(SeBootstrap.Configuration.SSL_CONTEXT, someSSLContextValue);
        verify(configurationBuilder).property(SeBootstrap.Configuration.SSL_CLIENT_AUTHENTICATION,
                someSSLClientAuthenticationValue);
    }

    /**
     * Assert that {@code Configuration}'s convenience methods delegate to its generic {@code property(name)} method using
     * the <em>right</em> property name.
     *
     * @since 3.1
     */
    @Test
    public final void shouldPullCorrespondingPropertiesFromConfiguration() {
        // given
        final String someProtocolValue = mockString();
        final String someHostValue = mockString();
        final int somePortValue = mockInt();
        final String someRootPathValue = mockString();
        final SSLContext someSSLContextValue = mock(SSLContext.class);
        final SSLClientAuthentication someSSLClientAuthenticationValue = SSLClientAuthentication.MANDATORY;
        final SeBootstrap.Configuration configuration = spy(SeBootstrap.Configuration.class);
        given(configuration.property(SeBootstrap.Configuration.PROTOCOL)).willReturn(someProtocolValue);
        given(configuration.property(SeBootstrap.Configuration.HOST)).willReturn(someHostValue);
        given(configuration.property(SeBootstrap.Configuration.PORT)).willReturn(somePortValue);
        given(configuration.property(SeBootstrap.Configuration.ROOT_PATH)).willReturn(someRootPathValue);
        given(configuration.property(SeBootstrap.Configuration.SSL_CONTEXT)).willReturn(someSSLContextValue);
        given(configuration.property(SeBootstrap.Configuration.SSL_CLIENT_AUTHENTICATION))
                .willReturn(someSSLClientAuthenticationValue);

        // when
        final String actualProtocolValue = configuration.protocol();
        final String actualHostValue = configuration.host();
        final int actualPortValue = configuration.port();
        final String actualRootPathValue = configuration.rootPath();
        final SSLContext actualSSLContextValue = configuration.sslContext();
        final SSLClientAuthentication actualSSLClientAuthenticationValue = configuration.sslClientAuthentication();

        // then
        assertThat(actualProtocolValue, is(sameInstance(someProtocolValue)));
        assertThat(actualHostValue, is(sameInstance(someHostValue)));
        assertThat(actualPortValue, is(somePortValue));
        assertThat(actualRootPathValue, is(sameInstance(someRootPathValue)));
        assertThat(actualSSLContextValue, is(sameInstance(someSSLContextValue)));
        assertThat(actualSSLClientAuthenticationValue, is(sameInstance(someSSLClientAuthenticationValue)));
    }

    private static String mockString() {
        return Integer.toString(mockInt());
    }

    private static int mockInt() {
        return (int) Math.round(Integer.MAX_VALUE * Math.random());
    }

}
