package zynx

class LoginController {

    def form(String id) {
        [userId: id]
    }

    def signIn(String userId, String password) {
        def user = User.findByUserId(userId)
        if (user && user.password == password) {
            session.user = user
            redirect uri: "/"
        }
        else {
            flash.error = "Unknown username or password"
            redirect action: "form"
        }
    }
}