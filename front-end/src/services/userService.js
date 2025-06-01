import { get } from "../utils/request";

export const getUser = async (token) => {
    const result = await get(`users?token=${token}`);
    return result;
}
