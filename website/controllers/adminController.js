app.controller('adminController', function(NgMap, $scope) {

	$scope.gender = 'both';
	$scope.age = "all";

	$scope.bhamashah_data = [
	{
		id : 1,
		name : "Ram",
		age : 25,
		gender : "Male",
		coords : '26.893795, 75.802835'
	},
	{
		id : 2,
		name : "Shyam",
		age : 40 ,
		gender : "Male",
		coords : '26.901300, 75.788862'
	},
	{
		id : 3,
		name : "Seema",
		age : 30,
		gender : "Female",
		coords : '26.917971, 75.799862'
	},
	{
		id : 4,
		name : "Arjun",
		age : 28,
		gender : "Male",
		coords : '26.907948, 75.784835'
	},
	{
		id : 5,
		name : "Sai",
		age : 29,
		gender : "Male",
		coords : '26.897141, 75.787077'
	},
	{
		id : 6,
		name : "Krishna",
		age : 35,
		gender : "Male",
		coords : '26.895969, 75.788426'
	},
	{
		id : 7,
		name : "Diya",
		age : 26,
		gender : "Female",
		coords : '26.896542, 75.791993'
	},
	{
		id : 8,
		name : "Sita",
		age : 32,
		gender : "Female",
		coords : '26.892805, 75.794642'
	},
	{
		id : 9,
		name : "Mita",
		age : 48,
		gender : "Female",
		coords : '26.890263, 75.787199'	
	},
	{
		id : 10,
		name : "Rita",
		age : 22,
		gender : "Female",
		coords : '26.883804, 75.781733'
	},
	{
		id : 11,
		name : "Kabir",
		age : 26,
		gender : "Male",
		coords : '26.884163, 75.780828'
	},
	{
		id : 12,
		name : "Dhruv",
		age : 26,
		gender : "Male",
		coords : '26.885329, 75.775766'
	},
	{
		id : 13,
		name : "Ramlal",
		age : 26,
		gender : "Male",
		coords : '26.884223, 75.801515'
	},
	{
		id : 14,
		name : "Muhammad",
		age : 26,
		gender : "Male",
		coords : '26.898691, 75.816832'
	},
	{
		id : 15,
		name : "Vikas",
		age : 26,
		gender : "Male",
		coords : '26.919646, 75.828728'
	},
	]

  // NgMap.getMap().then(function(map) {
  //   console.log(map.getCenter());
  //   console.log('markers', map.markers);
  //   console.log('shapes', map.shapes);
  // });
});