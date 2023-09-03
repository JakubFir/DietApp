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

export const getUserInformation = userId  =>
    fetch(`api/v1/users/${userId}`, {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'GET',
    }).then(checkStatus);