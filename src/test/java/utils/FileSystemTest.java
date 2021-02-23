package utils;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class FileSystemTest {
  @Test
  void pathJoin() {
    class TestCase {
      final String root;
      final String[] paths;
      final String want;

      TestCase(final String root, final String[] paths, final String want) {
        this.root = root;
        this.paths = paths;
        this.want = want;
      }
    }

    TestCase[] testCases = new TestCase[]{
        new TestCase(
            "/a",
            new String[]{"bbb", "cc.txt"},
            "/a" + File.separator + "bbb" + File.separator + "cc.txt"
        ),
        new TestCase(
            "./a",
            new String[]{"bbb", "cc.txt"},
            "./a" + File.separator + "bbb" + File.separator + "cc.txt"
        ),
        new TestCase(
            "a",
            new String[]{"bbb", "cc.txt"},
            "./a" + File.separator + "bbb" + File.separator + "cc.txt"
        ),
        new TestCase(
            "./a",
            new String[]{"bbb", "cc"},
            "./a" + File.separator + "bbb" + File.separator + "cc"
        ),
        new TestCase("./a", null, "./a"),
        new TestCase("/a", null, "/a"),
        new TestCase("a", null, "./a"),
        new TestCase("", null, "./"),
        new TestCase(null, null, "./"),
        new TestCase(null, null, "./"),
    };

    for (TestCase testCase : testCases) {
      assertEquals(testCase.want, FileSystem.pathJoin(testCase.root, testCase.paths));
    }
  }
}