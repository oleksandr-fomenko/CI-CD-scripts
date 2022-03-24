import java.util.*

void CHECKOUT_TO_BRANCH(def branch, def credId, def url) {
	echo "Checkout to the branch=${branch}"
	checkout([$class: 'GitSCM', branches: [[name: branch]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: credId, url: url]]])
}

def GET_ARTIFACTS_BY_JOB_NUMBER(def jenkinsUrl, def jenkinsCredId, def jobName, def jobPath, def jobNumber, def artifactFullName){
	withCredentials([usernamePassword(credentialsId: jenkinsCredId, passwordVariable: 'USER_PWD', usernameVariable: 'USER_NAME')]) {
        def jobNameFullPath = "${jobPath}/${jobName}/${jobNumber}/artifact/${artifactFullName}"
	    def requestCurl = "curl --user ${USER_NAME}:${USER_PWD} -g ${jenkinsUrl}${jobNameFullPath}"
        return sh(script: "${requestCurl}", returnStdout: true)
	}
}

return this