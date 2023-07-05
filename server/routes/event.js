const express = require("express");
const EventDal = require("../dal/EventDal");
const GroupDal = require("../dal/GroupDal");
const { sessionValidations } = require("../middleware/auth");
const validation = require("../middleware/InputValidation");
const router = express.Router();

const eventDal = new EventDal();
const groupDal = new GroupDal();

async function isMemberInGroup(groupId, userId) {
  const { data, err } = await groupDal.isMemberInGroup(groupId, userId);
  if (err) return { err };
  if (!data) console.log(`${groupId} : ${userId} not matching`);
  return { data };
}

/* Assumption This is mounted on /group/:groupId/event */

/**
 * Create an event
 * Expect: validate jwt token, existing user
 * Expect: existing groupId, and user is part of group
 * Request: {name: string, groupId: string}
 * Response: error or the group created
 */
router.post("/create", ...sessionValidations, validation.eventCreation,
  async (req, res, next) => {
    const userId = req.jwtData.userId;
    const groupId = req.body.groupId;
    const name = req.body.name;
    // Make sure member is in group
    let { data: result, err: checkErr } = await isMemberInGroup(groupId, userId);
    if (checkErr) return next(err);
    if (!result) return res.status(404).send("Not Found: no matching group found");
    // Create event
    const { data, err } = await eventDal.createEvent(name, groupId, userId);
    if (err) return next(err);
    if (!data) return next("Failed to create event"); // db error
    return res.status(201).send(data);
  }
);

/**
 * Create an event
 * Expect: validate jwt token, existing user
 * Expect: existing groupId, and user is part of group
 * Request: {name: string, groupId: string}
 * Response: error or the group created
 */
router.get("/:eventId", ...sessionValidations,
  async (req, res, next) => {
    const userId = req.jwtData.userId;
    const eventId = req.params.eventId;
    // TODO: check if user is in event
    // Retrieve event
    const { data, err } = await eventDal.getById(eventId);
    if (err) return next(err);
    if (!data) return res.status(404).send("Not Found: event not found");
    return res.status(200).send(data);
  }
);

/* Add a location to event */
router.post("/:eventId/location/suggest", ...sessionValidations, async (req, res, next) => {
  // const userId = req.jwtData.userId;
  // const { data, err } = await eventDal.call();
  // if (err) next(err);
  // if (!data) next("Database error");
  return res.status(404).send("Not Implemented")
});
/* Change a vote on location */
router.put("/:eventId/location/:location/vote", ...sessionValidations, async (req, res, next) => {
  // const userId = req.jwtData.userId;
  // const { data, err } = await eventDal.call();
  // if (err) next(err);
  // if (!data) next("Database error");
  return res.status(404).send("Not Implemented")
});
/* Get a decision */
router.post("/:eventId/location/decision", ...sessionValidations, async (req, res, next) => {
  // const userId = req.jwtData.userId;
  // const { data, err } = await eventDal.call();
  // if (err) next(err);
  // if (!data) next("Database error");
  return res.status(404).send("Not Implemented")
});

module.exports = router;