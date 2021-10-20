# RMIT SEPT 2021 Major Project

# Group 3 (Moving Houses) Friday 3.30pm

## Members
* Jared Song - s3857657
* Aili Gong - s3858053
* Alexander Aloi - s3842524
* Shannon Dann - s3858053
* Carl Karama - s3713721

## Records

* Github repository :https://github.com/AlexAloi/SEPT
* Jira Board : https://s3842524.atlassian.net/jira/software/projects/SEPT/boards/1/backlog
* CircleCI: https://app.circleci.com/pipelines/github/AlexAloi/SEPT?invite=true 
* DockerHub ID: septmovinghouses 
* AWS: Not yet deployed 
	
## Code documentation - Release 2.0.1 18/09/2021

To run in command prompt:
1) cd into BackEnd\loginmicroservices
2) run: mvnw package && java -jar target/login-1.0.0.jar (do not terminate this process, it will run in the background)
3) check Aili's instructions for running the front end in the README file in the frontend folder

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

To run front end:
This is a [Next.js](https://nextjs.org/) project bootstrapped with [`create-next-app`](https://github.com/vercel/next.js/tree/canary/packages/create-next-app).

## Getting Started

First, run the development server:

```bash
npm run dev
# or
yarn dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

You can start editing the page by modifying `pages/index.js`. The page auto-updates as you edit the file.

[API routes](https://nextjs.org/docs/api-routes/introduction) can be accessed on [http://localhost:3000/api/hello](http://localhost:3000/api/hello). This endpoint can be edited in `pages/api/hello.js`.

The `pages/api` directory is mapped to `/api/*`. Files in this directory are treated as [API routes](https://nextjs.org/docs/api-routes/introduction) instead of React pages.

## Learn More

To learn more about Next.js, take a look at the following resources:

- [Next.js Documentation](https://nextjs.org/docs) - learn about Next.js features and API.
- [Learn Next.js](https://nextjs.org/learn) - an interactive Next.js tutorial.

You can check out [the Next.js GitHub repository](https://github.com/vercel/next.js/) - your feedback and contributions are welcome!

## Deploy on Vercel

The easiest way to deploy your Next.js app is to use the [Vercel Platform](https://vercel.com/new?utm_medium=default-template&filter=next.js&utm_source=create-next-app&utm_campaign=create-next-app-readme) from the creators of Next.js.

Check out our [Next.js deployment documentation](https://nextjs.org/docs/deployment) for more details.





To run the application locally : (Homy's Original)
1) cd into each and every microservice (ms_booking, ms_availability, ms_profiles, ms_service) and run :
2) ./mvnw package && java -jar target/ms_[microservice]-0.0.1-SNAPSHOT.jar
3) cd into FrontEnd/myfirstapp
4) run "npm install"
5) run "npm start"





