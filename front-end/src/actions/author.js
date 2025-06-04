export const setAuthor = (items) => {
    return ({
        type: "SET_AUTHOR",
        items: items,
    })
}

export const deleteAuthor = (id) => {
    return ({
        type: "DELETE_AUTHOR",
        id: id,
    })
}