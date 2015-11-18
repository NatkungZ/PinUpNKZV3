package com.example.undine.project_ooad;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageAccount extends Fragment {


    private TextView changePass;
    private TextView deleteAcc;
    private TextView myEvents;
    private TextView logOut;
    private TextView helpAndFeed;

    public ManageAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String fbid = sharedPreferences.getString("userid", "USERID");
        final String uname = sharedPreferences.getString("uname", "Name Surmane");
        //final Bitmap profilePic = getFacebookProfilePicture(fbid);
        /*URL image_value = null;
        try {
            image_value = new URL("http://graph.facebook.com/"+fbid+"/picture" );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/

        final View view = inflater.inflate(R.layout.fragment_manage_account, container, false);
        View layout = inflater.inflate(R.layout.fragment_manage_account, null);

        //Toast.makeText(getContext(),image_value.toString(),Toast.LENGTH_LONG).show();
        if (fbid != null) {
            ImageView uImg = (ImageView) view.findViewById(R.id.user_image);
            Picasso.with(getContext())
                    .load("https://graph.facebook.com/" + fbid + "/picture?type=large")
                    .transform(new CircleTransform())
                    .into(uImg);
            TextView tname = (TextView) view.findViewById(R.id.user_name);
            tname.setText(uname);
        }
        //uImg = (ImageView) getView().findViewById(R.id.user_image);
        //uImg.setImageBitmap(profilePic);

        deleteAcc = (TextView) view.findViewById(R.id.go_delete_account);
        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAcc.setBackground(getResources().getDrawable(R.drawable.background_select_on_click));
                //startActivity(new Intent(view.getContext(), ChangePassword.class));
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this account?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userid","");
                                editor.apply();

                                LoginManager.getInstance().logOut();
                                startActivity(new Intent(view.getContext(), LoginActivity.class));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),fbid,Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });

        myEvents = (TextView) view.findViewById(R.id.go_my_event);
        myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEvents.setBackground(getResources().getDrawable(R.drawable.background_select_on_click));
                startActivity(new Intent(view.getContext(), MyEventsCollection.class));

            }
        });

        logOut = (TextView) view.findViewById(R.id.go_log_out);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut.setBackground(getResources().getDrawable(R.drawable.background_select_on_click));
                LoginManager.getInstance().logOut();
                startActivity(new Intent(view.getContext(), LoginActivity.class));

            }
        });

        helpAndFeed = (TextView)view.findViewById(R.id.go_help);
        helpAndFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpAndFeed.setBackground(getResources().getDrawable(R.drawable.background_select_on_click));
                startActivity(new Intent(view.getContext(), HelpAndFeedback.class));

            }
        });


        return view;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    public static Bitmap getFacebookProfilePicture(String userID){
        URL imageURL = null;
        try {
            imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
