import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

import { config } from "../config/constants";

export const subscribe = (onUpdate) => {
    const socket = new SockJS(`${config.BASE_PATH}/ws`);
    const stompClient = Stomp.over(() => socket); 
    stompClient.debug = () => {};
    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/posts', (message) => {
            const received = JSON.parse(message.body);
            if(received && received.data === "posts.newPost"){
                onUpdate(received.data);
            }
        });
    });
}
