const webpack = require("webpack");

module.exports = {
  "mode": "development",
  "entry": "./index.js",
  "output": {
    "path": __dirname,
    "filename": "bundle.js"
  },
  "module": {
    "rules": [
      {
        "test": /\.js$/,
        "loader": "babel-loader"
      }
    ]
  }
};
