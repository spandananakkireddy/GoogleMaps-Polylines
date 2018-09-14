package com.example.spandananakkireddy.inclass09;

import java.util.ArrayList;

/**
 * Created by Spandana Nakkireddy on 3/26/2018.
 */

public class Trip {

    ArrayList<Points> points;

    public ArrayList<Points> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Points> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "points=" + points +
                '}';
    }

    public  static class Points{
        String latitude,longitude;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return "Points{" +
                    "latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    '}';
        }
    }
}
