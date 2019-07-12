import React from "react";

const RecipeDetail = (props) => (
	<div style={props.style}>
		<h2>R1</h2>
		<div>
			<span>Dessert</span>
			<span>1200 cal</span>
		</div>
		<h3>Ingredients</h3>
		<ul>
			<li>I1</li>
			<li>I2</li>
			<li>I3</li>
		</ul>
		<h3>Steps</h3>
		<ol>
			<li>S1</li>
			<li>S2</li>
			<li>S3</li>
		</ol>
	</div>
);

export default RecipeDetail;