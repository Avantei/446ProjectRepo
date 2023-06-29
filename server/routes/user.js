const express = require("express");
const UserDal = require("../dal/UserDal");
const { authMiddleware, signToken, authenticate, createJwtData } = require("../middleware/auth");
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

/* Login a user */
router.post("/login", async (req, res, next) => {
  // Make sure use is not already logged in 
  const decoded = authenticate(req);
  if (decoded) {
    const found = await userDal.hasUser(decoded.userId);
    if (found) return res.sendStatus(200);
  }
  // Input Validation
  if (req.body.username && req.body.password) {
    const { username, password } = req.body;
    // validate credential
    const { data, err } = await userDal.validateUser(username, password);
    if (err) next(err)
    if (!data) return res.status(400).send("Invalid Credential");
    // Sign JWT token
    const { token, expireSec } = signToken(createJwtData(data));
    // Set cookie
    res.cookie("token", token, { maxAge: expireSec * 1000 });
    // Return success
    return res.status(200).send({ token, user: data });
  }
  // Invalid request
  return res.status(400).send("Invalid Credential");
});

/* Register a user */
router.post("/register", async (req, res, next) => {
  if (req.body.username && req.body.password) {
    const { username, password } = req.body;
    // TODO: input validation
    const {data, err} = await userDal.createUser(username, password);
    if (err) next(err);
    if (!data) next("Failed to create user"); // Should not happen unless uuid duplicates
    // Sign JWT token
    const { token, expireSec } = signToken(createJwtData(data));
    // Set cookie
    res.cookie("token", token, { maxAge: expireSec * 1000 });
    // Return success
    return res.status(201).send({ token, user: data });
  }
  // Invalid request
  return res.status(400).send("Invalid Input");
});

/* Get user information (username, preference, email, dietary, groupIds) */
router.get("/:userId", authMiddleware, async (req, res, next) => {
  const userId = req.params.userId;
  const signInUser = req.jwtData.userId
  // should only be able to get signInUser's own information
  if (userId !== signInUser) return res.status(401).send("Unauthorized");
  // Get user info
  const { data, err } = await userDal.getUser(userId);
  if (err) next(err)
  if (data) return res.status(200).send(data);
  // Should never be reached
  return res.status(401).send("Unauthorized");
});



module.exports = router;
