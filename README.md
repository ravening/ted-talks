# Ted Talks

A small application to upload ted talks from csv, parse it and store in database.

Web application written using Java and spring boot with H2 as in memory database.
Provides REST api endpoints to fetch the data stored in DB.


## Prerequisites to run the application

Docker - to run container \
Postman - to send DELETE request

Java 11 \
Maven


Dockerfile is provided, so it can run in container too

Docker image can be built using

```html
docker build -t ted-talks .
```


## How to run:

Build the application using

```html
mvn clean package
```

Start the application using

```html
 java -jar target/ted-talks.jar
```


## Endpoints

API documentation is available at

```html
http://localhost:8080/swagger-ui/index.html
```

Navigate to below endpoint to upload csv file

```html
http://localhost:8080/api/upload
```

Then select the csv file to upload. If the upload is successful, it will display success message else
it will display the error message.


To list all the talks


```html
http://localhost:8080/api/talks
```


To list the talks by its author

```html
http://localhost:8080/api/author/<name>
```

To list the talks by its likes

```html
http://localhost:8080/api/likes/<count>
```

To list the talks by its views

```html
http://localhost:8080/api/views/<count>
```

To list the talks by its title

```html
http://localhost:8080/api/title/<name>
```

