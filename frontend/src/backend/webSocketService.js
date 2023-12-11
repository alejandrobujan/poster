import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

import { config } from "../config/constants";

export let stompClient;

export const subscribe = (onUpdate) => {
    const socket = new SockJS(`${config.BASE_PATH}/ws`);
    stompClient = Stomp.over(() => socket); 
    stompClient.debug = () => {};
    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/posts', (message) => {
            onReceive(message, onUpdate);
        });
    });
}

export const onReceive = (message, onUpdate) => {
    const received = JSON.parse(message.body);
    if(received && received.data === "posts.newPost"){
        onUpdate(received.data);
    }
}
