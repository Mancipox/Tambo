package com.tambo.Controller;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.Model.User;
import com.tambo.R;

import java.util.ArrayList;


/**
 * Main activity of student-professor. Implements methods of {@link DataCommunication}
 */
public class YekabeActivity extends AppCompatActivity implements DataCommunication {

    private TabLayout tabLayout;
    private TabItem tabItemStudent;
    private TabItem tabItemProfessor;
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private String questionText;

    private User user;

    private Question questionProfessor;
    private Question questionStudent;

    private String token;



    /**
     * Set the view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_yekabe);

        tabLayout = findViewById(R.id.tablayout);
        tabItemStudent = findViewById(R.id.tabStudent);
        tabItemProfessor = findViewById(R.id.tabProfessor);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_nav,R.string.close_nav);

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerView = findViewById(R.id.navigation_view);

        mDrawerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.account:
                        Toast.makeText(YekabeActivity.this, "My account", Toast.LENGTH_SHORT).show(); break;
                    case R.id.settings:
                        Toast.makeText(YekabeActivity.this, "Settings", Toast.LENGTH_SHORT).show(); break;
                    case R.id.mycart:
                        Toast.makeText(YekabeActivity.this, "My cart", Toast.LENGTH_SHORT).show(); break;
                        default: return true;
                }
                return true;
            }
        });

        final ViewPager viewPager = findViewById(R.id.viewPager);
        //tabLayout.setupWithViewPager(viewPager);

        final PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        Bundle extras = getIntent().getExtras();
        User usermain = (User)extras.get("user");
        setToken((String)extras.get("token"));
        setUser(usermain);
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public void setQuestionText(String text) {
        questionText=text;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user=user;
    }


    @Override
    public Question getQuestionProfessor() {
        return questionProfessor;
    }

    @Override
    public void setQuestionProfessor(Question questionProfessor) {
        this.questionProfessor=questionProfessor;
    }

    @Override
    public Question getQuestionStudent() {
        return questionStudent;
    }

    @Override
    public void setQuestionStudet(Question questionStudent) {
        this.questionStudent=questionStudent;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token=token;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
