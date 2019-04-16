package com.tambo.Controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.Model.User;
import com.tambo.R;




/**
 * Main activity of student-professor. Implements methods of {@link DataCommunication}
 */
public class YekabeActivity extends AppCompatActivity implements DataCommunication {

    private TabLayout tabLayout;
    private TabItem tabItemStudent;
    private TabItem tabItemProfessor;
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;
    private ImageView imageView;
    private TextView textView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    //private Toolbar mTopToolbar;

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
        final Bundle extras = getIntent().getExtras();
        final User usermain = (User)extras.get("user");
        final String sendtoken = (String) extras.get("token");
        setToken((String)extras.get("token"));

        setUser(usermain);

        setContentView(R.layout.activity_yekabe);

        tabLayout = findViewById(R.id.tablayout);
        tabItemStudent = findViewById(R.id.tabStudent);
        tabItemProfessor = findViewById(R.id.tabProfessor);
        //mTopToolbar = findViewById(R.id.my_toolbar);
        //setSupportActionBar(mTopToolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_nav,R.string.close_nav);


        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDrawerView = findViewById(R.id.navigation_view);

        View headerView = mDrawerView.getHeaderView(0);

        imageView = headerView.findViewById(R.id.image_profile);
        textView = headerView.findViewById(R.id.textViewUsername);

                mDrawerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.account:
                        Toast.makeText(YekabeActivity.this, "Mi perfil", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(YekabeActivity.this,MyProfileActivity.class);
                        intent2.putExtra("user", usermain);
                        intent2.putExtra("token",(String)extras.get("token"));

                        startActivity(intent2);
                        break;
                    case R.id.events:
                        DialogFragment calendarFragment = CalendarFragment.newInstance();
                        calendarFragment.show(getSupportFragmentManager(), "calendar_fragment");
                        break;
                    case R.id.information:
                        Intent intent = new Intent(YekabeActivity.this,InformationActivity.class);
                        startActivity(intent);
                        break;
                        default: return true;
                }
                return true;
            }
        });

        final ViewPager viewPager = findViewById(R.id.viewPager);

        final PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);




        textView.setText(textView.getText()+" "+usermain.getUserName()+"?");
        imageView.setImageResource((usermain.getGender().equals("Masculino"))?R.drawable.user_mal:R.drawable.user_fem);

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        /*int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            Toast.makeText(YekabeActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
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

}
