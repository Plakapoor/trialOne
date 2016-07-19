package lecture14.lecture14permissions;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="hello";
    public static final int REQUEST_CODE_STORAGE_PERM=445;
    String[] reqPerms=new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWriteFile= (Button) findViewById(R.id.btn);
        btnWriteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasWritePerm())
                {
                    writeMyFile();
                }
                else
                {
                    askWrite();
                }
            }
        });







//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
//        {
           int permStatus= ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        Log.d(TAG, "onCreate: "+permStatus);
//
//        switch(permStatus)
//            {
//                case(PackageManager.PERMISSION_GRANTED):
//                    Log.d(TAG, "onCreate: granted");
//                    break;
//                case(PackageManager.PERMISSION_DENIED):
//                    Log.d(TAG, "onCreate: denied" );
//                    break;
//            }

             if(permStatus==PackageManager.PERMISSION_DENIED){

                    ActivityCompat.requestPermissions(this,reqPerms,REQUEST_CODE_STORAGE_PERM);
             }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode==REQUEST_CODE_STORAGE_PERM)
        {
            if(requestCode==REQUEST_CODE_STORAGE_PERM){
                if(permissions.length>0)
                {
                    if(grantResults[1]==PackageManager.PERMISSION_GRANTED){
                        writeMyFile();
                    }
//                    for(String perm:permissions)
//                    {
//                        Log.d(TAG, "onRequestPermissionsResult: "+perm);
//                    }
                }
            }
            if(grantResults.length>0)
            {
                for(int res:grantResults)
                {
                    Log.d(TAG, "onRequestPermissionsResult: "+res);
                }
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void askWrite()
    {
        ActivityCompat.requestPermissions(this,reqPerms,REQUEST_CODE_STORAGE_PERM);
    }
    public boolean hasWritePerm()
    {
        return(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED);
    }
    public void writeMyFile(){

        try {
            File myFile = new File(Environment.getExternalStorageDirectory(), "myFile");
            FileOutputStream fOut = new FileOutputStream(myFile);
            fOut.write("Hello".getBytes());
            fOut.close();
        }
        catch(IOException e){
            e.printStackTrace();

        }

    }
}
