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
	resultsMap.data.addGeoJson(JSON.parse(geoJSONStringRepresentation));
	// test locations
	// resultsMap.data.addGeoJson({"features":[{"geometry":{"coordinates":[17.005518,51.11112],"type":"Point"},"type":"Feature","properties":{"transport":"tmp_id"}},{"geometry":{"coordinates":[17.07346,51.115074],"type":"Point"},"type":"Feature","properties":{"transport":"tmp_id"}},{"geometry":{"coordinates":[17.103502,51.114044],"type":"Point"},"type":"Feature","properties":{"transport":"tmp_id"}},{"geometry":{"coordinates":[17.019949,51.108353],"type":"Point"},"type":"Feature","properties":{"transport":"tmp_id"}},{"geometry":{"coordinates":[17.036953,51.105125],"type":"Point"},"type":"Feature","properties":{"transport":"tmp_id"}},{"geometry":{"coordinates":[16.971334,51.129566],"type":"Point"},"type":"Feature","properties":{"transport":"tmp_id"}},{"geometry":{"coordinates":[17.038548,51.106888],"type":"Point"},"type":"Feature","properties":{"transport":"tmp_id"}},{"geometry":{"coordinates":[17.068865,51.113926],"type":"Point"},"type":"Feature","properties":{"transport":"tmp_id"}},{"geometry":{"coordinates":[16.958519,51.13249],"type":"Point"},"type":"Feature","properties":{"transport":"tmp_id"}},{"geometry":{"coordinates":[16.983587,51.12529],"type":"Point"},"type":"Feature","properties":{"transport":"tmp_id"}}],"type":"FeatureCollection"});
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