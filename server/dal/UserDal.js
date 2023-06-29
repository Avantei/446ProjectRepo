const { v4: uuidv4 } = require("uuid");
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

  async createUser(username, password) {
    const userId = uuidv4();
    const newUser = {
      username,
      password,
      userId
    };
    try {
      const db = await this._getCollection();
      const result = await db.insertOne(newUser);
      if (result.acknowledged)
        return { data: new UserDto(await db.findOne({ _id: result.insertedId })) };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
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