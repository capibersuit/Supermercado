package ar.gov.chris.server.clases;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ar.gov.chris.server.excepciones.ExcepcionIO;


public class CorreoE {
	
	
	public  String Username;// ="alarma.vencimientos";
    public  String PassWord;// = "laquevenga";
    public String Mensage;// = "Este es un mensaje de prueba enviado desde eclipse ";
    public String To;// = "christianferro79@gmail.com";//,mariela_amil@hotmail.com";
//    static String To = "reclarinette2002@yahoo.com.ar";
    

    public String Subject;// = "ATENCI�N: Productos con vencimiento pr�ximo...";// + new Date().toString();
    
  
     public void SendMail(String cuerpo_mail) throws RuntimeException {
		System.out.println("Entro al sendmail");
		
//	    LectorPropiedades.

		try {
			Username = "alarma.vencimientos";//LectorPropiedades.obtener_valor("Username");					
			 PassWord = "laquevenga";//LectorPropiedades.obtener_valor("PassWord");
		     To = LectorPropiedades.obtener_valor("To");	    
		     Subject = LectorPropiedades.obtener_valor("Subject");
		    
			
			
			
		} catch (ExcepcionIO e1) {
			throw new RuntimeException(e1.getMessage());
		}
    	Mensage= cuerpo_mail;

    	
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Username, PassWord);
                    }
                });

        try {

        	MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Username));
            
            InternetAddress[] dir_receptor= separar_direcciones(To);
            
            message.setRecipients(Message.RecipientType.TO, dir_receptor);
            
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(To));
            message.setSubject(Subject);
            message.setText(Mensage);                         
            message.setText(Mensage, "utf-8", "html");

            Transport.send(message);
    		System.out.println("Salgo del sendmail y supuestamente envió el correo !!!");


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
     
     
     
     /** Env�a un correo electr�nico con el MIME type de tipo texto y el subtipo
 	 * indicado.
 	 * @param from El From.
 	 * @param receptores Direcciones de los receptores, separadas por coma.
 	 * @param receptores_bcc Direcciones de recpetores con copia oculta,
 	 * separados por coma. Si es <code>null</code> no se env�a copia oculta
 	 * a nadie.
 	 * @param subject El subject.
 	 * @param texto El cuerpo del mensaje.
 	 * @param headers Arreglo de headers adicionales. Puede ser
 	 * <code>null</code> si no se van a usar headers adicionales.
 	 * @param charset Codificaci�n del texto pasado c�mo par�metro 
 	 * (por ej. "iso-8859-1", "utf-8").
 	 * @param subtipo_mime Subtipo del MIME type texto (por ej.: "plain", "html",
 	 * etc.).
 	 */
 	static public void enviar(String from, String receptores, String receptores_bcc,
 		String subject, String texto, String headers[], String charset,
 		String subtipo_mime) {
 	 Properties props= new Properties();
 	 props.setProperty("mail.transport.protocol", "SMTP");
 	 props.setProperty("mail.smtp.host", "localhost");
 	 Session sesion= Session.getInstance(props);
 	 try {
 		InternetAddress dir_from= new InternetAddress(from);
 		InternetAddress[] dir_receptor= separar_direcciones(receptores);

 		MimeMessage msj= new MimeMessage(sesion);
 		msj.setFrom(dir_from);
 		msj.setRecipients(Message.RecipientType.TO, dir_receptor);
 		
 		// Manejo la copia oculta.
 		if (receptores_bcc!=null) {
 			InternetAddress[] dir_receptor_bcc= 
 				separar_direcciones(receptores_bcc);
 			msj.setRecipients(Message.RecipientType.BCC, dir_receptor_bcc);
 		}
 		
 		msj.setSubject(subject);
 		// Agrego los headers.
 		for (int i= 0; headers!=null && i<headers.length; i++)
 			msj.addHeaderLine(headers[i]);
 		msj.setText(texto, charset, subtipo_mime);
 		
 		Transport.send(msj);
 	 } catch (MessagingException ex) {
 		System.out.println("No se pudo mandar mensaje: "+
 			ex.getMessage());
 	 }
 	}

 	/** Toma una lista de direcciones separadas por coma y devuelve un arreglo
 	 * de {@link InternetAddress}.
 	 * @param receptores La lista de receptores separados por coma.
 	 * @return �dem.
 	 * @throws AddressException Si alguna direcci�n no es tiene el formato
 	 * correcto.
 	 */
 	private static InternetAddress[] separar_direcciones(String receptores)
 		throws AddressException {
 	 String[] destinatarios= receptores.split(",");
 	 InternetAddress[] dir_receptor= new InternetAddress[destinatarios.length];
 	 for (int i = 0; i < destinatarios.length; i++) {
 		dir_receptor[i]= new InternetAddress(destinatarios[i]);
 	 }
 	 return dir_receptor;
 	}
}
