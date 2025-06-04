import { del, get, patch } from "../utils/request"

export const getCategories = async () => {
    const result = await get("categories");
    return result;
}

export const getDetailCategory = async (id) => {
    const result = await get(`categories?id=${id}`);
    return result;
}

export const updateCategory = async (options, id) => {
    const result = await patch(options, `categories/${id}`);
    return result;
}

export const delCategory = async (id) => {
    const result = await del(`categories/${id}`);
    return result;
}
