package zynx

class PostController {
    static scaffold = true

    def timeline() {
        def user = User.findByUserId(params.id)
        if (!user) {
            response.sendError(404)
        } else {
            [ user : user ]
        }
    }
}
