const path = require("path");
const webpack = require("webpack");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
  "mode": "development",
  "context": path.join(__dirname, "src"),
  "entry": "./index.js",
  "output": {
    "path": path.resolve(__dirname, "dist"),
    "filename": "bundle.js"
  },
  "module": {
    "rules": [
      {
        "test": /\.js$/,
        "loader": "babel-loader"
      }
    ]
  },
  "plugins": [
    new HtmlWebpackPlugin({
      "template": "index.html",
      "inject": "body"
    }) 
  ]
};
