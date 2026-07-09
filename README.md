# fasp-api — QAT Backend API

The **Quantification Analytics Tool (QAT)** is an open-source, country-led platform for health commodity forecasting and supply planning. It replaces the legacy PipeLine (supply planning) and Quantimed (forecasting) tools with a unified, cloud-based system that supports offline use and provides advanced data visualization, master data management, and updated planning logic.

This repository contains the Java/Spring Boot REST API that powers the QAT backend. The frontend is maintained separately in the [fasp-core-ui](https://github.com/FASP-QAT/fasp-core-ui) repository.

QAT is funded by PEPFAR, PMI, USAID's FP/RH program, and USAID's MCH program, and is maintained by Chemonics International Inc. It is released under the [Apache 2.0 License](./LICENSE.md).

---

## Table of Contents

- [Modules](#modules)
- [Branch Strategy](#branch-strategy)
- [Related Repositories](#related-repositories)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Build and Deploy](#build-and-deploy)
- [Configuration](#configuration)
- [Documentation](#documentation)
- [Contributing](#contributing)
- [License](#license)

---

## Modules

QAT is composed of two functional modules, both served by this API:

| Module | Description | Released |
|---|---|---|
| Supply Planning | Country-level supply plan management, stock projections, shipment tracking, and reorder calculations | December 2020 |
| Forecasting | Consumption-based and demographic forecasting with scenario planning support | June 2022 |

---

## Branch Strategy

| Branch | Purpose |
|---|---|
| `master2` | Latest stable production release. Always pull from here for the most recent release. |
| `devMod2` | Active development branch. Code here is in progress and not production-ready. |
| `master.{major}.{minor}` | Tagged release branches. Production releases are tagged in this format going forward. |

> Prior to open-sourcing, all production code was released on `master2` only. New production releases are now tagged as `master.major_no.minor_no`.

To get the latest stable release:

```sh
git clone https://github.com/FASP-QAT/fasp-api.git
git checkout master2
```

---

## Related Repositories

| Repository | Description |
|---|---|
| [fasp-core-ui](https://github.com/FASP-QAT/fasp-core-ui) | React-based frontend application |
| [fasp-db](https://github.com/FASP-QAT/fasp-db) | Database schema and seed scripts |

---

## Prerequisites

Before building or running the API, ensure the following are installed and configured:

- **Java** — JDK 8 or higher
- **Maven** — 3.6+ (or use the included `mvnw` wrapper)
- **MySQL** — A running MySQL instance with a database provisioned for QAT
- **Git**

---

## Getting Started

### 1. Clone the repository

```sh
git clone https://github.com/FASP-QAT/fasp-api.git
cd fasp-api
git checkout master2
```

### 2. Configure the database connection

Edit `src/main/resources/application.properties` (or `application.yml`) and set your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fasp_db
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Replace `fasp_db`, `your_db_user`, and `your_db_password` with values matching your local MySQL setup.

### 3. Build the project

Using the Maven wrapper (no local Maven installation required):

```sh
./mvnw clean package -DskipTests
```

Or with a locally installed Maven:

```sh
mvn clean package -DskipTests
```

### 4. Run the application

```sh
java -jar target/fasp-api-*.jar
```

The API will start on `http://localhost:8080` by default.

---

## Build and Deploy

A build-and-deploy script is included for streamlined deployment to a server:

```sh
./buildAndDeploy.sh
```

Review the script before running it to ensure the target paths and environment variables match your deployment environment.

For full instructions on hosting QAT in a production environment, refer to the [Technical Documentation](./docs/QAT-technical-manual.md) in the `docs/` folder.

---

## Configuration

Key application properties you may need to adjust:

| Property | Description |
|---|---|
| `spring.datasource.url` | JDBC connection URL for your MySQL database |
| `spring.datasource.username` | Database username |
| `spring.datasource.password` | Database password |
| `server.port` | Port the API listens on (default: `8080`) |

For environment-specific configuration (e.g., staging, production), use Spring Boot profiles or externalize the `application.properties` file outside the JAR at deployment time.

---

## Documentation

- [Technical Manual](./docs/QAT-technical-manual.md) — Architecture overview, hosting guide, and additional technical details
- [HELP.md](./HELP.md) — Quick reference for common Spring Boot tasks
- [CONTRIBUTING.md](./CONTRIBUTING.md) — Contributor guidelines
- [CODE_OF_CONDUCT.md](./CODE_OF_CONDUCT.md) — Community standards

---

## Contributing

Contributions are welcome. Please read [CONTRIBUTING.md](./CONTRIBUTING.md) before opening a pull request.

Bug reports and feature requests should be filed in the [GitHub Issues tracker](https://github.com/FASP-QAT/fasp-api/issues).

---

## License

Copyright © Chemonics International Inc.

Licensed under the [Apache License, Version 2.0](./LICENSE.md). You may use, reproduce, distribute, and prepare derivative works of this software, provided that all copyright notices and this license statement are retained in all copies and derivative works.
