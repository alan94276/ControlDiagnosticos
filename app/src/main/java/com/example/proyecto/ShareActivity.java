package com.example.proyecto;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShareActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    EditText nombre, correo, tel,modelo,averia;
    Button enviar;
    private ImageView foto;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database2;
    static final String DATABASE_NAME = "share_info.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "shared_info";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_ISSUE = "issue";
    public static final String COLUMN_PHOTO_PATH = "photo_path";

    LinearLayout home, settings, share, about, logout, map, contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        databaseHelper = new DatabaseHelper(this);
        database2 = databaseHelper.getWritableDatabase();

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        about = findViewById(R.id.about);
        logout = findViewById(R.id.logout);
        settings = findViewById(R.id.settings);
        share = findViewById(R.id.share);
        map = findViewById(R.id.map);
        contacto = findViewById(R.id.contact);
        //llenado de informacion
        nombre = findViewById(R.id.edtNombreR);
        correo = findViewById(R.id.edtCorreoR);
        tel = findViewById(R.id.edtTelefonoR);
        modelo = findViewById(R.id.edtModelo);
        averia = findViewById(R.id.edtAveria);
        //botones
        enviar = findViewById(R.id.btnEnviar);
        foto = (ImageView) findViewById(R.id.imgFoto);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    insertDataToDatabase();
                    sendEmail();

                }
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ShareActivity.this, MainActivity.class);

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });


        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ShareActivity.this, AboutActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ShareActivity.this, LoginActivity.class);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ShareActivity.this, MapsActivity.class);
            }
        });

        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ShareActivity.this, ContactoActivity.class);
            }
        });


    }//onCreate
    private boolean validateFields() {
        String nameValue = nombre.getText().toString().trim();
        String emailValue = correo.getText().toString().trim();
        String phoneValue = tel.getText().toString().trim();
        String modelValue = modelo.getText().toString().trim();
        String issueValue = averia.getText().toString().trim();

        if (nameValue.isEmpty() || emailValue.isEmpty() || phoneValue.isEmpty() ||
                modelValue.isEmpty() || issueValue.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPhone(phoneValue)) {
            Toast.makeText(this, "Número de teléfono inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile("^[+]?[0-9]{10,13}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private void insertDataToDatabase() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, nombre.getText().toString().trim());
        values.put(COLUMN_EMAIL, correo.getText().toString().trim());
        values.put(COLUMN_PHONE, tel.getText().toString().trim());
        values.put(COLUMN_MODEL, modelo.getText().toString().trim());
        values.put(COLUMN_ISSUE, averia.getText().toString().trim());
        // You can also save the photo path if you wish, for now, let's leave it empty.
        values.put(COLUMN_PHOTO_PATH, "");

        database2.insert(TABLE_NAME, null, values);
    }

    private void sendEmail() {
        String nameValue = nombre.getText().toString().trim();
        String emailValue = correo.getText().toString().trim();
        String phoneValue = tel.getText().toString().trim();
        String modelValue = modelo.getText().toString().trim();
        String issueValue = averia.getText().toString().trim();

        // Replace "recipient@example.com" with the actual recipient's email address.
        String recipientEmail = "2120300128@soy.utj.edu.mx";
        String subject = "Información Compartida";
        String message = "Nombre: " + nameValue + "\n" +
                "Correo: " + emailValue + "\n" +
                "Teléfono: " + phoneValue + "\n" +
                "Modelo: " + modelValue + "\n" +
                "Avería: " + issueValue;

        EmailSender emailSender = new EmailSender(this, recipientEmail, subject, message);
        emailSender.execute();
    }



    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);


    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }

    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    //FOTO

    public void tomarFoto(View view){
        abrirCamara();
    }//tomarFoto

    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,1);
        }//intent
    }//abirCamara

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "Tomar fotografía", Toast.LENGTH_SHORT).show();
        if( requestCode == 1 && resultCode == RESULT_OK){
            Toast.makeText(this, "Foto capturada", Toast.LENGTH_SHORT).show();
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            foto.setImageBitmap(imgBitmap);
        }
    }//onActivityResult

}//clase
