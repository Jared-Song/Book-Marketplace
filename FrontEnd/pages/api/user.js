import withSession from "../../src/lib/session";

function handler(req, res, session) {
  const token = req.session.get("token");
  res.send({ token });
}

export default withSession(handler);
