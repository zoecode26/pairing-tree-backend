## Pairing App - Backend

The backend of our Pair Programming app.

Check out the frontend: <a href="https://github.com/Duncan-7/pairing-app-frontend">Pairing App Frontend</a>

### Backend Stack

- `maven` to build the project
- `java` to develop back-end
- `spring boot`
- `intellij` text editor for `java`
- `postgresql` for database

### Card wall

<a href="https://trello.com/b/BRtHyVfB/super-team-education"> Team's Trello Board</a> 

### Database Schema

Here's the database schema:

<img src="images/db_schema.png" width="700" height="500">

### Features

- Signup/Login/Logout
- Password encryption
- Pair matching algorithm
- Messaging functionality once a pair is matched
- Match display

### Pairing Algorithm

Starts with list of all users signed up and filters them down through the following criteria to find the most optimal matches:
1. Users that are active
2. Users that have the fewest selected language preferences
3. Users that have at least one language in common
4. Users that haven't been paired together before
5. Users that have the most similar ability rating in the selected language

Users will therefore get to pair with as many people as possible whilst always having a number of factors in common.

### Try it!

Want to see & try our app? Here's how:
- Clone this repo using `git clone`
- Clone the frontend using `git clone`
- Install Maven `brew install maven`
- Install `npm install`
- From the command line create a dev database `createdb pairing_app`<br>
  (if you're not using `postgresql` make sure you amend `application.properties` file to suit your database)
- Build the app and start the server, using the Maven command `mvn spring-boot:run`
- Build the frontend by running the command `npm start` in a different terminal window
- Visit `localhost:3000`
