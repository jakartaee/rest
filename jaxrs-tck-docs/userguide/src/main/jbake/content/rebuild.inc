///////////////////////////////////////////////////////////////////////////////

    Copyright (c) 2020, 2021 Oracle and/or its affiliates. All rights reserved.

    This program and the accompanying materials are made available under the
    terms of the Eclipse Public License v. 2.0, which is available at
    http://www.eclipse.org/legal/epl-2.0.

    This Source Code may also be made available under the following Secondary
    Licenses when the conditions for such availability set forth in the
    Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
    version 2 with the GNU Classpath Exception, which is available at
    https://www.gnu.org/software/classpath/license.html.

    SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0

///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////
NOTE TO WRITERS:
The following sections should be customized for the technology.
This text was originally from the {TechnologyShortName} TCK.  Most references
to {TechnologyShortName} have been parameterized to serve as a simple starting
point for customization.  There are still many details that will
need to be changed or removed.
///////////////////////////////////////////////////////////////////////

[[GCLIZ]]


[[b-packaging-the-test-applications-in-servlet-compliant-war-files-with-vi-specific-information]]
B Packaging the Test Applications in Servlet-Compliant WAR Files With VI-Specific Information
---------------------------------------------------------------------------------------------

[Note]
====
If you are using a compatible implementation, other than {TechnologyRI}, please consult documentation
which may contain additional details specific for that implementation.

====

The {TechnologyShortName} {TechnologyVersion} specification specifies
how {TechnologyShortName} applications are to be published in a Java SE
environment, {TechnologyShortName} endpoint, or Servlet–compliant Web container.



The {TechnologyShortName} TCK comes with prebuilt test WAR files for
deployment on Jakarta EE {JakartaEEVersion} CI, Eclipse GlassFish 6.1, 
which provides a Servlet–compliant Web
container. The WAR files are {TechnologyRI}-specific, with {TechnologyRI}'s servlet
class and {TechnologyRI}'s servlet defined in the `web.xml` deployment
descriptor. To run the TCK tests against the VI in a Servlet–compliant
Web container, the tests need to be repackaged to include the
VI-specific servlet, and the VI-specific servlet must be defined in the
deployment descriptor.

The {TechnologyShortName} TCK makes it easier for the vendor by
including template WAR files that contain all of the necessary files
except for the VI-specific servlet adaptor class. The
{TechnologyShortName} TCK provides a tool to help with the repackaging
task.

This appendix contains the following sections:

* link:#GCLIO[Overview]
* link:#GCLLW[Creating the VI-Specific Servlet–Compliant WAR
Files]

[[GCLIO]][[b.1-overview]]

B.1 Overview
~~~~~~~~~~~~

The classes that ship with the {TechnologyShortName} TCK were built 
using the Eclipse GlassFish 6.1, Compatible
Implementation. The archives for tests are built and deployed in the runtime.

[[GCLLW]][[b.2-creating-the-vi-specific-servletcompliant-war-files]]

B.2 Creating the TCK VI-Specific Servlet–Compliant WAR Files
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

All resource and application class files are already compiled. 
All tests also come with a `web.xml.template` file that is used 
for generating deployment descriptor files.

Each test that has a {TechnologyShortName} resource class to publish, comes with a
template deployment descriptor file. For example, the file
`ee/jakarta/tck/ws/rs/ee/rs/get/web.xml.template`
contains the following elements:

[source,oac_no_warn]
----
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="5.0" xmlns="https://jakarta.ee/xml/ns/jakartaee" \
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" \
xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee \
https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd">
    <servlet>
        <servlet-name>CTSJAX-RSGET</servlet-name>
        <servlet-class>servlet_adaptor</servlet-class>
        <init-param>
            <param-name>jakarta.ws.rs.core.Application</param-name>
            <param-value>ee.jakarta.tck.ws.rs.ee.rs.get.TSAppConfig</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>CTSJAX-RSGET</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>
    <session-config>
        <session-timeout>30</session-timeout>
    </session-config>
</web-app>
----

In this example, the `<servlet-class>` element has a value of
`servlet_adaptor`, which is a placeholder for the
implementation-specific Servlet class. A {TechnologyRI}-specific
deployment descriptor also comes with the {TechnologyShortName} TCK,
and has the values for the
`org.glassfish.jersey.servlet.ServletContainer`:

[source,oac_no_warn]
----
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="5.0" xmlns="https://jakarta.ee/xml/ns/jakartaee" \
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" \
xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee \
https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd">
    <servlet>
        <servlet-name>CTSJAX-RSGET</servlet-name>
        <servlet-class>
            org/glassfish/jersey/servlet/ServletContainer
        </servlet-class>
        <init-param>
            <param-name>jakarta.ws.rs.core.Application</param-name>
            <param-value>ee.jakarta.tck.ws.rs.ee.rs.get.TSAppConfig</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>CTSJAX-RSGET</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>
    <session-config>
        <session-timeout>30</session-timeout>
        </session-config>
</web-app>
----

[[GCLMA]][[b.2.1-to-create-a-vi-specific-deployment-descriptor]]

B.2.1 To Create a VI-Specific Deployment Descriptor
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^


1.  Set the VI Servlet class property. +
Set the `servlet_adaptor` property as System property. This property
will be used to set the value of the `<servlet-class>` element in the
deployment descriptor before packaging and deploying the archive.
