#!/bin/bash
set -euox pipefail

cp -a ../02_location_distance/build/bin/linuxX64/releaseExecutable/02_location_distance.kexe .
docker build --platform linux/amd64 --tag tiny_droidcon .
