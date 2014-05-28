# vertx-eventbus-client

This is a vert.x 2.1 eventbus client library for node.js.

### Installation

```
npm install vertx-eventbus-client
```

### API

```js
var vertx = require('vertx-eventbus-client');

var eb = new vertx.EventBus('http://localhost:8080/eventbus');

eb.onopen = function() {
  console.log('eb.onopen start.');
  eb.send('some-address', {'action': 'say', 'message': 'Hello World!'}, function(reply) {
    console.log("receive message: " + JSON.stringify(reply));
  });
};
```

### TEST

ServerSide(vert.x):
```
vertx run test/VertxEventBusServer.java
```

ClientSide(node.js):
```
node test/node-client.js
```

### LICENSE

ASL 2.0

