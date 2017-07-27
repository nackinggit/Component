package com.pnj.file;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 *
 * @author nanjing
 * @date 17-5-15:下午2:26
 */
public class FileUtilTest {
    @Test
    public void getName() throws Exception {
        assertEquals(FileUtil.getName("res"), "res");
    }

}