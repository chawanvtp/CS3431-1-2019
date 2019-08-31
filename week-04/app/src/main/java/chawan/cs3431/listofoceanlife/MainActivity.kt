package chawan.cs3431.listofoceanlife

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.listview_row.view.*

class MainActivity : AppCompatActivity() {

    private var lifeList = ArrayList<Life>()
    var adapter: CustomAdapter? = null
    private val myImageList = intArrayOf(R.drawable.octopus, R.drawable.crab, R.drawable.sea_otter, R.drawable.sea_turtle, R.drawable.shark, R.drawable.killer_whale)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prepareData()
        Log.d(TAG, "TEST"+lifeList.size)
        adapter = CustomAdapter(this@MainActivity, lifeList)
        listView.adapter = adapter

        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Get the GridView selected/clicked item text
                val selectedItem = parent.getItemAtPosition(position).toString()
                val title = view.title.text.toString()
                Log.d(TAG, "clicked at : $selectedItem - $title")
//            val courseName = view.tvCourseName.text.toString()
//            val courseNumStudents = (view.tvCourseNumStudents.text.toString()).substring(11)
//            val courseTotalClasses = (view.tvCourseTotalClasses.text.toString()).substring(16)
//            // Display the selected/clicked item text and position on TextView
//            Log.d(TAG,"GridView item clicked : $courseID \nAt index position : $position")
            val intent = Intent(this@MainActivity,DetailActivity::class.java)

                intent.putExtra("title",lifeList[position].title)
                intent.putExtra("th",lifeList[position].th)
                intent.putExtra("en",lifeList[position].en)
                intent.putExtra("image",myImageList[position])

            startActivity(intent)
            }
        }

    }

    private fun prepareData(){
        var life = Life(
            myImageList[0]
            ,"Octopus"
            ,"หมึกสาย หรือ หมึกยักษ์ เป็นมอลลัสก์ประเภทหมึกอันดับหนึ่ง ใช้ชื่อวิทยาศาสตร์ว่า Octopoda"
            ,"The octopus is a soft-bodied, eight-limbed mollusc of the order Octopoda. Around 300 species are recognised, and the order is grouped within the class Cephalopoda with squids, cuttlefish, and nautiloids. Like other cephalopods, the octopus is bilaterally symmetric with two eyes and a beak, with its mouth at the center point of the eight limbs (\"tentacle\" is used as an umbrella term for cephalopod limbs; however, within a teuthological context; \"arm\" is used to refer to such limbs while \"tentacle\" is reserved for feeding appendages not found on octopuses). The soft body can rapidly alter its shape, enabling octopuses to squeeze through small gaps. They trail their eight appendages behind them as they swim. The siphon is used both for respiration and for locomotion, by expelling a jet of water. Octopuses have a complex nervous system and excellent sight, and are among the most intelligent and behaviourally diverse of all invertebrates."
        )
        lifeList.add(life)

        life = Life(
            myImageList[1]
            ,"Crab"
            ,"ปู เป็นสัตว์พวกเท้าปล้องชนิดหนึ่ง อยู่ในไฟลัมอาโทรโพดา ในอันดับฐานบราชีอูรา (Brachyura) มีลักษณะสิบขา มีหลายชนิดที่อยู่ทั้งน้ำจืดและทะเล รวมถึงอยู่แต่เฉพาะบนบก"
            ,"Crabs are decapod crustaceans of the infraorder Brachyura, which typically have a very short projecting \"tail\" (abdomen) (Greek: βραχύς, romanized: brachys = short,[2] οὐρά / οura = tail[3]), usually entirely hidden under the thorax. They live in all the world's oceans, in fresh water, and on land, are generally covered with a thick exoskeleton and have a single pair of pincers. Many other animals with similar names – such as hermit crabs, king crabs, porcelain crabs, horseshoe crabs, and crab lice – are not true crabs."
        )
        lifeList.add(life)

        life = Life(
            myImageList[2]
            ,"Sea Otter"
            ,"นากทะเล (อังกฤษ: Sea otters; ชื่อวิทยาศาสตร์: Enhydra lutris) เป็นสัตว์เลี้ยงลูกด้วยนมทางทะเล ประเภทหนึ่งโดยอาศัยอยู่บริเวณชายฝั่งตอนเหนือและตะวันออกเฉียงเหนือของมหาสมุทรแปซิฟิก ในวัยเจริญพันธุ์จะมีน้ำหนักประมาณ 14–45 กิโลกรัม (31–99 ปอนด์) นากทะเลเป็นสัตว์ในวงศ์เพียงพอน (Mustelidae) ชนิดหนึ่ง และเป็นสัตว์เลี้ยงลูกด้วยน้ำนมทางทะเลที่มีขนาดเล็กที่สุดอีกด้วย นากทะเลนั้นไม่เหมือนสัตว์เลี้ยงลูกด้วยนมทางทะเลทั่วไปเพราะมีฉนวนกันความร้อนด้วยขนที่หนาแน่น จึงทำให้นากทะเลสามารถหาอาหารในทะเลเป็นเวลานาน ๆ ได้"
            ,"The sea otter (Enhydra lutris) is a marine mammal native to the coasts of the northern and eastern North Pacific Ocean. Adult sea otters typically weigh between 14 and 45 kg (31 and 99 lb), making them the heaviest members of the weasel family, but among the smallest marine mammals. Unlike most marine mammals, the sea otter's primary form of insulation is an exceptionally thick coat of fur, the densest in the animal kingdom. Although it can walk on land, the sea otter is capable of living exclusively in the ocean."
        )
        lifeList.add(life)

        life = Life(
            myImageList[3]
            ,"Sea Turtle"
            ,"เต่าทะเล (อังกฤษ: Sea turtle) เป็นเต่าที่อยู่ในวงศ์ใหญ่ Chelonioidea ซึ่งวิวัฒนาการจนสามารถอาศัยอยู่ได้ในทะเลตลอดเวลา โดยจะไม่ขึ้นมาบนบกเลย นอกจากการวางไข่ของตัวเมียเท่านั้น"
            ,"Sea turtles (superfamily Chelonioidea), sometimes called marine turtles, are reptiles of the order Testudines and of the suborder Cryptodira. The seven existing species of sea turtles are the green sea turtle, loggerhead sea turtle, Kemp's ridley sea turtle, olive ridley sea turtle, hawksbill sea turtle, flatback sea turtle, and leatherback sea turtle."
        )
        lifeList.add(life)

        life = Life(
            myImageList[4]
            ,"Shark"
            ,"ปลาฉลาม (ชื่อทางวิทยาศาสตร์: Selachimorpha) เป็นปลาในชั้นปลากระดูกอ่อนจำพวกหนึ่ง มีรูปร่างโดยรวมเพรียวยาว ส่วนใหญ่มีซี่กรองเหงือก 5 ซี่ ครีบทุกครีบแหลมคม ครีบหางเป็นแฉกเว้าลึก มีจุดเด่นคือ ส่วนหัวและจะงอยปากแหลมยาว ปากเว้าคล้ายพระจันทร์เสี้ยวภายในมีฟันแหลมคม"
            ,"Sharks are a group of elasmobranch fish characterized by a cartilaginous skeleton, five to seven gill slits on the sides of the head, and pectoral fins that are not fused to the head. Modern sharks are classified within the clade Selachimorpha (or Selachii) and are the sister group to the rays. However, the term \"shark\" has also been used for extinct members of the subclass Elasmobranchii outside the Selachimorpha, such as Cladoselache and Xenacanthus, as well as other Chondrichthyes such as the holocephalid eugenedontidans."
        )
        lifeList.add(life)

        life = Life(
            myImageList[5]
            ,"Killer Whale"
            ,"วาฬเพชฌฆาต (อังกฤษ: killer whale) หรือ วาฬออร์กา (orca) เป็นสปีชีส์ที่ใหญ่ที่สุดในในวงศ์โลมามหาสมุทร (Delphinidae) สามารถพบเห็นได้ในมหาสมุทรทั่วโลก ตั้งแต่แถบอาร์กติกและแอนตาร์กติกา จนถึงทะเลในแถบเขตร้อน จนอาจเรียกได้ว่าเป็นสัตว์เลี้ยงลูกด้วยนมที่สามารถพบเห็นได้ทั่วโลกมากที่สุดนอกเหนือจากมนุษย์"
            ,"The killer whale or orca (Orcinus orca) is a toothed whale belonging to the oceanic dolphin family, of which it is the largest member. Killer whales have a diverse diet, although individual populations often specialize in particular types of prey. Some feed exclusively on fish, while others hunt marine mammals such as seals and other species of dolphin. They have been known to attack baleen whale calves, and even adult whales. Killer whales are apex predators, as no animal preys on them. A cosmopolitan species, they can be found in each of the world's oceans in a variety of marine environments, from Arctic and Antarctic regions to tropical seas, absent only from the Baltic and Black seas, and some areas of the Arctic Ocean."
        )
        lifeList.add(life)
    }

    companion object {
        private const val TAG = "TAG"
    }
}
