const userReducer = (state = [], action) => {
    let newState = [...state];

    switch (action.type) {
        case "SET_USER":
            return action.items;
        case "DELETE_USER":
            return newState.filter(item => item.id !== action.id);
        // case "DELETE_ALL_USER":
        //     return [];
        default:
            return state;
    }
}

export default userReducer;