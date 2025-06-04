const categoryReducer = (state = [], action) => {
    let newState = [...state];

    switch (action.type) {
        case "SET_CATEGORY":
            return action.items;
        case "DELETE_CATEGORY":
            return newState.filter(item => item.id !== action.id);
        // case "DELETE_ALL_CATEGORY":
        //     return [];
        default:
            return state;
    }
}

export default categoryReducer;