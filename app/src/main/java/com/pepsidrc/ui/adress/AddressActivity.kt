package com.pepsidrc.ui.adress

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.jesualex.autocompletelocation.*
import com.pepsidrc.BuildConfig.APPLICATION_ID
import com.pepsidrc.R
import com.pepsidrc.ui.place_order.PlaceOrderActivity
import com.pepsidrc.utils.AndroidUtils
import kotlinx.android.synthetic.main.activity_adress.*
import kotlinx.android.synthetic.main.app_custom_tool_bar_filter.*

class AddressActivity : FragmentActivity(),
    OnMapReadyCallback,
    AutocompleteLocationListener,
    OnSearchListener,
    OnPlaceLoadListener
{
    private var mMap: GoogleMap? = null
    var amount: String = ""
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    /**
     * Represents a geographical location.
     */
    protected var mLastLocation: Location? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adress)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

      /* val bounds = RectangularBounds.newInstance(
            LatLng(-33.880490, 151.184363),
            LatLng(-33.858754, 151.229596))
*/
        val autoCompleteLocation = findViewById<AutocompleteLocation>(R.id.autocomplete_location)
        autoCompleteLocation.setAutoCompleteTextListener(this)
        autoCompleteLocation.setOnSearchListener(this)
        //autoCompleteLocation.setCountry("IN")
        //autoCompleteLocation.setLocationBias(bounds)

        //Set placeListener to auto calculate Place object when a AutocompletePrediction has selected.
        autoCompleteLocation.setPlaceListener(this)

        //Set placeFields to receive only the fields what you need. By default PlaceUtil.getDefaultFields() is call.
        autoCompleteLocation.setPlaceFields(PlaceUtils.defaultFields)
        intent?.run {
            //modified by vijayaraj.s
            amount = getStringExtra(KEY_TOTAL_AMOUNT).toString()
        }
        btn_clear.setOnClickListener {
            val validateAddress1Error =
                AndroidUtils.validateName(autocomplete_location.text.toString())


            if (TextUtils.isEmpty(validateAddress1Error)
            ) {
                startActivity(
                    PlaceOrderActivity.getIntent(
                        this,
                        autocomplete_location.text.toString(), amount
                    )
                )
            } else {
                autocomplete_location.error = validateAddress1Error
            }

        }
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    public override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val madrid = LatLng(24.466667, 54.366669)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(madrid, 16f))
    }

    public override fun onResume() {
        super.onResume()
        btn_clear.visibility = View.VISIBLE
        btn_clear.text = AndroidUtils.getString(R.string.next)
        tv_tool_title.text = AndroidUtils.getString(R.string.step1)
    }
    override fun onTextClear() {
        mMap!!.clear()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (isLocationEnabled()) {

            mFusedLocationClient!!.lastLocation
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        mLastLocation = task.result

                        Log.e(
                            "" + (mLastLocation)!!.longitude, "" +
                                    (mLastLocation)!!.latitude
                        )
                        var a = getAddress((mLastLocation)!!.latitude, (mLastLocation)!!.longitude)
                        autocomplete_location.setText(a)
                        addMapMarker(LatLng(mLastLocation!!.latitude,mLastLocation!!.longitude))
                    } else {
                        /*Log.w(TAG, "getLastLocation:exception", task.exception)
                        showMessage(getString(R.string.no_location_detected))*/
                        requestNewLocationData()
                    }
                }
        } else {
            Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(intent, 0)
        }
    }

    fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0) + " "
        list[0].getLocality() + " " +
                list[0].getAdminArea() + " "
        list[0].getCountryName()
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            var a = getAddress((mLastLocation)!!.latitude, (mLastLocation)!!.longitude)
            autocomplete_location.setText(a)

            addMapMarker(LatLng(mLastLocation.latitude,mLastLocation.longitude))
        }
    }

    private fun showMessage(text: String) {

        Toast.makeText(this@AddressActivity, text, Toast.LENGTH_LONG).show()

    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    override fun onItemSelected(selectedPlace: AutocompletePrediction?) {
        Log.i(javaClass.simpleName, "A autocomplete has selected: ")
        selectedPlace?.let { logAutocomplete(it) }
    }

    private fun showSnackbar(
        mainTextStringId: Int, actionStringId: Int,
        listener: View.OnClickListener
    ) {

        Toast.makeText(this@AddressActivity, getString(mainTextStringId), Toast.LENGTH_LONG).show()
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this@AddressActivity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                View.OnClickListener {
                    // Request permission
                    startLocationPermissionRequest()
                })

        } else {
            Log.i(TAG, "Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest()
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation()
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                    View.OnClickListener {
                        // Build intent that displays the App settings screen.
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                        val uri = Uri.fromParts(
//                            "package",
//                            BuildConfig.APPLICATION_ID, null
//                        )

                        val uri = Uri.fromParts(
                            "package",
                            "BuildConfig.APPLICATION_ID", null
                        )
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
            }
        }
    }
    override fun onSearch(address: String, predictions: List<AutocompletePrediction>) {
        for (prediction in predictions) {
            logAutocomplete(prediction)
        }
    }

    override fun onPlaceLoad(place: Place) {
        addMapMarker(place.latLng)
    }

    private fun logAutocomplete(selectedPrediction: AutocompletePrediction) {
        Log.i(javaClass.simpleName, selectedPrediction.placeId)
        Log.i(javaClass.simpleName, selectedPrediction.getPrimaryText(null).toString())
    }

    private fun addMapMarker(latLng: LatLng?) {
        mMap!!.clear()
        mMap!!.addMarker(MarkerOptions().position(latLng!!))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
    }
    companion object {
        const val KEY_TOTAL_AMOUNT = "KEY_ORDER_ID"
        private val TAG = "LocationProvider"

        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
        fun getIntent(context: Context?, total: String?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, AddressActivity::class.java).putExtra(KEY_TOTAL_AMOUNT, total)

        }
    }
}
