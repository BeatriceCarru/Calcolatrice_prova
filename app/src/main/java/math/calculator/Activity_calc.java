package math.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;

import org.mariuszgromada.math.mxparser.*;

public class Activity_calc extends AppCompatActivity {

    private TextView risultato;
    private EditText schermo;
    private TextView title;
    private ImageButton spazio;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        risultato = findViewById(R.id.risultato);
        schermo = findViewById(R.id.display);
        title = findViewById(R.id.benvenuto);
        spazio=findViewById(R.id.spazio);
        schermo.setShowSoftInputOnFocus(false); /*Imposta un valore che indica se il metodo di
                                                 input soft verrà reso visibile quando questo
                                                  TextView viene messo a punto. L'impostazione
                                                  predefinita è true */
        //saluto nel menu
        Intent i = getIntent();
        String nickname = i.getStringExtra("nickname");
        title.setText(String.format("%s %s", "Ciao", nickname));
        //assegnamento variabile
        drawerLayout = findViewById(R.id.drawer_layout);
        spazio.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clear(v);
                return true;
            }
        });

    }

    //navigation drawer
    public void openDrawer(View view) {
        //apro drawer
        drawerLayout.openDrawer(GravityCompat.START);
    }


    public void closeDrawer(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //quando il drawer è aperto lo chiudo
            drawerLayout.closeDrawer(GravityCompat.START);

        }
    }

    public void clickHome(View view) {
        recreate();
    }



    public void clickconvertitore(View view) {
        redirectActivity(this, Convertitore.class);
    }


    public void Clickgrafico(View view) {
        redirectActivity(this, Grafico.class);
    }


    public void ClickLogout(View view) {
        logout(this);
    }

    public static void logout(Activity activity_drawer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity_drawer);
        builder.setTitle("Logout");
        builder.setMessage("sei sicuro di voler fare il logout?");
        builder.setPositiveButton("SI", (dialog, which) -> {
            //finish activity
            activity_drawer.finishAffinity();
            System.exit(0);
        });
        builder.setNegativeButton("N0", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);

    }


    //calcolatrice
    private void upText(String strToAdd) {
        String oldStr = schermo.getText().toString();
        int cursore = schermo.getSelectionStart();
        String leftStr = oldStr.substring(0, cursore);
        String rightStr = oldStr.substring(cursore);
        schermo.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
        schermo.setSelection(cursore + strToAdd.length());
    }


    public void zero(View v) {
        upText("0");
    }

    public void uno(View v) {
        upText("1");
    }

    public void due(View v) {
        upText("2");
    }

    public void tre(View v) {
        upText("3");
    }

    public void quattro(View v) {
        upText("4");
    }

    public void cinque(View v) {
        upText("5");
    }

    public void sei(View v) {
        upText("6");
    }

    public void sette(View v) {
        upText("7");
    }

    public void otto(View v) {
        upText("8");
    }

    public void nove(View v) {
        upText("9");
    }

    public void clear(View v) {
        schermo.setText("");
        risultato.setText("");
    }

    public void esponente(View v) {
        upText("^(");
    }

    public void parentesi(View v) {
        int cursorePos = schermo.getSelectionStart();
        int apertaPar = 0;
        int chiusaPar = 0;
        int lunghezza = schermo.getText().length();
        for (int i = 0; i < cursorePos; i++) {
            if (schermo.getText().toString().substring(i, i + 1).equals("(")) {
                apertaPar += 1;
            }
            if (schermo.getText().toString().substring(i, i + 1).equals(")")) {
                chiusaPar += 1;
            }
        }
        if (apertaPar == chiusaPar || schermo.getText().toString().substring(lunghezza - 1, lunghezza).equals("(")) {
            upText("(");
        }
        if (chiusaPar < apertaPar && !schermo.getText().toString().substring(lunghezza - 1, lunghezza).equals("(")) {
            upText(")");
        }
        schermo.setSelection(cursorePos + 1);
    }

    public void divisione(View v) {
        upText("÷");
    }

    public void moltiplicazione(View v) {
        upText("×");
    }

    public void add(View v) {
        upText("+");
    }

    public void sub(View v) {
        upText("-");
    }

    public void addsub(View v) {
        upText("+/-");
    }

    public void punto(View v) {
        upText(".");
    }

    public void uguale(View v) {
        String user = schermo.getText().toString();
        risultato.setText(user);

        user = user.replaceAll("÷", "/");
        user = user.replaceAll("×", "*");

        Expression exp = new Expression(user);
        String result = String.valueOf(exp.calculate()); //usa la libreria mxparser
        schermo.setText(result);
        schermo.setSelection(result.length());
    }

    public void spazio(View v) {
        int cursore = schermo.getSelectionStart();
        int textLen = schermo.getText().length();

        if (cursore != 0 && textLen != 0) {
            SpannableStringBuilder sel = (SpannableStringBuilder) schermo.getText();
            //replace sostituisce il testo compreso tra start e end con la stringa che vogliamo
            sel.replace(cursore - 1, cursore, "");
            schermo.setText(sel);
            schermo.setSelection(cursore - 1);
        }
    }

    public void coseno(View view) {

        upText("cos(");


    }

    public void seno(View view) {
        upText("sin(");
    }

    public void percentuale(View view) {
        upText("%");
    }

    public void tangente(View view) {
        upText("tan(");
    }

    public void arcseno(View view) {
        upText("arcsin(");
    }

    public void arccoseno(View view) {
        upText("arccos(");
    }

    public void arctangente(View view) {
        upText("arctan(");
    }

    public void logaritmo_naturale(View view) {
        upText("ln");
    }

    public void logaritmo(View view) {
        upText("log");
    }

    public void radice(View view) {
        upText("sqrt(");
    }

    public void nepero(View view) {
        upText("e");
    }

    public void pigreco(View view) {
        upText("pi");
    }

    public void modulo(View view) {
        upText("abs(");
    }

    public void primo(View view) {
        upText("ispr(");
    }

    public void xQuadro(View view) {
        upText("^(2)");
    }

    public void scriviX(View view) {
        upText("x");
    }
    public void scriviY(View view) {
        upText("y");
    }
    public void funzione(View view) {
        upText("f(");
    }
}

