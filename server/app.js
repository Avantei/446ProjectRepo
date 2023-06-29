const express = require("express");
const morgan = require("morgan");
const cors = require("cors");
const cookieParser = require("cookie-parser");

const router = require("./routes/index");

const app = express();

// CORS
if (!process.env.NODE_ENV || process.env.NODE_ENV === "development")
  app.use(
    cors({
      origin: "http://localhost:8080",
      credentials: true,
      exposedHeaders: ["set-cookie"]
    })
  );
// Request Logger
app.use(morgan("dev"));
// Express MW
app.use(cookieParser());
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

// routing
app.use(router);

// error handler
app.use(function (err, req, res, next) {
  // only provide error message in develoment
  let displayed =
    req.app.get("env") === "development"
      ? "<b>Running in development mode:</b><br>" + err
      : res.status;
  res.status(err.status || 500);
  res.send(displayed);
});

module.exports = app;
