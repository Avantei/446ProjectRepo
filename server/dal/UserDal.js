class UserDal {
  async login(username, password) {
    if (username !== "admin" || password !== "admin")
      return null;
    return "admin";
  }

  async getUser(userId) {
    if (userId !== "admin") return null;
    return { username: "admin" };
  }
};

module.exports = UserDal;