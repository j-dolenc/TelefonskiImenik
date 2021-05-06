package si.src.naloga.imenik;

import si.src.naloga.kontakt.Kontakt;

import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import java.io.*;

public class TelefonskiImenik {

    private List<Kontakt> seznamKontaktov;
    private Connection conn= null;
    private String url = "jdbc:sqlite:TelefonskiImenik.db";
    public List<Kontakt> getSeznam(){
        return seznamKontaktov;
    }

    public TelefonskiImenik() throws ClassNotFoundException{
        
        seznamKontaktov = new ArrayList<>();
        Class.forName("org.sqlite.JDBC");
        
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(30);
            //ResultSet res = conn.getMetaData().getTables(null, null, null, null);
            
            //statement.executeUpdate("create table imenik (id integer primary key, ime string, priimek string, naslov string, elektronskaPosta string, telefon string, mobilniTelefon string, opomba string)");
            //statement.executeUpdate("insert into imenik values (NULL, 'Peter', 'asdak', 'asdasd', 'asdadk@gmail.com', '123241231', '+3867092325', 'aaaaa hehehe')");
            ResultSet res = statement.executeQuery("select * from imenik");
            //System.out.println(res);
            while(res.next()){
                // System.out.println(res.getString("TABLE_NAME"));
                //System.out.println(res);
                Kontakt oseba = new Kontakt();
                oseba.setId(res.getInt("id"));
                oseba.setIme(res.getString("ime"));
                oseba.setPriimek(res.getString("priimek"));
                oseba.setNaslov(res.getString("naslov"));
                oseba.setElektronskaPosta(res.getString("elektronskaPosta"));
                oseba.setTelefon(res.getString("telefon"));
                oseba.setMobilniTelefon("mobilniTelefon");
                oseba.setOpomba("opomba");
                seznamKontaktov.add(oseba);
                
                // System.out.println(res.getString("ime"));
                // System.out.println(res.getString("priimek"));
                // System.out.println(res.getString("naslov"));
                // System.out.println(res.getString("elektronskaPosta"));
                // System.out.println(res.getString("telefon"));
                // System.out.println(res.getString("mobilniTelefon"));
                // System.out.println(res.getString("opomba"));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        finally {
            try{
                if(conn != null) conn.close();
            }
            catch(SQLException e){
                System.err.println(e);
            }
        }
    }

    // public List<Kontatk> getTelefonskiImenik() {
    //     return seznamKontaktov;
    // }
    /**
     * Metoda izpise vse kontakte
     */
    public void izpisiVseKontakte() {
        for(Kontakt kontakt : seznamKontaktov){
            System.out.println(kontakt.toString());
        }
        // try {
        //     conn = DriverManager.getConnection(url);
        //     Statement statement = conn.createStatement();
        //     statement.setQueryTimeout(30);
        //     //ResultSet res = conn.getMetaData().getTables(null, null, null, null);
            
        //     //statement.executeUpdate("create table imenik (id integer primary key, ime string, priimek string, naslov string, elektronskaPosta string, telefon string, mobilniTelefon string, opomba string)");
        //     //statement.executeUpdate("insert into imenik values (NULL, 'Peter', 'asdak', 'asdasd', 'asdadk@gmail.com', '123241231', '+3867092325', 'aaaaa hehehe')");
        //     ResultSet res = statement.executeQuery("select * from imenik");
        //     //System.out.println(res);
        //     while(res.next()){
        //         // System.out.println(res.getString("TABLE_NAME"));
        //         //System.out.println(res);
                
        //         //seznamKontaktov.add(oseba);
                
        //         System.out.println(res.getString("ime"));
        //         System.out.println(res.getString("priimek"));
        //         System.out.println(res.getString("naslov"));
        //         System.out.println(res.getString("elektronskaPosta"));
        //         System.out.println(res.getString("telefon"));
        //         System.out.println(res.getString("mobilniTelefon"));
        //         System.out.println(res.getString("opomba"));
        //     }
        // }
        // catch(SQLException e){
        //     System.err.println(e.getMessage());
        // }
        // finally {
        //     try{
        //         if(conn != null) conn.close();
        //     }
        //     catch(SQLException e){
        //         System.err.println(e);
        //     }
        // }
        
    }

    /**
     * Metoda doda nov kontakt v imenik
     *
     * onemogocimo dodajanje dupliciranega kontakta
     */
    public void dodajKontakt(Kontakt nova) {
        //Scanner sc = new Scanner(System.in);
        seznamKontaktov.add(nova);
        //dodamo kontakt še v bazo

        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(30);
            //Statement statement = conn.createStatement();
            //statement.setQueryTimeout(30);
            //ResultSet res = conn.getMetaData().getTables(null, null, null, null);
            statement.executeUpdate("insert into imenik values (NULL, '"
                                                                +nova.getIme()+"', '"
                                                                +nova.getPriimek()+"', '"
                                                                +nova.getNaslov()+"', '"
                                                                +nova.getElektronskaPosta()+"', '"
                                                                +nova.getTelefon()+"', '"
                                                                +nova.getMobilniTelefon()+"', '"
                                                                +nova.getOpomba()+"')");
            //ResultSet res = statement.executeQuery("select * from kontakti");
            ResultSet res = statement.executeQuery("select id from imenik where ime='"+ nova.getIme() +"'");
            //System.out.println("lalala");
            while(res.next()){
                // System.out.println(res.getString("TABLE_NAME"));
                //System.out.println(res);
                nova.setId(res.getInt("id"));
                //System.out.println(nova.getId());
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        finally {
            try{
                if(conn != null) conn.close();
            }
            catch(SQLException e){
                System.err.println(e);
            }
        }

        //System.out.println("Metoda se ni implementirana");
    }

    /**
     * Metoda popravi podatke na obstojecem kontaktu
     * ID kontakta ni mogoce spreminjati
     */
    public void urediKontakt(Kontakt urejen) {
        //kontakt iz imenika je urejen v Main, tukaj bom dodal urejanje v bazi...
        //System.out.println("Metoda se ni implementirana");
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(30);
            //Statement statement = conn.createStatement();
            //statement.setQueryTimeout(30);
            //ResultSet res = conn.getMetaData().getTables(null, null, null, null);
            
            //ResultSet res = statement.executeQuery("select * from kontakti");
            statement.executeUpdate("update imenik set ime='"+urejen.getIme()+"' where id="+urejen.getId()+"");
            statement.executeUpdate("update imenik set priimek='"+urejen.getPriimek()+"' where id="+urejen.getId()+"");
            statement.executeUpdate("update imenik set naslov='"+urejen.getNaslov()+"' where id="+urejen.getId()+"");
            statement.executeUpdate("update imenik set elektronskaPosta='"+urejen.getElektronskaPosta()+"' where id="+urejen.getId()+"");
            statement.executeUpdate("update imenik set telefon='"+urejen.getTelefon()+"' where id="+urejen.getId()+"");
            statement.executeUpdate("update imenik set mobilniTelefon='"+urejen.getMobilniTelefon()+"' where id="+urejen.getId()+"");
            statement.executeUpdate("update imenik set opomba='"+urejen.getOpomba()+"' where id="+urejen.getId()+"");
            //System.out.println("lalala");
            // while(res.next()){
            //     // System.out.println(res.getString("TABLE_NAME"));
            //     //System.out.println(res);
            //     nova.setId(res.getInt("id"));
            //     System.out.println(nova.getId());
            // }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        finally {
            try{
                if(conn != null) conn.close();
            }
            catch(SQLException e){
                System.err.println(e);
            }
        }

    }

    /**
     * Brisanje kontakta po ID-ju
     */
    public void izbrisiKontaktPoId(int id) {
        //System.out.println("Metoda se ni implementirana");
        int indeks = 0;
        for(Kontakt kontakt : seznamKontaktov){
            if(kontakt.getId() == id){
                System.out.println("Izbrisan kontakt: "+kontakt.toString());
                break;
            }
            indeks++;
        }
        seznamKontaktov.remove(indeks);

        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(30);
            
            statement.executeUpdate("delete from imenik where id='"+id+"'");
            
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        finally {
            try{
                if(conn != null) conn.close();
            }
            catch(SQLException e){
                System.err.println(e);
            }
        }
        izpisiVseKontakte();
    }

    /**
     * Izpis kontakta po ID-ju
     */
    public void izpisiKontaktZaId(int id) {
        Kontakt isci = new Kontakt();
        for(Kontakt kontakt : seznamKontaktov){
            if(kontakt.getId() == id){
                isci = kontakt;
                //System.out.println(kontakt.toString());
            }
        }
        if(isci.getIme() == null){
            System.out.println("Oseba z id: " +id + " ne obstaja.");
        }
        else{
            System.out.println(isci.toString());
        }  
        //zakljuceno
    }

    /**
     * Izpis stevila kontaktov
     */
    public void izpisiSteviloKontaktov() {
        System.out.println("V tem imeniku imate shranjenih " + seznamKontaktov.size() + " kontaktov.");
        //System.out.println("Metoda se ni implementirana");
    }

    /**
     * Serializiraj seznam kontaktov na disk.
     * Ime datoteke naj bo "kontakti.ser"
     */
    public void serializirajSeznamKontaktov() {
        try {
            //tukaj nevem kako nastavit pot da mi da access??
            File file = new File("./src/main/resources/kontakti.ser");
            if(file.createNewFile()){
                System.out.println("Ustvarjena datoteka: "+ file.getName());
            } 
            else{
                System.out.println("Datoteka ze obstaja");
            }

            FileOutputStream file2 = new FileOutputStream(file.getAbsolutePath());
            ObjectOutputStream out = new ObjectOutputStream(file2);
            out.writeObject(seznamKontaktov);
            // for(Kontakt kontakt : seznamKontaktov){
            //     out.writeObject(kontakt);
            // }
            out.close();
            file2.close();
            System.out.println("Serializirani podatki so shranjeni v datoteki kontakti.ser"); 
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        //System.out.println("Metoda se ni implementirana");
    }

    // public File ustvariDatoteko(){
    //     try {
    //         File file = new File("kontakti.ser");
    //         if(file.createNewFile()){
    //             System.out.println("Ustvarjena datoteka: "+ file.getName());
    //             return file;
    //         } 
    //         else{
    //             System.out.println("Datoteka ze obstaja");
    //         }

    //     } catch (IOException e) {
    //         //TODO: handle exception
    //         System.err.println(e.getMessage());
    //     }
    // }
    /**
     * Pereberi serializiran seznam kontaktov iz diska
     */
    public void naloziSerializiranSeznamKontakotv(String pot) {
        //System.out.println("Metoda se ni implementirana");
        //deserializacija

        try {
            
            FileInputStream in = new FileInputStream(pot);
            ObjectInputStream filein = new ObjectInputStream(in);
            //Object obj;
            List<Kontakt> deser = (List<Kontakt>)filein.readObject();
            //obj = filein.readObject();
            filein.close();
            //System.out.println(obj.toString());
            // for(Kontakt kontakt : deser){
            //     System.out.println(kontakt.toString());
            // }
            //v bazo pa pogledat ce ze obstaja in ce ne ga dodaj
            //pa isto z seznamKontaktov
            List<Kontakt> neobstojeci = new ArrayList<>();
            for(Kontakt kontakt : deser){
                //System.out.println(kontakt.toString());
                int obstaja =0;
                for(Kontakt stari : seznamKontaktov){

                    if(stari.equals(kontakt)){
                        obstaja = 1;
                    }
                }
                if(obstaja == 0){
                    neobstojeci.add(kontakt);
                }
            }
            for(Kontakt kontakt : neobstojeci){
                //System.out.println(kontakt.toString());
                dodajKontakt(kontakt);
            }

        } catch (IOException e) {
            //TODO: handle exception
            System.err.println(e.getMessage());
        }
        catch(ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
        
        

    }

    /**
     * Izvozi seznam kontaktov v CSV datoteko.
     * Naj uporabnik sam izbere ime izhodne datoteke.
     */
    public void izvoziPodatkeVCsvDatoteko() {
        System.out.println("Metoda se ni implementirana");
    }
}
