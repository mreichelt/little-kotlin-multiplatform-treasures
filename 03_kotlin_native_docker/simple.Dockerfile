FROM ubuntu:22.04
COPY 02_location_distance.kexe /location_distance
ENTRYPOINT ["/location_distance"]
