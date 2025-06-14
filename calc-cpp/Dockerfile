# Stage 1: Build
FROM debian:bookworm as build

RUN apt-get update && apt-get install -y \
    build-essential \
    cmake \
    git \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

RUN git clone https://github.com/CrowCpp/Crow.git /tmp/crow && \
    cp /tmp/crow/include/crow_all.h /app/

COPY . .

RUN mkdir build && cd build && \
    cmake .. && \
    cmake --build .

# Stage 2: Runtime
FROM debian:bookworm-slim

WORKDIR /app

COPY --from=build /app/build/calc-cpp .

EXPOSE 8081

ENTRYPOINT ["./calc-cpp"]
