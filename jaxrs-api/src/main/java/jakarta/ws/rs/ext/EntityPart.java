/*******************************************************************
* Copyright (c) 2021 Eclipse Foundation
*
* This specification document is made available under the terms
* of the Eclipse Foundation Specification License v1.0, which is
* available at https://www.eclipse.org/legal/efsl.php.
*******************************************************************/
package jakarta.ws.rs.ext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;

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
 * Likewise, a client may receive a
 * multipart response by reading the returned entity as a collection of EntityParts
 * (ex: {@code response.readEntity(new GenericType<List<EntityPart>>() {})}).
 * </p>
 * <p>
 * In order to send a multipart entity either as a client request or a response
 * from a resource method, you may create the Lists using {@code EntityPart.Builder}.
 * For example:
 * </p>
 * 
 * <pre>
 * Client c = ClientBuilder.newClient();
 * WebTarget target = c.target(someURL);
 * List&lt;EntityPart&gt; parts = Arrays.asList(EntityPart.withName("name1").fileName("file1.doc").content(stream1).build(),
 *         EntityPart.withName("name1").fileName("file2.doc").content(stream2).build(),
 *         EntityPart.withName("name1").fileName("file3.doc").content(stream3).build());
 * Entity entity = Entity.entity(parts, MediaType.MULTIPART_FORM_DATA);
 * Response r = target.request().post(entity);
 * </pre>
 * 
 * Note that when building a EntityPart, the name and content are required.
 * Other properties such as headers, file name, and media type are optional.
 * 
 * It is the responsibility of the implementation code to close the content input streams when sending the multipart
 * content. Closing the stream before the implementation has sent it could result in unexpected exceptions. It is the
 * responsibility of the calling code to close the stream when receiving the multipart content.
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
     * Creates a new {@code EntityPart.Builder} instance that sets the part {@code name} and
     * {@code fileName} to the passed in {@code partAndFileName} value.
     * <p>
     * Logically, this is the same as {@code EntityPart.withName(x).fileName(x)}.
     * </p>
     * @param partAndFileName name and filename of the part to create within the multipart entity
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
     * {@code Optional.empty()}
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
     * Converts the content stream for this part to the specified class and returns it.
     * The implementation must convert the stream by finding a {@link MessageBodyReader}
     * that handles the specified type as well as the {@link MediaType} of the part.
     * If no {@link MessageBodyReader} can be found to perform the conversion, this method
     * will throw an {@code IllegalArgumentException}.
     * 
     * The implementation is required to close the content stream when this method is
     * invoked, so it may only be invoked once. Subsequent invocations will result in
     * an {@code IllegalStateException}. Likewise this method will throw an
     * {@code IllegalStateException} if it is called after calling {@link #getContent}
     * or {@link #getContent(GenericType)}.
     * 
     * @param type the {@code Class} that the implementation should convert this part to
     * @return an instance of the specified {@code Class} representing the content of this part
     * @throws IllegalArgumentException if no {@link MessageBodyReader} can handle the conversion of this part to the specified type
     * @throws IllegalStateException if this method or any of the other {@code getContent} methods has already been invoked
     * @throws IOException if the {@link MessageBodyReader#readFrom(Class, java.lang.reflect.Type, java.lang.annotation.Annotation[], MediaType, MultivaluedMap, InputStream)}
     * method throws an {@code IOException}
     * @throws WebApplicationException if the {@link MessageBodyReader#readFrom(Class, java.lang.reflect.Type, java.lang.annotation.Annotation[], MediaType, MultivaluedMap, InputStream)}
     * method throws an {@code WebApplicationException}
     */
    <T> T getContent(Class<T> type) throws IllegalArgumentException, IllegalStateException, IOException,
        WebApplicationException;

    /**
     * Converts the content stream for this part to the specified type and returns it.
     * The implementation must convert the stream by finding a {@link MessageBodyReader}
     * that handles the specified type as well as the {@link MediaType} of the part.
     * If no {@link MessageBodyReader} can be found to perform the conversion, this method
     * will throw an {@code IllegalArgumentException}.
     * 
     * The implementation is required to close the content stream when this method is
     * invoked, so it may only be invoked once. Subsequent invocations will result in
     * an {@code IllegalStateException}. Likewise this method will throw an
     * {@code IllegalStateException} if it is called after calling {@link #getContent}
     * or {@link #getContent(Class)}.
     * 
     * @param type the generic type that the implementation should convert this part to
     * @return an instance of the specified generic type representing the content of this part
     * @throws IllegalArgumentException if no {@link MessageBodyReader} can handle the conversion of this part to the specified type
     * @throws IllegalStateException if this method or any of the other {@code getContent} methods has already been invoked
     * @throws IOException if the {@link MessageBodyReader#readFrom(Class, java.lang.reflect.Type, java.lang.annotation.Annotation[], MediaType, MultivaluedMap, InputStream)}
     * method throws an {@code IOException}
     * @throws WebApplicationException if the {@link MessageBodyReader#readFrom(Class, java.lang.reflect.Type, java.lang.annotation.Annotation[], MediaType, MultivaluedMap, InputStream)}
     * method throws an {@code WebApplicationException}
     */
    <T> T getContent(GenericType<T> type) throws IllegalArgumentException, IllegalStateException, IOException,
        WebApplicationException;

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
    public interface Builder {

        /**
         * Sets the media type for the EntityPart. This will also set the
         * {@code Content-Type} header for this part.
         * 
         * @param mediaType the media type for the part to be built
         * @return the updated builder
         * @throws IllegalArgumentException if {@code mediaType} is {@code null}
         */
        public Builder mediaType(MediaType mediaType) throws IllegalArgumentException;

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
        public Builder mediaType(String mediaTypeString) throws IllegalArgumentException;

        /**
         * Adds a new header or replaces a previously added header and sets the header value(s).
         * 
         * @param headerName the header name
         * @param headerValues the header value(s)
         * @return the updated builder
         * @throws IllegalArgumentException if {@code headerName} is {@code null}
         */
        public Builder header(String headerName, String... headerValues) throws IllegalArgumentException;

        /**
         * Adds new headers or replaces previously added headers. The behavior of this method would be the same as if
         * iterating over the entry set and invoking the {@link #header(String, String...)} method.
         * 
         * @param newHeaders the multivalued map of headers to add to this part
         * @return the updated builder
         * @throws IllegalArgumentException if {@code newHeaders} is {@code null}
         */
        public Builder headers(MultivaluedMap<String, String> newHeaders) throws IllegalArgumentException;

        /**
         * Sets the file name for this part. The file name will be specified as an attribute in the
         * {@code Content-Disposition} header of this part. When this method is called, the default
         * media type used for the built part will be "application/octet-stream" if not otherwise
         * specified.
         * 
         * @param fileName the file name for this part
         * @return the updated builder
         * @throws IllegalArgumentException if {@code fileName} is {@code null}
         */
        public Builder fileName(String fileName) throws IllegalArgumentException;

        /**
         * Sets the content for this part. This method or the convenience method,
         * {@link #content(String, InputStream)} must be invoked before invoking the
         * {@link #build()} method.
         * 
         * The {@code InputStream} will be closed by the implementation code after
         * sending the multipart data. Closing the stream before it is sent could
         * result in unexpected behavior.
         * 
         * @param content {@code InputStream} of the content of this part
         * @return the updated builder
         * @throws IllegalArgumentException if {@code content} is {@code null}
         */
        public Builder content(InputStream content) throws IllegalArgumentException;

        /**
         * Convenience method, equivalent to calling {@code fileName(fileName).content(contentStream)}.
         * 
         * @param fileName the filename of the part.
         * @param content the content stream of the part.
         * @return the updated builder.
         * @throws IllegalArgumentException if either parameter is {@code null}.
         */
        public Builder content(String fileName, InputStream content) throws IllegalArgumentException;

        /**
         * Builds a new EntityPart instance using the provided property values.
         * 
         * @return {@link EntityPart} instance built from the provided property values.
         * @throws IllegalStateException if the content was not specified.
         */
        public EntityPart build() throws IllegalStateException;
    }
}
