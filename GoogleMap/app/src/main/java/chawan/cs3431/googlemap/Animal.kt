package chawan.cs3431.googlemap

class Animal {

    var name : String = ""
    var picture: Int = 0
    var lat: Double=0.0
    var long:Double = 0.0
    var isCatch: Boolean = false

    constructor(name:String,picture: Int,lat:Double,long:Double, isCatch: Boolean = false){
        this.name = name
        this.picture = picture
        this.lat = lat
        this.long = long
        this.isCatch = isCatch
    }

}
