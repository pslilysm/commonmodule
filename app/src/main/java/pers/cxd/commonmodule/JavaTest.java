package pers.cxd.commonmodule;

import pers.cxd.corelibrary.AppHolder;
import pers.cxd.corelibrary.EventHandler;
import pers.cxd.corelibrary.Singleton;
import pers.cxd.corelibrary.util.Pair;

/**
 * @author cxd
 * Created on 2022/8/13 19:11
 */
public class JavaTest {

    private static final Singleton<JavaTest> sInstance = new Singleton<JavaTest>() {
        @Override
        protected JavaTest create() {
            return new JavaTest();
        }
    };
    
    private static void test() {
        Pair<String, String> pair = Pair.obtain("sad", "sdaa");
        EventHandler.getDefault();
        AppHolder.get();
        sInstance.getInstance();
    }
    
}
