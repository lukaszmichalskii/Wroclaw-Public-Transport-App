const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
let labelIndex = 0;
let markers = [];
let markerClusterer;
let circle;

function initMap() {
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 13,
		center: {lat: 51.1078479035455, lng: 17.038266655466643}
	});

	// google.maps.event.addListener(map, "click", (event) => {
	// 	addMarker(event.latLng, map);
	// });

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

	document.getElementById('deleteMarkers').addEventListener('click', function () {
		deleteMarkers();
	})

	document.getElementById('deleteCircle').addEventListener('click', function () {
		deleteCircle(circle);
	})
}

function deleteCircle(circle) {
	circle.setMap(null);
	circle = null;
}

function drawArea(resultsMap) {
	var radius = document.getElementById('area').value;

	if (circle != null) {
		deleteCircle(circle);
	}

	circle = new google.maps.Circle({
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
	deleteMarkers();
	var geoJSONStringRepresentation = document.getElementById('geoJSON').value;
	// var geoJSONStringRepresentation = '{"features":[{"geometry":{"coordinates":[16.996735,51.069267],"type":"Point"},"type":"Feature","properties":{"line":"D"}},{"geometry":{"coordinates":[17.03747,51.10453],"type":"Point"},"type":"Feature","properties":{"line":"D"}},{"geometry":{"coordinates":[17.119217,51.15489],"type":"Point"},"type":"Feature","properties":{"line":"D"}},{"geometry":{"coordinates":[17.044888,51.10724],"type":"Point"},"type":"Feature","properties":{"line":"D"}},{"geometry":{"coordinates":[17.044258,51.12393],"type":"Point"},"type":"Feature","properties":{"line":"8"}},{"geometry":{"coordinates":[17.03738,51.104237],"type":"Point"},"type":"Feature","properties":{"line":"8"}},{"geometry":{"coordinates":[17.00688,51.075115],"type":"Point"},"type":"Feature","properties":{"line":"D"}},{"geometry":{"coordinates":[17.101238,51.14427],"type":"Point"},"type":"Feature","properties":{"line":"D"}},{"geometry":{"coordinates":[17.054882,51.127865],"type":"Point"},"type":"Feature","properties":{"line":"N"}},{"geometry":{"coordinates":[16.946033,51.097393],"type":"Point"},"type":"Feature","properties":{"line":"119"}},{"geometry":{"coordinates":[17.036354,51.101494],"type":"Point"},"type":"Feature","properties":{"line":"N"}},{"geometry":{"coordinates":[17.014126,51.088474],"type":"Point"},"type":"Feature","properties":{"line":"D"}},{"geometry":{"coordinates":[17.1039,51.14442],"type":"Point"},"type":"Feature","properties":{"line":"N"}},{"geometry":{"coordinates":[17.115835,51.147274],"type":"Point"},"type":"Feature","properties":{"line":"N"}},{"geometry":{"coordinates":[17.040453,51.10729],"type":"Point"},"type":"Feature","properties":{"line":"N"}},{"geometry":{"coordinates":[17.073626,51.115147],"type":"Point"},"type":"Feature","properties":{"line":"D"}},{"geometry":{"coordinates":[16.980238,51.126755],"type":"Point"},"type":"Feature","properties":{"line":"119"}},{"geometry":{"coordinates":[16.955753,51.11034],"type":"Point"},"type":"Feature","properties":{"line":"119"}},{"geometry":{"coordinates":[17.114613,51.14719],"type":"Point"},"type":"Feature","properties":{"line":"N"}},{"geometry":{"coordinates":[17.028625,51.100494],"type":"Point"},"type":"Feature","properties":{"line":"D"}},{"geometry":{"coordinates":[17.063278,51.079094],"type":"Point"},"type":"Feature","properties":{"line":"8"}},{"geometry":{"coordinates":[17.043682,51.121937],"type":"Point"},"type":"Feature","properties":{"line":"8"}},{"geometry":{"coordinates":[16.947449,51.0966],"type":"Point"},"type":"Feature","properties":{"line":"119"}},{"geometry":{"coordinates":[17.062767,51.079765],"type":"Point"},"type":"Feature","properties":{"line":"8"}},{"geometry":{"coordinates":[17.062643,51.07993],"type":"Point"},"type":"Feature","properties":{"line":"8"}},{"geometry":{"coordinates":[17.037212,51.13456],"type":"Point"},"type":"Feature","properties":{"line":"8"}},{"geometry":{"coordinates":[17.029646,51.08921],"type":"Point"},"type":"Feature","properties":{"line":"8"}}],"type":"FeatureCollection"}';

	var geoJSON = JSON.parse(geoJSONStringRepresentation);
	var vehiclesInfo = geoJSON['features'];

	// extract desired data from geoJSON form
	for (var i = 0; i < vehiclesInfo.length; i++) {
		var marker = new google.maps.Marker({
			map: resultsMap,
			position: { lat: vehiclesInfo[i]['geometry']['coordinates'][1], lng: vehiclesInfo[i]['geometry']['coordinates'][0]},
			label: vehiclesInfo[i]['properties']['line'],
		});
		markers.push(marker);
	}

	const imagePath = "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m";
	markerClusterer = new MarkerClusterer(resultsMap, markers, {imagePath: imagePath});
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
	new google.maps.Marker({
		position: location,
		label: labels[labelIndex++ % labels.length],
		map: map,
	});
}

function setMapOnAll(map) {
	for (let i = 0; i < markers.length; i++) {
		markers[i].setMap(map);
	}
}

function hideMarkers() {
	setMapOnAll(null);
}

function deleteMarkers() {
	hideMarkers();
	for (var i = 0; i < markers.length; i++) {
		markerClusterer.removeMarker(markers[i]);
	}
	markers = [];

}