const { v4: uuidv4 } = require("uuid");
const mongo = require("./db/mongo");
const config = require("../config");
const collectionName = "events"


class Location {
  external = ""; // reserved for third party reference
  constructor(name, id, vote, suggester) {
    this.id = id; // This is local id for location in the event
    this.name = name;
    this.vote = vote;
    this.suggesterId = suggester; // string
  }
}

class Event {
  constructor(eventId, name, groupId, leaderId) {
    this.eventId = eventId; // string; unique id
    this.groupId = groupId; // string; id of the group hosting it
    this.name = name; // string; name of event
    this.leaderId = leaderId; // string; userId of leaser
    this.locations = []; // Location[]
    this._idTracker = 0;
  }
}

class EventDto {
  eventId; groupId; name; leaderId; locations;
  constructor(data) {
    for (const [key, value] of Object.entries(this)) {
      this[key] = data[key];
    }
  }
}


class EventDal {
  async _getCollection() {
    const client = await mongo.getClient();
    return client.db(config.mongoConfig.dbName).collection(collectionName);
  }
  async createEvent(name, groupId, userId) {
    const eventId = uuidv4();
    const event = new Event(eventId, name, groupId, userId);
    try {
      const db = await this._getCollection();
      const result = await db.insertOne(event);
      if (result.acknowledged)
        return { data: new EventDto(await db.findOne({ _id: result.insertedId })) };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
  }

  async getById(eventId) {
    try {
      const db = await this._getCollection();
      const result = await db.findOne({ eventId });
      if (result) return { data: new EventDto(result) };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
  }

  async addLocation(eventId, location, suggesterId) {
    try {
      const db = await this._getCollection();
      const locationToAdd = new Location(location, "some-id", 0, suggesterId);
      const updateRes = await db.updateOne({ eventId },
        { $addToSet: { locations: locationToAdd } }
      );
      if (updateRes.modifiedCount === 0) return { data: false };
      return { data: true };
    } catch (ex) {
      return { err: ex }
    }
  }

  // Join with groups and check if userId is in the group 
  async getEventWithMember(eventId, userId) {
    try {
      const db = await this._getCollection();
      const cursor = db.aggregate([
        { $match: { eventId } },
        {
          $lookup: {
            from: "groups",
            localField: "groupId",
            foreignField: "groupId",
            as: "groups"
          }
        },
        { $match: { "groups.0.members": { $elemMatch: { $eq: userId } } } },
        { $unset: "groups" }
      ]);
      const result = await cursor.next();
      if (result) return { data: new EventDto(result) };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
  }

  async isMemberInEvent(eventId, userId) {
    try {
      const db = await this._getCollection();
      const cursor = db.aggregate([
        { $match: { eventId } },
        {
          $lookup: {
            from: "groups",
            localField: "groupId",
            foreignField: "groupId",
            as: "groups"
          }
        },
        { $match: { "groups.0.members": { $elemMatch: { $eq: userId } } } },
        { $count: "count" }
      ]);
      const result = await cursor.next();
      if (result) return { data: result.count === 1 };
      return { data: null };
    } catch (ex) {
      return { err: ex }
    }
  }
};


module.exports = EventDal;