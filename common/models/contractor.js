'use strict';
var intersect = require('underscore');
var async = require("async");
var app = require("../../server/server");
var math = require("math");

module.exports = function(Contractor) {

	global.final_result = [];
	global.result1 = [];
	global.result2 = [];
	global.result3 = [];

	Contractor.afterRemote("login", function(context, contractor, next) {
		var id = contractor.userId;
		Contractor.findById(id, function(err, instance) {
			context.result.data = instance;
			next();
		});
	});

	Contractor.assign = function(task_id, worker_id, task_info, rate, cb) {
		var Task = app.models.Task;
		Task,findById(task_id, function(err, instance) {
			Task.create({
				'info': task_info,
				'rate_decided': rate,
				'assigner': instance.client_id,
				'assignee': worker_id,
				'assigned_under': instance.contractor_id,
				'location': instance.location,
				'report_time': instance.report_time,
				'duration': instance.duration,
				'assigned': true
			}, function(err, instance, created) {
				if(err)
					console.log("FrontEnd Error! :D");
				else
					cb();
			});
		});
	}

	Contractor.remoteMethod(
		'assign',
		{
			accepts: [
						{arg: 'task_id', type: 'number', required: true},
						{arg: 'worker_id', type: 'number', required: true},
						{arg: 'task_info', type: 'string', required: true},
						{arg: 'rate', type: 'string', required: true}
					],
			http: {path: '/assign', verb: 'post'}
		}
	);

	Contractor.all_assigned = function(task_id, cb) {
		var Task = app.models.Task;
		Task.findById(task_id, function(err, instance) {
			instance.done = true;
			instance.save();
			cb();
		});
	}

	Contractor.remoteMethod(
		'all_assigned',
		{
			accepts: {arg: 'task_id', type: 'number', required: true},
			http: {path: '/allAssigned', verb: 'post'}
		}
	);

	Contractor.search = function(rating_filter, services_names, location, cb) {
		var coords = location.split(",");
		var Service = app.models.Service;
		var Vacancy = app.models.Vacancy;
		async.series([
			function(cb1) {
				var services = services_names.split(",");
				if(rating_filter != -1) {
					if(rating_filter)
						Contractor.find({"order": "rating"}, function(err, instances) {
							for(var i=0; i<instances.length; i++)
								global.result1.push(instances[i].id);
						});
					else
						Contractor.find({"order": "rating desc"}, function(err, instances) {
							for(var i=0; i<instances.length; i++)
								global.result1.push(instances[i].id);
						});
				}
				for(var i=0; i<services.length; i++) {
					Service.find({where: {"name": services[i]}}, function(err, instances) {
						if(instances.length == 0)
							Vacancy.create({
								'type': services[i],
								'location': location
							}, function(err, instance, created) {
								if(err)
									console.log("(\"._.) yet another mistake");
							});
						for(var j=0;j<instances.length; j++) {
							global.result2.push(instances[j].id);
						}
					});
				}
				Contractor.find({"order": "location"}, function(err, instances) {
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
					Contractor.findById(intersection[i], function(err, instance) {
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

	Contractor.remoteMethod(
		'search',
		{
			accepts: [
						{arg: 'rating_filter', type: 'number', required: true},
						{arg: 'services_names', type: 'string', required: true},
						{arg: 'location', type: 'string', required: true}
					],
			returns: {arg: 'result', type: 'object'},
			http: {path: '/search', verb: 'get'}
		}
	);

};
