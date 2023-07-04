const jwtUtil = require("../util/JwtUtil");
const UserDal = require("../dal/UserDal");

const userDal = new UserDal();

/**
 * Middleware that checks token, return 401 if invalid, or next() if valid
 * store the decoded data in req.jwtData
 * This validates that jwt is valid and not expired
 * @param {*} req 
 * @param {*} res 
 * @param {*} next 
 * @returns 
 */
const validateToken = async (req, res, next) => {
  const tokenData = jwtUtil.checkToken(req.cookies.token);
  if (tokenData) {
    req.jwtData = decoded.data;
    return next();
  }
  return res.status(401).send("Unauthorized: bad jwt token");
}

/**
 * Middleware that checks if userId in jwtData exists
 * Must be called after validateToken
 * This make sure the data in the token is valid and not fraud
 * @param {*} req 
 * @param {*} res 
 * @param {*} next 5
 */
const validateUser = async (req, res, next) => {
  const isReal = await userDal.checkById(req.jwtData.userId);
  if (isReal) return next();
  return res.status(404).send("Not Found: user session not found");
}

/**
 * This is a short cut to validate token as well as user
 */
const sessionValidations = [validateToken, validateUser];
module.exports = {
  validateUser, validateToken, sessionValidations
}