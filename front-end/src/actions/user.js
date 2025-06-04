export const setUser = (items) => {
    return ({
        type: "SET_USER",
        items: items,
    })
}

export const editUser = (options) => {
    return ({
        type: "EDIT_USER",
        options: options,
    })
}

export const deleteUser = (id) => {
    return ({
        type: "DELETE_USER",
        id: id,
    })
}

