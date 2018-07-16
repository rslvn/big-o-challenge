# challange

## Build and Execute
The command triggers compiling code, executing unit tests, creating docker image and run a docker container.
```
mvn clean install
mvn spring-boot:run 
```
or
```
mvn clean install spring-boot:run
```
## Test
Coverage: `%98`
Line Coverage: `163/165`

## APIs

### /transactions
```
curl -H "Content-Type: application/json" -X POST http://localhost:8080/transactions -d '
{
"amount": 3.4,
"timestamp": 1531675920000
}'
```

### /statistics
```
curl -H "Content-Type: application/json" -X GET http://localhost:8080/statistics
```

### folder structure
```
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── n26
│   │   │           └── challange
│   │   │               ├── ChallangeAppConstant.java
│   │   │               ├── ChallangeApplication.java
│   │   │               ├── controller
│   │   │               │   └── ChallangeController.java
│   │   │               ├── model
│   │   │               │   ├── Statistics.java
│   │   │               │   └── Transaction.java
│   │   │               ├── service
│   │   │               │   └── TransactionService.java
│   │   │               └── util
│   │   │                   └── StatisticsUtil.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── n26
│                   └── challange
│                       ├── ChallangeAppConstantTest.java
│                       ├── ChallangeApplicationTests.java
│                       ├── controller
│                       │   ├── ChallageControllerIT.java
│                       │   └── ChallageControllerTest.java
│                       ├── service
│                       │   └── TransactionServiceTest.java
│                       └── util
│                           └── StatisticsUtilTest.java

```