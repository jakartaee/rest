/*
 * Copyright (c) 2011, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.client;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Locale;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Variant;

/**
 * Encapsulates message entity including the associated variant information.
 *
 * @param <T> entity type.
 * @author Marek Potociar
 */
public final class Entity<T> {
    private static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];

    private final T entity;
    private final Variant variant;
    private final Annotation[] annotations;

    /**
     * Create an entity using a supplied content media type.
     *
     * @param <T>       entity Java type.
     * @param entity    entity data.
     * @param mediaType entity content type.
     * @return entity instance.
     */
    public static <T> Entity<T> entity(final T entity, final MediaType mediaType) {
        return new Entity<T>(entity, mediaType);
    }

    /**
     * Create an entity using a supplied content media type.
     *
     * @param <T>         entity Java type.
     * @param entity      entity data.
     * @param mediaType   entity content type.
     * @param annotations entity annotations.
     * @return entity instance.
     */
    public static <T> Entity<T> entity(final T entity, final MediaType mediaType, Annotation[] annotations) {
        return new Entity<T>(entity, mediaType, annotations);
    }

    /**
     * Create an entity using a supplied content media type.
     *
     * @param <T>       entity Java type.
     * @param entity    entity data.
     * @param mediaType entity content type.
     * @return entity instance.
     * @throws IllegalArgumentException if the supplied string cannot be parsed
     *                                  or is {@code null}.
     */
    public static <T> Entity<T> entity(final T entity, final String mediaType) {
        return new Entity<T>(entity, MediaType.valueOf(mediaType));
    }

    /**
     * Create an entity using a supplied content media type.
     *
     * @param <T>     entity Java type.
     * @param entity  entity data.
     * @param variant entity {@link Variant variant} information.
     * @return entity instance.
     */
    public static <T> Entity<T> entity(final T entity, final Variant variant) {
        return new Entity<T>(entity, variant);
    }

    /**
     * Create an entity using a supplied content media type.
     *
     * @param <T>         entity Java type.
     * @param entity      entity data.
     * @param variant     entity {@link Variant variant} information.
     * @param annotations entity annotations.
     * @return entity instance.
     */
    public static <T> Entity<T> entity(final T entity, final Variant variant, Annotation[] annotations) {
        return new Entity<T>(entity, variant, annotations);
    }

    /**
     * Create a {@value javax.ws.rs.core.MediaType#TEXT_PLAIN} entity.
     *
     * @param <T>    entity Java type.
     * @param entity entity data.
     * @return {@value javax.ws.rs.core.MediaType#TEXT_PLAIN} entity instance.
     */
    public static <T> Entity<T> text(final T entity) {
        return new Entity<T>(entity, MediaType.TEXT_PLAIN_TYPE);
    }

    /**
     * Create an {@value javax.ws.rs.core.MediaType#APPLICATION_XML} entity.
     *
     * @param <T>    entity Java type.
     * @param entity entity data.
     * @return {@value javax.ws.rs.core.MediaType#APPLICATION_XML} entity instance.
     */
    public static <T> Entity<T> xml(final T entity) {
        return new Entity<T>(entity, MediaType.APPLICATION_XML_TYPE);
    }

    /**
     * Create an {@value javax.ws.rs.core.MediaType#APPLICATION_JSON} entity.
     *
     * @param <T>    entity Java type.
     * @param entity entity data.
     * @return {@value javax.ws.rs.core.MediaType#APPLICATION_JSON} entity instance.
     */
    public static <T> Entity<T> json(final T entity) {
        return new Entity<T>(entity, MediaType.APPLICATION_JSON_TYPE);
    }

    /**
     * Create a {@value javax.ws.rs.core.MediaType#TEXT_HTML} entity.
     *
     * @param <T>    entity Java type.
     * @param entity entity data.
     * @return {@value javax.ws.rs.core.MediaType#TEXT_HTML} entity instance.
     */
    public static <T> Entity<T> html(final T entity) {
        return new Entity<T>(entity, MediaType.TEXT_HTML_TYPE);
    }

    /**
     * Create an {@value javax.ws.rs.core.MediaType#APPLICATION_XHTML_XML} entity.
     *
     * @param <T>    entity Java type.
     * @param entity entity data.
     * @return {@value javax.ws.rs.core.MediaType#APPLICATION_XHTML_XML} entity
     *         instance.
     */
    public static <T> Entity<T> xhtml(final T entity) {
        return new Entity<T>(entity, MediaType.APPLICATION_XHTML_XML_TYPE);
    }

    /**
     * Create an {@value javax.ws.rs.core.MediaType#APPLICATION_FORM_URLENCODED}
     * form entity.
     *
     * @param form form data.
     * @return {@value javax.ws.rs.core.MediaType#APPLICATION_FORM_URLENCODED}
     *         form entity instance.
     */
    public static Entity<Form> form(final Form form) {
        return new Entity<Form>(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
    }

    /**
     * Create an {@value javax.ws.rs.core.MediaType#APPLICATION_FORM_URLENCODED}
     * form entity.
     *
     * @param formData multivalued map representing the form data.
     * @return {@value javax.ws.rs.core.MediaType#APPLICATION_FORM_URLENCODED}
     *         form entity instance.
     */
    public static Entity<Form> form(final MultivaluedMap<String, String> formData) {
        return new Entity<Form>(new Form(formData), MediaType.APPLICATION_FORM_URLENCODED_TYPE);
    }

    private Entity(final T entity, final MediaType mediaType) {
        this(entity, new Variant(mediaType, (Locale) null, null), null);
    }

    private Entity(final T entity, final Variant variant) {
        this(entity, variant, null);
    }

    private Entity(final T entity, final MediaType mediaType, Annotation[] annotations) {
        this(entity, new Variant(mediaType, (Locale) null, null), annotations);
    }

    private Entity(final T entity, final Variant variant, Annotation[] annotations) {
        this.entity = entity;
        this.variant = variant;

        this.annotations = (annotations == null) ? EMPTY_ANNOTATIONS : annotations;
    }

    /**
     * Get entity {@link Variant variant} information.
     *
     * @return entity variant information.
     */
    public Variant getVariant() {
        return variant;
    }

    /**
     * Get entity media type.
     *
     * @return entity media type.
     */
    public MediaType getMediaType() {
        return variant.getMediaType();
    }

    /**
     * Get entity encoding.
     *
     * @return entity encoding.
     */
    public String getEncoding() {
        return variant.getEncoding();
    }

    /**
     * Get entity language.
     *
     * @return entity language.
     */
    public Locale getLanguage() {
        return variant.getLanguage();
    }

    /**
     * Get entity data.
     *
     * @return entity data.
     */
    public T getEntity() {
        return entity;
    }

    /**
     * Get the entity annotations.
     *
     * @return entity annotations if set, an empty annotation array if no
     *         entity annotations have been specified.
     */
    public Annotation[] getAnnotations() {
        return annotations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity entity1 = (Entity) o;

        if (!Arrays.equals(annotations, entity1.annotations)) return false;
        if (entity != null ? !entity.equals(entity1.entity) : entity1.entity != null) return false;
        if (variant != null ? !variant.equals(entity1.variant) : entity1.variant != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = entity != null ? entity.hashCode() : 0;
        result = 31 * result + (variant != null ? variant.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(annotations);
        return result;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entity=" + entity +
                ", variant=" + variant +
                ", annotations=" + Arrays.toString(annotations) +
                '}';
    }
}
