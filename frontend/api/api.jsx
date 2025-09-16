import axios from 'axios';

const axiosParam = {
    baseURL: REACT_APP_SLBANK_API_URL || 'http://localhost:8080/api/v1',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    }
}

const axiosInstance = axios.create(axiosParam);

const api = (instance) => {
    return {
        get: (url, headers = {}) => instance.get(url, { headers }),
        post: (url, body, headers = {}) => instance.post(url, body, {headers}),
        put: (url, body, headers = {}) => instance.put(url, body, {headers}),
        delete: (url, headers = {}) => instance.delete(url, {headers}),
        patch: (url, body, headers = {}) => instance.patch(url, body, {headers}),
    }
}

export default api(axiosInstance);