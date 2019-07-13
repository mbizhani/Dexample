import React from "react";
import Header from "./Header";
import RecipeList from "./RecipeList";
import RecipeDetail from "./RecipeDetail";

class App extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			recipes: [],
			currentRecipe: null,
			aProp: ""
		};

		// (A) this.onRecipeClick = this.onRecipeClick.bind(this);
	}

	// ---------------

	componentDidMount() {
		fetch(`${API_URL}/v1/recipes`)
			.then(resp => resp.json())
			.then(recipes => {
				console.log("Recipes Data: ", recipes);

				/*this.setState({
					recipes: recipes
				});*/

				// or

				this.setState({recipes});
			});
	}

	render() {
		const {recipes, currentRecipe} = this.state;
		return (
			<div>
				<Header/>
				<main style={{display: "flex"}}>
					<RecipeList
						recipes={recipes}
						style={{flex: 3}}
						onClick={this.onRecipeClick}
					/>
					<RecipeDetail
						recipe={currentRecipe}
						style={{flex: 5}}
					/>
				</main>
			</div>
		);
	}

	// ---------------

	// (A) onRecipeClick(id) { // bind this to be accessible in components! - OR the following solution
	/*
	1. npm i --save-dev babel-preset-stage-0
	2. add "stage-0" to .babelrc
	 */
	onRecipeClick = (id) => {
		console.log("Recipe Click: ", id);

		fetch(`${API_URL}/v1/recipes/${id}`)
			.then(resp => resp.json())
			.then(recipe => {
				console.log("Clicked Data: ", recipe);

				this.setState({currentRecipe: recipe});
			});
	};
}

export default App;

/*
const App = () => (
	<div>
		<Header/>
		<main style={{display: "flex"}}>
			<RecipeList style={{flex: 3}}/>
			<RecipeDetail style={{flex: 5}}/>
		</main>
	</div>
);
*/