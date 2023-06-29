const bcrypt = require("bcrypt");
const saltRounds = 10;

/**
 * 
 * @param {*} password 
 * @returns hash password
 */
async function hash(password) {
  return await bcrypt.hash(password, saltRounds);
}
/**
 * 
 * @param {*} input 
 * @param {*} expect 
 * @returns boolean
 */
async function compare(input, expect) {
  return await bcrypt.compare(input, expect);
}

module.exports = { hash, compare };