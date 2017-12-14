import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 

public class testBDD {
	//****************** ATTRIBUTS ************************//
	private String driver;
	private String hostBdd ;
	private String loginBdd;
	private String pwdBdd;
	private Connection connection;
	
	//REQUETTES
	PreparedStatement instructionInsert;
	PreparedStatement instructionUnom;
	PreparedStatement instructionUtatoo;
	PreparedStatement instructionUnaiss;
	PreparedStatement instructionUtaille;
	PreparedStatement instructionDelete;
	
	//****************** CONSTRUCTEUR ************************//
	public testBDD(String driver, String hostBdd, String loginBdd, String pwdBdd) {
		//CONNECTION
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(hostBdd, loginBdd, pwdBdd);
			
			Statement instruction = connection.createStatement();
			int resultat = instruction.executeUpdate("CREATE TABLE IF NOT EXISTS tableTest("
															+ "id INT AUTO_INCREMENT NOT NULL,"
															+ "nom VARCHAR(50) NOT NULL,"
															+ "tatoo INT NOT NULL,"
															+ "naiss DATE NOT NULL,"
															+ "taille DOUBLE,PRIMARY KEY(id))");
			ResultSet reponse =instruction.executeQuery("SELECT user FROM mysql.user WHERE user = 'rt'");
			String rep = null;
			while(reponse.next()){
				 rep = reponse.getString("user");
			}
			if(rep==null)
			{				
				instruction.executeUpdate("CREATE USER 'rt'@'localhost' IDENTIFIED by 'pwtest'");
				instruction.executeUpdate("GRANT ALL PRIVILEGES ON `basetest`.* TO 'rt'@'localhost'WITH GRANT OPTION");
			}
			connection.close();
			connection = DriverManager.getConnection(hostBdd, "rt", "pwtest");
			//REQUETTES PREPARES
			this.instructionInsert = this.connection.prepareStatement("INSERT INTO tableTest(`nom`, `tatoo`, `naiss`, `taille`) VALUES(?,?,?,?)");
			this.instructionUnom = this.connection.prepareStatement("UPDATE tabletest SET nom = ? WHERE id = ?");
			this.instructionUtatoo = this.connection.prepareStatement("UPDATE tabletest SET tatoo = ? WHERE id = ?");
			this.instructionUnaiss = this.connection.prepareStatement("UPDATE tabletest SET naiss = ? WHERE id = ?");
			this.instructionUtaille = this.connection.prepareStatement("UPDATE tabletest SET taille = ? WHERE id = ?");
			this.instructionDelete= this.connection.prepareStatement("DELETE FROM tabletest WHERE id = ?");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	//****************** METHODES ************************//
	public void selectData() {
		try {
			Statement instruction = connection.createStatement();
			ResultSet resultat = instruction.executeQuery("SELECT * FROM tableTest");
			while(resultat.next()) {
				System.out.println(resultat.getInt("id")+"/"+resultat.getString("nom")+"/"+resultat.getDate("naiss")+"/"+resultat.getDouble("taille"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertData(String pNom, int pTatoo, String pNaiss, Double pTaille) {
		try {
			Date pNaissD =Date.valueOf(pNaiss);
			
			this.instructionInsert.setString(1, pNom);
			this.instructionInsert.setInt(2, pTatoo);
			this.instructionInsert.setDate(3, pNaissD);
			this.instructionInsert.setDouble(4, pTaille);
			this.instructionInsert.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public void deleteData(int id) {
		try {
			this.instructionDelete.setInt(1, id);
			this.instructionDelete.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateDataNom(String pValue, int id) {
		try {
			this.instructionUnom.setInt(2, id);
			this.instructionUnom.setString(1, pValue);
			this.instructionUnom.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateDataTatoo(String pValue, int id) {
		try {
			this.instructionUtatoo.setInt(2, id);
			this.instructionUtatoo.setInt(1, Integer.parseInt(pValue));
			this.instructionUtatoo.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateDataNaiss(String pValue, int id) {
		try {
			Date pNaissD =Date.valueOf(pValue);
			this.instructionUnaiss.setInt(2, id);
			this.instructionUnaiss.setDate(1, pNaissD);
			this.instructionUnaiss.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateDataTaille(String pValue, int id) {
		try {
			this.instructionUtaille.setInt(2, id);
			this.instructionUtaille.setDouble(1, Double.parseDouble(pValue));
			this.instructionUtaille.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateData(String pChp, String pValue, int id) {
		switch (pChp) {
        	case "naiss":
        		this.updateDataNaiss(pValue, id);
                break;
        	case "tatoo":
        		this.updateDataTatoo(pValue, id);
                break;
        	case "taille":
        		this.updateDataTaille(pValue, id);
                break;
        	case "nom":
            	this.updateDataNom(pValue, id);
            	break;
        }
	}
}
