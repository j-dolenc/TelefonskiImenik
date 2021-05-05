package si.src.naloga.imenik;

import si.src.naloga.kontakt.Kontakt;

import java.util.ArrayList;
import java.util.List;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

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
                
                System.out.println(res.getString("ime"));
                System.out.println(res.getString("priimek"));
                System.out.println(res.getString("naslov"));
                System.out.println(res.getString("elektronskaPosta"));
                System.out.println(res.getString("telefon"));
                System.out.println(res.getString("mobilniTelefon"));
                System.out.println(res.getString("opomba"));
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
            System.out.println("lalala");
            while(res.next()){
                // System.out.println(res.getString("TABLE_NAME"));
                //System.out.println(res);
                nova.setId(res.getInt("id"));
                System.out.println(nova.getId());
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

    }

    /**
     * Izpis kontakta po ID-ju
     */
    public void izpisiKontaktZaId(int id) {
        for(Kontakt kontakt : seznamKontaktov){
            if(kontakt.getId() == id){
                System.out.println(kontakt.toString());
            }
        }
        //System.out.println("Metoda se ni implementirana");
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
        System.out.println("Metoda se ni implementirana");
    }

    /**
     * Pereberi serializiran seznam kontaktov iz diska
     */
    public void naloziSerializiranSeznamKontakotv() {
        System.out.println("Metoda se ni implementirana");
    }

    /**
     * Izvozi seznam kontaktov v CSV datoteko.
     * Naj uporabnik sam izbere ime izhodne datoteke.
     */
    public void izvoziPodatkeVCsvDatoteko() {
        System.out.println("Metoda se ni implementirana");
    }
}
