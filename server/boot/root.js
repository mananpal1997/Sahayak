'use strict';

module.exports = function(app) {
  var User = app.models.User;
  var Role = app.models.Role;
  var RoleMapping = app.models.RoleMapping;

  try {
  	User.create({ username: 'admin', email: 'admin@sahayak.com', password: 'admin@admin'}, function(err, instance, created) {});
  } catch (e) { }

  Role.create({name: 'admin'}, function(err, role) {
  	if(err)
  		console.log("Not gonna tell. Find yourself :P");
  	else {
  		role.principals.create({
  			principalType: RoleMapping.USER,
  			principalId: 1
  		}, function(err, principal) {});
  	}
  });
};
