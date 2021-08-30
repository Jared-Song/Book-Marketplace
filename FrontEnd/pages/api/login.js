import withSession from "../../src/lib/session";
import axios from "axios";

async function handler(req, res) {
  const body = await req.body;
  const username = body.username;
  const password = body.password;

  const saveSession = async (userId, username) => {
    req.session.set("user", {
      userId: userId,
      username: username,
    });
    await req.session.save();
  };

  if (req.method === "POST" && username && password) {
    const url = process.env.LOGIN_URL;

    //TODO
    console.log(body, url);
    // await saveSession("userId", username);

    await axios
      .post(url, body)
      .then(async (response) => {
        console.log(response)
        await saveSession("11111", username);
        res.statusCode = 200;
        res.json({ user_name: username });
        // if (res.status == 200) {
        //   saveSession(data.Item.user_id, data.Item.user_name);
        // }
      })
      .catch(({response}) => {
        // console.log(error)
        res.status(response.status || 400).end(error)
      }
        );
  } else {
    res.statusCode = 404;
    res.json({ Error: "error" });
  }
}
export default withSession(handler);
