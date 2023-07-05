const express = require("express");
const userRouter = require("./user");
const groupRouter = require("./group");
const eventRouter = require("./event");

const router = express.Router();

// Home
router.get("/", (req, res) => res.send("Connected"));

// API route
router.use("/user", userRouter);
router.use("/group", groupRouter);
router.use("/event", eventRouter);
// API 404
router.use("/*", (req, res) => {
  res.status(404);
  res.send("Not Found: Endpoint or method not defined");
});

module.exports = router;
