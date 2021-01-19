<h2 align="center">Notes API</h2>

<p align="center">
    <a href="https://travis-ci.com/mbagrat/notes-api"><img src="https://img.shields.io/travis/mbagrat/notes-api/main.svg?logo=travis" alt="Travis CI"/></a>
    <a href="https://codecov.io/gh/mbagrat/notes-api/branch/main"><img src="https://codecov.io/gh/jonathanlermitage/manon/branch/spring5-light/graph/badge.svg" alt="Codecov"/></a>
</p>

### Before running project
Before running project be sure that `docker` and `MySQL 8` installed in you development machine. 

### Running project
- Local  
  In the terminal go to the project root directory run following command
  ```
  ./gradlew clean build
  ```
  > if you want to skip test at the end of gradle command add `-x test`  
  
  Then run app by triggering
  ```
  java -jar ./build/libs/notes-api-0.0.1-SNAPSHOT.jar
  ```

- Docker  
  In the terminal go to the project root directory execute following command
  
  Start
  ```
  docker-compose up --build -d
  ```
  Stop
  ```
  docker-compose down -v
  ```

### Testing

> In your local machine before start testing, you should add data to the database. 
> In order, to have that you can execute `SQL` script which is located in the resource folder.

#### Local
  - Login
  ```curl
  curl -s -o /dev/null -D - POST 'http://localhost:8080/login' \
  --header 'Content-Type: application/json' \
  --data-raw '{
      "username":"test.one@gmail.com",
      "password":"Pa$$word!1"
  }'
  ```
  > From Response header take Bearer token and use in each next request

  - GET Note
  ```curl
  curl --location --request GET 'http://localhost:8080/notes/1' \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYxMTc3NzcxNX0._0SsFPTfgCqHA0MpCpPKYPHPCDWi7yPrkkmElUOj8m4LQwU0BnPyRUqIltZTsOXiH23ERg1dW94TMRS6VUAOkg' \
  --data-raw ''
  ```

  - GET Notes
  ```curl
  curl --location --request GET 'http://localhost:8080/notes/' \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYxMTc3NzcxNX0._0SsFPTfgCqHA0MpCpPKYPHPCDWi7yPrkkmElUOj8m4LQwU0BnPyRUqIltZTsOXiH23ERg1dW94TMRS6VUAOkg' \
  --data-raw ''
  ```

  - Create Note
  ```curl
  curl --location --request POST 'http://localhost:8080/notes/' \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0Lm9uZUBnbWFpbC5jb20iLCJleHAiOjE2MTE4NzgzNDl9.msVeyPkap_dK7DXZYH_JxQzF7YM2_ktYlSYD-G2tG4Bw0P-qzd7BKv-psW73qcNxhGx9LmiyrNFuqj8WrDRhIg' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "title": "Update Note One Title",
  "note": "Update Note One Text",
  "createTime": "2015-10-22T23:34:40",
  "lastUpdateTime": "2020-03-18T01:18:32",
    "user": {
      "username": "test.one@gmail.com",
      "password": "Pa$$word@2",
      "createTime": "2021-01-17T10:08:27",
      "lastUpdateTime": "2021-01-17T10:08:32"
    }
  }'
  ```

  - Update Note
  ```curl
  curl --location --request PACH 'http://localhost:8080/notes/1' \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0Lm9uZUBnbWFpbC5jb20iLCJleHAiOjE2MTE4NzgzNDl9.msVeyPkap_dK7DXZYH_JxQzF7YM2_ktYlSYD-G2tG4Bw0P-qzd7BKv-psW73qcNxhGx9LmiyrNFuqj8WrDRhIg' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "id": 1,
  "title": "Update Note One Title",
  "note": "Update Note One Text",
  "createTime": "2015-10-22T23:34:40",
  "lastUpdateTime": "2020-03-18T01:18:32",
    "user": {
      "id": 1,
      "username": "test.one@gmail.com",
      "password": "Pa$$word@2",
      "createTime": "2021-01-17T10:08:27",
      "lastUpdateTime": "2021-01-17T10:08:32"
    }
  }'
  ```

  - Delete Note By ID
  ```curl
  curl --location --request DELETE 'http://localhost:8080/notes/1' \\
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0Lm9uZUBnbWFpbC5jb20iLCJleHAiOjE2MTE4NzgzNDl9.msVeyPkap_dK7DXZYH_JxQzF7YM2_ktYlSYD-G2tG4Bw0P-qzd7BKv-psW73qcNxhGx9LmiyrNFuqj8WrDRhIg' \
  --data-raw ''
  ```

  - Delete Note
  ```curl
  curl --location --request DELETE 'http://localhost:8080/notes/note' \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0Lm9uZUBnbWFpbC5jb20iLCJleHAiOjE2MTE4NzgzNDl9.msVeyPkap_dK7DXZYH_JxQzF7YM2_ktYlSYD-G2tG4Bw0P-qzd7BKv-psW73qcNxhGx9LmiyrNFuqj8WrDRhIg' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "id": 1,
    "title": "Note One Title",
    "note": "Note One Text",
    "createTime": "2015-10-22T23:34:40",
    "lastUpdateTime": "2020-03-18T01:18:32",
    "user": {
      "id": 1,
      "username": "test.one@gmail.com",
      "password": "Pa$$word!1",
      "createTime": "2021-01-17T10:08:27",
      "lastUpdateTime": "2021-01-17T10:08:32"
    }
  }'
  ```
  
#### Docker env
  - Login
  ```curl
  curl -s -o /dev/null -D - POST 'http://localhost:8084/login' \
  --header 'Content-Type: application/json' \
  --data-raw '{
      "username":"test.one@gmail.com",
      "password":"Pa$$word!1"
  }'
  ```
  > From Response header take Bearer token and use in each next request
  
  - GET Note
  ```curl
  curl --location --request GET 'http://localhost:8084/notes/1' \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYxMTc3NzcxNX0._0SsFPTfgCqHA0MpCpPKYPHPCDWi7yPrkkmElUOj8m4LQwU0BnPyRUqIltZTsOXiH23ERg1dW94TMRS6VUAOkg' \
  --data-raw ''
  ```

  - GET Notes
  ```curl
  curl --location --request GET 'http://localhost:8084/notes/' \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYxMTc3NzcxNX0._0SsFPTfgCqHA0MpCpPKYPHPCDWi7yPrkkmElUOj8m4LQwU0BnPyRUqIltZTsOXiH23ERg1dW94TMRS6VUAOkg' \
  --data-raw ''
  ```

  - Create Note
  ```curl
  curl --location --request POST 'http://localhost:8084/notes/' \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0Lm9uZUBnbWFpbC5jb20iLCJleHAiOjE2MTE4NzgzNDl9.msVeyPkap_dK7DXZYH_JxQzF7YM2_ktYlSYD-G2tG4Bw0P-qzd7BKv-psW73qcNxhGx9LmiyrNFuqj8WrDRhIg' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "title": "Update Note One Title",
  "note": "Update Note One Text",
  "createTime": "2015-10-22T23:34:40",
  "lastUpdateTime": "2020-03-18T01:18:32",
    "user": {
      "username": "test.one@gmail.com",
      "password": "Pa$$word@2",
      "createTime": "2021-01-17T10:08:27",
      "lastUpdateTime": "2021-01-17T10:08:32"
    }
  }'
  ```
  
  - Update Note
  ```curl
  curl --location --request PACH 'http://localhost:8084/notes/1' \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0Lm9uZUBnbWFpbC5jb20iLCJleHAiOjE2MTE4NzgzNDl9.msVeyPkap_dK7DXZYH_JxQzF7YM2_ktYlSYD-G2tG4Bw0P-qzd7BKv-psW73qcNxhGx9LmiyrNFuqj8WrDRhIg' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "id": 1,
  "title": "Update Note One Title",
  "note": "Update Note One Text",
  "createTime": "2015-10-22T23:34:40",
  "lastUpdateTime": "2020-03-18T01:18:32",
    "user": {
      "id": 1,
      "username": "test.one@gmail.com",
      "password": "Pa$$word@2",
      "createTime": "2021-01-17T10:08:27",
      "lastUpdateTime": "2021-01-17T10:08:32"
    }
  }'
  ```

  - Delete Note By ID
  ```curl
  curl --location --request DELETE 'http://localhost:8084/notes/1' \\
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0Lm9uZUBnbWFpbC5jb20iLCJleHAiOjE2MTE4NzgzNDl9.msVeyPkap_dK7DXZYH_JxQzF7YM2_ktYlSYD-G2tG4Bw0P-qzd7BKv-psW73qcNxhGx9LmiyrNFuqj8WrDRhIg' \
  --data-raw ''
  ```

  - Delete Note
  ```curl
  curl --location --request DELETE 'http://localhost:8084/notes/note' \
  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0Lm9uZUBnbWFpbC5jb20iLCJleHAiOjE2MTE4NzgzNDl9.msVeyPkap_dK7DXZYH_JxQzF7YM2_ktYlSYD-G2tG4Bw0P-qzd7BKv-psW73qcNxhGx9LmiyrNFuqj8WrDRhIg' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "id": 1,
    "title": "Note One Title",
    "note": "Note One Text",
    "createTime": "2015-10-22T23:34:40",
    "lastUpdateTime": "2020-03-18T01:18:32",
    "user": {
      "id": 1,
      "username": "test.one@gmail.com",
      "password": "Pa$$word!1",
      "createTime": "2021-01-17T10:08:27",
      "lastUpdateTime": "2021-01-17T10:08:32"
    }
  }'
  ```