const express = require("express");
const UserDal = require("../dal/UserDal");
const { authMiddleware, signToken, authenticate } = require("../middleware/auth");
const router = express.Router();

const userDal = new UserDal();
router.get("/validate", authMiddleware, (req, res) => {
  res.sendStatus(200);
});

router.post("/login", async (req, res) => {
  // Make sure use is not already logged in 
  const decoded = authenticate(req);
  if (decoded) {
    return res.sendStatus(200);
  }
  // Input Validation
  if (req.body.username && req.body.password) {
    const { username, password } = req.body;
    // Check database for user credential
    const result = await userDal.login(username, password);
    // Bad credential
    if (!result) {
      res.status(400);
      return res.send("Invalid Credential");
    }
    // Sign JWT token
    // TODO: update JWT token content
    const { token, expireSec } = signToken({ userId: result});
    // Set cookie
    res.cookie("token", token, { maxAge: expireSec * 1000 });
    // Return success
    return res.sendStatus(200);
  } else {
    // Invalid request
    res.status(400);
    return res.send("Invalid Credential");
  }
});

router.post("/register", async (req, res) => {
  return res.status(200).send("Not Implemented");
});

router.get("/:userId", authMiddleware, async (req, res) => {
  const userId = req.params.userId;
  const signInUser = req.jwtData.userId
  // should only be able to get signInUser's own information
  if (userId !== signInUser) {
    return res.status(400).send("Unauthorized");
  }
  // get user information
  const user = await userDal.getUser(userId);
  if (user) {
    return res.status(200).send(user);
  }
  // This should never be reached
  return res.status(400).send("Unauthorized");
});



module.exports = router;
