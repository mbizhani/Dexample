import React from "react";

const RecipeList = (props) => (
	<div style={props.style}>
		<h2>Recipe List</h2>
		<ul>
			<li>
				<span>R1</span>
			</li>
			<li>
				<span>R2</span>
			</li>
			<li>
				<span>R3</span>
			</li>
			<li>
				<span>R4</span>
			</li>
		</ul>
	</div>
);

export default RecipeList;