import { del, get } from "../utils/request"

export const getAuthors = async () => {
    const result = await get("authors");
    return result;
}

export const getDetailAuthor = async (id) => {
    const result = await get(`authors?id=${id}`);
    return result;
}

export const delAuthor = async (id) => {
    const result = await del(`authors/${id}`);
    return result;
}