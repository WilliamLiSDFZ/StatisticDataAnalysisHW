import com.william.data.Constant;
import com.william.util.Process;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试类。
 * @author WilliamLi
 * @version: 1.0
 * @date 2022/5/25 22:52
 */
public class TestProcess {

    private Process process = null;

    @Before
    public void init() {
        this.process = new Process();
    }

    @Test
    public void testAnalyse() {
        int[][] resultArr = new int[4][4];
        int[][] handWriteData = new int[][]{
                {0, 0, 0, 0},
                {0, 1, 0, 1},
                {4, 8, 12, 5},
                {1, 8, 2, 5}
        };

        String[][] result = process.analyse_Data();
        for (int i = 0; i < Constant.CORRECT_RATE.length; i++) {
            for (int j = 0; j < Constant.FINISH_TIME.length; j++) {
                for (int z = 2; z < result.length; z++) {
                    if (result[z][16].equals(Constant.FINISH_TIME[j]) && result[z][19].equals(Constant.CORRECT_RATE[i])) {
                        resultArr[i][j]++;
                    }
                }
            }
        }

        for (int i=0;i<resultArr.length;i++){
            for (int j=0;j<resultArr[i].length;j++){
                Assert.assertEquals(resultArr[i][j],handWriteData[i][j]);
            }
        }

    }

    @After
    public void dispose() {
        this.process = null;
    }

}
