# Diet App

The Diet App is a web application designed to assist users in managing their daily caloric intake. It offers an automatic calorie calculator that calculates the caloric demand based on user-specific information. The app's primary function is to help users monitor and manage their dietary requirements.

## Accessing the Diet App

You can access the Diet App on AWS by following this link: [Diet App](http://foodapp-env.eba-4n8fgprv.us-east-1.elasticbeanstalk.com/)

## Features

1. **Calories Calculator:** The Diet App features an intelligent calories calculator that takes into account user information such as age, gender, weight, and activity level to determine daily caloric needs.

2. **Diet Strategies:** Currently, the app offers a simple diet strategy that divides daily caloric intake into 40% carbohydrates, 30% proteins, and 30% fat. In the future, users can expect a variety of diet strategies to choose from, catering to different dietary preferences and goals.

3. **Edamam Integration:** The app communicates with the Edamam API, which provides comprehensive nutritional information for various food products. This integration ensures accurate calorie data for foods consumed by users.


## Authenticate Controller

### Description
Handles user authentication and provides a JWT token for authentication.

### Base Path
`/api/v1/login`

#### Endpoints:

1. **User Authentication**
    - **Method:** POST
    - **Path:** `/`
    - **Description:** Authenticate a user and generate a JWT token.
    - **Request Body:** AuthenticateRequest (data transfer object containing user/login details)
    - **Example:** `POST /api/v1/login`

    - **Response Body:** AuthenticationResponse (data transfer object containing authentication token)
## Diet Controller

### Description
Handles user diets

### Base Path
`/api/v1/diets`

#### Endpoints:

1. **Set Default Diet for User**
    - **Method:** GET
    - **Path:** `/{userId}/default`
    - **Description:** Set the default diet for a user.
    - **Path Variable:** userId (ID of the user)
    - **Example:** `GET /api/v1/diets/123/default`

    - **Response:** No response body (void)
    - 
## Ingredients Controller 

### Description
Handles ingredients and provides an endpoint to retrieve a list of ingredients.

### Base Path
`/api/v1/ingredients`

#### Endpoints:

1. **Get All Ingredients**
    - **Method:** GET
    - **Path:** `/`
    - **Description:** Retrieve a list of all ingredients.
    - **Example:** `GET /api/v1/ingredients`

    - **Response Body:** List of Ingredients (data transfer objects containing ingredient details)

## Meal Controller

### Description
Handles user meals and provides endpoints for adding, updating, and deleting meals from a user's meal diary.

### Base Path
`/api/v1/meals`

#### Endpoints:

1. **Add Meal to User's Meal Diary**
    - **Method:** POST
    - **Path:** `/{userId}`
    - **Description:** Add a meal to a user's meal diary.
    - **Path Variable:** userId (ID of the user)
    - **Request Body:** MealDto (data transfer object containing meal details)
    - **Example:** `POST /api/v1/meals/123`

    - **Response:** No response body (HTTP 200 OK)

2. **Update User Meal**
    - **Method:** PUT
    - **Path:** `/{userId}`
    - **Description:** Update a user's meal in their meal diary.
    - **Path Variable:** userId (ID of the user)
    - **Request Body:** MealDto (data transfer object containing updated meal details)
    - **Example:** `PUT /api/v1/meals/123`

    - **Response:** No response body (HTTP 200 OK)

3. **Delete Meal from User's Meal Diary**
    - **Method:** DELETE
    - **Path:** `/{userId}/{date}`
    - **Description:** Delete a meal from a user's meal diary for a specific date.
    - **Path Variables:** userId (ID of the user), date (date of the meal)
    - **Request Body:** MealDto (data transfer object containing meal details)
    - **Example:** `DELETE /api/v1/meals/123/2023-10-02`

    - **Response:** No response body (HTTP 200 OK)

## Meal Diary Controller

### Description
Handles user meal diaries and provides an endpoint for retrieving a user's meal diary for a specific date.

### Base Path
`/api/v1/diary`

#### Endpoints:

1. **Get User Meal Diary for a Date**
    - **Method:** GET
    - **Path:** `/{userId}/{date}`
    - **Description:** Retrieve a user's meal diary for a specific date.
    - **Path Variables:** userId (ID of the user), date (date for which to retrieve the meal diary)
    - **Example:** `GET /api/v1/diary/123/2023-10-02`

    - **Response Body:** MealDiaryDto (data transfer object containing meal diary details)

## Register Controller

### Description
Handles user registration and provides a JWT token upon successful registration.

### Base Path
`/api/v1/register`

#### Endpoints:

1. **Register a New User**
    - **Method:** POST
    - **Path:** `/`
    - **Description:** Register a new user.
    - **Request Body:** RegisterRequest (data transfer object containing user registration details)
    - **Example:** `POST /api/v1/register`

    - **Response:** No response body (HTTP 200 OK)
    - **Response Header:** Authorization header containing the JWT token for the newly registered user

## User Controller

### Description
Handles user data management, including retrieving user information, updating user details, and deleting user accounts.

### Base Path
`/api/v1/users`

#### Endpoints:

1. **Get User Information**
    - **Method:** GET
    - **Path:** `/{id}`
    - **Description:** Retrieve user information by user ID.
    - **Path Variable:** id (ID of the user)
    - **Example:** `GET /api/v1/users/123`

    - **Response Body:** UserDto (data transfer object containing user details)

2. **Delete User Account**
    - **Method:** DELETE
    - **Path:** `/{id}`
    - **Description:** Delete a user account by user ID.
    - **Path Variable:** id (ID of the user)
    - **Example:** `DELETE /api/v1/users/123`

    - **Response:** No response body (HTTP 200 OK)

3. **Update User Details**
    - **Method:** PUT
    - **Path:** `/{id}`
    - **Description:** Update user details by user ID.
    - **Path Variable:** id (ID of the user)
    - **Request Body:** RequestUpdateBody (data transfer object containing updated user details)
    - **Example:** `PUT /api/v1/users/123`

    - **Response Body:** UserDto (data transfer object containing updated user details)