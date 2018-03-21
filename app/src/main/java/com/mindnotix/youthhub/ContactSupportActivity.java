package com.mindnotix.youthhub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.ContactSupportListAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.contactsupport.list.DataContactSupportFilter;
import com.mindnotix.model.contactsupport.list.Supportlist;
import com.mindnotix.model.contactsupport.master.DataContactSupportMaster;
import com.mindnotix.model.contactsupport.master.Ticket_status;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Admin on 19-02-2018.
 */

public class ContactSupportActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = ContactSupportActivity.class.getSimpleName();
    private static final int PICK_GALLERY_IMAGE_REQUEST_CODE = 200;
    private static RelativeLayout bottomLayout;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    String NextPage = "";
    ProgressDialog progressDialog;
    ArrayList<Ticket_status> ticket_statusArrayList = new ArrayList<>();
    ArrayAdapter<Ticket_status> ticket_statusArrayAdapter;
    ArrayList<Supportlist> supportlistArrayList = new ArrayList<>();
    TextView txtEmpty;
    RecyclerViewClickListener listener;
    ContactSupportListAdapter contactSupportListAdapter;
    String edtSearch = "";
    private boolean userScrolled = true;
    private android.support.v7.widget.Toolbar toolbar;
    private LinearLayout progressBar;
    private LinearLayout linearFilter;
    private LinearLayout txtRaiseTicket;
    private Button linearTickerSearch;
    private EditText edtTicketSearch;
    private Spinner spnrTicketStatus;
    private SwipeRefreshLayout swipeRefreshLayout;
    List<String> pageIds = new ArrayList<>();
    private String edtTicketSearchText="";
    private int spnrTicketStatusText=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);
        //   setContentView(R.layout.content_load_contact_support);

        UiInitialization();

        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {


            }
        };

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        getSupportMaster();

                                    }
                                }
        );


        //getSupportMaster();


        implementScrollListener();
    }


    @Override
    public void onRefresh() {

        spnrTicketStatus.setSelection(0);
        edtTicketSearch.setText("");
        swipeRefreshLayout.setRefreshing(true);

        NextPage = "";
        getSupportList();
    }

    private void getSupportMaster() {

        ticket_statusArrayList.clear();


        //  progressDialog = Utils.createProgressDialog(this);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU2IiwidXNlcm5hbWUiOiJwcm0ubW9oYW5yYWpAZ21haWwuY29tIn0sImlhdCI6MTUxOTAyOTg1NSwianRpIjoiYWpKbFpGTjFRbXN5YkZOQ1UzSlBabkpVVGtJPSIsImlzcyI6Imh0dHBzOlwvXC9iYWNrZW5kLnlvdXRoaHViLmNvLm56XC8iLCJuYmYiOjE1MTkwMjk4NTUsImV4cCI6MTUxOTYzNDY1NX0.2yS4aD808K8PXS05h0Ky-zMTOeqsY5XfsPyeSzXJOCI";

        String token = Pref.getPreToken();

        Log.d(TAG, "changePassword: token " + token);

        Call<DataContactSupportMaster> call = apiService.getSupportFilterMaster(token);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataContactSupportMaster>() {
            @Override
            public void onResponse(Call<DataContactSupportMaster> call, Response<DataContactSupportMaster> response) {
                //  progressDialog.dismiss();
                try {


                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {


                            final Ticket_status regional_council_list = new Ticket_status();
                            regional_council_list.setId("00");
                            regional_council_list.setName("All");
                            ticket_statusArrayList.add(0, regional_council_list);

                            ticket_statusArrayList.addAll(response.body().getData().getTicket_status());


                            ticket_statusArrayAdapter = new ArrayAdapter<Ticket_status>(ContactSupportActivity.this,
                                    android.R.layout.simple_spinner_item, ticket_statusArrayList);
                            ticket_statusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnrTicketStatus.setAdapter(ticket_statusArrayAdapter);

                            edtTicketSearch.setText(edtTicketSearchText);
                            spnrTicketStatus.setSelection(spnrTicketStatusText);

                            Log.d(TAG, "onResponse:supportlistArrayList " + supportlistArrayList.size());

                            NextPage = "";
                            getSupportList();
                        } else {

                            Toast.makeText(ContactSupportActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(ContactSupportActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(ContactSupportActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(ContactSupportActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(ContactSupportActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(ContactSupportActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(ContactSupportActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ContactSupportActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    // progressDialog.dismiss();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataContactSupportMaster> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.linearTickerSearch:

                getSupportListSearch();
                break;

            case R.id.txtRaiseTicket:
                //showChangeLangDialog();

                Intent ticketIntent = new Intent(ContactSupportActivity.this, TicketRaiseActivity.class);
                startActivity(ticketIntent);

                //startActivityForResult(ticketIntent, 200);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                    Supportlist supportlist = new Supportlist();
                    supportlist.setFirst_name(data.getStringExtra("first_name"));
                    supportlist.setLast_name(data.getStringExtra("last_name"));
                    supportlist.setTicket_id(data.getStringExtra("ticket_id"));
                    supportlist.setPost_date(data.getStringExtra("post_date"));
                    supportlist.setSubject(data.getStringExtra("subject"));
                    supportlist.setReplies(data.getStringExtra("replies"));
                    supportlist.setTicket_status(data.getStringExtra("ticket_status"));
                    supportlist.setTicket_status_id(data.getStringExtra("ticket_status_id"));
                    supportlist.setUser_id(data.getStringExtra("user_id"));
                    supportlistArrayList.add(supportlist);
                    contactSupportListAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    private void UiInitialization() {

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        txtEmpty = findViewById(R.id.txtEmpty);
        bottomLayout = (RelativeLayout) findViewById(R.id.loadItemsLayout_recyclerView);

        this.spnrTicketStatus = (Spinner) findViewById(R.id.spnrTicketStatus);
        this.edtTicketSearch = (EditText) findViewById(R.id.edtTicketSearch);
        this.txtRaiseTicket = (LinearLayout) findViewById(R.id.txtRaiseTicket);
        this.linearTickerSearch = (Button) findViewById(R.id.linearTickerSearch);
        this.txtRaiseTicket.setOnClickListener(this);
        this.linearTickerSearch.setOnClickListener(this);
        //  this.linearFilter = (LinearLayout) findViewById(R.id.linearFilter);
//        this.linearFilter.setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // getSupportActionBar().setTitle("Job list");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }

    }


    //For check internet connection available or not
    public boolean nbIsNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    //For download image from url
    public void nbShowNoInternet(Activity tutorialActivity) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(tutorialActivity);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.no_internet_dialog, null);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);

        dialogView.findViewById(R.id.txtOkay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }


    private void implementScrollListener() {

        recyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {

                        super.onScrollStateChanged(recyclerView, newState);

                        // If scroll state is touch scroll then set userScrolled
                        // true
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            userScrolled = true;

                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx,
                                           int dy) {

                        super.onScrolled(recyclerView, dx, dy);
                        // Here get the child count, item count and visibleitems
                        // from layout manager

                        visibleItemCount = linearLayoutManager.getChildCount();
                        totalItemCount = linearLayoutManager.getItemCount();
                        pastVisiblesItems = linearLayoutManager
                                .findFirstVisibleItemPosition();

                        // Now check if userScrolled is true and also check if
                        // the item is end then update recycler view and set
                        // userScrolled to false
                        if (userScrolled
                                && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                            userScrolled = false;
                            loadMoreListItems();
                        }

                    }

                });
    }


    private void getSupportList() {

        Log.d(TAG, "getSupportList: edtTicketSearch" + edtTicketSearch.getText().toString());
        Log.d(TAG, "getSupportList: edtTicketSearch getId " + ticket_statusArrayList.get(spnrTicketStatus.getSelectedItemPosition()).getId());
        //supportlistArrayList.clear();

        supportlistArrayList = new ArrayList<>();
      /*  if (!progressDialog.isShowing())
            progressDialog = Utils.createProgressDialog(this);
*/
        Map<String, String> data = new HashMap<>();
        data.put("fm", NextPage);
        data.put("tvalue", "");
        data.put("tshow", "");

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("joblist  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU2IiwidXNlcm5hbWUiOiJwcm0ubW9oYW5yYWpAZ21haWwuY29tIn0sImlhdCI6MTUxOTAyOTg1NSwianRpIjoiYWpKbFpGTjFRbXN5YkZOQ1UzSlBabkpVVGtJPSIsImlzcyI6Imh0dHBzOlwvXC9iYWNrZW5kLnlvdXRoaHViLmNvLm56XC8iLCJuYmYiOjE1MTkwMjk4NTUsImV4cCI6MTUxOTYzNDY1NX0.2yS4aD808K8PXS05h0Ky-zMTOeqsY5XfsPyeSzXJOCI";

        String token = Pref.getPreToken();
        Log.d(TAG, "changePassword: token " + token);

        Call<DataContactSupportFilter> call = apiService.getContactSupportList(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataContactSupportFilter>() {
            @Override
            public void onResponse(Call<DataContactSupportFilter> call, Response<DataContactSupportFilter> response) {
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

                try {

                    Log.d(TAG, "onResponse: contact support " + new Gson().toJson(response.body()));
                    // progressDialog.dismiss();


                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {

                            txtEmpty.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            supportlistArrayList.addAll(response.body().getData().getSupportlist());


                            Log.d(TAG, "onResponse:supportlistArrayList " + supportlistArrayList.size());
                            NextPage = response.body().getNextpage();

                            contactSupportListAdapter = new ContactSupportListAdapter(ContactSupportActivity.this, supportlistArrayList, listener);
                            recyclerView.setAdapter(contactSupportListAdapter);

                            // contactSupportListAdapter.notifyDataSetChanged();

                        } else {
                            NextPage = "";
                            txtEmpty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            Toast.makeText(ContactSupportActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(ContactSupportActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(ContactSupportActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(ContactSupportActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(ContactSupportActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(ContactSupportActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(ContactSupportActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ContactSupportActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataContactSupportFilter> call, Throwable t) {
                //progressDialog.dismiss();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

                t.printStackTrace();
            }
        });
    }


    private void getSupportListSearch() {

        Log.d(TAG, "getSupportList: edtTicketSearch" + edtTicketSearch.getText().toString());
        Log.d(TAG, "getSupportList: edtTicketSearch getId " + ticket_statusArrayList.get(spnrTicketStatus.getSelectedItemPosition()).getId());
        //supportlistArrayList.clear();

        supportlistArrayList = new ArrayList<>();
      /*  if (!progressDialog.isShowing())
            progressDialog = Utils.createProgressDialog(this);
*/
        progressDialog = Utils.createProgressDialog(this);
        Map<String, String> data = new HashMap<>();
        data.put("fm", "");
        data.put("tvalue", edtTicketSearch.getText().toString());
        if (!ticket_statusArrayList.get(spnrTicketStatus.getSelectedItemPosition()).getId().equals("00"))
            data.put("tshow", ticket_statusArrayList.get(spnrTicketStatus.getSelectedItemPosition()).getId());
        else
            data.put("tshow", "");

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("joblist  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU2IiwidXNlcm5hbWUiOiJwcm0ubW9oYW5yYWpAZ21haWwuY29tIn0sImlhdCI6MTUxOTAyOTg1NSwianRpIjoiYWpKbFpGTjFRbXN5YkZOQ1UzSlBabkpVVGtJPSIsImlzcyI6Imh0dHBzOlwvXC9iYWNrZW5kLnlvdXRoaHViLmNvLm56XC8iLCJuYmYiOjE1MTkwMjk4NTUsImV4cCI6MTUxOTYzNDY1NX0.2yS4aD808K8PXS05h0Ky-zMTOeqsY5XfsPyeSzXJOCI";

        String token = Pref.getPreToken();
        Log.d(TAG, "changePassword: token " + token);

        Call<DataContactSupportFilter> call = apiService.getContactSupportList(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataContactSupportFilter>() {
            @Override
            public void onResponse(Call<DataContactSupportFilter> call, Response<DataContactSupportFilter> response) {
                // stopping swipe refresh
                // swipeRefreshLayout.setRefreshing(false);

                progressDialog.dismiss();
              //  edtTicketSearch.setText("");
                try {

                    Log.d(TAG, "onResponse: contact support " + new Gson().toJson(response.body()));
                    // progressDialog.dismiss();


                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {

                            txtEmpty.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            supportlistArrayList.addAll(response.body().getData().getSupportlist());


                            Log.d(TAG, "onResponse:supportlistArrayList " + supportlistArrayList.size());
                            NextPage = response.body().getNextpage();

                            contactSupportListAdapter = new ContactSupportListAdapter(ContactSupportActivity.this, supportlistArrayList, listener);
                            recyclerView.setAdapter(contactSupportListAdapter);

                            // contactSupportListAdapter.notifyDataSetChanged();

                        } else {
                            NextPage = "";
                            txtEmpty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            Toast.makeText(ContactSupportActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(ContactSupportActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(ContactSupportActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(ContactSupportActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(ContactSupportActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(ContactSupportActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(ContactSupportActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ContactSupportActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                 //   edtTicketSearch.setText("");
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataContactSupportFilter> call, Throwable t) {
                edtTicketSearch.setText("");
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });


        edtTicketSearchText=edtTicketSearch.getText().toString();
        spnrTicketStatusText=spnrTicketStatus.getSelectedItemPosition();
    }


    private void loadMoreListItems() {

        if (!pageIds.contains(NextPage)) {


            Log.d(TAG, "loadMoreListItems: Next Page " + NextPage);

            bottomLayout.setVisibility(View.VISIBLE);
            Map<String, String> data = new HashMap<>();
            data.put("fm", NextPage);
            data.put("tvalue", edtTicketSearch.getText().toString());
            if (!ticket_statusArrayList.get(spnrTicketStatus.getSelectedItemPosition()).getId().equals("00"))
                data.put("tshow", ticket_statusArrayList.get(spnrTicketStatus.getSelectedItemPosition()).getId());
            else
                data.put("tshow", "");

            for (Map.Entry<String, String> entry : data.entrySet()) {
                String key = entry.getKey().toString();
                String value = entry.getValue();
                System.out.println("joblist  key, " + key + " value " + value);
            }


            pageIds.add(NextPage);
            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            //String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTU2IiwidXNlcm5hbWUiOiJwcm0ubW9oYW5yYWpAZ21haWwuY29tIn0sImlhdCI6MTUxOTAyOTg1NSwianRpIjoiYWpKbFpGTjFRbXN5YkZOQ1UzSlBabkpVVGtJPSIsImlzcyI6Imh0dHBzOlwvXC9iYWNrZW5kLnlvdXRoaHViLmNvLm56XC8iLCJuYmYiOjE1MTkwMjk4NTUsImV4cCI6MTUxOTYzNDY1NX0.2yS4aD808K8PXS05h0Ky-zMTOeqsY5XfsPyeSzXJOCI";
            String token = Pref.getPreToken();
            Log.d(TAG, "changePassword: token " + token);

            Call<DataContactSupportFilter> call = apiService.getContactSupportList(token, data);
            Log.d(TAG, "logMeIn: url " + call.request().url());

            call.enqueue(new Callback<DataContactSupportFilter>() {
                @Override
                public void onResponse(Call<DataContactSupportFilter> call, Response<DataContactSupportFilter> response) {

                    try {

                        bottomLayout.setVisibility(View.GONE);
                        if (response.code() == 200) {

                            if (response.body().getStatus().equals("1")) {

                                NextPage = response.body().getNextpage();


                                if (!NextPage.equals("0")) {
                                    supportlistArrayList.addAll(response.body().getData().getSupportlist());
                                    contactSupportListAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(ContactSupportActivity.this, "No more data", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                NextPage = "";
                                //Toast.makeText(ContactSupportActivity.this, "No record available.", Toast.LENGTH_SHORT).show();
                            }
                        } else if (response.code() == 304) {

                            Toast.makeText(ContactSupportActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 400) {

                            Toast.makeText(ContactSupportActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 401) {

                            Toast.makeText(ContactSupportActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 403) {

                            Toast.makeText(ContactSupportActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 404) {

                            Toast.makeText(ContactSupportActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                        } else if (response.code() == 422) {

                            Toast.makeText(ContactSupportActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(ContactSupportActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<DataContactSupportFilter> call, Throwable t) {
                    bottomLayout.setVisibility(View.GONE);
                    pageIds.remove(NextPage);
                    t.printStackTrace();
                }
            });
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


}
