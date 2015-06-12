package sticfit3.sticfit3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by kevin on 23/04/2015.
 */
public class Info extends MainActivity {

        private EditText emailSubject = null;
        private EditText emailBody = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.info);

            emailSubject = (EditText) findViewById(R.id.subject);
            emailBody = (EditText) findViewById(R.id.emailBody);

            //Button sent envoi un mail
            // toEmail ecrit par défault , contrairement au subject et au message
            final Button sent = (Button) findViewById(R.id.sent);
            sent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    String toEmail = "kevin.marques.joaquim@gmail.com";
                    String subject = emailSubject.getText().toString();
                    String message = emailBody.getText().toString();

                    //Intente Action envoi
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[] { toEmail });
                    email.putExtra(Intent.EXTRA_SUBJECT, subject);
                    email.putExtra(Intent.EXTRA_TEXT, message);

                    // need this to prompts email client only
                    email.setType("message/rfc822");

                    startActivity(Intent.createChooser(email, "Choisir votre Email client"));


                }
            });


        }

        }

