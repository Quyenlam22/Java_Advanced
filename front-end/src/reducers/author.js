const authorReducer = (state = [], action) => {
    let newState = [...state];

    switch (action.type) {
        case "SET_AUTHOR":
            return action.items;
        case "DELETE_AUTHOR":
            return newState.filter(item => item.id !== action.id);
        // case "DELETE_ALL_AUTHOR":
        //     return [];
        default:
            return state;
    }
}

export default authorReducer;