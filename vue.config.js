const path = require('path');
const CopyPlugin = require('copy-webpack-plugin');

module.exports = {
  css: {
    sourceMap: true,
  },
  publicPath: '/',
  outputDir: 'src/main/resources/public',
  assetsDir: 'assets',
  pages: {
    index: {
      entry: 'src/main/client/app/main.js',
      template: 'src/main/client/public/index.html',
      filename: 'index.html',
    },
  },
  configureWebpack: {
    performance: {
      maxAssetSize: 500000,
    },
    resolve: {
      alias: {
        '@': path.resolve('src/main/client/app'),
      },
    },
  },
  chainWebpack: (config) => {
    config
      .plugin('copy')
      .use(CopyPlugin, [[{
        from: 'src/main/client/public',
        to: '',
        toType: 'dir',
        ignore: [
          '.DS_Store',
          {
            glob: 'index.html',
            matchBase: false,
          },
        ],
      }]]);
  },
};
