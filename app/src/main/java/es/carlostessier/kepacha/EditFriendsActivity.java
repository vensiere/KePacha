package es.carlostessier.kepacha;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class EditFriendsActivity extends ListActivity {

    final static String TAG = EditFriendsActivity.class.getName();

    List<ParseUser> mUsers;
    ArrayList<String> usernames;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friends);

        usernames= new  ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,usernames);
        setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ParseQuery query = ParseUser.getQuery();
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(ParseConstants.MAX_USERS);


        final ProgressDialog dialog = ProgressDialog.show(EditFriendsActivity.this,
                getString(R.string.edit_friends_message),
                getString(R.string.waiting_message), true);


        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e == null){
                    //sucess
                    dialog.dismiss();
                    mUsers = users;
                    for(ParseUser user:users){
                        adapter.add(user.getUsername());
                    }

                }
                else{
                    Log.e(TAG, "ParseException caught: ", e);
                    errorEditFriendsdDialog(getString(R.string.error_message));
                }
            }
        });

    }

    private void errorEditFriendsdDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditFriendsActivity.this);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setTitle(R.string.signup_error_title);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog dialog = builder.create();

        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
