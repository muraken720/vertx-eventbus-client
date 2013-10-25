'use strict';

var scope = require('jsdom').jsdom().createWindow()
  , library = __dirname + '/lib/vertxbus.js/index.js'
  , read = require('fs').readFileSync;

scope.SockJS = require('sockjs-client-node');
scope.eval(read(library, 'utf-8'));

module.exports = scope.vertx;
