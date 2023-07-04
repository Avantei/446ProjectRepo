const express = require("express");
const UserDal = require("../dal/UserDal");
const { sessionValidations } = require("../middleware/auth");
const jwtUtil = require("../util/JwtUtil");
const validation = require("../middleware/InputValidation");

const router = express.Router();
const userDal = new UserDal();

/**
 * Get info of current user
 * Expect: validate jwt token, existing user
 * Response: {user}
 */
router.get("/info", ...sessionValidations, async (req, res) => {
  const { data, err } = await userDal.getUserById(req.jwtData.userId);
  if (err) return next(err);
  if (!data) return res.status(404).send("Not Found: userId not found"); // Likely due to fake token or delete user
  return res.status(200).send({ user: data });
});

/**
 * Login, if existing token is valid, it will just return 200 (no refresh)
 * Expect: user exists
 * Request: {email: string, password: string}
 * Response: error || {token, user} || "OK"
 */
router.post("/login", validation.userLogin, async (req, res, next) => {
  // skip validation if the token is valid
  const decoded = jwtUtil.checkToken(req.cookies.token);
  if (decoded) {
    const found = await userDal.checkById(decoded.userId);
    if (found) return res.status(200).send("OK: token valid");
  }
  const { email, password } = req.body;
  // check credential
  const { data, err } = await userDal.validateUser(email, password);
  if (err) return next(err);
  if (!data) return res.status(400).send("Bad Request: Invalid credential");
  // sign JWT token
  const { token, expireSec } = jwtUtil.signToken(jwtUtil.createTokenData(data));
  // set cookie
  res.cookie("token", token, { maxAge: expireSec * 1000 });
  return res.status(200).send({ token, user: data });
});

/**
 * Register a user
 * Expect: email not used
 * Request: {name: string, email: string, password: string}
 * Response: cookie with token, {token, user}
 */
router.post("/register", validation.userRegister, async (req, res, next) => {
  const { email, username, password } = req.body;
  // check email not used
  if (await userDal.checkByEmail(email)) return res.status(409).send("Conflict: email used");
  // create user
  const { data, err } = await userDal.createUser(email, password, username);
  if (err) return next(err);
  if (!data) return next("Failed to create user"); // Should not happen unless uuid duplicates
  // sign JWT token
  const { token, expireSec } = jwtUtil.signToken(jwtUtil.createTokenData(data));
  // set cookie
  res.cookie("token", token, { maxAge: expireSec * 1000 });
  return res.status(201).send({ token, user: data });
});

module.exports = router;
