import { get } from "../utils/request"

export const getCategories = async () => {
    const result = await get("categories");
    return result;
}

export const getDetailCategory = async (id) => {
    const result = await get(`categories?id=${id}`);
    return result;
}