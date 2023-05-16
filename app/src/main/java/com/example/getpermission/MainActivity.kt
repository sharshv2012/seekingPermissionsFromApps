package com.example.getpermission

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private val cameraResultLauncher : ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
                isGranted ->

                if(isGranted){
                    Toast.makeText(this ,"Permission granted for camera"
                    , Toast.LENGTH_LONG).show()
                }

        } // for one permission

    private val cameraAndLocationResultLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
            permissions ->
            permissions.entries.forEach{
                var permissionName = it.key
                val isGranted = it.value

                if(isGranted){
                    if(permissionName == Manifest.permission.ACCESS_FINE_LOCATION)
                    {
                        Toast.makeText(this , "Permission Granted for location" ,
                        Toast.LENGTH_LONG).show()
                    }else if(permissionName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(this , "Permission for coarse location is granted" , Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this , "Permission granted or Camera" , Toast.LENGTH_LONG).show()
                    }
                }else{
                    if(permissionName == Manifest.permission.ACCESS_FINE_LOCATION)
                    {
                        Toast.makeText(this , "Location Permission Denied for location" ,
                            Toast.LENGTH_LONG).show()
                    }else if(permissionName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(this , "Permission for coarse location is denied" , Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this , "Permission Denied or Camera" , Toast.LENGTH_LONG).show()
                    }
                }
            }



        } //for multiple permissions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission:Button = findViewById(R.id.btnPermission)
        btnCameraPermission.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                showRationaleDialog(
                    "permission demo requires certain access",
                    "camera cannot be used because camera  access is denied"
                )
            }else{
                cameraAndLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.ACCESS_FINE_LOCATION)
                )

            }
        }
    }

    private fun showRationaleDialog(
        title: String,
        message: String,
    ){
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel"){dialog , _->
                dialog.dismiss()
            }
        builder.create().show()
    }

}