package com.example.proyecto;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender extends AsyncTask<Void, Void, Boolean> {
    private final Context context;
    private final String recipientEmail;
    private final String subject;
    private final String message;

    EmailSender(Context context, String recipientEmail, String subject, String message) {
        this.context = context;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        // Replace these credentials with your actual email credentials
        final String email = "2120300128@soy.utj.edu.mx";
        final String password = "faordqnqjvyfmmkj";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP host
        props.put("mail.smtp.port", "587"); // Replace with your SMTP port

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(email));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            return true; // Indicate success
        } catch (MessagingException e) {
            e.printStackTrace();
            return false; // Indicate failure
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Toast.makeText(context, "Correo enviado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al enviar el correo", Toast.LENGTH_SHORT).show();
        }
    }
}


