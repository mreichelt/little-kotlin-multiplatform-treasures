FROM ubuntu:22.04 AS build-stage

FROM scratch AS export-stage

COPY --from=build-stage /usr/lib/x86_64-linux-gnu/libc.so.6 /lib/
COPY --from=build-stage /usr/lib/x86_64-linux-gnu/libpthread.so.0 /lib/
COPY --from=build-stage /usr/lib/x86_64-linux-gnu/libdl.so.2 /lib/
COPY --from=build-stage /usr/lib/x86_64-linux-gnu/libgcc_s.so.1 /lib/
COPY --from=build-stage /usr/lib/x86_64-linux-gnu/libm.so.6 /lib/
COPY --from=build-stage /usr/lib/x86_64-linux-gnu/ld-linux-x86-64.so.2 /lib64/

COPY 02_location_distance.kexe /location_distance

ENTRYPOINT ["/location_distance"]
