#!/bin/bash
#
# Copyright:: Copyright (c) 2017-present Sonatype, Inc. Apache License, Version 2.0.
#

cd /opt/sonatype/nexus

exec ./bin/nexus run

## Use the below extra commands to change admin password.

# java -jar "${NEXUS_HOME}"/lib/support/nexus-orient-console.jar "${NEXUS_HOME}"/orientdb.txt

# pkill java

# exec ./bin/nexus run
