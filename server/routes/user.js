const express = require("express");
const UserDal = require("../dal/UserDal");
const { authMiddleware, signToken, authenticate } = require("../middleware/auth");
const toNumber = require("lodash").toNumber
const router = express.Router();

const userDal = new UserDal();
router.get("/validate", authMiddleware, async (req, res) => {
  const { data, err } = await userDal.getUser(req.jwtData.userId);
  if (data) {
    return res.status(200).send(`Hello ${data.username}`);
  }
  return res.status(401).end();
});
// Login a user
router.post("/login", async (req, res) => {
  // Make sure use is not already logged in 
  const decoded = authenticate(req);
  if (decoded) {
    const found = await userDal.hasUser(decoded.userId);
    if (found) return res.sendStatus(200);
  }
  // Input Validation
  if (req.body.username && req.body.password) {
    const { username, password } = req.body;
    // Check database for user credential
    const { data, err } = await userDal.validateUser(username, password);
    // Exception or Bad credential 
    if (err) next(err)
    if (!data) return res.status(400).send("Invalid Credential");
    // Sign JWT token
    // TODO: update JWT token content
    const { token, expireSec } = signToken({ userId: data.userId });
    // Set cookie
    res.cookie("token", token, { maxAge: expireSec * 1000 });
    // Return success
    return res.status(200).send({ token, username: data.username });
  } else {
    // Invalid request
    res.status(400);
    return res.send("Invalid Credential");
  }
});
// Register a user
router.post("/register", async (req, res) => {
  return res.status(200).send("Not Implemented");
});
// Get user information (username, preference, email, dietary, groupIds)
router.get("/:userId", authMiddleware, async (req, res) => {
  const userId = req.params.userId;
  const signInUser = req.jwtData.userId
  // should only be able to get signInUser's own information
  if (userId !== signInUser) {
    return res.status(401).send("Unauthorized");
  }
  // get user information
  const { data, err } = await userDal.getUser(userId);
  if (err) next(result.err)
  if (data) return res.status(200).send(data);
  // This should never be reached
  return res.status(401).send("Unauthorized");
});



module.exports = router;
