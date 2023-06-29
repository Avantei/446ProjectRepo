const config = require("./config");
const local = require("./config.local");

if (!process.env.NODE_ENV || process.env.NODE_ENV=="development")
  module.exports = local;
else
  module.exports = config;