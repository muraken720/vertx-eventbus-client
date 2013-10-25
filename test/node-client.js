'use strict';

//var vertx = require('vertx-eventbus-client');
var vertx = require('../index.js');

var eb = new vertx.EventBus('http://localhost:8080/eventbus');

eb.onopen = function() {
  console.log('eb.onopen start.');
  eb.send('some-address', {'action': 'say', 'message': 'Hello World!'}, function(reply) {
    console.log("receive message: " + JSON.stringify(reply));
  });
};
