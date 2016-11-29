package ar.edu.utn.frba.coeliacs.coeliacapp.models.map.fragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.CoordinatesLocation;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.location.LocationPermissionHandler;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.ViewDiscountActivity;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.search.ShopDetailsActivity;

import static com.google.android.gms.maps.CameraUpdateFactory.newCameraPosition;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap map;
    private List<ShopMarker> markers = new ArrayList<>();

    public void clearMarkers() {
        if (map != null) {
            map.clear();
        }
        markers.clear();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        for (ShopMarker shop : markers) {
            if (marker.equals(shop.getMarker())) {
                Intent shopIntent = new Intent(getActivity(), ShopDetailsActivity.class);
                shopIntent.putExtra(ViewDiscountActivity.EXTRA_SHOP, shop.getShop());
                startActivity(shopIntent);
            }
        }
    }

    public interface MapFragmentListener {

        void mapReady();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.getMapAsync(this);
    }

    public void setMarkers(List<Shop> shops) {
        clearMarkers();
        if (shops != null) {
            Iterator<Shop> iterator = shops.iterator();

            while (iterator.hasNext()) {
                Shop shop = iterator.next();
                CoordinatesLocation location = shop.getLocation();
                MarkerOptions position = new MarkerOptions()
                        .title(shop.getName())
                        .snippet(getString(R.string.more_info))
                        .position(new LatLng(location.getLat(), location.getLong()));

                Marker marker = map.addMarker(position);

                ShopMarker shopMarker = new ShopMarker();
                shopMarker.setMarker(marker);
                shopMarker.setShop(shop);
                markers.add(shopMarker);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnInfoWindowClickListener(this);
        ((MapFragmentListener) this.getActivity()).mapReady();
    }

    public void enableMyLocation() {
        map.setMyLocationEnabled(true);
    }

    public void disableMyLocation() {
        map.setMyLocationEnabled(false);
    }

    public void updateCameraByShops(int cameraView) {
        if (!markers.isEmpty()) {
            Shop shop = markers.get(0).getShop();
            LatLng latLang = new LatLng(shop.getLocation().getLat(), shop.getLocation().getLong());

            CameraPosition camera = new CameraPosition.Builder().target(latLang).zoom(cameraView).build();
            map.moveCamera(newCameraPosition(camera));
        }
    }

    public void updateCameraByLocation(Location location, int cameraView) {
        CameraPosition camera = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(cameraView)
                .build();
        map.moveCamera(newCameraPosition(camera));
    }

}