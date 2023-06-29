
// TODO: input validation

function register(req, res, next) {
  const { email, username, password } = req.body;
  if (email && username && password) return next();
  return res.status(400).send("Invalid input");
}

function login(req, res, next) {
  const { email, password } = req.body;
  if (email && password) return next();
  return res.status(400).send("Invalid input");
}

module.exports = {
  register, login
};