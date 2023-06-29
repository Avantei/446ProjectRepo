const express = require("express");
const UserDal = require("../dal/UserDal");
const jwt = require("../middleware/auth");
const validationMW = require("../middleware/UserValidation");

const router = express.Router();
const userDal = new UserDal();

/* Get info of current user (from jwt token) */
router.get("/info", jwt.authMiddleware, async (req, res) => {
  const { data, err } = await userDal.getUserById(req.jwtData.userId);
  if (err) next(err)
  if (!data) return res.status(401).send("Unauthorized: bad userId"); // Likely due to fake token or delete user
  return res.status(200).send({ user: data });
});

/* Login a user */
router.post("/login", validationMW.login, async (req, res, next) => {
  // skip validation if the token is valid
  const decoded = jwt.authenticate(req);
  if (decoded) {
    const found = await userDal.checkById(decoded.userId);
    if (found) return res.status(200).send("OK: token valid");
  }
  const { email, password } = req.body;
  // check credential
  const { data, err } = await userDal.validateUser(email, password);
  if (err) next(err)
  if (!data) return res.status(400).send("Invalid credential");
  // sign JWT token
  const { token, expireSec } = jwt.signToken(jwt.createTokenData(data));
  // set cookie
  res.cookie("token", token, { maxAge: expireSec * 1000 });
  return res.status(200).send({ token, user: data });
});

/* Register a user */
router.post("/register", validationMW.register, async (req, res, next) => {
  const { email, username, password } = req.body;
  // check email not used
  if (await userDal.checkByEmail(email)) return res.status(409).send("email occupied");
  // create user
  const { data, err } = await userDal.createUser(email, password, username);
  if (err) next(err);
  if (!data) next("Failed to create user"); // Should not happen unless uuid duplicates
  // sign JWT token
  const { token, expireSec } = jwt.signToken(jwt.createTokenData(data));
  // set cookie
  res.cookie("token", token, { maxAge: expireSec * 1000 });
  return res.status(201).send({ token, user: data });
});

module.exports = router;
