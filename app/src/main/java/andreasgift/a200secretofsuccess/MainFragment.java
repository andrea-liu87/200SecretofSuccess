package andreasgift.a200secretofsuccess;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



/**
 * Created by Andrea on 29/9/2016.
 */

public class MainFragment extends Fragment {

    private String quote = "";
    public int lineNumb  ;
    public MainFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        lineNumb = getArguments().getInt("lineNumb");
     View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) fragmentView.findViewById(R.id.textView);
        quote = getQuote(getActivity().getApplicationContext(), lineNumb);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(quote);
        return fragmentView;
    }


    /*This method to read the quote from the file on the assets folder
    based on the number of line that provided*/
    public String getQuote(Context context, int numb){
        String ret = "";

        try {
            InputStream inputStream = context.getAssets().open("source.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ( numb >0 && (receiveString = bufferedReader.readLine()) != null ) {
                    ret = receiveString.toString();
                    numb = numb-1;
                }

                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

}
