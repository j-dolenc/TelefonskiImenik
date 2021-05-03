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

    public List<Kontakt> getSeznam(){
        return seznamKontaktov;
    }

    public TelefonskiImenik() /*throws ClassNotFoundException*/{
        
        seznamKontaktov = new ArrayList<>();
        // Connection conn= null;
        // try {
        //     Class.forName("org.sqlite.JDBC");
        //     String url = "jdbc:sqlite:TelefonskiImenik.db";
        //     conn = DriverManager.getConnection(url);
        //     Statement statement = conn.createStatement();
        //     statement.setQueryTimeout(30);
            
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
        //System.out.println("Metoda se ni implementirana");
    }

    /**
     * Metoda doda nov kontakt v imenik
     *
     * onemogocimo dodajanje dupliciranega kontakta
     */
    public void dodajKontakt(Kontakt e) {
        //Scanner sc = new Scanner(System.in);
        seznamKontaktov.add(e);
        //System.out.println("Metoda se ni implementirana");
    }

    /**
     * Metoda popravi podatke na obstojecem kontaktu
     * ID kontakta ni mogoce spreminjati
     */
    public void urediKontakt(Kontakt e) {
        //kontakt iz imenika je urejen v Main, tukaj bom dodal urejanje v bazi...
        //System.out.println("Metoda se ni implementirana");
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
        int st = 0;
        for(Kontakt kontakt : seznamKontaktov){
            st++;
        }
        System.out.println("V tem imeniku imate shranjenih " + st + " kontaktov.");
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
