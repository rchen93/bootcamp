package zynx

class PostController {
    static scaffold = true

    static defaultAction = "home"

    def postService

    def home() {
        if (!params.id) {
            params.id = "chuck_norris"
        }
        redirect(action: 'timeline', params: params)
    }

    def timeline() {
        def user = User.findByUserId(params.id)
        if (!user) {
            response.sendError(404)
        } else {
            [ user : user ]
        }
    }

    def addPost(String id, String content) {
        try {
            def newPost = postService.createPost(id, content)
            flash.message = "Added new post: ${newPost.content}"
        } catch (PostException pe) {
            flash.message = pe.message
        }
        redirect(action: 'timeline', id: id)
    }

    def global() {
        [ posts: Post.list(params), postCount: Post.count()]
    }
/*
    def addPost() {
        def user = User.findByUserId(params.id)
        if (user) {
            def post = new Post(params)
            user.addToPosts(post)
            if (user.save()) {
                flash.message = "Successfully created Post"
            } else {
                flash.message = "Invalid or empty post"
            }
        } else {
            flash.message = "Invalid User Id"
        }
        redirect(action: 'timeline', id: params.id)
    }
    */
}
