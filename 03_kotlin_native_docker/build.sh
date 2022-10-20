#!/bin/bash
set -euo pipefail

docker build --platform linux/amd64 --tag droidcon .
