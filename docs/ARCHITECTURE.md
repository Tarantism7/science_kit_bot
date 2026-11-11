# System Architecture
   The application consists of several components that work together to process user requests and perform scientific calculations.

## System Architecture Diagram
```mermaid
flowchart TD
A["Telegram User"] --> B["Telegram Bot"]
B --> C["Java - Spring Boot"]

    C --> D["Command Processing"]
    D --> E["Calculation Request"]

    E --> F["JSON Request File"]
    F --> G{"Rust Engine Available?"}

    G -->|Yes| H["Rust Calculation Engine"]
    H --> I{"Calculation Successful?"}

    I -->|Yes| J["Rust Result"]
    I -->|No| K["Java Fallback Calculator"]

    G -->|No| K

    J --> L["Java Result Processing"]
    K --> L

    L --> M["Telegram Response"]
    M --> A
```
Component Responsibilities
 - ![Telegram](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white) Bot:

Receives messages and commands.
Sends responses to users.
Acts as the communication interface.

- ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) Application:

Processes incoming commands.
Validates calculation requests.
Prepares JSON requests.
Communicates with the Rust engine.
Handles errors and fallback calculations.
Formats calculation results.

- ![Rust](https://img.shields.io/badge/rust-%23000000.svg?style=for-the-badge&logo=rust&logoColor=white) Calculation Engine:

Receives JSON calculation requests.
Parses input data.
Executes calculations.
Returns calculation results.

- ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) Fallback Calculator:

Provides an alternative calculation implementation.
Takes over when Rust is unavailable or fails.
Allows calculations to continue without depending entirely on Rust.

### Calculation Architecture
Calculation Workflow 

The calculation process follows these steps:
1) A user sends a calculation command to Telegram.
2) Java receives and processes the command.
3) Java validates the input.
4) Java prepares a JSON file containing the calculation request.
5) The JSON request is sent to the Rust engine.
6) Rust processes the calculation.
7) Rust returns the calculation result to Java.
8) Java validates and processes the result.
9) Java sends the final response to Telegram.
10) If Rust is unavailable or encounters an error, Java uses its own calculator instead.

# Database Architecture
The application uses PostgreSQL for persistent data storage.

Spring Data JPA provides database interaction, while Liquibase manages database schema migrations.

Responsibilities
-  ![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white) PostgreSQL

Stores persistent application data.
Maintains structured scientific information.
- Spring Data JPA

Provides object-relational mapping.
Handles database operations.
- Liquibase

Tracks database schema changes.
Applies migrations.
Helps maintain consistent database structure.
