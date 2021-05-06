package si.src.naloga;

import si.src.naloga.imenik.TelefonskiImenik;
import si.src.naloga.kontakt.Kontakt;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException{

        TelefonskiImenik telefonskiImenik = new TelefonskiImenik();

        izpisiMenu();

        Scanner in = new Scanner(System.in);
        String akcija = "";

        // zanka za izris menija
        while (!"0".equals(akcija)) {
            akcija = in.next();

            switch (akcija) {
                case "1": {
                    telefonskiImenik.izpisiVseKontakte();
                    break;
                }
                case "2": {
                    Kontakt novo = new Kontakt();
                    
                    String test=in.nextLine();
                    
                    System.out.println("Dodaj kontakt v imenik!");
                    novo.setId(-1);

                    System.out.println("Ime: ");
                    novo.setIme(in.nextLine());

                    System.out.println("Priimek: ");
                    novo.setPriimek(in.nextLine());

                    System.out.println("Naslov: ");
                    novo.setNaslov(in.nextLine());

                    System.out.println("Elektronska posta: ");
                    novo.setElektronskaPosta(in.nextLine());

                    System.out.println("Telefon: ");
                    novo.setTelefon(in.nextLine());
                    
                    System.out.println("Mobilni telefon: ");
                    novo.setMobilniTelefon(in.nextLine());

                    System.out.println("Opomba: ");
                    novo.setOpomba(in.nextLine());

                    telefonskiImenik.dodajKontakt(novo);
                    System.out.println("Dodajanje osebe "+novo.getIme()+" "+novo.getPriimek() +" uspesno zakljuceno.");
                    break;
                }
                case "3": {
                    telefonskiImenik.izpisiVseKontakte();
                    System.out.print("Vpisite ID kontakta, ki ga zelite urediti:");
                    int idKontakta = Integer.parseInt(in.next());
                    System.out.println(idKontakta);
                    //TODO: if ne obstaja print urejanje neuspesno
                    
                    
                    Kontakt oseba = new Kontakt();
                    oseba.setId(idKontakta);
                    //System.out.println(oseba.getIme());
                    //Kontakt k = new Kontakt();
                    for(Kontakt kontakt : telefonskiImenik.getSeznam()){
                        if(oseba.getId() == kontakt.getId()){
                            oseba=kontakt;
                            //k=kontakt;
                        }
                    }
                    if(oseba.getIme() == null){
                        System.out.println("Kontakt ne obstaja, urejanje neuspesno!");
                        break;
                    }

                    String sprememba = "";
                    izpisiUrejanje();
                    while(!"0".equals(sprememba)){
                        sprememba=in.next();
                        if(sprememba.equals("0")){
                            break;
                        }
                        switch(sprememba){
                            case "1": {
                                System.out.print("Novo ime:");
                                oseba.setIme(in.next());
                                break;
                            }
                            case "2": {
                                System.out.print("Nov priimek:");
                                oseba.setPriimek(in.next());
                                break;
                            }
                            case "3": {
                                System.out.print("Nov naslov:");
                                oseba.setNaslov(in.next());
                                break;
                            }
                            case "4": {
                                System.out.print("Nova elektronska posta:");
                                oseba.setElektronskaPosta(in.next());
                                break;
                            }
                            case "5": {
                                System.out.print("Nov telefon:");
                                oseba.setTelefon(in.next());
                                break;
                            }
                            case "6": {
                                System.out.print("Nov mobilni telefon:");
                                oseba.setMobilniTelefon(in.next());
                                break;
                            }
                            case "7": {
                                System.out.print("Nova opomba:");
                                oseba.setOpomba(in.next());
                                break;
                            }
                            case "0": {
                                break;
                            }
                            default:{
                                System.out.println("Napacna izbira!!!");
                                break;
                            }
                        }
                        izpisiUrejanje();
                    }
                    telefonskiImenik.urediKontakt(oseba);

                    System.out.println("Urejanje kontakta zakljuceno!");
                    //System.out.println(oseba.toString());
                    break;
                }
                case "4": {
                    telefonskiImenik.izpisiVseKontakte();
                    System.out.print("Prosim napisite ID kontakta, ki ga zelite izbrisati:");
                    int idKontakta = in.nextInt();
                    int truid= -1;
                    for(Kontakt kontakt : telefonskiImenik.getSeznam()){
                        if(idKontakta == kontakt.getId()){
                            truid=idKontakta;
                            //k=kontakt;
                        }
                    }
                    if(truid == -1){
                        System.out.println("Kontakt z id: "+idKontakta+" ne obstaja. Brisanje neuspesno.");
                        break;
                    }

                    telefonskiImenik.izbrisiKontaktPoId(idKontakta);
                    System.out.println("Brisanje zakljuceno!");
                    break;
                }
                case "5": {
                    telefonskiImenik.izpisiVseKontakte();
                    System.out.print("Prosim napisite ID kontakta, ki ga zelite izpisati:");
                    int idKontakta = in.nextInt();
                    telefonskiImenik.izpisiKontaktZaId(idKontakta);
                    break;
                }
                case "6": {
                    telefonskiImenik.izpisiSteviloKontaktov();
                    break;
                }
                case "7": {
                    telefonskiImenik.serializirajSeznamKontaktov();
                    break;
                }
                case "8": {
                    System.out.println("Vpisite pot do .ser datoteke:");
                    String pot = in.next();
                    telefonskiImenik.naloziSerializiranSeznamKontakotv(pot);
                    break;
                }
                case "9": {
                    telefonskiImenik.izvoziPodatkeVCsvDatoteko();
                    break;
                }
                case "0": {
                    System.exit(0);
                    break;
                }
                case "42":{
                    System.out.println("Bravo, nasel si pot do skritega koticka, zdaj pa nazaj na delo.");
                    break;
                }
                default:{
                    System.out.println("Napacna izbira!!!");
                    break;
                }
            }

            izpisiMenu();
        }
        in.close();	
    }

    /**
     * Uporabniku izpisemo menu
     */
    public static void izpisiMenu() {

        System.out.println("");
        System.out.println("");
        System.out.println("Aplikacija telefonski imenik:");
        System.out.println("-----------------------------------");
        System.out.println("Akcije:");
        System.out.println("1 - izpisi vse kontakte v imeniku");
        System.out.println("2 - dodaj kontakt v imenik");
        System.out.println("3 - uredi obstojeci kontakt");
        System.out.println("4 - brisi kontakt po ID-ju");
        System.out.println("5 - izpisi kontakt po ID-ju");
        System.out.println("6 - izpisi stevilo vseh kontaktov");
        System.out.println("7 - Shrani kontakte na disk (serializacija)");
        System.out.println("8 - Preberi kontake iz serializirano datoteke");
        System.out.println("9 - Izvozi kontakte v csv");
        System.out.println("");
        System.out.println("0 - Izhod iz aplikacije");
        System.out.println("----------------------------------");
        System.out.println("Akcija: ");


    }
    public static void izpisiUrejanje() {
        System.out.println("");
        System.out.println("");
        System.out.println("Urejanje kontakta");
        System.out.println("-----------------------------------");
        System.out.println("Akcije urejanja:");
        System.out.println("1 - Ime");
        System.out.println("2 - Priimek");
        System.out.println("3 - Naslov");
        System.out.println("4 - Elektronsla posta");
        System.out.println("5 - Telefon");
        System.out.println("6 - Mobilni telefon");
        System.out.println("7 - Opomba");
        System.out.println("");
        System.out.println("0 - Izhod iz urejanja kontakta");
        System.out.println("----------------------------------");
        System.out.println("Akcija: ");    
    }
    
}
