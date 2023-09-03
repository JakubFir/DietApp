import fetch from "unfetch";

const checkStatus = response => {
    if (response.ok) {
        return response;
    }
    // convert non-2xx HTTP responses into errors:
    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
}

export const getUserMealDiary = (userId,mealDate) =>
    fetch(`/api/v1/dairy/${userId}/${mealDate}`, {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'GET'
    }).then(checkStatus);


export const addMealToUserMealDiary = (userId, meal) =>
    fetch(`api/v1/meals/${userId}`, {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'GET',
        body: JSON.stringify(meal)
    }).then(checkStatus);
