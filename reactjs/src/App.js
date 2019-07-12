import React from "react";
import Logo from "./static/images/logo.png";

/*
class App extends React.Component {
    render() {
        return(
            <h1>Hello World!</h1>
        );
    }
}*/

const App = () => (
    <div>
        <h1>Hello World!</h1>
        <img src={Logo}/>
    </div>
);

/*
const App = () => {
    return (<h1>Hello World!</h1>);
}
*/

export default App;