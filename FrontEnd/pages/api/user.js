import withSession from "../../src/lib/session";

function handler(req, res, session) {
  var user = req.session.get("user");
  res.send({ user });
}

export default withSession(handler);
