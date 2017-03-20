'use strict';
var intersect = require('underscore');
var async = require("async");
var app = require("../../server/server");
var math = require("math");

module.exports = function(Worker) {

	global.final_result = [];
	global.result1 = [];
	global.result2 = [];
	global.result3 = [];

	Worker.afterRemote("login", function(context, worker, next) {
		var id = worker.userId;
		Worker.findById(id, function(err, instance) {
			context.result.data = instance;
			next();
		});
	});

	Worker.search = function(rating_filter, service_name, work_type, location, cb) {
		var coords = location.split(",");
		var Service = app.models.Service;
		var Vacancy = app.models.Vacancy;
		async.series([
			function(cb1) {
				if(rating_filter != -1) {
					if(rating_filter)
						Worker.find({"order": "rating"}, function(err, instances) {
							for(var i=0; i<instances.length; i++)
								global.result1.push(instances[i].id);
						});
					else
						Worker.find({"order": "rating desc"}, function(err, instances) {
							for(var i=0; i<instances.length; i++)
								global.result1.push(instances[i].id);
						});
				}
				Service.find({where: {"name": service_name, "work_type": work_type}}, function(err, instances) {
					if(instances.length == 0)
						Vacancy.create({
							'type': service_name,
							'location': location
						}, function(err, instance, created) {
							if(err)
								console.log("Why don't you just leave coding -__-");
						});
					for(var j=0;j<instances.length; j++) {
						global.result2.push(instances[j].id);
					}
				});
				Worker.find({"order": "location"}, function(err, instances) {
					for(var i=0; i<instances.length; i++) {
						var coords1 = instances[i].location.split(",");
						var lat1 = parseFloat(coords[0]);
						var lng1 = parseFloat(coords[1]);
						var lat2 = parseFloat(coords1[0]);
						var lng2 = parseFloat(coords1[1]);
						var a = math.sin((lat2-lat1)*3.14/360)*math.sin((lat2-lat1)*3.14/360) + math.cos(lat1)*math.cos(lat2)*math.sin((lng2-lng1)*3.14/360)*math.sin((lng2-lng1)*3.14/360);
						var c = 2*math.atan2(math.sqrt(a), math.sqrt(1-a));
						var d = 6371 * c;
						if(d <= 5)
							global.result3.push(instances[i].id);
						if(i==instances.length-1)
							cb1();
					}
				});
			},
			function(cb2) {
				if(global.result1.length != 0)
					global.final_result.push(global.result1);
				if(global.result2.length != 0)
					global.final_result.push(global.result2);
				if(global.result3.length != 0)
					global.final_result.push(global.result3);

				var intersection = intersect.intersection.apply(intersect, global.final_result);

				global.final_data = [];

				for(var i=0; i<intersection.length; i++) {
					Worker.findById(intersection[i], function(err, instance) {
						global.final_data.push(instance);
						if(global.final_data.length == intersection.length) {
							cb2();
							cb(null, global.final_data);
						}
					});
				}
			}
		]);
	}

	Worker.remoteMethod(
		'search',
		{
			accepts: [
						{arg: 'rating_filter', type: 'number', required: true},
						{arg: 'service_name', type: 'string', required: true},
						{arg: 'work_type', type: 'string', required: true},
						{arg: 'location', type: 'string', required: true}
					],
			returns: {arg: 'result', type: 'object'},
			http: {path: '/search', verb: 'get'}
		}
	);

};
