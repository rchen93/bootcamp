package zynx

class User {

	String userId
	String password
	//String homepage	 	
	Date dateCreated

	static hasOne = [ profile: Profile]
    static constraints = {
        userId size: 3..20, unique: true, nullable: false
        password size: 6..8, blank: false, validator: { passwd, user ->
            return passwd != user.userId
        }
        //homepage url: true, nullable: true
        profile nullable: true
    }
    static hasMany = [ posts: Post ]
}
