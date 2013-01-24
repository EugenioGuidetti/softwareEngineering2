package utility;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Utilita {
	
	public static final long DIMENSIONE_MAX_IMG = 700;
	
	public static final String IMG_PATH_BASE ="/SWIMv2Client/Immagini/";
	
	public static final String AVATAR_PATH_BASE ="/SWIMv2Client/Immagini/Avatar/";
	
	public static final String ICONA_PATH_BASE ="/SWIMv2Client/Immagini/Icone/";
	
	public static final String EMAIL_ADMIN = "swimv2.polimi@gmail.com";
	
	public static final String USER_DEFAULT_AVATAR = 
			"/SWIMv2Client/Immagini/user.png";
	public static final String ADMIN_DEFAULT_AVATAR = 
			"/SWIMv2Client/Immagini/admin.png";
	public static final String EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
			"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
	
	public static final String OGGETTO_MAIL_REGISTRAZIONE = "Registrazione completata - SWIMv2";
	
	public static final String OGGETTO_MAIL_MODIFICA = "Modifica completata - SWIMv2";
	
	public static final String MESSAGGIO_REGISTRAZIONE = "la registrazione è stata completata correttamente.\n\n";
	
	public static final String MESSAGGIO_MODIFICA = "la modifica dei dati è stata eseguita correttamente.\n\n";
	
	public static final String from = "swimv2.polimi@gmail.com";
	
	public static final String host = "localhost";

	private Utilita() {
		super();
	}
	
	
	public static void sendMail(String nickname, String password, String cognome, String nome, String email, String tipoOggetto, String tipoMessaggio){

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", Utilita.host);

		InitialContext ctx;
		try {
			ctx = new InitialContext();
			Session session = (Session) ctx.lookup("java:/Mail");

			MimeMessage message = new MimeMessage( session );
			try {
				message.setFrom( new InternetAddress(Utilita.from) );

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

}
