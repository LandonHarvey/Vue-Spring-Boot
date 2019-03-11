const VuetifyLoaderPlugin = require('vuetify-loader/lib/plugin')
module.exports = {
  configureWebpack: {
    plugins: [
      new VuetifyLoaderPlugin()
    ]
  }
};

module.exports = {
  outputDir: 'target/dist',
  assetsDir: 'static'
};
