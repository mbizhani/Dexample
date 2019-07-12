import React from "react";
import Header from "./Header";
import RecipeList from "./RecipeList";
import RecipeDetail from "./RecipeDetail";

const App = () => (
	<div>
		<Header/>
		<main style={{display: "flex"}}>
			<RecipeList style={{flex: 3}}/>
			<RecipeDetail style={{flex: 5}}/>
		</main>
	</div>
);

export default App;