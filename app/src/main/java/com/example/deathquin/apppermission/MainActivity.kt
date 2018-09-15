package com.example.deathquin.apppermission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val tag = "abc"
    private lateinit var listView: ListView

    val appList: ArrayList<String> = ArrayList()
    val appPackageList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check ALL
        //checkAllGrantedPermissions(this)

        // check Not ALL App
        //checkAllGrantPermissions(this, "com.example.deric.syncscreenapp")

        getAppList(this)

        val lv = findViewById(R.id.recipe_list_view) as ListView

        lv.adapter = ListAdapter(this, appList)

        lv.setOnItemClickListener { parent, view, position, id ->

            val appName = appList[position]
            Toast.makeText(this, "Position Clicked: $appName",Toast.LENGTH_SHORT).show()

            val intent = Intent(this@MainActivity,MainDetail::class.java)

            intent.putExtra("packageName", appPackageList[position])
            startActivity(intent)

        }

    }

    fun getAppList(context: Context) {

        val pm = context.packageManager

        val packageInfos = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)

        for (packageInfo in packageInfos) {

            if (packageInfo.requestedPermissions == null) {
                continue
            }

            val appName = packageInfo.applicationInfo.loadLabel(pm).toString()
            val packageName = packageInfo.packageName

            appList.add(appName)
            appPackageList.add(packageName)

        }

    }

    fun checkAllGrantedPermissions(context: Context) {

        val pm = context.packageManager

        val packageInfos = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)

        for (packageInfo in packageInfos) {

            if(packageInfo.requestedPermissions == null) {
                continue
            }

            val appName = packageInfo.applicationInfo.loadLabel(pm).toString()
            val packageName = packageInfo.packageName

            for(permission in packageInfo.requestedPermissions) {

                try {
                    pm.getPermissionInfo(permission, 0)
                } catch (e:PackageManager.NameNotFoundException) {
                    continue
                }

                if(!isGranted(context, packageName, permission)) {
                    Log.i(tag, "$appName $packageName is denied permission $permission")
                } else {
                    Log.i(tag, "$appName $packageName is has granted permission $permission")
                }

            }

        }

    }

    fun checkAllGrantPermissions(context: Context, packName: String) {

        val pm = context.packageManager

        val packageInfos = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)

        for (packageInfo in packageInfos) {

            if(packageInfo.requestedPermissions == null) {
                continue
            }

            val appName = packageInfo.applicationInfo.loadLabel(pm).toString()
            val packageName = packageInfo.packageName

            if(!packageName.equals(packName)) continue

            //Log.i(tag, appName)

            for(permission in packageInfo.requestedPermissions) {

                try {
                    pm.getPermissionInfo(permission, 0)
                } catch (e:PackageManager.NameNotFoundException) {
                    continue
                }

                if(!isGranted(context, packageName, permission)) {
                    Log.i(tag, "$appName $packageName is denied permission $permission")
                } else {
                    Log.i(tag, "$appName $packageName is has granted permission $permission")
                }

            }

        }

    }

    fun isGranted(content: Context, packageName: String, permissionName: String): Boolean {

        val pm = content.packageManager

        val pmCheckPermission = pm.checkPermission(permissionName, packageName)
        val pmGranted = PackageManager.PERMISSION_GRANTED

        return pmCheckPermission == pmGranted

    }



}
