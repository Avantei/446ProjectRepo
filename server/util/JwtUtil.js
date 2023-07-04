const jwt = require("jsonwebtoken");

const { jwtConfig } = require("../config/index");

const jwtKey = jwtConfig.secret;
const jwtExpire = jwtConfig.expireSec; // seconds

/**
 * Sign a JWT with the userInfo
 * @param {*} userInfo 
 * @returns {*} {token: TokenData, expireSec: Number}
 */
const signToken = (userInfo) => {
  const token = jwt.sign({ data: userInfo }, jwtKey, {
    algorithm: "HS256",
    expiresIn: jwtExpire
  });
  return { token, expireSec: jwtExpire };
}

/**
 * Decode a token and return its content
 * @params token
 * @returns TokenData || null
 */
const checkToken = (token) => {
  try {
    decoded = jwt.verify(token, jwtKey);
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
  signToken,
  checkToken,
  createTokenData
}