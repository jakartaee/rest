/*******************************************************************
* Copyright (c) 2021 Eclipse Foundation
*
* This specification document is made available under the terms
* of the Eclipse Foundation Specification License v1.0, which is
* available at https://www.eclipse.org/legal/efsl.php.
*******************************************************************/
package jakarta.ws.rs.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.RuntimeDelegate;

/**
 * A {@code EntityPart} is one part of a multipart entity. As defined in
 * <a href="https://tools.ietf.org/html/rfc7578">RFC 7578</a>, a multipart
 * request or response must have a content type of "multipart/form-data" with a
 * {@code boundary} parameter indicating where one part ends the next may begin.
 * <p>
 * Multipart entities may be received in a resource method as a collection of
 * parts (e.g. {@code List<EntityPart>}) or as a form parameter (ex:
 * {@code @FormParam("part1Name") EntityPart part1}).
 * </p>
 * <p>
 * Likewise, a client may receive a multipart response by reading the returned
 * entity as a collection of EntityParts (ex: {@code response.readEntity(new
 * GenericType<List<EntityPart>>() {})}).
 * </p>
 * <p>
 * In order to send a multipart entity either as a client request or a response
 * from a resource method, you may create the Lists using
 * {@code EntityPart.Builder}. For example:
 * </p>
 *
 * <pre>
 * Client c = ClientBuilder.newClient();
 *WebTarget target = c.target(someURL);
 *List&lt;EntityPart&gt; parts = Arrays.asList(
 *     EntityPart.withName("name1").fileName("file1.doc").content(stream1).build(),
 *     EntityPart.withName("name2").fileName("file2.doc").content(stream2).build(),
 *     EntityPart.withName("name3").fileName("file3.xml").content(myObject, MyClass.class).mediaType("application/xml").build());
 *GenericEntity&lt;List&lt;EntityPart&gt;&gt; genericEntity = new GenericEntity&lt;&gt;(parts){};
 *Entity entity = Entity.entity(genericEntity, MediaType.MULTIPART_FORM_DATA);
 *Response r = target.request().post(entity);
 * </pre>
 *
 * Note that when building a EntityPart, the name and content are required.
 * Other properties such as headers, file name, and media type are optional.
 *
 * It is the responsibility of the implementation code to close the content
 * input streams when sending the multipart content. Closing the stream before
 * the implementation has sent it could result in unexpected exceptions. It is
 * the responsibility of the calling code to close the stream when receiving the
 * multipart content.
 *
 * @since 3.1
 */
public interface EntityPart {

    /**
     * Creates a new {@code EntityPart.Builder} instance.
     *
     * @param partName name of the part to create within the multipart entity
     * @return {@link Builder} for building new {@link EntityPart} instances
     */
    static Builder withName(String partName) {
        return RuntimeDelegate.getInstance().createEntityPartBuilder(partName);
    }

    /**
     * Creates a new {@code EntityPart.Builder} instance that sets the part
     * {@code name} and {@code fileName} to the passed in {@code partAndFileName}
     * value.
     * <p>
     * Logically, this is the same as {@code EntityPart.withName(x).fileName(x)}.
     * </p>
     *
     * @param partAndFileName name and filename of the part to create within the
     *                        multipart entity
     * @return {@link Builder} for building new {@link EntityPart} instances
     */
    static Builder withFileName(String partAndFileName) {
        return RuntimeDelegate.getInstance().createEntityPartBuilder(partAndFileName).fileName(partAndFileName);
    }

    /**
     * Returns the name of this part within the multipart entity. This will be the
     * "name" attribute of the {@code Content-Disposition} header for this part.
     *
     * @return the part name
     */
    String getName();

    /**
     * Returns the filename of this part. This will be the "filename" attribute of
     * the {@code Content-Disposition} header for this part. A filename is not
     * required in a part, so if a filename is not present it will return
     * {@code Optional.empty()}.
     *
     * @return an {@code Optional<String>} indicating the filename if present
     */
    Optional<String> getFileName();

    /**
     * Returns the input stream for this part. This is the content body of the part
     * and is accessed as a stream to avoid loading potentially large amounts of
     * data into the heap.
     *
     * It is the responsibility of the calling code to close this stream after
     * receiving it.
     *
     * @return an {@code InputStream} representing the content of this part
     */
    InputStream getContent();

    /**
     * Converts the content stream for this part to the specified class and returns
     * it. The implementation must convert the stream by finding a
     * {@link jakarta.ws.rs.ext.MessageBodyReader} that handles the specified type
     * as well as the {@link MediaType} of the part. If no
     * {@link jakarta.ws.rs.ext.MessageBodyReader} can be found to perform the
     * conversion, this method will throw an {@code IllegalArgumentException}.
     *
     * The implementation is required to close the content stream when this method
     * is invoked, so it may only be invoked once. Subsequent invocations will
     * result in an {@code IllegalStateException}. Likewise this method will throw
     * an {@code IllegalStateException} if it is called after calling
     * {@link #getContent} or {@link #getContent(GenericType)}.
     *
     * @param type the {@code Class} that the implementation should convert this
     *             part to
     * @param <T> the entity type
     * @return an instance of the specified {@code Class} representing the content
     *         of this part
     * @throws IllegalArgumentException if no
     *                                  {@link jakarta.ws.rs.ext.MessageBodyReader}
     *                                  can handle the conversion of this part to
     *                                  the specified type
     * @throws IllegalStateException    if this method or any of the other
     *                                  {@code getContent} methods has already been
     *                                  invoked
     * @throws IOException              if the
     *                                  {@link jakarta.ws.rs.ext.MessageBodyReader#readFrom(Class,
     *                                  java.lang.reflect.Type, java.lang.annotation.Annotation[], MediaType, MultivaluedMap, InputStream)}
     *                                  method throws an {@code IOException}
     * @throws WebApplicationException  if the
     *                                  {@link jakarta.ws.rs.ext.MessageBodyReader#readFrom(Class,
     *                                  java.lang.reflect.Type, java.lang.annotation.Annotation[], MediaType, MultivaluedMap, InputStream)}
     *                                  method throws an
     *                                  {@code WebApplicationException}
     */
    //CHECKSTYLE:OFF - More than 3 Exceptions are desired here
    <T> T getContent(Class<T> type) throws IllegalArgumentException, IllegalStateException, IOException,
        WebApplicationException;
    //CHECKSTYLE:ON

    /**
     * Converts the content stream for this part to the specified type and returns
     * it. The implementation must convert the stream by finding a
     * {@link jakarta.ws.rs.ext.MessageBodyReader} that handles the specified type
     * as well as the {@link MediaType} of the part. If no
     * {@link jakarta.ws.rs.ext.MessageBodyReader} can be found to perform the
     * conversion, this method will throw an {@code IllegalArgumentException}.
     *
     * The implementation is required to close the content stream when this method
     * is invoked, so it may only be invoked once. Subsequent invocations will
     * result in an {@code IllegalStateException}. Likewise this method will throw
     * an {@code IllegalStateException} if it is called after calling
     * {@link #getContent} or {@link #getContent(Class)}.
     *
     * @param type the generic type that the implementation should convert this part
     *             to
     * @param <T> the entity type
     * @return an instance of the specified generic type representing the content of
     *         this part
     * @throws IllegalArgumentException if no
     *                                  {@link jakarta.ws.rs.ext.MessageBodyReader}
     *                                  can handle the conversion of this part to
     *                                  the specified type
     * @throws IllegalStateException    if this method or any of the other
     *                                  {@code getContent} methods has already been
     *                                  invoked
     * @throws IOException              if the
     *                                  {@link jakarta.ws.rs.ext.MessageBodyReader#readFrom(Class,
     *                                  java.lang.reflect.Type, java.lang.annotation.Annotation[], MediaType, MultivaluedMap, InputStream)}
     *                                  method throws an {@code IOException}
     * @throws WebApplicationException  if the
     *                                  {@link jakarta.ws.rs.ext.MessageBodyReader#readFrom(Class,
     *                                  java.lang.reflect.Type, java.lang.annotation.Annotation[], MediaType, MultivaluedMap, InputStream)}
     *                                  method throws an
     *                                  {@code WebApplicationException}
     */
    //CHECKSTYLE:OFF - More than 3 Exceptions are desired here
    <T> T getContent(GenericType<T> type) throws IllegalArgumentException, IllegalStateException, IOException,
        WebApplicationException;
    //CHECKSTYLE:ON

    /**
     * Returns an immutable multivalued map of headers for this specific part.
     *
     * @return immutable {@code MultivaluedMap<String, String>} of part headers
     */
    MultivaluedMap<String, String> getHeaders();

    /**
     * Returns the content type of this part, and equivalent to calling
     * {@code MediaType.valueOf(part.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE))}.
     *
     * @return the media type for this part
     */
    MediaType getMediaType();

    /**
     * Builder for {@link EntityPart} instances.
     *
     * @since 3.1
     */
    interface Builder {

        /**
         * Sets the media type for the EntityPart. This will also set the
         * {@code Content-Type} header for this part.
         *
         * @param mediaType the media type for the part to be built
         * @return the updated builder
         * @throws IllegalArgumentException if {@code mediaType} is {@code null}
         */
        Builder mediaType(MediaType mediaType) throws IllegalArgumentException;

        /**
         * Convenience method for setting the media type for the EntityPart. This will
         * also set the {@code Content-Type} header for this part. This call is
         * effectively the same as
         * {@code mediaType(MediaType.valueOf(mediaTypeString))}.
         *
         * @param mediaTypeString the media type for the part to be built
         * @return the updated builder
         * @throws IllegalArgumentException if {@code mediaTypeString} cannot be parsed
         *                                  or is {@code null}
         */
        Builder mediaType(String mediaTypeString) throws IllegalArgumentException;

        /**
         * Adds a new header or replaces a previously added header and sets the header
         * value(s).
         *
         * @param headerName   the header name
         * @param headerValues the header value(s)
         * @return the updated builder
         * @throws IllegalArgumentException if {@code headerName} is {@code null}
         */
        Builder header(String headerName, String... headerValues) throws IllegalArgumentException;

        /**
         * Adds new headers or replaces previously added headers. The behavior of this
         * method would be the same as if iterating over the entry set and invoking the
         * {@link #header(String, String...)} method.
         *
         * @param newHeaders the multivalued map of headers to add to this part
         * @return the updated builder
         * @throws IllegalArgumentException if {@code newHeaders} is {@code null}
         */
        Builder headers(MultivaluedMap<String, String> newHeaders) throws IllegalArgumentException;

        /**
         * Sets the file name for this part. The file name will be specified as an
         * attribute in the {@code Content-Disposition} header of this part. When this
         * method is called, the default media type used for the built part will be
         * "application/octet-stream" if not otherwise specified.
         *
         * @param fileName the file name for this part
         * @return the updated builder
         * @throws IllegalArgumentException if {@code fileName} is {@code null}
         */
        Builder fileName(String fileName) throws IllegalArgumentException;

        /**
         * Sets the content for this part. The content of this builder must be specified
         * before invoking the {@link #build()} method.
         * <p>
         * The {@code InputStream} will be closed by the implementation code after
         * sending the multipart data. Closing the stream before it is sent could result
         * in unexpected behavior.
         * </p>
         *
         * @param content {@code InputStream} of the content of this part
         * @return the updated builder
         * @throws IllegalArgumentException if {@code content} is {@code null}
         */
        Builder content(InputStream content) throws IllegalArgumentException;

        /**
         * Convenience method, equivalent to calling
         * {@code fileName(fileName).content(content)}.
         *
         * @param fileName the filename of the part.
         * @param content  the content stream of the part.
         * @return the updated builder.
         * @throws IllegalArgumentException if either parameter is {@code null}.
         */
        default Builder content(String fileName, InputStream content) throws IllegalArgumentException {
            return this.fileName(fileName).content(content);
        }

        /**
         * Sets the content for this part. The content of this builder must be specified
         * before invoking the {@link #build()} method.
         * <p>
         * If the content is specified using this method, then the {@link #build()}
         * method is responsible for finding a registered
         * {@link jakarta.ws.rs.ext.MessageBodyWriter} that is capable of writing the
         * object type specified here using the default {@link MediaType} or the
         * {@link MediaType} specified in the {@link #mediaType(MediaType)} or
         * {@link #mediaType(String)} methods and using any headers specified via the
         * {@link #header(String, String...)} or {@link #headers(MultivaluedMap)}
         * methods.
         * </p>
         *
         * @param content the object to be used as the content
         * @param type    the type of this object which will be used when selecting the
         *                appropriate {@link jakarta.ws.rs.ext.MessageBodyWriter}
         * @param <T>     the entity type
         * @return the updated builder.
         * @throws IllegalArgumentException if {@code content} is {@code null}
         */
        <T> Builder content(T content, Class<? extends T> type) throws IllegalArgumentException;

        /**
         * Sets the content for this part. The content of this builder must be specified
         * before invoking the {@link #build()} method.
         * <p>
         * If the content is specified using this method, then the {@link #build()}
         * method is responsible for finding a registered
         * {@link jakarta.ws.rs.ext.MessageBodyWriter} that is capable of writing the
         * object's class type specified here using the default {@link MediaType} or the
         * {@link MediaType} specified in the {@link #mediaType(MediaType)} or
         * {@link #mediaType(String)} methods and using any headers specified via the
         * {@link #header(String, String...)} or {@link #headers(MultivaluedMap)}
         * methods.
         * </p>
         * <p>
         * This is the equivalent of calling
         * {@code content(content, content.getClass())}.
         * </p>
         *
         * @param content the object to be used as the content
         * @throws IllegalArgumentException if {@code content} is {@code null}
         * @return the updated builder.
         */
        default Builder content(Object content) throws IllegalArgumentException {
            return this.content(content, content.getClass());
        }

        /**
         * Sets the content for this part. The content of this builder must be specified
         * before invoking the {@link #build()} method.
         * <p>
         * If the content is specified using this method, then the {@link #build()}
         * method is responsible for finding a registered
         * {@link jakarta.ws.rs.ext.MessageBodyWriter} that is capable of writing the
         * object type specified here using the default {@link MediaType} or the
         * {@link MediaType} specified in the {@link #mediaType(MediaType)} or
         * {@link #mediaType(String)} methods and using any headers specified via the
         * {@link #header(String, String...)} or {@link #headers(MultivaluedMap)}
         * methods.
         * </p>
         *
         * @param content the object to be used as the content
         * @param type    the generic type of this object which will be used when
         *                selecting the appropriate
         *                {@link jakarta.ws.rs.ext.MessageBodyWriter}
         * @param <T> the entity type
         * @return the updated builder.
         * @throws IllegalArgumentException if {@code content} is {@code null}
         */
        <T> Builder content(T content, GenericType<T> type) throws IllegalArgumentException;

        /**
         * Builds a new EntityPart instance using the provided property values.
         *
         * @return {@link EntityPart} instance built from the provided property values.
         * @throws IllegalStateException   if the content was not specified or no
         *                                 matching
         *                                 {@link jakarta.ws.rs.ext.MessageBodyWriter}
         *                                 was found.
         * @throws IOException             if the underlying
         *                                 {@link jakarta.ws.rs.ext.MessageBodyWriter}
         *                                 throws an {@code IOException}
         * @throws WebApplicationException if the underlying
         *                                 {@link jakarta.ws.rs.ext.MessageBodyWriter}
         *                                 throws a {@code WebApplicationException}
         */
        EntityPart build() throws IllegalStateException, IOException, WebApplicationException;
    }
}
