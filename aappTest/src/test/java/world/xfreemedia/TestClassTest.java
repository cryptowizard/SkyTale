package world.xfreemedia;

import android.database.Observable;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestClassTest {

    @Test
    public void tood()
    {
        Observable<Boolean> result = new Observable<Boolean>() {
            @Override
            public void registerObserver(Boolean observer) {
                super.registerObserver(observer);
                System.out.println(observer);
            }
        };


        result.registerObserver(true);
        result.registerObserver(true);
        result.registerObserver(false);
    }
}