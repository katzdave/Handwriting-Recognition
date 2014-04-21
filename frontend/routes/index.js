var events = require('events');
var eventEmitter = new events.EventEmitter();
var count = 0;
var net = require('net');
var carrier = require('carrier');
var socket;
var MAXLEN = 15;
var IDPOS = 1;
var DELIM = '~';
var MSGID = 'W';

var sendMessage = function(stream, message) {
  stream.write(message + "\n");
};

var convertToMsgString = function(messageObj, count) {
  console.log(count);
  var retstr = (MSGID + DELIM + count);
  for (var i = 0; i != MAXLEN; ++i) {
    if (messageObj.hasOwnProperty(i.toString()))
      retstr += (DELIM + messageObj[i.toString()]);
  }
  return retstr;
};

var sendToClient = function(strArr, res) {
  res.send(strArr.toString());
}

exports.startConnection = function(port, host) {
  var port = port || 20000;
  var host = host || "127.0.0.1";
  socket = net.createConnection(port, host);
  carrier.carry(socket, function(line) {
    console.log('got one line: ' + line);
    var strArr = line.split(DELIM);
    eventEmitter.emit(strArr[IDPOS], strArr);
  });
};

exports.index = function(req, res, next) {
  res.render('home');
};

exports.upload = function(req, res) {
  eventEmitter.once(count.toString(), function(data){sendToClient(data, res);});
  sendMessage(socket, convertToMsgString(req.query, count));
  ++count;
};