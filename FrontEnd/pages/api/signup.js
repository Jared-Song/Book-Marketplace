import withSession from "../../src/lib/session";
import axios from "axios";

async function handler(req, res) {
  const body = await req.body;
  const username = body.username;
  const password = body.password;

  if (req.method === "POST" && username && password) {
    const url = process.env.REGISTER_URL;

    await axios
      .post(url, body)
      .then((response) => {
        const id = response.data.id
        res.statusCode = id ? 200 : 500;
        res.json(response.data);
      })
      .catch((error) => {
        console.log(error.response.data)
        res.status(error.response.status || 400).json(error.response.data)
      });
  } else {

    res.statusCode = 404;
    res.json({ Error: "error" });
  }
}
export default withSession(handler);
