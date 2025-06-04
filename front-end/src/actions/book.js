export const setBook = (items) => {
    return ({
        type: "SET_BOOK",
        items: items,
    })
}

export const editBook = (options) => {
    return ({
        type: "EDIT_BOOK",
        options: options,
    })
}

export const deleteBook = (id) => {
    return ({
        type: "DELETE_BOOK",
        id: id,
    })
}