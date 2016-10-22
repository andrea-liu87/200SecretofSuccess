package andreasgift.a200secretofsuccess;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*The Top 200 Secret of Success and the Pillars of Self Mastery
  Author : Robin S. Sharma
  Apps created by Andrea 2016*/
public class MainActivity extends ActionBarActivity {

    public int umb ;
    private String LINE_NUMBER = "lineNumb";


    @Override
    protected void onCreate(Bundle outState) {
        super.onCreate(outState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPrefs = getPreferences(getApplicationContext().MODE_PRIVATE);
        umb = sharedPrefs.getInt(LINE_NUMBER,1);
        replaceFragment(umb);
        final Button nextButton = (Button) findViewById(R.id.nextbutton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                umb = umb+1;
                replaceFragment(umb);
                savePage(umb);  //saved page position if application is terminated
            }
        });
        final Button prevButton = (Button) findViewById(R.id.prevbutton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                umb = umb-1;
                replaceFragment(umb);
                savePage(umb);  //saved page position if application is terminated
            }
        });
        final Button shareButton = (Button) findViewById (R.id.sharebutton);
        shareButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                shareAction(new MainFragment());
            }
        });
    }


    /*Inflate the menu in the main activity*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_goto) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Go To Page :");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (input.getText().toString().isEmpty()){}
                    else{
                    final int number = Integer.parseInt(input.getText().toString());
                    replaceFragment(number);
                    umb = number;
                    savePage(umb);}
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
            return true;
        }

        if (id == R.id.about) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("The Top 200 Secret of Success & the Pillars of Self Mastery " + '\n'+
                    "Author : Robin S. Sharma" + '\n'
                    +"--------------------------------------"+'\n'
                    +"2016 - AndreaGiftMail@yahoo.com");
            alertDialog.setTitle("About");
            alertDialog.setButton(RESULT_OK,"DONE",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
                });
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /*This method is to call new fragment and replace the existing fragment in
      the main activity, and passing arguments to fragment
     */
    private void replaceFragment(int input) {
        umb = input;
        Bundle bundle = new Bundle();
        bundle.putInt(LINE_NUMBER, input);
        Fragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        // Add the fragment to the activity, pushing this transaction on to the back stack.
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    /*This method is to save the page number to SharedPreferences*/
    private void savePage (int input){
        SharedPreferences sharedPrefs = getPreferences(getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(LINE_NUMBER,umb);
        editor.commit();
    }

    /*This method to share the quote to any available social media*/
     private void shareAction(MainFragment fragment){
         String quote = fragment.getQuote(this, umb);
         Intent sendIntent = new Intent();
         sendIntent.setAction(Intent.ACTION_SEND);
         sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
         sendIntent.putExtra(Intent.EXTRA_TEXT, quote);
         sendIntent.setType("text/plain");
         startActivity(Intent.createChooser(sendIntent,"Share via"));
     }
}
