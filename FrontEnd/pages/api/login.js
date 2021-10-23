import withSession from "../../src/lib/session";
import axios from "axios";

async function handler(req, res) {
  const body = await req.body;
  const username = body.username;
  const password = body.password;

  const saveSession = async (token) => {
    req.session.set("token", token.substring(7));
    await req.session.save();
  };

  if (req.method === "POST" && username && password) {
    const url = process.env.LOGIN_URL;

    await axios
      .post(url, body)
      .then(async (response) => {
        console.log(response.data);
        await saveSession(response.data.token);
        res.statusCode = 200;
        res.json({ token: response.data.token });
      })
      .catch((error) => {
        console.log(error)
        res.status(error.response.status || 400).end(error);
      });
  } else {
    res.statusCode = 404;
    res.json({ Error: "error" });
  }
}
export default withSession(handler);
