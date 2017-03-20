var path = require('path');

var app = require(path.resolve(__dirname, '../server/server'));
var ds = app.datasources.mysqlDs;
var loopback = require('loopback');

ds.once('connected', function() {
	ds.autoupdate()
	.then(function(err) {
		if(err)
			throw err;
	});
});