#!/bin/bash

set -euo pipefail

# script is meant to be run in the build/swiftPackage directory

# Git repo where the Package.swift is stored (usually remote).
# If you don't have it yet, you can create it with:
#   git init --bare --initial-branch=main ~/06_publish_swift_package
GITHUB_REPO=~/06_publish_swift_package

if [ -z "$1" ]; then
  echo "No version given"
  exit 1
fi
VERSION="$1"

rm -Rf manifest_repo
git clone "$GITHUB_REPO" manifest_repo
pushd manifest_repo

# commit locally
cp ../Package.swift Package.swift
if [ -z "$(git status --porcelain)" ]; then
	echo "No changes for version $VERSION"
	exit 1
fi

echo "Updating Package.swift to version $VERSION"
git add Package.swift
git commit -m "update Package.swift to $VERSION"

# push main
git push origin main

# add + push tag
echo "Adding tag $VERSION"
git push origin ":refs/tags/$VERSION"
git tag "$VERSION"
git push origin "$VERSION"

popd
