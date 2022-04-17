const BundleAnalyzerPlugin = require('webpack-bundle-analyzer')
    .BundleAnalyzerPlugin;

module.exports = {
  devServer: {
    port: 8082
  },
  configureWebpack: {
    plugins: [new BundleAnalyzerPlugin()]
  }
}