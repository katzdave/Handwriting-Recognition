var events = require('events');
var eventEmitter = new events.EventEmitter();
var count = 0;
var net = require('net');
var carrier = require('carrier');

exports.startConnection = function(port, host) {
  var port = port || 20000;
  var host = host || "127.0.0.1";
  var socket = net.createConnection(port, host);
  carrier.carry(socket, function(line) {
    console.log('got one line: ' + line);
  });
  //can emit with arguments
  //check if there is listeners
  console.log('nonblocking!');
  setTimeout(function() {console.log('emitting!!!!');eventEmitter.emit('0');}, 10000);
};

exports.index = function(req, res, next) {
  //console.log("hello world");
  res.render('testpg');
};

exports.upload = function(req, res) {
  console.log('set up event listener!');
  eventEmitter.once(count.toString(), function() {console.log('hello')});
  //increment count

  //result = 'sent';
  //console.log(req.query);

  //console.log(req.body);
  //use event handlers
  //setTimeout(function() {res.send('your word was: '+result);}, 3000);
  //console.log(POST);
};