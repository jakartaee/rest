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

{TechnologyShortName} TCK test application classes that are to be
published in a Java SE environment are located under
`$TS_HOME/classes`.

The {TechnologyShortName} TCK comes with prebuilt test WAR files for
deployment on JAkarta EE {JakartaEEVersion} CI, Eclipse GlassFish 6, which provides a Servlet–compliant Web
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

The set of prebuilt archives and classes that ship with the
{TechnologyShortName} TCK were built using the Eclipse GlassFish 6, Compatible
Implementation, and must be deployed on a Jakarta EE {JakartaEEVersion} CI and run against
the {TechnologyRI} CI.

The prebuilt {TechnologyRI}-specific Servlet–compliant WAR files are located
under $TS_HOME`/dist`, and have `jersey` as part of their name; for
example:

[source,oac_no_warn]
----
$TS_HOME/dist/com/sun/ts/tests/jaxrs/ee/rs/get/jaxrs_rs_get_web.war.jersey
----

The names are made unique by including keyword `jersey` to minimize the chances
that the files will be overwritten or modified.

The Vendor must create VI-specific Servlet–compliant WAR files if the
Vendor chooses to publish on a Servlet–compliant Web container, so that
the VI-specific Servlet class will be included instead of the
{TechnologyRI}-specific Servlet class.

[[GCLLW]][[b.2-creating-the-vi-specific-servletcompliant-war-files]]

B.2 Creating the TCK VI-Specific Servlet–Compliant WAR Files
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

All resource and application class files are already compiled. The
Vendor needs to package these files. All tests also come with a
`web.xml.template` file to be used for generating deployment descriptor
files with a VI-specific Servlet definition.

Each test that has a {TechnologyShortName} resource class to publish comes with a
template deployment descriptor file. For example, the file
`$TS_HOME/src/com/sun/ts/tests/jaxrs/ee/rs/get/web.xml.template`
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
            <param-value>com.sun.ts.tests.jaxrs.ee.rs.get.TSAppConfig</param-value>
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
            <param-value>com.sun.ts.tests.jaxrs.ee.rs.get.TSAppConfig</param-value>
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

The {TechnologyShortName} TCK provides a tool,
`${ts.home}/bin/xml/impl/glassfish/jersey.xml`, for use with the Jakarta EE {JakartaEEVersion} CI, Eclipse GlassFish 6
that you can use as a model to help you create your own VI-specific Web
test application.

[[GCLMA]][[b.2.1-to-create-a-vi-specific-deployment-descriptor]]

B.2.1 To Create a VI-Specific Deployment Descriptor
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

1.  Create a VI handler file. +
Create a VI-specific handler file
`$TS_HOME/bin/xml/impl/${`impl.vi`}/`jaxrs_impl_name`.xml` if one does
not already exist. Make sure the `jaxrs_impl_name` property is set in
the +{jteFileName}+, file and that it has a unique name so no file will be
overwritten.
2.  Set the VI Servlet class property. +
Set the `servlet_adaptor` property in the +{jteFileName}+ file. This property
will be used to set the value of the `<servlet-class>` element in the
deployment descriptor.
3.  Create VI Ant tasks. +
Create a `update.jaxrs.wars` target in the VI handler file. Reference
this `update.jaxrs.wars` target in the `jersey.xml` file. +
This target will create a `web.xml.${`jaxrs_impl_name`}` for each test
that has a deployment descriptor template. The
`web.xml.${`jaxrs_impl_name`}` will contain the VI-specific Servlet
class name. It will also create the test WAR files under
`$TS_HOME/dist`; for example: +
+
[source,oac_no_warn]
----
ls $TS_HOME/dist/com/sun/ts/tests/jaxrs/ee/rs/get/
jaxrs_rs_get_web.war.jersey
jaxrs_rs_get_web.war.${impl_name}
----
+
4.  Change to the `$TS_HOME/bin` directory and execute the
`update.jaxrs.wars` Ant target. +
This creates a `web.xml.`VI_name file for each test based on the VI's
servlet class name and repackage the tests.
