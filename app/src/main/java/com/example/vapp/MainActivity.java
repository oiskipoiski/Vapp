package com.example.vapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements FragmentChangeListener, NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //zet aangemaakte toolbar als actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        //reference nr navigatieview --> kunnen gebruiken
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //icon en drawer fixen dus de drie streepjes om naviagtie te openen
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //wire up the button
        //get the button, what happens when clicked
        //starten met activity --> home openen
        if(savedInstanceState == null) { //enkel dit fragment tonen als opnieuw opgestart, niet als even gekilled en dan weer opstarten door device zelf
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //openen van juiste fragment
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;
            case R.id.nav_numbers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NumbersFragment()).commit();
                break;
            case R.id.nav_graph:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GraphFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true; //item wordt selected na klikken

    }

    @Override
    //willen niet direct terug als navigatievenster open -> enkel terug nr gewone fragment; navigatievenster sluiten
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){ //als drawer langs rechterkan openen (=END) dan moet hier ook .END gebruikt worden, ipv .START
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //anders wel algemene uitvoering functie
            super.onBackPressed();
        }
    }
    @Override
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}