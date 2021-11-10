///////////////////////////////////////////////////////////////////////
NOTE TO WRITERS:
These CLI examples can be customized as necessary.
///////////////////////////////////////////////////////////////////////

1.  Change to any subdirectory under `<TS_HOME>/src/com/sun/ts/tests`.
2.  Start JavaTest using the following command:
+
--
[source,oac_no_warn]
----
ant runclient
----
--

[[GCMCU]]

Example 5-1 {TechnologyShortName} TCK Signature Tests

To run the {TechnologyShortName} TCK signature tests, enter the
following commands:

[source,subs="attributes"]
----
cd {sigTestDirectoryExample}
ant runclient
----

[[GCMBV]]


Example 5-2 Single Test Directory

To run a single test directory, enter the following commands:

[source,subs="attributes"]
----
cd {singleTestDirectoryExample}
ant runclient
----

[[GCMCA]]


Example 5-3 Subset of Test Directories

To run a subset of test directories, enter the following commands:

[source,subs="attributes"]
----
cd {subsetTestDirectoryExample}
ant runclient
----
