/*
 * Copyright (c) 2010, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.ws.rs.core;

import javax.ws.rs.ext.RuntimeDelegate;
import javax.ws.rs.ext.RuntimeDelegate.HeaderDelegate;

/**
 * An abstraction for the value of a HTTP Entity Tag, used as the value
 * of an ETag response header.
 *
 * @author Paul Sandoz
 * @author Marc Hadley
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.11">HTTP/1.1 section 3.11</a>
 * @since 1.0
 */
public class EntityTag {

    private static final HeaderDelegate<EntityTag> HEADER_DELEGATE =
            RuntimeDelegate.getInstance().createHeaderDelegate(EntityTag.class);
    private String value;
    private boolean weak;

    /**
     * Creates a new instance of a strong {@code EntityTag}.
     *
     * @param value the value of the tag, quotes not included.
     * @throws IllegalArgumentException if value is {@code null}.
     */
    public EntityTag(final String value) {
        this(value, false);
    }

    /**
     * Creates a new instance of an {@code EntityTag}.
     *
     * @param value the value of the tag, quotes not included.
     * @param weak  {@code true} if this represents a weak tag, {@code false} otherwise.
     * @throws IllegalArgumentException if value is {@code null}.
     */
    public EntityTag(final String value, final boolean weak) {
        if (value == null) {
            throw new IllegalArgumentException("value==null");
        }
        this.value = value;
        this.weak = weak;
    }

    /**
     * Creates a new instance of {@code EntityTag} by parsing the supplied string.
     *
     * @param value the entity tag string.
     * @return the newly created entity tag.
     * @throws IllegalArgumentException if the supplied string cannot be parsed
     *                                  or is {@code null}.
     */
    public static EntityTag valueOf(final String value) {
        return HEADER_DELEGATE.fromString(value);
    }

    /**
     * Check the strength of an {@code EntityTag}.
     *
     * @return {@code true} if this represents a weak tag, {@code false} otherwise.
     */
    public boolean isWeak() {
        return weak;
    }

    /**
     * Get the value of an {@code EntityTag}.
     *
     * @return the value of the tag.
     */
    public String getValue() {
        return value;
    }

    /**
     * Compares {@code obj} to this tag to see if they are the same considering
     * weakness and value.
     *
     * @param obj the object to compare to.
     * @return {@code true} if the two tags are the same, {@code false} otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof EntityTag)) {
            return super.equals(obj);
        }
        EntityTag other = (EntityTag) obj;
        if (value.equals(other.getValue()) && weak == other.isWeak()) {
            return true;
        }
        return false;
    }

    /**
     * Generate hashCode based on value and weakness.
     *
     * @return the entity tag hash code.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.value != null ? this.value.hashCode() : 0);
        hash = 17 * hash + (this.weak ? 1 : 0);
        return hash;
    }

    /**
     * Convert the entity tag to a string suitable for use as the value of the
     * corresponding HTTP header.
     *
     * @return a string version of the entity tag.
     */
    @Override
    public String toString() {
        return HEADER_DELEGATE.toString(this);
    }
}
