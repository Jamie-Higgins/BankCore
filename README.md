# Running the Application

##Through Docker
In the root of the project with the Dockerfile build docker:

`build -t g2/bankingapp .`

Next run docker:

`docker run --rm -p 8080:8080 g2/bankingapp` Replace the left most 8080 with your desired port if 8080 is in use on your machine.
##
###Through IDE

Java8 and Gradle are used so Import each of the gradle projects

Build the Jar of api-common which will be created in api-common/build/libs and copy this to banking-api/build/libs

Add a run configuration using bootRun of the banking-api, this will run the project.

The project will now be running on http://localhost:8080/

##
###Using the Application

Swagger docs and endpoints can be found at the following link - you will need to authenticate yourself with a bearer token so I used postman for this.
http://localhost:8080/swagger-ui.html

To Register see the following endpoint which will return an account number:

http://localhost:8080/swagger-ui.html#/authentication-controller/createBankAccountUsingPOST


Once you've created an account and have your account number and pin you can sign in:

http://localhost:8080/swagger-ui.html#/authentication-controller/signInToAccountUsingPOST

After this you will have a bearer token which you can add to your postman authorisation to access the account endpoints of the app.

See the relevant swagger docs for how to access the various endpoints.


##
###External Services

There is a simple demo module included to simulate transactions. This can only be accessed through the IDE. Once you've created an account you can change the variable below to this created account number:

`private static final String ACCOUNT_NUMBER`

You can also specify the number of transactions to take place.

Note that this simply generates random CHECKS and DEBITS with random values to populate transactions - some of these will not go through as they will generated withdrawal DEBITS of values greater than the current balance.

##

Any questions feel free to email me at jamie.higgins@unosquare.com








