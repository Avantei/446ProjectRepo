const jwt = require("jsonwebtoken");

const { jwtConfig } = require("../config/index");

const jwtKey = jwtConfig.secret;
const jwtExpire = jwtConfig.expireSec; // seconds

/**
 * Sign a JWT with the userInfo
 * @param {*} userInfo 
 * @returns 
 */
const signToken = (userInfo) => {
  const token = jwt.sign({ data: userInfo }, jwtKey, {
    algorithm: "HS256",
    expiresIn: jwtExpire
  });
  return { token, expireSec: jwtExpire };
}

/**
 * Middleware that checks token, return 401 if invalid, or next() if valid
 * store the decoded data in req.jwtData
 * @param {*} req 
 * @param {*} res 
 * @param {*} next 
 * @returns 
 */
const authMiddleware = (req, res, next) => {
  const token = req.cookies.token
  if (!token) {
    return res.status(401).end()
  }

  try {
    decoded = jwt.verify(token, jwtKey)
    req.jwtData = decoded.data
    next()
  } catch (badToken) {
    return res.status(401).send("Unauthorized: bad jwt token")
  }
}

/**
 * 
 * @param {*} cookie 
 * @returns decoded object or null
 */
const authenticate = (req) => {
  const token = req.cookies.token
  if (!token) return null;
  try {
    decoded = jwt.verify(token, jwtKey)
    return decoded.data;
  } catch (badToken) {
    return null;
  }
}
 // TODO: update jwt content
const createTokenData = (user) => {
  return {
    userId: user.userId
  };
}

module.exports = {
  authMiddleware,
  authenticate,
  signToken,
  createTokenData
}