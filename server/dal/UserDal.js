const mongo = require("./db/mongo");
const config = require("../config");
const collectionName = "users"

class UserDto {
  userId = ""
  username = ""
  constructor(user) {
    this.userId = user.userId;
    this.username = user.username;
  }
}

class UserDal {
  async _getCollection() {
    const client = await mongo.getClient();
    return client.db(config.mongoConfig.dbName).collection(collectionName);
  }
  async validateUser(username, password) {
    try {
      const db = await this._getCollection();
      const result = await db.findOne({ username: username, password: password });
      if (result) return { data: new UserDto(result) };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
  }

  async getUser(userId) {
    try {
      const db = await this._getCollection();
      const result = await db.findOne({ userId });
      if (result) return { data: new UserDto(result) };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
  }

  async createUser() {

  }

  async removeUser(userId) {

  }

  async hasUser(userId) {
    const db = await this._getCollection();
    const count = await db.countDocuments({ userId })
    return count === 1;
  }
};

module.exports = UserDal;