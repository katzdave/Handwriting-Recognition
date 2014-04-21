
var express = require('express'),
    exphbs  = require('ejs'),
    emitter = require('events').EventEmitter(),
    carrier = require('carrier'),
    net = require('net'),
    path = require('path'),
    app = express()
    index = require('./routes/index');

app.configure(function() {
  app.set('port', process.env.PORT || 3000);
  app.set('views', __dirname + '/views');
  app.set('view engine', 'ejs');
});

app.use(express.logger('dev'));
app.use(express.json());
app.use(express.static(path.join(__dirname, 'public')));
app.use(app.router);

if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

app.get('/', index.index);
app.get('/data*', index.upload);

index.startConnection();
app.listen(3000);