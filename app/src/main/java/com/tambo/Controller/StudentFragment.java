package com.tambo.Controller;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Class;
import com.tambo.Model.User;
import com.tambo.Utils.Utils;
import com.tambo.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass. Represents the student activity @BD
 */
public class StudentFragment extends Fragment {


    private ArrayList<Class> aClasses;

    private AdapterQuestionStudent adapter;

    private EditText editTextQuestionTitle;

    private TextView textViewmessage;

    private FloatingActionButton floatingActionButtonPostQuestion;

    private RecyclerView recyclerView;

    private DataCommunication mCallBack; //To pass information between fragments

    private TextView textViewKarma;

    private User mainUser; //Main user after login

    private RequestQueue queue;


    public StudentFragment() {
        // Required empty public constructor
    }

    /**
     * Obligatory attach the fragment to main activity to pass information with {@link DataCommunication}
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallBack = (DataCommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DataCommunication");
        }
    }

    /**
     * State onCreateView. Logic of the view here
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        aClasses = new ArrayList<Class>();
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        editTextQuestionTitle = view.findViewById(R.id.editTextQuestion);

        textViewmessage = view.findViewById(R.id.textInfoStudent);

        //Creating the recyclerView
        recyclerView = view.findViewById(R.id.recyclerViewStudent);

        textViewKarma = view.findViewById(R.id.textkarma);
        mainUser = mCallBack.getUser();


        FloatingActionButton buttonKarma = view.findViewById(R.id.fabkarma);
        buttonKarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadCoinsByUser();
            }
        });

        queue = Volley.newRequestQueue(getContext());

        //FloatingActionButton buttonReload = view.findViewById(R.id.fabstudent);

        /*buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadQuestionsByUser();
            }
        });*/

        //Specify an adapter to recycler view
        adapter = new AdapterQuestionStudent(getContext(), aClasses, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mCallBack.setQuestionStudet(aClasses.get(position));
                CompletedDialogFragment dialogFragment = new CompletedDialogFragment();
                dialogFragment.show(getFragmentManager(), "infoComplete");
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        floatingActionButtonPostQuestion = view.findViewById(R.id.buttonPostQuestion);
        floatingActionButtonPostQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra("usermain", mainUser);
                intent.putExtra("token", mCallBack.getToken());
                startActivityForResult(intent, 2);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadCoinsByUser();
        reloadQuestionsByUser();
    }

    public void reloadQuestionsByUser() {
        StringRequest myReq = new StringRequest(Request.Method.GET, Connect_Server.url_server + "ServletClass?option=askedBy&user=" + Utils.toJson(mainUser) + "&authorization=" + mCallBack.getToken(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type QuestionsType = new TypeToken<ArrayList<Class>>() {
                }.getType();
                Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
                aClasses = gson.fromJson(response, QuestionsType);
                //Error because not attached context to this fragment?
                //if(aClasses.isEmpty()) textViewmessage.setText(getText(R.string.empty_classes_student));
                //else textViewmessage.setText(getText(R.string.student_message));
                adapter = new AdapterQuestionStudent(getContext(), aClasses, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, final int position) {
                        mCallBack.setQuestionStudet(aClasses.get(position));
                        final CompletedDialogFragment dialogFragment = CompletedDialogFragment.newInstance(new DataCommunication.DialogCallback() {

                            @Override
                            public void updateRecyclerView(Class question) {
                                //Nothing to do, this case is used in StudentFragment
                            }

                            @Override
                            public void updateRecyclerView(boolean state) {
                                if (state) {
                                    AdapterQuestionStudent.QuestionViewHolder questionViewHolder = adapter.getHolder(position);
                                    questionViewHolder.imageView.setImageResource(R.drawable.correct2);
                                }
                            }
                        });
                        //CompletedDialogFragment dialogFragment = new CompletedDialogFragment();
                        dialogFragment.show(getFragmentManager(), "infoComplete");
                    }
                });
                recyclerView.setAdapter(adapter);
                // adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Ha ocurrido un error conectando al servidor", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(myReq);
    }

    public void reloadCoinsByUser() {
        StringRequest myReq = new StringRequest(Request.Method.POST, Connect_Server.url_server + "ServletUser", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] respSplit = response.split("/");

                User user_temp = (User) Utils.fromJson(respSplit[0], User.class);
                if (user_temp != null) {
                    textViewKarma.setText("$ " + user_temp.getKarma());
                    mCallBack.setUser(user_temp);
                } else {
                    Toast.makeText(getContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error en la petición", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("option", "login");
                MyData.put("user", Utils.toJson(mainUser));
                return MyData;
            }

        };
        queue.add(myReq);
    }

    public void scheduleReload(final int periodCalled) {
        final Handler handler = new Handler();
        Runnable runnabler = new Runnable() {
            @Override
            public void run() {
                reloadQuestionsByUser();
                reloadCoinsByUser();
                handler.postDelayed(this, periodCalled);
            }
        };
        handler.postDelayed(runnabler, periodCalled);
        getActivity().runOnUiThread(runnabler);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && data != null) {
            Bundle bundle = data.getBundleExtra("bundle");
            Class aClass = (Class) bundle.getSerializable("question");
            adapter.setItem(aClass);
            reloadCoinsByUser();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem item = menu.findItem(R.id.spinner);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                reloadQuestionsByUser();
                reloadCoinsByUser();
                break;
            default:
                break;
        }
        return true;
    }

}
