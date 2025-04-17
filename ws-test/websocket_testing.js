import { Client } from '@stomp/stompjs';

import { WebSocket } from 'ws';
Object.assign(global, { WebSocket });

const client = new Client({
  brokerURL: 'ws://localhost:8080/ws-weather',
  onConnect: () => {
  console.log('WebSocket Connected');
    client.subscribe('/topic/measurements', message =>
      console.log(`Received: ${message.body}`)
    );
  },
});

client.activate();
