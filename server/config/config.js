module.exports = {
  mongoConfig: {
    username: process.env.MONGO_USERNAME,
    password: process.env.MONGO_PASSWORD,
    dbName: process.env.MONGO_DB_NAME
  },
  serviceConfig: {
    PORT: process.env.PORT
  },
  jwtConfig: {
    secret: process.env.JWT_SECRET,
    expireSec: process.env.JWT_EXPIRE_SEC
  }
}
