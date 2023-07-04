const express = require("express");
const GroupDal = require("../dal/GroupDal");
const jwt = require("../middleware/auth");
const router = express.Router();

const groupDal = new GroupDal();

// Create a group
router.post("/create", jwt.authMiddleware, async (req, res, next) => {
  const userId = req.jwtData.userId;
  const groupName = req.body.name;

  const { data, err } = await groupDal.createGroup(groupName, userId);
  if (err) next(err);
  if (!data) next("Failed to create group"); // should not happen unless db error
  return res.status(201).send(data);
});
// Get details of a group (name, memberIds, eventIds)
router.get("/:groupId", jwt.authMiddleware, async (req, res, next) => {
  const userId = req.jwtData.userId;
  const groupId = req.params.groupId;
  // check if user is in the group
  const { data, err } = await groupDal.findGroupWithMember(groupId, userId)
  if (err) next(err)
  if (!data) return res.status(400).send("Bad Request: no matching group found");
  return res.status(200).send(data);
});
// Join a group, provide userId and groupId
router.get("/join", async (req, res) => {
  res.status(404).send("Not Implemented")
});
// Leave a group, provide userId and groupId
router.post("/leave", async (req, res) => {
  res.status(404).send("Not Implemented")
});


module.exports = router;
