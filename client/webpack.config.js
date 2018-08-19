// Webpack v4
const path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
  entry: {
    main: path.resolve(__dirname, 'ts', 'main.ts'),
  },
  output: {
    path: path.resolve(__dirname, 'build', 'dist'),
    filename: '[name].js'
  },
  module: {
    rules: [{
      test: /\.js$/,
      exclude: /node_modules/,
      use: {
        loader: "babel-loader",
      },
    }, {
      test: /\.ts$/,
      use: {
        loader: 'ts-loader',
      },
    }],
  },
};
