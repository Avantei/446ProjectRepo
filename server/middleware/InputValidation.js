
// TODO: input validation

function userRegister(req, res, next) {
  const { email, username, password } = req.body;
  if (email && username && password) return next();
  return res.status(400).send("Bad Request: must provide email, username, and password");
}

function userLogin(req, res, next) {
  const { email, password } = req.body;
  if (email && password) return next();
  return res.status(400).send("Bad Request: must provide email and password");
}

function groupCreation(req, res, next) {
  const { name } = req.body;
  if (name) return next();
  return res.status(400).send("Bad Request: must provide name");
}

function eventCreation(req, res, next) {
  const { name, groupId } = req.body;
  if (name && groupId) return next();
  return res.status(400).send("Bad Request: must provide name and groupId");
}

module.exports = {
  userRegister, userLogin,
  groupCreation,
  eventCreation
};