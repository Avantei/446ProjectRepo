const express = require("express");
const userRouter = require("./user");
const groupRouter = require("./group");

const router = express.Router();

// Home
router.get("/", (req, res) => res.send("Connected"));

// API route
// router.use("/api/voicework", auth, voiceworkRouter);
router.use("/user", userRouter);
router.use("/group", groupRouter);
// API 404
router.use("/api/*", (req, res) => {
  res.status(404);
  res.send("API Endpoint not found");
});

module.exports = router;
