
public class Main {

	public static void main(String[] args) {
		//CONNECTION
		testBDD bdd = new testBDD("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/basetest", "root", "root");
		
	    //INSERTION
		//bdd.insertData("test", 4523, "2015-03-31", 12.3);
		
		//UPDATE 
		//bdd.updateData("tatoo","46424", 9);
		
		//DELETE
		//bdd.deleteData(9);
		
		//LECTURE
		bdd.selectData();
	}

}
