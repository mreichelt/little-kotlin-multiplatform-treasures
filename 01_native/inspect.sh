#!/bin/bash

set -euox pipefail

# run Mac app
build/bin/macosArm64/releaseExecutable/01_native.kexe

# get some basic info on any file by running `file <file>`
file build/bin/macosArm64/releaseExecutable/01_native.kexe
file build/bin/iosArm64/releaseFramework/01_native.framework/01_native
file build/fat-framework/release/01_native.framework/01_native

# find all binaries(.kexe, .exe, binary in .framework folders)
find build -name '*.exe' -or -name '01_native' -maxdepth 6

binaries=$(find build -name '*.exe' -or -name '01_native' -maxdepth 6)
echo "$binaries" | xargs ls -alh # show size of files
echo "$binaries" | xargs file    # show file type for all files

# show linked libraries of a Mac/iOS binary:
otool -L build/bin/macosArm64/releaseExecutable/01_native.kexe

# this does not work on non-Mac-O binaries, so for the others we can use objdump
objdump -p build/bin/linuxX64/releaseExecutable/01_native.kexe
objdump -p build/bin/linuxX64/releaseExecutable/01_native.kexe | grep NEEDED

# you can even look at assembly code of binaries! 🌟
objdump -d build/bin/linuxX64/releaseExecutable/01_native.kexe | head -n 100

# another handy tool: with 'strings' you can find ASCII
#  strings in a file (not Unicode though)
strings build/bin/linuxX64/releaseExecutable/01_native.kexe
strings build/bin/linuxX64/releaseExecutable/01_native.kexe | grep -i kotlin
strings build/bin/linuxX64/releaseExecutable/01_native.kexe | grep -i treasure

# but we didn't find the treasure yet - let's use Ghidra to do that!
