const express = require("express");
const GroupDal = require("../dal/GroupDal");
const { sessionValidations } = require("../middleware/auth");
const validation = require("../middleware/InputValidation");
const router = express.Router();

const groupDal = new GroupDal();

/**
 * Create a group
 * Expect: validate jwt token, existing user
 * Request: {name: string}
 * Response: error or the group created
 */
router.post("/create", ...sessionValidations, validation.groupCreation,
  async (req, res, next) => {
    const userId = req.jwtData.userId;
    const groupName = req.body.name;
    const { data, err } = await groupDal.createGroup(groupName, userId);
    if (err) return next(err);
    if (!data) return next("Failed to create group"); // should not happen unless db error
    return res.status(201).send(data);
  }
);
/**
 * get detail of a group
 * Expect: validate jwt token, existing user
 * Expect: user must be a member of the request group
 * Request: N/A
 * Response: error or the group requested
 */
router.get("/:groupId", ...sessionValidations,
  async (req, res, next) => {
    const userId = req.jwtData.userId;
    const groupId = req.params.groupId;
    // check if user is in the group
    const { data, err } = await groupDal.findGroupWithMember(groupId, userId)
    if (err) return next(err)
    if (!data) return res.status(404).send("Not Found: no matching group found");
    return res.status(200).send(data);
  }
);
// Join a group, provide userId and groupId
router.get("/join", async (req, res) => {
  res.status(404).send("Not Implemented")
});
// Leave a group, provide userId and groupId
router.post("/leave", async (req, res) => {
  res.status(404).send("Not Implemented")
});

module.exports = router;
