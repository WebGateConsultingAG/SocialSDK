require([ "sbt/dom", "sbt/json", "sbt/connections/ActivityService" ], function(dom,json,ActivityService) {
        var activitiesService = new ActivityService();        
        var member = activitiesService.newMember({     
        	"id" : "%{name=sample.memberId|helpSnippetId=Social_Activities_API_UpdateActivityMember}",
        	"userId" : "%{name=sample.userId2|helpSnippetId=Social_Activities_API_UpdateActivityMember}",
        	"role" : "reader",
        	"category" : "person"
        });
        var activityId = "%{name=sample.activityId|helpSnippetId=Social_Activities_API_AddMemberToActivity}";
        var promise = activitiesService.updateMember(activityId, member);        
        promise.then(
            function(activityMember) {
                dom.setText("json", json.jsonBeanStringify(activityMember));
            },
            function(error) {
                dom.setText("json", json.jsonBeanStringify(error));
            }
        );
    }
);
