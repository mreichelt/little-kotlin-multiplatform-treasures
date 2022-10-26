#!/bin/bash

set -euox pipefail
pushd ..

### NOTE: `clean` is only used here to skip the cache, so we can show the different timings
###  of course you shouldn't call it when you want faster builds ;)


# this can take some time (compiles everything, runs all tests on all platforms)
./gradlew 05:clean 05:check

# but you can have a much faster feedback loop:
./gradlew 05:clean 05:jvmTest                 # run just JVM tests
./gradlew 05:clean 05:jsTest                  # run just JS tests
./gradlew 05:clean 05:iosSimulatorArm64Test   # run just iOS tests (but only re-compiles 1 target)

# or if you want builds only, not tests:
./gradlew 05:clean 05:jvmJar                          # compile just JVM stuff
./gradlew 05:clean 05:compileKotlinIosSimulatorArm64  # compile just iOS stuff (1 target)

popd
