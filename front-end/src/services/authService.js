import { get, post } from "../utils/requestLocal"

export const checkLogin = async (username, password) => {
    const result = await get(`users?username=${username}&password=${password}`);
    return result;
}

export const loginPost = async (options) => {
    const result = await post(options, "login");
    return result;
}