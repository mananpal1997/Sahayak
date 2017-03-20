'use strict';
var app = require("../../server/server");
var plivo = require("plivo");
var request = require("request");
var async = require("async");

var p = plivo.RestAPI({authId: 'MAZDA4ZJKXOTK4YZG5NT', authToken: 'OTdjNTliMWU3MmU1YWRjY2I1MzAzZjBkMmFiZDI2'});

module.exports = function(Client) {

	global.client_name = "";
	global.client_phone = "";
	global.loc = "";

	Client.book_worker = function(client_id, worker_id, task_info, service_id, location, report_time, duration, cb) {
		async.series([
			function(cb1) {
				Client.findById(client_id, function(err, instance) {
					if(err)
						console.log("catch it homie");
					else {
						global.client_name = instance.name;
						global.client_phone = instance.phone_number;
					}
					cb1();
				});
			},
			function(cb2) {
				var Worker = app.models.Worker;
				request.get("https://maps.googleapis.com/maps/api/geocode/json?latlng="+location+"&key=AIzaSyCXLOyTx9owpJEkv-RgboWAVCtllFmwjnQ", function(err, resp, body) {
					Worker.findById(worker_id, function(err, instance) {
						global.loc = (JSON.parse(body)).results[0].formatted_address;
						p.send_message({
							'src': '+919457226437',
							'dst': '+91' + instance.phone_number,
							'method': 'GET',
							'text': 'Your service has been requested at ' + global.loc
									+ '\nby ' + global.client_name + ' for ' + task_info + ' on ' + String(report_time)
									+ '. Contact number: ' + global.client_phone
						}, function(status, res) {
							cb2();
						});
					});
				});
			},
			function(cb3) {
				var Service = app.models.Service;
				Service.findById(service_id, function(err, instance) {
					if(err)
						console.log("Still not gonna tell the cause :P");
					else {
						var Task = app.models.Task;
						Task.create({
							'info': task_info,
							'rate_decided': instance.rate,
							'assigner': client_id,
							'assignee': worker_id,
							'location': global.loc,
							'report_time': report_time,
							'duration': duration,
							'assigned': true,
							'done': false
						}, function(err, inst) {
							if(err)
								console.log("C'mon, write good code!");
							else {
								cb3();
								cb();
							}
						});
					}
				});
			}
		]);
	}

	Client.remoteMethod(
		'book_worker',
		{
			accepts: [
						{arg: 'client_id', type: 'number', required: true},
						{arg: 'worker_id', type: 'number', required: true},
						{arg: 'task_info', type: 'string', required: true},
						{arg: 'service_id', type: 'number', required: true},
						{arg: 'location', type: 'string', required: true},
						{arg: 'report_time', type: 'string', required: true},
						{arg: 'duration', type: 'string', required: true}
					],
			http: {path: '/book_worker', verb: 'post'}
		}
	);

};
