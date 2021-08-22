import { withIronSession } from "next-iron-session";

export default function withSession(handler) {
  return withIronSession(handler, {
    cookieName: "sept_project",
    password: process.env.SESSION_PASSWORD,
    cookieOptions: {
      secure: false,
    },
  });
}
