#MyLearning Library Project

This Application handle library daily activities. Please see below functionality supported :

where User can get membership in library
We can add book with number of copies in the stock

Here Member can check if any specific book available now to read!

Member can borrow the book.
    case 1 : he don't have this book assigned in his borrowings then he can take this book
    case 2 : If he has already borrowed this book then Library will deny this book for borrowing

Member will return the book

Delete the membership

# How to run this application

with bash/cmd run <location> java -jar hexad-0.0.1-SNAPSHOT.jar

it will run on port number 8888 with context path /hexad

# how to test manual with Postman

import postman project
first run add book API It will store the data
second run add member API
then test other apis.

# code coverage

you will find on location : %project dir%\target\site\jacoco\index.html