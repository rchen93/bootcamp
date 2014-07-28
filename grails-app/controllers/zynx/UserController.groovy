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
}
