package chawan.cs3431.recyclerview

class Life {
    var image: Int = 0
    var title: String = ""
    var th: String = ""
    var en: String = ""

    constructor() {}

    constructor(image: Int, title: String, th: String, en: String) {
        this.image = image
        this.title = title
        this.th = th
        this.en = en
    }
}