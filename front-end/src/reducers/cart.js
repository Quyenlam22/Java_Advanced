const cartReducer = (state = [], action) => {
    let newState = [...state];
    switch (action.type) {
        case "ADD_TO_CART":
            return [
                ...state,
                {
                    id: action.info.id,
                    info: action.info,
                    quantity: 1
                }
            ];
        case "UPDATE_QUANTITY":
            const itemUpdate = newState.find(item => item.id === action.id);
            itemUpdate.quantity = itemUpdate.quantity + action.quantity;
            return newState;
        default:
            return state;
    }
}

export default cartReducer;