const { v4: uuidv4 } = require("uuid");
const mongo = require("./db/mongo");
const config = require("../config");
const collectionName = "groups"

class Group {
  constructor(groupId, groupName, ownerId) {
    this.groupId = groupId; // string
    this.name = groupName; // string
    this.ownerId = ownerId; // creator's id
    this.members = []; // string[] of userId
    this.events = []; // string[] of eventId
    this.invite = ""; // invite code for the group
  }
}

class GroupDto {
  groupId; name; ownerId; members; events; invite;
  constructor(data) {
    for (const [key, value] of Object.entries(this)) {
      this[key] = data[key];
    }
  }
}

class GroupDal {
  async _getCollection() {
    const client = await mongo.getClient();
    return client.db(config.mongoConfig.dbName).collection(collectionName);
  }

  async createGroup(groupName, ownerId) {
    const groupId = uuidv4();
    const group = new Group(groupId, groupName, ownerId);
    group.members.push(ownerId);
    try {
      const db = await this._getCollection();
      const result = await db.insertOne(group);
      if (result.acknowledged)
        return { data: new GroupDto(await db.findOne({ _id: result.insertedId })) };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
  }

  async getGroup(groupId) {
    try {
      const db = await this._getCollection();
      const result = await db.findOne({ groupId });
      if (result) return { data: new GroupDto(result) };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
  }

  async findGroupWithMember(groupId, userId) {
    try {
      const db = await this._getCollection();
      const result = await db.findOne({
        groupId,
        members: { $elemMatch: { $eq: userId } }
      });
      if (result) return { data: new GroupDto(result) };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
  }

  async isMemberInGroup(groupId, userId) {
    try {
      const db = await this._getCollection();
      const result = await db.countDocuments({
        groupId,
        members: { $elemMatch: { $eq: userId } }
      });
      return { data: count === 1 };
    } catch (ex) {
      return { err: ex }
    }
  }
};

module.exports = GroupDal;