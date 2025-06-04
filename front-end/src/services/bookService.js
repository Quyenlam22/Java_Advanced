import { del, get } from "../utils/request";

export const getBook = async () => {
    const result = await get("books");
    return result;
}

export const getBookById = async (id) => {
    const result = await get(`books?id=${id}`);
    return result;
}

export const getBooksByCategory = async (id) => {
    const result = await get(`books?category_id=${id}`);
    return result;
}

export const delBook = async (id) => {
    const result = await del(`books/${id}`);
    return result;
}