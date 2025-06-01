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
            if(itemUpdate) {
                itemUpdate.quantity = itemUpdate.quantity + action.quantity;
            }
            return newState;
        case "DELETE_ITEM":
            return newState.filter(item => item.id !== action.id);
        case "DELETE_ALL_ITEM":
            return [];
        default:
            return state;
    }
}

export default cartReducer;