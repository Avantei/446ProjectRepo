const app = require("./app");
const http = require("http");
// const { serviceConfig } = require("./config/index");

const PORT = 8080;
app.set("port", PORT);

const server = http.createServer(app);

server.on("error", (error) => {
  if (error.syscall !== "listen") {
    throw error;
  }

  var bind = typeof PORT === "string"
    ? "Pipe " + PORT
    : "Port " + PORT;

  // handle specific listen errors with friendly messages
  switch (error.code) {
    case "EACCES":
      console.error(bind + " requires elevated privileges");
      process.exit(1);
    case "EADDRINUSE":
      console.error(bind + " is already in use");
      process.exit(1);
    default:
      throw error;
  }
});

server.on("listening", () => {
  const addr = server.address();
  const bind = typeof addr === "string"
    ? "pipe " + addr
    : "port " + addr.port;
  console.info(`Access http://localhost:${PORT}`);
});

server.on("close", () => {
  console.info(`Server closing...`);
});

server.listen(PORT);