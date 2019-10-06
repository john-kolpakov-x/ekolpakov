const webpack = require('webpack');

module.exports = {
  context: __dirname,
  devtool: "source-map",
  entry: "./src/main.ts",
  output: {
    path: __dirname + "/build",
    filename: "bundle.js"
  },
  module: {
    rules: [{
      test: /\.tsx?$/,
      use: 'ts-loader',
      exclude: /node_modules/,
    }]
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
  }
};
