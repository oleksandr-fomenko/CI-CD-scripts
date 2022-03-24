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


return this