package zynx

class PostController {
    static scaffold = true

    static defaultAction = "home"

    static navigation = [
        [group: 'tabs', action: 'timeline', title: 'My Timeline', order: 0],
        [action: 'global', title: 'Global Timeline', order: 1],

    ]

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

    def addPostAjax(String content) {
        try {
            def newPost = postService.createPost(session.user.userId, content)
            def recentPosts = Post.findAllByUser(
                    session.user,
                    [sort: 'dateCreated', order: 'desc', max: 20])
            render template: 'postEntry', collection: recentPosts, var: 'post'
        } catch (PostException pe) {
            render {
                div(class:"errors", pe.message)
            }
        }
    }

    def tinyUrl(String fullUrl) {
        def origUrl = fullUrl?.encodeAsURL()
        def tinyUrl =
            new URL("http://tinyurl.com/api-create.php?url=${origUrl}").text
        render(contentType:"application/json") {
            urls(small: tinyUrl, full:fullUrl)
        }
    }

    def global() {
        def posts = Post.list(params)
        def postCount = Post.count()
        [ posts: posts, postCount: postCount]
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
