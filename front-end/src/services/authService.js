import { post } from "../utils/request"

export const loginPost = async (options) => {
    const result = await post(options, "login");
    return result;
}