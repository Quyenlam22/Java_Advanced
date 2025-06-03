export const setBook = (items) => {
    return ({
        type: "SET_BOOK",
        items: items,
    })
}

export const deleteBook = (id) => {
    return ({
        type: "DELETE_BOOK",
        id: id,
    })
}