const bookReducer = (state = [], action) => {
    let newState = [...state];

    switch (action.type) {
        case "SET_BOOK":
            return action.items;
        case "DELETE_BOOK":         
            return newState.filter(item => item.key !== action.id);
        // case "DELETE_ALL_ITEM":
        //     return [];
        default:
            return state;
    }
}

export default bookReducer;