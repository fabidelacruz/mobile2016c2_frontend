package ar.edu.utn.frba.coeliacs.coeliacapp.models;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;

public class SearchActivity extends AppCompatActivity {

    //DEFINIR ESTOS STRINGS DE MANERA GLOBAL. DONDE SERIA MEJOR???
    public final static String EXTRA_ITEM_TYPE = "ar.edu.utn.frba.coeliacs.coeliacapp.ITEM_TYPE";
    public final static String EXTRA_ITEM_TYPE_PRODUCT = "ar.edu.utn.frba.coeliacs.coeliacapp.ITEM_TYPE_PRODUCT";
    public final static String EXTRA_ITEM_TYPE_SHOP = "ar.edu.utn.frba.coeliacs.coeliacapp.ITEM_TYPE_SHOP";

    private Button productsButton;
    private Button shopsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Get image buttons references
        productsButton = (Button) findViewById(R.id.searchProductButton);
        shopsButton = (Button) findViewById(R.id.searchShopButton);

        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchDetailsActivity.class);
                //PASS BUNDLE IF ARGUMENTS NEEDED
                Bundle b = new Bundle();
                b.putString(EXTRA_ITEM_TYPE, EXTRA_ITEM_TYPE_PRODUCT);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        shopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchDetailsActivity.class);
                //PASS BUNDLE IF ARGUMENTS NEEDED
                Bundle b = new Bundle();
                b.putString(EXTRA_ITEM_TYPE, EXTRA_ITEM_TYPE_SHOP);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
