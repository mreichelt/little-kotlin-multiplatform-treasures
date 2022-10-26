#!/bin/bash

set -euox pipefail
pushd .. # work in project root

# publishes all artifacts to the ~/.m2/repository folder, locally on your machine
./gradlew publishToMavenLocal
ls -al ~/.m2/repository/com/example/droidcon/

popd
