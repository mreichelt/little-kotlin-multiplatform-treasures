#!/bin/bash
set -euox pipefail

docker run --rm --platform linux/amd64 --entrypoint bash -it droidcon
