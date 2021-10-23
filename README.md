# RMIT SEPT 2021 Major Project

# Group 3 (Moving Houses) Friday 3.30pm

## Members
* Jared Song - s3857657
* Aili Gong - s3858053
* Alexander Aloi - s3842524
* Shannon Dann - s3858053
* Carl Karama - s3713721
	
# Guide to building and running our application locally:

## Running back-end locally
### Prerequisites

Make sure you have installed maven. This can be done using the following command:
```mvn install```
If you don't have maven installed, visit https://maven.apache.org/ and follow the installation instructions online.

### Building the libaries
First we must enter the each microservice folder located at ~/BackEnd. If you are in the root directory then use this command:
``` cd BackEnd/[microservice] ```
Now that we are in the microservice directory we can use the following command to build it: 
``` mvnw package ```
A new file will be created in target folder and will be called [microservice]-1.0.0.jar
You can then run this java file with the following command:
``` java -jar target/[microservice]-1.0.0.jar ```

An example with the login microservice is shown:
1) ``` cd BackEnd/Login ```
2) ``` mvnw package ```
3) ``` java -jar target/login-1.0.0.jar ``` (do not terminate this process, it will run in the background)

To package and run the files, each service with it's commands are listed below: (Assuming that you are in the appropriate microservice directory)
Services:
### Login
mvnw package && java -jar target/login-1.0.0.jar
### Users
mvnw package && java -jar target/users-1.0.0.jar
### Books
mvnw package && java -jar target/books-1.0.0.jar
### Browsing
mvnw package && java -jar target/browsing-1.0.0.jar
### Transactions
mvnw package && java -jar target/transactions-1.0.0.jar
### Requests
mvnw package && java -jar target/requests-1.0.0.jar
### Reviews
mvnw package && java -jar target/reviews-1.0.0.jar

## Running front-end locally
### Prerequisites

You need to have installed the latest version of [Node.js](https://nodejs.org/en/). You can verify that you have installed Node.js by running the following command in your terminal:

``` node -v ```

Node.js will be installed if this command returns a version number.
The back-end services must also be running, as without the required services, certain functions will be missing.

### Starting the front-end locally
Navigate to the directory on your machine where you have cloned this repository. Then, change directories into the `FrontEnd` folder by running:

```cd FrontEnd/ ```

Once you are in the `FrontEnd` directory, you will need to run the following:

``` npm install ```

If, for some reason, the command failed when trying to install dependencies, there are a few things you could try:

- Delete `package-lock.json` file and `node_modules` folder if they exist.
- Ensure you have the latest version of Node.js.
- Try to run the install commmand again.

Once the command finishes, run the following to start running the front-end:

``` npm run dev ```





