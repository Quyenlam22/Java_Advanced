import { get } from "../utils/request";

export const getBook = async () => {
    const result = await get("books");
    return result;
}