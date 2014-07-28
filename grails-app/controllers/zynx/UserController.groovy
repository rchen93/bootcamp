package zynx

class UserController {
    static scaffold = true

    def search() {}

    def results(String userId) {
    	def users = User.where {
    		userId =~ "%${userId}%"
    	}.list()
    	return [ users: users, term: params.userId,
    			totalUsers: User.count()]
    }

    def advSearch() {}

    def advResults() {
        def profileProps = Profile.metaClass.properties*.name
        def profiles = Profile.withCriteria {
            "${params.queryType}" {

                params.each { field, value ->

                    if (profileProps.grep(field) && value) {
                        ilike(field, value)
                    }
                }

            }

        }
        [ profiles : profiles ]

    }

    def register() {
        if (request.method == "POST") {
            def user = new User(params)
            if (user.validate()) {
                user.save()
                flash.message = "Successfully Created User"
                redirect(uri: '/')
            } else {
                flash.message = "Error Registering User"
                return [ user: user ]
            }
        }
    } 

    def register2(UserRegistrationCommand urc) {
        if (urc.hasErrors()) {
            render view: "register", model: [ user : urc ]
        } else {
            def user = new User(urc.properties)
            user.profile = new Profile(urc.properties)
            if (user.validate() && user.save()) {
                flash.message = "Welcome aboard, ${urc.fullName ?: urc.userId}"
                redirect(uri: '/')
            } else {
                // maybe not unique loginId?
                return [ user : urc ]
            }
        }
    }

     def profile(String id) {
        def user = User.findByUserId(id)
        if (user) {
            return [profile: user.profile]
        } else {
            response.sendError(404)
        }
    }
}

class UserRegistrationCommand {
    String userId
    String password
    String passwordRepeat
    byte[] photo
    String fullName
    String bio
    String homepage
    String email
    String timezone
    String country
    String jabberAddress

    static constraints = {
        importFrom Profile
        importFrom User
        password(size: 6..8, blank: false,
                 validator: { passwd, urc ->
                    return passwd != urc.loginId
                })
        passwordRepeat(nullable: false,
                validator: { passwd2, urc ->
                    return passwd2 == urc.password
                })
    }
}

