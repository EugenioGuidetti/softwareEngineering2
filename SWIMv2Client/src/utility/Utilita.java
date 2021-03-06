package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utilita {

	public static final long DIMENSIONE_MAX_IMG = 700;

	public static final String IMG_PATH_BASE ="/SWIMv2Client/Immagini/";

	public static final String AVATAR_PATH_BASE ="/SWIMv2Client/Immagini/Avatar/";

	public static final String ICONA_PATH_BASE ="/SWIMv2Client/Immagini/Icone/";

	public static final String EMAIL_ADMIN = "swimv2.polimi@gmail.com";

	public static final String SESSO_MASCHIO = "Maschio";
	
	public static final String SESSO_FEMMINA = "Femmina";
	
	public static final String USER_DEFAULT_AVATAR_MASCHIO = 
			"/SWIMv2Client/Immagini/Avatar/userMaschio.png";
	
	public static final String USER_DEFAULT_AVATAR_FEMMINA = 
			"/SWIMv2Client/Immagini/Avatar/userFemmina.png";
	
	public static final String ADMIN_DEFAULT_AVATAR = 
			"/SWIMv2Client/Immagini/admin.png";
	
	public static final String NICKNAME_PATTERN = "^[a-zA-Z0-9]{1,25}$";
	
	public static final String EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
			"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

	public static final String OGGETTO_MAIL_REGISTRAZIONE = "Registrazione completata - SWIMv2";

	public static final String OGGETTO_MAIL_MODIFICA = "Modifica completata - SWIMv2";

	public static final String MESSAGGIO_REGISTRAZIONE = "la registrazione � stata completata correttamente.\n\n";

	public static final String MESSAGGIO_MODIFICA = "la modifica dei dati � stata eseguita correttamente.\n\n";

	public static final String FROM = "swimv2.polimi@gmail.com";

	public static final String HOST = "localhost";

	public static final String TIPO_UPLOAD_ICONA = "icona";
	
	public static final String TIPO_UPLOAD_REGISTRAZIONE = "rAvatar";
	
	public static final String TIPO_UPLOAD_MODIFICA = "nAvatar";

	private Utilita() {
		super();
	}

	/**
	 * Il metodo serve per inviare una email all'indirizzo passato come parametro utilizzando la mail del sistema
	 * 
	 * @param nickname	associato allo user proprietario dell'indirizzo email 
	 * @param password	dello user
	 * @param cognome	dello user
	 * @param nome		dello user
	 * @param email		a cui inviare il messaggio di posta elettronica
	 * @param tipoOggetto	tipo di stringa da specificare nell'oggetto della mail
	 * @param tipoMessaggio	tipo di stringa da specificare nel corpo della mail
	 */
	public static void sendMail(String nickname, String password, String cognome, String nome, String email, String tipoOggetto, String tipoMessaggio){

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", Utilita.HOST);

		InitialContext ctx;
		try {
			ctx = new InitialContext();
			Session session = (Session) ctx.lookup("java:/Mail");

			MimeMessage message = new MimeMessage( session );
			try {
				message.setFrom( new InternetAddress(Utilita.FROM) );

				message.addRecipient(RecipientType.TO, new InternetAddress(email));
				message.setSubject(tipoOggetto);
				message.setText( 
						"Ciao "+ nome + " " + cognome +	",\n" +
								tipoMessaggio +
								"Queste sono le tue credenziali di accesso attuali:\n" +
								"nickname: " + nickname +
								"\npassword: " + password +
								"\n\n Puoi cambiare la tua password in qualsiasi momento dalla tua area personale." +
						"\n\n\n@admin");
				Transport.send( message );
			}
			catch (MessagingException ex){
				System.err.println("Errore nell'invio della mail:" + ex);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Il metodo server per effettuare l'upload di un file
	 * 
	 * @param inputFile		file di cui si vuole effettuare l'upload
	 * @param tipoUpload	tipo di file da uploadare {icona, avatar}
	 * @param nomeFileUpload	nome del file da uploadare
	 * @param destinazione		percorso del server in cui andare a memorizzare il file da uploadre
	 * 
	 * @return		restituisce il percorso relativo al progetto, del file uploadato
	 * 
	 * @throws IOException
	 */
	public static String uploadFile(File inputFile, String tipoUpload, String nomeFileUpload, String destinazione) throws IOException{

		File output;
		FileInputStream inputStream;
		FileOutputStream outputStream;
		String stringaRitorno = null;

		//controllo le dimensioni del file
		long dimensioneFileKB = inputFile.length() / 1024;
		if(dimensioneFileKB > DIMENSIONE_MAX_IMG){
			//immagine troppo grande
			throw new IOException();
		}
		else{
			//effettuo l'upload
			
			//creo gli stream
			output = new File(destinazione);
			inputStream = new FileInputStream(inputFile);
			outputStream = new FileOutputStream(output);

			//passaggio dei byte dalla sorgente alla destinazione
			while(inputStream.available() > 0) {
				outputStream.write(inputStream.read());
			}
			inputStream.close();
			outputStream.close();

			//restituisco la stringa da memorizzare nel DB
			if(tipoUpload.equals(TIPO_UPLOAD_ICONA)){
				//ho fatto l'upload di un'icona
				stringaRitorno = ICONA_PATH_BASE + nomeFileUpload.replace("\\", "");
			}
			else{
				//ho fatto l'upload di un avatar
				stringaRitorno = AVATAR_PATH_BASE + nomeFileUpload.replace("\\", "");
			}

		}
		return stringaRitorno;
	}

	
	/**
	 * Il metodo serve per generare una stringa di 10 caratteri casuali
	 * 
	 * @return	una stringa alfanumerica composta da 10 caratteri
	 */
    public static String generatePassword() {
    	String alfabeto = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz";
        Random random = new Random(System.currentTimeMillis());
        int lenght = 10;
        
        StringBuilder sb = new StringBuilder(lenght);
        
        for (int i = 0; i < lenght; i++) {
            sb.append(alfabeto.charAt(random.nextInt(alfabeto.length())));
        }
        return sb.toString();
    }
    
    /**
     * Il metodo controlla che non si possa accedere a pagine di una sessione utente senza aver eseguito prima 
     * l'operazione di login
     * 
     * @param request	
     * @param response
     * 
     * @return	restituisce true, se esiste una sessione utente instaurata con quel nickname; false altrimenti 
     */
    public static boolean controlloSessione(HttpServletRequest request, HttpServletResponse response){
    	if(request.getSession().getAttribute("nickname") == null ){
    		try {
				response.sendRedirect("index.jsp");
				return false;
			} catch (IOException e) {
				return false;
			}
    	}
    	return true;    	
    }
}
