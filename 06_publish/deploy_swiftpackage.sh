#!/bin/bash

set -euox pipefail
pushd .. # work in project root

./gradlew 06_publish:deploySwiftPackage

popd
