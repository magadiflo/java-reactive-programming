package dev.magadiflo.app.sec09;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec09.helper.FlightSearch;

public class Lec06MergeUseCase {
    public static void main(String[] args) {
        FlightSearch.getFlights()
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }
}
