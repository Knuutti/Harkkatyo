/* PlayerSearchPage.java

This code file defines the functionality of the search page for players

*/

package com.example.harkkatyo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PlayerSearchPage extends AppCompatActivity {

    ReadJSON rJson = ReadJSON.getInstance();

    private EditText etPlayerSearch;
    private ListView lvPlayerList;

    private User currentUser;

    private ArrayList<Player> playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_search_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        currentUser = rJson.getCurrentUser(this);

        etPlayerSearch = findViewById(R.id.etPlayerSearch);
        lvPlayerList = findViewById(R.id.lvPlayerList);

        lvPlayerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PlayerSearchPage.this, PlayerPage.class);
                intent.putExtra("playerId", playerList.get(i).getPlayerId());
                startActivity(intent);
            }
        });
    }

    // Method for searching players from the API
    public void searchPlayers(View v) {
        if (etPlayerSearch.getText() != null) {
            playerList = rJson.playerSearch(etPlayerSearch.getText().toString(), "lookup");
            if (playerList.size() == 0) {
                playerList.addAll(rJson.playerSearch(etPlayerSearch.getText().toString(), "name"));
            }
            setPlayerList();
        }
    }

    // Method for displaying the search results in a ListView
    private void setPlayerList(){
        if (playerList != null) {
            PlayerListAdapter adapter = new PlayerListAdapter(this, R.layout.player_list_view, playerList);
            lvPlayerList.setAdapter(adapter);
        }
    }

    // This method makes the keyboard disappear when clicking outside of it
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem login = menu.findItem(R.id.login);
        if (currentUser != null) {
            login.setTitle(currentUser.getUsername());
        }
        else {
            login.setTitle("LOGIN");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
                return true;
            case R.id.search:
                Intent searchIntent = new Intent(getApplicationContext(), GameSearchPage.class);
                startActivity(searchIntent);
                return true;
            case R.id.login:
                Intent loginIntent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(loginIntent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
