const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
let labelIndex = 0;
let markers = [];

function initMap() {
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 13,
		center: {lat: 51.1078479035455, lng: 17.038266655466643}
	});

	google.maps.event.addListener(map, "click", (event) => {
		addMarker(event.latLng, map);
	});

	var geocoder = new google.maps.Geocoder();

	document.getElementById('submit').addEventListener('click', function() {
		geocodeAddress(geocoder, map);
	});

	document.getElementById('render').addEventListener('click', function () {
		renderLocations(geocoder, map);
	})

	document.getElementById('search').addEventListener('click', function () {
		drawArea(map);
	})
}

function drawArea(resultsMap) {
	var radius = document.getElementById('area').value;
	console.log(resultsMap.markers)

	const cityCircle = new google.maps.Circle({
		strokeColor: "#FF0000",
		strokeOpacity: 0.8,
		strokeWeight: 2,
		fillColor: "#FF0000",
		fillOpacity: 0.35,
		map: resultsMap,
		center: {lat: 51.110744131381935, lng: 17.044514318387613},
		radius: parseFloat(radius),
	});
}

function renderLocations(geocoder, resultsMap) {
	var geoJSONStringRepresentation = document.getElementById('geoJSON').value;
	var geoJSON = JSON.parse(geoJSONStringRepresentation);
	var vehiclesInfo = geoJSON['features'];
	var coordinates = [];
	var labels = [];

	// extract desired data from geoJSON form
	for (var i = 0; i < vehiclesInfo.length; i++) {
		coordinates.push(vehiclesInfo[i]['geometry']['coordinates']);
		labels.push(vehiclesInfo[i]['properties']['line']);
	}

	// render markers on locations
	for (var j = 0; j < coordinates.length; j++) {
		var marker = new google.maps.Marker({
			map: resultsMap,
			position: { lat: coordinates[j][1], lng: coordinates[j][0]},
			label: labels[j],
		});
		markers.push(marker);
	}
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

function addMarker(location, map) {
	// Add the marker at the clicked location, and add the next-available label
	// from the array of alphabetical characters.
	if (markers.length > 0) {
		deleteMarkers();
	}
	markers.add(new google.maps.Marker({
		position: location,
		label: labels[labelIndex++ % labels.length],
		map: map,
	}));
}

function setMapOnAll(map) {
	for (let i = 0; i < markers.length; i++) {
		markers[i].setMap(map);
	}
}

function deleteMarkers() {
	setMapOnAll(null);
	markers = [];
}