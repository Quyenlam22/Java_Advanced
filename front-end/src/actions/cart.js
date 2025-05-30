export const addToCart = (info) => {
    return ({
        type: "ADD_TO_CART",
        id: info.id,
        info: info,
    })
}

export const updateQuantity = (id, quantity = 1) => {
    return ({
        type: "UPDATE_QUANTITY",
        id: id,
        quantity: quantity
    })
}