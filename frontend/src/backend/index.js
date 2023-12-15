import { init } from "./appFetch";
import * as userService from "./userService";
import * as postService from "./postService";
import * as ratingService from "./ratingService";
import * as catalogService from "./catalogService";
import * as notificationService from "./notificationService";
import * as commentService from "./commentService";
import * as saveService from "./saveService";
import * as webSocketService from "./webSocketService";

export { default as NetworkError } from "./NetworkError";

// eslint-disable-next-line
export default { init, userService, postService, ratingService, catalogService, commentService, notificationService, saveService, webSocketService };
