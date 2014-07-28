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
}


