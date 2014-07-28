package zynx

class PhotoUploadCommand {
    byte[] photo
    String userId
}

class ImageController {

    def upload(PhotoUploadCommand puc) {
        def user = User.findByUserId(puc.userId)
        user.profile.photo = puc.photo
        redirect controller: "user", action: "profile", id: puc.userId
    }

    def form() {
        // pass through to upload form
        [ userList : User.list() ]
    }

    def renderImage(String id) {
        def user = User.findByUserId(id)
        if (user?.profile?.photo) {
            response.setContentLength(user.profile.photo.size())
            response.outputStream.write(user.profile.photo)
        } else {
            response.sendError(404)
        }
    }
    
}
