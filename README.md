Here's a basic implementation of the RESTful API for the online library system using Flask. It handles:

Subscription Plan Limits: Users can borrow items based on their plan.
Age Restriction: Books in the Crime genre are restricted to users aged 18 or above.
Transaction Limit: Users are restricted to 10 transactions per month.

The script now includes two APIs:

Order API (/order):
Allows users to borrow books or magazines.
Enforces borrowing limits, checks availability, and validates user eligibility.

Return API (/return):
Allows users to return books or magazines.
Updates item availability and the user's borrowed items list.

When this Spring Boot application is run, it starts a RESTful API for managing users, books, and magazines for an online library system. 
The specific output depends on the requests sent to the API endpoints.


When a Transaction Happens:
Order API (/api/order):

Example: Alice borrows the book "History 101".
The Borrowed_Books join table is updated to include User_ID = 1 and Book_ID = 3.
The Available column for the book "History 101" in the Book table is set to false.
Alice's Transactions count in the User table is incremented by 1.
Return API (/api/return):

Example: Alice returns the magazine "Tech Monthly".
The Borrowed_Magazines join table entry for User_ID = 1 and Magazine_ID = 1 is removed.
The Available column for the magazine "Tech Monthly" in the Magazine table is set to true.

Steps in Postman:

Open Postman.
Set the method to POST.
Enter the URL: http://localhost:8080/api/order.
Go to the Body tab and select raw, then set it to JSON.
Paste the JSON body above.
Click Send.


Example Outputs:
Order API (/api/order):
Request:
json
{
  "userId": 1,
  "itemType": "book",
  "title": "Murder Mystery"
}
Response (Success):
json
{
  "message": "Item borrowed successfully."
}

Response (Failure - Crime genre age restriction):
json
{
  "error": "Crime genre books are restricted to users 18+."
}
Response (Failure - Plan limit):
json
{
  "error": "Book borrowing limit reached for your plan."
}

Open Postman.
Set the method to POST.
Enter the URL: http://localhost:8080/api/return.
Go to the Body tab and select raw, then set it to JSON.
Paste the JSON body above.
Click Send.
Return API (/api/return):

Request:
json
{
  "userId": 1,
  "titles": ["Murder Mystery", "Tech Monthly"]
}
Response (Success):
json
{
  "message": "Items returned successfully."
}
