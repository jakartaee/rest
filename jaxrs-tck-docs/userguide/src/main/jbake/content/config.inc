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
need to be changed or removed.  The major sections 4.1, 4.2, and
4.3 should be preserved.  If their titles are changed, the links
at the top of config.adoc will need to be changed as well as well
as toc.adoc.
///////////////////////////////////////////////////////////////////////

[[GBFVU]][[configuring-your-environment-to-run-the-tck-against-the-reference-implementation]]

4.1 Configuring Your Environment to Run the TCK Against a Compatible Implementation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

After configuring your environment as described in this section,
continue with the instructions in link:#GBFUY[Section 4.6, "Using the
JavaTest Harness Software."]


[NOTE]
=======================================================================

In these instructions, variables in angle brackets need to be expanded
for each platform. For example, `<TS_HOME>` becomes `$TS_HOME` on
Solaris/Linux and `%TS_HOME%` on Windows. In addition, the forward
slashes (`/`) used in all of the examples need to be replaced with
backslashes (`\`) for Windows. Finally, be sure to use the appropriate
separator for your operating system when specifying multiple path
entries (`;` on Windows, `:` on UNIX/Linux).

On Windows, you must escape any backslashes with an extra backslash in
path separators used in any of the following properties, or use forward
slashes as a path separator instead.

=======================================================================


1.  Set the following environment variables in your shell environment:
  a.  `JAVA_HOME` to the directory in which Java SE {SEversion} is installed
  b.  `TS_HOME` to the directory in which the {TechnologyShortName} TCK
  {TechnologyVersion} software is installed
  c.  +{TechnologyHomeEnv}+ to the directory in which the {TechnologyShortName}
  {TechnologyVersion} CI has been installed
  d.  `PATH` to include the following directories: `JAVA_HOME/bin`,
  +{TechnologyHomeEnv}/bin+, and `ANT_HOME/bin`
2.  Copy <TS_HOME>/bin/ts.jte.jdk11 as <TS_HOME>/bin/ts.jte if JAVA_HOME is Java SE 11.
Edit your `<TS_HOME>/bin/ts.jte` file and set the following
environment variables:
  a.  Set the `webServerHost` property to the name of the host on which
  Jakarta EE {JakartaEEVersion} CI is running. +
  The default setting is `localhost`.
  b.  Set the `webServerPort` property to the port number of the host on
  which Jakarta EE {JakartaEEVersion} CI is running. +
  The default setting is `8080`.
  c.  Set the `web.home` property to the installation directory of Jakarta EE
  {JakartaEEVersion} CI.
  d.  Set the `jaxrs.classes` property to point to the classes or JAR file
  for the {TechnologyShortName} {TechnologyVersion} API. +
  The default setting for this property is
  `${web.home}/modules/jakarta.ws.rs-api.jar`.
  e.  Set the `jaxrs_impl_lib` and `jaxrs_impl.classes` properties to
  point to, for example the {TechnologyRI} CI. +
  The default setting for the `jaxrs_impl_lib` property is
  `${web.home}/modules/jersey-container-servlet-core.jar`. +
  The default setting, if you are using the {TechnologyRI} CI for the `jaxrs_impl.`classes property is as follows
  (all on one line):
+
[source,oac_no_warn]
----
${web.home}/modules/jersey-client.jar:
${web.home}/modules/jersey-common.jar:
${web.home}/modules/jersey-server.jar:
${web.home}/modules/jersey-container-servlet.jar:
${web.home}/modules/jersey-container-servlet-core.jar:
${web.home}/modules/jersey-media-jaxb.jar:
${web.home}/modules/jersey-media-sse.jar:
${web.home}/modules/jersey-hk2.jar:
${web.home}/modules/osgi-resource-locator.jar:
${web.home}/modules/jakarta.inject-api.jar:
${web.home}/modules/guava.jar:
${web.home}/modules/hk2-api.jar:
${web.home}/modules/hk2-locator.jar:
${web.home}/modules/hk2-utils.jar:
${web.home}/modules/cglib.jar:
${web.home}/modules/asm-all-repackaged.jar:
${web.home}/modules/bean-validator.jar:
${web.home}/modules/jakarta.annotation-api.jar:
${web.home}/modules/jakarta.xml.bind-api.jar:
${web.home}/modules/jaxb-osgi.jar:
${web.home}/modules/jakarta.activation.jar:
${web.home}/modules/javassist.jar
----
+
  f.  Set the `servlet_adaptor` property to point to the Servlet adaptor
  class for the {TechnologyShortName} implementation. +
  The default setting for this property, if you are using the {TechnologyRI} CI is
  `org/glassfish/jersey/servlet/ServletContainer.class`.
  g.  Set the `impl.vi` property to the name of the Jakarta EE {JakartaEEVersion} CI . +
  The relevant porting files are located under the
  `$TS_HOME/bin/xml/impl/glassfish/` directory. +
  The default setting for this property, when using Eclipse GlassFish as the CI is `glassfish`.
  h.  Set the `jaxrs_impl_name` property to the name of the
  {TechnologyShortName} CI. +
  A file bearing this name has been created under
  `<TS_HOME>/bin/xml/impl/glassfish/jersey.xml` with packaging
  instructions. +
  The default setting for this property when using the {TechnologyRI} CI is `jersey`.
  i.  Set the `impl.vi.deploy.dir` property to point to the `autodeploy`
  directory of the Jakarta EE {JakartaEEVersion} CI you are using. +
  The default setting for this property is
  `${web.home}/domains/domain1/autodeploy`.
  j. Verify that the `tools.jar` property is set to the location of the
  `tools.jar` file that is distributed with Java SE 8.
  k. Set the `porting.ts.url.class.1` property to your porting
  implementation class that is used for obtaining URLs. +
  The default setting for this property is
  `com.sun.ts.lib.implementation.sun.common.SunRIURL`.
  l. Optionally, to use vendor implementation of HttpsURLConnection,
  set the `porting.ts.HttpsURLConnection.class.1` property to that
  implementation of HttpsURLConnection. +
  The default setting for this property is
  `com.sun.ts.lib.implementation.sun.javaee.SunRIHttpsURLConnection`.
+
3.  Provide compatible implementation of the porting package interface
provided with the {TechnologyShortName} TCK. +
The porting package interface, `TSURLInterface.java`, obtains URL
strings for web resources in an implementation-specific manner. API
documentation for the `TSURLInterface.java` porting package interface is
available in the {TechnologyShortName} TCK documentation bundle.
4.  If the {TechnologyShortName} TCK test applications are published on
a Servlet 5.0-compliant Web container to run the CI, the `servlet_adaptor` 
property needs to be set in the `ts.jte` file, and CI-specific WAR files
containing the Servlet information need to be created for publishing. +
The CI-specific WAR files should never override any existing files that
come with the TCK. Refer to link:rebuild.html#GCLIZ[Appendix B,
"Packaging the Test Applications in Servlet-Compliant WAR
Files With VI-Specific Information,"] for more information.
5.  When creating CI-specific WAR files, deploying {TechnologyShortName} test
applications, or running the {TechnologyShortName} TCK, it is recommended that you
create a porting file named `$jaxrs_impl_name` under
`$TS_HOME/bin/xml/${impl_vi}`. +
Use the `jersey.xml` file as a reference.
6.  When creating CI-specific application server settings, it is
recommended that you create a configuring file named `config.vi.xml`
under `$TS_HOME/bin/xml/${impl_vi}`. +
Use the `$TS_HOME/bin/xml/glassfish/config.vi.xml` file as a reference.
7.  Run the `ant config.vi` target to configure the Compatible
Implementation that is defined in the `impl.vi` property.
+
[source,oac_no_warn]
----
cd <TS_HOME>/bin
ant config.vi
----
+
This target performs the following tasks:

* Stops the application server running the {TechnologyShortName} {TechnologyVersion} CI
* Copies the TCK-dependent files `${tslib.name}.jar` and `tsharness.jar` into the application server's external library folder
* Starts the application server
* Creates users and the appropriate roles
* Enables HTTP trace requests
* Sets up users and passwords for your {TechnologyShortName} server.

[[GCLHU]][[configuring-your-environment-to-repackage-and-run-the-tck-against-the-vendor-implementation]]

4.2 Configuring Your Environment to Repackage and Run the TCK Against the Vendor Implementation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

After configuring your environment as described in this section,
continue with the instructions in link:#GBFUY[Section 4.4, "Using the
JavaTest Harness Software."]


[NOTE]
=======================================================================

In these instructions, variables in angle brackets need to be expanded
for each platform. For example, `<TS_HOME>` becomes `$TS_HOME` on
Solaris/Linux and `%TS_HOME%` on Windows. In addition, the forward
slashes (`/`) used in all of the examples need to be replaced with
backslashes (`\`) for Windows. Finally, be sure to use the appropriate
separator for your operating system when specifying multiple path
entries (`;` on Windows, `:` on UNIX/Linux).

On Windows, you must escape any backslashes with an extra backslash in
path separators used in any of the following properties, or use forward
slashes as a path separator instead.

=======================================================================


[[sthref9]]

Before You Begin

Decide against which {TechnologyShortName} implementation the tests
will be run and determine to which Servlet–compliant Web server the
{TechnologyShortName} TCK applications will be published.

Package the {TechnologyShortName} test applications for that
{TechnologyShortName} implementation and Servlet–compliant Web
server.

See link:rebuild.html#GCLIZ[Appendix B, "Packaging the Test
Applications in Servlet-Compliant WAR Files With VI-Specific
Information,"] for information about repackaging the
{TechnologyShortName} test application.

1.  Set the following environment variables in your shell environment:
  a.  `JAVA_HOME` to the directory in which Java SE {SEversion} is installed
  b.  `TS_HOME` to the directory in which the {TechnologyShortName} TCK
  {TechnologyVersion} software is installed
  c.  +{TechnologyHomeEnv}+ to the directory in which the
  {TechnologyShortName} {TechnologyVersion} VI has been installed
  d.  `PATH` to include the following directories: `JAVA_HOME/bin`,
  +{TechnologyHomeEnv}/bin+, and `ANT_HOME/bin`
2.  Copy <TS_HOME>/bin/ts.jte.jdk11 as <TS_HOME>/bin/ts.jte if JAVA_HOME is Java SE 11.
Edit your `<TS_HOME>/bin/ts.jte` file and set the following
environment variables:
  a.  Set the `webServerHost` property to the name of the host on which
  your Web server is running that is configured with the Vendor
  Implementation. +
  The default setting is `localhost`.
  b.  Set the `webServerPort` property to the port number of the host on
  which the Web server is running that is configured with the Vendor
  {TechnologyShortName} {TechnologyVersion} Vendor Implementation. +
  The default setting is `8080`.
  c.  Set the `web.home` property to the installation directory of the Web
  server.
  d.  Set the `jaxrs.classes` property to point to the classes or JAR file
  for the {TechnologyShortName} {TechnologyVersion} API. +
  The default setting for this property is
  `${web.home}/modules/jakarta.ws.rs-api.jar`.
  e.  If you are using {TechnologyRI}, set the `jaxrs_impl_lib` and `jaxrs_impl.classes` properties to
  point to the {TechnologyRI} CI. +
  The default setting for the `jaxrs_impl_lib` property is
  `${web.home}/modules/jersey-container-servlet-core.jar` . +
  The default setting for the `jaxrs_impl.classes` property is as follows
  (all on one line):
+
[source,oac_no_warn]
----
${web.home}/modules/jersey-client.jar:
${web.home}/modules/jersey-common.jar:
${web.home}/modules/jersey-server.jar:
${web.home}/modules/jersey-container-servlet.jar:
${web.home}/modules/jersey-container-servlet-core.jar:
${web.home}/modules/jersey-media-jaxb.jar:
${web.home}/modules/jersey-media-sse.jar:
${web.home}/modules/jersey-hk2.jar
${web.home}/modules/osgi-resource-locator.jar:
${web.home}/modules/jakarta.inject.jar:
${web.home}/modules/guava.jar:
${web.home}/modules/hk2-api.jar:
${web.home}/modules/hk2-locator.jar:
${web.home}/modules/hk2-utils.jar:
${web.home}/modules/cglib.jar:
${web.home}/modules/asm-all-repackaged.jar:
${web.home}/modules/bean-validator.jar:
${web.home}/modules/jakarta.annotation-api.jar:
${web.home}/modules/jakarta.xml.bind-api.jar:
${web.home}/modules/jaxb-osgi.jar:
${web.home}/modules/jakarta.activation.jar:
${web.home}/modules/javassist.jar
----
+
  f.  Set the `servlet_adaptor` property to point to the Servlet adaptor
  class for the {TechnologyShortName} implementation. +
  The default setting for this property is
  `org/glassfish/jersey/servlet/ServletContainer.class`.
  g.  Set the `impl.vi` property to the name of the Web server. +
  The relevant porting files are located under the
  `$TS_HOME/bin/xml/impl/${impl.vi}/` directory. +
  The default setting for this property is `glassfish`.
  h.  Set the `jaxrs_impl_name` property to the name of the
  {TechnologyShortName} implementation to be tested. +
  The name of the property must be unique. A file bearing this name will
  be created under
  `$TS_HOME/bin/xml/impl/${impl.vi}/${jaxrs_impl_name}.xml` with packaging
  and/or deployment instructions. +
  The default setting for this property is `jersey`.
  i.  Set the `impl.vi.deploy.dir` property to point to the `autodeploy`
  directory for the Web server. +
  The default setting for this property is
  `${web.home}/domains/domain1/autodpeloy`.
  j. Verify that the `tools.jar` property is set to the location of the
  `tools.jar` file that is distributed with Java SE 8.
  k. Set the `porting.ts.url.class.1` property to your porting
  implementation class that is used for obtaining URLs. +
  The default setting for this property is
  `com.sun.ts.lib.implementation.sun.common.SunRIURL`.
  l. Optionally, to use your own implementation of HttpsURLConnection,
  set the `porting.ts.HttpsURLConnection.class.1` property to your
  implementation of HttpsURLConnection. +
  The default setting for this property is
  `com.sun.ts.lib.implementation.sun.javaee.SunRIHttpsURLConnection`.
+
3.  Provide your own implementation of the porting package interface
provided with the {TechnologyShortName} TCK. +
The porting package interface, `TSURLInterface.java`, obtains URL
strings for web resources in an implementation-specific manner. API
documentation for the `TSURLInterface.java` porting package interface is
available in the {TechnologyShortName} TCK documentation bundle.
4.  If the {TechnologyShortName} TCK test applications are published on
a Servlet 5.0-compliant Web container to certify the VI, the `servlet_adaptor`
property needs to be set in the `ts.jte` file, and VI-specific WAR files
containing the Servlet information need to be created for publishing. +
The VI-specific WAR files should never override any existing files that
come with the TCK. Refer to link:rebuild.html#GCLIZ[Appendix B,
"Packaging the Test Applications in Servlet-Compliant WAR
Files With VI-Specific Information,"] for more information.
5.  When creating VI-specific WAR files, deploying {TechnologyShortName} test
applications, or running the {TechnologyShortName} TCK, it is recommended that you
create a porting file named `$jaxrs_impl_name` under
`$TS_HOME/bin/xml/${impl_vi}`. +
Use the `jersey.xml` file as a reference.
6.  When creating VI-specific application server settings, it is
recommended that you create a configuring file named `config.vi.xml`
under `$TS_HOME/bin/xml/${impl_vi}`. +
Use the `$TS_HOME/bin/xml/glassfish/config.vi.xml` file as a reference.
7.  Run the `ant config.vi` target to configure the Compatible
Implementation that is defined in the `impl.vi` property.
+
[source,oac_no_warn]
----
cd <TS_HOME>/bin
ant config.vi
----
+
The target performs the following tasks:

* Stops the application server running the {TechnologyShortName} {TechnologyVersion} CI
* Copies the TCK-dependent files `${tslib.name}.jar` and `tsharness.jar` into the application server's external library folder
* Starts the application server
* Creates users and the appropriate roles
* Enables HTTP trace requests
* Sets up users and passwords for your {TechnologyShortName} server as follows:
+
--
[width="100%",cols="34%,33%,33%",options="header",]
|==============================
|User |Password |Groups
|`javajoe` |`javajoe` |`guest`
|`j2ee` |`j2ee` |`staff`, `mgr`
|==============================
Also make sure the principal to role-mappings that are specified in the
runtime XML files are properly mapped in your environment. These
mappings may vary for each application.
--

[[GHGDG]][[publishing-the-test-applications]]

4.3 Publishing the Test Applications
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The {TechnologyShortName} TCK provides an automatic way of deploying
both the prebuilt and Vendor-built archives to the configured web
container or containers by using deployment handlers.

The handler file (`<TS_HOME>/bin/xml/impl/glassfish/jersey.xml` is
written to be used with {TechnologyRI} and Jakarta EE {JakartaEEVersion} CI, Eclipse GlassFish 6.0. If the Vendor chooses
not to use Jakarta EE {JakartaEEVersion} CI, Eclipse GlassFish 6.0 to run with their implementation but still
chooses to publish to a Servlet-compliant Web container, then a
VI-specific {TechnologyShortName} TCK tests Web archive needs to be created.
This archive contains the VI-specific servlet class, and the Vendor should
create their own version handler file to provide this functionality. It
is recommended that the handler file be named `${jaxrs_impl_name}` and
be located in `$TS_HOME/bin/xml/${impl.vi}/${jaxrs_impl_name}`. Refer to
link:rebuild.html#GCLIZ[Appendix B, "Packaging the Test
Applications in Servlet-Compliant WAR Files With VI-Specific
Information,"] for more information.

This section describes the various commands used for deploying the
classes or WAR files to the configured web container.

* link:#GCLJG[Generic Deployment Command Scenarios]
* link:#GCLIW[Deploying the Prebuilt Archives]
* link:#GCLIL[Deploying the Test Applications Against the Vendor Implementation]

[[GCLJG]][[generic-deployment-command-scenarios]]

4.3.1 Generic Deployment Command Scenarios
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

You can deploy all {TechnologyShortName} WAR files to Jakarta EE {JakartaEEVersion} CI or
the Web server chosen by a Vendor, deploy just a single test directory,
or deploy of subset of test directories.

[[GCLJK]][[to-deploy-all-the-war-files-from-the-ts_homedist-to-a-web-server]]

4.3.1.1 To Deploy all the WAR Files From the <TS_HOME>/dist to a Web Server
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Type the following command:

[source,oac_no_warn]
----
ant deploy.all
----

[[GCLII]][[to-deploy-a-single-test-directory]]

4.3.1.2 To Deploy a Single Test Directory
+++++++++++++++++++++++++++++++++++++++++

Type the following commands:

[source,oac_no_warn]
----
cd <TS_HOME>/src/com/sun/ts/tests/jaxrs/api/rs/get
ant deploy
----

[[GCLJC]][[to-deploy-a-subset-of-test-directories]]

4.3.1.3 To Deploy a Subset of Test Directories
++++++++++++++++++++++++++++++++++++++++++++++

Type the following commands:

[source,oac_no_warn]
----
cd <TS_HOME>/src/com/sun/ts/tests/jaxrs/api 
ant deploy
----

[[GCLIW]][[deploying-the-prebuilt-archives]]

4.3.2 Deploying the Prebuilt Archives
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

[NOTE]
====
If you are using a compatible implementation that is not Eclipse GlassFish 6,
please refer to deployment details provided with that implementation.

====

This section explains issues regarding the deployment of the
{TechnologyShortName} TCK prebuilt archives. Before conducting any
deployment, ensure that your environment has been configured by
following the instructions in either link:#GBFVU[Section 4.1,
"Configuring Your Environment to Run the TCK Against the Eclipse GlassFish 6 Compatible
Implementation,"] or link:#GCLHU[Section 4.2, "Configuring Your
Environment to Repackage and Run the TCK Against the Vendor
Implementation."]

The `<TS_HOME>/dist` directory contains all WAR files for the
{TechnologyShortName} TCK tests that have been compiled and packaged
using the Eclipse GlassFish 6 Compatible Implementation for deployment on a
Servlet-compliant Web container, Jakarta EE {JakartaEEVersion} CI, using the standard Web
Archive (WAR) format.

These WAR files contain only {TechnologyRI}, a Jakarta EE {JakartaEEVersion} CI-specfic servlet
adaptor, and are tailored to run against the Eclipse GlassFish 6 Compatible Implementation.
These WAR files allow you to deploy (without any additional setup or
modification) against the Eclipse GlassFish 6 Compatible Implementation to test the various
features and functionality of this implementation.

To deploy the {TechnologyShortName} TCK tests, use either the `deploy`
or `deploy.all` batch command as described in link:#GCLJG[Section
4.3.1, "Generic Deployment Command Scenarios."]

[[GCLIL]][[deploying-the-test-applications-against-the-vendor-implementation]]

4.3.3 Deploying the Test Applications Against the Vendor Implementation
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

This section describes how to deploy the {TechnologyShortName} TCK test
applications against the Vendor Implementation and vendor's choice of
Web server.  Before conducting the deployment, ensure that you have
followed the instructions in link:#GCLHU[Section 4.2, "Configuring Your
Environment to Repackage and Run the TCK Against the Vendor
Implementation."] Vendors can deploy {TechnologyShortName} TCK tests in
either Java class or WAR format.  All test resource classes are located
under `$TS_HOME/classes`. All test resources packaged in WAR files are
located under `$TS_HOME/dist`.

If a vendor chooses to deploy WAR files on a Servlet–compliant Web
container other than a Jakarta EE {JakartaEEVersion} CI, it is necessary to build test WAR
files that contain the VI's servlet class and servlet class property in
the `web.xml` deployment descriptor, as specified in the {TechnologyShortName}
specification. The {TechnologyShortName} TCK comes with a
`web.xml.template` file for each test, which contains all information
except the servlet class. The {TechnologyShortName} TCK also comes with
a tool to replace the CI or VI's servlet adaptor class name in the
`web.xml.template`. Refer to link:rebuild.html#GCLIZ[Appendix B,
"Packaging the Test Applications in Servlet-Compliant WAR Files With
VI-Specific Information,"] for more information about repackaging the
{TechnologyShortName} TCK tests.

To deploy a resource class in class file format, a vendor must create a
handler file that supports deploy options as described in
link:#GCLJG[Section 4.3.1, "Generic Deployment Command Scenarios."] All
tests with resource classes have a `${resource.classes}` property set in
each test's individual `build.xml` file; this value contains all
resource classes in the test.

To deploy the tests, the vendor should perform a deployment using either
the `deploy` or `deploy.all` batch command, as described in
link:#GCLJG[Section 4.3.1, "Generic Deployment Command Scenarios."]
