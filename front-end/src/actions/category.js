export const setCategory = (items) => {
    return ({
        type: "SET_CATEGORY",
        items: items,
    })
}

export const deleteCategory = (id) => {
    return ({
        type: "DELETE_CATEGORY",
        id: id,
    })
}