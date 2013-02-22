(function($) {
	$(document).ready(function() {
		initialize();
		$('a[href^="#"]').click(function(){  
			var the_id = $(this).attr("href");  
			$('html, body').animate({  
				scrollTop:$(the_id).offset().top-32
			}, 'slow');  
			return false;
		}); 
	});
})(jQuery);


function initialize() {
	console.log('initializing...');
	
	console.log('initializing media');
	
	var mediaConfig = {'video':true, 'audio': true};
	navigator.webkitGetUserMedia(mediaConfig, onUserMediaSuccess);

}

var localStream = null;

function attachMediaStream(stream) {
	localStream = stream;
	console.log("Attaching media stream");
	var localMedia = $('video.LocalMedia').get(0);
	localMedia.src = webkitURL.createObjectURL(stream);
};

var remoteMedia = null;
var remoteStream = null;
var pc1 = null;
var pc2 = null;


function onUserMediaSuccess(stream) {
	console.log('getUserMedia success');
	attachMediaStream(stream);
	createPeers();
}

function createPeers() {
	var pc_config = {"iceServers": [{"url": "stun:stun.l.google.com:19302"}]};
	pc1 = new webkitRTCPeerConnection(null);
	pc1.onicecandidate = function(event) {
		if (event.candidate) {
			pc2.addIceCandidate(event.candidate);
		}
	};
	pc1.addStream(localStream);
	pc1.createOffer(onGotLocalDescription);
}

function onGotRemoteDescription(remoteDescription) {
	pc1.setRemoteDescription(remoteDescription);
	pc2.setLocalDescription(remoteDescription);
}

function onGotLocalDescription(localDescription) {
	pc2 = new webkitRTCPeerConnection(null);
	
	pc2.onicecandidate = function(event) {
		if (event.candidate) {
			$.ajax({
				type: 'POST',
                                dataType: 'json',
				data: {RTCIceCandidate: JSON.stringify(event.candidate)},
				url: 'http://127.0.0.1:9090/Conference/IceCandidate'
			});
			pc1.addIceCandidate(event.candidate);
		}
	};
	
	pc2.onaddstream = function(event) {
		remoteMedia = $('video.RemoteMedia').get(0);
		remoteStream = event.stream;
		remoteMedia.src = webkitURL.createObjectURL(event.stream);
	};
	pc2.setRemoteDescription(localDescription);
	pc1.setLocalDescription(localDescription);
	pc2.createAnswer(onGotRemoteDescription);
}