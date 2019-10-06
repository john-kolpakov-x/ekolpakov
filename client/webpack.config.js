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
    }, {
      test: /\.js$/,
      use: {
        loader: 'babel-loader',
        options: {
          presets: ['@babel/preset-env'],
          plugins: [
            "transform-custom-element-classes",
            "@babel/plugin-proposal-class-properties",
          ]
        },
      },
      exclude: /node_modules/
    }]
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
  }
};
