import java.util.*

String GET_UI_COOKIE(def testRailUri, def username, def password) {
	def loginRequest = "curl -s -D - -o /dev/null -X POST '${testRailUri}/index.php?/auth/login/' --form 'name=${username}' --form 'password=${password}'"
    def loginResponse = sh(script: loginRequest, returnStdout: true)
    
    return loginResponse.split("\n")
        .findAll{it.contains("tr_session")}.last()
        .split(";").find{it.contains("tr_session")}
        .split(": ").last()
}

return this