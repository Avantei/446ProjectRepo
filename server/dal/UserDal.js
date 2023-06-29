const { v4: uuidv4 } = require("uuid");
const mongo = require("./db/mongo");
const encrypt = require("./encrypt");

const config = require("../config");
const collectionName = "users"

class User {
  constructor(userId, email, password, username) {
    this.userId = userId;
    this.email = email;
    this.username = username;
    this.password = password;
    this.isVegan = false;
    this.isVegetarian = false;
    this.isHalal = false;
    this.isGlutenFree = false;
    this.isDairyFree = false;
    this.isKosher = false;
    this.preferences = [];
  }
}

class UserDto {
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
  async validateUser(email, password) {
    try {
      const db = await this._getCollection();
      const result = await db.findOne({ email: email });
      if (result && (await encrypt.compare(password, result.password)))
        return { data: new UserDto(result) };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
  }

  async getUserById(userId) {
    try {
      const db = await this._getCollection();
      const result = await db.findOne({ userId });
      if (result) return { data: new UserDto(result) };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
  }

  async createUser(email, password, username) {
    const userId = uuidv4();
    const hashedPw = await encrypt.hash(password);
    const newUser = new User(userId, email, hashedPw, username);
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

  async checkById(userId) {
    const db = await this._getCollection();
    const count = await db.countDocuments({ userId })
    return count === 1;
  }
  async checkByEmail(email) {
    const db = await this._getCollection();
    const count = await db.countDocuments({ email })
    return count === 1;
  }
};

module.exports = UserDal;