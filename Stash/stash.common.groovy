import java.util.*

String STASH_CRED_ID() {
	return 'your-stash-cred-Id'
}

String STASH_URL() {
	return "https://your_stash_url"
}

String STASH_PROJECT_NAME() {
	return 'your-project-name'
}

String STASH_REPO_SLUG() {
	return 'your-repo-slug'
}

String STASH_PROJECT_URL() {
	def baseUrl = STASH_URL()
	def projectName = STASH_PROJECT_NAME()
	def repoSlug = STASH_REPO_SLUG()
	return "${baseUrl}/scm/${repoSlug}/${projectName}.git"
}

String STASH_PROJECT_PR_URL() {
	def baseUrl = STASH_URL()
	def projectName = STASH_PROJECT_NAME()
	def repoSlug = STASH_REPO_SLUG()
	return "${baseUrl}/rest/api/latest/projects/${repoSlug}/repos/${projectName}/pull-requests"
}

String STASH_PROJECT_PR_URL_UI_BY_ID(def prId) {
	def baseUrl = STASH_URL()
	def projectName = STASH_PROJECT_NAME()
	def repoSlug = STASH_REPO_SLUG()
	return String.format("${baseUrl}/projects/${repoSlug}/repos/${projectName}/pull-requests/%s/diff", prId)
}

String PULL_REQUEST_UI_MESSAGE(def prId){
	def url = STASH_PROJECT_PR_URL_UI_BY_ID(prId)
	return "Pull Request - [${url}](${url})"
}

Map<String, String> GET_PR_DATA(def bitbucketPayload, def prIdFromJob) {
	String prId = null
	String branch = null
	echo "STASH_PAYLOAD: ${bitbucketPayload}" 
	
	if (bitbucketPayload) {
		echo "Checkout to the PR from the Bitbucket web-hook"
        try {
			def payload = new groovy.json.JsonSlurper().parseText(bitbucketPayload)
			prId = payload.pullRequest.id
			branch = payload.pullRequest.fromRef.displayId
		} catch (NotSerializableException ex){
			// ignore this exception
		}
    } else {
		echo "Checkout to the PR from the parameter"
        prId = prIdFromJob
		
		withCredentials([usernamePassword(credentialsId: STASH_CRED_ID(), passwordVariable: 'STASH_PWD', usernameVariable: 'STASH_USER')]) {
			def url = "${STASH_PROJECT_PR_URL()}/${prIdFromJob}"
			def requestCurl = "curl -H 'Accept: application/json' -H 'Content-type: application/json' -k -u ${STASH_USER}:${STASH_PWD} ${url}"
			def requestedPayload = sh(script: "${requestCurl}", returnStdout: true)
			try {
				def payload = new groovy.json.JsonSlurper().parseText(requestedPayload)
				branch = payload.fromRef.displayId
			} catch (NotSerializableException ex){
			// ignore this exception
			} 
		}
	}
	
	def result = ["PR_ID":prId, "BRANCH": "origin/"+ branch]
	echo "RESULT=${result}"
	return result
}


return this