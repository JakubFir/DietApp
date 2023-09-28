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

export const deleteUserMeal = (userId, mealDate, mealDto) =>
    fetch(`/api/v1/meals/${userId}/${mealDate}`, {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'DELETE',
        body: JSON.stringify(mealDto)
    }).then(checkStatus);