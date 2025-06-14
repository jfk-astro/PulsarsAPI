# PulsarsAPI
> PulsarsAPI is an API for easy access to Pulsars. This project is made using Springboot for Java and Crow for C++.

PulsarsAPI is a project composed of many microservices aimed to give easy access to pulsar data via a database from the [Australia Telescope National Facility](https://www.atnf.csiro.au/).

PulsarsAPI provides various endpoints for **calculations and data.** Some example endpoints would be `/api/pulsars` and `/api/calculate`.

## Features
- Various endpoints for pulsars.
- Various endpoints for calculations.
- Calculation data such as `age`, `bfield`, and `edot`.
- Derived properties such as `lorentz`, `surface-gravity`, and `pulsar-distance`.
- Visualization data for graphs such as "Age vs Magnetic Field" and "Edot vs Period".

## Architecture
In order to make this project work, there are 2 main microservices, `api-java` and `calc-cpp`.

`api-java` is a Springboot application made with JDK 17 that has all the endpoints of the API. This is also responsible for calling the second service, `calc-cpp`, for calculations. We chose Springboot as our main framework due to its great infrastructure setup done before hand and auto configurations.

The second microservice, `calc-cpp`, is responsible for doing all of the calculations for the calculation endpoint. We chose to use C++ for this in order to save on compute time and resources, this makes our project more efficient and more resource friendly. It was not feasible to make the main api in C++ due to the massive amount of time needed and lack of major cross platform support.

## Build
> [!CAUTION]
> **In order to build and edit this project, you need JDK 17+, GCC, and Python 3.11+!**

To build this project, you can use the dockerfiles within each directory. The `api-java` folder is responsible for the Springboot layer and provides the public endpoints for people to access.

### api-java
To compile this, you'll first have to run `mvnw package`.

```bash
# If you're on MacOS, you'll have to run this once to give it run permissions.
# "+x" grants the mvnw file execute permission.
chmod +x ./mvnw

# Compile the Java code WITHOUT testing.
# We cannot use tests as the JPA will not find it database and error out.
./mvnw build -DskipTests

# Build the docker container.
docker build -t api-java .
```

### calc-cpp
In order to make this project, you must have a version of GCC or any suitable C++ compiler present. This microservice uses CMake in order to manage all of its libraries.

```bash
# Change directory into the calc-cpp folder.
cd calc-cpp

# Build the docker container.
docker build -t calc-cpp .
```

After compilation, you should get an executable file such as a `.exe`.

## Credits
PulsarsAPI was writen by Saumil Sharma and Om Kasar.