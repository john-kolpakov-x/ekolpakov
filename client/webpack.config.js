// Webpack v4
const path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const CleanWebpackPlugin = require("clean-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");
const ManifestPlugin = require('webpack-manifest-plugin');


module.exports = (env, argv) => {
  // console.log("****** env = ", env);
  // console.log("****** argv = ", argv);

  let prod = argv.mode === 'production';
  let dev = argv.mode === 'development';

  const config = {
    entry: {
      main: path.resolve(__dirname, 'source', 'main.ts'),
      style: path.resolve(__dirname, 'styles', 'main.scss'),
    },
    output: {
      path: path.resolve(__dirname, 'build', 'dist'),
      filename: 'js/' + (prod ? '[name].[chunkhash].js' : '[name].js'),
      publicPath: '',
    },
    devtool: prod ? 'source-map' : 'inline-source-map',
    module: {
      rules: [{
        test: /\.js$/,
        exclude: /node_modules/,
        use: ["babel-loader"],
      }, {
        test: /\.ts$/,
        use: ['ts-loader'],
      }, {
        test: /\.(sass|scss)$/,
        use: [{
          loader: 'style-loader',
        }, {
          loader: MiniCssExtractPlugin.loader,
        }, {
          loader: 'css-loader',
        }, {
          loader: 'postcss-loader',
          options: {
            sourceMap: true,
          },
        }, {
          loader: 'sass-loader',
          options: {
            sourceMap: true,
          },
        }],
      }],
    },
    plugins: [
      new CleanWebpackPlugin([
        'build/dist', 'build/manifest.json',
      ], {}),
      new MiniCssExtractPlugin({
        filename: 'css/' + (prod ? '[name].[contenthash].css' : '[name].css'),
        chunkFilename: prod ? '[id].[contenthash].css' : '[id].css',
      }),
      new CopyWebpackPlugin([{
        from: './img',
        to: './img',
      }, {
        from: './fonts',
        to: './fonts',
      }]),
    ],
  };

  if (prod) {
    config.plugins.push(new ManifestPlugin({
      fileName: '../manifest.json',
    }));
  }

  return config;
};
