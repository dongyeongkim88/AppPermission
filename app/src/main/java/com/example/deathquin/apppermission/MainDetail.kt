package com.example.deathquin.apppermission

import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_detail.*


class MainDetail : AppCompatActivity() {

    private val tag = "abc"

    private val currentLocation = hashMapOf("ACCESS_FINE_LOCATION" to false, "READ_EXTERNAL_STORAGE" to false, "READ_CALENDAR" to false, "WRITE_CALENDAR" to false,  "CAMERA" to false)
    private val biometricInformation = hashMapOf("CAMERA" to false, "BODY_SENSORS" to false, "USE_FINGERPRINT" to false)
    private val phoneNumber = hashMapOf("ACCESS_FINE_LOCATION" to false, "ANSWER_PHONE_CALLS" to false, "SEND_SMS" to false, "GET_ACCOUNTS" to false)
    private val socialGraph = hashMapOf("READ_CONTACTS" to false, "PROCESS_OUTGOING_CALLS" to false, "ACCESS_FINE_LOCATION" to false, "READ_SMS" to false, "READ_CALL_LOG" to false)
    private val addressOrAreaOfLiving = hashMapOf("READ_CALENDAR" to false, "READ_EXTERNAL_STORAGE" to false, "ACCESS_FINE_LOCATION" to false, "SEND_SMS" to false)
    private val workPlace = hashMapOf("READ_CALL_LOG" to false, "ACCESS_FINE_LOCATION" to false, "READ_EXTERNAL_STORAGE" to false)
    private val googleId = hashMapOf("GET_ACCOUNTS" to false, "READ_PHONE_NUMBERS" to false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_detail)

        val packageName: String = intent.getStringExtra("packageName")

        checkAllGrantPermissions(this, packageName)

        val listHeader = listOf("Current Location", "Biometric Information", "Phone Number", "Social Graph", "Address or Area of Living", "Workplace", "Googld ID")

        val profilingTable =  HashMap<String, HashMap<String, Boolean>>()

        profilingTable.put("Current Location", currentLocation)
        profilingTable.put("Biometric Information", biometricInformation)
        profilingTable.put("Phone Number", phoneNumber)
        profilingTable.put("Social Graph", socialGraph)
        profilingTable.put("Address or Area of Living", addressOrAreaOfLiving)
        profilingTable.put("Workplace", workPlace)
        profilingTable.put("Googld ID", googleId)

        val currentLocationList = listOf("ACCESS_FINE_LOCATION", "READ_EXTERNAL_STORAGE", "READ_CALENDAR", "WRITE_CALENDAR", "CAMERA")
        val biometricInformationList = listOf("CAMERA", "BODY_SENSORS", "USE_FINGERPRINT")
        val phoneNumberList = listOf("ACCESS_FINE_LOCATION", "ANSWER_PHONE_CALLS", "SEND_SMS", "GET_ACCOUNTS")
        val socialGraphList = listOf("READ_CONTACTS", "PROCESS_OUTGOING_CALLS", "ACCESS_FINE_LOCATION", "READ_SMS", "READ_CALL_LOG")
        val addressOrAreaOfLivingList = listOf("READ_CALENDAR", "READ_EXTERNAL_STORAGE", "ACCESS_FINE_LOCATION", "SEND_SMS")
        val workPlaceList = listOf("READ_CALL_LOG", "ACCESS_FINE_LOCATION", "READ_EXTERNAL_STORAGE")
        val googleIdList = listOf("GET_ACCOUNTS", "READ_PHONE_NUMBERS")

        val listChild = HashMap<String, List<String>>()

        listChild.put(listHeader[0], currentLocationList)
        listChild.put(listHeader[1], biometricInformationList)
        listChild.put(listHeader[2], phoneNumberList)
        listChild.put(listHeader[3], socialGraphList)
        listChild.put(listHeader[4], addressOrAreaOfLivingList)
        listChild.put(listHeader[5], workPlaceList)
        listChild.put(listHeader[6], googleIdList)

        val expandableListAdapter = ExpandaListAdapter(this, listHeader, listChild, profilingTable)
        expandable_list_view.setAdapter(expandableListAdapter)

        val count = expandableListAdapter.groupCount

        for (i in 1..count) {
            expandable_list_view.expandGroup(i-1)
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

            for(permission in packageInfo.requestedPermissions) {

                try {
                    pm.getPermissionInfo(permission, 0)
                } catch (e: PackageManager.NameNotFoundException) {
                    continue
                }

                if(!isGranted(context, packageName, permission)) {
                    //Log.i(tag, "$appName $packageName is denied permission $permission")
                } else {
                    //Log.i(tag, "$appName $packageName is has granted permission $permission")

                    val splitPermission = permission.split(".")
                    val splitPermissionName = splitPermission[splitPermission.size-1]

                    if(currentLocation.containsKey(splitPermissionName)) { currentLocation[splitPermissionName] = true }
                    if(biometricInformation.containsKey(splitPermissionName)) { biometricInformation[splitPermissionName] = true }
                    if(phoneNumber.containsKey(splitPermissionName)) { phoneNumber[splitPermissionName] = true }
                    if(socialGraph.containsKey(splitPermissionName)) { socialGraph[splitPermissionName] = true }
                    if(addressOrAreaOfLiving.containsKey(splitPermissionName)) { addressOrAreaOfLiving[splitPermissionName] = true }
                    if(workPlace.containsKey(splitPermissionName)) { workPlace[splitPermissionName] = true }
                    if(googleId.containsKey(splitPermissionName)) { googleId[splitPermissionName] = true }

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
