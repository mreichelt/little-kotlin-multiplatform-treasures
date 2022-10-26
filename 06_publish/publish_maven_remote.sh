#!/bin/bash

set -euox pipefail
pushd .. # work in project root

# publishes to a remote maven repo, e.g. artifactory (task name contains the name you gave the repo)
./gradlew publishAllPublicationsToDroidconrepoRepository

popd
