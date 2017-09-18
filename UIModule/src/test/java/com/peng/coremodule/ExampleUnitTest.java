package com.peng.coremodule;

import com.peng.library.mantis.hint.HintConfig;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
//    @Test
//    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
//    }
//

    @Test
    public void test_HintConfigBuilder() throws Exception {
        HintConfig.Builder builder = new HintConfig.Builder().layoutEmptyId(1).layoutErrorId(2).layoutNetworkErrorId(3).layoutProgressId(4);
        HintConfig.Builder clone = builder.clone();
        System.out.println("Builder  old -> " + Integer.toHexString(builder.hashCode()) + "  new -> " + Integer.toHexString(clone.hashCode()));
        System.out.println("HintConfig  old -> " + Integer.toHexString(builder.build().hashCode()) + "  new -> " + Integer.toHexString(clone.build().hashCode()));
    }


}