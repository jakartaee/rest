#!/bin/bash -xe

# Copyright (c) 2022 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0, which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# This Source Code may also be made available under the following Secondary
# Licenses when the conditions for such availability set forth in the
# Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
# version 2 with the GNU Classpath Exception, which is available at
# https://www.gnu.org/software/classpath/license.html.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0

if [ -z "$WORKSPACE" ]; then
  export WORKSPACE="$(dirname "$(pwd)")"
fi

cd $WORKSPACE

export VERSION="$2"

if [ -z "$VERSION" ]; then
  export VERSION="3.1.2"
fi

if [[ "$1" == "epl" || "$1" == "EPL" ]]; then
    mvn clean install
    sed "s/jakarta-restful-ws-tck/restful-ws-tck/g" $WORKSPACE/jaxrs-tck/pom.xml > $WORKSPACE/jaxrs-tck/pom.epl.xml
    cd $WORKSPACE/jaxrs-tck
    mvn clean install -f pom.epl.xml
else
    mvn clean install
fi

cd $WORKSPACE/jaxrs-tck-docs/userguide
mvn

rm -rf $WORKSPACE/bundle

mkdir -p $WORKSPACE/bundle/docs
mkdir -p $WORKSPACE/bundle/docs/html-usersguide
mkdir -p $WORKSPACE/bundle/docs/pdf-usersguide
cp $WORKSPACE/jaxrs-tck-docs/userguide/target/generated-docs/*.pdf $WORKSPACE/bundle/docs/pdf-usersguide/
cp $WORKSPACE/jaxrs-tck-docs/userguide/target/staging/*.html $WORKSPACE/bundle/docs/html-usersguide/
cp -r $WORKSPACE/jaxrs-tck-docs/userguide/target/staging/css $WORKSPACE/bundle/docs/html-usersguide/
cp -r $WORKSPACE/jaxrs-tck-docs/userguide/target/staging/img $WORKSPACE/bundle/docs/html-usersguide/
cp $WORKSPACE/jaxrs-tck-docs/*.html $WORKSPACE/bundle/docs/
cp $WORKSPACE/jaxrs-tck-docs/*.txt $WORKSPACE/bundle/docs/
cp -r $WORKSPACE/jaxrs-tck-docs/assertions $WORKSPACE/bundle/docs/

mkdir -p $WORKSPACE/bundle
cp $WORKSPACE/jaxrs-tck/target/*.jar $WORKSPACE/bundle/

cd $WORKSPACE/bundle

if [[ "$1" == "epl" || "$1" == "EPL" ]]; then
    cp $WORKSPACE/LICENSE.md $WORKSPACE/bundle/LICENSE.md
    cp $WORKSPACE/jaxrs-tck/pom.epl.xml $WORKSPACE/bundle/restful-ws-tck-"$VERSION".pom
    zip -r restful-ws-tck-"$VERSION".zip *
else
    cp $WORKSPACE/jaxrs-tck-docs/LICENSE_EFTL.md $WORKSPACE/bundle/LICENSE.md
    cp $WORKSPACE/jaxrs-tck/pom.xml $WORKSPACE/bundle/jakarta-restful-ws-tck-"$VERSION".pom
    zip -r jakarta-restful-ws-tck-"$VERSION".zip * 
fi
