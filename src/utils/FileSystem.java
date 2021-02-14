package utils;

import java.io.File;

/**
 * this class define the methods concerned to file system.
 */
public class FileSystem {
  /**
   * pathJoin method create file path.
   * this method can use in Unix and Windows (not confirming in max).
   * if root is empty and paths' length is 0, return "./".
   * if root's prefix is not "/" or "./", add root to "./".
   *
   * @param root  String
   * @param paths String
   * @return String
   */
  public static String pathJoin(final String root, final String... paths) {
    if (root == null || root.equals("")) {
      return "./";
    }

    String normalizedRoot = root;
    if (!root.startsWith("/") && !root.startsWith("./")) {
      normalizedRoot = "./" + normalizedRoot;
    }

    if (paths == null || paths.length == 0) {
      return normalizedRoot;
    }

    StringBuilder filePath = new StringBuilder(normalizedRoot);
    for (String p : paths) {
      filePath.append(File.separator).append(p);
    }

    return filePath.toString();
  }
}
