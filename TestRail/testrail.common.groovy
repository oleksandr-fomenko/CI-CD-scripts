import java.util.*

String GET_UI_COOKIE(def testRailUri, def username, def password) {
	def loginRequest = "curl -s -D - -o /dev/null -X POST '${testRailUri}/index.php?/auth/login/' --form 'name=${username}' --form 'password=${password}'"
    def loginResponse = sh(script: loginRequest, returnStdout: true)
    
    return loginResponse.split("\n")
        .findAll{it.contains("tr_session")}.last()
        .split(";").find{it.contains("tr_session")}
        .split(": ").last()
}

String DOWNLOAD_SUITE(def testRailUri, def suiteId, def cookie) {
    def downloadSuiteRequest = "curl -X GET '${testRailUri}/index.php?/suites/export/${suiteId}' --header 'Cookie: ${cookie}'"
	
	return sh(script: downloadSuiteRequest, returnStdout: true)
}

def DOWNLOAD_SUITES(def testRailUri, def suiteIds, def cookie) {
	def resultList = []
	
	suiteIds.each{
		def suiteContent = DOWNLOAD_SUITE(testRailUri, it, cookie)
		resultList.add(suiteContent)
	}
	
	return resultList
}

return this