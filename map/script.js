function initMap() {
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 13,
		center: {lat: 51.1078479035455, lng: 17.038266655466643}
	});

	map.data.addGeoJson(
		// {
		//     "features": [
		//         {
		//         "type": "Feature",
		//         "geometry": {
		//             "coordinates":[16.978977,51.136314],
		//             "type":"Point"
		//             },
		//         "properties": {
		//             "population":200
		//             }
		//         },
		//         {
		//         "type": "Feature",
		//         "geometry": {
		//             "coordinates":[16.948153,51.143665],
		//             "type":"Point"
		//             },
		//         "properties": {
		//             "population":200
		//             }
		//         }]
		//     ,"type":"FeatureCollection"}

	);

	var geocoder = new google.maps.Geocoder();

	document.getElementById('submit').addEventListener('click', function() {
		geocodeAddress(geocoder, map);
	});
}

function geocodeAddress(geocoder, resultsMap) {
	var address = document.getElementById('address').value;
	geocoder.geocode({'address': address}, function(results, status) {
		if (status === 'OK') {
			resultsMap.setCenter(results[0].geometry.location);
			var marker = new google.maps.Marker({
				map: resultsMap,
				position: results[0].geometry.location
			});
		} else {
			alert('Geocode was not successful for the following reason: ' + status);
		}
	});
}