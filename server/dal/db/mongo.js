const { MongoClient } = require("mongodb");
const { mongoConfig } = require("../../config/index");
// Replace the uri string with your MongoDB deployment's connection string.
const uri = `mongodb+srv://${mongoConfig.username}:${mongoConfig.password}@446-cluster.s5jj9js.mongodb.net/?retryWrites=true&w=majority`;

const mongoOptions = {
  useNewUrlParser: true,
  useUnifiedTopology: true
};

class MongoInstance {
  static _client = new MongoClient(uri, mongoOptions);

  static isInitialized() {
    return this._client !== undefined;
  }
  static isConnect() {
    return !!this._client && !!this._client.topology && this._client.topology.isConnected()
  }
  static async getClient() {
    if (this.isConnect()) return this._client;
    this._client = new MongoClient(uri, mongoOptions);
    await this._client.connect();
    console.info(`Mongo Connection Established`);
    return this._client;
  }
}

module.exports = MongoInstance;