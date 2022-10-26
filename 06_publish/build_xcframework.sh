#!/bin/bash

set -euox pipefail
pushd .. # work in project root

./gradlew 06_publish:assembleXCFramework

ls -al build/XCFrameworks/release/06_publish.xcframework/

popd
