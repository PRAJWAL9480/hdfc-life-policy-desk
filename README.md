# HDFC Life Policy Desk API

A Spring Boot REST API for managing HDFC Life insurance policies and claims.

## Technology Stack

- Java 17+
- Spring Boot 3.5.5
- Spring Web
- Spring Validation
- Spring Data JPA
- H2 Database
- PostgreSQL
- Flyway
- OpenAPI / Swagger UI
- Maven

## Project Structure

```text
src/main/java/com/hdfclife/desk
│
├── config
│   ├── DataSeeder.java
│   ├── HdfcProperties.java
│   ├── OpenApiConfig.java
│   └── StoreLifecycle.java
│
├── exception
│   ├── DeskException.java
│   ├── PolicyNotFoundException.java
│   ├── ClaimNotFoundException.java
│   ├── DuplicatePolicyException.java
│   ├── InvalidClaimException.java
│   └── RestExceptionHandler.java
│
├── model
│   ├── Policy.java
│   ├── Claim.java
│   └── Urgency.java
│
├── service
│   ├── PolicyService.java
│   └── ClaimService.java
│
├── store
│   ├── PolicyStore.java
│   └── InMemoryPolicyStore.java
│
└── web
    ├── PolicyController.java
    └── ClaimController.java
Running the Application
Development

The default profile is dev.

spring.profiles.active=dev

The development profile uses an in-memory H2 database.

Run:

mvn spring-boot:run

Application URL:

http://localhost:8080
Swagger UI

Swagger documentation is available at:

http://localhost:8080/swagger-ui/index.html
H2 Console

H2 console:

http://localhost:8080/h2-console

JDBC URL:

jdbc:h2:mem:hdfclife;MODE=PostgreSQL;DB_CLOSE_DELAY=-1

Username:

sa
Production Configuration

The prod profile uses PostgreSQL.

Set these environment variables:

DB_URL
DB_USER
DB_PASSWORD

Then run:

mvn spring-boot:run -Dspring-boot.run.profiles=prod

Database schema creation and migrations are managed by Flyway.

Hibernate schema generation is disabled with:

ddl-auto: none
Database Relationships

The application database contains five tables:

customers
policies
claims
riders
policy_riders

Relationships:

customers 1 ---- * policies

policies 1 ---- * claims

policies * ---- * riders
                  |
             policy_riders
REST API
Policies
Method	Endpoint	Description
GET	/api/policies	Get all policies
GET	/api/policies/{policyNo}	Get policy by number
GET	/api/policies?status=Active	Filter by status
GET	/api/policies?type=TERM	Filter by type
POST	/api/policies	Create policy
PUT	/api/policies/{policyNo}	Update policy
DELETE	/api/policies/{policyNo}	Delete policy
GET	/api/policies/{policyNo}/claims	Get claims for policy
Claims
Method	Endpoint	Description
POST	/api/claims	File a claim
GET	/api/claims/{claimNo}	Get claim by number
HTTP Status Codes
Status	Meaning
200	Successful request
201	Resource created
204	Resource deleted
400	Invalid claim
404	Resource not found
409	Duplicate policy
405	HTTP method not supported
Seeded Policies

The application starts with six policies:

Policy No	Customer	Type	Premium	Status
HDFC-LIFE-1001	Anita Sharma	TERM	18500	Active
HDFC-LIFE-1002	Rahul Mehta	ULIP	42000	Active
HDFC-LIFE-1003	Priya Nair	ENDOWMENT	27000	Lapsed
HDFC-LIFE-1004	Vikram Singh	TERM	15200	Active
HDFC-LIFE-1005	Sneha Patel	ULIP	36000	Active
HDFC-LIFE-1006	Anita Sharma	ENDOWMENT	22000	Pending

Startup verification:

Seeded policy count → 6
Active policy count via PolicyService → 4
TERM policy count via PolicyService → 2
Unique customer count → 5
Claim Rules
Claim amount must be greater than zero.
Maximum claim amount is ₹5,00,000.
Policy must exist before a claim can be filed.
Newly created claims have status SUBMITTED.
Claim numbers are generated as CLM-01, CLM-02, etc.
Dependency Injection

The application uses constructor injection throughout.

Example:

public PolicyService(PolicyStore policyStore) {
    this.policyStore = policyStore;
}

The PolicyStore interface is implemented by:

InMemoryPolicyStore
Database Migration


Flyway migrations are located under:

src/main/resources/db/migration

Current migrations:

V1__hdfc_life_schema.sql
V2__seed_reference_riders.sql

Flyway owns database schema creation and migration.

Architecture
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
PolicyStore
     ↓
InMemoryPolicyStore

Exceptions are converted into HTTP responses using:

RestExceptionHandler
