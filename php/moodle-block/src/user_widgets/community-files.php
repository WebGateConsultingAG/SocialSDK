<div id="<?php echo $this->config->elementID;?>"></div>

<?php 
// Ensure that element IDs are unique
$timestamp = time();

global $CFG;
require_once $CFG->dirroot . '/blocks/ibmsbt/user_widgets/templates/ibm-sbt-community-files.php';
?>

<script type="text/javascript">
var grid<?php echo $timestamp; ?>;

function onCommunityChange() {
	require(["sbt/dom", "sbt/connections/controls/files/FileGrid"], function(dom, FileGrid) {
		var communityList = dom.byId("ibm-sbt-communities-<?php echo $timestamp; ?>");
		var currentCommunity = communityList.options[communityList.selectedIndex].value;

		var domNode = dom.byId("fileRow-<?php echo $timestamp; ?>");
		console.log(domNode);
		var FileRow = domNode.text || domNode.textContent;
	    domNode = dom.byId("pagingHeader-<?php echo $timestamp; ?>");
	    var PagingHeader = domNode.text || domNode.textContent;
	    domNode = dom.byId("pagingFooter-<?php echo $timestamp; ?>");
	    var PagingFooter = domNode.text || domNode.textContent;
		
		grid<?php echo $timestamp; ?> = new FileGrid({
	         type : "communityFiles",
	         communityId: currentCommunity,
	         endpoint: "<?php echo $this->config->endpoint; ?>",
	         hidePager: false,
	         hideSorter: true,
	         hideFooter: false,
	    	 rendererArgs : { template : FileRow, pagerTemplate : PagingHeader, footerTemplate : PagingFooter}       
	    });
		dom.byId("ibm-sbt-community-files-list-<?php echo $timestamp; ?>").innerHTML = "";
        dom.byId("ibm-sbt-community-files-list-<?php echo $timestamp; ?>").appendChild(grid<?php echo $timestamp; ?>.domNode);
        
        grid<?php echo $timestamp; ?>.update();
	});
}

function addOnClickHandlers(communityService, dom) {

	dom.byId("uploadBtn").onclick = function(evt) {
		var communityList = dom.byId("ibm-sbt-communities-<?php echo $timestamp; ?>");
		var currentCommunity = communityList.options[communityList.selectedIndex].value;
		var communityName = communityList.options[communityList.selectedIndex].innerHTML;
		if (currentCommunity) {
			uploadCommunityFile(communityService, currentCommunity, communityName, dom);
		}
	};
}

function handleLoggedIn(communityService, dom) {
	loadCommunity(communityService, dom);
	addOnClickHandlers(communityService, dom);
}

function uploadCommunityFile(communityService, communityId, communityName, dom) {
	displayMessage(dom, "Uploading...Please wait.");
	var img = dom.byId("ibm-sbt-loading-<?php echo $timestamp; ?>");
	img.style.display = "block";
	communityService.uploadCommunityFile("ibm-sbt-community-files-<?php echo $timestamp; ?>", communityId).then(
		function(community) {
			displayMessage(dom, "Community file uploaded successfuly to community : " + communityName);
			img.style.display = "none";
			grid<?php echo $timestamp; ?>.update(null);
		}, 
		function(error) {
			handleError(dom, error);
		}
	);
}

function displayMessage(dom, msg) {
	dom.setText("ibm-sbt-community-files-success-<?php echo $timestamp; ?>", msg);
	dom.byId("ibm-sbt-community-files-success-<?php echo $timestamp; ?>").style.display = "";
	dom.byId("ibm-sbt-community-files-error-<?php echo $timestamp; ?>").style.display = "none";
}

function handleError(dom, error) {
	dom.setText("ibm-sbt-community-files-error-<?php echo $timestamp; ?>", "Error: " + error.message);

	dom.byId("ibm-sbt-community-files-success-<?php echo $timestamp; ?>").style.display = "none";
	dom.byId("ibm-sbt-community-files-error-<?php echo $timestamp; ?>").style.display = "";
}

function clearError(dom) {
	dom.setText("ibm-sbt-community-files-error-<?php echo $timestamp; ?>", "");
	dom.byId("ibm-sbt-community-files-error-<?php echo $timestamp; ?>").style.display = "none";
}

function loadCommunity(communityService, dom) {
	communityService.getMyCommunities({
		ps : 1
	}).then(function(communities) {
		var communityList = dom.byId("ibm-sbt-communities-<?php echo $timestamp; ?>");
		for (var i = 0; i < communities.length; i++) {
			var opt = document.createElement('option');
		    opt.value = communities[i].getCommunityUuid();
		    var title = communities[i].getTitle();
		    if (title.length > 20) {
			    title = title.substr(0, 16) + '...';
		    }
		    opt.innerHTML = title;
		    communityList.appendChild(opt);
		}
		onCommunityChange();
	}, function(error) {
		console.log(error);
		handleError(dom, error);
	});

}

require([ "sbt/connections/CommunityService", "sbt/dom", "sbt/config", ], function(CommunityService, dom, config) {
	var communityService = new CommunityService({endpoint: "<?php echo $this->config->endpoint; ?>"});
	// To make sure authentication happens before upload
	communityService.endpoint.authenticate().then(function() {
		handleLoggedIn(communityService, dom);
	});
});
</script>