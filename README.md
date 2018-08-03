# challange

[![Build Status](http://circleci-badges-max.herokuapp.com/img/rslvn/challange/master?token=a184b3b3ba22854b647a8ba2af392724ef2eace4)](https://circleci.com/gh/rslvn/challange/tree/master)

## Goal

The main use case for our API is to calculate realtime statistic from the last 60 seconds. There will be two APIs, one of them is called every time a transaction is made. It is also the sole input of this rest API. The other one returns the statistic based of the transactions of the last 60 seconds.

## Requirements

For the rest api, the biggest and maybe hardest requirement is to make the GET /statistics execute in constant time and space. The best solution would be O(1). It is very recommended to tackle the O(1) requirement as the last thing to do as it is not the only thing which will be rated in the code challenge.

- The API have to be threadsafe with concurrent requests
- The API have to function properly, with proper result
- The project should be buildable, and tests should also complete successfully. e.g. If
maven is used, then mvn clean install should complete successfully.
- The API should be able to deal with time discrepancy, which means, at any point of time,
we could receive a transaction which have a timestamp of the past
- Make sure to send an in memory solution without database, (including no in memory
database).
- Endpoints have to execute in constant time and memory (O(1))

## Build and Execute

```
mvn clean install
mvn spring-boot:run 
```
or
```
mvn clean install spring-boot:run
```
## Test

Test source includes unit tests and integration tests for endpoints.

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
This is the main endpoint of this task, this endpoint have to execute in constant time and memory (O(1)). It returns the statistic based on the transactions which happened in the last 60 seconds.

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