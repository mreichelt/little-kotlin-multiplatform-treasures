#!/bin/bash

set -euox pipefail
pushd .. # work in project root

./gradlew 06_publish:createSwiftPackageManifest

ls -alh 06_publish/build/swiftPackage/

popd
