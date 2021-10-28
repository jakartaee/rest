/*
 * Copyright (c) 2007, 2021 Oracle and/or its affiliates and others.
 * All rights reserved.
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

/*
 * $Id$
 */

package com.sun.ts.tests.signaturetest;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Properties;

import com.sun.ts.lib.harness.EETest;
import com.sun.ts.lib.util.TestUtil;

/**
 * This class should be extended by TCK developers that wish to create a set of
 * signature tests that run outside of any Java EE container. Developers must
 * implement the getPackages method to specify which packages are to be tested
 * by the signature test framework.
 */
public abstract class SigTest extends EETest {

  protected SignatureTestDriver driver;

  /**
   * <p>
   * Returns a {@link SignatureTestDriver} appropriate for the particular TCK
   * (using API check or the Signature Test Framework).
   * </p>
   *
   * <p>
   * The default implementation of this method will return a
   * {@link SignatureTestDriver} that will use API Check. TCK developers can
   * override this to return the desired {@link SignatureTestDriver} for their
   * TCK.
   */
  protected SignatureTestDriver getSigTestDriver() {

    if (driver == null) {
      driver = SignatureTestDriverFactory.getInstance(SignatureTestDriverFactory.SIG_TEST);
    }

    return driver;

  } // END getSigTestDriver

  /**
   * Returns the location of the package list file. This file denotes the valid
   * sub-packages of any package being verified in the signature tests.
   *
   * Sub-classes are free to override this method if they use a different path
   * or filename for their package list file. Most users should be able to use
   * this default implementation.
   *
   * @return String The path and name of the package list file.
   */
  protected String getPackageFile() {
    return getSigTestDriver().getPackageFileImpl(testInfo.getBinDir());
  }

  /**
   * Returns the path and name of the signature map file that this TCK uses when
   * conducting signature tests. The signature map file tells the signature test
   * framework which API versions of tested packages to use. To keep this code
   * platform independent, be sure to use the File.separator string (or the
   * File.separatorChar) to denote path separators.
   *
   * Sub-classes are free to override this method if they use a different path
   * or filename for their signature map file. Most users should be able to use
   * this default implementation.
   *
   * @return String The path and name of the signature map file.
   */
  protected String getMapFile() {
    return getSigTestDriver().getMapFileImpl(testInfo.getBinDir());
  }

  /**
   * Returns the directory that contains the signature files.
   *
   * Sub-classes are free to override this method if they use a different
   * signature repository directory. Most users should be able to use this
   * default implementation.
   *
   * @return String The signature repository directory.
   */
  protected String getRepositoryDir() {
    return getSigTestDriver().getRepositoryDirImpl(testInfo.getTSHome());
  }

  /**
   * Returns the list of Optional Packages which are not accounted for. By
   * 'unlisted optional' we mean the packages which are Optional to the
   * technology under test that the user did NOT specifically list for testing.
   * For example, with Java EE 7 implementation, a user could additionally opt
   * to test a JSR-88 technology along with the Java EE technology. But if the
   * user chooses NOT to list this optional technology for testing (via ts.jte
   * javaee.level prop) then this method will return the packages for JSR-88
   * technology with this method call.
   * <p/>
   * This is useful for checking for a scenarios when a user may have forgotten
   * to identify a whole or partial technology implementation and in such cases,
   * Java EE platform still requires testing it.
   * <p/>
   * Any partial or complete impl of an unlistedOptionalPackage sends up a red
   * flag indicating that the user must also pass tests for this optional
   * technology area.
   * <p/>
   * Sub-classes are free to override this method if they use a different
   * signature repository directory. Most users should be able to use this
   * default implementation - which means that there was NO optional technology
   * packages that need to be tested.
   *
   * @return ArrayList<String>
   */
  protected ArrayList<String> getUnlistedOptionalPackages() {
    return null;
  }

  /**
   * Returns the list of packages that must be tested by the siganture test
   * framework. TCK developers must implement this method in their signature
   * test sub-class.
   *
   * @return String A list of packages that the developer wishes to test using
   *         the signature test framework.
   */
  protected abstract String[] getPackages();

  /**
   * Returns an array of individual classes that must be tested by the signature
   * test framwork. TCK developers may override this method when this
   * functionality is needed. Most will only need package level granularity.
   *
   * @return an Array of Strings containing the individual classes the framework
   *         should test. The default implementation of this method returns a
   *         zero-length array.
   */
  protected String[] getClasses() {

    return new String[] {};

  } // END getClasses

  protected SigTestData testInfo; // holds the bin.dir property

  /**
   * Called by the test framework to initialize this test. The method simply
   * retrieves some state information that is necessary to run the test when
   * when the test framework invokes the run method (actually the test1 method).
   *
   * @param args
   *          List of arguments passed to this test.
   * @param p
   *          Properties specified by the test user and passed to this test via
   *          the test framework.
   * @throws Fault
   *           When an error occurs reading or saving the state information
   *           processed by this method.
   */
  public void setup(String[] args, Properties p) throws Fault {
    try {
      TestUtil.logTrace("$$$ SigTest.setup() called");
      this.testInfo = new SigTestData(p);
      TestUtil.logTrace("$$$ SigTest.setup() complete");
    } catch (Exception e) {
      logErr("Unexpected exception " + e.getMessage());
      throw new Fault("setup failed!", e);
    }
  }

  /**
   * Called by the test framework to run this test. This method utilizes the
   * state information set in the setup method to run the signature tests. All
   * signature test code resides in the utility class so it can be reused by the
   * signature test framework base classes.
   *
   * @throws Fault
   *           When an error occurs executing the signature tests.
   */
  public void signatureTest() throws Fault {
    TestUtil.logTrace("$$$ SigTest.test1() called");
    SigTestResult results = null;
    String mapFile = getMapFile();
    String repositoryDir = getRepositoryDir();
    String[] packages = getPackages();
    String[] classes = getClasses();
    String packageFile = getPackageFile();
    String testClasspath = testInfo.getTestClasspath();
    String optionalPkgToIgnore = testInfo.getOptionalTechPackagesToIgnore();

    // unlisted optional technology packages are packages for optional
    // technologies that were not specified by the user. We want to
    // ensure there are no full or partial implementations of an
    // optional technology which were not declared.
    ArrayList<String> unlistedTechnologyPkgs = getUnlistedOptionalPackages();

    // If testing with Java 9+, extract the JDK's modules so they can be used
    // on the testcase's classpath.
    Properties sysProps = System.getProperties();
    String version = (String) sysProps.get("java.version");
    if (!version.startsWith("1.")) {
      String jimageDir = testInfo.getJImageDir();
      File f = new File(jimageDir);
      f.mkdirs();

      String javaHome = (String) sysProps.get("java.home");
      TestUtil.logMsg("Executing JImage");

      try {
        ProcessBuilder pb = new ProcessBuilder(javaHome + "/bin/jimage", "extract", "--dir=" + jimageDir, javaHome + "/lib/modules");
        TestUtil.logMsg(javaHome + "/bin/jimage extract --dir=" + jimageDir + " " + javaHome + "/lib/modules");
        pb.redirectErrorStream(true);
        Process proc = pb.start();
        BufferedReader out = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String line = null;
        while ((line = out.readLine()) != null) {
          TestUtil.logMsg(line);
        }

        int rc = proc.waitFor();
        TestUtil.logMsg("JImage RC = " + rc);
        out.close();
      } catch (Exception e) {
        TestUtil.logMsg("Exception while executing JImage!  Some tests may fail.");
        e.printStackTrace();
      }
    }

    try {
      results = getSigTestDriver().executeSigTest(packageFile, mapFile,
          repositoryDir, packages, classes, testClasspath,
          unlistedTechnologyPkgs, optionalPkgToIgnore);
      TestUtil.logMsg(results.toString());
      if (!results.passed()) {
        TestUtil.logTrace("results.passed() returned false");
        throw new Exception();
      }
      TestUtil.logTrace("$$$ SigTest.test1() returning");
    } catch (Exception e) {
      if (results != null && !results.passed()) {
        throw new Fault("SigTest.test1() failed!, diffs found");
      } else {
        TestUtil.logErr("Unexpected exception " + e.getMessage());
        throw new Fault("test1 failed with an unexpected exception", e);
      }
    }
  }

  /**
   * Called by the test framework to cleanup any outstanding state. This method
   * simply passes the message through to the utility class so the
   * implementation can be used by both framework base classes.
   *
   * @throws Fault
   *           When an error occurs cleaning up the state of this test.
   */
  public void cleanup() throws Fault {
    TestUtil.logTrace("$$$ SigTest.cleanup() called");
    try {
      getSigTestDriver().cleanupImpl();
      TestUtil.logTrace("$$$ SigTest.cleanup() returning");
    } catch (Exception e) {
      throw new Fault("Cleanup failed!", e);
    }
  }

} // end class SigTest
